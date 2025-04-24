package com.github.rccookie.interpreter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.github.rccookie.interpreter.methods.*;
import com.github.rccookie.interpreter.methods.Void;
import com.github.rccookie.interpreter.tokenizer.Token;
import com.github.rccookie.interpreter.tokenizer.TokenType;
import com.github.rccookie.interpreter.tokenizer.Tokenizer;
import com.github.rccookie.util.Console;

public class Interpreter {



    static final File COMPILER_ROOT = new File("/java");



    private String imports = "import com.github.rccookie.interpreter.*;";



    public Interpreter addImport(String importTarget) {
        return addImports(importTarget);
    }

    public Interpreter addImports(String... importTargets) {
        for(String importTarget : importTargets)
            imports += "import " + Objects.requireNonNull(importTarget) + ";";
        return this;
    }



    public Void asVoid(String codeBlock) throws InterpretationException {
        String classCode = "package interpretedstuff;public class VoidImpl implements com.github.rccookie.interpreter.methods.Void{public void run(){" + codeBlock + "}}";
        try {
            return interpreteClass(classCode, Void.class, true).getConstructor().newInstance();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    public <I> Void1<I> asVoid(String codeBlock, Class<I> argClass) throws InterpretationException {
        return asVoid(codeBlock, argClass, "arg");
    }

    @SuppressWarnings("unchecked")
    public <I> Void1<I> asVoid(String codeBlock, Class<I> argClass, String argName) throws InterpretationException {
        String classCode = "package interpretedstuff;public class VoidImpl implements com.github.rccookie.interpreter.methods.Void1<"
                           + argClass.getName()
                           + ">{public void run(" + argClass.getName() + " " + argName
                           + "){" + codeBlock + "}}";
        try {
            return interpreteClass(classCode, Void1.class, true).getConstructor().newInstance();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    public <I1, I2> Void2<I1, I2> asVoid(String codeBlock, Class<I1> arg1Class, Class<I2> arg2Class) throws InterpretationException {
        return asVoid(codeBlock, arg1Class, "arg1", arg2Class, "arg2");
    }

    @SuppressWarnings("unchecked")
    public <I1, I2> Void2<I1, I2> asVoid(String codeBlock, Class<I1> arg1Class, String arg1Name, Class<I2> arg2Class, String arg2Name) throws InterpretationException {
        String classCode = "package interpretedstuff;public class VoidImpl implements com.github.rccookie.interpreter.methods.Void2<"
                           + arg1Class.getName() + "," + arg2Class.getName() +
                           ">{public void run(" + arg1Class.getName() + " " + arg1Name
                           + "," + arg2Class.getName() + " " + arg2Name +
                           "){" + codeBlock + "}}";
        try {
            return interpreteClass(classCode, Void2.class, true).getConstructor().newInstance();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    public <I1, I2, I3> Void3<I1, I2, I3> asVoid(String codeBlock,
                                                 Class<I1> arg1Class,
                                                 Class<I2> arg2Class,
                                                 Class<I3> arg3Class) throws InterpretationException {
        return asVoid(codeBlock, arg1Class, "arg1", arg2Class, "arg2", arg3Class, "arg3");
    }

    @SuppressWarnings("unchecked")
    public <I1, I2, I3> Void3<I1, I2, I3> asVoid(String codeBlock,
                                                 Class<I1> arg1Class, String arg1Name,
                                                 Class<I2> arg2Class, String arg2Name,
                                                 Class<I3> arg3Class, String arg3Name) throws InterpretationException {
        String classCode = "package interpretedstuff;public class VoidImpl implements com.github.rccookie.interpreter.methods.Void3<"
                           + arg1Class.getName() + "," + arg2Class.getName() + "," + arg3Class.getName()
                           + ">{public void run(" + arg1Class.getName() + " " + arg1Name
                           + "," + arg2Class.getName() + " " + arg2Name
                           + "," + arg3Class.getName() + " " + arg3Name
                           + "){" + codeBlock + "}}";
        try {
            return interpreteClass(classCode, Void3.class, true).getConstructor().newInstance();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    public <I1, I2, I3, I4> Void4<I1, I2, I3, I4> asVoid(String codeBlock,
                                                 Class<I1> arg1Class,
                                                 Class<I2> arg2Class,
                                                 Class<I3> arg3Class,
                                                 Class<I4> arg4Class) throws InterpretationException {
        return asVoid(codeBlock, arg1Class, "arg1", arg2Class, "arg2", arg3Class, "arg3", arg4Class, "arg4");
    }

    @SuppressWarnings("unchecked")
    public <I1, I2, I3, I4> Void4<I1, I2, I3, I4> asVoid(String codeBlock,
                                                 Class<I1> arg1Class, String arg1Name,
                                                 Class<I2> arg2Class, String arg2Name,
                                                 Class<I3> arg3Class, String arg3Name,
                                                 Class<I4> arg4Class, String arg4Name) throws InterpretationException {
        String classCode = "package interpretedstuff;public class VoidImpl implements com.github.rccookie.interpreter.methods.Void4<"
                           + arg1Class.getName() + "," + arg2Class.getName() + "," + arg3Class.getName() + "," + arg4Class.getName()
                           + ">{public void run(" + arg1Class.getName() + " " + arg1Name
                           + "," + arg2Class.getName() + " " + arg2Name
                           + "," + arg3Class.getName() + " " + arg3Name
                           + "," + arg4Class.getName() + " " + arg4Name
                           + "){" + codeBlock + "}}";
        try {
            return interpreteClass(classCode, Void4.class, true).getConstructor().newInstance();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    public <I1, I2, I3, I4, I5> Void5<I1, I2, I3, I4, I5> asVoid(String codeBlock,
                                                 Class<I1> arg1Class,
                                                 Class<I2> arg2Class,
                                                 Class<I3> arg3Class,
                                                 Class<I4> arg4Class,
                                                 Class<I5> arg5Class) throws InterpretationException {
        return asVoid(codeBlock, arg1Class, "arg1", arg2Class, "arg2", arg3Class, "arg3", arg4Class, "arg4", arg5Class, "arg5");
    }

    @SuppressWarnings("unchecked")
    public <I1, I2, I3, I4, I5> Void5<I1, I2, I3, I4, I5> asVoid(String codeBlock,
                                                 Class<I1> arg1Class, String arg1Name,
                                                 Class<I2> arg2Class, String arg2Name,
                                                 Class<I3> arg3Class, String arg3Name,
                                                 Class<I4> arg4Class, String arg4Name,
                                                 Class<I5> arg5Class, String arg5Name) throws InterpretationException {
        String classCode = "package interpretedstuff;public class VoidImpl implements com.github.rccookie.interpreter.methods.Void5<"
                           + arg1Class.getName() + "," + arg2Class.getName() + "," + arg3Class.getName() + "," + arg4Class.getName() + "," + arg5Class.getName()
                           + ">{public void run("+ arg1Class.getName() + " " + arg1Name
                           + "," + arg2Class.getName() + " " + arg2Name
                           + "," + arg3Class.getName() + " " + arg3Name
                           + "," + arg4Class.getName() + " " + arg4Name
                           + "," + arg5Class.getName() + " " + arg5Name
                           + "){" + codeBlock + "}}";
        try {
            return interpreteClass(classCode, Void5.class, true).getConstructor().newInstance();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }





    @SuppressWarnings("unchecked")
    public <R> Method<R> asMethod(String codeBlock, Class<R> returnClass) throws InterpretationException {
        String classCode = "package interpretedstuff;public class MethodImpl implements com.github.rccookie.interpreter.methods.Method<"
                            + returnClass.getName()
                            + ">{public " + returnClass.getName() + " run(){" + codeBlock + "}}";
        try {
            return interpreteClass(classCode, Method.class, true).getConstructor().newInstance();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    public <I, R> Method1<I, R> asMethod(String codeBlock,
                                                 Class<I> argClass,
                                                 Class<R> returnClass) throws InterpretationException {
        return asMethod(codeBlock, argClass, "arg", returnClass);
    }

    @SuppressWarnings("unchecked")
    public <I1, R> Method1<I1, R> asMethod(String codeBlock,
                                                 Class<I1> argClass, String argName,
                                                 Class<R> returnClass) throws InterpretationException {
        String classCode = "package interpretedstuff;public class MethodImpl implements com.github.rccookie.interpreter.methods.Method1<"
                           + argClass.getName() + "," + returnClass.getName()
                           + ">{public " + returnClass.getName() + " run("+ argClass.getName() + " " + argName
                           + "){" + codeBlock + "}}";
        try {
            return interpreteClass(classCode, Method1.class, true).getConstructor().newInstance();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    public <I1, I2, R> Method2<I1, I2, R> asMethod(String codeBlock,
                                                 Class<I1> arg1Class,
                                                 Class<I2> arg2Class,
                                                 Class<R> returnClass) throws InterpretationException {
        return asMethod(codeBlock, arg1Class, "arg1", arg2Class, "arg2", returnClass);
    }

    @SuppressWarnings("unchecked")
    public <I1, I2, R> Method2<I1, I2, R> asMethod(String codeBlock,
                                                 Class<I1> arg1Class, String arg1Name,
                                                 Class<I2> arg2Class, String arg2Name,
                                                 Class<R> returnClass) throws InterpretationException {
        String classCode = "package interpretedstuff;public class MethodImpl implements com.github.rccookie.interpreter.methods.Method2<"
                           + arg1Class.getName() + "," + arg2Class.getName() + "," + returnClass.getName()
                           + ">{public " + returnClass.getName() + " run("+ arg1Class.getName() + " " + arg1Name
                           + "," + arg2Class.getName() + " " + arg2Name
                           + "){" + codeBlock + "}}";
        try {
            return interpreteClass(classCode, Method2.class, true).getConstructor().newInstance();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    public <I1, I2, I3, R> Method3<I1, I2, I3, R> asMethod(String codeBlock,
                                                 Class<I1> arg1Class,
                                                 Class<I2> arg2Class,
                                                 Class<I3> arg3Class,
                                                 Class<R> returnClass) throws InterpretationException {
        return asMethod(codeBlock, arg1Class, "arg1", arg2Class, "arg2", arg3Class, "arg3", returnClass);
    }

    @SuppressWarnings("unchecked")
    public <I1, I2, I3, R> Method3<I1, I2, I3, R> asMethod(String codeBlock,
                                                 Class<I1> arg1Class, String arg1Name,
                                                 Class<I2> arg2Class, String arg2Name,
                                                 Class<I3> arg3Class, String arg3Name,
                                                 Class<R> returnClass) throws InterpretationException {
        String classCode = "package interpretedstuff;public class MethodImpl implements com.github.rccookie.interpreter.methods.Method3<"
                           + arg1Class.getName() + "," + arg2Class.getName() + "," + arg3Class.getName() + "," + returnClass.getName()
                           + ">{public " + returnClass.getName() + " run("+ arg1Class.getName() + " " + arg1Name
                           + "," + arg2Class.getName() + " " + arg2Name
                           + "," + arg3Class.getName() + " " + arg3Name
                           + "){" + codeBlock + "}}";
        try {
            return interpreteClass(classCode, Method3.class, true).getConstructor().newInstance();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    public <I1, I2, I3, I4, R> Method4<I1, I2, I3, I4, R> asMethod(String codeBlock,
                                                 Class<I1> arg1Class,
                                                 Class<I2> arg2Class,
                                                 Class<I3> arg3Class,
                                                 Class<I4> arg4Class,
                                                 Class<R> returnClass) throws InterpretationException {
        return asMethod(codeBlock, arg1Class, "arg1", arg2Class, "arg2", arg3Class, "arg3", arg4Class, "arg4", returnClass);
    }

    @SuppressWarnings("unchecked")
    public <I1, I2, I3, I4, R> Method4<I1, I2, I3, I4, R> asMethod(String codeBlock,
                                                 Class<I1> arg1Class, String arg1Name,
                                                 Class<I2> arg2Class, String arg2Name,
                                                 Class<I3> arg3Class, String arg3Name,
                                                 Class<I4> arg4Class, String arg4Name,
                                                 Class<R> returnClass) throws InterpretationException {
        String classCode = "package interpretedstuff;public class MethodImpl implements com.github.rccookie.interpreter.methods.Method4<"
                           + arg1Class.getName() + "," + arg2Class.getName() + "," + arg3Class.getName() + "," + arg4Class.getName() + "," + returnClass.getName()
                           + ">{public " + returnClass.getName() + " run("+ arg1Class.getName() + " " + arg1Name
                           + "," + arg2Class.getName() + " " + arg2Name
                           + "," + arg3Class.getName() + " " + arg3Name
                           + "," + arg4Class.getName() + " " + arg4Name
                           + "){" + codeBlock + "}}";
        try {
            return interpreteClass(classCode, Method4.class, true).getConstructor().newInstance();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    public <I1, I2, I3, I4, I5, R> Method5<I1, I2, I3, I4, I5, R> asMethod(String codeBlock,
                                                 Class<I1> arg1Class,
                                                 Class<I2> arg2Class,
                                                 Class<I3> arg3Class,
                                                 Class<I4> arg4Class,
                                                 Class<I5> arg5Class,
                                                 Class<R> returnClass) throws InterpretationException {
        return asMethod(codeBlock, arg1Class, "arg1", arg2Class, "arg2", arg3Class, "arg3", arg4Class, "arg4", arg5Class, "arg5", returnClass);
    }

    @SuppressWarnings("unchecked")
    public <I1, I2, I3, I4, I5, R> Method5<I1, I2, I3, I4, I5, R> asMethod(String codeBlock,
                                                 Class<I1> arg1Class, String arg1Name,
                                                 Class<I2> arg2Class, String arg2Name,
                                                 Class<I3> arg3Class, String arg3Name,
                                                 Class<I4> arg4Class, String arg4Name,
                                                 Class<I5> arg5Class, String arg5Name,
                                                 Class<R> returnClass) throws InterpretationException {
        String classCode = "package interpretedstuff;public class MethodImpl implements com.github.rccookie.interpreter.methods.Method5<"
                           + arg1Class.getName() + "," + arg2Class.getName() + "," + arg3Class.getName() + "," + arg4Class.getName() + "," + arg5Class.getName() + "," + returnClass.getName()
                           + ">{public " + returnClass.getName() + " run("+ arg1Class.getName() + " " + arg1Name
                           + "," + arg2Class.getName() + " " + arg2Name
                           + "," + arg3Class.getName() + " " + arg3Name
                           + "," + arg4Class.getName() + " " + arg4Name
                           + "," + arg5Class.getName() + " " + arg5Name
                           + "){" + codeBlock + "}}";
        try {
            return interpreteClass(classCode, Method5.class, true).getConstructor().newInstance();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    /**
     * Runs the given code from an unspecified class without any parameters and no return value.
     * The code may contain {@code return;} statements.
     * 
     * @param codeBlock The code to run
     */
    public void runStaticScript(String codeBlock) throws InterpretationException {
        String classCode = "package interpretedStuff;public final class StaticBlockRunner{public static void run(){" + codeBlock + "}}";
        try {
            interpreteClass(classCode, true).getMethod("run").invoke(null);
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <R> R runStaticScript(String codeBlock, Class<R> returnType) throws InterpretationException {
        
        String classCode = "package interpretedStuff;public final class StaticBlockRunner{public static " + returnType.getName() + " run(){" + codeBlock + "}}";
        try {
            return (R)interpreteClass(classCode, true).getMethod("run").invoke(null);
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    /**
     * Intended for instantiation of objects. For example, to get a new {@code java.lang.Object},
     * the code string to pass has to look something like this: {@code "new Object()"}.
     * 
     * @param <R> The type of object returned
     * @param code A single-line statement that returnes something of type {@code R}, without 
     *             {@code "returns"} or a semicolon at the end
     * @param returnType The class of the type of object to be returned
     * @return Whatever was generated by the given code
     * @throws InterpretationException If the syntax was incorrect, or an IOException was thrown
     *                                 during the file generation / compilation
     */
    public <R> R get(String code, Class<R> returnType) throws InterpretationException {
        return asMethod("return " + code + ";", returnType).run();
    }

    Object get(String code, String pkg) throws InterpretationException {
        String classCode = "package " + pkg + ";public class MethodImpl implements com.github.rccookie.interpreter.methods.Method<Object>{public Object run(){return " + code + ";}}";
        try {
            return interpreteClass(classCode, Method.class, false).getConstructor().newInstance().run();
        } catch(Exception e) {
            throw new InterpretationException(e);
        }
    }



    private Set<File> allCompiledFiles = new HashSet<>();

    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> interpreteClass(String sourceCode, Class<T> superClass, boolean isTempClass) throws InterpretationException {
        return ((Class<T>)interpreteClass(sourceCode, isTempClass));
    }

    private URLClassLoader loader;
    {
        nextLoader();
    }

    public void nextLoader() {
        try {
            loader = new URLClassLoader(new URL[] {COMPILER_ROOT.toURI().toURL()});
        } catch(Exception e) { }
        Console.mapDebug("Next class loader", loader);
    }

    public Class<?> interpreteClass(String sourceCode, boolean isTempClass) throws InterpretationException {
        try {
            Console.printStackTrace();
            String pkg;
            String clsName;

            PeekIterator<Token> iterator = new PeekIterator<>(Tokenizer.streamCode(sourceCode).iterator());

            if(iterator.peek() == Token.PACKAGE)
                pkg = iterator.stream().skip(1).takeWhile(t -> t != Token.STATEMENT_END).map(Token::toString).collect(Collectors.joining());
            else pkg = "";

            iterator.stream().anyMatch(t -> t.is(TokenType.CLASS_TYPE));
            clsName = iterator.next().toString();

            Console.mapDebug("Package", "'{}'", pkg);
            Console.mapDebug("Class", "'{}'", clsName);
            Console.debug("");
            Console.mapDebug("Default imports", imports);

            sourceCode = addDefaultImports(sourceCode);

            Console.debug("");
            Console.mapDebug("Source code", sourceCode);
            Console.debug("");

            // Save source in .java file.
            File sourceFile = new File(COMPILER_ROOT, pkg.replace(".", "/") + "/" + clsName + ".java");
            sourceFile.getParentFile().mkdirs();
            Files.write(sourceFile.toPath(), sourceCode.getBytes(StandardCharsets.UTF_8));
            Set<File> files = new HashSet<>(allCompiledFiles);
            files.add(sourceFile);
            if(!isTempClass) allCompiledFiles.add(sourceFile);

            // Compile source file.
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

            Console.mapDebug("Compiling files", files);

            try(StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
                Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjectsFromFiles(files);

                JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, fileObjects);

                boolean success = task.call() == true;

                try {
                    for(Diagnostic<? extends JavaFileObject> d: diagnostics.getDiagnostics() ) {
                        Console.error("Line {}, {} in {}", d.getLineNumber(), d.getMessage( null ), d.getSource().getName());
                    }    
                } catch(Exception e) { }

                if(!success) {
                    throw new InterpretationException("Failed to compile source code");
                }
    
                Console.mapDebug("Full name", "'{}'", (pkg.length() == 0 ? "" : pkg + '.') + clsName);
    
                // Load compiled class.
                URLClassLoader classLoader = loader;// /*isTempClass ? */URLClassLoader.newInstance(new URL[] { COMPILER_ROOT.toURI().toURL() })/* : loader*/;
                Console.mapDebug("Is temp class", isTempClass);
                Console.mapDebug("Using class loader", classLoader);
                Class<?> cls = Class.forName((pkg.length() == 0 ? "" : pkg + '.') + clsName, true, classLoader);
                //classLoader.close();
                return cls;
            }

        } catch(ClassNotFoundException e) {
            throw new InterpretationException("Internal exception", e);
        } catch(IOException e) {
            throw new InterpretationException(e);
        } catch(RuntimeException e) {
            throw new InterpretationException("Internal exception", e);
        }
    }

    private String addDefaultImports(String sourceCode) {
        if(sourceCode.strip().startsWith("package"))
            return sourceCode.substring(0, sourceCode.indexOf(";") + 1) + imports + sourceCode.substring(sourceCode.indexOf(";") + 1);
        return imports + sourceCode;
    }



    public static void main(String[] args) throws Exception {
        Console.getFilter().setEnabled("debug", true);

        System.setSecurityManager(null);

        /*Interpreter interpreter = new Interpreter();
        interpreter.addImport("com.github.rccookie.util.*");
        interpreter.interpreteClass("package xy; public class Runner implements Runnable{public void run(){System.out.println(\"Running\");}}", Runnable.class, false).getConstructor().newInstance().run();*/
    }
}
