package com.github.rccookie.interpreter.methods;

@FunctionalInterface
public interface Method3<I1, I2, I3, R> extends Method1<Object[], R> {

    public R run(I1 arg1, I2 arg2, I3 arg3);

    @Override
    @SuppressWarnings("unchecked")
    default R run(Object[] args) {
        return run((I1)args[0], (I2)args[1], (I3)args[2]);
    }
}
