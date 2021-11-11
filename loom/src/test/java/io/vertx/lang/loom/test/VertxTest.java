package io.vertx.lang.loom.test;

import org.junit.Test;

import io.vertx.core.Vertx;

public class VertxTest extends AbstactAsyncLoomTest {

  @Test
  public void testRunOnContext() {
    Vertx vertx = Vertx.vertx();
    vertx.runOnContext(v -> {
      expectVirtualEventloopThread();
      testComplete();
    });
    waitFor();
  }

  @Test
  public void testSetPeriodic() {
    Vertx vertx = Vertx.vertx();
    vertx.setPeriodic(500, t -> {
      expectVirtualEventloopThread();
      vertx.cancelTimer(t.intValue());
      testComplete();
    });
    waitFor();
  }

  @Test
  public void testSetTimer() {
    Vertx vertx = Vertx.vertx();
    vertx.setTimer(500, t -> {
      expectVirtualEventloopThread();
      testComplete();
    });
    waitFor();
  }

  @Test
  public void testDeployVerticle() {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(TestVerticle.class.getName(), onSuccess((String deploymentId) -> {
      expectVirtualEventloopThread();
      testComplete();
    }));
    waitFor();
  }

  @Test
  public void testUndeployVerticle() {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new TestVerticle(), onSuccess(deploymentId -> {
      expectVirtualEventloopThread();
      vertx.undeploy(deploymentId, onSuccess(v -> {
        expectVirtualEventloopThread();
        testComplete();
      }));
    }));
    waitFor();
  }
}
