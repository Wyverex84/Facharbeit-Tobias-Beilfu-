package rccookie.box2d;

import java.util.List;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import greenfoot.Actor;
import greenfoot.GreenfootImage;
import rccookie.game.AdvancedActor;
import rccookie.geometry.Vector2D;
import rccookie.ui.basic.UIWorld;
import rccookie.util.Lists;

import static rccookie.util.Tags.CLASS_TAGS;

public abstract class GBox2DWorld extends UIWorld {

    public static final float DEFAULT_UNIT = 10;
    


    public final float UNIT;
    private final World world;





    public GBox2DWorld(int pixelWidth, int pixelHeight) {
        this(pixelWidth, pixelHeight, DEFAULT_UNIT);
    }

    public GBox2DWorld(int pixelWidth, int pixelHeight, boolean gravity) {
        this(pixelWidth, pixelHeight, DEFAULT_UNIT, gravity, false);
    }

    public GBox2DWorld(int pixelWidth, int pixelHeight, float unit) {
        this(pixelWidth, pixelHeight, unit, true, false);
    }

    public GBox2DWorld(int pixelWidth, int pixelHeight, float unit, boolean gravity, boolean physicalBouded) {
        super(pixelWidth, pixelHeight, 1, false);
        UNIT = unit;
        world = new World(new Vec2(0, gravity ? -9.81f * 2 : 0));

        if(physicalBouded) addObject(new WorldBounds(pixelWidth / UNIT, pixelHeight / UNIT), 0, 0);
    }




    @Override
    public final void act() {
        earlyUpdate();
        world.step((float)time().deltaTime(), (int)(5 * time().timeScale * 0.5), (int)(4 + time().timeScale * 0.5));
        update();
    }

    public void earlyUpdate() { }

    public void update() { }









    public Body createBody(BodyDef bodyDef) {
        return world.createBody(bodyDef);
    }






    public Vec2 worldLoc(Vec2 pixelLoc) {
        return worldLoc(pixelLoc.x, pixelLoc.y);
    }
    public Vec2 worldLoc(Vector2D pixelLoc) {
        return worldLoc(pixelLoc.x, pixelLoc.y);
    }
    public Vec2 worldLoc(double pixelX, double pixelY) {
        return new Vec2(worldLength(pixelX), -worldLength(pixelY));
    }

    public float worldLength(double pixelLength) {
        return (float)(pixelLength / UNIT);
    }


    public Vec2 pixelLoc(Vec2 worldLoc) {
        return new Vec2(pixelLength(worldLoc.x), -pixelLength(worldLoc.y));
    }

    public float pixelLength(double worldLength) {
        return (float)worldLength * UNIT;
    }







    @Override
    public <A> List<A> getObjects(Class<A> cls) {
        return Lists.only(super.getObjects(cls), o -> !(CLASS_TAGS.isTagged(o.getClass(), "hidden")));
    }

    @Override
    public void addObject(Actor object, int x, int y) {
        super.addObject(object, x, y);
    }

    public void addObject(Actor object, double x, double y) {
        super.addObject(object, (int)x, (int)y);
        if(object instanceof AdvancedActor) ((AdvancedActor)object).setLocation(x, y);
    }


    @Override
    public void removeObject(Actor object) {
        super.removeObject(object);
        if(object instanceof rccookie.box2d.Body) world.destroyBody(((rccookie.box2d.Body)object).getBody());
    }



    protected World getJBox2DWorld() { return world; }



    private static class WorldBounds extends rccookie.box2d.Body {
        static {
            CLASS_TAGS.tag(WorldBounds.class, "hidden");
        }
        private WorldBounds(final float width, final float height) {
            super(new BodyDef(), getFixtureDef(width, -height));
            setImage((GreenfootImage)null);
        }
        private static final FixtureDef getFixtureDef(float width, float height) {
            ChainShape shape = new ChainShape();
            Vec2[] corners = {
                new Vec2(),
                new Vec2(0, height),
                new Vec2(width, height),
                new Vec2(width, 0)
            };
            shape.createLoop(corners, 4);
            FixtureDef def = new FixtureDef();
            def.shape = shape;
            return def;
        }
    }
}
