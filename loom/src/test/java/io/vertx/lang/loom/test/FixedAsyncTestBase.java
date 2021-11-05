package io.vertx.lang.loom.test;

import java.util.function.Consumer;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.test.core.AsyncTestBase;

/**
 * Extended {@link AsyncTestBase} which does not expose the {@link AsyncTestBase#await(long, java.util.concurrent.TimeUnit) method}
 */
public class FixedAsyncTestBase extends AsyncTestBase {

  @Override
  public void await() {
    super.await();
  }

  @Override
  public <T> Handler<AsyncResult<T>> onSuccess(Consumer<T> consumer) {
    return super.onSuccess(consumer);
  }

  @Override
  public void testComplete() {
    super.testComplete();
  }

}
