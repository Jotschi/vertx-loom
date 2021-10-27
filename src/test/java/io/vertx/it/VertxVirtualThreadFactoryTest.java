package io.vertx.it;
import org.junit.Test;

import io.vertx.test.core.VertxTestBase;

public class VertxVirtualThreadFactoryTest extends VertxTestBase {

  @Test
  public void testJsonObject() {
    vertx.runOnContext(v -> {
      Thread current = Thread.currentThread();
      assertTrue("The returned thread is not a virtual thread", current.isVirtual());
      testComplete();
    });
    await();
  }
}

