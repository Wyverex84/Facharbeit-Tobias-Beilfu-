package rccookie.box2d.prefabs;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.FixtureDef;

public class CircleDef extends FixtureDef {
    public CircleDef(float radius) {
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        this.shape = shape;
        density = 1;
        friction = 0.2f;
        restitution = 0.5f;
    }
}
