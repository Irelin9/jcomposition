package jcomposition.processor.utils;

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import com.squareup.javapoet.*;
import jcomposition.api.IComposition;

import javax.annotation.processing.ProcessingEnvironment;
import javax.inject.Inject;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompositionUtils {

     public static TypeSpec getCompositionTypeSpec(TypeElement typeElement, ProcessingEnvironment env) {
         env.getMessager().printMessage(Diagnostic.Kind.NOTE, "getCompositionTypeSpec typeElement: " + typeElement);
         TypeSpec.Builder builder = TypeSpec.classBuilder("Composition");
         List<FieldSpec> fieldSpecs = new ArrayList<FieldSpec>();
         if (typeElement.getSimpleName().toString().equals("IPresenter")) {
             // shareProtected
             HashMap<TypeSpec, FieldSpec> subtypesSpecs = getSubtypesSpecs(typeElement, env);
             for (Map.Entry<TypeSpec, FieldSpec> entry : subtypesSpecs.entrySet()) {
                 builder.addType(entry.getKey());
                 builder.addField(entry.getValue());
                 env.getMessager().printMessage(Diagnostic.Kind.NOTE
                         , "Type: " + entry.getKey());
                 env.getMessager().printMessage(Diagnostic.Kind.NOTE
                         , "Field: " + entry.getValue());
             }
         } else {
             fieldSpecs = getFieldsSpecs(typeElement, env);
         }

         builder.addModifiers(Modifier.FINAL, Modifier.PROTECTED);

         if (fieldSpecs.size() > 0) {
             if (TypeElementUtils.hasInheritedInjectionAnnotation(typeElement)) {
                 String compositionName = TypeElementUtils.getCompositionName(typeElement, env.getElementUtils());

                 builder.addMethod(MethodSpec.constructorBuilder()
                         .addStatement(compositionName + ".this.onInject(this)")
                         .addModifiers(Modifier.PRIVATE)
                         .build());
             }

             builder.addFields(fieldSpecs);
         }
         return builder.build();
     }

    private static HashMap<TypeSpec, FieldSpec> getSubtypesSpecs(TypeElement typeElement, ProcessingEnvironment env) {
        HashMap<TypeSpec, FieldSpec> specs = new HashMap<TypeSpec, FieldSpec>();
        for (TypeMirror typeInterface : typeElement.getInterfaces()) {
            TypeElement typeInterfaceElement = MoreTypes.asTypeElement(typeInterface);
            TypeElement bindClassType = TypeElementUtils.getBindClassType(typeInterfaceElement, env);
            env.getMessager().printMessage(Diagnostic.Kind.NOTE
                    , "bindClassType: " + bindClassType.getSimpleName());

            List<ExecutableElement> elements = new ArrayList<ExecutableElement>();

            TypeSpec.Builder typeBuilder = TypeSpec.classBuilder("Composition_" + bindClassType.getSimpleName())
                    .addModifiers(Modifier.FINAL, Modifier.PRIVATE)
                    .superclass(TypeName.get(bindClassType.asType()))
                    .addMethods(getProtectedMethodsSpecs(typeElement, elements));
            TypeSpec typeSpec = typeBuilder.build();

            FieldSpec.Builder specBuilder = FieldSpec.builder(ClassName.bestGuess(typeSpec.name), "composition_" + bindClassType.getSimpleName())
                    .addModifiers(Modifier.PROTECTED)
                    .initializer("null");

            specs.put(typeBuilder.build(), specBuilder.build());
        }
        return specs;
    }

    private static List<MethodSpec> getProtectedMethodsSpecs(TypeElement typeElement, List<ExecutableElement> elements) {
        List<MethodSpec> result = new ArrayList<MethodSpec>();
        for (ExecutableElement element : elements) {
            List<MethodSpec> spec = getProtectedMethodSpec(element, typeElement);

            if (!spec.isEmpty()) {
                result.addAll(spec);
            }
        }
        return result;
    }

    private static List<MethodSpec> getProtectedMethodSpec(ExecutableElement executableElement, TypeElement typeElement) {
        List<MethodSpec> result = new ArrayList<MethodSpec>();
        //DeclaredType declaredType = MoreTypes.asDeclared(typeElement.asType());

        MethodSpec.Builder builder = MethodSpec.overriding(executableElement);
        String statement = getProtectedExecutableStatement(executableElement);
        if (statement != null) {
            builder.addStatement(statement);
            result.add(builder.build());
        }

        MethodSpec.Builder sBuilder = MethodSpec.methodBuilder("_super_" + executableElement.getSimpleName().toString())
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(executableElement.getReturnType()));
        String sStatement = getSuperProtectedExecutableStatement(executableElement);
        if (sStatement != null) {
            sBuilder.addStatement(sStatement);
            result.add(sBuilder.build());
        }

        return result;
    }

    private static String getProtectedExecutableStatement(ExecutableElement executableElement) {
        StringBuilder builder = new StringBuilder();

        if (executableElement.getReturnType().getKind() != TypeKind.VOID) {
            builder.append("return ");
        }

        builder.append("PresenterGenerated" + ".this." + executableElement.getSimpleName() + "(" + getParametersScope(executableElement) + ")");

        return builder.toString();
    }

    private static String getSuperProtectedExecutableStatement(ExecutableElement executableElement) {
        StringBuilder builder = new StringBuilder();

        if (executableElement.getReturnType().getKind() != TypeKind.VOID) {
            builder.append("return ");
        }

        builder.append("super." + executableElement.getSimpleName() + "(" + getParametersScope(executableElement) + ")");

        return builder.toString();
    }

    private static String getParametersScope(ExecutableElement element) {
        StringBuilder paramBuilder = new StringBuilder();
        List<? extends VariableElement> parameters = element.getParameters();

        for (int i = 0; i < parameters.size(); i++) {
            VariableElement variableElement = parameters.get(i);

            paramBuilder.append(variableElement.getSimpleName());

            if (i < parameters.size() - 1) {
                paramBuilder.append(", ");
            }
        }

        return paramBuilder.toString();
    }

    private static List<FieldSpec> getFieldsSpecs(TypeElement typeElement, ProcessingEnvironment env) {
        List<FieldSpec> specs = new ArrayList<FieldSpec>();

        for (TypeMirror typeInterface : typeElement.getInterfaces()) {
            TypeElement typeInterfaceElement = MoreTypes.asTypeElement(typeInterface);
            TypeElement bindClassType = TypeElementUtils.getBindClassType(typeInterfaceElement, env);

            if (bindClassType == null)
                continue;

            boolean useInjection = TypeElementUtils.hasUseInjectionAnnotation(typeInterfaceElement)
                    || TypeElementUtils.hasUseInjectionAnnotation(bindClassType);

            List<?> typeArguments = MoreTypes.asDeclared(typeInterface).getTypeArguments();
            TypeMirror[] typeMirrors = new TypeMirror[typeArguments.size()];

            DeclaredType declaredType = env.getTypeUtils().getDeclaredType(bindClassType, typeArguments.toArray(typeMirrors));

            TypeName typeName = TypeName.get(declaredType);
            String initializer = useInjection ? "null" : "new " + typeName.toString() + "()";

            FieldSpec.Builder specBuilder = FieldSpec.builder(typeName, "composition_" + bindClassType.getSimpleName())
                    .addModifiers(Modifier.PROTECTED)
                    .initializer(initializer);

            if (useInjection) {
                specBuilder.addAnnotation(Inject.class);
            } else {
                specBuilder.addModifiers(Modifier.FINAL);
            }

            specs.add(specBuilder.build());
        }

        return specs;
    }

    public static ClassName getNestedCompositionClassName(TypeElement typeElement, Elements utils) {
        String name = TypeElementUtils.getCompositionName(typeElement, utils);
        ClassName nested = ClassName.get(MoreElements.getPackage(typeElement).toString(), name, "Composition");

        return nested;
    }

    public static TypeName getInheritedCompositionInterface(TypeElement typeElement, ProcessingEnvironment env) {
        ClassName composition = ClassName.get(IComposition.class);
        ClassName nested = getNestedCompositionClassName(typeElement, env.getElementUtils());

        return ParameterizedTypeName.get(composition, nested);
    }

    public static FieldSpec getCompositeFieldSpec(TypeElement typeElement) {
        TypeName compositionTypeName = TypeVariableName.get("Composition");

        return FieldSpec.builder(compositionTypeName, "_composition")
                .addModifiers(Modifier.FINAL, Modifier.PRIVATE)
                .initializer("new " + compositionTypeName.toString()  +"()")
                .build();
    }

    public static MethodSpec getCompositeMethodSpec(TypeElement typeElement, ProcessingEnvironment env) {
        return MethodSpec.methodBuilder("getComposition")
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                .returns(TypeVariableName.get("Composition"))
                .addAnnotation(Override.class)
                .addStatement("return this._composition")
                .build();
    }
}
