package rccookie.geometry;

import rccookie.data.json.JsonObject;

/**
 * The Vector class is used to store an arrow using containing an x and
 * a y distance. It is commonly used in physics or graphics math.<p>
 * Unlike the official Vector, this one is not a type of list and cannot
 * contain a variable number of dimensions. Also, it can only store two
 * doubles and no other types of objects. Therefore, it contains a number
 * of helpful methods to interact with it in a mathmatical way which is
 * not supported by the official Vector class.
 * 
 * @author RcCookie
 * @version 2.1
 */
public class Vector2D extends Vector {

    /**
     * Creates a new zero vector.
     */
    public Vector2D(){
        this(0, 0);
    }

    /**
     * Creates a new vector from the given one.
     * The new Vector will be identical with the given one, but is a
     * different object.
     * 
     * @param copy The Vector to create the new vector from
     */
    public Vector2D(Vector2D copy){
        this(copy.x, copy.y);
    }

    public Vector2D(Vector copy) {
        this(make2D(copy));
    }

    /**
     * Creates a new vector with the given length parallel to the x axis.
     * 
     * @param x The x length of the vector
     */
    public Vector2D(double x){
        this(x, 0);
    }

    /**
     * Creates a new vector with the given x and y distances.
     * 
     * @param x The x distance of the new vector
     * @param y The y distance of the new vector
     */
    public Vector2D(double x, double y){
        super(x, y);
    }




    /**
     * Creates and returns a copy of this vector. This means, that for the vector v
     * <blockquote>
     * <pre>
     * x.clone() == x</pre></blockquote>
     * will be false, and
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be true. In other words, the returned vector will have the same x and y
     * length than the cloned vector.
     */
    @Override
    public Vector2D clone(){
        return new Vector2D(this);
    }

    @Override
    public String toString(){
        return "[" + x + "|" + y + "]";//: " + getClass().getName();
    }

    /**
     * Adds the given Vector onto the vector.
     * 
     * @param addedVector The Vector to add
     * @return The Vector itself
     */
    @Override
    public Vector2D add(Vector addedVector){
        if(addedVector != null) {
            x += addedVector.x;
            y += addedVector.y;
        }
        return this;
    }

    @Override
    public Vector2D add(double length) {
        return (Vector2D)super.add(length);
    }

    @Override
    public Vector2D added(Vector addedVector) {
        return (Vector2D)super.add(addedVector);
    }

    @Override
    public Vector2D added(double length) {
        return (Vector2D)super.added(length);
    }


    @Override
    public Vector2D subtract(Vector subtractedVector) {
        return (Vector2D)super.subtract(subtractedVector);
    }

    @Override
    public Vector2D subtract(double length) {
        return (Vector2D)super.subtract(length);
    }

    @Override
    public Vector2D subtracted(Vector subtractedVector) {
        return (Vector2D)super.subtracted(subtractedVector);
    }

    @Override
    public Vector2D subtracted(double length) {
        return (Vector2D)super.subtracted(length);
    }



    @Override
    public Vector2D scale(double factor) {
        return (Vector2D)super.scale(factor);
    }

    @Override
    public Vector2D scaled(double factor) {
        return (Vector2D)super.scaled(factor);
    }


    @Override
    public Vector2D divide(double divident) {
        return (Vector2D)super.divide(divident);
    }

    @Override
    public Vector2D divided(double divident) {
        return (Vector2D)super.divided(divident);
    }


    @Override
    public Vector2D inverted() {
        return (Vector2D)super.inverted();
    }

    @Override
    public Vector2D invert() {
        return (Vector2D)super.invert();
    }

    
    @Override
    public Vector2D normalize() {
        return (Vector2D)super.normalize();
    }

    @Override
    public Vector2D normalized() {
        return (Vector2D)super.normalized();
    }





    @Override
    public Vector2D rotate(double angle) {
        return (Vector2D)super.rotate(angle);
    }

    @Override
    public Vector2D rotate(Rotation rotation) {
        return (Vector2D)super.rotate(rotation);
    }

    @Override
    public Vector2D rotated(double angle) {
        return (Vector2D)super.rotated(angle);
    }

    @Override
    public Vector2D rotated(Rotation rotation) {
        return make2D(super.rotated(new Rotation(rotation.z)));
    }





    @Override
    public Vector2D floor(int digitIndex) {
        return (Vector2D)super.floor(digitIndex);
    }

    @Override
    public Vector2D ceil(int digitIndex) {
        return (Vector2D)super.ceil(digitIndex);
    }

    @Override
    public Vector2D round(int digitIndex) {
        return (Vector2D)super.round(digitIndex);
    }

    @Override
    public Vector2D floor() {
        return (Vector2D)super.floor();
    }

    @Override
    public Vector2D ceil() {
        return (Vector2D)super.ceil();
    }

    @Override
    public Vector2D round() {
        return (Vector2D)super.round();
    }

    
    @Override
    public Vector2D floored(int digitIndex) {
        return (Vector2D)super.floored(digitIndex);
    }

    @Override
    public Vector2D ceiled(int digitIndex) {
        return (Vector2D)super.ceiled(digitIndex);
    }

    @Override
    public Vector2D rounded(int digitIndex) {
        return (Vector2D)super.rounded(digitIndex);
    }

    @Override
    public Vector2D floored() {
        return (Vector2D)super.floored();
    }

    @Override
    public Vector2D ceiled() {
        return (Vector2D)super.ceiled();
    }

    @Override
    public Vector2D rounded() {
        return (Vector2D)super.rounded();
    }








    /**
     * Returns a new Vector that is orthogonal to this vector and has the
     * same length.
     * 
     * @return A new vector orthogonal to this one
     */
    @Override
    public Vector2D orthogonal(){
        return angledVector(angle() + 90, abs());
    }



    

    /**
     * Creates a new vector with the given angle and length in that
     * direction. If the length is negative, the vector will point in
     * the opposite direction.
     * 
     * @param angle The angle of the new Vector
     * @param length The length of the new vector
     */
    public static Vector2D angledVector(double angle, double length){
        return new Vector2D(
            length * Math.cos(Math.toRadians(angle)),
            length * Math.sin(Math.toRadians(angle))
        );
    }

    /**
     * Creates a new Vector that points from {@code v} to {@code w}.
     * 
     * @param from The start point of the vector
     * @param to The end point of the vector
     * @return The vector between {@code v} and {@code w}
     */
    public static Vector2D between(Vector2D from, Vector2D to){
        return make2D(Vector.between(from, to));
    }

    
    
    public static Vector2D average(Vector2D... vectors) {
        if(vectors.length == 0) return null;
        Vector2D average = new Vector2D();
        for(Vector2D v : vectors) average.add(v);
        return average.divide(vectors.length);
    }

    /**
     * Returns a new vector representing the sum of the two given
     * vectors.
     * 
     * @param w The first vector
     * @param v The second Vector
     * @return The sum of the vectors
     */
    public static Vector2D add(Vector2D v, Vector2D w){
        return make2D(Vector.add(v, w));
    }

    /**
     * Returns the angle between the two given vectors. If any of the
     * given vectors is a zero vector, the result will be 0.
     * 
     * @param v The first vector
     * @param w The second vector
     * @return The angle between the vectors
     */
    public static double angleBetween(Vector2D v, Vector2D w){
        if(v.equals(ZERO_VECTOR) || w.equals(ZERO_VECTOR)) return 0;
        Vector2D vNormal = v.normalized(), wNormal = w.normalized();
        double result = Math.toDegrees(Math.atan2(vNormal.y, vNormal.x) - Math.atan2(wNormal.y, wNormal.x));
        result += (result >= 0)? 0 : 360;
        return result;
    }

    /**
     * Returns a new vector that represents the reflected version of
     * {@code reflect} reflecting of {@code base}.
     * 
     * @param base The vector to reflect from
     * @param reflect The vector that reflects from{@code base}
     * @return A new vector representing the reflection of {@code reflect} form {@code base}
     */
    public static Vector2D reflect(Vector2D base, Vector2D reflect){
        return angledVector(base.angle() + angleBetween(base, reflect), reflect.abs());
    }




    /**
     * Returns a new vector that represents the orthogonal
     * projection of the first vector onto the second one.
     * If the result is zero, the first vector is a zero
     * Vector or the two vectors are orthogonal.
     * 
     * @param vectorToProject The Vector th be projected
     * @param onto The Vector to be projected onto
     * @return The representation of the projection
     * @throws ArithmeticException If the vector to project
     * onto is a zero Vector
     */
    public static Vector2D project(Vector2D vectorToProject, Vector2D onto){
        return make2D(Vector.project(vectorToProject, onto));
    }





    public static Vector2D make2D(Vector maybeNot2D) {
        if(maybeNot2D == null) return null;
        return maybeNot2D instanceof Vector2D ? (Vector2D)maybeNot2D : new Vector2D(maybeNot2D.x, maybeNot2D.y);
    }


    @Override
    public JsonObject getSaveData() {
        JsonObject data = new JsonObject();
        data.put("x", x);
        data.put("y", y);
        data.put("z", 0d);
        return data;
    }

    @Override
    public String getSaveName() {
        return "vector2D" + hashCode();
    }

    @Override
    public void load(Object data) {
        JsonObject jData = (JsonObject)data;
        x = jData.getDouble("x");
        y = jData.getDouble("y");
        z = 0;
    }




    public static void main(String[] args) {
        
    }
}