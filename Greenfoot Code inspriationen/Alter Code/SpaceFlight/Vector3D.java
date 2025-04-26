/**
 * Vector3D is a minimal 3-tuple.
 * 
 * @author Michael Lastufka
 * @version Dec 2008
 */
public class Vector3D
{
    public double x;
    public double y;
    public double z;

    /**
     * Create a simple Vector3D with no particular units.
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @param z the z-coordinate.
     */
    public Vector3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Adds v to this vector to get a new vector.
     * @param v a vector.
     * @return the addition of the two vectors.
     */
    public Vector3D add(Vector3D v)
    {
        return new Vector3D(x + v.x, y + v.y, z + v.z);
    }
    
    /**
     * Scales a vector producing a new one.
     * @param factor how much to multiply by.
     * @return a new vector, scaled to the factor.
     */
    public Vector3D scale(double factor)
    {
        return new Vector3D(factor*x, factor*y, factor*z);
    }
    
    /**
     * Rotate a vector about the z-axis angle degrees.
     * Positive angle turns clock-wise.
     * Assume a right-handed coordinate frame.
     * @param angle the amount to rotate.
     * @return a new rotated vector.
     **/
    public Vector3D rotateAroundZ(double angle)
    {
        double ang = Math.toRadians(angle);
        double s = x*Math.cos(ang) - y*Math.sin(ang);
        double t =  x*Math.sin(ang) + y*Math.cos(ang);
        return new Vector3D(s, t, z);
    }
    
    /**
     * Rotate a vector about the x-axis angle degrees.
     * Positive angle turns clock-wise.
     * Assume a right-handed coordinate frame.
     * @param angle the amount to rotate.
     * @return a new rotated vector.
     **/
    public Vector3D rotateAroundX(double angle)
    {
        double ang = Math.toRadians(angle);
        double t = y*Math.cos(ang) - z*Math.sin(ang);
        double u =  y*Math.sin(ang) + z*Math.cos(ang);
        return new Vector3D(x, t, u);
    }
    
    /**
     * Rotate a vector about the y-axis angle degrees.
     * Positive angle turns clock-wise.
     * Assume a right-handed coordinate frame.
     * @param angle the amount to rotate.
     * @return a new rotated vector.
     **/
    public Vector3D rotateAroundY(double angle)
    {
        double ang = Math.toRadians(angle);
        double u = z*Math.cos(ang) - x*Math.sin(ang);
        double s =  z*Math.sin(ang) + x*Math.cos(ang);
        return new Vector3D(s, y, u);
    }
    
    /**
     * Returns axis coordinate by axis index.
     * x = 0, y = 1, z = 2. Makes it easier to use Vectors in loops.
     * @param the axis index, 0 to 2.
     * @return the coordinate value for the specified axis or Double.NaN if not an axis..
     */
    public double axis (int axisInd)
    {
        if (axisInd == 0) return x;
        if (axisInd == 1) return y;
        if (axisInd == 2) return z;
        return Double.NaN;
    }
}
