package rccookie.rendering;

import rccookie.geometry.Line;
import rccookie.geometry.Vector;

public class RenderSphere extends RenderPanel {
    public final double radius;
    public Vector offset;

    public RenderSphere(double radius) {
        this(radius, new Vector());
    }
    public RenderSphere(double radius, Vector offset) {
        this.radius = radius;
        this.offset = offset;
    }

    @Override
    public Line getReflection(Line ray) {
        Vector hitLoc = getIntersectionLoc(ray);
        if(hitLoc == null) return null;

        Vector reflectionNorm = Vector.between(location().added(offset), hitLoc);
        Vector reflected = Vector.reflect(ray.direction, reflectionNorm);

        return new Line(hitLoc, reflected);
    }

    @Override
    public Vector getIntersectionLoc(Line ray) {
        Vector centerToCamera = Vector.between(location().added(offset).added(offset), ray.start);
        double dirXctc = Vector.dotProduct(ray.direction, centerToCamera);

        double delta = Math.pow(Vector.dotProduct(ray.direction, (centerToCamera)), 2) - (Math.pow(centerToCamera.abs(), 2) - radius * radius);

        if(delta < 0) return null;
        
        double rtDelta = Math.sqrt(delta);

        //Always smaller or equal to the smaller hit
        double hitIndex = -dirXctc - rtDelta;

        if(delta == 0 || hitIndex >= 0) {
            if(hitIndex < 0) return null;
            return ray.direction.scaled(hitIndex).add(ray.start);
        }

        //Only try if first point is behind camera (remove if no rendering from insides)
        hitIndex = -dirXctc + rtDelta;
        if(hitIndex < 0) return null;
        return ray.direction.scaled(hitIndex).add(ray.start);
    }


    @Override
    public boolean intersects(Line ray) {
        return Vector.distanceBetween(ray, location().added(offset)) <= radius;
    }
}
