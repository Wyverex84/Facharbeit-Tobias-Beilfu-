package com.github.rccookie.interpreter.tokenizer;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class Tokens {

    private Tokens() {
        throw new UnsupportedOperationException();
    }

    private static final Set<Token> ALL_TOKENS = Arrays.stream(FixedToken.class.getFields())
        .map(f -> { try { return (Token)f.get(null); } catch(Exception e) { throw new RuntimeException(e); } })
        .collect(Collectors.toSet());

    public static Set<String> findAll(TokenType type) {
        return ALL_TOKENS.stream()
            .filter(t -> t.is(type))
            .map(t -> t.toString())
            .collect(Collectors.toSet());
    }
}
