package io.vertx.lang.loom;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import io.vertx.core.Context;
import io.vertx.core.Future;

/**
 * await() must be run on a Virtual Thread. Whenever we block, the Virtual Thread will yield execution. So await() blocks until the Future.onComplete handler
 * unblocks us.
 */
class Coroutine {

  private final ReentrantLock lock = new ReentrantLock();
  private final Condition cond = lock.newCondition();
  private final Context vertxContext;

  Coroutine(Context vertxContext) {
    this.vertxContext = vertxContext;
  }

  public <A> A await(Future<A> future) {
    lock.lock();
    try {

      future.onComplete(ar -> {
        // Future.onComplete can execute immediately,
        // which would cause deadlock if we don't run it asynchronously.
        vertxContext.runOnContext(voidd -> {
          lock.lock();
          try {
            cond.signal();
          } finally {
            lock.unlock();
          }
        });
      });

      cond.await();

      if (future.succeeded()) {
        return future.result();
      } else {
        throw new RuntimeException(future.cause());
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }

  }

  public <A> List<A> await(io.reactivex.rxjava3.core.Observable<A> obs) {
    return await(obs.toList());
  }

  public <A> A await(io.reactivex.rxjava3.core.Single<A> single) {
    lock.lock();
    try {
      AtomicReference<A> ref = new AtomicReference<>();
      single.subscribe(res -> {
        // Future.onComplete can execute immediately,
        // which would cause deadlock if we don't run it asynchronously.
        vertxContext.runOnContext(voidd -> {
          lock.lock();
          try {
            ref.set(res);
            cond.signal();
          } finally {
            lock.unlock();
          }
        });

      }, err -> {
        throw new RuntimeException(err);
      });

      cond.await();

      return ref.get();

    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }
  }
}
