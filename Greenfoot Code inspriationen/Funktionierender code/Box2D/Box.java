import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import greenfoot.Color;
import greenfoot.GreenfootImage;

public class Box extends rccookie.box2d.Body {
    float width, height;

    public Box(final float width, final float height) {
        super(getBodyDef(), getFixtureDef());
        this.width = width;
        this.height = height;

        GreenfootImage image = new GreenfootImage((int) width, (int) height);
        image.setColor(Color.BLUE);
        image.fill();
        setImage(image);
    }

    private static final FixtureDef getFixtureDef() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;
        return fixtureDef;
    }

    private static final BodyDef getBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        return bodyDef;
    }
}
