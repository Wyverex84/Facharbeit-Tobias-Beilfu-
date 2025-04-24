import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import rccookie.box2d.Body;
import rccookie.box2d.prefabs.CircleDef;

public class Circle extends Body {

    public Circle(final float radius, float density, float bounce) {
        super(getBodyDef(), getFixtureDef(radius, density, bounce));

        addAddedAction(w -> {
            GreenfootImage image = new GreenfootImage((int)(2 * radius * ((MyWorld)w).UNIT), (int)(2 * radius * ((MyWorld)w).UNIT));
            image.setColor(Color.RED);
            image.fillOval(0, 0, (int)(2 * radius * ((MyWorld)w).UNIT) - 1, (int)(2 * radius * ((MyWorld)w).UNIT) - 1);
            setImage(image);
        });
    }

    private static final FixtureDef getFixtureDef(float radius, float density, float bounce) {
        FixtureDef def = new CircleDef(radius);
        def.density = density;
        def.restitution = bounce;
        return def;
    }

    private static final BodyDef getBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        return bodyDef;
    }
}
