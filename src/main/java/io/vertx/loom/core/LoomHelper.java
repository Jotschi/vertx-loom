package io.vertx.loom.core;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public final class LoomHelper {

	private static final ThreadLocal<AsyncContext> ASYNC_CONTEXT = new ThreadLocal<>();

	private record AsyncContext(Context vertxContext, ThreadFactory vThreadFactory) {
	}

	private LoomHelper() {

	}

	public static void execute(Runnable runnable) {
		AsyncContext asyncContext = ASYNC_CONTEXT.get();

		if (asyncContext == null) {
			Context vertxContext = Objects.requireNonNull(Vertx.currentContext(),
					"This thread needs a Vertx Context to use async/await");

			Executor contextThreadExecutor = command -> {
				vertxContext.runOnContext(v -> command.run());
			};

			ThreadFactory vtFactory = Thread.ofVirtual().name("vert.x-virtual-thread-", 0)
					.scheduler(contextThreadExecutor).factory();
			asyncContext = new AsyncContext(vertxContext, vtFactory);
			ASYNC_CONTEXT.set(asyncContext);
		}

		AsyncContext finalAsyncCtx = asyncContext;

		finalAsyncCtx.vThreadFactory.newThread(() -> {
			try {
				ASYNC_CONTEXT.set(finalAsyncCtx);
				runnable.run();
			} catch (Throwable t) {
				throw t;
			}
		}).start();
	}

	public static Context currentVertxContext() {
		AsyncContext asyncContext = ASYNC_CONTEXT.get();
		Context context = asyncContext.vertxContext;
		return context;
	}
}
