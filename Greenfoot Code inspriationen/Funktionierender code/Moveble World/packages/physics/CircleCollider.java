package packages.physics;

import greenfoot.Actor;
import packages.twoD.*;
/**
 * A collider that has a collision box of a circle.
 */
public class CircleCollider extends Collider{

    /**
     * The raduis of the circle of the collider.
     */
    public final int radius;

    /**
     * Constructs a new circle collider with the given actor as host, the given radius and the given offset to the host.
     * 
     * @param host The colliders host
     * @param radius The radius of the circle of the collider
     * @param offset The offset of the collider to the host
     */
    public CircleCollider(Actor host, int radius, Transform offset){
        super(host, offset, new Vector(2 * radius, 2 * radius));
        this.radius = radius;
    }

    /**
     * Constructs a new circle collider with the given radius and the given offset to the host. The host
     * actor should be set later on.
     * 
     * @param radius The radius of the circle of the collider
     * @param offset The offset of the collider to the host
     */
    public CircleCollider(int radius, Vector offset){
        this(null, radius, new Transform(offset));
    }
    
    /**
     * Constructs a new circle collider with the given radius and no offset. The host
     * actor should be set later on.
     * 
     * @param radius The radius of the circle of the collider
     */
    public CircleCollider(int raduis){
        this(null, raduis, new Transform());
    }

    /**
     * Constructs a new circle collider with the given actor as host and no offset. The radius will be the average of the
     * hosts image width and height.
     * 
     * @param host The actor this collider belongs to and the raduis is based on
     */
    public CircleCollider(Actor host){
        this(host, (host.getImage().getWidth() + host.getImage().getHeight()) / 4, new Transform());
    }

    public void setImage(greenfoot.GreenfootImage image){
        boolean temp = debug;
        debug = false;
        super.setImage(image);
        debug = temp;
        
        //debug
        
        if(debug){
            image.setColor(new greenfoot.Color(255, 0, 0, 100));
            image.fillOval(0, 0, image.getWidth(), image.getHeight());
        }
    }
    
    public Vector getEdgeTowards(Actor a){
        return Vector.angledVector(new Vector(a.getX() - getX(), a.getY() - getY()).angle() + 90, 1);
    }

    public boolean intersects(Actor a){
        if(isIgnored(a)) return false;
        if(a instanceof CircleCollider){
            return new Vector(a.getX() - getX(), a.getY() - getY()).abs() < radius + ((CircleCollider)a).radius;
        }
        return BoxCollider.intersecting((BoxCollider)a, this);
    }
    
    public int getArea(){
        return (int)(radius * radius * Math.PI);
    }
    
    

    @Override
    public Vector getWorldEdge(){
        if(getX() - radius <= 0 || getX() + radius >= getWorld().getWidth() - 1) return new Vector(0, 1);
        if(getY() - radius <= 0 || getY() + radius >= getWorld().getHeight()- 1) return new Vector(1, 0);
        return null;
    }
}