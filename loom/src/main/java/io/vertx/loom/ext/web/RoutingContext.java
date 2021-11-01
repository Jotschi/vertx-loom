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

import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.LanguageHeader;
import io.vertx.ext.web.ParsedHeaderValues;
import io.vertx.ext.web.Session;

/**
 * Represents the context for the handling of a request in Vert.x-Web.
 * <p>
 * A new instance is created for each HTTP request that is received in the
 * {@link io.vertx.core.Handler} of the router.
 * <p>
 * The same instance is passed to any matching request or failure handlers during the routing of the request or
 * failure.
 * <p>
 * The context provides access to the  and 
 * and allows you to maintain arbitrary data that lives for the lifetime of the context. Contexts are discarded once they
 * have been routed to the handler for the request.
 * <p>
 * The context also provides access to the {@link io.vertx.loom.ext.web.Session}, cookies and body for the request, given the correct handlers
 * in the application.
 * <p>
 * If you use the internal error handler
 *
 * <p/>
 */

public class RoutingContext {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RoutingContext that = (RoutingContext) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  private final io.vertx.ext.web.RoutingContext delegate;

  public RoutingContext(io.vertx.ext.web.RoutingContext delegate) {
    this.delegate = delegate;
  }

  public RoutingContext(Object delegate) {
    this.delegate = (io.vertx.ext.web.RoutingContext)delegate;
  }

  public io.vertx.ext.web.RoutingContext getDelegate() {
    return delegate;
  }

  /**
   * @return the HTTP request object
   */
  public HttpServerRequest request() { 
    return delegate.request();
  }

  /**
   * @return the HTTP response object
   */
  public HttpServerResponse response() { 
    return delegate.response();
  }

  /**
   * Tell the router to route this context to the next matching route (if any).
   * This method, if called, does not need to be called during the execution of the handler, it can be called
   * some arbitrary time later, if required.
   * <p>
   * If next is not called for a handler then the handler should make sure it ends the response or no response
   * will be sent.
   */
  public void next() { 
    delegate.next();
  }

  /**
   * Fail the context with the specified status code.
   * <p>
   * This will cause the router to route the context to any matching failure handlers for the request. If no failure handlers
   * match It will trigger the error handler matching the status code. You can define such error handler with
   * {@link Router#errorHandler}. If no error handler is not defined, It will send a default failure response with provided status code.
   * @param statusCode the HTTP status code
   */
  public void fail(int statusCode) { 
    delegate.fail(statusCode);
  }

  /**
   * Fail the context with the specified throwable and 500 status code.
   * <p>
   * This will cause the router to route the context to any matching failure handlers for the request. If no failure handlers
   * match It will trigger the error handler matching the status code. You can define such error handler with
   * {@link io.vertx.loom.ext.web.Router#errorHandler}. If no error handler is not defined, It will send a default failure response with 500 status code.
   * @param throwable a throwable representing the failure
   */
  public void fail(java.lang.Throwable throwable) { 
    delegate.fail(throwable);
  }

  /**
   * Fail the context with the specified throwable and the specified the status code.
   * <p>
   * This will cause the router to route the context to any matching failure handlers for the request. If no failure handlers
   * match It will trigger the error handler matching the status code. You can define such error handler with
   * {@link io.vertx.loom.ext.web.Router#errorHandler}. If no error handler is not defined, It will send a default failure response with provided status code.
   * @param statusCode the HTTP status code
   * @param throwable a throwable representing the failure
   */
  public void fail(int statusCode, java.lang.Throwable throwable) { 
    delegate.fail(statusCode, throwable);
  }

  /**
   * Put some arbitrary data in the context. This will be available in any handlers that receive the context.
   * @param key the key for the data
   * @param obj the data
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.ext.web.RoutingContext put(java.lang.String key, java.lang.Object obj) { 
    delegate.put(key, obj);
    return this;
  }

  /**
   * Get some data from the context. The data is available in any handlers that receive the context.
   * @param key the key for the data
   * @return the data
   */
  public <T> T get(java.lang.String key) { 
    T ret = (T) delegate.get(key);
    return ret;
  }

  /**
   * Get some data from the context. The data is available in any handlers that receive the context.
   * @param key the key for the data
   * @param defaultValue when the underlying data doesn't contain the key this will be the return value.
   * @return the data
   */
  public <T> T get(java.lang.String key, T defaultValue) { 
    T ret = (T) delegate.get(key, defaultValue);
    return ret;
  }

  /**
   * Remove some data from the context. The data is available in any handlers that receive the context.
   * @param key the key for the data
   * @return the previous data associated with the key
   */
  public <T> T remove(java.lang.String key) { 
    T ret = (T) delegate.remove(key);
    return ret;
  }

  /**
   * @return the Vert.x instance associated to the initiating {@link io.vertx.loom.ext.web.Router} for this context
   */
  public io.vertx.loom.core.Vertx vertx() { 
    if (cached_2 != null) {
      return cached_2;
    }
    io.vertx.loom.core.Vertx ret = io.vertx.loom.core.Vertx.newInstance((io.vertx.core.Vertx)delegate.vertx());
    cached_2 = ret;
    return ret;
  }

  /**
   * @return the mount point for this router. It will be null for a top level router. For a sub-router it will be the path at which the subrouter was mounted.
   */
  public java.lang.String mountPoint() { 
    java.lang.String ret = delegate.mountPoint();
    return ret;
  }

  /**
   * @return the current route this context is being routed through.
   */
  public io.vertx.loom.ext.web.Route currentRoute() { 
    io.vertx.loom.ext.web.Route ret = io.vertx.loom.ext.web.Route.newInstance((io.vertx.ext.web.Route)delegate.currentRoute());
    return ret;
  }

  /**
   * Use {@link io.vertx.loom.ext.web.RoutingContext#normalizedPath} instead
   * @return 
   */
  @Deprecated()
  public java.lang.String normalisedPath() { 
    java.lang.String ret = delegate.normalisedPath();
    return ret;
  }

  /**
   * Return the normalized path for the request.
   * <p>
   * The normalized path is where the URI path has been decoded, i.e. any unicode or other illegal URL characters that
   * were encoded in the original URL with `%` will be returned to their original form. E.g. `%20` will revert to a space.
   * Also `+` reverts to a space in a query.
   * <p>
   * The normalized path will also not contain any `..` character sequences to prevent resources being accessed outside
   * of the permitted area.
   * <p>
   * It's recommended to always use the normalized path as opposed to 
   * if accessing server resources requested by a client.
   * @return the normalized path
   */
  public java.lang.String normalizedPath() { 
    java.lang.String ret = delegate.normalizedPath();
    return ret;
  }

  /**
   * @param name the cookie name
   * @return the cookie
   */
  @Deprecated()
  public Cookie getCookie(java.lang.String name) { 
    Cookie ret = delegate.getCookie(name);
    return ret;
  }

  /**
   * @param cookie the cookie
   * @return a reference to this, so the API can be used fluently
   */
  @Deprecated()
  public io.vertx.loom.ext.web.RoutingContext addCookie(Cookie cookie) { 
    delegate.addCookie(cookie);
    return this;
  }

  /**
   * @param name the name of the cookie
   * @return the cookie, if it existed, or null
   */
  @Deprecated()
  public Cookie removeCookie(java.lang.String name) { 
    return delegate.removeCookie(name);
  }

  /**
   * @param name the name of the cookie
   * @param invalidate 
   * @return the cookie, if it existed, or null
   */
  @Deprecated()
  public Cookie removeCookie(java.lang.String name, boolean invalidate) { 
    return delegate.removeCookie(name, invalidate);
  }

  /**
   * @return the number of cookies.
   */
  @Deprecated()
  public int cookieCount() { 
    int ret = delegate.cookieCount();
    return ret;
  }

  /**
   * @return a map of all the cookies.
   */
  @Deprecated()
  public java.util.Map<java.lang.String,Cookie> cookieMap() { 
    java.util.Map<java.lang.String,Cookie> ret = delegate.cookieMap().entrySet().stream().collect(Collectors.toMap(_e -> _e.getKey(), _e -> _e.getValue()));
    return ret;
  }

  /**
   * @return the entire HTTP request body as a string, assuming UTF-8 encoding if the request does not provide the content type charset attribute. If a charset is provided in the request that it shall be respected. The context must have first been routed to a {@link io.vertx.loom.ext.web.handler.BodyHandler} for this to be populated.
   */
  public java.lang.String getBodyAsString() { 
    java.lang.String ret = delegate.getBodyAsString();
    return ret;
  }

  /**
   * Get the entire HTTP request body as a string, assuming the specified encoding. The context must have first been routed to a
   * {@link io.vertx.loom.ext.web.handler.BodyHandler} for this to be populated.
   * @param encoding the encoding, e.g. "UTF-16"
   * @return the body
   */
  public java.lang.String getBodyAsString(java.lang.String encoding) { 
    java.lang.String ret = delegate.getBodyAsString(encoding);
    return ret;
  }

  /**
   * Gets the current body buffer as a . If a positive limit is provided the parsing will only happen
   * if the buffer length is smaller or equal to the limit. Otherwise an {@link java.lang.IllegalStateException} is thrown.
   *
   * When the application is only handling uploads in JSON format, it is recommended to set a limit on
   * {@link io.vertx.loom.ext.web.handler.BodyHandler#setBodyLimit} as this will avoid the upload to be parsed and
   * loaded into the application memory.
   * @param maxAllowedLength if the current buffer length is greater than the limit an {@link java.lang.IllegalStateException} is thrown. This can be used to avoid DDoS attacks on very long JSON payloads that could take over the CPU while attempting to parse the data.
   * @return Get the entire HTTP request body as a . The context must have first been routed to a {@link io.vertx.loom.ext.web.handler.BodyHandler} for this to be populated. <br/> When the body is <code>null</code> or the <code>"null"</code> JSON literal then <code>null</code> is returned.
   */
  public io.vertx.core.json.JsonObject getBodyAsJson(int maxAllowedLength) { 
    io.vertx.core.json.JsonObject ret = delegate.getBodyAsJson(maxAllowedLength);
    return ret;
  }

  /**
   * Gets the current body buffer as a . If a positive limit is provided the parsing will only happen
   * if the buffer length is smaller or equal to the limit. Otherwise an {@link java.lang.IllegalStateException} is thrown.
   *
   * When the application is only handling uploads in JSON format, it is recommended to set a limit on
   * {@link io.vertx.loom.ext.web.handler.BodyHandler#setBodyLimit} as this will avoid the upload to be parsed and
   * loaded into the application memory.
   * @param maxAllowedLength if the current buffer length is greater than the limit an {@link java.lang.IllegalStateException} is thrown. This can be used to avoid DDoS attacks on very long JSON payloads that could take over the CPU while attempting to parse the data.
   * @return Get the entire HTTP request body as a . The context must have first been routed to a {@link io.vertx.loom.ext.web.handler.BodyHandler} for this to be populated. <br/> When the body is <code>null</code> or the <code>"null"</code> JSON literal then <code>null</code> is returned.
   */
  public io.vertx.core.json.JsonArray getBodyAsJsonArray(int maxAllowedLength) { 
    io.vertx.core.json.JsonArray ret = delegate.getBodyAsJsonArray(maxAllowedLength);
    return ret;
  }

  /**
   * @return Get the entire HTTP request body as a . The context must have first been routed to a {@link io.vertx.loom.ext.web.handler.BodyHandler} for this to be populated. <br/> When the body is <code>null</code> or the <code>"null"</code> JSON literal then <code>null</code> is returned.
   */
  public io.vertx.core.json.JsonObject getBodyAsJson() { 
    io.vertx.core.json.JsonObject ret = delegate.getBodyAsJson();
    return ret;
  }

  /**
   * @return Get the entire HTTP request body as a . The context must have first been routed to a {@link io.vertx.loom.ext.web.handler.BodyHandler} for this to be populated. <br/> When the body is <code>null</code> or the <code>"null"</code> JSON literal then <code>null</code> is returned.
   */
  public io.vertx.core.json.JsonArray getBodyAsJsonArray() { 
    io.vertx.core.json.JsonArray ret = delegate.getBodyAsJsonArray();
    return ret;
  }

  /**
   * @return Get the entire HTTP request body as a . The context must have first been routed to a {@link io.vertx.loom.ext.web.handler.BodyHandler} for this to be populated.
   */
  public Buffer getBody() { 
    return delegate.getBody();
  }

  /**
   * @return a set of fileuploads (if any) for the request. The context must have first been routed to a {@link io.vertx.loom.ext.web.handler.BodyHandler} for this to work.
   */
  public java.util.Set<FileUpload> fileUploads() { 
    return delegate.fileUploads();
  }

  /**
   * Get the session. The context must have first been routed to a {@link io.vertx.loom.ext.web.handler.SessionHandler}
   * for this to be populated.
   * Sessions live for a browser session, and are maintained by session cookies.
   * @return the session.
   */
  public Session session() { 
    return delegate.session();
  }

  /**
   * Whether the {@link io.vertx.loom.ext.web.RoutingContext#session} has been already called or not. This is usually used by the
   * {@link io.vertx.loom.ext.web.handler.SessionHandler}.
   * @return true if the session has been accessed.
   */
  public boolean isSessionAccessed() { 
    boolean ret = delegate.isSessionAccessed();
    return ret;
  }

  /**
   * Get the authenticated user (if any). This will usually be injected by an auth handler if authentication if successful.
   * @return the user, or null if the current user is not authenticated.
   */
  public User user() { 
    return delegate.user();
  }

  /**
   * If the context is being routed to failure handlers after a failure has been triggered by calling
   * {@link io.vertx.loom.ext.web.RoutingContext#fail} then this will return that throwable. It can be used by failure handlers to render a response,
   * e.g. create a failure response page.
   * @return the throwable used when signalling failure
   */
  public java.lang.Throwable failure() { 
    if (cached_3 != null) {
      return cached_3;
    }
    java.lang.Throwable ret = delegate.failure();
    cached_3 = ret;
    return ret;
  }

  /**
   * If the context is being routed to failure handlers after a failure has been triggered by calling
   * {@link io.vertx.loom.ext.web.RoutingContext#fail}  then this will return that status code.  It can be used by failure handlers to render a response,
   * e.g. create a failure response page.
   *
   * When the status code has not been set yet (it is undefined) its value will be -1.
   * @return the status code used when signalling failure
   */
  public int statusCode() { 
    if (cached_4 != null) {
      return cached_4;
    }
    int ret = delegate.statusCode();
    cached_4 = ret;
    return ret;
  }

  /**
   * If the route specifies produces matches, e.g. produces `text/html` and `text/plain`, and the `accept` header
   * matches one or more of these then this returns the most acceptable match.
   * @return the most acceptable content type.
   */
  public java.lang.String getAcceptableContentType() { 
    java.lang.String ret = delegate.getAcceptableContentType();
    return ret;
  }

  /**
   * The headers:
   * <ol>
   * <li>Accept</li>
   * <li>Accept-Charset</li>
   * <li>Accept-Encoding</li>
   * <li>Accept-Language</li>
   * <li>Content-Type</li>
   * </ol>
   * Parsed into {@link io.vertx.loom.ext.web.ParsedHeaderValue}
   * @return A container with the parsed headers.
   */
  public ParsedHeaderValues parsedHeaders() { 
    return delegate.parsedHeaders();
  }

  /**
   * Add a handler that will be called just before headers are written to the response. This gives you a hook where
   * you can write any extra headers before the response has been written when it will be too late.
   * @param handler the handler
   * @return the id of the handler. This can be used if you later want to remove the handler.
   */
  public int addHeadersEndHandler(io.vertx.core.Handler<java.lang.Void> handler) { 
    int ret = delegate.addHeadersEndHandler(handler);
    return ret;
  }

  /**
   * Remove a headers end handler
   * @param handlerID the id as returned from {@link io.vertx.loom.ext.web.RoutingContext#addHeadersEndHandler}.
   * @return true if the handler existed and was removed, false otherwise
   */
  public boolean removeHeadersEndHandler(int handlerID) { 
    boolean ret = delegate.removeHeadersEndHandler(handlerID);
    return ret;
  }

  /**
   * Provides a handler that will be called after the last part of the body is written to the wire.
   * The handler is called asynchronously of when the response has been received by the client.
   * This provides a hook allowing you to do more operations once the request has been sent over the wire.
   * Do not use this for resource cleanup as this handler might never get called (e.g. if the connection is reset).
   * @param handler the handler
   * @return the id of the handler. This can be used if you later want to remove the handler.
   */
  public int addBodyEndHandler(io.vertx.core.Handler<java.lang.Void> handler) { 
    int ret = delegate.addBodyEndHandler(handler);
    return ret;
  }

  /**
   * Remove a body end handler
   * @param handlerID the id as returned from {@link io.vertx.loom.ext.web.RoutingContext#addBodyEndHandler}.
   * @return true if the handler existed and was removed, false otherwise
   */
  public boolean removeBodyEndHandler(int handlerID) { 
    boolean ret = delegate.removeBodyEndHandler(handlerID);
    return ret;
  }

  /**
   * Add an end handler for the request/response context. This will be called when the response is disposed or an
   * exception has been encountered to allow consistent cleanup. The handler is called asynchronously of when the
   * response has been received by the client.
   * @param handler the handler that will be called with either a success or failure result.
   * @return the id of the handler. This can be used if you later want to remove the handler.
   */
  public int addEndHandler(io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    int ret = delegate.addEndHandler(handler);
    return ret;
  }

  /**
   * Remove an end handler
   * @param handlerID the id as returned from {@link io.vertx.loom.ext.web.RoutingContext#addEndHandler}.
   * @return true if the handler existed and was removed, false otherwise
   */
  public boolean removeEndHandler(int handlerID) { 
    boolean ret = delegate.removeEndHandler(handlerID);
    return ret;
  }

  /**
   * @return true if the context is being routed to failure handlers.
   */
  public boolean failed() { 
    boolean ret = delegate.failed();
    return ret;
  }

  /**
   * Set the body. Used by the {@link io.vertx.loom.ext.web.handler.BodyHandler}. You will not normally call this method.
   * @param body the body
   */
  public void setBody(Buffer body) { 
    delegate.setBody(body);
  }

  /**
   * Set the session. Used by the {@link io.vertx.loom.ext.web.handler.SessionHandler}. You will not normally call this method.
   * @param session the session
   */
  public void setSession(Session session) { 
    delegate.setSession(session);
  }

  /**
   * Set the user. Usually used by auth handlers to inject a User. You will not normally call this method.
   * @param user the user
   */
  public void setUser(User user) { 
    delegate.setUser(user);
  }

  /**
   * Clear the current user object in the context. This usually is used for implementing a log out feature, since the
   * current user is unbounded from the routing context.
   */
  public void clearUser() { 
    delegate.clearUser();
  }

  /**
   * Set the acceptable content type. Used by
   * @param contentType the content type
   */
  public void setAcceptableContentType(java.lang.String contentType) { 
    delegate.setAcceptableContentType(contentType);
  }

  /**
   * Restarts the current router with a new path and reusing the original method. All path parameters are then parsed
   * and available on the params list. Query params will also be allowed and available.
   * @param path the new http path.
   */
  public void reroute(java.lang.String path) { 
    delegate.reroute(path);
  }

  /**
   * Restarts the current router with a new method and path. All path parameters are then parsed and available on the
   * params list. Query params will also be allowed and available.
   * @param method the new http request
   * @param path the new http path.
   */
  public void reroute(io.vertx.core.http.HttpMethod method, java.lang.String path) { 
    delegate.reroute(method, path);
  }

  /**
   * Returns the languages for the current request. The languages are determined from the <code>Accept-Language</code>
   * header and sorted on quality.
   *
   * When 2 or more entries have the same quality then the order used to return the best match is based on the lowest
   * index on the original list. For example if a user has en-US and en-GB with same quality and this order the best
   * match will be en-US because it was declared as first entry by the client.
   * @return The best matched language for the request
   */
  public java.util.List<LanguageHeader> acceptableLanguages() { 
    return delegate.acceptableLanguages();
  }

  /**
   * Helper to return the user preferred language.
   * It is the same action as returning the first element of the acceptable languages.
   * @return the users preferred locale.
   */
  public LanguageHeader preferredLanguage() { 
    return delegate.preferredLanguage();
  }

  /**
   * Returns a map of named parameters as defined in path declaration with their actual values
   * @return the map of named parameters
   */
  public java.util.Map<java.lang.String,java.lang.String> pathParams() { 
    java.util.Map<java.lang.String,java.lang.String> ret = delegate.pathParams();
    return ret;
  }

  /**
   * Gets the value of a single path parameter
   * @param name the name of parameter as defined in path declaration
   * @return the actual value of the parameter or null if it doesn't exist
   */
  public java.lang.String pathParam(java.lang.String name) { 
    java.lang.String ret = delegate.pathParam(name);
    return ret;
  }

  /**
   * Returns a map of all query parameters inside the <a href="https://en.wikipedia.org/wiki/Query_string">query string</a><br/>
   * The query parameters are lazily decoded: the decoding happens on the first time this method is called. If the query string is invalid
   * it fails the context
   * @return the multimap of query parameters
   */
  public MultiMap queryParams() { 
    return delegate.queryParams();
  }

  /**
   * Gets the value of a single query parameter. For more info {@link io.vertx.loom.ext.web.RoutingContext#queryParams}
   * @param name The name of query parameter
   * @return The list of all parameters matching the parameter name. It returns an empty list if no query parameter with <code>name</code> was found
   */
  public java.util.List<java.lang.String> queryParam(java.lang.String name) { 
    java.util.List<java.lang.String> ret = delegate.queryParam(name);
    return ret;
  }

  /**
   * Set Content-Disposition get to "attachment" with optional <code>filename</code> mime type.
   * @param filename the filename for the attachment
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext attachment(java.lang.String filename) { 
    delegate.attachment(filename);
    return this;
  }

  /**
   * See {@link io.vertx.loom.ext.web.RoutingContext#redirect}.
   * @param url 
   * @param handler 
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext redirect(java.lang.String url, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.redirect(url, handler);
    return this;
  }

  /**
   * See {@link io.vertx.loom.ext.web.RoutingContext#redirect}.
   * @param url 
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext redirect(java.lang.String url) {
    return redirect(url, ar -> { });
  }

  /**
   * See {@link io.vertx.loom.ext.web.RoutingContext#json}.
   * @param json 
   * @param handler 
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext json(java.lang.Object json, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.json(json, handler);
    return this;
  }

  /**
   * See {@link io.vertx.loom.ext.web.RoutingContext#json}.
   * @param json 
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext json(java.lang.Object json) {
    return json(json, ar -> { });
  }

  /**
   * Check if the incoming request contains the "Content-Type"
   * get field, and it contains the give mime `type`.
   * If there is no request body, `false` is returned.
   * If there is no content type, `false` is returned.
   * Otherwise, it returns true if the `type` that matches.
   * <p/>
   * Examples:
   * <p/>
   * // With Content-Type: text/html; charset=utf-8
   * is("html"); // => true
   * is("text/html"); // => true
   * <p/>
   * // When Content-Type is application/json
   * is("application/json"); // => true
   * is("html"); // => false
   * @param type content type
   * @return The most close value
   */
  public boolean is(java.lang.String type) { 
    if (cached_8 != null) {
      return cached_8;
    }
    boolean ret = delegate.is(type);
    cached_8 = ret;
    return ret;
  }

  /**
   * Check if the request is fresh, aka
   * Last-Modified and/or the ETag
   * still match.
   * @return true if content is fresh according to the cache.
   */
  public boolean isFresh() { 
    boolean ret = delegate.isFresh();
    return ret;
  }

  /**
   * Set the ETag of a response.
   * This will normalize the quotes if necessary.
   * <p/>
   * etag('md5hashsum');
   * etag('"md5hashsum"');
   * ('W/"123456789"');
   * @param etag the etag value
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext etag(java.lang.String etag) { 
    delegate.etag(etag);
    return this;
  }

  /**
   * Set the Last-Modified date using a String.
   * @param instant the last modified instant
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext lastModified(java.lang.String instant) { 
    delegate.lastModified(instant);
    return this;
  }

  /**
   * See {@link io.vertx.loom.ext.web.RoutingContext#end}
   * @param chunk 
   * @param handler 
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext end(java.lang.String chunk, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.end(chunk, handler);
    return this;
  }

  /**
   * See {@link io.vertx.loom.ext.web.RoutingContext#end}
   * @param chunk 
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext end(java.lang.String chunk) {
    return 
end(chunk, ar -> { });
  }

  /**
   * See {@link io.vertx.loom.ext.web.RoutingContext#end}
   * @param buffer 
   * @param handler 
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext end(Buffer buffer, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.end(buffer, handler);
    return this;
  }

  /**
   * See {@link io.vertx.loom.ext.web.RoutingContext#end}
   * @param buffer 
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext end(Buffer buffer) {
    return end(buffer, ar -> { });
  }

  /**
   * See {@link io.vertx.loom.ext.web.RoutingContext#end}
   * @param handler 
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext end(io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.end(handler);
    return this;
  }

  /**
   * See {@link io.vertx.loom.ext.web.RoutingContext#end}
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext end() {
    return end(ar -> { });
  }

  /**
   * @return all the context data as a map
   */
  public java.util.Map<java.lang.String,java.lang.Object> data() { 
    java.util.Map<java.lang.String,java.lang.Object> ret = delegate.data();
    return ret;
  }

  /**
   * Always decode the current query string with the given <code>encoding</code>. The decode result is never cached. Callers
   * to this method are expected to cache the result if needed. Usually users should use {@link io.vertx.loom.ext.web.RoutingContext#queryParams}.
   *
   * This method is only useful when the requests without content type (<code>GET</code> requests as an example) expect that
   * query params are in the ASCII format <code>ISO-5559-1</code>.
   * @param encoding a non null character set.
   * @return the multimap of query parameters
   */
  public MultiMap queryParams(java.nio.charset.Charset encoding) { 
    return delegate.queryParams(encoding);
  }

  /**
   * Set the Last-Modified date using a Instant.
   * @param instant the last modified instant
   * @return 
   */
  public io.vertx.loom.ext.web.RoutingContext lastModified(java.time.Instant instant) { 
    delegate.lastModified(instant);
    return this;
  }

  private io.vertx.loom.core.Vertx cached_2;
  private java.lang.Throwable cached_3;
  private java.lang.Integer cached_4;
  private java.lang.Boolean cached_8;
  public static RoutingContext newInstance(io.vertx.ext.web.RoutingContext arg) {
    return arg != null ? new RoutingContext(arg) : null;
  }

}