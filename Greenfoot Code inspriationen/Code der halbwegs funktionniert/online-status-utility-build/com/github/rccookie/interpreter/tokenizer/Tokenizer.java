package com.github.rccookie.interpreter.tokenizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.github.rccookie.util.Console;

public class Tokenizer {

    private Tokenizer() {
        throw new UnsupportedOperationException();
    }

    public static Stream<Token> stream(String code) {
        Iterator<Token> iterator = new TokenizeIterator(code);
        Iterable<Token> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static Stream<Token> streamCode(String code) {
        return stream(code).filter(t -> !t.is(TokenType.COMMENT));
    }

    private static class TokenizeIterator implements Iterator<Token> {

        private final StringBuilder code;

        TokenizeIterator(String code) {
            this.code = new StringBuilder(code);
        }

        @Override
        public boolean hasNext() {
            return stripLeading(code).length() > 0;
        }

        @Override
        public Token next() {
            if(!hasNext()) throw new NoSuchElementException();

            Token token;

            if(code.charAt(0) == '"') token =  parseNextString(code);
            else if(code.charAt(0) == '\'') token =  parseNextChar(code);
            else if(code.toString().startsWith("//")) token =  parseNextSingleLineComment(code);
            else if(code.toString().startsWith("/*")) token =  parseNextMultiLineComment(code);
            else token = parseNextCodeToken(code);

            Console.mapDebug("Next token", token);

            return token;
        }
    }

    public static List<Token> parseAll(String code) {
        return stream(code).collect(Collectors.toList());
    }

    private static Token parseNextCodeToken(StringBuilder code) {

        if(code.length() == 0) return Token.IDENTIFIER("");

        String codeString = code.toString();
        if(Tokens.findAll(TokenType.ANY_CONTEXT).stream().anyMatch(codeString::startsWith))
            return parseNextAnyContextToken(code);



        StringBuilder token = new StringBuilder();
        while(true) {

            token.append(code.charAt(0));
            code.deleteCharAt(0);

            String codeString1 = code.toString();
            if(code.length() == 0 || codeString1.startsWith(" ")
            || Tokens.findAll(TokenType.ANY_CONTEXT).stream().anyMatch(codeString1::startsWith)) {
                return Token.parse(token.toString());
            }
        }
    }

    private static Token parseNextAnyContextToken(StringBuilder code) {
        char first = code.charAt(0);
        code.deleteCharAt(0);

        switch(first) {
            case '@': return Token.ANNOTATION_START;
            case '=': {
                if(code.length() != 0 && code.charAt(0) == '=') {
                    code.deleteCharAt(0);
                    return Token.EQUALS;
                }
                return Token.ASSIGNMENT_OPERATOR;
            }
            //case '*': return Asterisk.ASTERISK;
            case ']': return Token.CLOSE_ARRAY;
            case ')': return Token.CLOSE_BLOCK;
            case '}': return Token.CLOSE_CODE_BLOCK;
            case '>': {
                if(code.length() != 0) {
                    if(code.charAt(0) == '=') {
                        code.deleteCharAt(0);
                        return Token.GREATER_OR_EQUAL;
                    }
                    if(code.charAt(0) == '>') {
                        code.deleteCharAt(0);
                        if(code.length() != 0) {
                            if(code.charAt(0) == '=') {
                                code.deleteCharAt(0);
                                return Token.ASSIGNMENT_BIT_SHIFT_DOWN;
                            }
                            if(code.charAt(0) == '>') {
                                code.deleteCharAt(0);
                                if(code.length() != 0 && code.charAt(0) == '=') {
                                    code.deleteCharAt(0);
                                    return Token.ASSIGNMENT_BIT_SHIFT_DOWN_ZERO;
                                }
                                return Token.ASSIGNMENT_BIT_SHIFT_DOWN_ZERO;
                            }
                        }
                        return Token.BIT_SHIFT_DOWN;
                    }
                }
                return Token.GREATER;
            }
            case ',': return Token.COMMA;
            case '.': return Token.DOT;
            case '?': return Token.INLINE_IF;
            case ':': return Token.INLINE_THEN;
            case '&': {
                if(code.length() != 0) {
                    if(code.charAt(0) == '=') {
                        code.deleteCharAt(0);
                        return Token.ASSIGNMENT_BIT_AND;
                    }
                    if(code.charAt(0) == '&') {
                        code.deleteCharAt(0);
                        return Token.AND;
                    }
                }
                return Token.BIT_AND;
            }
            case '|': {
                if(code.length() != 0) {
                    if(code.charAt(0) == '=') {
                        code.deleteCharAt(0);
                        return Token.ASSIGNMENT_BIT_OR;
                    }
                    if(code.charAt(0) == '|') {
                        code.deleteCharAt(0);
                        return Token.OR;
                    }
                }
                return Token.BIT_OR;
            }
            case '^': {
                if(code.length() != 0 && code.charAt(0) == '=') {
                    code.deleteCharAt(0);
                    return Token.ASSIGNMENT_BIT_XOR;
                }
                return Token.BIT_XOR;
            }
            case '!': {
                if(code.length() != 0 && code.charAt(0) == '=') {
                    code.deleteCharAt(0);
                    return Token.NOT_EQUALS;
                }
                return Token.NOT;
            }
            case '+': {
                if(code.length() != 0) {
                    if(code.charAt(0) == '=') {
                        code.deleteCharAt(0);
                        return Token.ASSIGNMENT_PLUS;
                    }
                    if(code.charAt(0) == '+') {
                        code.deleteCharAt(0);
                        return Token.ASSIGNMENT_PLUS_1;
                    }
                }
                return Token.PLUS;
            }
            case '-': {
                if(code.length() != 0) {
                    if(code.charAt(0) == '=') {
                        code.deleteCharAt(0);
                        return Token.ASSIGNMENT_MINUS;
                    }
                    if(code.charAt(0) == '-') {
                        code.deleteCharAt(0);
                        return Token.ASSIGNMENT_MINUS_1;
                    }
                    if(code.charAt(0) == '>') {
                        code.deleteCharAt(0);
                        return Token.LAMBDA_ARROW;
                    }
                }
                return Token.MINUS;
            }
            case '*': {
                if(code.length() != 0 && code.charAt(0) == '=') {
                    code.deleteCharAt(0);
                    return Token.ASSIGNMENT_MULTIPLY;
                }
                return Token.MULTIPLY;
            }
            case '/': {
                if(code.length() != 0 && code.charAt(0) == '=') {
                    code.deleteCharAt(0);
                    return Token.ASSIGNMENT_DIVIDE;
                }
                return Token.DIVIDE;
            }
            case '[': return Token.OPEN_ARRAY;
            case '(': return Token.OPEN_BLOCK;
            case '{': return Token.OPEN_CODE_BLOCK;
            case '<': {
                if(code.length() != 0) {
                    if(code.charAt(0) == '<') {
                        code.deleteCharAt(0);
                        if(code.length() != 0 && code.charAt(0) == '=') {
                            code.deleteCharAt(0);
                            return Token.ASSIGNMENT_BIT_SHIFT_DOWN;
                        }
                        return Token.BIT_SHIFT_DOWN;
                    }
                    if(code.charAt(0) == '=') {
                        code.deleteCharAt(0);
                        return Token.LESS_OR_EQUAL;
                    }
                }
                return Token.LESS;
            }
            case ';': return Token.STATEMENT_END;
            default: return Token.IDENTIFIER(Character.toString(first));
        }
    }



    public static List<Token> parseStringsAndComments(String javaCode) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder code = new StringBuilder(javaCode);

        //noinspection ConstantConditions
        while((code = stripLeading(code)).length() != 0) {

            Console.mapDebug("Remaining", "'{}'", code);

            if(code.charAt(0) == '"') tokens.add(parseNextString(code));
            else if(code.charAt(0) == '\'') tokens.add(parseNextChar(code));
            else if(code.toString().startsWith("//")) tokens.add(parseNextSingleLineComment(code));
            else if(code.toString().startsWith("/*")) tokens.add(parseNextMultiLineComment(code));
            else tokens.add(parseNextAnything(code));
        }

        return tokens;
    }



    private static StringBuilder stripLeading(StringBuilder code) {
        while(code.length() > 0 && (code.charAt(0) == ' '
                                 || code.charAt(0) == '\n'
                                 || code.charAt(0) == '\r'
                                 || code.charAt(0) == '\t')) code.deleteCharAt(0);
        return code;
    }

    private static Token parseNextString(StringBuilder code) {
        code.deleteCharAt(0);

        StringBuilder string = new StringBuilder();
        while(code.length() > 0) {
            if(code.charAt(0) != '"' || (string.length() != 0 && string.charAt(string.length() - 1) == '\\')) {
                string.append(code.charAt(0));
                code.deleteCharAt(0);
            }
            else {
                code.deleteCharAt(0);
                break;
            }
        }

        return Token.HARDCODED_STRING(string.toString());
    }

    private static Token parseNextChar(StringBuilder code) {
        code.deleteCharAt(0);

        if(code.toString().startsWith("\\'")) {
            code.delete(0, Math.min(3, code.length()));
            return Token.HARDCODED_CHAR("'");
        }

        int endIndex = code.indexOf("'");

        Token result;
        if(endIndex == -1) {
            result = Token.HARDCODED_CHAR(code.toString());
            code.delete(0, code.length());
        }
        else {
            result = Token.HARDCODED_CHAR(code.substring(0, endIndex));
            code.delete(0, endIndex + 1);
        }

        return result;
    }

    private static Token parseNextSingleLineComment(StringBuilder code) {
        code.delete(0, 2);

        String comment;

        if(code.toString().contains("\n")) {
            int index = code.indexOf("\n");
            comment = code.substring(0, index);
            code.delete(0, index + 1);
        }
        else {
            comment = code.toString();
            code.delete(0, code.length() - 1);
        }

        return Token.SINGLE_LINE_COMMENT(comment);
    }

    private static Token parseNextMultiLineComment(StringBuilder code) {
        code.delete(0, 2);

        boolean javadoc = code.charAt(0) == '*';
        if(javadoc) code.deleteCharAt(0);

        StringBuilder comment = new StringBuilder();

        while(code.length() > 0) {
            if(code.toString().startsWith("*/")) {
                code.delete(0, 2);
                break;
            }
            comment.append(code.charAt(0));
            code.deleteCharAt(0);
        }

        return Token.MULTI_LINE_COMMENT(comment.toString(), javadoc);
    }

    private static Token parseNextAnything(StringBuilder code) {

        StringBuilder token = new StringBuilder();

        while(code.length() > 0) {
            if(code.charAt(0) == '"'
            || code.charAt(0) == '\''
            || code.toString().startsWith("//")
            || code.toString().startsWith("/*")) break;
            token.append(code.charAt(0));
            code.deleteCharAt(0);
        }

        Console.mapDebug("Unspecified", Token.IDENTIFIER(token.toString()).toString());
        return Token.IDENTIFIER(token.toString());
    }



    public static String assemble(List<Token> tokens, String delimiter) {
        return tokens.stream().map(Token::toString).collect(Collectors.joining(delimiter));
    }

    public static List<Token> withoutComments(List<Token> tokens) {
        return tokens.stream().filter(t -> !t.is(TokenType.COMMENT)).collect(Collectors.toList());
    }

    public static String format(List<Token> tokens) {
        return format(tokens, Token::toString);
    }

    public static String format(List<Token> tokens, Function<Token, String> toString) {
        if(tokens.isEmpty()) return "";
        if(tokens.size() == 1) return toString.apply(tokens.get(0));

        Console.mapDebug("Tokens", tokens);

        Iterator<Token> iterator = tokens.iterator();
        Token last = iterator.next();
        StringBuilder result = new StringBuilder(toString.apply(tokens.get(0)));

        int currentTabs = 0;

        while(iterator.hasNext()) {
            Token current = iterator.next();

            Console.mapDebug("Current state", result);
            Console.mapDebug("Current", toString.apply(current));

            
            if(current == Token.STATEMENT_END
            || (last == Token.DOT && current == Token.MULTIPLY)) result.append(toString.apply(current));

            else if(last == Token.OPEN_CODE_BLOCK) {
                if(current == Token.CLOSE_CODE_BLOCK)
                    result.append(" ").append(toString.apply(current));
                else result.append("\n").append("    ".repeat(Math.max(0, ++currentTabs))).append(toString.apply(current));
            }
            else if(current == Token.CLOSE_CODE_BLOCK)
                result.append("\n").append("    ".repeat(Math.max(0, --currentTabs))).append(toString.apply(current));

            else if(last.is(TokenType.NEW_LINE_REASON) && current != Token.CLOSE_BLOCK)
                result.append("\n").append("    ".repeat(Math.max(0, currentTabs))).append(toString.apply(current));

            else if(current.is(TokenType.PREFERES_WHITESPACE_BEFORE) || last.is(TokenType.PREFERES_WHITESPACE_AFTER))
                result.append(" ").append(toString.apply(current));

            else if(last.is(TokenType.ANY_CONTEXT) || current.is(TokenType.ANY_CONTEXT))
                result.append(toString.apply(current));

            else result.append(" ").append(toString.apply(current));

            last = current;
        }

        return result.toString();
    }



    public static void main(String[] args) {
        Console.getFilter().setEnabled("debug", true);

        String code = "package com.github.rccookie.interpreter.tokenizer;import com.github.rccookie.interpreter.Run;//Some comment\n/**Multiline comment*/public class Test{public @Run() int x() {return 0();}}";
        System.out.println(format(streamCode(code).collect(Collectors.toList()), Token::getInfo));
    }
}
