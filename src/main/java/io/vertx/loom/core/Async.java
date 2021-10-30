package io.vertx.loom.core;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public final class Async {

	private static final ThreadLocal<AsyncContext> ASYNC_CONTEXT = new ThreadLocal<>();
	private static final ThreadLocal<Coroutine> AWAIT_CONTEXT = new ThreadLocal<>();

	private record AsyncContext(Context vertxContext, ThreadFactory vThreadFactory) {
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

		AsyncContext asyncContext = new AsyncContext(vertxContext, vtFactory);
		ASYNC_CONTEXT.set(asyncContext);
		return asyncContext;
	}

	public static <A> A await(Future<A> future) {
		Coroutine coroutine = Objects.requireNonNull(AWAIT_CONTEXT.get(), "Must call await from inside an async scope");

		return coroutine.await(future);
	}

	public static void async(Runnable runnable) {
		AsyncContext asyncContext = ASYNC_CONTEXT.get();

		if (asyncContext == null) {
			asyncContext = prepareAsyncContext();
		}

		AsyncContext finalAsyncCtx = asyncContext;

		finalAsyncCtx.vThreadFactory.newThread(() -> {
			try {
				ASYNC_CONTEXT.set(finalAsyncCtx);
				AWAIT_CONTEXT.set(new Coroutine(finalAsyncCtx.vertxContext));
				runnable.run();
				AWAIT_CONTEXT.remove();
			} catch (Throwable t) {
				throw t;
			}
		}).start();
	}

	public static Context currentVertxContext() {
		AsyncContext asyncContext = ASYNC_CONTEXT.get();
		return asyncContext.vertxContext;
	}
}
