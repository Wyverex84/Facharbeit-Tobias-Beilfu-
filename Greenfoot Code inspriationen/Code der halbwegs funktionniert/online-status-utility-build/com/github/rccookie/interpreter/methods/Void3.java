package com.github.rccookie.interpreter.methods;

@FunctionalInterface
public interface Void3<I1, I2, I3> extends Void1<Object[]> {

    public void run(I1 arg1, I2 arg2, I3 arg3);

    @Override
    @SuppressWarnings("unchecked")
    default void run(Object[] arg) {
        run((I1)arg[0], (I2)arg[1], (I3)arg[2]);
    }
}
