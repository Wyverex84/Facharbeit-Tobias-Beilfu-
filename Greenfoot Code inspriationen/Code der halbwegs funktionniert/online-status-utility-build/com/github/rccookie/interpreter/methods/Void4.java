package com.github.rccookie.interpreter.methods;

@FunctionalInterface
public interface Void4<I1, I2, I3, I4> extends Void1<Object[]> {

    public void run(I1 arg1, I2 arg2, I3 arg3, I4 arg4);

    @Override
    @SuppressWarnings("unchecked")
    default void run(Object[] arg) {
        run((I1)arg[0], (I2)arg[1], (I3)arg[2], (I4)arg[3]);
    }
}
