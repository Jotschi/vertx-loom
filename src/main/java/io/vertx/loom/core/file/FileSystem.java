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

package io.vertx.loom.core.file;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystemProps;

/**
 * Contains a broad set of operations for manipulating files on the file system.
 * <p>
 * A (potential) blocking and non blocking version of each operation is provided.
 * <p>
 * The non blocking versions take a handler which is called when the operation completes or an error occurs.
 * <p>
 * The blocking versions are named <code>xxxBlocking</code> and return the results, or throw exceptions directly.
 * In many cases, depending on the operating system and file system some of the potentially blocking operations
 * can return quickly, which is why we provide them, but it's highly recommended that you test how long they take to
 * return in your particular application before using them on an event loop.
 * <p>
 * Please consult the documentation for more information on file system support.
 *
 * <p/>
 */

public class FileSystem {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FileSystem that = (FileSystem) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  private final io.vertx.core.file.FileSystem delegate;

  public FileSystem(io.vertx.core.file.FileSystem delegate) {
    this.delegate = delegate;
  }

  public FileSystem(Object delegate) {
    this.delegate = (io.vertx.core.file.FileSystem)delegate;
  }

  public io.vertx.core.file.FileSystem getDelegate() {
    return delegate;
  }


  /**
   * Copy a file from the path <code>from</code> to path <code>to</code>, asynchronously.
   * <p>
   * The copy will fail if the destination already exists.
   * @param from the path to copy from
   * @param to the path to copy to
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem copy(java.lang.String from, java.lang.String to, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.copy(from, to, handler);
    return this;
  }

  /**
   * Copy a file from the path <code>from</code> to path <code>to</code>, asynchronously.
   * <p>
   * The copy will fail if the destination already exists.
   * @param from the path to copy from
   * @param to the path to copy to
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem copy(java.lang.String from, java.lang.String to) {
    return copy(from, to, ar -> { });
  }

  /**
   * Copy a file from the path <code>from</code> to path <code>to</code>, asynchronously.
   * @param from the path to copy from
   * @param to the path to copy to
   * @param options options describing how the file should be copied
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem copy(java.lang.String from, java.lang.String to, io.vertx.core.file.CopyOptions options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.copy(from, to, options, handler);
    return this;
  }

  /**
   * Copy a file from the path <code>from</code> to path <code>to</code>, asynchronously.
   * @param from the path to copy from
   * @param to the path to copy to
   * @param options options describing how the file should be copied
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem copy(java.lang.String from, java.lang.String to, io.vertx.core.file.CopyOptions options) {
    return copy(from, to, options, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#copy}
   * @param from 
   * @param to 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem copyBlocking(java.lang.String from, java.lang.String to) { 
    delegate.copyBlocking(from, to);
    return this;
  }

  /**
   * Copy a file from the path <code>from</code> to path <code>to</code>, asynchronously.
   * <p>
   * If <code>recursive</code> is <code>true</code> and <code>from</code> represents a directory, then the directory and its contents
   * will be copied recursively to the destination <code>to</code>.
   * <p>
   * The copy will fail if the destination if the destination already exists.
   * @param from the path to copy from
   * @param to the path to copy to
   * @param recursive 
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem copyRecursive(java.lang.String from, java.lang.String to, boolean recursive, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.copyRecursive(from, to, recursive, handler);
    return this;
  }

  /**
   * Copy a file from the path <code>from</code> to path <code>to</code>, asynchronously.
   * <p>
   * If <code>recursive</code> is <code>true</code> and <code>from</code> represents a directory, then the directory and its contents
   * will be copied recursively to the destination <code>to</code>.
   * <p>
   * The copy will fail if the destination if the destination already exists.
   * @param from the path to copy from
   * @param to the path to copy to
   * @param recursive 
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem copyRecursive(java.lang.String from, java.lang.String to, boolean recursive) {
    return copyRecursive(from, to, recursive, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#copyRecursive}
   * @param from 
   * @param to 
   * @param recursive 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem copyRecursiveBlocking(java.lang.String from, java.lang.String to, boolean recursive) { 
    delegate.copyRecursiveBlocking(from, to, recursive);
    return this;
  }

  /**
   * Move a file from the path <code>from</code> to path <code>to</code>, asynchronously.
   * <p>
   * The move will fail if the destination already exists.
   * @param from the path to copy from
   * @param to the path to copy to
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem move(java.lang.String from, java.lang.String to, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.move(from, to, handler);
    return this;
  }

  /**
   * Move a file from the path <code>from</code> to path <code>to</code>, asynchronously.
   * <p>
   * The move will fail if the destination already exists.
   * @param from the path to copy from
   * @param to the path to copy to
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem move(java.lang.String from, java.lang.String to) {
    return 
move(from, to, ar -> { });
  }

  /**
   * Move a file from the path <code>from</code> to path <code>to</code>, asynchronously.
   * @param from the path to copy from
   * @param to the path to copy to
   * @param options options describing how the file should be copied
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem move(java.lang.String from, java.lang.String to, io.vertx.core.file.CopyOptions options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.move(from, to, options, handler);
    return this;
  }

  /**
   * Move a file from the path <code>from</code> to path <code>to</code>, asynchronously.
   * @param from the path to copy from
   * @param to the path to copy to
   * @param options options describing how the file should be copied
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem move(java.lang.String from, java.lang.String to, io.vertx.core.file.CopyOptions options) {
    return move(from, to, options, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#move}
   * @param from 
   * @param to 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem moveBlocking(java.lang.String from, java.lang.String to) { 
    delegate.moveBlocking(from, to);
    return this;
  }

  /**
   * Truncate the file represented by <code>path</code> to length <code>len</code> in bytes, asynchronously.
   * <p>
   * The operation will fail if the file does not exist or <code>len</code> is less than <code>zero</code>.
   * @param path the path to the file
   * @param len the length to truncate it to
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem truncate(java.lang.String path, long len, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.truncate(path, len, handler);
    return this;
  }

  /**
   * Truncate the file represented by <code>path</code> to length <code>len</code> in bytes, asynchronously.
   * <p>
   * The operation will fail if the file does not exist or <code>len</code> is less than <code>zero</code>.
   * @param path the path to the file
   * @param len the length to truncate it to
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem truncate(java.lang.String path, long len) {
    return truncate(path, len, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#truncate}
   * @param path 
   * @param len 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem truncateBlocking(java.lang.String path, long len) { 
    delegate.truncateBlocking(path, len);
    return this;
  }

  /**
   * Change the permissions on the file represented by <code>path</code> to <code>perms</code>, asynchronously.
   * <p>
   * The permission String takes the form rwxr-x--- as
   * specified <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
   * @param path the path to the file
   * @param perms the permissions string
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem chmod(java.lang.String path, java.lang.String perms, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.chmod(path, perms, handler);
    return this;
  }

  /**
   * Change the permissions on the file represented by <code>path</code> to <code>perms</code>, asynchronously.
   * <p>
   * The permission String takes the form rwxr-x--- as
   * specified <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
   * @param path the path to the file
   * @param perms the permissions string
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem chmod(java.lang.String path, java.lang.String perms) {
    return chmod(path, perms, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem #chmod(String, String, Handler)}
   * @param path 
   * @param perms 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem chmodBlocking(java.lang.String path, java.lang.String perms) { 
    delegate.chmodBlocking(path, perms);
    return this;
  }

  /**
   * Change the permissions on the file represented by <code>path</code> to <code>perms</code>, asynchronously.<p>
   * The permission String takes the form rwxr-x--- as
   * specified in {<a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>}.
   * <p>
   * If the file is directory then all contents will also have their permissions changed recursively. Any directory permissions will
   * be set to <code>dirPerms</code>, whilst any normal file permissions will be set to <code>perms</code>.
   * @param path the path to the file
   * @param perms the permissions string
   * @param dirPerms the directory permissions
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem chmodRecursive(java.lang.String path, java.lang.String perms, java.lang.String dirPerms, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.chmodRecursive(path, perms, dirPerms, handler);
    return this;
  }

  /**
   * Change the permissions on the file represented by <code>path</code> to <code>perms</code>, asynchronously.<p>
   * The permission String takes the form rwxr-x--- as
   * specified in {<a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>}.
   * <p>
   * If the file is directory then all contents will also have their permissions changed recursively. Any directory permissions will
   * be set to <code>dirPerms</code>, whilst any normal file permissions will be set to <code>perms</code>.
   * @param path the path to the file
   * @param perms the permissions string
   * @param dirPerms the directory permissions
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem chmodRecursive(java.lang.String path, java.lang.String perms, java.lang.String dirPerms) {
    return chmodRecursive(path, perms, dirPerms, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#chmodRecursive}
   * @param path 
   * @param perms 
   * @param dirPerms 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem chmodRecursiveBlocking(java.lang.String path, java.lang.String perms, java.lang.String dirPerms) { 
    delegate.chmodRecursiveBlocking(path, perms, dirPerms);
    return this;
  }

  /**
   * Change the ownership on the file represented by <code>path</code> to <code>user</code> and {code group}, asynchronously.
   * @param path the path to the file
   * @param user the user name, <code>null</code> will not change the user name
   * @param group the user group, <code>null</code> will not change the user group name
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem chown(java.lang.String path, java.lang.String user, java.lang.String group, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.chown(path, user, group, handler);
    return this;
  }

  /**
   * Change the ownership on the file represented by <code>path</code> to <code>user</code> and {code group}, asynchronously.
   * @param path the path to the file
   * @param user the user name, <code>null</code> will not change the user name
   * @param group the user group, <code>null</code> will not change the user group name
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem chown(java.lang.String path, java.lang.String user, java.lang.String group) {
    return chown(path, user, group, ar -> { });
  }

  /**
   * Blocking version of 
   *
   * @param path 
   * @param user 
   * @param group 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem chownBlocking(java.lang.String path, java.lang.String user, java.lang.String group) { 
    delegate.chownBlocking(path, user, group);
    return this;
  }

  /**
   * Obtain properties for the file represented by <code>path</code>, asynchronously.
   * <p>
   * If the file is a link, the link will be followed.
   * @param path the path to the file
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem props(java.lang.String path, io.vertx.core.Handler<io.vertx.core.AsyncResult<FileProps>> handler) { 
    delegate.props(path, new Handler<AsyncResult<io.vertx.core.file.FileProps>>() {
      public void handle(AsyncResult<io.vertx.core.file.FileProps> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Obtain properties for the file represented by <code>path</code>, asynchronously.
   * <p>
   * If the file is a link, the link will be followed.
   * @param path the path to the file
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem props(java.lang.String path) {
    delegate.props(path);
    return this;
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#props}
   * @param path 
   * @return 
   */
  public FileProps propsBlocking(java.lang.String path) { 
    FileProps ret = delegate.propsBlocking(path);
    return ret;
  }

  /**
   * Obtain properties for the link represented by <code>path</code>, asynchronously.
   * <p>
   * The link will not be followed.
   * @param path the path to the file
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem lprops(java.lang.String path, io.vertx.core.Handler<io.vertx.core.AsyncResult<FileProps>> handler) { 
    delegate.lprops(path, new Handler<AsyncResult<io.vertx.core.file.FileProps>>() {
      public void handle(AsyncResult<io.vertx.core.file.FileProps> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Obtain properties for the link represented by <code>path</code>, asynchronously.
   * <p>
   * The link will not be followed.
   * @param path the path to the file
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem lprops(java.lang.String path) {
    return lprops(path, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#lprops}
   * @param path 
   * @return 
   */
  public FileProps lpropsBlocking(java.lang.String path) { 
    return delegate.lpropsBlocking(path);
  }

  /**
   * Create a hard link on the file system from <code>link</code> to <code>existing</code>, asynchronously.
   * @param link the link
   * @param existing the link destination
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem link(java.lang.String link, java.lang.String existing, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.link(link, existing, handler);
    return this;
  }

  /**
   * Create a hard link on the file system from <code>link</code> to <code>existing</code>, asynchronously.
   * @param link the link
   * @param existing the link destination
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem link(java.lang.String link, java.lang.String existing) {
    return link(link, existing, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#link}
   * @param link 
   * @param existing 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem linkBlocking(java.lang.String link, java.lang.String existing) { 
    delegate.linkBlocking(link, existing);
    return this;
  }

  /**
   * Create a symbolic link on the file system from <code>link</code> to <code>existing</code>, asynchronously.
   * @param link the link
   * @param existing the link destination
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem symlink(java.lang.String link, java.lang.String existing, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.symlink(link, existing, handler);
    return this;
  }

  /**
   * Create a symbolic link on the file system from <code>link</code> to <code>existing</code>, asynchronously.
   * @param link the link
   * @param existing the link destination
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem symlink(java.lang.String link, java.lang.String existing) {
    return symlink(link, existing, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#link}
   * @param link 
   * @param existing 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem symlinkBlocking(java.lang.String link, java.lang.String existing) { 
    delegate.symlinkBlocking(link, existing);
    return this;
  }

  /**
   * Unlinks the link on the file system represented by the path <code>link</code>, asynchronously.
   * @param link the link
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem unlink(java.lang.String link, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.unlink(link, handler);
    return this;
  }

  /**
   * Unlinks the link on the file system represented by the path <code>link</code>, asynchronously.
   * @param link the link
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem unlink(java.lang.String link) {
    return unlink(link, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#unlink}
   * @param link 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem unlinkBlocking(java.lang.String link) { 
    delegate.unlinkBlocking(link);
    return this;
  }

  /**
   * Returns the path representing the file that the symbolic link specified by <code>link</code> points to, asynchronously.
   * @param link the link
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem readSymlink(java.lang.String link, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) { 
    delegate.readSymlink(link, handler);
    return this;
  }

  /**
   * Returns the path representing the file that the symbolic link specified by <code>link</code> points to, asynchronously.
   * @param link the link
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem readSymlink(java.lang.String link) {
    return readSymlink(link, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#readSymlink}
   * @param link 
   * @return 
   */
  public java.lang.String readSymlinkBlocking(java.lang.String link) { 
    java.lang.String ret = delegate.readSymlinkBlocking(link);
    return ret;
  }

  /**
   * Deletes the file represented by the specified <code>path</code>, asynchronously.
   * @param path path to the file
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem delete(java.lang.String path, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.delete(path, handler);
    return this;
  }

  /**
   * Deletes the file represented by the specified <code>path</code>, asynchronously.
   * @param path path to the file
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem delete(java.lang.String path) {
    return 
delete(path, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#delete}
   * @param path 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem deleteBlocking(java.lang.String path) { 
    delegate.deleteBlocking(path);
    return this;
  }

  /**
   * Deletes the file represented by the specified <code>path</code>, asynchronously.
   * <p>
   * If the path represents a directory and <code>recursive = true</code> then the directory and its contents will be
   * deleted recursively.
   * @param path path to the file
   * @param recursive delete recursively?
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem deleteRecursive(java.lang.String path, boolean recursive, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.deleteRecursive(path, recursive, handler);
    return this;
  }

  /**
   * Deletes the file represented by the specified <code>path</code>, asynchronously.
   * <p>
   * If the path represents a directory and <code>recursive = true</code> then the directory and its contents will be
   * deleted recursively.
   * @param path path to the file
   * @param recursive delete recursively?
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem deleteRecursive(java.lang.String path, boolean recursive) {
    return 
deleteRecursive(path, recursive, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#deleteRecursive}
   * @param path 
   * @param recursive 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem deleteRecursiveBlocking(java.lang.String path, boolean recursive) { 
    delegate.deleteRecursiveBlocking(path, recursive);
    return this;
  }

  /**
   * Create the directory represented by <code>path</code>, asynchronously.
   * <p>
   * The operation will fail if the directory already exists.
   * @param path path to the file
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem mkdir(java.lang.String path, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.mkdir(path, handler);
    return this;
  }

  /**
   * Create the directory represented by <code>path</code>, asynchronously.
   * <p>
   * The operation will fail if the directory already exists.
   * @param path path to the file
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem mkdir(java.lang.String path) {
    return mkdir(path, ar -> { });
  }


  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#mkdir}
   * @param path 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem mkdirBlocking(java.lang.String path) { 
    delegate.mkdirBlocking(path);
    return this;
  }

  /**
   * Create the directory represented by <code>path</code>, asynchronously.
   * <p>
   * The new directory will be created with permissions as specified by <code>perms</code>.
   * <p>
   * The permission String takes the form rwxr-x--- as specified
   * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
   * <p>
   * The operation will fail if the directory already exists.
   * @param path path to the file
   * @param perms the permissions string
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem mkdir(java.lang.String path, java.lang.String perms, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.mkdir(path, perms, handler);
    return this;
  }

  /**
   * Create the directory represented by <code>path</code>, asynchronously.
   * <p>
   * The new directory will be created with permissions as specified by <code>perms</code>.
   * <p>
   * The permission String takes the form rwxr-x--- as specified
   * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
   * <p>
   * The operation will fail if the directory already exists.
   * @param path path to the file
   * @param perms the permissions string
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem mkdir(java.lang.String path, java.lang.String perms) {
    return 
mkdir(path, perms, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#mkdir}
   * @param path 
   * @param perms 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem mkdirBlocking(java.lang.String path, java.lang.String perms) { 
    delegate.mkdirBlocking(path, perms);
    return this;
  }

  /**
   * Create the directory represented by <code>path</code> and any non existent parents, asynchronously.
   * <p>
   * The operation will fail if the <code>path</code> already exists but is not a directory.
   * @param path path to the file
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem mkdirs(java.lang.String path, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.mkdirs(path, handler);
    return this;
  }

  /**
   * Create the directory represented by <code>path</code> and any non existent parents, asynchronously.
   * <p>
   * The operation will fail if the <code>path</code> already exists but is not a directory.
   * @param path path to the file
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem mkdirs(java.lang.String path) {
    return 
mkdirs(path, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#mkdirs}
   * @param path 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem mkdirsBlocking(java.lang.String path) { 
    delegate.mkdirsBlocking(path);
    return this;
  }

  /**
   * Create the directory represented by <code>path</code> and any non existent parents, asynchronously.
   * <p>
   * The new directory will be created with permissions as specified by <code>perms</code>.
   * <p>
   * The permission String takes the form rwxr-x--- as specified
   * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
   * <p>
   * The operation will fail if the <code>path</code> already exists but is not a directory.
   * @param path path to the file
   * @param perms the permissions string
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem mkdirs(java.lang.String path, java.lang.String perms, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.mkdirs(path, perms, handler);
    return this;
  }

  /**
   * Create the directory represented by <code>path</code> and any non existent parents, asynchronously.
   * <p>
   * The new directory will be created with permissions as specified by <code>perms</code>.
   * <p>
   * The permission String takes the form rwxr-x--- as specified
   * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
   * <p>
   * The operation will fail if the <code>path</code> already exists but is not a directory.
   * @param path path to the file
   * @param perms the permissions string
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem mkdirs(java.lang.String path, java.lang.String perms) {
    return 
mkdirs(path, perms, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#mkdirs}
   * @param path 
   * @param perms 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem mkdirsBlocking(java.lang.String path, java.lang.String perms) { 
    delegate.mkdirsBlocking(path, perms);
    return this;
  }

  /**
   * Read the contents of the directory specified by <code>path</code>, asynchronously.
   * <p>
   * The result is an array of String representing the paths of the files inside the directory.
   * @param path path to the file
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem readDir(java.lang.String path, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.String>>> handler) { 
    delegate.readDir(path, handler);
    return this;
  }

  /**
   * Read the contents of the directory specified by <code>path</code>, asynchronously.
   * <p>
   * The result is an array of String representing the paths of the files inside the directory.
   * @param path path to the file
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem readDir(java.lang.String path) {
    return 
readDir(path, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#readDir}
   * @param path 
   * @return 
   */
  public java.util.List<java.lang.String> readDirBlocking(java.lang.String path) { 
    java.util.List<java.lang.String> ret = delegate.readDirBlocking(path);
    return ret;
  }

  /**
   * Read the contents of the directory specified by <code>path</code>, asynchronously.
   * <p>
   * The parameter <code>filter</code> is a regular expression. If <code>filter</code> is specified then only the paths that
   * match  @{filter}will be returned.
   * <p>
   * The result is an array of String representing the paths of the files inside the directory.
   * @param path path to the directory
   * @param filter the filter expression
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem readDir(java.lang.String path, java.lang.String filter, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.String>>> handler) { 
    delegate.readDir(path, filter, handler);
    return this;
  }

  /**
   * Read the contents of the directory specified by <code>path</code>, asynchronously.
   * <p>
   * The parameter <code>filter</code> is a regular expression. If <code>filter</code> is specified then only the paths that
   * match  @{filter}will be returned.
   * <p>
   * The result is an array of String representing the paths of the files inside the directory.
   * @param path path to the directory
   * @param filter the filter expression
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem readDir(java.lang.String path, java.lang.String filter) {
    return 
readDir(path, filter, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#readDir}
   * @param path 
   * @param filter 
   * @return 
   */
  public java.util.List<java.lang.String> readDirBlocking(java.lang.String path, java.lang.String filter) { 
    java.util.List<java.lang.String> ret = delegate.readDirBlocking(path, filter);
    return ret;
  }

  /**
   * Reads the entire file as represented by the path <code>path</code> as a , asynchronously.
   * <p>
   * Do not use this method to read very large files or you risk running out of available RAM.
   * @param path path to the file
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem readFile(java.lang.String path, io.vertx.core.Handler<io.vertx.core.AsyncResult<Buffer>> handler) { 
    delegate.readFile(path, new Handler<AsyncResult<io.vertx.core.buffer.Buffer>>() {
      public void handle(AsyncResult<io.vertx.core.buffer.Buffer> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Reads the entire file as represented by the path <code>path</code> as a , asynchronously.
   * <p>
   * Do not use this method to read very large files or you risk running out of available RAM.
   * @param path path to the file
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem readFile(java.lang.String path) {
    delegate.readFile(path); 
    return this;
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#readFile}
   * @param path 
   * @return 
   */
  public Buffer readFileBlocking(java.lang.String path) { 
    return delegate.readFileBlocking(path);
  }

  /**
   * Creates the file, and writes the specified <code>Buffer data</code> to the file represented by the path <code>path</code>,
   * asynchronously.
   * @param path path to the file
   * @param data 
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem writeFile(java.lang.String path, Buffer data, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.writeFile(path, data, handler);
    return this;
  }

  /**
   * Creates the file, and writes the specified <code>Buffer data</code> to the file represented by the path <code>path</code>,
   * asynchronously.
   * @param path path to the file
   * @param data 
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem writeFile(java.lang.String path, Buffer data) {
    delegate.writeFile(path, data);
    return this;
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#writeFile}
   * @param path 
   * @param data 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem writeFileBlocking(java.lang.String path, Buffer data) { 
    delegate.writeFileBlocking(path, data);
    return this;
  }

  /**
   * Open the file represented by <code>path</code>, asynchronously.
   * <p>
   * The file is opened for both reading and writing. If the file does not already exist it will be created.
   * @param path path to the file
   * @param options options describing how the file should be opened
   * @param handler 
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem open(java.lang.String path, io.vertx.core.file.OpenOptions options, io.vertx.core.Handler<io.vertx.core.AsyncResult<AsyncFile>> handler) { 
    delegate.open(path, options, new Handler<AsyncResult<io.vertx.core.file.AsyncFile>>() {
      public void handle(AsyncResult<io.vertx.core.file.AsyncFile> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Open the file represented by <code>path</code>, asynchronously.
   * <p>
   * The file is opened for both reading and writing. If the file does not already exist it will be created.
   * @param path path to the file
   * @param options options describing how the file should be opened
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem open(java.lang.String path, io.vertx.core.file.OpenOptions options) {
    return 
open(path, options, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#open}
   * @param path 
   * @param options 
   * @return 
   */
  public AsyncFile openBlocking(java.lang.String path, io.vertx.core.file.OpenOptions options) { 
    return delegate.openBlocking(path, options);
  }

  /**
   * Creates an empty file with the specified <code>path</code>, asynchronously.
   * @param path path to the file
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createFile(java.lang.String path, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.createFile(path, handler);
    return this;
  }

  /**
   * Creates an empty file with the specified <code>path</code>, asynchronously.
   * @param path path to the file
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createFile(java.lang.String path) {
    return createFile(path, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#createFile}
   * @param path 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem createFileBlocking(java.lang.String path) { 
    delegate.createFileBlocking(path);
    return this;
  }

  /**
   * Creates an empty file with the specified <code>path</code> and permissions <code>perms</code>, asynchronously.
   * @param path path to the file
   * @param perms the permissions string
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createFile(java.lang.String path, java.lang.String perms, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) { 
    delegate.createFile(path, perms, handler);
    return this;
  }

  /**
   * Creates an empty file with the specified <code>path</code> and permissions <code>perms</code>, asynchronously.
   * @param path path to the file
   * @param perms the permissions string
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createFile(java.lang.String path, java.lang.String perms) {
    return 
createFile(path, perms, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#createFile}
   * @param path 
   * @param perms 
   * @return 
   */
  public io.vertx.loom.core.file.FileSystem createFileBlocking(java.lang.String path, java.lang.String perms) { 
    delegate.createFileBlocking(path, perms);
    return this;
  }

  /**
   * Determines whether the file as specified by the path <code>path</code> exists, asynchronously.
   * @param path path to the file
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem exists(java.lang.String path, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Boolean>> handler) { 
    delegate.exists(path, handler);
    return this;
  }

  /**
   * Determines whether the file as specified by the path <code>path</code> exists, asynchronously.
   * @param path path to the file
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem exists(java.lang.String path) {
    return exists(path, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#exists}
   * @param path 
   * @return 
   */
  public boolean existsBlocking(java.lang.String path) { 
    boolean ret = delegate.existsBlocking(path);
    return ret;
  }

  /**
   * Returns properties of the file-system being used by the specified <code>path</code>, asynchronously.
   * @param path path to anywhere on the filesystem
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem fsProps(java.lang.String path, io.vertx.core.Handler<io.vertx.core.AsyncResult<FileSystemProps>> handler) { 
    delegate.fsProps(path, handler);
    return this;
  }

  /**
   * Returns properties of the file-system being used by the specified <code>path</code>, asynchronously.
   * @param path path to anywhere on the filesystem
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem fsProps(java.lang.String path) {
    return fsProps(path, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#fsProps}
   * @param path 
   * @return 
   */
  public FileSystemProps fsPropsBlocking(java.lang.String path) { 
    return delegate.fsPropsBlocking(path);
  }

  /**
   * Creates a new directory in the default temporary-file directory, using the given
   * prefix to generate its name, asynchronously.
   *
   * <p>
   * As with the <code>File.createTempFile</code> methods, this method is only
   * part of a temporary-file facility.A {@link java.lang.Runtime},
   * or the {@link java.io.File} mechanism may be used to delete the directory automatically.
   * </p>
   * @param prefix the prefix string to be used in generating the directory's name; may be <code>null</code>
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createTempDirectory(java.lang.String prefix, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) { 
    delegate.createTempDirectory(prefix, handler);
    return this;
  }

  /**
   * Creates a new directory in the default temporary-file directory, using the given
   * prefix to generate its name, asynchronously.
   *
   * <p>
   * As with the <code>File.createTempFile</code> methods, this method is only
   * part of a temporary-file facility.A {@link java.lang.Runtime},
   * or the {@link java.io.File} mechanism may be used to delete the directory automatically.
   * </p>
   * @param prefix the prefix string to be used in generating the directory's name; may be <code>null</code>
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createTempDirectory(java.lang.String prefix) {
    return createTempDirectory(prefix, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#createTempDirectory}
   * @param prefix 
   * @return 
   */
  public java.lang.String createTempDirectoryBlocking(java.lang.String prefix) { 
    java.lang.String ret = delegate.createTempDirectoryBlocking(prefix);
    return ret;
  }

  /**
   * Creates a new directory in the default temporary-file directory, using the given
   * prefix to generate its name, asynchronously.
   * <p>
   * The new directory will be created with permissions as specified by <code>perms</code>.
   * </p>
   * The permission String takes the form rwxr-x--- as specified
   * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
   *
   * <p>
   * As with the <code>File.createTempFile</code> methods, this method is only
   * part of a temporary-file facility.A {@link java.lang.Runtime},
   * or the {@link java.io.File} mechanism may be used to delete the directory automatically.
   * </p>
   * @param prefix the prefix string to be used in generating the directory's name; may be <code>null</code>
   * @param perms the permissions string
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createTempDirectory(java.lang.String prefix, java.lang.String perms, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) { 
    delegate.createTempDirectory(prefix, perms, handler);
    return this;
  }

  /**
   * Creates a new directory in the default temporary-file directory, using the given
   * prefix to generate its name, asynchronously.
   * <p>
   * The new directory will be created with permissions as specified by <code>perms</code>.
   * </p>
   * The permission String takes the form rwxr-x--- as specified
   * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
   *
   * <p>
   * As with the <code>File.createTempFile</code> methods, this method is only
   * part of a temporary-file facility.A {@link java.lang.Runtime},
   * or the {@link java.io.File} mechanism may be used to delete the directory automatically.
   * </p>
   * @param prefix the prefix string to be used in generating the directory's name; may be <code>null</code>
   * @param perms the permissions string
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createTempDirectory(java.lang.String prefix, java.lang.String perms) {
    return createTempDirectory(prefix, perms, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#createTempDirectory}
   * @param prefix 
   * @param perms 
   * @return 
   */
  public java.lang.String createTempDirectoryBlocking(java.lang.String prefix, java.lang.String perms) { 
    java.lang.String ret = delegate.createTempDirectoryBlocking(prefix, perms);
    return ret;
  }

  /**
   * Creates a new directory in the directory provided by the path <code>path</code>, using the given
   * prefix to generate its name, asynchronously.
   * <p>
   * The new directory will be created with permissions as specified by <code>perms</code>.
   * </p>
   * The permission String takes the form rwxr-x--- as specified
   * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
   *
   * <p>
   * As with the <code>File.createTempFile</code> methods, this method is only
   * part of a temporary-file facility.A {@link java.lang.Runtime},
   * or the {@link java.io.File} mechanism may be used to delete the directory automatically.
   * </p>
   * @param dir the path to directory in which to create the directory
   * @param prefix the prefix string to be used in generating the directory's name; may be <code>null</code>
   * @param perms the permissions string
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createTempDirectory(java.lang.String dir, java.lang.String prefix, java.lang.String perms, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) { 
    delegate.createTempDirectory(dir, prefix, perms, handler);
    return this;
  }

  /**
   * Creates a new directory in the directory provided by the path <code>path</code>, using the given
   * prefix to generate its name, asynchronously.
   * <p>
   * The new directory will be created with permissions as specified by <code>perms</code>.
   * </p>
   * The permission String takes the form rwxr-x--- as specified
   * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
   *
   * <p>
   * As with the <code>File.createTempFile</code> methods, this method is only
   * part of a temporary-file facility.A {@link java.lang.Runtime},
   * or the {@link java.io.File} mechanism may be used to delete the directory automatically.
   * </p>
   * @param dir the path to directory in which to create the directory
   * @param prefix the prefix string to be used in generating the directory's name; may be <code>null</code>
   * @param perms the permissions string
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createTempDirectory(java.lang.String dir, java.lang.String prefix, java.lang.String perms) {
    return createTempDirectory(dir, prefix, perms, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#createTempDirectory}
   * @param dir 
   * @param prefix 
   * @param perms 
   * @return 
   */
  public java.lang.String createTempDirectoryBlocking(java.lang.String dir, java.lang.String prefix, java.lang.String perms) { 
    java.lang.String ret = delegate.createTempDirectoryBlocking(dir, prefix, perms);
    return ret;
  }

  /**
   * Creates a new file in the default temporary-file directory, using the given
   * prefix and suffix to generate its name, asynchronously.
   *
   * <p>
   * As with the <code>File.createTempFile</code> methods, this method is only
   * part of a temporary-file facility.A {@link java.lang.Runtime},
   * or the {@link java.io.File} mechanism may be used to delete the directory automatically.
   * </p>
   * @param prefix the prefix string to be used in generating the directory's name; may be <code>null</code>
   * @param suffix the suffix string to be used in generating the file's name; may be <code>null</code>, in which case "<code>.tmp</code>" is used
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createTempFile(java.lang.String prefix, java.lang.String suffix, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) { 
    delegate.createTempFile(prefix, suffix, handler);
    return this;
  }

  /**
   * Creates a new file in the default temporary-file directory, using the given
   * prefix and suffix to generate its name, asynchronously.
   *
   * <p>
   * As with the <code>File.createTempFile</code> methods, this method is only
   * part of a temporary-file facility.A {@link java.lang.Runtime},
   * or the {@link java.io.File} mechanism may be used to delete the directory automatically.
   * </p>
   * @param prefix the prefix string to be used in generating the directory's name; may be <code>null</code>
   * @param suffix the suffix string to be used in generating the file's name; may be <code>null</code>, in which case "<code>.tmp</code>" is used
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createTempFile(java.lang.String prefix, java.lang.String suffix) {
    return 
createTempFile(prefix, suffix, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#createTempFile}
   * @param prefix 
   * @param suffix 
   * @return 
   */
  public java.lang.String createTempFileBlocking(java.lang.String prefix, java.lang.String suffix) { 
    java.lang.String ret = delegate.createTempFileBlocking(prefix, suffix);
    return ret;
  }

  /**
   * Creates a new file in the directory provided by the path <code>dir</code>, using the given
   * prefix and suffix to generate its name, asynchronously.
   *
   * <p>
   * As with the <code>File.createTempFile</code> methods, this method is only
   * part of a temporary-file facility.A {@link java.lang.Runtime},
   * or the {@link java.io.File} mechanism may be used to delete the directory automatically.
   * </p>
   * @param prefix the prefix string to be used in generating the directory's name; may be <code>null</code>
   * @param suffix the suffix string to be used in generating the file's name; may be <code>null</code>, in which case "<code>.tmp</code>" is used
   * @param perms 
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createTempFile(java.lang.String prefix, java.lang.String suffix, java.lang.String perms, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) { 
    delegate.createTempFile(prefix, suffix, perms, handler);
    return this;
  }

  /**
   * Creates a new file in the directory provided by the path <code>dir</code>, using the given
   * prefix and suffix to generate its name, asynchronously.
   *
   * <p>
   * As with the <code>File.createTempFile</code> methods, this method is only
   * part of a temporary-file facility.A {@link java.lang.Runtime},
   * or the {@link java.io.File} mechanism may be used to delete the directory automatically.
   * </p>
   * @param prefix the prefix string to be used in generating the directory's name; may be <code>null</code>
   * @param suffix the suffix string to be used in generating the file's name; may be <code>null</code>, in which case "<code>.tmp</code>" is used
   * @param perms 
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createTempFile(java.lang.String prefix, java.lang.String suffix, java.lang.String perms) {
    return 
createTempFile(prefix, suffix, perms, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#createTempFile}
   * @param prefix 
   * @param suffix 
   * @param perms 
   * @return 
   */
  public java.lang.String createTempFileBlocking(java.lang.String prefix, java.lang.String suffix, java.lang.String perms) { 
    java.lang.String ret = delegate.createTempFileBlocking(prefix, suffix, perms);
    return ret;
  }

  /**
   * Creates a new file in the directory provided by the path <code>dir</code>, using the given
   * prefix and suffix to generate its name, asynchronously.
   * <p>
   * The new directory will be created with permissions as specified by <code>perms</code>.
   * </p>
   * The permission String takes the form rwxr-x--- as specified
   * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
   *
   * <p>
   * As with the <code>File.createTempFile</code> methods, this method is only
   * part of a temporary-file facility.A {@link java.lang.Runtime},
   * or the {@link java.io.File} mechanism may be used to delete the directory automatically.
   * </p>
   * @param dir the path to directory in which to create the directory
   * @param prefix the prefix string to be used in generating the directory's name; may be <code>null</code>
   * @param suffix the suffix string to be used in generating the file's name; may be <code>null</code>, in which case "<code>.tmp</code>" is used
   * @param perms the permissions string
   * @param handler the handler that will be called on completion
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createTempFile(java.lang.String dir, java.lang.String prefix, java.lang.String suffix, java.lang.String perms, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) { 
    delegate.createTempFile(dir, prefix, suffix, perms, handler);
    return this;
  }

  /**
   * Creates a new file in the directory provided by the path <code>dir</code>, using the given
   * prefix and suffix to generate its name, asynchronously.
   * <p>
   * The new directory will be created with permissions as specified by <code>perms</code>.
   * </p>
   * The permission String takes the form rwxr-x--- as specified
   * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
   *
   * <p>
   * As with the <code>File.createTempFile</code> methods, this method is only
   * part of a temporary-file facility.A {@link java.lang.Runtime},
   * or the {@link java.io.File} mechanism may be used to delete the directory automatically.
   * </p>
   * @param dir the path to directory in which to create the directory
   * @param prefix the prefix string to be used in generating the directory's name; may be <code>null</code>
   * @param suffix the suffix string to be used in generating the file's name; may be <code>null</code>, in which case "<code>.tmp</code>" is used
   * @param perms the permissions string
   * @return a reference to this, so the API can be used fluently
   */
  public io.vertx.loom.core.file.FileSystem createTempFile(java.lang.String dir, java.lang.String prefix, java.lang.String suffix, java.lang.String perms) {
    return createTempFile(dir, prefix, suffix, perms, ar -> { });
  }

  /**
   * Blocking version of {@link io.vertx.loom.core.file.FileSystem#createTempFile}
   * @param dir 
   * @param prefix 
   * @param suffix 
   * @param perms 
   * @return 
   */
  public java.lang.String createTempFileBlocking(java.lang.String dir, java.lang.String prefix, java.lang.String suffix, java.lang.String perms) { 
    java.lang.String ret = delegate.createTempFileBlocking(dir, prefix, suffix, perms);
    return ret;
  }

  public static FileSystem newInstance(io.vertx.core.file.FileSystem arg) {
    return arg != null ? new FileSystem(arg) : null;
  }

}
