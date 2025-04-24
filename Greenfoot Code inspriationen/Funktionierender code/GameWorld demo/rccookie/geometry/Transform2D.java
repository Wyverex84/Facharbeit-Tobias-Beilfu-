package rccookie.geometry;

import rccookie.data.RuntimeLoadable;
import rccookie.data.json.JsonObject;

/**
 * The class transform represents the location and the rotation of an object
 * in a two dimentional world. It may also be used for example to save a movements
 * direction, speed and rotational speed.
 * 
 * @author RcCookie
 * @version 1.0
 */
public class Transform2D implements Cloneable, RuntimeLoadable {

    /**
     * The location part of the transform represented by a vector.
     */
    public Vector2D location;

    /**
     * The rotation part of the transform.
     */
    public double rotation;


    /**
     * Creates a new transform located at (0|0) and with the rotation 0.
     */
    public Transform2D(){
        this(new Vector2D(0, 0), 0);
    }


    public Transform2D(JsonObject data) {
        this();
        load(data);
    }

    /**
     * Creates a new transform by cloning the given one. This means that
     * the values will be copied, however, for the transform {@code x} <p>{@code new
     * Transform(x).location == x.location}<p>will be false, as a new vector
     * is being created.
     * 
     * @param clone The vector to clone
     */
    public Transform2D(Transform2D clone){
        this(clone.location.clone() , clone.rotation);
    }

    /**
     * Creates a new transform with the given vector as location. The angle will be 0.
     * 
     * @param location The location of the transform
     */
    public Transform2D(Vector2D location){
        this(location, 0);
    }

    /**
     * Creates a new transform with the given angle. The location will be a zero vector.
     * 
     * @param rotation The rotation of the transform
     */
    public Transform2D(double rotation){
        this(new Vector2D(), rotation);
    }

    /**
     * Creates a new transform with the specified values.
     * 
     * @param location The location of the transform
     * @param rotation The rotation of the transform
     */
    public Transform2D(Vector2D location, double rotation){
        this.location = location;
        this.rotation = rotation;
    }

    public Transform2D clone(){
        return new Transform2D(this);
    }

    public boolean equals(Object o){
        if(o instanceof Transform2D){
            return location.equals(((Transform2D)o).location) && rotation == ((Transform2D)o).rotation;
        }
        return false;
    }


    /**
     * Adds another transform onto itself by adding both the location vectors and the rotations.
     * 
     * @param t The transform to add
     * @return The transform itself
     */
    public Transform2D add(Transform2D t){
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
    public Transform2D subtract(Transform2D t){
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
    public Transform2D scale(double scale){
        location.scale(scale);
        rotation *= scale;
        return this;
    }




    @Override
    public JsonObject getSaveData() {
        JsonObject data = new JsonObject();
        data.put("rotation", rotation);
        data.put("location", location);
        return data;
    }

    @Override
    public String getSaveDir() {
        return "saves\\geomentry\\transforms\\";
    }

    @Override
    public String getSaveName() {
        return "transform2D" + hashCode();
    }

    @Override
    public void load(Object data) {
        JsonObject jData = (JsonObject)data;
        rotation = jData.getDouble("rotation");
        location.load(jData.getJson("location"));
    }
}