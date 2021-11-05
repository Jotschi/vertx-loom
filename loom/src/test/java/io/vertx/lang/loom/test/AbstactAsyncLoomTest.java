package io.vertx.lang.loom.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.lang.loom.Async;

/**
 * Normally I would use io.vertx.test.core.AsyncTestBase but this leads to a nameclash with used await methods.
 */
public abstract class AbstactAsyncLoomTest {

  FixedAsyncTestBase base = new FixedAsyncTestBase();

  @Before
  public void before() throws Exception {
    base.before();
  }

  @After
  public void after() throws Exception {
    base.after();
  }

  public void waitFor() {
    base.await(20, TimeUnit.SECONDS);
  }

  public <T> Handler<AsyncResult<T>> onSuccess(Consumer<T> consumer) {
    return base.onSuccess(consumer);
  }

  public void testComplete() {
    base.testComplete();
  }

  public void expectLoomThread() {
    Thread thread = Thread.currentThread();
    assertTrue("The thread did not have the correct name. Got: " + thread.getName(), thread.getName().startsWith("vert.x-virtual-thread-"));
    assertTrue("The current thread is not a virtual one", thread.isVirtual());
    assertNotNull("The context could not be loaded.", Async.currentVertxContext());
  }

  public void expectEventloopThread() {
    Thread thread = Thread.currentThread();
    assertTrue("The thread did not have the correct name. Got: " + thread.getName(), thread.getName().startsWith("vert.x-eventloop-thread-"));
    assertFalse("The current thread should not be virtual", thread.isVirtual());
    assertNotNull("The context could not be loaded.", Async.currentVertxContext());
  }

}
