package io.vertx.lang.loom;

import static io.vertx.codegen.type.ClassKind.API;
import static io.vertx.codegen.type.ClassKind.ASYNC_RESULT;
import static io.vertx.codegen.type.ClassKind.CLASS_TYPE;
import static io.vertx.codegen.type.ClassKind.FUNCTION;
import static io.vertx.codegen.type.ClassKind.FUTURE;
import static io.vertx.codegen.type.ClassKind.HANDLER;
import static io.vertx.codegen.type.ClassKind.LIST;
import static io.vertx.codegen.type.ClassKind.MAP;
import static io.vertx.codegen.type.ClassKind.OBJECT;
import static io.vertx.codegen.type.ClassKind.SET;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.vertx.codegen.ClassModel;
import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.TypeParamInfo;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.type.TypeVariableInfo;
import io.vertx.lang.rx.AbstractBaseVertxGenerator;

class LoomGenerator extends AbstractBaseVertxGenerator {

  LoomGenerator() {
    super("loom");
    this.name = "loom";
    this.kinds = Collections.singleton("class");
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Arrays.asList(DataObject.class, ModuleGen.class, VertxGen.class);
  }

  @Override
  protected void genReadStream(List<? extends TypeParamInfo> typeParams, PrintWriter writer) {
    // TODO Auto-generated method stub
  }

  @Override
  protected void genImports(ClassModel model, PrintWriter writer) {
    super.genImports(model, writer);
    writer.println("import io.vertx.loom.core.Async;");
  }
  
  @Override
  public String render(ClassModel model, int index, int size, Map<String, Object> session) {
    String output = super.render(model, index, size, session);
    System.out.println(output);
    return output;
  }

  @Override
  protected void genMethods(ClassModel model, MethodInfo method, List<String> cacheDecls, boolean genBody,
      PrintWriter writer) {
    genSimpleMethod("public", model, method, cacheDecls, genBody, writer);
  }
  
  protected String genConvParam(TypeInfo type, MethodInfo method, String expr) {
    ClassKind kind = type.getKind();
    if (isSameType(type, method)) {
      return expr;
    } else if (kind == OBJECT) {
      if (type.isVariable()) {
        String typeArg = genTypeArg((TypeVariableInfo) type, method);
        if (typeArg != null) {
          return typeArg + ".<" + type.getName() + ">unwrap(" + expr + ")";
        }
      }
      return expr;
    } else if (kind == API) {
      return expr + ".getDelegate()";
    } else if (kind == CLASS_TYPE) {
      return "io.vertx.lang." + id() + ".Helper.unwrap(" + expr + ")";
    } else if (type.isParameterized()) {
      ParameterizedTypeInfo parameterizedTypeInfo = (ParameterizedTypeInfo) type;
      if (kind == HANDLER) {
        TypeInfo eventType = parameterizedTypeInfo.getArg(0);
        ClassKind eventKind = eventType.getKind();
        if (eventKind == ASYNC_RESULT) {
          TypeInfo resultType = ((ParameterizedTypeInfo) eventType).getArg(0);
          String resultName = genTypeName(resultType);
          return "new Handler<AsyncResult<" + resultName + ">>() {\n" +
            "      public void handle(AsyncResult<" + resultName + "> ar) {\n" +
            "        Async.async(() -> {\n" +
            "          if (ar.succeeded()) {\n" +
            "            " + expr + ".handle(io.vertx.core.Future.succeededFuture(" + genConvReturn(resultType, method, "ar.result()") + "));\n" +
            "          } else {\n" +
            "            " + expr + ".handle(io.vertx.core.Future.failedFuture(ar.cause()));\n" +
            "          }\n" +
            "        })\n" +
            "      }\n" +
            "    }";
        } else {
          String eventName = genTypeName(eventType);
          return "new Handler<" + eventName + ">() {\n" +
            "      public void handle(" + eventName + " event) {\n" +
            "        Async.async(() -> {\n" +
            "          " + expr + ".handle(" + genConvReturn(eventType, method, "event") + ");\n" +
            "        });\n" +
            "      }\n" +
            "    }";
        }
      } else if (kind == FUNCTION) {
        TypeInfo argType = parameterizedTypeInfo.getArg(0);
        TypeInfo retType = parameterizedTypeInfo.getArg(1);
        String argName = genTypeName(argType);
        String retName = genTypeName(retType);
        return "new Function<" + argName + "," + retName + ">() {\n" +
          "      public " + retName + " apply(" + argName + " arg) {\n" +
          "        " + genParamTypeDecl(retType) + " ret = " + expr + ".apply(" + genConvReturn(argType, method, "arg") + ");\n" +
          "        return " + genConvParam(retType, method, "ret") + ";\n" +
          "      }\n" +
          "    }";
      } else if (kind == LIST || kind == SET) {
        return expr + ".stream().map(elt -> " + genConvParam(parameterizedTypeInfo.getArg(0), method, "elt") + ").collect(Collectors.to" + type.getRaw().getSimpleName() + "())";
      } else if (kind == MAP) {
        return expr + ".entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> " + genConvParam(parameterizedTypeInfo.getArg(1), method, "e.getValue()") + "))";
      } else if (kind == FUTURE) {
        ParameterizedTypeInfo futureType = (ParameterizedTypeInfo) type;
        return expr + ".map(val -> " + genConvParam(futureType.getArg(0), method, "val") + ")";
      }
    }
    return expr;
  }
  
}
