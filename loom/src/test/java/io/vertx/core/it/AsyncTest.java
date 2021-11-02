package io.vertx.core.it;

import org.junit.Test;

import io.vertx.core.Vertx;
import io.vertx.lang.loom.LoomAsync;
import io.vertx.test.core.AsyncTestBase;

public class AsyncTest extends AsyncTestBase {

	@Test
	public void testExecuteErrorHandling() {
		Vertx vertx = Vertx.vertx();
		vertx.runOnContext(e -> {
			LoomAsync.async(() -> new RuntimeException("Bäm"));
		});
	}

	@Test
	public void testExecute() {
		Vertx vertx = Vertx.vertx();
		vertx.runOnContext(e -> {
			LoomAsync.async(() -> {
				assertEquals("vert.x-virtual-thread-0", Thread.currentThread().getName());
				testComplete();
			});
		});
		await();
	}

	@Test(expected = NullPointerException.class)
	public void testExecuteOutside() {
		LoomAsync.async(() -> new RuntimeException("Bäm"));
	}

	@Test
	public void testNesting() throws InterruptedException {
		Vertx vertx = Vertx.vertx();
		vertx.runOnContext(e -> {
			LoomAsync.async(() -> {
				LoomAsync.async(() -> {
					LoomAsync.async(() -> {
						LoomAsync.async(() -> {
							assertEquals("vert.x-virtual-thread-3", Thread.currentThread().getName());
							testComplete();
						});
					});
				});
			});
		});
		await();
	}
}
