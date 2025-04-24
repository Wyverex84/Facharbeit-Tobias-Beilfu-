package com.github.rccookie.interpreter.tokenizer;

import java.util.Objects;
import java.util.Set;

public interface Token {

    public String toString();

    public String getInfo();

    public Set<TokenType> getTypes();

    public default boolean is(TokenType type) {
        return getTypes().contains(type);
    }

    public default boolean is(Token token) {
        return equals(token);
    }



    public static final Token ABTRACT = new FixedToken("abtract", TokenType.MODIFIER);
    public static final Token BOOLEAN = new FixedToken("boolean", TokenType.ANY_TYPE, TokenType.PRIMITIVE);
    public static final Token BREAK = new FixedToken("break", TokenType.BREAKING);
    public static final Token BYTE = new FixedToken("byte", TokenType.ANY_TYPE, TokenType.PRIMITIVE);
    public static final Token CASE = new FixedToken("case");
    public static final Token CATCH = new FixedToken("catch", TokenType.FOLLOWING_TRY);
    public static final Token CHAR = new FixedToken("char", TokenType.ANY_TYPE, TokenType.PRIMITIVE);
    public static final Token CLASS = new FixedToken("class", TokenType.CLASS_TYPE);
    public static final Token CONTINUE = new FixedToken("continue", TokenType.BREAKING);
    public static final Token DEFAULT = new FixedToken("default");
    public static final Token DO = new FixedToken("do", TokenType.CONTROL_STRUCTURE);
    public static final Token DOUBLE = new FixedToken("double", TokenType.ANY_TYPE, TokenType.PRIMITIVE);
    public static final Token ELSE = new FixedToken("else");
    public static final Token ENUM = new FixedToken("enum", TokenType.CLASS_TYPE);
    public static final Token EXTENDS = new FixedToken("extends", TokenType.FOLLOWING_CLASS_TYPE);
    public static final Token FINAL = new FixedToken("final", TokenType.MODIFIER);
    public static final Token FINALLY = new FixedToken("finally", TokenType.FOLLOWING_TRY);
    public static final Token FLOAT = new FixedToken("float", TokenType.ANY_TYPE, TokenType.PRIMITIVE);
    public static final Token FOR = new FixedToken("for", TokenType.CONTROL_STRUCTURE, TokenType.LOOP);
    public static final Token IF = new FixedToken("if", TokenType.CONTROL_STRUCTURE);
    public static final Token IMPLEMENTS = new FixedToken("implements", TokenType.FOLLOWING_CLASS_TYPE);
    public static final Token IMPORT = new FixedToken("import");
    public static final Token INSTANCEOF = new FixedToken("instanceof", TokenType.COMPARATOR, TokenType.BOOLEAN_COMPARATOR);
    public static final Token INT = new FixedToken("int", TokenType.ANY_TYPE, TokenType.PRIMITIVE);
    public static final Token INTERFACE = new FixedToken("interface", TokenType.CLASS_TYPE);
    public static final Token LONG = new FixedToken("long", TokenType.ANY_TYPE, TokenType.PRIMITIVE);
    public static final Token NATIVE = new FixedToken("native", TokenType.MODIFIER);
    public static final Token NEW = new FixedToken("new");
    public static final Token NULL = new FixedToken("null", TokenType.REFERENCE);
    public static final Token PACKAGE = new FixedToken("package");
    public static final Token PRIVATE = new FixedToken("private", TokenType.MODIFIER);
    public static final Token PROTECTED = new FixedToken("protected", TokenType.MODIFIER);
    public static final Token PUBLIC = new FixedToken("public", TokenType.MODIFIER);
    public static final Token RETURN = new FixedToken("return", TokenType.BREAKING);
    public static final Token SHORT = new FixedToken("short", TokenType.ANY_TYPE, TokenType.PRIMITIVE);
    public static final Token STATIC = new FixedToken("static", TokenType.MODIFIER);
    public static final Token STRICTFP = new FixedToken("strictfp", TokenType.MODIFIER);
    public static final Token SUPER = new FixedToken("super");
    public static final Token SWITCH = new FixedToken("switch", TokenType.CONTROL_STRUCTURE);
    public static final Token SYNCHRONIZED = new FixedToken("synchronized", TokenType.MODIFIER);
    public static final Token THIS = new FixedToken("this", TokenType.REFERENCE);
    public static final Token THROW = new FixedToken("throw");
    public static final Token THROWS = new FixedToken("throws");
    public static final Token TRANSIENT = new FixedToken("transient", TokenType.MODIFIER);
    public static final Token TRY = new FixedToken("try", TokenType.CONTROL_STRUCTURE);
    public static final Token VOID = new FixedToken("void", TokenType.ANY_TYPE);
    public static final Token VOLATILE = new FixedToken("volatile", TokenType.MODIFIER);
    public static final Token WHILE = new FixedToken("while", TokenType.CONTROL_STRUCTURE, TokenType.LOOP);

    // --------------------------------
    // Operators / Symbols
    // --------------------------------

    public static final Token STATEMENT_END = new FixedToken(";", TokenType.ANY_CONTEXT, TokenType.NEW_LINE_REASON);
    public static final Token ANNOTATION_START = new FixedToken("@", TokenType.ANY_CONTEXT, TokenType.PREFERES_WHITESPACE_BEFORE);
    //public static final Toke//ASTERISK = new FixedToken("*", TokenType.ANY_CONTEXT);
    //public static final Toke//OPEN_ARGUMENTS = new FixedToken("(", TokenType.ANY_CONTEXT);
    public static final Token OPEN_ARRAY = new FixedToken("[", TokenType.ANY_CONTEXT);
    public static final Token OPEN_BLOCK = new FixedToken("(", TokenType.ANY_CONTEXT);
    public static final Token OPEN_CODE_BLOCK = new FixedToken("{", TokenType.ANY_CONTEXT, TokenType.NEW_LINE_REASON, TokenType.PREFERES_WHITESPACE_BEFORE);
    //public static final Toke//OPEN_TYPE = new FixedToken(">", TokenType.ANY_CONTEXT, TokenType.PREFERES_WHITESPACE_AFTER);
    //public static final Toke//CLOSE_ARGUMENTS = new FixedToken(")", TokenType.ANY_CONTEXT, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token CLOSE_ARRAY = new FixedToken("]", TokenType.ANY_CONTEXT);
    public static final Token CLOSE_BLOCK = new FixedToken(")", TokenType.ANY_CONTEXT, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token CLOSE_CODE_BLOCK = new FixedToken("}", TokenType.ANY_CONTEXT, TokenType.NEW_LINE_REASON);
    //public static final Toke//CLOSE_TYPE = new FixedToken(">", TokenType.ANY_CONTEXT);
    public static final Token COMMA = new FixedToken(",", TokenType.ANY_CONTEXT, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token DOT = new FixedToken(".", TokenType.ANY_CONTEXT);
    public static final Token INLINE_IF = new FixedToken("?", TokenType.ANY_CONTEXT, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token INLINE_THEN = new FixedToken(":", TokenType.ANY_CONTEXT, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token LAMBDA_ARROW = new FixedToken("->", TokenType.ANY_CONTEXT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token AND = new FixedToken("&&", TokenType.ANY_CONTEXT, TokenType.COMPARATOR, TokenType.BOOLEAN_COMPARATOR, TokenType.BOOLEAN_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token OR = new FixedToken("||", TokenType.ANY_CONTEXT, TokenType.COMPARATOR, TokenType.BOOLEAN_COMPARATOR, TokenType.BOOLEAN_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token EQUALS = new FixedToken("==", TokenType.ANY_CONTEXT, TokenType.COMPARATOR, TokenType.BOOLEAN_COMPARATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token NOT_EQUALS = new FixedToken("!=", TokenType.ANY_CONTEXT, TokenType.COMPARATOR, TokenType.BOOLEAN_COMPARATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token GREATER = new FixedToken(">", TokenType.ANY_CONTEXT, TokenType.COMPARATOR, TokenType.BOOLEAN_COMPARATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token LESS = new FixedToken("<", TokenType.ANY_CONTEXT, TokenType.COMPARATOR, TokenType.BOOLEAN_COMPARATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token GREATER_OR_EQUAL = new FixedToken(">=", TokenType.ANY_CONTEXT, TokenType.COMPARATOR, TokenType.BOOLEAN_COMPARATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token LESS_OR_EQUAL = new FixedToken("<=", TokenType.ANY_CONTEXT, TokenType.COMPARATOR, TokenType.BOOLEAN_COMPARATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token NOT = new FixedToken("!", TokenType.ANY_CONTEXT, TokenType.SINGLE_OPERATOR);
    public static final Token PLUS = new FixedToken("+", TokenType.ANY_CONTEXT, TokenType.OPERATOR, TokenType.MATH_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token MINUS = new FixedToken("-", TokenType.ANY_CONTEXT, TokenType.OPERATOR, TokenType.MATH_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token MULTIPLY = new FixedToken("*", TokenType.ANY_CONTEXT, TokenType.OPERATOR, TokenType.MATH_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token DIVIDE = new FixedToken("/", TokenType.ANY_CONTEXT, TokenType.OPERATOR, TokenType.MATH_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token MODULO = new FixedToken("%", TokenType.ANY_CONTEXT, TokenType.OPERATOR, TokenType.MATH_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token BIT_AND = new FixedToken("&", TokenType.ANY_CONTEXT, TokenType.OPERATOR, TokenType.MATH_OPERATOR, TokenType.BOOLEAN_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token BIT_OR = new FixedToken("|", TokenType.ANY_CONTEXT, TokenType.OPERATOR, TokenType.MATH_OPERATOR, TokenType.BOOLEAN_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token BIT_XOR = new FixedToken("^", TokenType.ANY_CONTEXT, TokenType.OPERATOR, TokenType.MATH_OPERATOR, TokenType.BOOLEAN_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token BIT_SHIFT_UP = new FixedToken("<<", TokenType.ANY_CONTEXT, TokenType.OPERATOR, TokenType.MATH_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token BIT_SHIFT_DOWN = new FixedToken(">>", TokenType.ANY_CONTEXT, TokenType.OPERATOR, TokenType.MATH_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token BIT_SHIFT_DOWN_ZERO = new FixedToken(">>>", TokenType.ANY_CONTEXT, TokenType.OPERATOR, TokenType.MATH_OPERATOR, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token BIT_INVERSE = new FixedToken("~", TokenType.ANY_CONTEXT, TokenType.SINGLE_OPERATOR);
    public static final Token ASSIGNMENT_OPERATOR = new FixedToken("=", TokenType.ANY_CONTEXT, TokenType.ASSIGNMENT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token ASSIGNMENT_PLUS = new FixedToken("+=", TokenType.ANY_CONTEXT, TokenType.ASSIGNMENT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token ASSIGNMENT_MINUS = new FixedToken("-=", TokenType.ANY_CONTEXT, TokenType.ASSIGNMENT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token ASSIGNMENT_MULTIPLY = new FixedToken("*=", TokenType.ANY_CONTEXT, TokenType.ASSIGNMENT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token ASSIGNMENT_DIVIDE = new FixedToken("/=", TokenType.ANY_CONTEXT, TokenType.ASSIGNMENT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token ASSIGNMENT_MODULO = new FixedToken("%=", TokenType.ANY_CONTEXT, TokenType.ASSIGNMENT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token ASSIGNMENT_PLUS_1 = new FixedToken("++", TokenType.ANY_CONTEXT, TokenType.INCREASEMENT);
    public static final Token ASSIGNMENT_MINUS_1 = new FixedToken("--", TokenType.ANY_CONTEXT, TokenType.INCREASEMENT);
    public static final Token ASSIGNMENT_BIT_AND = new FixedToken("&=", TokenType.ANY_CONTEXT, TokenType.ASSIGNMENT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token ASSIGNMENT_BIT_OR = new FixedToken("|=", TokenType.ANY_CONTEXT, TokenType.ASSIGNMENT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token ASSIGNMENT_BIT_XOR = new FixedToken("^=", TokenType.ANY_CONTEXT, TokenType.ASSIGNMENT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token ASSIGNMENT_BIT_SHIFT_UP = new FixedToken("<<=", TokenType.ANY_CONTEXT, TokenType.ASSIGNMENT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token ASSIGNMENT_BIT_SHIFT_DOWN = new FixedToken(">>=", TokenType.ANY_CONTEXT, TokenType.ASSIGNMENT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);
    public static final Token ASSIGNMENT_BIT_SHIFT_DOWN_ZERO = new FixedToken(">>>=", TokenType.ANY_CONTEXT, TokenType.ASSIGNMENT, TokenType.PREFERES_WHITESPACE_BEFORE, TokenType.PREFERES_WHITESPACE_AFTER);

    /*public static final Token UNSPECIFIED(String name) {
        return new ContentToken(name, "token", TokenType.UNSPECIFIED);
    }*/

    public static Token IDENTIFIER(String name) {
        return new ContentToken(name, "identifier", TokenType.IDENTIFIER);
    }

    public static Token HARDCODED_CHAR(String name) {
        return new ContentToken("'" + name + "'", "hardcode-char", TokenType.TEXT, TokenType.HARDCODED_CHAR);
    }

    public static Token HARDCODED_STRING(String name) {
        return new ContentToken('"' + name + '"', "hardcode-string", TokenType.TEXT, TokenType.HARDCODED_STRING);
    }

    public static Token SINGLE_LINE_COMMENT(String name) {
        return new ContentToken("//" + name, "singleline-comment", TokenType.TEXT, TokenType.COMMENT, TokenType.SINGLE_LINE_COMMENT, TokenType.NEW_LINE_REASON);
    }

    public static Token MULTI_LINE_COMMENT(String name, boolean javadoc) {
        return new ContentToken(
            "/*" + (javadoc ? "*" : "") + name + "*/",
            "multiline-comment",
            TokenType.TEXT, TokenType.COMMENT,
            TokenType.MULTI_LINE_COMMENT,
            TokenType.NEW_LINE_REASON
        );
    }



    public static Token parse(String value) {
        if(Objects.equals(value, "abtract")) return FixedToken.ABTRACT;
        if(Objects.equals(value, "boolean")) return FixedToken.BOOLEAN;
        if(Objects.equals(value, "break")) return FixedToken.BREAK;
        if(Objects.equals(value, "byte")) return FixedToken.BYTE;
        if(Objects.equals(value, "case")) return FixedToken.CASE;
        if(Objects.equals(value, "catch")) return FixedToken.CATCH;
        if(Objects.equals(value, "char")) return FixedToken.CHAR;
        if(Objects.equals(value, "class")) return FixedToken.CLASS;
        if(Objects.equals(value, "continue")) return FixedToken.CONTINUE;
        if(Objects.equals(value, "default")) return FixedToken.DEFAULT;
        if(Objects.equals(value, "do")) return FixedToken.DO;
        if(Objects.equals(value, "double")) return FixedToken.DOUBLE;
        if(Objects.equals(value, "else")) return FixedToken.ELSE;
        if(Objects.equals(value, "enum")) return FixedToken.ENUM;
        if(Objects.equals(value, "extends")) return FixedToken.EXTENDS;
        if(Objects.equals(value, "final")) return FixedToken.FINAL;
        if(Objects.equals(value, "finally")) return FixedToken.FINALLY;
        if(Objects.equals(value, "float")) return FixedToken.FLOAT;
        if(Objects.equals(value, "for")) return FixedToken.FOR;
        if(Objects.equals(value, "if")) return FixedToken.IF;
        if(Objects.equals(value, "implements")) return FixedToken.IMPLEMENTS;
        if(Objects.equals(value, "import")) return FixedToken.IMPORT;
        if(Objects.equals(value, "instanceof")) return FixedToken.INSTANCEOF;
        if(Objects.equals(value, "int")) return FixedToken.INT;
        if(Objects.equals(value, "interface")) return FixedToken.INTERFACE;
        if(Objects.equals(value, "long")) return FixedToken.LONG;
        if(Objects.equals(value, "native")) return FixedToken.NATIVE;
        if(Objects.equals(value, "new")) return FixedToken.NEW;
        if(Objects.equals(value, "null")) return FixedToken.NULL;
        if(Objects.equals(value, "package")) return FixedToken.PACKAGE;
        if(Objects.equals(value, "private")) return FixedToken.PRIVATE;
        if(Objects.equals(value, "protected")) return FixedToken.PROTECTED;
        if(Objects.equals(value, "public")) return FixedToken.PUBLIC;
        if(Objects.equals(value, "return")) return FixedToken.RETURN;
        if(Objects.equals(value, "short")) return FixedToken.SHORT;
        if(Objects.equals(value, "static")) return FixedToken.STATIC;
        if(Objects.equals(value, "strictfp")) return FixedToken.STRICTFP;
        if(Objects.equals(value, "super")) return FixedToken.SUPER;
        if(Objects.equals(value, "switch")) return FixedToken.SWITCH;
        if(Objects.equals(value, "synchronized")) return FixedToken.SYNCHRONIZED;
        if(Objects.equals(value, "this")) return FixedToken.THIS;
        if(Objects.equals(value, "throw")) return FixedToken.THROW;
        if(Objects.equals(value, "throws")) return FixedToken.THROWS;
        if(Objects.equals(value, "transient")) return FixedToken.TRANSIENT;
        if(Objects.equals(value, "try")) return FixedToken.TRY;
        if(Objects.equals(value, "void")) return FixedToken.VOID;
        if(Objects.equals(value, "volatile")) return FixedToken.VOLATILE;
        if(Objects.equals(value, "while")) return FixedToken.WHILE;

        return Token.IDENTIFIER(value);// TODO
    }
}
