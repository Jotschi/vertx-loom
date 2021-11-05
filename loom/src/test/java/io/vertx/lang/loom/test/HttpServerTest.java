package io.vertx.lang.loom.test;

import org.junit.Test;

import io.vertx.loom.core.Vertx;
import io.vertx.loom.ext.web.Router;

public class HttpServerTest extends AbstactAsyncLoomTest {

  @Test
  public void testListen() {
    Vertx vertx = Vertx.vertx();
    Router router = Router.router(vertx);

    vertx.createHttpServer().requestHandler(router).listen(0, "localhost", onSuccess(s -> {
      expectLoomThread();
      testComplete();
    }));
    waitFor();
  }
}
