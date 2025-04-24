package rccookie.geometry;

import rccookie.data.RuntimeLoadable;
import rccookie.data.json.JsonObject;

/**
 * A vector represents a change in location, often also expressed as
 * an arrow or a movement. Every vector consists of a <i>x</i>, <i>y</i> and
 * <i>z</i> coordinate which represent the difference in the corresponding
 * direction.
 * <p>Vectors allow easy manipulation and share all common mathmatical
 * interactions such as absolute(length), normalization, addition and subtraction,
 * scalar and vector multiplication and more. Also the angle of the vector can
 * be measured relative to another vector or the x axis in form of a rotation
 * object.
 * <p>Vectors are commonly used for any kind of movement representation, but
 * also for other geometric effects. One example is to save the location of
 * an object using a vector which represents the distance from the point of
 * origin to the object.
 */
public class Vector implements Cloneable, RuntimeLoadable {

    /**
     * A vector with the length zero.
     */
    public static final Vector ZERO_VECTOR = new Vector();

    /**
     * A vector pointing along the x axis with the length of 1. Representation
     * of the x axis.
     */
    public static final Vector UNIT_VECTOR_X = new Vector(1);

    /**
     * A vector pointing along the y axis with the length of 1. Representation
     * of the y axis.
     */
    public static final Vector UNIT_VECTOR_Y = new Vector(0, 1);

    /**
     * A vector pointing along the z axis with the length of 1. Representation
     * of the zaxis.
     */
    public static final Vector UNIT_VECTOR_Z = new Vector(0, 0, 1);



    /**
     * The length of the vector parallel to the x axis.
     */
    public double x;
    
    /**
     * The length of the vector parallel to the y axis.
     */
    public double y;
    
    /**
     * The length of the vector parallel to the z axis.
     */
    public double z;

    


    /**
     * Creates a new zero vector.
     */
    public Vector() {
        this(0, 0, 0);
    }

    /**
     * Creates a new Vector from the given vector. In other words, this
     * creates a copy of the given vector so that for {@code Vector v}
     * {@code v.equals(new Vector(v))} will be true and {@code v == new Vector(v)}
     * will be false.
     * 
     * @param copy The vector to create a copy from
     */
    public Vector(Vector copy) {
        this(copy.x, copy.y, copy.z);
    }

    /**
     * Creates a new vector with the length {@code x} in the direction of the x axis.
     * <p>This does exactly the same as invoking {@code new Vector(x, 0, 0);}.
     * 
     * @param x The length of the vector in x direction
     */
    public Vector(double x) {
        this(x, 0, 0);
    }

    /**
     * Creates a new vector with the given x and y magnitude. This essentially creates
     * a 2D vector.
     * <p>This does exactly the same as invoking {@code new Vector(x, y, 0);}.
     * 
     * @param x The length of the vector in x direction
     * @param y The length of the vector in y direction
     */
    public Vector(double x, double y) {
        this(x, y, 0);
    }

    /**
     * Creates a new vector eith the given x, y and z magnitude.
     * 
     * @param x The length of the vector in x direction
     * @param y The length of the vector in y direction
     * @param z The length of the vector in z direction
     */
    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }








    /**
     * Creates and returns a copy of this vector so that for a vector {@code v}
     * <p>{@code v.equals(v.clone())}
     * <p>will be {@code true} and
     * <p>{@code v == v.copy()}
     * <p>will be {@code false}.
     */
    @Override
    public Vector clone() {
        return new Vector(this);
    }


    /**
     * Returns a string representation of this vector. The representation will look
     * something like this:
     * <p>{@code [x|y|z]}.
     */
    @Override
    public String toString() {
        return "[" + x + "|" + y + "|" + z + "]";//: "; + getClass().getName();
    }


    /**
     * Returns weather some other vector is "equal" to this one.
     * <p>An object is considered to be equal to this vector, if
     * <ul>
     * <li>the object is this vector
     * <li>the object is a vector and
     * <li>the vectors share the exact same x, y and z value
     * </ul>
     * This may be overridden by subclasses.
     * 
     * @param obj The object to compare
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null || !(obj instanceof Vector)) return false;
        return x == ((Vector)obj).x && y == ((Vector)obj).y && z == ((Vector)obj).z;
    }


    @Override
    public int hashCode() {
        return 0;
    }








    /**
     * Returns the absolute value of this vector.
     * <p>The absolute value is equivilent to the <b>length</b> of the vector and is computed
     * using pytagoras. Like any absolute value, the length is always positive.
     * 
     * @return The length of the vector
     */
    public double abs() {
        return abs(this);
    }

    /**
     * Returns a rotation object containing information about the direction the vector is pointing
     * in 3D space.
     * <p>Both z and y rotation will be within {@code -180}(exclusive) and {@code 180}(inclusive).
     * <p>The x rotation of the vector will always be {@code 0}.
     * 
     * @return The rotation of this vector
     */
    public Rotation rotation() {
        return rotation(this);
    }

    /**
     * Returns the angle between the x axis and this vector in degrees.
     * <p>The angle returned will be within {@code -180}(exclusive) and {@code 180}(inclusive).
     * 
     * @return The angle between the vector and the x axis
     */
    public double angle() {
        return angleBetween(this, UNIT_VECTOR_X);
    }

    /**
     * Returns the <b>smallest</b> angle between this and the given vector in degrees.
     * <p>The angle returned will be within {@code 0} and {@code 180}(inclusive).
     * 
     * @param v The vector to get the angle to
     * @return The angle between this and {@code v}
     */
    public double angleTo(Vector v) {
        return smallestAngleBetween(this, v);
    }








    /**
     * Scales all parameters of the vector by the given factor.
     * 
     * @param factor The factor to scale by
     * @return The vector itself
     */
    public Vector scale(double factor) {
        x *= factor;
        y *= factor;
        z *= factor;
        return this;
    }

    /**
     * Divides all parameters of the vector through the given divident.
     * <p>If the divident is 0, an ArithmeticException will be thrown.
     * 
     * @param divident The divident to be divided through
     * @return The vector itself
     */
    public Vector divide(double divident) {
        if(divident == 0) throw new ArithmeticException("Division by 0");
        return scale(1 / divident);
    }

    /**
     * Inverts the vector by inverting every parameter of it.
     * <p>This does exactly the same as invoking {@code v.scale(-1);}
     * 
     * @return The vector itself
     */
    public Vector invert() {
        return this.scale(-1);
    }

    /**
     * Normalizes the vector.
     * <p>A normalized vector has the length {@code 1} and points in the same
     * direction as the initial vector.
     * 
     * @return The vector itself
     */
    public Vector normalize() {
        if(equals(ZERO_VECTOR)) {
            x = 1;
            return this;
        }
        return scale(1 / abs());
    }

    /**
     * Adds the given vector onto this vector by adding each parameter of the given vector
     * onto this vector's parameter. The vector that is added stays unchanged.
     * 
     * @param addedVector The vector to be added onto this vector
     * @return The vector itself
     */
    public Vector add(Vector addedVector) {
        if(addedVector != null) {
            x += addedVector.x;
            y += addedVector.y;
            z += addedVector.z;
        }
        return this;
    }

    /**
     * Adds the given distance onto this vector. Positive values will always increase the length
     * of the vector while negative values might decrease its size. If the given length to add
     * is greater than the negative length of this vector, the direction will be inverted.
     * 
     * @param length The length to add
     * @return The vector itself
     */
    public Vector add(double length) {
        return add(angledVector(rotation(), length));
    }

    public Vector subtract(Vector subtractedVector) {
        return add(subtractedVector.inverted());
    }

    public Vector subtract(double length) {
        return add(-length);
    }

    public Vector rotate(double angle) {
        return rotate(new Rotation(angle));
    }

    public Vector rotate(Rotation rotation) {
        if(rotation != null) {
            Vector rotated = rotated(rotation);
            x = rotated.x;
            y = rotated.y;
            z = rotated.z;
        }
        return this;
    }

    public Vector floor(int digitIndex) {
        x = Geometry.floor(x, digitIndex);
        y = Geometry.floor(y, digitIndex);
        z = Geometry.floor(z, digitIndex);
        return this;
    }

    public Vector ceil(int digitIndex) {
        x = Geometry.ceil(x, digitIndex);
        y = Geometry.ceil(y, digitIndex);
        z = Geometry.ceil(z, digitIndex);
        return this;
    }

    public Vector round(int digitIndex) {
        x = Geometry.round(x, digitIndex);
        y = Geometry.round(y, digitIndex);
        z = Geometry.round(z, digitIndex);
        return this;
    }

    public Vector floor() {
        return floor(0);
    }

    public Vector ceil() {
        return ceil(0);
    }

    public Vector round() {
        return round(0);
    }

    






    public Vector scaled(double factor) {
        return clone().scale(factor);
    }

    public Vector divided(double divident) {
        return clone().divide(divident);
    }

    public Vector inverted() {
        return clone().invert();
    }

    public Vector normalized() {
        return clone().normalize();
    }

    public Vector added(Vector addedVector) {
        return clone().add(addedVector);
    }

    public Vector added(double length) {
        return clone().add(length);
    }

    public Vector subtracted(Vector subtractedVector) {
        return clone().subtract(subtractedVector);
    }

    public Vector subtracted(double length) {
        return clone().subtract(length);
    }

    public Vector rotated(double angle) {
        return rotated(new Rotation(angle));
    }

    public Vector rotated(Rotation rotation) {
        return angledVector(rotation().add(rotation), abs());
    }

    public Vector floored(int digitIndex) {
        return clone().floor(digitIndex);
    }

    public Vector ceiled(int digitIndex) {
        return clone().ceil(digitIndex);
    }

    public Vector rounded(int digitIndex) {
        return clone().round(digitIndex);
    }

    public Vector floored() {
        return clone().floor();
    }

    public Vector ceiled() {
        return clone().ceil();
    }

    public Vector rounded() {
        return clone().round();
    }

    public Vector orthogonal() {
        if(equals(ZERO_VECTOR)) return new Vector(1);
        if(rotation().equals(new Rotation())) {
            return UNIT_VECTOR_Y.scaled(abs());
        }
        return crossProduct(this, UNIT_VECTOR_X).normalize().scale(abs());
    }









    public static Vector angledVector(Rotation rotation, double length) {
        return angledVector(rotation.z, rotation.y, length);
    }

    public static Vector angledVector(double angle, double length) {
        return angledVector(angle, 0, length);
    }

    public static Vector angledVector(double angleZ, double angleY, double length) {
        return new Vector(
            length * Math.cos(Math.toRadians(angleZ)) * Math.cos(Math.toRadians(angleY)),
            length * Math.sin(Math.toRadians(angleZ)) * Math.cos(Math.toRadians(angleY)),
            length * Math.sin(Math.toRadians(angleY))
        );
    }


    public static Vector between(Vector from, Vector to) {
        return to.subtracted(from);
    }

    public static double distance(Vector v, Vector w) {
        return between(v, w).abs();
    }

    
    public static Vector average(Vector... vectors) {
        if(vectors.length == 0) return null;
        Vector average = new Vector();
        for(Vector v : vectors) average.add(v);
        return average.divide(vectors.length);
    }




    public static double abs(Vector v) {
        return Math.sqrt(dotProduct(v, v));
    }

    public static Rotation rotation(Vector v) {
        Vector onX = new Vector(v.x, v.y);
        return new Rotation(angleBetween(onX, UNIT_VECTOR_X), angleBetween(onX, v));
    }

    public static double angleBetween(Vector v, Vector w) {
        //return smallestAngleBetween(v, w);
        Vector crossP = crossProduct(v, w);
        return crossP.x + crossP.y + crossP.z > 0 ? -smallestAngleBetween(v, w) : smallestAngleBetween(v, w);
    }

    public static double smallestAngleBetween(Vector v, Vector w) {
        if(v == null || w == null) throw new NullPointerException();
        if(v.equals(ZERO_VECTOR) || w.equals(ZERO_VECTOR)) return 0;
        return Math.toDegrees(Math.acos(dotProduct(v, w) / (v.abs() * w.abs())));
    }



    public static Vector add(Vector v, Vector w) {
        return v.added(w);
    }



    public static double dotProduct(Vector v, Vector w) {
        return v.x * w.x + v.y * w.y + v.z * w.z;
    }

    public static Vector crossProduct(Vector v, Vector w) {
        return new Vector(
            v.y * w.z - v.z * w.y,
            v.z * w.x - v.x * w.z,
            v.x * w.y - v.y * w.x
        );
    }

    public static double areaBetween(Vector v, Vector w){
        return crossProduct(v, w).abs();
    }


    public static Vector shortestBetween(Line line, Vector point) {
        double r = (
            line.direction.x * (point.x - line.start.x) + 
            line.direction.y * (point.y - line.start.y) + 
            line.direction.z * (point.z - line.start.z)) /
            dotProduct(line.direction, line.direction
        );
        return line.start.added(line.direction.scaled(r)).subtract(point);
    }


    public static double distanceBetween(Line line, Vector point) {
        return shortestBetween(line, point).abs();
    }


    public static Vector mirror(Vector mirroredVector, Vector mirror) {
        return mirroredVector.added(shortestBetween(new Line(mirror), mirroredVector).scale(2));
    }

    public static Vector reflect(Vector base, Vector reflect){
        return mirror(reflect, base).invert();
    }

    public static Vector project(Vector vectorToProject, Vector onto){
        return new Vector(onto).scale(dotProduct(vectorToProject, onto) / (onto.abs() * onto.abs()));
    }





    @Override
    public JsonObject getSaveData() {
        JsonObject data = new JsonObject();
        data.put("x", x);
        data.put("y", y);
        data.put("z", z);
        return data;
    }

    @Override
    public String getSaveDir() {
        return "saves\\geomentry\\vectors\\";
    }

    @Override
    public String getSaveName() {
        return "vector" + hashCode();
    }

    @Override
    public void load(Object data) {
        JsonObject jData = (JsonObject)data;
        x = jData.getDouble("x");
        y = jData.getDouble("y");
        z = jData.getDouble("z");
    }
}
