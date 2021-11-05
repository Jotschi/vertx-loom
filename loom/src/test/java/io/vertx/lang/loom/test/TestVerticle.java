package io.vertx.lang.loom.test;

import io.vertx.core.Promise;
import io.vertx.loom.core.AbstractVerticle;

public class TestVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("TestVerticle#start");
    startPromise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    System.out.println("TestVerticle#stop");
    stopPromise.complete();
  }
}
