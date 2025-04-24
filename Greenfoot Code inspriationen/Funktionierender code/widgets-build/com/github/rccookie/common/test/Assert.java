package com.github.rccookie.common.test;

public final class Assert {
    private Assert() { }

    public static final void assertThrows(Class<? extends Exception> expected, TestMethod expression) {
        try {
            expression.test();
        } catch(Exception e) {
            if(e.getClass().isAssignableFrom(expected)) return;
            throw new AssertionError("The expected exception of type " + expected + " was not thrown, however an exception of type " + e.getClass() + ".");
        }
        throw new AssertionError("The expected exception of type " + expected + " was not thrown.");
    }

    public static final void assertThrowsError(Class<? extends Error> expected, TestMethod expression) {
        try {
            expression.test();
        } catch(Error e) {
            if(e.getClass().isAssignableFrom(expected)) return;
            throw new AssertionError("The expected error of type " + expected + " was not thrown, however an error of type " + e.getClass() + ".");
        } catch(Exception e) { }
        throw new AssertionError("The error exception of type " + expected + " was not thrown.");
    }

    public static interface TestMethod {
        public void test() throws Exception;
    }
}
