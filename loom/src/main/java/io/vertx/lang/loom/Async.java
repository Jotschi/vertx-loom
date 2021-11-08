package io.vertx.lang.loom;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.ContextInternal;
import io.vertx.lang.loom.rxjava3.LoomContextScheduler;

public final class Async {

  private static final ThreadLocal<AsyncContext> ASYNC_CONTEXT = new ThreadLocal<>();
  private static final ThreadLocal<Coroutine> AWAIT_CONTEXT = new ThreadLocal<>();

  private record AsyncContext(ContextInternal vertxContext, ThreadFactory vThreadFactory) {
  }

  private Async() {

  }

  public static <A> Future<A> async(Callable<A> fn) {
    AsyncContext asyncContext = ASYNC_CONTEXT.get();

    if (asyncContext == null) {
      asyncContext = prepareAsyncContext();
    }
    AsyncContext finalAsyncCtx = asyncContext;

    Promise<A> promise = Promise.promise();

    finalAsyncCtx.vThreadFactory.newThread(() -> {
      try {
        ASYNC_CONTEXT.set(finalAsyncCtx);
        AWAIT_CONTEXT.set(new Coroutine(finalAsyncCtx.vertxContext));
        promise.complete(fn.call());
        AWAIT_CONTEXT.remove();
      } catch (Throwable t) {
        promise.fail(t);
      }
    }).start();

    return promise.future();
  }
  
  public static <A> List<A> await(Observable<A> obs) {
    Coroutine coroutine = Objects.requireNonNull(AWAIT_CONTEXT.get(), "Must call await from inside an async scope");
    return coroutine.await(obs);
  }

  public static <A> A await(Single<A> single) {
    Coroutine coroutine = Objects.requireNonNull(AWAIT_CONTEXT.get(), "Must call await from inside an async scope");
    return coroutine.await(single);
  }

  public static <A> A await(Future<A> future) {
    Coroutine coroutine = Objects.requireNonNull(AWAIT_CONTEXT.get(), "Must call await from inside an async scope");
    return coroutine.await(future);
  }

  public static Thread async(Runnable runnable) {
    AsyncContext asyncContext = ASYNC_CONTEXT.get();

    if (asyncContext == null) {
      asyncContext = prepareAsyncContext();
    }

    AsyncContext finalAsyncCtx = asyncContext;

    Thread thread = finalAsyncCtx.vThreadFactory.newThread(() -> {
      try {
        ASYNC_CONTEXT.set(finalAsyncCtx);
        AWAIT_CONTEXT.set(new Coroutine(finalAsyncCtx.vertxContext));
        runnable.run();
        AWAIT_CONTEXT.remove();
      } catch (Throwable t) {
        throw t;
      }
    });
    thread.start();
    return thread;
  }

  public static Context currentVertxContext() {
    AsyncContext asyncContext = ASYNC_CONTEXT.get();
    return asyncContext.vertxContext;
  }

  public static ContextInternal currentVertxContextInternal() {
    AsyncContext asyncContext = ASYNC_CONTEXT.get();
    return asyncContext.vertxContext;
  }

  private static AsyncContext prepareAsyncContext() {
    Context vertxContext = Objects.requireNonNull(Vertx.currentContext(),
      "This thread needs a Vertx Context to use async/await");

    // Executor that executes the partner Virtual Thread on this Vertx Context.
    // todo; cast Context to ContextInternal, and use one of dispatch/emit/execute?
    // https://vert-x3.github.io/advanced-vertx-guide/index.html#_firing-events
    Executor contextThreadExecutor = command -> {
      vertxContext.runOnContext(v -> command.run());
    };
    ThreadFactory vtFactory = Thread.ofVirtual().name("vert.x-virtual-thread-", 0).scheduler(contextThreadExecutor)
      .factory();

    AsyncContext asyncContext = new AsyncContext((ContextInternal)vertxContext, vtFactory);
    ASYNC_CONTEXT.set(asyncContext);
    return asyncContext;
  }

  public static Scheduler scheduler() {
    return new LoomContextScheduler();
  }

}
