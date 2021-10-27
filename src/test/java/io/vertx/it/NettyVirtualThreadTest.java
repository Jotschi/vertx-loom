/*
 * Copyright (c) 2011-2019 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */

package io.vertx.it;

import org.junit.Test;

import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.test.core.VertxTestBase;

public class NettyVirtualThreadTest extends VertxTestBase {

  @Test
  public void testVirtualThreadServer() {
    vertx.createHttpServer()
        .requestHandler(req -> req.response().end(Thread.currentThread().getName()))
        .listen(8080, "localhost", onSuccess(s -> {
          HttpClient client = vertx.createHttpClient();
          client.request(HttpMethod.GET, 8080, "localhost", "/somepath", onSuccess(req -> {
            req.send(onSuccess(resp -> {
              resp.bodyHandler(buff -> {
                assertEquals("vert.x-eventloop-thread-0-virt", buff.toString());
                testComplete();
              });
            }));
          }));
        }));
    await();
  }
}
