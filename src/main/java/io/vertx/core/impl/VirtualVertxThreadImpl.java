package io.vertx.core.impl;

import java.util.concurrent.TimeUnit;

import io.vertx.core.VertxThread;

/**
 * Wrapper for virtual threads being used in Vert.x
 */
public class VirtualVertxThreadImpl implements VertxThread {

	private Thread instance;

	private final boolean worker;
	private final long maxExecTime;
	private final TimeUnit maxExecTimeUnit;
	private long execStart;
	private ContextInternal context;

	public VirtualVertxThreadImpl(Runnable runnable, String name, boolean worker, long maxExecTime,
			TimeUnit maxExecTimeUnit) {
		this.instance = Thread.ofVirtual().name(name).factory().newThread(runnable);
		this.worker = worker;
		this.maxExecTime = maxExecTime;
		this.maxExecTimeUnit = maxExecTimeUnit;
	}

	@Override
	public long startTime() {
		return execStart;
	}

	@Override
	public long maxExecTime() {
		return maxExecTime;
	}

	@Override
	public TimeUnit maxExecTimeUnit() {
		return maxExecTimeUnit;
	}

	@Override
	public ContextInternal context() {
		return context;
	}

	@Override
	public boolean isWorker() {
		return worker;
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return instance.getStackTrace();
	}

	@Override
	public void setDaemon(boolean b) {
		// Virtual threads are always running in daemon mode.
	}

	@Override
	public Thread unwrap() {
		return instance;
	}

	@Override
	public boolean isTrackable() {
		// TODO Auto-generated method stub
		return false;
	}
}
