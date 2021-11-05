package io.vertx.lang.loom.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import io.vertx.core.http.HttpMethod;
import io.vertx.loom.core.Vertx;
import io.vertx.loom.core.http.HttpClient;
import io.vertx.loom.ext.web.Router;

public class HttpClientTest extends AbstactAsyncLoomTest {

  @Test
  public void testBodyHandler() {
    Vertx vertx = Vertx.vertx();
    Router router = Router.router(vertx);

    router.route("/test").handler(rc -> {
      rc.end();
    });

    vertx.createHttpServer().requestHandler(router).listen(0, "localhost", onSuccess(s -> {
      HttpClient client = vertx.createHttpClient();
      client.request(HttpMethod.GET, s.actualPort(), "localhost", "/test", onSuccess(req -> {
        req.send(onSuccess(resp -> {
          resp.bodyHandler(buff -> {
            assertNotNull(buff.toString());
            // TODO Fails because http client is currently excluded from loomification
            expectLoomThread();
            testComplete();
          });
        }));
      }));
    }));
    waitFor();
  }

}
