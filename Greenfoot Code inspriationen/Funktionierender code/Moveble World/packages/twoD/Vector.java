package packages.twoD;

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
public class Vector implements Cloneable{


    /**
     * Representation of a vector with the length 0.
     */
    public static final Vector ZERO_VECTOR = new Vector();

    /**
     * The unit vector that represents the length 1 in the direction of
     * the x axis.
     */
    public static final Vector UNIT_VECTOR_X = new Vector(1);
    
    /**
     * The unit vector that represents the length 1 in the direction of
     * the y axis.
     */
    public static final Vector UNIT_VECTOR_Y = new Vector(0, 1);



    /**
     * The x distance of the vector.
     */
    public double x;
    
    /**
     * The y distance of the vector.
     */
    public double y;



    /**
     * Creates a new zero vector.
     */
    public Vector(){
        this(0, 0);
    }

    /**
     * Creates a new vector from the given one.
     * The new Vector will be identical with the given one, but is a
     * different object.
     * 
     * @param copy The Vector to create the new vector from
     */
    public Vector(Vector copy){
        this(copy.x, copy.y);
    }

    /**
     * Creates a new vector with the given length parallel to the x axis.
     * 
     * @param x The x length of the vector
     */
    public Vector(double x){
        this(x, 0);
    }

    /**
     * Creates a new vector with the given x and y distances.
     * 
     * @param x The x distance of the new vector
     * @param y The y distance of the new vector
     */
    public Vector(double x, double y){
        this.x=x;
        this.y=y;
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
    public Vector clone(){
        return new Vector(this);
    }

    public String toString(){
        return "[" + x + "," + y + "]: " + super.toString();
    }

    public boolean equals(Object otherVector){
        if(otherVector instanceof Vector){
            return x == ((Vector)otherVector).x && y == ((Vector)otherVector).y;
        }
        return false;
    }






    /**
     * Returns the angle between the vector and the x axis.
     * 
     * @return The vectors angle
     */
    public double angle(){
        return angleBetween(this, UNIT_VECTOR_X);
    }

    /**
     * Returns the absolute of the vector. This represents its length.
     * 
     * @return The length of the vector
     */
    public double abs(){
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Inverts the vectors direction while keeping its length. This
     * changes the vector itself rather than creating a new one.
     * 
     * @return The Vector itself
     */
    public Vector invert(){
        return this.scale(-1);
    }

    /**
     * Normalizes this vector. This means that the vectors angle will be kept and the length
     * will be set to 1.
     * 
     * @return The vector itself
     */
    public Vector normalize(){
        if(equals(ZERO_VECTOR)) return this;
        return scale(1 / abs());
    }

    /**
     * Adds the given distance onto the vectors end with the same
     * angle as the vector had before.
     * 
     * @param length the distance to add
     * @return The Vector ifself
     */
    public Vector add(double length){
        return add(angledVector(angle(), length));
    }

    /**
     * Adds the given Vector onto the vector.
     * 
     * @param addedVector The Vector to add
     * @return The Vector itself
     */
    public Vector add(Vector addedVector){
        x += addedVector.x;
        y += addedVector.y;
        return this;
    }

    /**
     * Performs a scalar multiplication with the given value on
     * this Vector. This means the vector will be scaled to the
     * value as percentage.
     * 
     * @param factor The scalar to multiply with
     * @return The Vector itself
     */
    public Vector scale(double factor){
        x*=factor;
        y*=factor;
        return this;
    }
    
    /**
     * Divides the vector through the given scalar. This does the same as scaling by 1/s.
     * 
     * @param divident The scalar to divide through
     * @return The Vector itself
     */
    public Vector divide(double divident){
        return scale(1 / divident);
    }

    /**
     * Rotates the vector the specified amount of degrees.
     * 
     * @param angle The angle to rotate
     * @return The Vector itself
     * @see #rotated(double)
     */
    public Vector rotate(double angle){
        Vector rotated = rotated(angle);
        x = rotated.x;
        y = rotated.y;
        return this;
    }



    /**
     * Returns a new vector that is the normalized version of this vector.
     * 
     * @return A new vector that is the normalized version of this
     */
    public Vector normalized(){
        return new Vector(this).normalize();
    }

    /**
     * Returns a new vector with inverted values. This means that its
     * absolute will be identical with the input Vector and the angle
     * will differ to 180°. The Vector itself will stay unchanged.
     * 
     * @return The negative Vector
     */
    public Vector inverted(){
        return new Vector(this).invert();
    }

    /**
     * Returns the sum of itself and the given vector as a new one.
     * 
     * @param addedVector The Vector to add
     * @return The new vector; the sum of the two
     */
    public Vector added(Vector addedVector){
        return new Vector(this).add(addedVector);
    }

    /**
     * Returns a new vector that is the scaled version of this vector
     * 
     * @param factor The factor to scale by
     * @return The scaled version of this vector
     */
    public Vector scaled(double factor){
        return new Vector(this).scale(factor);
    }

    /**
     * Returns a new vector that is the divided version of this vector
     * 
     * @param divident The scalar to devide by
     * @return The divided version of this vector
     */
    public Vector divided(double divident){
        return new Vector(this).divide(divident);
    }

    /**
     * Returns a rotated version of this vector.
     * <p>The length of the new Vector will be equal, and
     * the difference between the initial vectors angle and the new vectors
     * angle will be the given angle (or more precicely, the remainder of the angle devided
     * by 360).
     * 
     * @param angle The angle to rotate
     * @return The new rotated version of this vector
     */
    public Vector rotated(double angle){
        return angledVector(angle() + angle, abs());
    }





    /**
     * Returns a new Vector that is orthogonal to this vector and has the
     * same length.
     * 
     * @return A new vector orthogonal to this one
     */
    public Vector orthogonal(){
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
    public static Vector angledVector(double angle, double length){
        return new Vector(
            length * Math.cos(Math.toRadians(angle)),
            length * Math.sin(Math.toRadians(angle))
        );
    }

    /**
     * Creates a new Vector that points from {@code v} to {@code w}.
     * 
     * @param vectorA The start point of the vector
     * @param vectorB The end point of the vector
     * @return The vector between {@code v} and {@code w}
     */
    public static Vector between(Vector vectorA, Vector vectorB){
        return new Vector(vectorB).add(vectorA.inverted());
    }

    /**
     * Returns a new vector representing the sum of the two given
     * vectors.
     * 
     * @param vectorA The first vector
     * @param vectorB The second Vector
     * @return The sum of the vectors
     */
    public static Vector add(Vector vectorA, Vector vectorB){
        return new Vector(vectorA).add(vectorB);
    }

    /**
     * Returns the angle between the two given vectors. If any of the
     * given vectors is a zero vector, the result will be 0.
     * 
     * @param vectorA The first vector
     * @param vectorB The second vector
     * @return The angle between the vectors
     */
    public static double angleBetween(Vector vectorA, Vector vectorB){
        if(vectorA.equals(ZERO_VECTOR) || vectorB.equals(ZERO_VECTOR)) return 0;
        Vector vNormal = vectorA.normalized(), wNormal = vectorB.normalized();
        double result = Math.toDegrees(Math.atan2(vNormal.y, vNormal.x) - Math.atan2(wNormal.y, wNormal.x));
        result += (result >= 0)? 0 : 360;
        return result;
    }

    public static double smallestAngle(Vector v, Vector w){
        return Math.toDegrees(Math.acos(dotProduct(v, w) / (v.abs() * w.abs())));
    }

    /**
     * Returns the area of the parallelogram that the two vectors span.
     * 
     * @param vectorA The first vector
     * @param vectorB The second Vector
     * @return The area the vectors span
     */
    public static double areaBetween(Vector vectorA, Vector vectorB){
        return vectorA.abs() * vectorB.abs() * Math.cos(angleBetween(vectorA, vectorB));
    }

    /**
     * Returns the dot product of the two vectors.
     * 
     * @param vectorA The first vector
     * @param vectorB The second vector
     * @return The dot protuct of the two vectors
     */
    public static double dotProduct(Vector vectorA, Vector vectorB){
        return vectorA.x * vectorB.x + vectorA.y * vectorB.y;
    }

    /**
     * Returns a new vector that represents the reflected version of
     * {@code reflect} reflecting of {@code base}.
     * 
     * @param base The vector to reflect from
     * @param reflect The vector that reflects from{@code base}
     * @return A new vector representing the reflection of {@code reflect} form {@code base}
     */
    public static Vector reflect(Vector base, Vector reflect){
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
    public static Vector project(Vector vectorToProject, Vector onto){
        return new Vector(onto).scale(dotProduct(vectorToProject, onto) / Math.pow(onto.abs(), 2));
    }




    public static void main(String[] args) {
        System.out.println(smallestAngle(new Vector(2, 0), new Vector(3, -3)));
    }
}