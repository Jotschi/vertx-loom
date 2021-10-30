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

package io.vertx.loom.ext.web;

import java.util.function.Function;

import io.vertx.core.Handler;
import io.vertx.loom.core.LoomHelper;

/**
 * A route is a holder for a set of criteria which determine whether an HTTP request or failure should be routed
 * to a handler.
 *
 * <p/>
 */

public class Route {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Route that = (Route) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  private final io.vertx.ext.web.Route delegate;

  public Route(io.vertx.ext.web.Route delegate) {
    this.delegate = delegate;
  }

  public Route(Object delegate) {
    this.delegate = (io.vertx.ext.web.Route)delegate;
  }

  public io.vertx.ext.web.Route getDelegate() {
    return delegate;
  }


  /**
   * Add an HTTP method for this route. By default a route will match all HTTP methods. If any are specified then the route
   * will only match any of the specified methods
   * @param method the HTTP method to add
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route method(io.vertx.core.http.HttpMethod method) { 
    delegate.method(method);
    return this;
  }

  /**
   * Set the path prefix for this route. If set then this route will only match request URI paths which start with this
   * path prefix. Only a single path or path regex can be set for a route.
   * @param path the path prefix
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route path(java.lang.String path) { 
    delegate.path(path);
    return this;
  }

  /**
   * Set the path prefix as a regular expression. If set then this route will only match request URI paths, the beginning
   * of which match the regex. Only a single path or path regex can be set for a route.
   * @param path the path regex
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route pathRegex(java.lang.String path) { 
    delegate.pathRegex(path);
    return this;
  }

  /**
   * Add a content type produced by this route. Used for content based routing.
   * @param contentType the content type
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route produces(java.lang.String contentType) { 
    delegate.produces(contentType);
    return this;
  }

  /**
   * Add a content type consumed by this route. Used for content based routing.
   * @param contentType the content type
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route consumes(java.lang.String contentType) { 
    delegate.consumes(contentType);
    return this;
  }

  /**
   * Add a virtual host filter for this route.
   * @param hostnamePattern the hostname pattern that should match <code>Host</code> header of the requests
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route virtualHost(java.lang.String hostnamePattern) { 
    delegate.virtualHost(hostnamePattern);
    return this;
  }

  /**
   * Specify the order for this route. The router tests routes in that order.
   * @param order the order
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route order(int order) { 
    delegate.order(order);
    return this;
  }

  /**
   * Specify this is the last route for the router.
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route last() { 
    delegate.last();
    return this;
  }

  /**
   * Append a request handler to the route handlers list. The router routes requests to handlers depending on whether the various
   * criteria such as method, path, etc match. When method, path, etc are the same for different routes, You should add multiple
   * handlers to the same route object rather than creating two different routes objects with one handler for route
   * @param requestHandler the request handler
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route handler(io.vertx.core.Handler<io.vertx.loom.ext.web.RoutingContext> requestHandler) { 
    delegate.handler(new Handler<io.vertx.ext.web.RoutingContext>() {
      public void handle(io.vertx.ext.web.RoutingContext event) {
        LoomHelper.execute(() -> {
          requestHandler.handle(io.vertx.loom.ext.web.RoutingContext.newInstance((io.vertx.ext.web.RoutingContext)event));
        });
      }
    });
    return this;
  }

  /**
   * Like {@link io.vertx.loom.ext.web.Route#blockingHandler} called with ordered = true
   * @param requestHandler 
   * @return 
   */
  public io.vertx.loom.ext.web.Route blockingHandler(io.vertx.core.Handler<io.vertx.loom.ext.web.RoutingContext> requestHandler) { 
    delegate.blockingHandler(new Handler<io.vertx.ext.web.RoutingContext>() {
      public void handle(io.vertx.ext.web.RoutingContext event) {
        requestHandler.handle(io.vertx.loom.ext.web.RoutingContext.newInstance((io.vertx.ext.web.RoutingContext)event));
      }
    });
    return this;
  }

  /**
   * Use a (sub) {@link io.vertx.loom.ext.web.Router} as a handler. There are several requirements to be fulfilled for this
   * to be accepted.
   *
   * <ul>
   *     <li>The route path must end with a wild card</li>
   *     <li>Parameters are allowed but full regex patterns not</li>
   *     <li>No other handler can be registered before or after this call (but they can on a new route object for the same path)</li>
   *     <li>Only 1 router per path object</li>
   * </ul>
   * @param subRouter the router to add
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route subRouter(io.vertx.loom.ext.web.Router subRouter) { 
    delegate.subRouter(subRouter.getDelegate());
    return this;
  }

  /**
   * Specify a blocking request handler for the route.
   * This method works just like {@link io.vertx.loom.ext.web.Route#handler} excepted that it will run the blocking handler on a worker thread
   * so that it won't block the event loop. Note that it's safe to call context.next() from the
   * blocking handler as it will be executed on the event loop context (and not on the worker thread.
   * <p>
   * If the blocking handler is ordered it means that any blocking handlers for the same context are never executed
   * concurrently but always in the order they were called. The default value of ordered is true. If you do not want this
   * behaviour and don't mind if your blocking handlers are executed in parallel you can set ordered to false.
   * @param requestHandler the blocking request handler
   * @param ordered if true handlers are executed in sequence, otherwise are run in parallel
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route blockingHandler(io.vertx.core.Handler<io.vertx.loom.ext.web.RoutingContext> requestHandler, boolean ordered) { 
    delegate.blockingHandler(new Handler<io.vertx.ext.web.RoutingContext>() {
      public void handle(io.vertx.ext.web.RoutingContext event) {
        requestHandler.handle(io.vertx.loom.ext.web.RoutingContext.newInstance((io.vertx.ext.web.RoutingContext)event));
      }
    }, ordered);
    return this;
  }

  /**
   * Append a failure handler to the route failure handlers list. The router routes failures to failurehandlers depending on whether the various
   * criteria such as method, path, etc match. When method, path, etc are the same for different routes, You should add multiple
   * failure handlers to the same route object rather than creating two different routes objects with one failure handler for route
   * @param failureHandler the request handler
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route failureHandler(io.vertx.core.Handler<io.vertx.loom.ext.web.RoutingContext> failureHandler) { 
    delegate.failureHandler(new Handler<io.vertx.ext.web.RoutingContext>() {
      public void handle(io.vertx.ext.web.RoutingContext event) {
        failureHandler.handle(io.vertx.loom.ext.web.RoutingContext.newInstance((io.vertx.ext.web.RoutingContext)event));
      }
    });
    return this;
  }

  /**
   * Remove this route from the router
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route remove() { 
    delegate.remove();
    return this;
  }

  /**
   * Disable this route. While disabled the router will not route any requests or failures to it.
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route disable() { 
    delegate.disable();
    return this;
  }

  /**
   * Enable this route.
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route enable() { 
    delegate.enable();
    return this;
  }

  /**
   * Use {@link io.vertx.loom.ext.web.Route#useNormalizedPath} instead
   * @param useNormalizedPath 
   * @return 
   */
  @Deprecated()
  public io.vertx.loom.ext.web.Route useNormalisedPath(boolean useNormalizedPath) { 
    delegate.useNormalisedPath(useNormalizedPath);
    return this;
  }

  /**
   * If true then the normalized request path will be used when routing (e.g. removing duplicate /)
   * Default is true
   * @param useNormalizedPath use normalized path for routing?
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route useNormalizedPath(boolean useNormalizedPath) { 
    delegate.useNormalizedPath(useNormalizedPath);
    return this;
  }

  /**
   * @return the path prefix (if any) for this route
   */
  public java.lang.String getPath() { 
    java.lang.String ret = delegate.getPath();
    return ret;
  }

  /**
   * Returns true of the path is a regular expression, this includes expression paths.
   * @return true if backed by a pattern.
   */
  public boolean isRegexPath() { 
    boolean ret = delegate.isRegexPath();
    return ret;
  }

  /**
   * Returns true of the path doesn't end with a wildcard <code>*</code> or is <code>null</code>.
   * Regular expression paths are always assumed to be exact.
   * @return true if the path is exact.
   */
  public boolean isExactPath() { 
    boolean ret = delegate.isExactPath();
    return ret;
  }

  /**
   * @return the http methods accepted by this route
   */
  public java.util.Set<io.vertx.core.http.HttpMethod> methods() { 
    java.util.Set<io.vertx.core.http.HttpMethod> ret = delegate.methods();
    return ret;
  }

  /**
   * When you add a new route with a regular expression, you can add named capture groups for parameters. <br/>
   * However, if you need more complex parameters names (like "param_name"), you can add parameters names with
   * this function. You have to name capture groups in regex with names: "p0", "p1", "p2", ... <br/>
   * <br/>
   * For example: If you declare route with regex \/(?<p0>[a-z]*)\/(?<p1>[a-z]*) and group names ["param_a", "param-b"]
   * for uri /hello/world you receive inside pathParams() the parameter param_a = "hello"
   * @param groups group names
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route setRegexGroupsNames(java.util.List<java.lang.String> groups) { 
    delegate.setRegexGroupsNames(groups);
    return this;
  }

  /**
   * Giving a name to a route will provide this name as metadata to requests matching this route.
   * This metadata is used by metrics and is meant to group requests with different URI paths (due
   * to parameters) by a common identifier, for example "/resource/:resourceID"
   * common name
   * @param name The name of the route.
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route setName(java.lang.String name) { 
    delegate.setName(name);
    return this;
  }

  /**
   * @return the name of the route. If not given explicitly, the path or the pattern or null is returned (in that order)
   */
  public java.lang.String getName() { 
    java.lang.String ret = delegate.getName();
    return ret;
  }

  /**
   * Append a function request handler to the route handlers list. The function expects to receive the routing context
   * and users are expected to return a . The use of this functional interface allows users to quickly
   * link the responses from other vert.x APIs or clients directly to a handler. If the context response has been ended,
   * for example, {@link io.vertx.loom.ext.web.RoutingContext#end} has been called, then nothing shall happen. For the remaining cases, the
   * following rules apply:
   *
   * <ol>
   *   <li>When <code>body</code> is <code>null</code> then the status code of the response shall be 204 (NO CONTENT)</li>
   *   <li>When <code>body</code> is of type  and the <code>Content-Type</code> isn't set then the <code>Content-Type</code> shall be <code>application/octet-stream</code></li>
   *   <li>When <code>body</code> is of type {@link java.lang.String} and the <code>Content-Type</code> isn't set then the <code>Content-Type</code> shall be <code>text/html</code></li>
   *   <li>Otherwise the response of the future is then passed to the method {@link io.vertx.loom.ext.web.RoutingContext#json} to perform a JSON serialization of the result</li>
   * </ol>
   *
   * Internally the function is wrapped as a handler that handles error cases for the user too. For example, if the
   * function throws an exception the error will be catched and a proper error will be propagated throw the router.
   *
   * Also if the same happens while encoding the response, errors are catched and propagated to the router.
   * @param function the request handler function
   * @return a reference to this, so the API can be used fluently
   */
  public <T> io.vertx.loom.ext.web.Route respond(java.util.function.Function<io.vertx.loom.ext.web.RoutingContext,io.vertx.core.Future<T>> function) { 
    delegate.respond(new Function<io.vertx.ext.web.RoutingContext,io.vertx.core.Future<T>>() {
      public io.vertx.core.Future<T> apply(io.vertx.ext.web.RoutingContext arg) {
        io.vertx.core.Future<T> ret = function.apply(io.vertx.loom.ext.web.RoutingContext.newInstance((io.vertx.ext.web.RoutingContext)arg));
        return ret.map(val -> val);
      }
    });
    return this;
  }

  public static Route newInstance(io.vertx.ext.web.Route arg) {
    return arg != null ? new Route(arg) : null;
  }

}
