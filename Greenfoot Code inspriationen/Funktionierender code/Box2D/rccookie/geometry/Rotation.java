package rccookie.geometry;

import rccookie.data.RuntimeLoadable;
import rccookie.data.json.JsonObject;

public class Rotation implements Cloneable, RuntimeLoadable {

    public double z;
    public double y;
    public double x;


    public Rotation() {
        this(0, 0, 0);
    }

    public Rotation(Rotation copy) {
        this(copy.z, copy.y, copy.x);
    }
    
    public Rotation(double z) {
        this(z, 0, 0);
    }

    public Rotation(double z, double y) {
        this(z, y, 0);
    }

    public Rotation(double z, double y, double x) {
        this.z = z;
        this.y = y;
        this.x = x;
    }



    @Override
    public Rotation clone() {
        return new Rotation(this);
    }

    @Override
    public String toString() {
        return "{z=" + z + "|y=" + y + "|x=" + x + "}: " + super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Rotation)) return false;
        return z == ((Rotation)obj).z && y == ((Rotation)obj).y && x == ((Rotation)obj).x;
    }


    public Rotation add(Rotation addedRotation) {
        if(addedRotation != null) {
            z += addedRotation.z;
            y += addedRotation.y;
            x += addedRotation.x;
        }
        return this;
    }

    public Rotation added(Rotation addedRotation) {
        return clone().add(addedRotation);
    }




    @Override
    public Object getSaveData() {
        JsonObject data = new JsonObject();
        data.put("z", z);
        data.put("y", y);
        data.put("x", x);
        return data;
    }

    @Override
    public String getSaveDir() {
        return "saves\\geomentry\\rotations\\";
    }

    @Override
    public String getSaveName() {
        return "rotation" + hashCode();
    }

    @Override
    public void load(Object data) {
        JsonObject jData = (JsonObject)data;
        z = jData.getDouble("z");
        y = jData.getDouble("y");
        x = jData.getDouble("x");
    }
}
