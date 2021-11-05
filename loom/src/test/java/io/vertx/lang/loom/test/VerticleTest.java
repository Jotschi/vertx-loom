package io.vertx.lang.loom.test;

import org.junit.Test;

import io.vertx.core.Promise;
import io.vertx.loom.core.AbstractVerticle;
import io.vertx.loom.core.Vertx;

public class VerticleTest extends AbstactAsyncLoomTest {

  @Test
  public void testVerticleStart() {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new AbstractVerticle() {
      @Override
      public void start(Promise<Void> startPromise) throws Exception {
        expectLoomThread();
        startPromise.complete();
      }
    }, onSuccess(deploymentId -> {
      expectLoomThread();
    }));
    waitFor();
  }

  @Test
  public void testVerticleStop() {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new AbstractVerticle() {
      @Override
      public void stop(Promise<Void> stopPromise) throws Exception {
        expectLoomThread();
        stopPromise.complete();
      }
    }, onSuccess(deploymentId -> {
      expectLoomThread();
      vertx.undeploy(deploymentId, onSuccess(v -> {
        expectLoomThread();
        testComplete();
      }));
    }));
    waitFor();
  }
}
