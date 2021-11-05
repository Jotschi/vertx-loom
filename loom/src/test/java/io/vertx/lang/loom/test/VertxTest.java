package io.vertx.lang.loom.test;

import org.junit.Test;

import io.vertx.loom.core.Vertx;

public class VertxTest extends AbstactAsyncLoomTest {

  @Test
  public void testRunOnContext() {
    Vertx vertx = Vertx.vertx();
    vertx.runOnContext(v -> {
      expectLoomThread();
      testComplete();
    });
    waitFor();
  }

  @Test
  public void testSetPeriodic() {
    Vertx vertx = Vertx.vertx();
    vertx.setPeriodic(500, t -> {
      expectLoomThread();
      vertx.cancelTimer(t.intValue());
      testComplete();
    });
    waitFor();
  }

  @Test
  public void testSetTimer() {
    Vertx vertx = Vertx.vertx();
    vertx.setTimer(500, t -> {
      expectLoomThread();
      testComplete();
    });
    waitFor();
  }

  @Test
  public void testDeployVerticle() {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(TestVerticle.class.getName(), onSuccess((String deploymentId) -> {
      expectLoomThread();
      testComplete();
    }));
    waitFor();
  }

  @Test
  public void testUndeployVerticle() {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new TestVerticle(), onSuccess(deploymentId -> {
      expectLoomThread();
      vertx.undeploy(deploymentId, onSuccess(v -> {
        expectLoomThread();
        testComplete();
      }));
    }));
    waitFor();
  }
}
