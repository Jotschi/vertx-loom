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

package io.vertx.loom.core.eventbus;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryContext;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.eventbus.MessageProducer;
import io.vertx.core.metrics.Measured;

/**
 * A Vert.x event-bus is a light-weight distributed messaging system which allows different parts of your application,
 * or different applications and services to communicate with each in a loosely coupled way.
 * <p>
 * An event-bus supports publish-subscribe messaging, point-to-point messaging and request-response messaging.
 * <p>
 * Message delivery is best-effort and messages can be lost if failure of all or part of the event bus occurs.
 * <p>
 * Please refer to the documentation for more information on the event bus.
 *
 * <p/>
 */

public class EventBus implements Measured {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EventBus that = (EventBus) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  private final io.vertx.core.eventbus.EventBus delegate;

  public EventBus(io.vertx.core.eventbus.EventBus delegate) {
    this.delegate = delegate;
  }

  public EventBus(Object delegate) {
    this.delegate = (io.vertx.core.eventbus.EventBus)delegate;
  }

  public io.vertx.core.eventbus.EventBus getDelegate() {
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
   * Sends a message.
   * <p>
   * The message will be delivered to at most one of the handlers registered to the address.
   * @param address the address to send it to
   * @param message the message, may be <code>null</code>
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.eventbus.EventBus send(java.lang.String address, java.lang.Object message) { 
    delegate.send(address, message);
    return this;
  }

  /**
   * Like  but specifying <code>options</code> that can be used to configure the delivery.
   * @param address the address to send it to
   * @param message the message, may be <code>null</code>
   * @param options delivery options
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.eventbus.EventBus send(java.lang.String address, java.lang.Object message, io.vertx.core.eventbus.DeliveryOptions options) { 
    delegate.send(address, message, options);
    return this;
  }

  /**
   * Sends a message and specify a <code>replyHandler</code> that will be called if the recipient
   * subsequently replies to the message.
   * <p>
   * The message will be delivered to at most one of the handlers registered to the address.
   * @param address the address to send it to
   * @param message the message body, may be <code>null</code>
   * @param replyHandler reply handler will be called when any reply from the recipient is received
   * @return a reference to this, so the API can be used fluently
   */
  public <T> io.vertx.loom.core.eventbus.EventBus request(java.lang.String address, java.lang.Object message, io.vertx.core.Handler<io.vertx.core.AsyncResult<Message<T>>> replyHandler) {
    delegate.request(address, message, replyHandler);
    return this;
  }

  /**
   * Sends a message and specify a <code>replyHandler</code> that will be called if the recipient
   * subsequently replies to the message.
   * <p>
   * The message will be delivered to at most one of the handlers registered to the address.
   * @param address the address to send it to
   * @param message the message body, may be <code>null</code>
   * @return a reference to this, so the API can be used fluently
   */
  public <T> io.vertx.loom.core.eventbus.EventBus request(java.lang.String address, java.lang.Object message) {
    return request(address, message, ar -> { });
  }

  /**
   * Like  but specifying <code>options</code> that can be used to configure the delivery.
   * @param address the address to send it to
   * @param message the message body, may be <code>null</code>
   * @param options delivery options
   * @param replyHandler reply handler will be called when any reply from the recipient is received
   * @return a reference to this, so the API can be used fluently
   */
  public <T> io.vertx.loom.core.eventbus.EventBus request(java.lang.String address, java.lang.Object message, io.vertx.core.eventbus.DeliveryOptions options, io.vertx.core.Handler<io.vertx.core.AsyncResult<Message<T>>> replyHandler) { 
    delegate.request(address, message, options, new Handler<AsyncResult<io.vertx.core.eventbus.Message<T>>>() {
      public void handle(AsyncResult<io.vertx.core.eventbus.Message<T>> ar) {
        if (ar.succeeded()) {
          replyHandler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          replyHandler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Like  but specifying <code>options</code> that can be used to configure the delivery.
   * @param address the address to send it to
   * @param message the message body, may be <code>null</code>
   * @param options delivery options
   * @return a reference to this, so the API can be used fluently
   */
  public <T> io.vertx.loom.core.eventbus.EventBus request(java.lang.String address, java.lang.Object message, io.vertx.core.eventbus.DeliveryOptions options) {
    return request(address, message, options, ar -> { });
  }

  /**
   * Publish a message.<p>
   * The message will be delivered to all handlers registered to the address.
   * @param address the address to publish it to
   * @param message the message, may be <code>null</code>
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.eventbus.EventBus publish(java.lang.String address, java.lang.Object message) { 
    delegate.publish(address, message);
    return this;
  }

  /**
   * Like  but specifying <code>options</code> that can be used to configure the delivery.
   * @param address the address to publish it to
   * @param message the message, may be <code>null</code>
   * @param options the delivery options
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.eventbus.EventBus publish(java.lang.String address, java.lang.Object message, io.vertx.core.eventbus.DeliveryOptions options) { 
    delegate.publish(address, message, options);
    return this;
  }

  /**
   * Create a message consumer against the specified address.
   * <p>
   * The returned consumer is not yet registered
   * at the address, registration will be effective when {@link MessageConsumer#handler}
   * is called.
   * @param address the address that it will register it at
   * @return the event bus message consumer
   */
  public <T> MessageConsumer<T> consumer(java.lang.String address) { 
    return delegate.consumer(address);
  }

  /**
   * Create a consumer and register it against the specified address.
   * @param address the address that will register it at
   * @param handler the handler that will process the received messages
   * @return the event bus message consumer
   */
  public <T> MessageConsumer<T> consumer(java.lang.String address, io.vertx.core.Handler<Message<T>> handler) { 
    return delegate.consumer(address, handler);
  }

  /**
   * Like {@link io.vertx.loom.core.eventbus.EventBus#consumer} but the address won't be propagated across the cluster.
   * @param address the address to register it at
   * @return the event bus message consumer
   */
  public <T> MessageConsumer<T> localConsumer(java.lang.String address) { 
    return delegate.localConsumer(address);
  }

  /**
   * Like {@link io.vertx.loom.core.eventbus.EventBus#consumer} but the address won't be propagated across the cluster.
   * @param address the address that will register it at
   * @param handler the handler that will process the received messages
   * @return the event bus message consumer
   */
  public <T> MessageConsumer<T> localConsumer(java.lang.String address, io.vertx.core.Handler<Message<T>> handler) { 
    return delegate.localConsumer(address, handler);
  }

  /**
   * Create a message sender against the specified address.
   * <p>
   * The returned sender will invoke the 
   * method when the stream {@link io.vertx.loom.core.streams.WriteStream#write} method is called with the sender
   * address and the provided data.
   * @param address the address to send it to
   * @return The sender
   */
  public <T> MessageProducer<T> sender(java.lang.String address) { 
    return delegate.sender(address);
  }

  /**
   * Like {@link io.vertx.loom.core.eventbus.EventBus#sender} but specifying delivery options that will be used for configuring the delivery of
   * the message.
   * @param address the address to send it to
   * @param options the delivery options
   * @return The sender
   */
  public <T> MessageProducer<T> sender(java.lang.String address, io.vertx.core.eventbus.DeliveryOptions options) { 
    return delegate.sender(address, options);
  }

  /**
   * Create a message publisher against the specified address.
   * <p>
   * The returned publisher will invoke the 
   * method when the stream {@link io.vertx.loom.core.streams.WriteStream#write} method is called with the publisher
   * address and the provided data.
   * @param address The address to publish it to
   * @return The publisher
   */
  public <T> MessageProducer<T> publisher(java.lang.String address) { 
    return delegate.publisher(address);
  }

  /**
   * Like {@link io.vertx.loom.core.eventbus.EventBus#publisher} but specifying delivery options that will be used for configuring the delivery of
   * the message.
   * @param address the address to publish it to
   * @param options the delivery options
   * @return The publisher
   */
  public <T> MessageProducer<T> publisher(java.lang.String address, io.vertx.core.eventbus.DeliveryOptions options) { 
    return delegate.publisher(address, options);
  }

  /**
   * Add an interceptor that will be called whenever a message is sent from Vert.x
   * @param interceptor the interceptor
   * @return a reference to this, so the API can be used fluently
   */
  public <T> io.vertx.loom.core.eventbus.EventBus addOutboundInterceptor(io.vertx.core.Handler<DeliveryContext<T>> interceptor) { 
    delegate.addOutboundInterceptor(new Handler<io.vertx.core.eventbus.DeliveryContext<T>>() {
      public void handle(io.vertx.core.eventbus.DeliveryContext<T> event) {
        interceptor.handle(event);
      }
    });
    return this;
  }

  /**
   * Remove an interceptor that was added by {@link io.vertx.loom.core.eventbus.EventBus#addOutboundInterceptor}
   * @param interceptor the interceptor
   * @return a reference to this, so the API can be used fluently
   */
  public <T> io.vertx.loom.core.eventbus.EventBus removeOutboundInterceptor(io.vertx.core.Handler<DeliveryContext<T>> interceptor) { 
    delegate.removeOutboundInterceptor(new Handler<io.vertx.core.eventbus.DeliveryContext<T>>() {
      public void handle(io.vertx.core.eventbus.DeliveryContext<T> event) {
        interceptor.handle(event);
      }
    });
    return this;
  }

  /**
   * Add an interceptor that will be called whenever a message is received by Vert.x
   * @param interceptor the interceptor
   * @return a reference to this, so the API can be used fluently
   */
  public <T> io.vertx.loom.core.eventbus.EventBus addInboundInterceptor(io.vertx.core.Handler<DeliveryContext<T>> interceptor) { 
    delegate.addInboundInterceptor(new Handler<io.vertx.core.eventbus.DeliveryContext<T>>() {
      public void handle(io.vertx.core.eventbus.DeliveryContext<T> event) {
        interceptor.handle(event);
      }
    });
    return this;
  }

  /**
   * Remove an interceptor that was added by {@link io.vertx.loom.core.eventbus.EventBus#addInboundInterceptor}
   * @param interceptor the interceptor
   * @return a reference to this, so the API can be used fluently
   */
  public <T> io.vertx.loom.core.eventbus.EventBus removeInboundInterceptor(io.vertx.core.Handler<DeliveryContext<T>> interceptor) { 
    delegate.removeInboundInterceptor(interceptor);
    return this;
  }

  /**
   * Register a message codec.
   * <p>
   * You can register a message codec if you want to send any non standard message across the event bus.
   * E.g. you might want to send POJOs directly across the event bus.
   * <p>
   * To use a message codec for a send, you should specify it in the delivery options.
   * @param codec the message codec to register
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.eventbus.EventBus registerCodec(MessageCodec codec) { 
    io.vertx.loom.core.eventbus.EventBus ret = io.vertx.loom.core.eventbus.EventBus.newInstance((io.vertx.core.eventbus.EventBus)delegate.registerCodec(codec));
    return ret;
  }

  /**
   * Unregister a message codec.
   * <p>
   * @param name the name of the codec
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.eventbus.EventBus unregisterCodec(java.lang.String name) { 
    io.vertx.loom.core.eventbus.EventBus ret = io.vertx.loom.core.eventbus.EventBus.newInstance((io.vertx.core.eventbus.EventBus)delegate.unregisterCodec(name));
    return ret;
  }

  public static EventBus newInstance(io.vertx.core.eventbus.EventBus arg) {
    return arg != null ? new EventBus(arg) : null;
  }

}
