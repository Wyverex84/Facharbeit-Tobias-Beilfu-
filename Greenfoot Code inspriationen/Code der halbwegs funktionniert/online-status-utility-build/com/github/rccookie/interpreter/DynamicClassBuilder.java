package com.github.rccookie.interpreter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.github.rccookie.interpreter.tokenizer.Token;
import com.github.rccookie.interpreter.tokenizer.TokenType;
import com.github.rccookie.interpreter.tokenizer.Tokenizer;
import com.github.rccookie.util.Console;
import com.github.rccookie.util.Iterator;

public class DynamicClassBuilder {

    private static final String SCRIPT_CLASS_NAME = "aClassWithAnUnreasonablyLongName";//ForTheScriptMethodToBeLocatedInToMakeSureNobodyIsEverGonnaNameOneOfHisClassesAccidetlyLikeThisInternalClassAndIfSomeoneReallyDoesHeOrSheProbablyDoesntReallyUnderstandALotOfCodingBecauseWhoWouldEverWantToHaveConstructorCallsThatAreLongerThanHisOrHerScreenWidthAndForThatReasonItReallyDoesNotMatterIfTheyWereToGetAnCompilationExceptionFromThisAlsoItsLiterallyMoreLikelyThatTheyCrackSomeSHA256HashthenToNameItLikeThisButJustToGoExtraSureWeWillAppendARandomNumberRightH";

    static {
        Console.getFilter().setDefault("log", false);
    }

    private final Interpreter interpreter = new Interpreter();

    /**
     * The package of the class (the pure location, without {@code package} or
     * {@code ;}).
     */
    private String pkg = "interpretedstuff";

    /**
     * All the imports for this class as a single long string.
     */
    private String imports = "import com.github.rccookie.interpreter.*;";

    /**
     * The definition of the class; everything after the last import until the
     * first opening curly bracket (exclusive), including annotations. Something
     * like this:
     * <p>{@code @SomeAnnotation public class SomeClass}
     */
    private String classDef = null;

    /**
     * Content inside of the class, like fields, methods or constructors.
     */
    private String content = "";



    /**
     * Creates a new ClassBuilder.
     */
    public DynamicClassBuilder() { }



    /**
     * Processes the given code (snippet). The code may describe a package declaration,
     * an import statement, a class declaration, some field, method or constructor
     * declaration, a semantic corrent combination of the above, or a script that may
     * depend on the class and can be executed directly from a static context within
     * the classes package.
     * 
     * <p>If the code is a package declaration, that package will be parsed. If the
     * package was set before, that package will be overridden. Any code following a
     * package declaration will be threated as import and the main class declaration.
     * 
     * <p>If the code is defining import statements, these will be added to the previous
     * imports. Any code can rely on these imports, also scripts. Any code following the
     * import declarations will be threated the main class declaration.
     * 
     * <p>If the code starts by defining a class and the class was not specifically
     * defined yet, it will be interpreted as the class definition. Otherwise it will be
     * threated as an inner class. If the class is already defined and the code starts
     * with package or imports statements, an {@code IllegalStateException} will be
     * thrown.
     * 
     * <p>If the code clearly defines some fields, methods, constructors or initializers,
     * those will be added. Static methods annotated with {@link Run @Run} or
     * {@link RunWithInterpretedArgs @RunWithInterpretedArgs} and non-static methods with
     * {@link RunOn @RunOn} or {@link RunOnWithInterpretedArgs @RunOnWithInterpretedArgs}
     * will be invoked in build phases. Note that these annotations are imported by default
     * and don't require an import statement / full name.
     * 
     * <p>Note that some code cannot be seperated between fields and initializers, and
     * scripts (method content). For example:
     * <pre>
     * final int[] array = {1, 2, 3};
     * {
     *     System.out.println("Hello World!");
     * }
     * </pre>
     * could be both script or class content, and will be threatened as script. The
     * code above could however be made clearly not part of a script by adding another
     * modifier, i.e.
     * <pre>
     * public final int[] array = {1, 2, 3};
     * {
     *     System.out.println("Hello World!");
     * }
     * </pre>
     * 
     * <p>Code that cannot be declaring fields or similar, or cound not be clearly defined,
     * will be invoked instantly as script. A script has the syntax of code inside of a
     * method, called from some class in the same package as the class and from static or
     * non-static context. The script has all the imports declared before also available
     * and does not return anything. (However, {@code return;} statements can be utilized
     * as usual.)
     * 
     * @param code The code (snoppet) to process
     * @throws CompilationException If the code is a script and the compilation failed
     */
    public void process(String code) throws CompilationException {
        Iterator<Token> iterator = new Iterator<>(Tokenizer.streamCode(code).iterator());
        if(!iterator.hasNext()) return;

        if(iterator.peek() == Token.PACKAGE || iterator.peek() == Token.IMPORT) {
            Console.debug("Package or import declaration found, treating as full class");
            addFullClassContext(iterator);
        }
        else if(classDef != null) {
            Console.debug("No package or import found and class already defined, treating as content or script");
            runScriptOrAddContent(code, iterator);
        }
        else if(isNoPkgOrImportClass(code)) {
            Console.debug("Treating as full class without package or import");
            addFullClassContext(iterator);
        }
        else {
            Console.debug("Treating as class content or script");
            runScriptOrAddContent(code, iterator);
        }
    }



    /**
     * Assembles, compiles and loads this class and invokes all static methods annotated
     * with {@link Run @Run} or {@link RunWithInterpretedArgs @RunWithInterpretedArgs}
     * and non-static methods with {@link RunOn @RunOn} or
     * {@link RunOnWithInterpretedArgs @RunOnWithInterpretedArgs}. Finally it returns the
     * build' class.
     * 
     * @return The compiled and loaded class
     * @throws CompilationException If the compilation failed
     */
    public Class<?> build() throws CompilationException {
        interpreter.nextLoader();
        return compileAndRunIfNeeded(null);
    }



    private static boolean isNoPkgOrImportClass(String code) {

        Iterator<Token> iterator = new Iterator<>(Tokenizer.streamCode(code).iterator());

        while(iterator.hasNext()) {
            Token next = iterator.next();

            if(next.is(TokenType.CLASS_TYPE)) return true; // TODO: Check if followed by fields

            if(next.is(TokenType.MODIFIER)) {
                if(next != Token.PUBLIC && next != Token.ABTRACT && next != Token.FINAL) return false;
                continue;
            }
            if(next == Token.ANNOTATION_START) {
                traverseAnnotation(iterator);
                continue;
            }

            return false;
        }

        return false;
    }

    private static void traverseAnnotation(Iterator<Token> iterator) {
        iterator.next(); // Identifier
        if(iterator.peek() == Token.OPEN_BLOCK) {
            iterator.next();
            int state = 1;
            while(state != 0) {
                Token next = iterator.next();
                if(next == Token.OPEN_BLOCK) state++;
                else if(next == Token.CLOSE_BLOCK) state--;
            }
        }
    }

    private void addFullClassContext(Iterator<Token> iterator) {
        if(iterator.peek() == Token.PACKAGE) {
            pkg = nextPackage(iterator);
            Console.mapDebug("Package", pkg);
        }
        if(!iterator.hasNext()) return;

        String imports = nextImports(iterator);
        if(!imports.isBlank()) {
            Console.mapDebug("Imports", imports);
            this.imports += imports;
        }
        if(!iterator.hasNext()) return;

        if(classDef != null) throw new IllegalStateException();
        classDef = iterator.stream().takeWhile(t -> t != Token.OPEN_CODE_BLOCK).map(Token::toString).collect(Collectors.joining(" "));
        Console.mapDebug("Class definition", classDef);

        if(!iterator.hasNext()) return;
        addInClassContent(iterator, true);
    }

    private static String nextPackage(Iterator<Token> iterator) {
        return iterator.stream()
            .takeWhile(t -> t != Token.STATEMENT_END)
            .skip(1)
            .map(Token::toString)
            .collect(Collectors.joining());
    }

    /**
     * Only the actual package, without 'import' and ';'
     */
    private static String nextImports(Iterator<Token> iterator) {
        StringBuilder imports = new StringBuilder();
        while(iterator.hasNext() && iterator.peek() == Token.IMPORT) {
            imports.append(iterator.stream().takeWhile(t -> t != Token.STATEMENT_END).map(Token::toString).collect(Collectors.joining(" "))).append(";");
        }
        return imports.toString();
    }

    private void addInClassContent(Iterator<Token> iterator, boolean hasClassClosingBracket) {
        String newContent;
        if(hasClassClosingBracket) {
            List<Token> content = iterator.stream().collect(Collectors.toCollection(ArrayList::new));
            content.remove(content.size() - 1);
            newContent = content.stream().map(Token::toString).collect(Collectors.joining(" "));
        }
        else newContent = iterator.stream().map(Token::toString).collect(Collectors.joining(" "));
        content += newContent;
        Console.mapDebug("New content", newContent);
    }



    private void runScriptOrAddContent(String code, Iterator<Token> iterator) throws CompilationException {
        if(isScript(Tokenizer.streamCode(code).collect(Collectors.toList())))
            compileAndRunIfNeeded(code);
        else addInClassContent(iterator, false);
    }

    private static boolean isScript(List<Token> tokens) {
        Iterator<Token> iterator = new Iterator<>(withoutAnnotations(tokens).iterator());

        while(iterator.hasNext()) {
            Token next = iterator.next();

            // An empty statement
            if(next == Token.STATEMENT_END) continue;

            // May be initializer, but cound also just be a block in a method (yes, that is possible)
            if(next == Token.OPEN_CODE_BLOCK) {
                traverseCodeBlock(iterator);
                continue;
            }

            if(next == Token.SYNCHRONIZED)
                // Inside method: synchronized(target){...}, for method: modifier
                return iterator.next() == Token.OPEN_BLOCK;

            // In method: can be at most one modifier: final
            if(next == Token.FINAL) next = iterator.next();

            // More that one modifier / modifier other that final: cannot be inside method
            if(next.is(TokenType.MODIFIER)) {
                Console.debug("Illegal modifier {} inside of method -> is script", next);
                return false;
            }

            // -------------------------------------------------------------------------
            // At this point: No more modifiers
            // -------------------------------------------------------------------------

            // When inside a method next may be control structure
            if(!(next.is(TokenType.ANY_TYPE) || next.is(TokenType.IDENTIFIER))) return true; //TODO: Consider identifiers with full class name
            next = iterator.next();

            // Only identifier: constructor
            if(next == Token.OPEN_BLOCK) return false;

            if(next == Token.OPEN_ARRAY) {
                Console.debug("Type is array");
                // In methods, an array element may be assigned, resulting in next being an index
                if(iterator.next() != Token.CLOSE_ARRAY) return true;
                next = iterator.next();
            }

            // In any declaration (method or field, ctor was filtered above) now must be the identifier
            if(!next.is(TokenType.IDENTIFIER)) {
                Console.debug("{} is not an identifier -> is script", next);
                return true;
            }
            next = iterator.next();

            // Three options if in class: field declaration, field declaration and initialization, or method
            
            // Field declaration, i.e. 'final int[] array;'
            if(next == Token.STATEMENT_END) continue;

            // Field declaration and initialization, i.e. 'final int[] array = {1,2,3};'
            if(next == Token.ASSIGNMENT_OPERATOR) {
                Console.debug("Field or variable declaration");
                while((next = iterator.next()) != Token.STATEMENT_END) {
                    // We're not looking for semicolons inside of anonumous classes. Also: array initializations
                    if(next == Token.OPEN_CODE_BLOCK) traverseCodeBlock(iterator);
                    if(next.is(TokenType.CONTROL_STRUCTURE)) return true; // No control structures found outside of methods
                }
                continue;
            }

            if(!next.is(TokenType.IDENTIFIER)) return true;

            Console.debug("Must be either method or script because next is {}", next);

            // Either it is clearly a method, so it's not a script, or it cannot be a method, meaning that it cannot
            // be inside the class and must be a script.
            return iterator.next() != Token.OPEN_BLOCK;
        }

        // At this point it could be both a script or a field declaration. Field declaration is irreversible,
        // so we should rather threat it as a script.
        Console.warn("Could be both script or field declaration, returning 'is script'");
        return true;
    }

    private static List<Token> withoutAnnotations(List<Token> tokens) {
        List<Token> result = new ArrayList<>();
        Iterator<Token> iterator = new Iterator<>(tokens.iterator());

        while (iterator.hasNext()) {
            Token next = iterator.next();
            if(next != Token.ANNOTATION_START) {
                result.add(next);
                continue;
            }
            iterator.next();// Identifier
            if(iterator.peek() == Token.OPEN_BLOCK) {
                iterator.next();
                int state = 1;
                while(state != 0) {
                    next = iterator.next();
                    if(next == Token.OPEN_BLOCK) state++;
                    else if(next == Token.CLOSE_BLOCK) state--;
                }
            }
        }

        return result;
    }

    private static void traverseCodeBlock(Iterator<Token> iterator) {
        int state = 1;
        while(true) {
            Token next = iterator.next();
            if(next == Token.OPEN_CODE_BLOCK) state++;
            else if(next == Token.CLOSE_CODE_BLOCK && --state == 0) return;
        }
    }



    @SuppressWarnings("unchecked")
    private Class<?> compileAndRunIfNeeded(String scriptCode) throws CompilationException {

        Console.debug("Compiling");

        if(isEmpty() && (scriptCode == null || scriptCode.isBlank())) return Object.class;

        CompileGroup compileGroup = new CompileGroup();
        compileGroup.add(assembly());

        // Compile main to be able to analyze annotations
        Class<?> cls = compileGroup.compile()[0];



        boolean hasScript = scriptCode != null && !scriptCode.isBlank();
        if(hasScript) {
            runScript(scriptCode, cls, compileGroup);
            return isEmpty() ? Object.class : cls; // If there's a script don't run the annotated methods
        }



        Collection<UncompiledInvocationTask> uncompiledTasks = getAnnotationTasks(cls);
        if(uncompiledTasks.isEmpty()) return cls;


        uncompiledTasks.stream()
            .map(UncompiledInvocationTask::getGetterArguments)
            .reduce(new ArrayList<UncompiledArgument>(), (r,g) -> {r.addAll(g); return r;}, (a, b) -> {a.addAll(b); return a;})
            .stream()
            .map(arg -> arg.getterCode)
            .forEach(compileGroup::add);



        Class<Supplier<Object>>[] getterClasses = new Class[compileGroup.size() - 1]; // Without main class

        // Don't compile if there are no additional files needed
        if(compileGroup.size() > 1) {
            Class<?>[] compiledClasses = compileGroup.compile();
            for(int i=0; i<getterClasses.length; i++)
                getterClasses[i] = (Class<Supplier<Object>>)compiledClasses[i+1];
        }

        Iterator<Supplier<Object>> getterIterator = new Iterator<>(Arrays.stream(getterClasses)
            .map(c -> {
                try {
                    return c.getConstructor().newInstance();
                } catch(Exception e) {
                    throw new RuntimeException(e);
                }
            }).iterator());


        Set<InvocationTask> tasks = uncompiledTasks.stream().map(t -> t.build(getterIterator)).collect(Collectors.toSet());
        runAnnotatedMethods(tasks);

        return cls;
    }

    private static class UncompiledInvocationTask {

        public final Method method;
        private final UncompiledArgument target;
        private final UncompiledArgument[] args;

        public UncompiledInvocationTask(Method method, UncompiledArgument target, UncompiledArgument... args) {
            Console.debug("new uncompiled task ", method, target, args);
            this.method = method;
            this.target = target;
            this.args = args;
        }

        public Collection<UncompiledArgument> getGetterArguments() {
            Set<UncompiledArgument> result = new HashSet<>();
            if(target.needsCompiling()) result.add(target);
            result.addAll(Arrays.stream(args).filter(UncompiledArgument::needsCompiling).collect(Collectors.toSet()));
            return result;
        }

        /**
         * 'Consumes' exactly as many suppliers as needed
         */
        public InvocationTask build(Iterator<Supplier<Object>> getters) {

            Object buildTarget = target.get(getters);
            Object[] buildArgs = new Object[args.length];
            for(int i=0; i<args.length; i++) buildArgs[i] = args[i].get(getters);

            return new InvocationTask(method, buildTarget, buildArgs);
        }
    }

    private static class UncompiledArgument {

        public final String getterCode;
        private final Object fixedValue;

        private UncompiledArgument(String getterCode, Object fixedValue) {
            this.getterCode = getterCode;
            this.fixedValue = fixedValue;
        }

        public Object get(Iterator<Supplier<Object>> getter) {
            return needsCompiling() ? getter.next().get() : fixedValue;
        }

        public boolean needsCompiling() {
            return getterCode != null;
        }
    }

    public UncompiledArgument getterArgument(String getterCode, Class<?> cls) {

        return new UncompiledArgument(
            "package "
                    + cls.getPackageName() + ";"
                    + imports
                    + "public class Getter"
                    + Math.abs((long) new Random().nextInt())
                    + " implements java.util.function.Supplier<Object>{public Object get(){return "
                    + getterCode + ";}}",
            null
        );
    }

    public UncompiledArgument fixedArgument(Object value) {
        return new UncompiledArgument(null, value);
    }

    private boolean isEmpty() {
        return classDef == null && content.isBlank();
    }

    private void runScript(String scriptCode, Class<?> fromClass, CompileGroup compileGroup) throws CompilationException {

        List<Token> tokens = Tokenizer.stream(scriptCode).collect(Collectors.toCollection(ArrayList::new));
        boolean isOneLine = scriptCode.stripTrailing().endsWith(";");

        String scriptClassCode = "package "
                               + fromClass.getPackageName() + ";"
                               + imports
                               + "public class "
                               + SCRIPT_CLASS_NAME
                               + Math.abs((long)new Random().nextInt())
                               + "{public static void run(){"
                               + scriptCode
                               + "}}";
        compileGroup.add(scriptClassCode);

        try {
            Method script = compileGroup.compile()[1].getDeclaredMethod("run");
            script.setAccessible(true);
            script.invoke(null);
        } catch(CompilationException e) {
            throw e;
        } catch(Exception e) {
            throw new RuntimeException("Internal error", e);
        }
    }

    private String generateScriptClass(String script, Class<?> fromClass) {
        StringBuilder code = new StringBuilder();
        String pkg = fromClass.getPackageName();

        if(!pkg.isEmpty()) code.append("package ").append(pkg).append(";");
        code.append(imports);
        code.append("public class ").append(SCRIPT_CLASS_NAME).append(Math.abs((long)new Random().nextInt())).append("{");

        return code.toString();
    }

    private Collection<UncompiledInvocationTask> getAnnotationTasks(Class<?> cls) {
        final Set<UncompiledInvocationTask> tasks = new HashSet<>();
        for(Method method : cls.getDeclaredMethods()) {
            try {

                method.setAccessible(true);

                UncompiledArgument target = fixedArgument(null);
                UncompiledArgument[] args;

                if(method.isAnnotationPresent(Run.class))
                    args = Arrays.stream(method.getAnnotation(Run.class).value())
                            .map(this::fixedArgument).toArray(UncompiledArgument[]::new);

                else if(method.isAnnotationPresent(RunOn.class)) {
                    target = getterArgument(method.getAnnotation(RunOn.class).value(), cls);
                    args = Arrays.stream(method.getAnnotation(RunOn.class).args())
                            .map(this::fixedArgument).toArray(UncompiledArgument[]::new);
                }

                else if(method.isAnnotationPresent(RunWithInterpretedArgs.class)) {
                    args = Arrays.stream(method.getAnnotation(RunWithInterpretedArgs.class).value())
                            .map(code -> getterArgument(code, cls)).toArray(UncompiledArgument[]::new);
                }

                else if(method.isAnnotationPresent(RunOnWithInterpretedArgs.class)) {
                    target = getterArgument(method.getAnnotation(RunOn.class).value(), cls);
                    args = Arrays.stream(method.getAnnotation(RunOnWithInterpretedArgs.class).args())
                            .map(code -> getterArgument(code, cls)).toArray(UncompiledArgument[]::new);
                }
                else continue;
                tasks.add(new UncompiledInvocationTask(method, target, args));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    private void runAnnotatedMethods(Collection<InvocationTask> tasks) {
        Exception exception = null;
        for(InvocationTask task : tasks) {
            try {
                if(task.target == null && !Modifier.isStatic(task.method.getModifiers()))
                    throw new IllegalStateException("Cannot run non-static method " + task.method.getName() + " from static context");
                task.method.invoke(task.target, task.args);
            } catch(Exception e) {
                if(exception == null) exception = e;
                else exception.addSuppressed(e);
            }
        }
        if(exception != null) throw new RuntimeException(exception);
    }

    private static class InvocationTask {

        public Method method;
        public Object target;
        public Object[] args;

        public InvocationTask(Method method, Object target, Object[] args) {
            this.method = method;
            this.target = target;
            this.args = args;
        }
    }



    private String assembly() {
        Console.log("Building...");
        StringBuilder code = new StringBuilder();

        if(pkg != null)
            code.append("package ").append(pkg).append(";");
        code.append(imports);

        code.append(classDef != null ? classDef : "public class BuildClass").append("{");
        code.append(content).append("}");

        Console.log("Formatting...");
        String out = Tokenizer.format(Tokenizer.parseAll(code.toString()));
        Console.log("Done");
        return out;
    }



    public static void main(String[] args) throws Exception {
        Console.getFilter().setEnabled("debug", false);
        Console.getFilter(Interpreter.class).setEnabled("debug", false);
        Console.Config.includeLineNumber = false;

        DynamicClassBuilder builder = new DynamicClassBuilder();
        
        String input;
        while(!(input = Console.input("")).isBlank()) {
            try { builder.process(input); } catch(Exception e) {
                Console.error("{} ({})", e.getClass().getName(), e.getMessage());
            }
        }

        /*builder.process("package xy; public class Foo { }");
        builder.process("private int[] numbers = {1, 2, 3};");
        builder.process("import com.github.rccookie.util.Console;");
        builder.process("public void print() { System.out.println(\"Print using default toString: \" + numbers); }");
        builder.process("new Foo().print();");
        builder.process("import java.util.Arrays;");
        builder.process("public void printUsingArrays() { System.out.println(Arrays.toString(numbers)); }");
        builder.process("new Foo().printUsingArrays();");*/

        builder.build();
    }
}
