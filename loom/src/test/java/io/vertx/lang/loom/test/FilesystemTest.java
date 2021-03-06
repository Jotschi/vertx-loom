package io.vertx.lang.loom.test;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import io.vertx.loom.core.Vertx;
import io.vertx.loom.core.file.FileSystem;

public class FilesystemTest extends AbstactAsyncLoomTest {

  @Test
  public void testChmod() throws IOException {
    File tempFile = File.createTempFile("vertx-test-", ".tmp");
    tempFile.deleteOnExit();
    Vertx vertx = Vertx.vertx();
    FileSystem fs = vertx.fileSystem();
    fs.chmod(tempFile.getAbsolutePath(), "rwxr-x---", onSuccess(r -> {
      expectLoomThread();
      testComplete();
    }));
    waitFor();
  }

  @Test
  public void testDelete() throws IOException {
    File tempFile = File.createTempFile("vertx-test-", ".tmp");
    tempFile.deleteOnExit();
    Vertx vertx = Vertx.vertx();
    FileSystem fs = vertx.fileSystem();
    fs.delete(tempFile.getAbsolutePath(), onSuccess(r -> {
      expectLoomThread();
      assertFalse("File should be deleted", tempFile.exists());
      testComplete();
    }));
    waitFor();
  }
}
