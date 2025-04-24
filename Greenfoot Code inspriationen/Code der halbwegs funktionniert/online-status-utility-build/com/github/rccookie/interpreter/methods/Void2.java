package com.github.rccookie.interpreter.methods;

@FunctionalInterface
public interface Void2<I1, I2> extends Void1<Object[]> {

    public void run(I1 arg1, I2 arg2);

    @Override
    @SuppressWarnings("unchecked")
    default void run(Object[] arg) {
        run((I1)arg[0], (I2)arg[1]);
    }
}
