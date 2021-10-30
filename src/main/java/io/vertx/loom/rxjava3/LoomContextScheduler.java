package io.vertx.loom.rxjava3;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.vertx.loom.core.Async;
import io.vertx.loom.rxjava3.LoomContextScheduler.LoomContextWorker.TimedAction;

public class LoomContextScheduler extends Scheduler {

	private Set<TimedAction> actions = new HashSet<>();

	private boolean disposed = false;

	@Override
	public @NonNull Worker createWorker() {
		return new LoomContextWorker();
	}

	public class LoomContextWorker extends Worker {

		@Override
		public void dispose() {
			actions.forEach(TimedAction::dispose);
			disposed = true;
		}

		@Override
		public boolean isDisposed() {
			return disposed;
		}

		@Override
		public @NonNull Disposable schedule(@NonNull Runnable action, long delay, @NonNull TimeUnit unit) {
			TimedAction timedAction = new TimedAction(action, Duration.of(delay, unit.toChronoUnit()));
			timedAction.run();
			return timedAction;
		}

		class TimedAction implements Disposable {

			private Thread thread;
			private boolean disposed = false;
			private final Runnable action;
			private final Duration delay;

			public TimedAction(Runnable action, Duration delay) {
				this.action = action;
				this.delay = delay;
			}

			public void run() {
				thread = Async.async(() -> {
					try {
						// Thread sleep is safe in a virtual thread since loom will not block the
						// carrier thread if doing so. Instead the loom scheduler will continue
						// executing another virtual thread.
						Thread.sleep(delay.toMillis());
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					if (isDisposed()) {
						return;
					}
					try {
						action.run();
					} catch (Exception e) {

					}
					// TODO Remove action from tracked actions
				});
			}

			@Override
			public void dispose() {
				thread.interrupt();
				// TODO Remove action from tracked actions
				disposed = true;
			}

			@Override
			public boolean isDisposed() {
				return disposed;
			}

		}
	}

}
