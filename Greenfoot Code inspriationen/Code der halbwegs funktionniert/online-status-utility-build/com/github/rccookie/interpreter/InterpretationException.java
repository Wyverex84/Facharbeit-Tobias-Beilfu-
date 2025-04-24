package com.github.rccookie.interpreter;

public class InterpretationException extends Exception {

    public InterpretationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterpretationException(Throwable cause) {
        super(cause);
    }

    public InterpretationException(String message) {
        super(message);
    }
}
