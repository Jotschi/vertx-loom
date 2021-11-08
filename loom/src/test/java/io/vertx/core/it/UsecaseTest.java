package io.vertx.core.it;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.lang.loom.Async;
import io.vertx.test.core.AsyncTestBase;;

public class UsecaseTest extends AsyncTestBase {

	@Test
	public void testServer() {
		Vertx vertx = Vertx.vertx();
		Router router = Router.router(vertx);
		router.route("/test").handler(rc -> {
			List<Long> userIds = Async.await(loadUserIds());
			JsonObject response = new JsonObject();
			response.put("userIds", new JsonArray(userIds));
			rc.end(response.encodePrettily());
		});

		vertx.createHttpServer().requestHandler(router).listen(0, "localhost", onSuccess(s -> {
			HttpClient client = vertx.createHttpClient();
			client.request(HttpMethod.GET, s.actualPort(), "localhost", "/test", onSuccess(req -> {
				req.send(onSuccess(resp -> {
					resp.bodyHandler(buff -> {
						assertNotNull(buff.toString());
						testComplete();
					});
				}));
			}));
		}));
		await();
	}

	private Future<List<Long>> loadUserIds() {
		return Async.async(() -> {
			Thread.sleep(100);
			List<Long> nestedIds = Async.await(loadIdsFromDb());
			return nestedIds;
		});
	}

	private Future<List<Long>> loadIdsFromDb() {
		return Async.async(() -> {
			Thread.sleep(100);
			if (true) {
//				throw new RuntimeException("Error");
			}
			return Arrays.asList(1L, 2L, 3L, 42L);
		});
	}

}
