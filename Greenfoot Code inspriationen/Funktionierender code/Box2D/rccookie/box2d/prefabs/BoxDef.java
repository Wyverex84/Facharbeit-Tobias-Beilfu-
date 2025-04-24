package rccookie.box2d.prefabs;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.FixtureDef;

public class BoxDef extends FixtureDef {

    public BoxDef(float width, float height) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        this.shape = shape;
        density = 1;
        friction = 0.2f;
        restitution = 0.5f;
    }
}
