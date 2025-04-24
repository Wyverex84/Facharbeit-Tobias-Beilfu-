package com.github.rccookie.interpreter.methods;

@FunctionalInterface
public interface Method<R> extends Method1<Object, R> {

    public R run();

    @Override
    default R run(Object arg) {
        return run();
    }
}
