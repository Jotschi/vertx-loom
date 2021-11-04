package io.vertx.lang.loom;

import io.vertx.codegen.annotations.CacheReturn;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;

@VertxGen
public interface ITest {

  /**
   * See {@link #end(String)}
   */
  @Fluent
  default ITest end(String chunk, Handler<AsyncResult<Void>> handler) {
    end(chunk).onComplete(handler);
    return this;
  }

  /**
   * Shortcut to the response end.
   * 
   * @param chunk
   *          a chunk
   * @return future
   */
  default Future<Void> end(String chunk) {
    return response().end(chunk);
  }

  /**
   * @return the HTTP response object
   */
  @CacheReturn
  HttpServerResponse response();
}
