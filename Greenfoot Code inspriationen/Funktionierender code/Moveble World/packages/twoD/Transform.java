package packages.twoD;

/**
 * The class transform represents the location and the rotation of an object
 * in a two dimentional world. It may also be used for example to save a movements
 * direction, speed and rotational speed.
 * 
 * @author RcCookie
 * @version 1.0
 */
public class Transform implements Cloneable{

    /**
     * The location part of the transform represented by a vector.
     */
    public Vector location;

    /**
     * The rotation part of the transform.
     */
    public double rotation;


    /**
     * Creates a new transform located at (0|0) and with the rotation 0.
     */
    public Transform(){
        this(new Vector(0, 0), 0);
    }

    /**
     * Creates a new transform by cloning the given one. This means that
     * the values will be copied, however, for the transform {@code x} <p>{@code new
     * Transform(x).location == x.location}<p>will be false, as a new vector
     * is being created.
     * 
     * @param clone The vector to clone
     */
    public Transform(Transform clone){
        this(clone.location.clone() , clone.rotation);
    }

    /**
     * Creates a new transform with the given vector as location. The angle will be 0.
     * 
     * @param location The location of the transform
     */
    public Transform(Vector location){
        this(location, 0);
    }

    /**
     * Creates a new transform with the given angle. The location will be a zero vector.
     * 
     * @param rotation The rotation of the transform
     */
    public Transform(double rotation){
        this(new Vector(), rotation);
    }

    /**
     * Creates a new transform with the specified values.
     * 
     * @param location The location of the transform
     * @param rotation The rotation of the transform
     */
    public Transform(Vector location, double rotation){
        this.location = location;
        this.rotation = rotation;
    }

    public Transform clone(){
        return new Transform(this);
    }

    public boolean equals(Object o){
        if(o instanceof Transform){
            return location.equals(((Transform)o).location) && rotation == ((Transform)o).rotation;
        }
        return false;
    }


    /**
     * Adds another transform onto itself by adding both the location vectors and the rotations.
     * 
     * @param t The transform to add
     * @return The transform itself
     */
    public Transform add(Transform t){
        location.add(t.location);
        rotation += t.rotation;
        return this;
    }

    /**
     * Subtracts another transform from itself by subtracting both the location vectors and the rotations.
     * 
     * @param t The transform to subtract
     * @return The transform itself
     */
    public Transform subtract(Transform t){
        location.add(t.location.inverted());
        rotation -= t.rotation;
        return this;
    }

    /**
     * Scales all menbers of the transform by the given factor.
     * 
     * @param scale The factor to scale by
     * @return The transform itself
     */
    public Transform scale(double scale){
        location.scale(scale);
        rotation *= scale;
        return this;
    }
}