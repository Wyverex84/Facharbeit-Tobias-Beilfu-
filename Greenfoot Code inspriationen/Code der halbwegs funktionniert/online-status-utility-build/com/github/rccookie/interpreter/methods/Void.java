package com.github.rccookie.interpreter.methods;

@FunctionalInterface
public interface Void extends Void1<Object> {

    public void run();

    @Override
    default void run(Object arg) {
        run();
    }
}
