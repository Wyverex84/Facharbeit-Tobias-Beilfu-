package rccookie.box2d;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import greenfoot.World;

import rccookie.game.AdvancedActor;
import rccookie.geometry.Vector2D;

public abstract class Body extends AdvancedActor {

    private static final String WRONG_WORLD_STRING = "Cannot add a Box2D body to a non-Box2d world";
    
    private org.jbox2d.dynamics.Body body;


    protected Body(final BodyDef bodyDef, final FixtureDef... fixtureDefs) {
        addAddedAction(world -> added(world, bodyDef, fixtureDefs));
    }

    private void added(World world, BodyDef bodyDef, FixtureDef... fixtureDefs) {
        if(!(world instanceof GBox2DWorld)) throw new IllegalStateException(WRONG_WORLD_STRING);

        bodyDef.position.set(worldLoc(getLocation()));

        body = getWorld().createBody(bodyDef);

        if(fixtureDefs != null) {
            for(FixtureDef fixtureDef : fixtureDefs) if(fixtureDef != null) body.createFixture(fixtureDef);
        }
    }


    public void addToWorld(GBox2DWorld world, float realX, float realY) {
        world.addObject(this, (int)world.pixelLength(realX), (int)world.pixelLength(realY));
    }






    @Override
    protected final void physicsUpdate() {
        setLocation(pixelLoc(body.getPosition()));
        setRotation(-Math.toDegrees(body.getAngle()));
    }





    public org.jbox2d.dynamics.Body getBody() {
        return body;
    }















    public void setLocation(Vec2 location) {
        setLocation(new Vector2D(location.x, location.y));
    }



    public Vec2 worldLoc(Vec2 pixelLoc) {
        return getWorld().worldLoc(pixelLoc);
    }
    public Vec2 worldLoc(Vector2D pixelLoc) {
        return getWorld().worldLoc(pixelLoc);
    }
    public Vec2 worldLoc(double pixelX, double pixelY) {
        return getWorld().worldLoc(pixelX, pixelY);
    }

    public float worldLength(double pixelLength) {
        return getWorld().worldLength(pixelLength);
    }


    public Vec2 pixelLoc(Vec2 worldLoc) {
        return getWorld().pixelLoc(worldLoc);
    }

    public float pixelLength(double worldLength) {
        return getWorld().pixelLength(worldLength);
    }

    @Override
    public GBox2DWorld getWorld() {
        if(!(super.getWorld() instanceof GBox2DWorld)) throw new IllegalStateException(WRONG_WORLD_STRING);
        return (GBox2DWorld) super.getWorld();
    }





    public static Body createBody(BodyDef bodyDef, FixtureDef... fixtureDefs) {
        return new Body(bodyDef, fixtureDefs) {};
    }
}
