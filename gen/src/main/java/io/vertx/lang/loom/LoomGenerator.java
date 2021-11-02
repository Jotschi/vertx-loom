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
import static java.util.stream.Collectors.joining;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.vertx.codegen.ClassModel;
import io.vertx.codegen.ConstantInfo;
import io.vertx.codegen.Helper;
import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.TypeParamInfo;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ClassTypeInfo;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.type.TypeVariableInfo;
import io.vertx.lang.rx.AbstractBaseVertxGenerator;

class LoomGenerator extends AbstractBaseVertxGenerator {

  LoomGenerator() {
    super("loom");
    this.kinds = Collections.singleton("class");
    this.name = "Loom";
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
//    System.out.println(output);
    return output;
  }

  @Override
  protected void genMethods(ClassModel model, MethodInfo method, List<String> cacheDecls, boolean genBody,
      PrintWriter writer) {
    genSimpleMethod("public", model, method, cacheDecls, genBody, writer);
  }

  @Override
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
            "        io.vertx.loom.core.Async.async(() -> {\n" +
            "          if (ar.succeeded()) {\n" +
            "            " + expr + ".handle(io.vertx.core.Future.succeededFuture(" + genConvReturn(resultType, method, "ar.result()") + "));\n" +
            "          } else {\n" +
            "            " + expr + ".handle(io.vertx.core.Future.failedFuture(ar.cause()));\n" +
            "          }\n" +
            "        });\n" +
            "      }\n" +
            "    }";
        } else {
          String eventName = genTypeName(eventType);
          return "new Handler<" + eventName + ">() {\n" +
            "      public void handle(" + eventName + " event) {\n" +
            "        io.vertx.loom.core.Async.async(() -> {\n" +
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

  @Override
  protected void generateClassBody(ClassModel model, String constructor, PrintWriter writer) {
    ClassTypeInfo type = model.getType();
    String simpleName = type.getSimpleName();
    if (model.isConcrete()) {
      writer.print("  public static final TypeArg<");
      writer.print(simpleName);
      writer.print("> __TYPE_ARG = new TypeArg<>(");
      writer.print("    obj -> new ");
      writer.print(simpleName);
      writer.print("((");
      writer.print(type.getName());
      writer.println(") obj),");
      writer.print("    ");
      writer.print(simpleName);
      writer.println("::getDelegate");
      writer.println("  );");
      writer.println();
    }
    writer.print("  private final ");
    writer.print(Helper.getNonGenericType(model.getIfaceFQCN()));
    List<TypeParamInfo.Class> typeParams = model.getTypeParams();
    if (typeParams.size() > 0) {
      writer.print(typeParams.stream().map(TypeParamInfo.Class::getName).collect(joining(",", "<", ">")));
    }
    writer.println(" delegate;");

    for (TypeParamInfo.Class typeParam : typeParams) {
      writer.print("  public final TypeArg<");
      writer.print(typeParam.getName());
      writer.print("> __typeArg_");
      writer.print(typeParam.getIndex());
      writer.println(";");
    }
    writer.println("  ");

    writer.print("  public ");
    writer.print(constructor);
    writer.print("(");
    writer.print(Helper.getNonGenericType(model.getIfaceFQCN()));
    writer.println(" delegate) {");

    if (model.isConcrete() && model.getConcreteSuperType() != null) {
      writer.println("    super(delegate);");
    }
    writer.println("    this.delegate = delegate;");
    for (TypeParamInfo.Class typeParam : typeParams) {
      writer.print("    this.__typeArg_");
      writer.print(typeParam.getIndex());
      writer.print(" = TypeArg.unknown();");
    }
    writer.println("  }");
    writer.println();

    // Object constructor
    writer.print("  public ");
    writer.print(constructor);
    writer.print("(Object delegate");
    for (TypeParamInfo.Class typeParam : typeParams) {
      writer.print(", TypeArg<");
      writer.print(typeParam.getName());
      writer.print("> typeArg_");
      writer.print(typeParam.getIndex());
    }
    writer.println(") {");
    if (model.isConcrete() && model.getConcreteSuperType() != null) {
      // This is incorrect it will not pass the generic type in some case
      // we haven't yet ran into that bug
      writer.print("    super((");
      writer.print(Helper.getNonGenericType(model.getIfaceFQCN()));
      writer.println(")delegate);");
    }
    writer.print("    this.delegate = (");
    writer.print(Helper.getNonGenericType(model.getIfaceFQCN()));
    writer.println(")delegate;");
    for (TypeParamInfo.Class typeParam : typeParams) {
      writer.print("    this.__typeArg_");
      writer.print(typeParam.getIndex());
      writer.print(" = typeArg_");
      writer.print(typeParam.getIndex());
      writer.println(";");
    }
    writer.println("  }");
    writer.println();

    writer.print("  public ");
    writer.print(type.getName());
    writer.println(" getDelegate() {");
    writer.println("    return delegate;");
    writer.println("  }");
    writer.println();

    List<MethodInfo> methods = new ArrayList<>();
    methods.addAll(model.getMethods());
    methods.addAll(model.getAnyJavaTypeMethods());
    int count = 0;
    for (MethodInfo method : methods) {
      TypeInfo returnType = method.getReturnType();
      if (returnType instanceof ParameterizedTypeInfo) {
        ParameterizedTypeInfo parameterizedType = (ParameterizedTypeInfo)returnType;
        List<TypeInfo> typeArgs = parameterizedType.getArgs();
        Map<TypeInfo, String> typeArgMap = new HashMap<>();
        for (TypeInfo typeArg : typeArgs) {
          if (typeArg.getKind() == API && !containsTypeVariableArgument(typeArg)) {
            String typeArgRef = "TYPE_ARG_" + count++;
            typeArgMap.put(typeArg, typeArgRef);
            genTypeArgDecl(typeArg, method, typeArgRef, writer);
          }
        }
        methodTypeArgMap.put(method, typeArgMap);
      }
    }
    // Cosmetic space
    if (methodTypeArgMap.size() > 0) {
      writer.println();
    }

    List<String> cacheDecls = new ArrayList<>();
    for (MethodInfo method : methods) {
      genMethods(model, method, cacheDecls, true, writer);
    }

    for (ConstantInfo constant : model.getConstants()) {
      genConstant(model, constant, writer);
    }

    for (String cacheDecl : cacheDecls) {
      writer.print("  ");
      writer.print(cacheDecl);
      writer.println(";");
    }
  }  
}
