package io.vertx.core.it;

import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import io.vertx.core.Vertx;
import io.vertx.loom.core.LoomHelper;

public class LoomHelperTest {

	@Test
	public void testExecuteErrorHandling() {
		Vertx vertx = Vertx.vertx();
		vertx.runOnContext(e -> {
			LoomHelper.execute(() -> new RuntimeException("Bäm"));
		});
	}

	@Test(expected = NullPointerException.class)
	public void testExecuteOutside() {
		LoomHelper.execute(() -> new RuntimeException("Bäm"));
	}

	@Test
	public void testNesting() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		Vertx vertx = Vertx.vertx();
		vertx.runOnContext(e -> {
			LoomHelper.execute(() -> {
				LoomHelper.execute(() -> {
					LoomHelper.execute(() -> {
						LoomHelper.execute(() -> {
							System.out.println("Done");
							latch.countDown();
						});
					});
				});
			});
		});

		if (!latch.await(1, TimeUnit.SECONDS)) {
			fail("Timeout reached");
		}
	}
}
