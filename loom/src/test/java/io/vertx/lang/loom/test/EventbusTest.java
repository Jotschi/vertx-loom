package io.vertx.lang.loom.test;

import org.junit.Test;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class EventbusTest extends AbstactAsyncLoomTest {

  @Test
  public void testConsumer() {
    Vertx vertx = Vertx.vertx();
    EventBus eb = vertx.eventBus();
    eb.consumer("test", msg -> {
      expectVirtualEventloopThread();
      testComplete();
    });
    eb.publish("test", "dummy");
    waitFor();
  }

}
