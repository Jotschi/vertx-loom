package io.vertx.loom.core;

import java.util.concurrent.TimeUnit;

import io.vertx.core.impl.VertxThread;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.spi.VertxThreadFactory;

public class VertxVirtualThreadFactory implements VertxThreadFactory {

  private static final Logger log = LoggerFactory.getLogger(VertxVirtualThreadFactory.class);

  static {
    // Print warnings on state of virtual threads.
    log.warn("Please note that virtual thread support is experimental");
    log.warn(
        """
            Using a large poolsize with virtual threads enabled can result in stalled request processing due to virtual threads being pinned to the needed carrier thread.
            A larger poolsize can exceed the default max of 256 carrier threads. (See jdk.defaultScheduler.parallelism and jdk.defaultScheduler.maxPoolSize.
            """);
  }

  @Override
  public VertxThread newVertxThread(Runnable runnable, String name, boolean worker, long maxExecuteTime,
      TimeUnit maxExecuteTimeUnit) {
    VirtualVertxThreadImpl thread = new VirtualVertxThreadImpl(runnable, name + "-virt", worker, maxExecuteTime,
        maxExecuteTimeUnit);
    return thread;
  }

}