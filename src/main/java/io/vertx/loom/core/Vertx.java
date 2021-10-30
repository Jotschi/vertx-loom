/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.loom.core;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.TimeoutStream;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.dns.DnsClient;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.core.metrics.Measured;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetServer;
import io.vertx.core.shareddata.SharedData;

/**
 * The entry point into the Vert.x Core API.
 * <p>
 * You use an instance of this class for functionality including:
 * <ul>
 *   <li>Creating TCP clients and servers</li>
 *   <li>Creating HTTP clients and servers</li>
 *   <li>Creating DNS clients</li>
 *   <li>Creating Datagram sockets</li>
 *   <li>Setting and cancelling periodic and one-shot timers</li>
 *   <li>Getting a reference to the event bus API</li>
 *   <li>Getting a reference to the file system API</li>
 *   <li>Getting a reference to the shared data API</li>
 *   <li>Deploying and undeploying verticles</li>
 * </ul>
 * <p>
 * Most functionality in Vert.x core is fairly low level.
 * <p>
 * To create an instance of this class you can use the static factory methods: {@link io.vertx.loom.core.Vertx#vertx},
 * {@link io.vertx.loom.core.Vertx#vertx} and {@link io.vertx.loom.core.Vertx#clusteredVertx}.
 * <p>
 * Please see the user manual for more detailed usage information.
 *
 * <p/>
 */

public class Vertx implements Measured {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vertx that = (Vertx) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  private final io.vertx.core.Vertx delegate;
  
  public Vertx(io.vertx.core.Vertx delegate) {
    this.delegate = delegate;
  }

  public Vertx(Object delegate) {
    this.delegate = (io.vertx.core.Vertx)delegate;
  }

  public io.vertx.core.Vertx getDelegate() {
    return delegate;
  }


  /**
   * Whether the metrics are enabled for this measured object
   * @return <code>true</code> if metrics are enabled
   */
  public boolean isMetricsEnabled() { 
    boolean ret = delegate.isMetricsEnabled();
    return ret;
  }

  /**
   * Creates a non clustered instance using default options.
   * @return the instance
   */
  public static io.vertx.loom.core.Vertx vertx() { 
    io.vertx.loom.core.Vertx ret = io.vertx.loom.core.Vertx.newInstance((io.vertx.core.Vertx)io.vertx.core.Vertx.vertx());
    return ret;
  }

  /**
   * Creates a non clustered instance using the specified options
   * @param options the options to use
   * @return the instance
   */
  public static io.vertx.loom.core.Vertx vertx(io.vertx.core.VertxOptions options) { 
    io.vertx.loom.core.Vertx ret = io.vertx.loom.core.Vertx.newInstance((io.vertx.core.Vertx)io.vertx.core.Vertx.vertx(options));
    return ret;
  }

  /**
   * Creates a clustered instance using the specified options.
   * <p>
   * The instance is created asynchronously and the resultHandler is called with the result when it is ready.
   * @param options the options to use
   * @param resultHandler the result handler that will receive the result
   */
  public static void clusteredVertx(io.vertx.core.VertxOptions options, io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.loom.core.Vertx>> resultHandler) { 
    io.vertx.core.Vertx.clusteredVertx(options, new Handler<AsyncResult<io.vertx.core.Vertx>>() {
      public void handle(AsyncResult<io.vertx.core.Vertx> ar) {
        if (ar.succeeded()) {
          resultHandler.handle(io.vertx.core.Future.succeededFuture(io.vertx.loom.core.Vertx.newInstance((io.vertx.core.Vertx)ar.result())));
        } else {
          resultHandler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
  }

  /**
   * Creates a clustered instance using the specified options.
   * <p>
   * The instance is created asynchronously and the resultHandler is called with the result when it is ready.
   * @param options the options to use
   */
  public static void clusteredVertx(io.vertx.core.VertxOptions options) {
    clusteredVertx(options, ar -> { });
  }

  /**
   * Gets the current context
   * @return The current context or <code>null</code> if there is no current context
   */
  public static io.vertx.loom.core.Context currentContext() { 
    io.vertx.loom.core.Context ret = io.vertx.loom.core.Context.newInstance((io.vertx.core.Context)io.vertx.core.Vertx.currentContext());
    return ret;
  }

  /**
   * Gets the current context, or creates one if there isn't one
   * @return The current context (created if didn't exist)
   */
  public io.vertx.loom.core.Context getOrCreateContext() { 
    io.vertx.loom.core.Context ret = io.vertx.loom.core.Context.newInstance((io.vertx.core.Context)delegate.getOrCreateContext());
    return ret;
  }

  /**
   * Create a TCP/SSL server using the specified options
   * @param options the options to use
   * @return the server
   */
  public NetServer createNetServer(io.vertx.core.net.NetServerOptions options) { 
    return delegate.createNetServer(options);
  }

  /**
   * Create a TCP/SSL server using default options
   * @return the server
   */
  public NetServer createNetServer() { 
    return delegate.createNetServer();
  }

  /**
   * Create a TCP/SSL client using the specified options
   * @param options the options to use
   * @return the client
   */
  public NetClient createNetClient(io.vertx.core.net.NetClientOptions options) { 
    return delegate.createNetClient(options);
  }

  /**
   * Create a TCP/SSL client using default options
   * @return the client
   */
  public NetClient createNetClient() { 
    return delegate.createNetClient();
  }

  /**
   * Create an HTTP/HTTPS server using the specified options
   * @param options the options to use
   * @return the server
   */
  public HttpServer createHttpServer(io.vertx.core.http.HttpServerOptions options) { 
    return delegate.createHttpServer(options);
  }

  /**
   * Create an HTTP/HTTPS server using default options
   * @return the server
   */
  public HttpServer createHttpServer() { 
    return delegate.createHttpServer();
  }

  /**
   * Create a HTTP/HTTPS client using the specified options
   * @param options the options to use
   * @return the client
   */
  public HttpClient createHttpClient(io.vertx.core.http.HttpClientOptions options) { 
    return delegate.createHttpClient(options);
  }

  /**
   * Create a HTTP/HTTPS client using default options
   * @return the client
   */
  public HttpClient createHttpClient() { 
    return delegate.createHttpClient();
  }

  /**
   * Create a datagram socket using the specified options
   * @param options the options to use
   * @return the socket
   */
  public DatagramSocket createDatagramSocket(io.vertx.core.datagram.DatagramSocketOptions options) { 
    return delegate.createDatagramSocket(options);
  }

  /**
   * Create a datagram socket using default options
   * @return the socket
   */
  public DatagramSocket createDatagramSocket() { 
    return delegate.createDatagramSocket();
  }

  /**
   * Get the filesystem object. There is a single instance of FileSystem per Vertx instance.
   * @return the filesystem object
   */
  public io.vertx.loom.core.file.FileSystem fileSystem() { 
    if (cached_0 != null) {
      return cached_0;
    }
    io.vertx.loom.core.file.FileSystem ret = io.vertx.loom.core.file.FileSystem.newInstance((io.vertx.core.file.FileSystem)delegate.fileSystem());
    cached_0 = ret;
    return ret;
  }

  /**
   * Get the event bus object. There is a single instance of EventBus per Vertx instance.
   * @return the event bus object
   */
  public io.vertx.loom.core.eventbus.EventBus eventBus() { 
    if (cached_1 != null) {
      return cached_1;
    }
    io.vertx.loom.core.eventbus.EventBus ret = io.vertx.loom.core.eventbus.EventBus.newInstance((io.vertx.core.eventbus.EventBus)delegate.eventBus());
    cached_1 = ret;
    return ret;
  }

  /**
   * Create a DNS client to connect to a DNS server at the specified host and port, with the default query timeout (5 seconds)
   * <p/>
   * @param port the port
   * @param host the host
   * @return the DNS client
   */
  public DnsClient createDnsClient(int port, java.lang.String host) { 
    return delegate.createDnsClient(port, host);
  }

  /**
   * Create a DNS client to connect to the DNS server configured by {@link io.vertx.core.VertxOptions}
   * <p>
   * DNS client takes the first configured resolver address provided by }
   * @return the DNS client
   */
  public DnsClient createDnsClient() {
    return delegate.createDnsClient();
  }

  /**
   * Create a DNS client to connect to a DNS server
   * @param options the client options
   * @return the DNS client
   */
  public DnsClient createDnsClient(io.vertx.core.dns.DnsClientOptions options) { 
    return delegate.createDnsClient(options);
  }

  /**
   * Get the shared data object. There is a single instance of SharedData per Vertx instance.
   * @return the shared data object
   */
  public SharedData sharedData() { 
    return delegate.sharedData();
  }

  /**
   * Set a one-shot timer to fire after <code>delay</code> milliseconds, at which point <code>handler</code> will be called with
   * the id of the timer.
   * @param delay the delay in milliseconds, after which the timer will fire
   * @param handler the handler that will be called with the timer ID when the timer fires
   * @return the unique ID of the timer
   */
  public long setTimer(long delay, io.vertx.core.Handler<java.lang.Long> handler) { 
    long ret = delegate.setTimer(delay, handler);
    return ret;
  }

  /**
   * Returns a one-shot timer as a read stream. The timer will be fired after <code>delay</code> milliseconds after
   * the  has been called.
   * @param delay the delay in milliseconds, after which the timer will fire
   * @return the timer stream
   */
  public TimeoutStream timerStream(long delay) { 
    return delegate.timerStream(delay);
  }

  /**
   * Set a periodic timer to fire every <code>delay</code> milliseconds, at which point <code>handler</code> will be called with
   * the id of the timer.
   * @param delay the delay in milliseconds, after which the timer will fire
   * @param handler the handler that will be called with the timer ID when the timer fires
   * @return the unique ID of the timer
   */
  public long setPeriodic(long delay, io.vertx.core.Handler<java.lang.Long> handler) { 
    long ret = delegate.setPeriodic(delay, handler);
    return ret;
  }

  /**
   * Returns a periodic timer as a read stream. The timer will be fired every <code>delay</code> milliseconds after
   * the  has been called.
   * @param delay the delay in milliseconds, after which the timer will fire
   * @return the periodic stream
   */
  public TimeoutStream periodicStream(long delay) { 
    return delegate.periodicStream(delay);
  }

  /**
   * Cancels the timer with the specified <code>id</code>.
   * @param id The id of the timer to cancel
   * @return true if the timer was successfully cancelled, or false if the timer does not exist.
   */
  public boolean cancelTimer(long id) { 
    boolean ret = delegate.cancelTimer(id);
    return ret;
  }

  /**
   * Puts the handler on the event queue for the current context so it will be run asynchronously ASAP after all
   * preceeding events have been handled.
   * @param action - a handler representing the action to execute
   */
  public void runOnContext(io.vertx.core.Handler<java.lang.Void> action) { 
    delegate.runOnContext(action);
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#close} but the completionHandler will be called when the close is complete
   * @param completionHandler The handler will be notified when the close is complete.
   */
  public void close(io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> completionHandler) { 
    delegate.close(completionHandler);
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#close} but the completionHandler will be called when the close is complete
   */
  public void close() {
    close(ar -> { });
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#deployVerticle} but the completionHandler will be notified when the deployment is complete.
   * <p>
   * If the deployment is successful the result will contain a String representing the unique deployment ID of the
   * deployment.
   * <p>
   * This deployment ID can subsequently be used to undeploy the verticle.
   * @param name The identifier
   * @param completionHandler a handler which will be notified when the deployment is complete
   */
  public void deployVerticle(java.lang.String name, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> completionHandler) { 
    delegate.deployVerticle(name, completionHandler);
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#deployVerticle} but the completionHandler will be notified when the deployment is complete.
   * <p>
   * If the deployment is successful the result will contain a String representing the unique deployment ID of the
   * deployment.
   * <p>
   * This deployment ID can subsequently be used to undeploy the verticle.
   * @param name The identifier
   */
  public void deployVerticle(java.lang.String name) {
    deployVerticle(name, ar -> { });
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#deployVerticle} but {@link io.vertx.core.DeploymentOptions} are provided to configure the
   * deployment.
   * @param name the name
   * @param options the deployment options.
   * @param completionHandler a handler which will be notified when the deployment is complete
   */
  public void deployVerticle(java.lang.String name, io.vertx.core.DeploymentOptions options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> completionHandler) { 
    delegate.deployVerticle(name, options, completionHandler);
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#deployVerticle} but {@link io.vertx.core.DeploymentOptions} are provided to configure the
   * deployment.
   * @param name the name
   * @param options the deployment options.
   */
  public void deployVerticle(java.lang.String name, io.vertx.core.DeploymentOptions options) {
    deployVerticle(name, options, ar -> { });
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx #undeploy(String)} but the completionHandler will be notified when the undeployment is complete.
   * @param deploymentID the deployment ID
   * @param completionHandler a handler which will be notified when the undeployment is complete
   */
  public void undeploy(java.lang.String deploymentID, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> completionHandler) { 
    delegate.undeploy(deploymentID, completionHandler);
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx #undeploy(String)} but the completionHandler will be notified when the undeployment is complete.
   * @param deploymentID the deployment ID
   */
  public void undeploy(java.lang.String deploymentID) {
    undeploy(deploymentID, ar -> { });
  }

  /**
   * Return a Set of deployment IDs for the currently deployed deploymentIDs.
   * @return Set of deployment IDs
   */
  public java.util.Set<java.lang.String> deploymentIDs() { 
    java.util.Set<java.lang.String> ret = delegate.deploymentIDs();
    return ret;
  }

  /**
   * Is this Vert.x instance clustered?
   * @return true if clustered
   */
  public boolean isClustered() { 
    boolean ret = delegate.isClustered();
    return ret;
  }

  /**
   * Safely execute some blocking code.
   * <p>
   * Executes the blocking code in the handler <code>blockingCodeHandler</code> using a thread from the worker pool.
   * <p>
   * When the code is complete the handler <code>resultHandler</code> will be called with the result on the original context
   * (e.g. on the original event loop of the caller).
   * <p>
   * A <code>Future</code> instance is passed into <code>blockingCodeHandler</code>. When the blocking code successfully completes,
   * the handler should call the {@link Promise#complete} or {@link Promise#complete} method, or the {@link Promise#fail}
   * method if it failed.
   * <p>
   * In the <code>blockingCodeHandler</code> the current context remains the original context and therefore any task
   * scheduled in the <code>blockingCodeHandler</code> will be executed on the this context and not on the worker thread.
   * <p>
   * The blocking code should block for a reasonable amount of time (i.e no more than a few seconds). Long blocking operations
   * or polling operations (i.e a thread that spin in a loop polling events in a blocking fashion) are precluded.
   * <p>
   * When the blocking operation lasts more than the 10 seconds, a message will be printed on the console by the
   * blocked thread checker.
   * <p>
   * Long blocking operations should use a dedicated thread managed by the application, which can interact with
   * verticles using the event-bus or {@link io.vertx.loom.core.Context#runOnContext}
   * @param blockingCodeHandler handler representing the blocking code to run
   * @param ordered if true then if executeBlocking is called several times on the same context, the executions for that context will be executed serially, not in parallel. if false then they will be no ordering guarantees
   * @param resultHandler handler that will be called when the blocking code is complete
   */
  public <T> void executeBlocking(io.vertx.core.Handler<Promise<T>> blockingCodeHandler, boolean ordered, io.vertx.core.Handler<io.vertx.core.AsyncResult<T>> resultHandler) { 
    delegate.executeBlocking(blockingCodeHandler, ordered, resultHandler);
  }

  /**
   * Safely execute some blocking code.
   * <p>
   * Executes the blocking code in the handler <code>blockingCodeHandler</code> using a thread from the worker pool.
   * <p>
   * When the code is complete the handler <code>resultHandler</code> will be called with the result on the original context
   * (e.g. on the original event loop of the caller).
   * <p>
   * A <code>Future</code> instance is passed into <code>blockingCodeHandler</code>. When the blocking code successfully completes,
   * the handler should call the {@link Promise#complete} or {@link Promise#complete} method, or the {@link Promise#fail}
   * method if it failed.
   * <p>
   * In the <code>blockingCodeHandler</code> the current context remains the original context and therefore any task
   * scheduled in the <code>blockingCodeHandler</code> will be executed on the this context and not on the worker thread.
   * <p>
   * The blocking code should block for a reasonable amount of time (i.e no more than a few seconds). Long blocking operations
   * or polling operations (i.e a thread that spin in a loop polling events in a blocking fashion) are precluded.
   * <p>
   * When the blocking operation lasts more than the 10 seconds, a message will be printed on the console by the
   * blocked thread checker.
   * <p>
   * Long blocking operations should use a dedicated thread managed by the application, which can interact with
   * verticles using the event-bus or {@link io.vertx.loom.core.Context#runOnContext}
   * @param blockingCodeHandler handler representing the blocking code to run
   * @param ordered if true then if executeBlocking is called several times on the same context, the executions for that context will be executed serially, not in parallel. if false then they will be no ordering guarantees
   */
  public <T> void executeBlocking(io.vertx.core.Handler<Promise<T>> blockingCodeHandler, boolean ordered) {
    delegate.executeBlocking(blockingCodeHandler, ordered);
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#executeBlocking} called with ordered = true.
   * @param blockingCodeHandler 
   * @param resultHandler 
   */
  public <T> void executeBlocking(io.vertx.core.Handler<Promise<T>> blockingCodeHandler, io.vertx.core.Handler<io.vertx.core.AsyncResult<T>> resultHandler) { 
    delegate.executeBlocking(blockingCodeHandler, resultHandler);
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#executeBlocking} called with ordered = true.
   * @param blockingCodeHandler 
   */
  public <T> void executeBlocking(io.vertx.core.Handler<Promise<T>> blockingCodeHandler) {
    executeBlocking(blockingCodeHandler, ar -> { });
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#createSharedWorkerExecutor} but with the {@link io.vertx.core.VertxOptions} <code>poolSize</code>.
   * @param name 
   * @return 
   */
  public WorkerExecutor createSharedWorkerExecutor(java.lang.String name) { 
    return delegate.createSharedWorkerExecutor(name);
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#createSharedWorkerExecutor} but with the {@link io.vertx.core.VertxOptions} <code>maxExecuteTime</code>.
   * @param name 
   * @param poolSize 
   * @return 
   */
  public WorkerExecutor createSharedWorkerExecutor(java.lang.String name, int poolSize) { 
    return delegate.createSharedWorkerExecutor(name, poolSize);
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#createSharedWorkerExecutor} but with the .
   * @param name 
   * @param poolSize 
   * @param maxExecuteTime 
   * @return 
   */
  public WorkerExecutor createSharedWorkerExecutor(java.lang.String name, int poolSize, long maxExecuteTime) { 
    return delegate.createSharedWorkerExecutor(name, poolSize, maxExecuteTime);
  }

  /**
   * Create a named worker executor, the executor should be closed when it's not needed anymore to release
   * resources.<p/>
   *
   * This method can be called mutiple times with the same <code>name</code>. Executors with the same name will share
   * the same worker pool. The worker pool size , max execute time and unit of max execute time are set when the worker pool is created and
   * won't change after.<p>
   *
   * The worker pool is released when all the {@link io.vertx.loom.core.WorkerExecutor} sharing the same name are closed.
   * @param name the name of the worker executor
   * @param poolSize the size of the pool
   * @param maxExecuteTime the value of max worker execute time
   * @param maxExecuteTimeUnit the value of unit of max worker execute time
   * @return the named worker executor
   */
  public WorkerExecutor createSharedWorkerExecutor(java.lang.String name, int poolSize, long maxExecuteTime, java.util.concurrent.TimeUnit maxExecuteTimeUnit) { 
    return delegate.createSharedWorkerExecutor(name, poolSize, maxExecuteTime, maxExecuteTimeUnit);
  }

  /**
   * @return whether the native transport is used
   */
  public boolean isNativeTransportEnabled() { 
    if (cached_3 != null) {
      return cached_3;
    }
    boolean ret = delegate.isNativeTransportEnabled();
    cached_3 = ret;
    return ret;
  }

  /**
   * Set a default exception handler for {@link io.vertx.loom.core.Context}, set on  at creation.
   * @param handler the exception handler
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.Vertx exceptionHandler(io.vertx.core.Handler<java.lang.Throwable> handler) { 
    delegate.exceptionHandler(handler);
    return this;
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#deployVerticle} but the completionHandler will be notified when the deployment is complete.
   * <p>
   * If the deployment is successful the result will contain a string representing the unique deployment ID of the
   * deployment.
   * <p>
   * This deployment ID can subsequently be used to undeploy the verticle.
   * @param verticle the verticle instance to deploy
   * @param completionHandler a handler which will be notified when the deployment is complete
   */
  public void deployVerticle(io.vertx.core.Verticle verticle, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> completionHandler) { 
    delegate.deployVerticle(verticle, completionHandler);
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#deployVerticle} but the completionHandler will be notified when the deployment is complete.
   * <p>
   * If the deployment is successful the result will contain a string representing the unique deployment ID of the
   * deployment.
   * <p>
   * This deployment ID can subsequently be used to undeploy the verticle.
   * @param verticle the verticle instance to deploy
   */
  public void deployVerticle(io.vertx.core.Verticle verticle) {
    deployVerticle(verticle, ar -> { });
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#deployVerticle} but {@link io.vertx.core.DeploymentOptions} are provided to configure the
   * deployment.
   * @param verticle the verticle instance to deploy
   * @param options the deployment options.
   * @param completionHandler a handler which will be notified when the deployment is complete
   */
  public void deployVerticle(io.vertx.core.Verticle verticle, io.vertx.core.DeploymentOptions options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> completionHandler) { 
    delegate.deployVerticle(verticle, options, completionHandler);
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#deployVerticle} but {@link io.vertx.core.DeploymentOptions} are provided to configure the
   * deployment.
   * @param verticle the verticle instance to deploy
   * @param options the deployment options.
   */
  public void deployVerticle(io.vertx.core.Verticle verticle, io.vertx.core.DeploymentOptions options) {
    deployVerticle(verticle, options, ar -> { });
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#deployVerticle} but {@link io.vertx.core.Verticle} instance is created by
   * invoking the <code>verticleSupplier</code>.
   * <p>
   * The supplier will be invoked as many times as {@link io.vertx.core.DeploymentOptions}.
   * It must not return the same instance twice.
   * <p>
   * Note that the supplier will be invoked on the caller thread.
   * @param verticleSupplier 
   * @param options 
   * @param completionHandler 
   */
  public void deployVerticle(java.util.function.Supplier<io.vertx.core.Verticle> verticleSupplier, io.vertx.core.DeploymentOptions options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> completionHandler) { 
    delegate.deployVerticle(verticleSupplier, options, completionHandler);
  }

  /**
   * Like {@link io.vertx.loom.core.Vertx#deployVerticle} but {@link io.vertx.core.Verticle} instance is created by
   * invoking the <code>verticleSupplier</code>.
   * <p>
   * The supplier will be invoked as many times as {@link io.vertx.core.DeploymentOptions}.
   * It must not return the same instance twice.
   * <p>
   * Note that the supplier will be invoked on the caller thread.
   * @param verticleSupplier 
   * @param options 
   */
  public void deployVerticle(java.util.function.Supplier<io.vertx.core.Verticle> verticleSupplier, io.vertx.core.DeploymentOptions options) {
    deployVerticle(verticleSupplier, options, ar -> { });
  }

  /**
   * Register a <code>VerticleFactory</code> that can be used for deploying Verticles based on an identifier.
   * @param factory the factory to register
   */
  public void registerVerticleFactory(io.vertx.core.spi.VerticleFactory factory) { 
    delegate.registerVerticleFactory(factory);
  }

  /**
   * Unregister a <code>VerticleFactory</code>
   * @param factory the factory to unregister
   */
  public void unregisterVerticleFactory(io.vertx.core.spi.VerticleFactory factory) { 
    delegate.unregisterVerticleFactory(factory);
  }

  /**
   * Return the Set of currently registered verticle factories.
   * @return the set of verticle factories
   */
  public java.util.Set<io.vertx.core.spi.VerticleFactory> verticleFactories() { 
    java.util.Set<io.vertx.core.spi.VerticleFactory> ret = delegate.verticleFactories();
    return ret;
  }

  /**
   * Return the Netty EventLoopGroup used by Vert.x
   * @return the EventLoopGroup
   */
  public io.netty.channel.EventLoopGroup nettyEventLoopGroup() { 
    io.netty.channel.EventLoopGroup ret = delegate.nettyEventLoopGroup();
    return ret;
  }

  private io.vertx.loom.core.file.FileSystem cached_0;
  private io.vertx.loom.core.eventbus.EventBus cached_1;
  private java.lang.Boolean cached_3;
  public static Vertx newInstance(io.vertx.core.Vertx arg) {
    return arg != null ? new Vertx(arg) : null;
  }

}
