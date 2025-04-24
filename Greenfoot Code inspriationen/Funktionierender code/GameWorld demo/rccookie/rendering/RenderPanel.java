package rccookie.rendering;

import greenfoot.Color;
import rccookie.geometry.Line;
import rccookie.geometry.Rotation;
import rccookie.geometry.Transform;
import rccookie.geometry.Vector;

public abstract class RenderPanel {
    

    /**
     * Returns a ray that describes the reflection of the given ray on this object.
     * If the ray returned is {@code null}, the ray did not hit the object or did not get effected by it.
     * 
     * @param ray The ray to get the reflection for
     * @return The reflection ray, or {@code null}
     */
    public abstract Line getReflection(Line ray);


    /**
     * Indicates weather a given ray hits this object. This may be the same as invoking
     * {@code getReflection(ray) != null} but does not neccecarily have to be equal for edge cases.
     * 
     * @param ray The ray to check collision for
     * @return Weather the given ray hits this object
     */
    public abstract boolean intersects(Line ray);


    /**
     * Returns the point at which the given ray hits the object, or {@code null} if it does not hit the object
     * at all.
     * 
     * @param ray The ray to check intersection for
     * @return The location of the hit, or {@code null}
     */
    public abstract Vector getIntersectionLoc(Line ray);



    public RenderPanel() {
        this(new Transform());
    }

    public RenderPanel(Transform transform) {
        this.transform = transform;
    }



    private Transform transform;


    
    public Vector location() {
        return transform.location.clone();
    }

    
    public void setLocation(Vector location) {
        if(location == null) return;
        transform.location = location.clone();
    }

    
    public Rotation rotation() {
        return transform.rotation.clone();
    }

    
    public void setRotation(Rotation rotation) {
        if(rotation == null) return;
        transform.rotation = rotation.clone();
    }

    
    public Vector scale() {
        return transform.scale.clone();
    }

    
    public void setScale(Vector scale) {
        if(scale == null) return;
        transform.scale = scale.clone();
    }

    
    public Transform transform() {
        return transform.clone();
    }

    
    public void setTransform(Transform transform) {
        if(transform == null) return;
        this.transform = transform;
    }





    public static final Color DEF_COLOR = Color.GRAY;

    private Color color = DEF_COLOR;


    public Color color() {
        return color;
    }
    public Color pureColor() {
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }
    public double transparency() {
        return color.getAlpha();
    }

    public void setColor(Color color) {
        if(color == null) return;
        this.color = color;
    }
}
