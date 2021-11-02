//package io.vertx.core.it;
//
//import org.junit.Test;
//
//import io.vertx.core.http.HttpClient;
//import io.vertx.core.http.HttpMethod;
//import io.vertx.loom.core.Vertx;
//import io.vertx.loom.ext.web.Router;
//import io.vertx.test.core.AsyncTestBase;
//
//public class LoomRouterTest extends AsyncTestBase {
//
//	@Test
//	public void testServer() {
//		Vertx vertx = Vertx.vertx();
//		Router router = Router.router(vertx);
//		router.route("/test").handler(rc -> {
//			assertNotNull("The context should not be null",Vertx.currentContext());
//			rc.end(Thread.currentThread().getName());
//		});
//		vertx.createHttpServer().requestHandler(router).listen(8080, "localhost", onSuccess(s -> {
//			HttpClient client = vertx.createHttpClient();
//			client.request(HttpMethod.GET, 8080, "localhost", "/test", onSuccess(req -> {
//				req.send(onSuccess(resp -> {
//					resp.bodyHandler(buff -> {
//						assertEquals("vert.x-virtual-thread-0", buff.toString());
//						testComplete();
//					});
//				}));
//			}));
//		}));
//		await();
//	}
//
//}
