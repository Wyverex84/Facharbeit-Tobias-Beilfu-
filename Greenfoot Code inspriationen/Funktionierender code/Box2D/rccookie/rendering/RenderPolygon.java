package rccookie.rendering;

import java.util.ArrayList;
import java.util.List;

import rccookie.geometry.Line;
import rccookie.geometry.Vector;

public class RenderPolygon extends RenderPanel {

    public static final double DEF_SIZE = 10;

    public static final double RANGE = 0.0000001;
    public static final double MIN_FOR_INSIDE = 360 - RANGE;

    private final List<Vector> vertexes = new ArrayList<>();



    public RenderPolygon() {
        this(defVertexes());
    }
    private static List<Vector> defVertexes() {
        List<Vector> vertexes = new ArrayList<>();
        vertexes.add(new Vector(0, -DEF_SIZE, -DEF_SIZE));
        vertexes.add(new Vector(0, -DEF_SIZE, DEF_SIZE));
        vertexes.add(new Vector(0, DEF_SIZE, DEF_SIZE));
        vertexes.add(new Vector(0, DEF_SIZE, -DEF_SIZE));
        return vertexes;
    }
    public RenderPolygon(List<Vector> vertexes) {
        if(vertexes.size() < 3) throw new IllegalArgumentException("a polygon cannot consist of only " + vertexes.size() + " vertexes");
        this.vertexes.addAll(vertexes);
    }




    @Override
    public Line getReflection(Line ray) {
        
        if(!intersects(ray)) return null;

        return new Line(
            getHitOnPlane(ray),
            Vector.reflect(
                getNormal(),
                ray.direction
            )
        );
    }

    @Override
    public Vector getIntersectionLoc(Line ray) {
        Vector hit = getHitOnPlane(ray);
        return isInside(hit) ? hit : null;
    }


    private boolean isInside(Vector loc) {
        if(loc == null) return false;

        double angleSum = 0;

        for(int i=0; i<vertexes.size(); i++) {
            Vector v1 = Vector.between(loc, vertexes.get(i));
            Vector v2 = Vector.between(loc, vertexes.get((i+1) % vertexes.size()));
            angleSum += v1.angleTo(v2);
        }
        return angleSum >= MIN_FOR_INSIDE;
    }


    private Vector getHitOnPlane(Line ray) {
        Vector normal = getNormal();
        double dotNormDir = Vector.dotProduct(normal, ray.direction);
        if(Math.abs(dotNormDir) < RANGE) return null;

        return ray.get((Vector.dotProduct(normal, location()) - Vector.dotProduct(normal, ray.start)) / dotNormDir);
    }


    private Vector getNormal() {
        return Vector.crossProduct(
            Vector.between(vertexes.get(0), vertexes.get(1)),
            Vector.between(vertexes.get(0), vertexes.get(2))
        );
    }



    @Override
    public boolean intersects(Line ray) {
        return getIntersectionLoc(ray) != null;
    }
}
