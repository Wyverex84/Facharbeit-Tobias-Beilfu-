package rccookie.game.util;

import rccookie.geometry.Vector2D;

/**
 * The result of a raycast. Stores any results and some additional information.
 * All information is stored in public final fields.
 * 
 * @Version 1.1
 */
public class RaycastResult{
    /**
     * The length of the raycast.
     */
    public final int length;
    
    /**
     * The x coordinate of the end of the raycast. It might not hit a target
     * object if the raycast was not successful.
     */
    public final int x;
    
    /**
     * The y coordinate of the end of the raycast. It might not hit a target
     * object if the raycast was not successful.
     */
    public final int y;
    
    /**
     * The direction of the raycast. This is technically not a result, but it
     * may be useful to exclusivly work with the result.
     */
    public final int direction;
    
    /**
     * The actor that was hit by the raycast. If this is null, it means that
     * the raycast did not hit any target objects. This may be because the
     * world border or the maximum length was reached.
     */
    public final greenfoot.Actor target;
    
    /**
     * A vector representation of this vector pointing towards the raycast
     * endpoint. May be used to recreate the raycast starting location.
     */
    public final Vector2D vector;

    /**
     * Indicates weather the raycast has been successful.
     */
    public final boolean successful;
    
    /**
     * Constructor for raycast results. Sets all fields to the given ones.
     */
    public RaycastResult(int length, int x, int y, int direction, greenfoot.Actor target, boolean successful){
        this.length = length;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.target = target;
        this.successful = successful;
        
        vector = Vector2D.angledVector(direction, length);
    }
    
    public String toString(){
        StringBuilder out = new StringBuilder();
        out.append("Raycast - length(");
        out.append(length);
        out.append("), direction(");
        out.append(direction);
        out.append("), target actor: ");
        out.append(target);
        out.append(", successful(");
        out.append(successful);
        out.append(")");
        return out.toString();
    }
}