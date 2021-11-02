package io.vertx.lang.loom;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import io.vertx.codegen.CodeGenProcessor;
import io.vertx.codegen.Compiler;
import io.vertx.ext.web.Route;

public class LoomGeneratorTest {

  private static File testDir;

  @Rule
  public final TestName name = new TestName();

  @Before
  public void before() throws Exception {
    int count = 0;
    while (true) {
      String suffix = "testgen_" + name.getMethodName();
      if (count > 0) {
        suffix += "-" + count;
      }
      count++;
      testDir = new File(new File("target").getAbsoluteFile(), suffix);
      if (!testDir.exists()) {
        assertTrue(testDir.mkdir());
        break;
      }
    }
  }

  private void assertCompile(String gen, Class... classes) throws Exception {
    DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
    Compiler compiler = new Compiler(new CodeGenProcessor(), collector);
    compiler.addOption("-Acodegen.generators=" + gen);
    compiler.addOption("-Acodegen.output=" + testDir.getAbsolutePath());
    boolean result = compiler.compile(classes);
    for (Diagnostic<? extends JavaFileObject> diag : collector.getDiagnostics()) {
      System.err.println(diag.getPosition() + ":" + diag.getColumnNumber() + " @ " + diag.getMessage(Locale.ENGLISH));
      System.err.println(diag.toString());
    }
    if (!result) {
      fail("Compilation failed");
    }
  }

  @Test
  public void testRoute() throws Exception {
    assertCompile("loom", Route.class);
  }

}
