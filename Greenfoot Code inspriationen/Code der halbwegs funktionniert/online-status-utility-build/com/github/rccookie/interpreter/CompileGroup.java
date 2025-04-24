package com.github.rccookie.interpreter;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.github.rccookie.interpreter.tokenizer.Token;
import com.github.rccookie.interpreter.tokenizer.TokenType;
import com.github.rccookie.interpreter.tokenizer.Tokenizer;
import com.github.rccookie.util.Console;

public class CompileGroup {

    private final URLClassLoader classLoader;

    private final List<CompileTarget> files = new ArrayList<>();



    public CompileGroup() {
        try {
            classLoader = new URLClassLoader(new URL[] {Interpreter.COMPILER_ROOT.toURI().toURL()});
        } catch(MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }



    public void add(String code) {
        try {
            PeekIterator<Token> iterator = new PeekIterator<>(Tokenizer.streamCode(Objects.requireNonNull(code)).iterator());

            String pkg = iterator.peek() == Token.PACKAGE ?
                iterator.stream().skip(1).takeWhile(t -> t != Token.STATEMENT_END).map(Token::toString).collect(Collectors.joining()) : "";

            iterator.stream().anyMatch(t -> t.is(TokenType.CLASS_TYPE));
            String clsName = iterator.next().toString();

            if(files.stream().anyMatch(t -> t.className == clsName)) throw new IllegalArgumentException();

            File sourceFile = new File(Interpreter.COMPILER_ROOT, pkg.replace(".", "/") + "/" + clsName + ".java");
            sourceFile.getParentFile().mkdirs();
            Files.write(sourceFile.toPath(), code.getBytes());

            files.add(new CompileTarget(sourceFile, (pkg.length() == 0 ? "" : pkg + '.') + clsName));
        } catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }



    public int size() {
        return files.size();
    }

    public List<File> getFiles() {
        return files.stream().map(ct -> ct.file).collect(Collectors.toList());
    }



    public Class<?>[] compile() throws CompilationException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        try(StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {

            Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjectsFromFiles(files.stream().map(t -> t.file).collect(Collectors.toList()));

            boolean success = compiler.getTask(null, null, diagnostics, null, null, fileObjects).call() == true;
            printDiagnostics(diagnostics);
            if(!success) throw new CompilationException();

            return loadClasses();
        } catch(IOException e) {
            throw new UncheckedIOException(e);
        } catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void printDiagnostics(DiagnosticCollector<JavaFileObject> diagnostics) {
        for(Diagnostic<? extends JavaFileObject> d: diagnostics.getDiagnostics()) {
            try {
                Console.error("");
                for(String out : ("Line " + d.getLineNumber() + ": " + d.getMessage(null) + "\nin " + (d.getSource() != null ? d.getSource().toUri().getPath().substring(1) : "?")).split("\n"))
                    Console.error(out);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Class<?>[] loadClasses() throws ClassNotFoundException {

        Class<?>[] classes = new Class[files.size()];
        ClassNotFoundException exception = null;

        for(int i=0; i<classes.length; i++) {
            try {
                classes[i] = Class.forName(files.get(i).className, true, classLoader);
            } catch(ClassNotFoundException e) {
                if(exception == null) exception = e;
                else exception.addSuppressed(e);
            }
        }

        if(exception != null) throw exception;
        return classes;
    }

    public static class CompileTarget {

        public final File file;
        public final String className;

        public CompileTarget(File file, String className) {
            this.file = file;
            this.className = className;
        }
    }



    public static void main(String[] args) throws Exception {
        String code = "package test; public class Test { public void test() { System.out.println(\"Hello World!\"); } }";
        String code2 = "package test; public class TestDependent { public static void main(String[] args) { new Test().test(); } }";

        CompileGroup compileGroup = new CompileGroup();
        compileGroup.add(code2);
        compileGroup.add(code);
        Console.info(compileGroup.compile());
    }
}
