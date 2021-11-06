package io.vertx.loom.core;

import java.util.concurrent.TimeUnit;

import io.vertx.core.impl.VertxThread;

/**
 * Wrapper for virtual threads being used in Vert.x
 */
public class VirtualVertxThreadImpl extends VertxThread {

  private Thread instance;
  
  private static final ThreadLocal<VirtualVertxThreadImpl> THREAD_REF = new ThreadLocal<>();

  public VirtualVertxThreadImpl(Runnable runnable, String name, boolean worker, long maxExecTime,
    TimeUnit maxExecTimeUnit) {
    super(runnable, name, worker, maxExecTime, maxExecTimeUnit);
    this.instance = Thread.ofVirtual().name(name).factory().newThread(() -> {
      VirtualVertxThreadImpl.setVertexThread(this);
      runnable.run(); 
    });
  }


  @Override
  public StackTraceElement[] getStackTrace() {
    return instance.getStackTrace();
  }

  @Override
  public void start() {
    this.instance.start();
  }

  @Override
  public void interrupt() {
    this.instance.interrupt();
  }

  @Override
  public boolean isInterrupted() {
    return this.instance.isInterrupted();
  }

  public Thread unwrap() {
    return instance;
  }
  
  public static VertxThread getVertexThread() {
    return THREAD_REF.get();
  }

  private static void setVertexThread(VirtualVertxThreadImpl virtualVertxThread) {
    THREAD_REF.set(virtualVertxThread);
  }
}