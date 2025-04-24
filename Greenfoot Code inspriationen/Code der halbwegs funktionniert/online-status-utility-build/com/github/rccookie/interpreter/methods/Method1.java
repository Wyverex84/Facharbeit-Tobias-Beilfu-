package com.github.rccookie.interpreter.methods;

@FunctionalInterface
public interface Method1<I, R> {

    public R run(I arg);
}
