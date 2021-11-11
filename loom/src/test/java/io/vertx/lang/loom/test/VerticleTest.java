package io.vertx.lang.loom.test;

import org.junit.Test;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class VerticleTest extends AbstactAsyncLoomTest {

  @Test
  public void testVerticleStart() {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new AbstractVerticle() {
      @Override
      public void start(Promise<Void> startPromise) throws Exception {
        expectVirtualEventloopThread();
        startPromise.complete();
        testComplete();
      }
    }, onSuccess(deploymentId -> {
      expectVirtualEventloopThread();
    }));
    waitFor();
  }

  @Test
  public void testVerticleStop() {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new AbstractVerticle() {
      @Override
      public void stop(Promise<Void> stopPromise) throws Exception {
        expectVirtualEventloopThread();
        stopPromise.complete();
      }
    }, onSuccess(deploymentId -> {
      expectVirtualEventloopThread();
      vertx.undeploy(deploymentId, onSuccess(v -> {
        expectVirtualEventloopThread();
        testComplete();
      }));
    }));
    waitFor();
  }
}
