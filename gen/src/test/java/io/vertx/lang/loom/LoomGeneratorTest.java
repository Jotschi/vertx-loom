package io.vertx.lang.loom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import io.vertx.codegen.ClassModel;
import io.vertx.ext.web.Route;

public class LoomGeneratorTest {

  @Test
  public void testWhitelistCheck() {
    ClassModel model1 = mock(ClassModel.class);
    when(model1.getFqn()).thenReturn(Route.class.getName());
    assertTrue("The provided model class should be supported.", new LoomGenerator().checkForLoomSupport(model1));
  }

  @Test
  public void testWhitelistCheckFailed() {
    ClassModel model1 = mock(ClassModel.class);
    when(model1.getFqn()).thenReturn(Object.class.getName());
    assertFalse("The provided model class should not be supported.", new LoomGenerator().checkForLoomSupport(model1));
  }
}
