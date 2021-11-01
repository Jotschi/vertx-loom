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

import io.vertx.core.Handler;
import io.vertx.core.Promise;

/**
 * The execution context of a {@link io.vertx.core.Handler} execution.
 * <p>
 * When Vert.x provides an event to a handler or calls the start or stop methods of a {@link io.vertx.core.Verticle},
 * the execution is associated with a <code>Context</code>.
 * <p>
 * Usually a context is an *event-loop context* and is tied to a specific event loop thread. So executions for that
 * context always occur on that exact same event loop thread.
 * <p>
 * In the case of worker verticles and running inline blocking code a worker context will be associated with the execution
 * which will use a thread from the worker thread pool.
 * <p>
 * When a handler is set by a thread associated with a specific context, the Vert.x will guarantee that when that handler
 * is executed, that execution will be associated with the same context.
 * <p>
 * If a handler is set by a thread not associated with a context (i.e. a non Vert.x thread). Then a new context will
 * be created for that handler.
 * <p>
 * In other words, a context is propagated.
 * <p>
 * This means that when a verticle is deployed, any handlers it sets will be associated with the same context - the context
 * of the verticle.
 * <p>
 * This means (in the case of a standard verticle) that the verticle code will always be executed with the exact same
 * thread, so you don't have to worry about multi-threaded acccess to the verticle state and you can code your application
 * as single threaded.
 * <p>
 * This class also allows arbitrary data to be {@link io.vertx.loom.core.Context#put} and {@link io.vertx.loom.core.Context#get} on the context so it can be shared easily
 * amongst different handlers of, for example, a verticle instance.
 * <p>
 * This class also provides {@link io.vertx.loom.core.Context#runOnContext} which allows an action to be executed asynchronously using the same context.
 *
 * <p/>
 */

public class Context {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Context that = (Context) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  private final io.vertx.core.Context delegate;
  
  public Context(io.vertx.core.Context delegate) {
    this.delegate = delegate;
  }

  public Context(Object delegate) {
    this.delegate = (io.vertx.core.Context)delegate;
  }

  public io.vertx.core.Context getDelegate() {
    return delegate;
  }


  /**
   * Is the current thread a worker thread?
   * <p>
   * NOTE! This is not always the same as calling {@link io.vertx.loom.core.Context#isWorkerContext}. If you are running blocking code
   * from an event loop context, then this will return true but {@link io.vertx.loom.core.Context#isWorkerContext} will return false.
   * @return true if current thread is a worker thread, false otherwise
   */
  public static boolean isOnWorkerThread() { 
    boolean ret = io.vertx.core.Context.isOnWorkerThread();
    return ret;
  }

  /**
   * Is the current thread an event thread?
   * <p>
   * NOTE! This is not always the same as calling {@link io.vertx.loom.core.Context#isEventLoopContext}. If you are running blocking code
   * from an event loop context, then this will return false but {@link io.vertx.loom.core.Context#isEventLoopContext} will return true.
   * @return true if current thread is an event thread, false otherwise
   */
  public static boolean isOnEventLoopThread() { 
    boolean ret = io.vertx.core.Context.isOnEventLoopThread();
    return ret;
  }

  /**
   * Is the current thread a Vert.x thread? That's either a worker thread or an event loop thread
   * @return true if current thread is a Vert.x thread, false otherwise
   */
  public static boolean isOnVertxThread() { 
    boolean ret = io.vertx.core.Context.isOnVertxThread();
    return ret;
  }

  /**
   * Run the specified action asynchronously on the same context, some time after the current execution has completed.
   * @param action the action to run
   */
  public void runOnContext(io.vertx.core.Handler<java.lang.Void> action) { 
    delegate.runOnContext(action);
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
    delegate.executeBlocking(new Handler<io.vertx.core.Promise<T>>() {
      public void handle(io.vertx.core.Promise<T> event) {
        blockingCodeHandler.handle(event);
      }
    }, ordered, resultHandler);
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
    executeBlocking(blockingCodeHandler, ordered, ar -> { });
  }

  /**
   * Invoke {@link io.vertx.loom.core.Context#executeBlocking} with order = true.
   * @param blockingCodeHandler handler representing the blocking code to run
   * @param resultHandler handler that will be called when the blocking code is complete
   */
  public <T> void executeBlocking(io.vertx.core.Handler<Promise<T>> blockingCodeHandler, io.vertx.core.Handler<io.vertx.core.AsyncResult<T>> resultHandler) { 
    delegate.executeBlocking(new Handler<io.vertx.core.Promise<T>>() {
      public void handle(io.vertx.core.Promise<T> event) {
        blockingCodeHandler.handle(event);
      }
    }, resultHandler);
  }

  /**
   * Invoke {@link io.vertx.loom.core.Context#executeBlocking} with order = true.
   * @param blockingCodeHandler handler representing the blocking code to run
   */
  public <T> void executeBlocking(io.vertx.core.Handler<Promise<T>> blockingCodeHandler) {
    executeBlocking(blockingCodeHandler, ar -> { });
  }

  /**
   * If the context is associated with a Verticle deployment, this returns the deployment ID of that deployment.
   * @return the deployment ID of the deployment or null if not a Verticle deployment
   */
  public java.lang.String deploymentID() { 
    java.lang.String ret = delegate.deploymentID();
    return ret;
  }

  /**
   * If the context is associated with a Verticle deployment, this returns the configuration that was specified when
   * the verticle was deployed.
   * @return the configuration of the deployment or null if not a Verticle deployment
   */
  public io.vertx.core.json.JsonObject config() { 
    io.vertx.core.json.JsonObject ret = delegate.config();
    return ret;
  }

  /**
   * The process args
   * @return 
   */
  public java.util.List<java.lang.String> processArgs() { 
    java.util.List<java.lang.String> ret = delegate.processArgs();
    return ret;
  }

  /**
   * Is the current context an event loop context?
   * <p>
   * NOTE! when running blocking code using {@link io.vertx.loom.core.Vertx#executeBlocking} from a
   * standard (not worker) verticle, the context will still an event loop context and this 
   * will return true.
   * @return true if false otherwise
   */
  public boolean isEventLoopContext() { 
    boolean ret = delegate.isEventLoopContext();
    return ret;
  }

  /**
   * Is the current context a worker context?
   * <p>
   * NOTE! when running blocking code using {@link io.vertx.loom.core.Vertx#executeBlocking} from a
   * standard (not worker) verticle, the context will still an event loop context and this 
   * will return false.
   * @return true if the current context is a worker context, false otherwise
   */
  public boolean isWorkerContext() { 
    boolean ret = delegate.isWorkerContext();
    return ret;
  }

  /**
   * Get some data from the context.
   * @param key the key of the data
   * @return the data
   */
  public <T> T get(java.lang.Object key) { 
    T ret = (T) delegate.get(key);
    return ret;
  }

  /**
   * Put some data in the context.
   * <p>
   * This can be used to share data between different handlers that share a context
   * @param key the key of the data
   * @param value the data
   */
  public void put(java.lang.Object key, java.lang.Object value) { 
    delegate.put(key, value);
  }

  /**
   * Remove some data from the context.
   * @param key the key to remove
   * @return true if removed successfully, false otherwise
   */
  public boolean remove(java.lang.Object key) { 
    boolean ret = delegate.remove(key);
    return ret;
  }

  /**
   * Get some local data from the context.
   * @param key the key of the data
   * @return the data
   */
  public <T> T getLocal(java.lang.Object key) { 
    T ret = (T) delegate.getLocal(key);
    return ret;
  }

  /**
   * Put some local data in the context.
   * <p>
   * This can be used to share data between different handlers that share a context
   * @param key the key of the data
   * @param value the data
   */
  public void putLocal(java.lang.Object key, java.lang.Object value) { 
    delegate.putLocal(key, value);
  }

  /**
   * Remove some local data from the context.
   * @param key the key to remove
   * @return true if removed successfully, false otherwise
   */
  public boolean removeLocal(java.lang.Object key) { 
    boolean ret = delegate.removeLocal(key);
    return ret;
  }

  /**
   * @return The Vertx instance that created the context
   */
  public io.vertx.loom.core.Vertx owner() { 
    io.vertx.loom.core.Vertx ret = io.vertx.loom.core.Vertx.newInstance((io.vertx.core.Vertx)delegate.owner());
    return ret;
  }

  /**
   * @return the number of instances of the verticle that were deployed in the deployment (if any) related to this context
   */
  public int getInstanceCount() { 
    int ret = delegate.getInstanceCount();
    return ret;
  }

  /**
   * Set an exception handler called when the context runs an action throwing an uncaught throwable.<p/>
   *
   * When this handler is called, {@link io.vertx.loom.core.Vertx#currentContext} will return this context.
   * @param handler the exception handler
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.Context exceptionHandler(io.vertx.core.Handler<java.lang.Throwable> handler) { 
    delegate.exceptionHandler(handler);
    return this;
  }

  public static Context newInstance(io.vertx.core.Context arg) {
    return arg != null ? new Context(arg) : null;
  }

}
