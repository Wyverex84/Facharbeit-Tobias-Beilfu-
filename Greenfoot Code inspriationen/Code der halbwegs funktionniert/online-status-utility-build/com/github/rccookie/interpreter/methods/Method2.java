package com.github.rccookie.interpreter.methods;

@FunctionalInterface
public interface Method2<I1, I2, R> extends Method1<Object[], R> {

    public R run(I1 arg1, I2 arg2);

    @Override
    @SuppressWarnings("unchecked")
    default R run(Object[] args) {
        return run((I1)args[0], (I2)args[1]);
    }
}
