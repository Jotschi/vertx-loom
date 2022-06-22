# Disclaimer

Work on this PoC has __concluded__. A dedicated [Discord Channel](https://vertx.io/channels/) has been setup to discuss further ideas around Vert.x and Loom.

## Vert.x Loom Wrapper Codegen

This project contains a proof of concept implementation for a codegen wrapper API that provides virtual async-await support to Vert.x

[Upstream changes](https://github.com/vert-x3/vertx-rx/pull/271) for this PoC were made to `vert-rx` and are needed to compile the project.

This PoC is based on the [Async/Await support by August Nagro](https://github.com/AugustNagro/vertx-async-await).


# Example

```java
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.lang.loom.Async;
import io.vertx.loom.core.Vertx;
import io.vertx.loom.ext.web.Router;

Vertx vertx = Vertx.vertx();
Router router = Router.router(vertx);
// The handler method will automatically be invoked in a dedicated virtual thread when using the wrapper API within the `io.vertx.loom` packages.
router.route("/test").handler(rc -> {
  // The await method can be used to wait until the async operation has completed
  List<Long> userIds = Async.await(loadUserIds());
  JsonObject response = new JsonObject();
  response.put("userIds", new JsonArray(userIds));
  rc.end(response.encodePrettily());
});

private Future<List<Long>> loadUserIds() {
  // Invoking async will run the code in new virtual thread and return a future
  return Async.async(() -> {
    Thread.sleep(100);
    return Async.await(loadIdsFromDb());
  });
}

private Future<List<Long>> loadIdsFromDb() {
  return Async.async(() -> {
    Thread.sleep(100);
    return Arrays.asList(1L, 2L, 3L, 42L);
  });
}
```

# Benefits

**Stacktraces**

The traces which can be uses to followup on the execution sequence. Normally with async operations the information about the origin of the operation is often obfuscated or no longer accessible.

```
Nov 02, 2021 6:30:06 PM io.vertx.test.core.AsyncTestBase
INFO: Starting test: UsecaseTest#testServer
Exception in thread "vert.x-virtual-thread-0" java.lang.RuntimeException: java.lang.RuntimeException: java.lang.RuntimeException: Error
	at io.vertx.lang.loom.Coroutine.await(Coroutine.java:48)
	at io.vertx.lang.loom.Async.await(Async.java:60)
	at io.vertx.core.it.UsecaseTest.lambda$0(UsecaseTest.java:25)
	at io.vertx.loom.ext.web.Route$1.lambda$0(Route.java:174)
	at io.vertx.lang.loom.Async.lambda$1(Async.java:76)
	at java.base/java.lang.VirtualThread.run(VirtualThread.java:299)
	at java.base/java.lang.VirtualThread$VThreadContinuation.lambda$new$0(VirtualThread.java:176)
	at java.base/java.lang.Continuation.enter0(Continuation.java:372)
	at java.base/java.lang.Continuation.enter(Continuation.java:365)
Caused by: java.lang.RuntimeException: java.lang.RuntimeException: Error
	at io.vertx.lang.loom.Coroutine.await(Coroutine.java:48)
	at io.vertx.lang.loom.Async.await(Async.java:60)
	at io.vertx.core.it.UsecaseTest.lambda$5(UsecaseTest.java:48)
	at io.vertx.lang.loom.Async.lambda$0(Async.java:42)
	... 4 more
Caused by: java.lang.RuntimeException: Error
	at io.vertx.core.it.UsecaseTest.lambda$6(UsecaseTest.java:57)
	... 5 more
```

**Virtual Threads** 

The PoC makes use of JDK 18 Project Loom and thus allows the use of virtual threads. The callback handlers in various Vert.x classes will be automatically be wrapped and executed in a virtual thread. This allows for great parallelism. Potential calls to blocking APIs will no longer block the allocated thread in the JVM (`carrier thread`). Instead the JVM will automatically switch over to another virtual thread and continue executing code there. In the example above `Thread.sleep` is used to simulate this behaviour.

# Open Tasks

* Investigate thread local impact and potential callback issues with body-handler of the http client
* Implement ways to limit virtual thread pool size
* Check impact of very high virtual thread count on performance and memory
* Check how metrics on the virtual pool scheduler can be gathered and exposed
