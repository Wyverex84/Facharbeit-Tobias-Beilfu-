package packages.physics;

import greenfoot.*;
import packages.twoD.*;

/**
 * A rectangular collider that has the same size as its image.
 */
public class BoxCollider extends Collider{

    /**
     * Constructs a new box collider with the given actor as host, the given offset to the host and the given size.
     * 
     * @param host The colliders host
     * @param offset The offset of the collider to the host
     * @param size The size of the collider
     */
    public BoxCollider(Actor host, Transform offset, Vector size){
        super(host, offset, size);
    }

    /**
     * Constructs a new box collider with the given actor as host and no offset. The size will be identical to the actors image size.
     * 
     * @param host The colliders host
     */
    public BoxCollider(Actor host){
        this(host, new Transform(), new Vector(host.getImage().getWidth(), host.getImage().getHeight()));
    }

    /**
     * Constructs a new box collider with the given offset to the host and the given size. The host actor should be assigned later on.
     * 
     * @param offset The offset of the collider to the host
     * @param size The size of the collider
     */
    public BoxCollider(Transform offset, Vector size){
        this(null, offset, size);
    }

    /**
     * Constructs a new box collider with no offset and the given size. The host actor should be assigned later on.
     * 
     * @param size The size of the collider
     */
    public BoxCollider(Vector size){
        this(null, new Transform(), size);
    }
    
    public boolean intersects(Actor a){
        if(isIgnored(a)) return false;
        if(a instanceof CircleCollider){
            return BoxCollider.intersecting(this, (CircleCollider)a);
        }
        return imageIntersects(a);
    }
    
    public int getArea(){
        return getImage().getWidth() * getImage().getHeight();
    }
    
    public Vector getWorldEdge(){
        for(Vector corner : getCorners()){
            if(corner.x <= 0){
                if(corner.y <= 0) return Vector.angledVector(-45, 1);
                if(corner.y >= getWorld().getHeight() - 1)
                    return Vector.angledVector(45, 1);
                return new Vector(0, 1);
            }
            if(corner.x >= getWorld().getWidth() - 1){
                if(corner.y <= 0) return Vector.angledVector(45, 1);
                if(corner.y >= getWorld().getHeight() - 1)
                    return Vector.angledVector(-45, 1);
                return new Vector(0, 1);
            }
            if(corner.y <= 0 || corner.y >= getWorld().getHeight() - 1) return new Vector(1, 0);
        }
        return null;
    }
    
    /**
     * Returns an array of vectors pointing at the locations of the four corners of the collider.
     * 
     * @return Vectors to the corners of the collider
     */
    protected Vector[] getCorners(){
        Vector[] boxCorner = new Vector[4];
        for(int i=0; i<boxCorner.length; i++){
            boxCorner[i] = new Vector(getX(), getY());
        }
        int boxX = getImage().getWidth() / 2, boxY = getImage().getHeight() / 2;
        boxCorner[0].add(Vector.angledVector(getRotation(), boxX));
        boxCorner[0].add(Vector.angledVector(getRotation() + 90, boxY));
        boxCorner[1].add(Vector.angledVector(getRotation(), -boxX));
        boxCorner[1].add(Vector.angledVector(getRotation() + 90, boxY));
        boxCorner[2].add(Vector.angledVector(getRotation(), -boxX));
        boxCorner[2].add(Vector.angledVector(getRotation() + 90, -boxY));
        boxCorner[3].add(Vector.angledVector(getRotation(), boxX));
        boxCorner[3].add(Vector.angledVector(getRotation() + 90, -boxY));
        return boxCorner;
    }
    
    /**
     * Returns weather the given box collider and the given circle collider intersect.
     * @param box The box collider
     * @param circle The circle collider
     * @return Weather the two colliders intersect
     */
    static boolean intersecting(BoxCollider box, CircleCollider circle){
        return getCollNorm(box, circle) != null;
    }
    
    /**
     * Returns a vector of the length one that points from the box collider towards the circle collider in the 
     * direction of the physical impact of the colliders. If there is no collision, null will be returned.
     * 
     * @param box The box collider
     * @param circle The circle collider
     * @return A vector that has the direction of the physical impact towards the circleCollider with the length 1
     */
    public static Vector getCollNorm(BoxCollider box, CircleCollider circle){
        //checking corners
        Vector[] boxCorner = box.getCorners();
        for(Vector corner : boxCorner){
            if(new Vector(circle.getX() - corner.x, circle.getY() - corner.y).abs() < circle.radius) 
                return new Vector(corner).add(new Vector(-circle.getX(), -circle.getY())).normalize();
                //corner.normalize();
        }
        
        //checking edges
        
        Vector m = new Vector(circle.getX(), circle.getY());
        for(int i=0; i<4; i++){
            Vector c = boxCorner[i];
            Vector e = Vector.angledVector(90 * i + box.getRotation() + 180, 1);
            double r = (e.x * (m.x - c.x) + e.y + (m.y - c.y)) / (e.x * e.x + e.y * e.y);
            Vector d = new Vector(c.x + r * e.x - m.x, c.y + r * e.y - m.y);
            if(d.abs() < circle.radius){
                if(r >= 0 && ((i % 2 == 0 && r <= box.getImage().getWidth()) || r <= box.getImage().getHeight())){
                    return Vector.angledVector(e.angle() + 90, 1);
                }
            }
        }
        return null;
    }
    
    public Vector getEdgeTowards(Actor a){
        double cornerAngle[] = new double[4];
        for(int i=0; i<4; i++) cornerAngle[i] = new Vector(getCorners()[i]).add(new Vector(-getX(), -getY())).angle();
        double actorAngle = new Vector(a.getX() - getX(), a.getY() - getY()).angle();
        if(actorAngle < cornerAngle[0]) return Vector.angledVector(getRotation() + 90, 1);
        if(actorAngle < cornerAngle[1]) return Vector.angledVector(getRotation(), 1);
        if(actorAngle < cornerAngle[2]) return Vector.angledVector(getRotation() + 90, 1);
        if(actorAngle < cornerAngle[3]) return Vector.angledVector(getRotation(), 1);
        return Vector.angledVector(getRotation() + 90, 1);
    }
}