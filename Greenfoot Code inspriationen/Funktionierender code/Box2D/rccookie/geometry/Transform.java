package rccookie.geometry;

import rccookie.data.RuntimeLoadable;
import rccookie.data.json.JsonObject;

public class Transform implements Cloneable, RuntimeLoadable {

    /**
     * Do not set to {@code null}!
     */
    public Vector location;

    /**
     * Do not set to {@code null}!
     */
    public Rotation rotation;

    /**
     * Do not set to {@code null}!
     */
    public Vector scale;


    public Transform() {
        this(new Vector(), new Rotation(), new Vector(1, 1, 1));
    }
    public Transform(Transform copy) {
        this(
            copy.location.clone(),
            copy.rotation.clone(),
            copy.scale.clone()
        );
    }
    public Transform(Vector location) {
        this(location, new Rotation(), new Vector(1, 1 ,1));
    }
    public Transform(Vector location, Rotation rotation, Vector scale) {
        this.location = location;
        this.rotation = rotation;
        this.scale = scale;
    }



    @Override
    public Transform clone() {
        return new Transform(this);
    }




    @Override
    public JsonObject getSaveData() {
        JsonObject data = new JsonObject();
        data.put("rotation", rotation.getSaveData());
        data.put("location", location);
        return data;
    }

    @Override
    public String getSaveDir() {
        return "saves\\geomentry\\transforms\\";
    }

    @Override
    public String getSaveName() {
        return "transform" + hashCode();
    }

    @Override
    public void load(Object data) {
        JsonObject jData = (JsonObject)data;
        rotation.load(jData.getJson("rotation"));
        location.load(jData.getJson("location"));
    }
}
