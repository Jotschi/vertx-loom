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

import java.util.stream.Collectors;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;

/**
 * A router receives request from an {@link io.vertx.loom.core.http.HttpServer} and routes it to the first matching
 * {@link io.vertx.loom.ext.web.Route} that it contains. A router can contain many routes.
 * <p>
 * Routers are also used for routing failures.
 *
 * <p/>
 */

public class Router implements Handler<HttpServerRequest> {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Router that = (Router) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  private final io.vertx.ext.web.Router delegate;
  
  public Router(io.vertx.ext.web.Router delegate) {
    this.delegate = delegate;
  }

  public Router(Object delegate) {
    this.delegate = (io.vertx.ext.web.Router)delegate;
  }

  public io.vertx.ext.web.Router getDelegate() {
    return delegate;
  }

  /**
   * Something has happened, so handle it.
   * @param event the event to handle
   */
  public void handle(HttpServerRequest event) { 
    delegate.handle(event);
  }

  /**
   * Create a router
   * @param vertx the Vert.x instance
   * @return the router
   */
  public static io.vertx.loom.ext.web.Router router(io.vertx.loom.core.Vertx vertx) { 
    io.vertx.loom.ext.web.Router ret = io.vertx.loom.ext.web.Router.newInstance((io.vertx.ext.web.Router)io.vertx.ext.web.Router.router(vertx.getDelegate()));
    return ret;
  }

  /**
   * Add a route with no matching criteria, i.e. it matches all requests or failures.
   * @return the route
   */
  public io.vertx.loom.ext.web.Route route() { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.route());
    return ret;
  }

  /**
   * Add a route that matches the specified HTTP method and path
   * @param method the HTTP method to match
   * @param path URI paths that begin with this path will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route route(io.vertx.core.http.HttpMethod method, java.lang.String path) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.route(method, path));
    return ret;
  }

  /**
   * Add a route that matches the specified path
   * @param path URI paths that begin with this path will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route route(java.lang.String path) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.route(path));
    return ret;
  }

  /**
   * Add a route that matches the specified HTTP method and path regex
   * @param method the HTTP method to match
   * @param regex URI paths that begin with a match for this regex will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route routeWithRegex(io.vertx.core.http.HttpMethod method, java.lang.String regex) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.routeWithRegex(method, regex));
    return ret;
  }

  /**
   * Add a route that matches the specified path regex
   * @param regex URI paths that begin with a match for this regex will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route routeWithRegex(java.lang.String regex) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.routeWithRegex(regex));
    return ret;
  }

  /**
   * Add a route that matches any HTTP GET request
   * @return the route
   */
  public io.vertx.loom.ext.web.Route get() { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.get());
    return ret;
  }

  /**
   * Add a route that matches a HTTP GET request and the specified path
   * @param path URI paths that begin with this path will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route get(java.lang.String path) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.get(path));
    return ret;
  }

  /**
   * Add a route that matches a HTTP GET request and the specified path regex
   * @param regex URI paths that begin with a match for this regex will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route getWithRegex(java.lang.String regex) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.getWithRegex(regex));
    return ret;
  }

  /**
   * Add a route that matches any HTTP HEAD request
   * @return the route
   */
  public io.vertx.loom.ext.web.Route head() { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.head());
    return ret;
  }

  /**
   * Add a route that matches a HTTP HEAD request and the specified path
   * @param path URI paths that begin with this path will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route head(java.lang.String path) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.head(path));
    return ret;
  }

  /**
   * Add a route that matches a HTTP HEAD request and the specified path regex
   * @param regex URI paths that begin with a match for this regex will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route headWithRegex(java.lang.String regex) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.headWithRegex(regex));
    return ret;
  }

  /**
   * Add a route that matches any HTTP OPTIONS request
   * @return the route
   */
  public io.vertx.loom.ext.web.Route options() { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.options());
    return ret;
  }

  /**
   * Add a route that matches a HTTP OPTIONS request and the specified path
   * @param path URI paths that begin with this path will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route options(java.lang.String path) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.options(path));
    return ret;
  }

  /**
   * Add a route that matches a HTTP OPTIONS request and the specified path regex
   * @param regex URI paths that begin with a match for this regex will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route optionsWithRegex(java.lang.String regex) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.optionsWithRegex(regex));
    return ret;
  }

  /**
   * Add a route that matches any HTTP PUT request
   * @return the route
   */
  public io.vertx.loom.ext.web.Route put() { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.put());
    return ret;
  }

  /**
   * Add a route that matches a HTTP PUT request and the specified path
   * @param path URI paths that begin with this path will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route put(java.lang.String path) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.put(path));
    return ret;
  }

  /**
   * Add a route that matches a HTTP PUT request and the specified path regex
   * @param regex URI paths that begin with a match for this regex will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route putWithRegex(java.lang.String regex) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.putWithRegex(regex));
    return ret;
  }

  /**
   * Add a route that matches any HTTP POST request
   * @return the route
   */
  public io.vertx.loom.ext.web.Route post() { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.post());
    return ret;
  }

  /**
   * Add a route that matches a HTTP POST request and the specified path
   * @param path URI paths that begin with this path will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route post(java.lang.String path) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.post(path));
    return ret;
  }

  /**
   * Add a route that matches a HTTP POST request and the specified path regex
   * @param regex URI paths that begin with a match for this regex will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route postWithRegex(java.lang.String regex) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.postWithRegex(regex));
    return ret;
  }

  /**
   * Add a route that matches any HTTP DELETE request
   * @return the route
   */
  public io.vertx.loom.ext.web.Route delete() { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.delete());
    return ret;
  }

  /**
   * Add a route that matches a HTTP DELETE request and the specified path
   * @param path URI paths that begin with this path will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route delete(java.lang.String path) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.delete(path));
    return ret;
  }

  /**
   * Add a route that matches a HTTP DELETE request and the specified path regex
   * @param regex URI paths that begin with a match for this regex will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route deleteWithRegex(java.lang.String regex) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.deleteWithRegex(regex));
    return ret;
  }

  /**
   * Add a route that matches any HTTP TRACE request
   * @return the route
   */
  public io.vertx.loom.ext.web.Route trace() { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.trace());
    return ret;
  }

  /**
   * Add a route that matches a HTTP TRACE request and the specified path
   * @param path URI paths that begin with this path will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route trace(java.lang.String path) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.trace(path));
    return ret;
  }

  /**
   * Add a route that matches a HTTP TRACE request and the specified path regex
   * @param regex URI paths that begin with a match for this regex will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route traceWithRegex(java.lang.String regex) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.traceWithRegex(regex));
    return ret;
  }

  /**
   * Add a route that matches any HTTP CONNECT request
   * @return the route
   */
  public io.vertx.loom.ext.web.Route connect() { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.connect());
    return ret;
  }

  /**
   * Add a route that matches a HTTP CONNECT request and the specified path
   * @param path URI paths that begin with this path will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route connect(java.lang.String path) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.connect(path));
    return ret;
  }

  /**
   * Add a route that matches a HTTP CONNECT request and the specified path regex
   * @param regex URI paths that begin with a match for this regex will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route connectWithRegex(java.lang.String regex) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.connectWithRegex(regex));
    return ret;
  }

  /**
   * Add a route that matches any HTTP PATCH request
   * @return the route
   */
  public io.vertx.loom.ext.web.Route patch() { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.patch());
    return ret;
  }

  /**
   * Add a route that matches a HTTP PATCH request and the specified path
   * @param path URI paths that begin with this path will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route patch(java.lang.String path) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.patch(path));
    return ret;
  }

  /**
   * Add a route that matches a HTTP PATCH request and the specified path regex
   * @param regex URI paths that begin with a match for this regex will match
   * @return the route
   */
  public io.vertx.loom.ext.web.Route patchWithRegex(java.lang.String regex) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.patchWithRegex(regex));
    return ret;
  }

  /**
   * @return a list of all the routes on this router
   */
  public java.util.List<io.vertx.loom.ext.web.Route> getRoutes() { 
    java.util.List<io.vertx.loom.ext.web.Route> ret = delegate.getRoutes().stream().map(elt -> io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)elt)).collect(Collectors.toList());
    return ret;
  }

  /**
   * Remove all the routes from this router
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Router clear() { 
    delegate.clear();
    return this;
  }

  /**
   * Mount a sub router on this router
   * @param mountPoint the mount point (path prefix) to mount it on
   * @param subRouter the router to mount as a sub router
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Route mountSubRouter(java.lang.String mountPoint, io.vertx.loom.ext.web.Router subRouter) { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.mountSubRouter(mountPoint, subRouter.getDelegate()));
    return ret;
  }

  /**
   * Specify an handler to handle an error for a particular status code. You can use to manage general errors too using status code 500.
   * The handler will be called when the context fails and other failure handlers didn't write the reply or when an exception is thrown inside an handler.
   * You <b>must not</b> use {@link io.vertx.loom.ext.web.RoutingContext#next} inside the error handler
   * This does not affect the normal failure routing logic.
   * @param statusCode status code the errorHandler is capable of handle
   * @param errorHandler error handler. Note: You <b>must not</b> use {@link io.vertx.loom.ext.web.RoutingContext#next} inside the provided handler
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Router errorHandler(int statusCode, io.vertx.core.Handler<io.vertx.loom.ext.web.RoutingContext> errorHandler) { 
    delegate.errorHandler(statusCode, new Handler<io.vertx.ext.web.RoutingContext>() {
      public void handle(io.vertx.ext.web.RoutingContext event) {
        errorHandler.handle(io.vertx.loom.ext.web.RoutingContext.newInstance((io.vertx.ext.web.RoutingContext)event));
      }
    });
    return this;
  }

  /**
   * Used to route a context to the router. Used for sub-routers. You wouldn't normally call this method directly.
   * @param context the routing context
   */
  public void handleContext(io.vertx.loom.ext.web.RoutingContext context) { 
    delegate.handleContext(context.getDelegate());
  }

  /**
   * Used to route a failure to the router. Used for sub-routers. You wouldn't normally call this method directly.
   * @param context the routing context
   */
  public void handleFailure(io.vertx.loom.ext.web.RoutingContext context) { 
    delegate.handleFailure(context.getDelegate());
  }

  /**
   * When a Router routes are changed this handler is notified.
   * This is useful for routes that depend on the state of the router.
   * @param handler a notification handler that will receive this router as argument
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Router modifiedHandler(io.vertx.core.Handler<io.vertx.loom.ext.web.Router> handler) { 
    delegate.modifiedHandler(new Handler<io.vertx.ext.web.Router>() {
      public void handle(io.vertx.ext.web.Router event) {
        handler.handle(io.vertx.loom.ext.web.Router.newInstance((io.vertx.ext.web.Router)event));
      }
    });
    return this;
  }

  /**
   * Set whether the router should parse "forwarded"-type headers
   * @param allowForwardHeaders to enable parsing of "forwarded"-type headers
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.Router allowForward(io.vertx.ext.web.AllowForwardHeaders allowForwardHeaders) { 
    delegate.allowForward(allowForwardHeaders);
    return this;
  }

  public static Router newInstance(io.vertx.ext.web.Router arg) {
    return arg != null ? new Router(arg) : null;
  }

}
