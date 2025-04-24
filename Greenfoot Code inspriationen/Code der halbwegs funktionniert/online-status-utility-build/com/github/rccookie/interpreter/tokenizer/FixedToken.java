package com.github.rccookie.interpreter.tokenizer;

import java.util.Objects;
import java.util.Set;

class FixedToken implements Token {

    private final String content;
    private final String info;
    private final Set<TokenType> types;

    FixedToken(String content, TokenType... types) {
        this.content = Objects.requireNonNull(content);
        info = content.toUpperCase();
        this.types = Set.of(types);
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public Set<TokenType> getTypes() {
        return types;
    }
}
