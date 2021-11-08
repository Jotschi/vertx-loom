package io.vertx.lang.loom.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class RouterTest extends AbstactAsyncLoomTest {

  @Test
  public void testErrorHandler() {
    Vertx vertx = Vertx.vertx();
    Router router = Router.router(vertx);
    router.errorHandler(400, err -> {
      expectLoomThread();
      testComplete();
    });

    router.route("/test").handler(rc -> {
      rc.fail(400);
    });

    vertx.createHttpServer().requestHandler(router).listen(0, "localhost", onSuccess(s -> {
      HttpClient client = vertx.createHttpClient();
      client.request(HttpMethod.GET, s.actualPort(), "localhost", "/test", onSuccess(req -> {
        req.send(onSuccess(resp -> {
          resp.bodyHandler(buff -> {
            assertNotNull(buff.toString());
          });
        }));
      }));
    }));
    waitFor();
  }
}
