package io.vertx.core.it;

import static io.vertx.lang.loom.Async.async;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.vertx.core.Vertx;
import io.vertx.lang.loom.test.AbstactAsyncLoomTest;

public class AsyncTest extends AbstactAsyncLoomTest {

  @Test
  public void testExecuteErrorHandling() {
    Vertx vertx = Vertx.vertx();
    vertx.runOnContext(e -> {
      async(() -> new RuntimeException("Bäm"));
    });
  }

  @Test
  public void testExecute() {
    Vertx vertx = Vertx.vertx();
    vertx.runOnContext(e -> {
      async(() -> {
        assertEquals("vert.x-virtual-thread-0", Thread.currentThread().getName());
        testComplete();
      });
    });
    waitFor();
  }

  @Test(expected = NullPointerException.class)
  public void testExecuteOutside() {
    async(() -> new RuntimeException("Bäm"));
  }

  @Test
  public void testNesting() throws InterruptedException {
    Vertx vertx = Vertx.vertx();
    vertx.runOnContext(e -> {
      async(() -> {
        async(() -> {
          async(() -> {
            async(() -> {
              assertEquals("vert.x-virtual-thread-3", Thread.currentThread().getName());
              testComplete();
            });
          });
        });
      });
    });
    waitFor();
  }
}
