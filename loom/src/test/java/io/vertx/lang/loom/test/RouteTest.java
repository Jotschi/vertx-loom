package io.vertx.lang.loom.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class RouteTest extends AbstactAsyncLoomTest {

  @Test
  public void testRouteHandler() {
    Vertx vertx = Vertx.vertx();
    Router router = Router.router(vertx);

    router.route("/test").handler(rc -> {
      expectLoomThread();
      testComplete();
      rc.end();
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
