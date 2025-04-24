package com.github.rccookie.interpreter.tokenizer;

import java.util.Objects;
import java.util.Set;

class ContentToken implements Token {

    private final String content;
    private final String tokenName;
    private final Set<TokenType> types;

    ContentToken(String content, String tokenName, TokenType... types) {
        this.content = Objects.requireNonNull(content);
        this.tokenName = tokenName;
        this.types = Set.of(types);
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public String getInfo() {
        return tokenName;
    }

    @Override
    public Set<TokenType> getTypes() {
        return types;
    }
}
