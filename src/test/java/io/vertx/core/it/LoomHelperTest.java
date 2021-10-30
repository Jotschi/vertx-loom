package io.vertx.core.it;

import org.junit.Test;

import io.vertx.core.Vertx;
import io.vertx.loom.core.LoomHelper;
import io.vertx.test.core.AsyncTestBase;

public class LoomHelperTest extends AsyncTestBase {

	@Test
	public void testExecuteErrorHandling() {
		Vertx vertx = Vertx.vertx();
		vertx.runOnContext(e -> {
			LoomHelper.execute(() -> new RuntimeException("Bäm"));
		});
	}

	@Test
	public void testExecute() {
		Vertx vertx = Vertx.vertx();
		vertx.runOnContext(e -> {
			LoomHelper.execute(() -> {
				System.out.println("Done");
				testComplete();
			});
		});
		await();
	}

	@Test(expected = NullPointerException.class)
	public void testExecuteOutside() {
		LoomHelper.execute(() -> new RuntimeException("Bäm"));
	}

	@Test
	public void testNesting() throws InterruptedException {
		Vertx vertx = Vertx.vertx();
		vertx.runOnContext(e -> {
			LoomHelper.execute(() -> {
				LoomHelper.execute(() -> {
					LoomHelper.execute(() -> {
						LoomHelper.execute(() -> {
							System.out.println("Done");
							testComplete();
						});
					});
				});
			});
		});
		await();
	}
}
