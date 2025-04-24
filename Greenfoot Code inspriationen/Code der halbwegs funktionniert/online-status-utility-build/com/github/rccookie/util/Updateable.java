package com.github.rccookie.util;

/**
 * An interface containing the method {@link #update()} indicating that
 * the class implementing this method can and should be updated regularly.
 * It may also be used as a functional interface.
 * 
 * @author RcCookie
 * @version 1.0
 */
@FunctionalInterface
public interface Updateable {

    /**
     * Runs an update for this updatable.
     */
    public void update();
}
