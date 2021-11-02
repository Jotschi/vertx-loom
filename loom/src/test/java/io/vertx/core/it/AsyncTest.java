package io.vertx.core.it;

import org.junit.Test;

import io.vertx.core.Vertx;
import io.vertx.loom.core.Async;
import io.vertx.test.core.AsyncTestBase;

public class AsyncTest extends AsyncTestBase {

	@Test
	public void testExecuteErrorHandling() {
		Vertx vertx = Vertx.vertx();
		vertx.runOnContext(e -> {
			Async.async(() -> new RuntimeException("Bäm"));
		});
	}

	@Test
	public void testExecute() {
		Vertx vertx = Vertx.vertx();
		vertx.runOnContext(e -> {
			Async.async(() -> {
				assertEquals("vert.x-virtual-thread-0", Thread.currentThread().getName());
				testComplete();
			});
		});
		await();
	}

	@Test(expected = NullPointerException.class)
	public void testExecuteOutside() {
		Async.async(() -> new RuntimeException("Bäm"));
	}

	@Test
	public void testNesting() throws InterruptedException {
		Vertx vertx = Vertx.vertx();
		vertx.runOnContext(e -> {
			Async.async(() -> {
				Async.async(() -> {
					Async.async(() -> {
						Async.async(() -> {
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
