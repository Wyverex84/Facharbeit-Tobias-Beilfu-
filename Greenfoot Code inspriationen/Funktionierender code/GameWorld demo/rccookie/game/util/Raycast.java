package rccookie.game.util;

import greenfoot.*;
/**
 * An abstract class containing some static methods to use for raycasts.
 * Up to 5000 raycasts per frame at 100 fps.
 */
public abstract class Raycast{

    public static final int DEFAULT_DISTANCE = 1000;

    public static RaycastResult raycast(
        World world,
        double x,
        double y,
        int direction,
        int maxDistance,
        RaycastCondition condition
    ){
        if(world == null) return null;

        int length = 0;
        double delta = Math.abs(Math.sin(direction % 180)) + Math.abs(Math.cos(direction % 180));

        boolean success = false;

        while(x >= 0 && y >= 0 && x < world.getWidth() && y < world.getHeight() && length <= maxDistance){
            if(condition.collidingAt((int)x, (int)y, world, length)){
                success = true;
                break;
            }

            length += delta;

            double radians = Math.toRadians(direction);
            x += Math.cos(radians) * delta;
            y += Math.sin(radians) * delta;
        }
        
        return new RaycastResult(
            (int)length,
            (int)x,
            (int)y,
            direction,
            null,
            success
        );
    }



    public static RaycastResult raycast(
        Actor self,
        int direction,
        Class<?> cls,
        int maxDistance,
        Actor... ignore
    ) {
        return raycast(
            self.getWorld(),
            self.getX(),
            self.getY(),
            direction,
            1,
            cls,
            ignore
        );
    }


    public static RaycastResult raycast(
        Actor self,
        int direction,
        RaycastCondition condition
    ){
        if(self == null || self.getWorld() == null) return null;
        return raycast(self.getWorld(), self.getX(), self.getY(), direction, DEFAULT_DISTANCE, condition);
    }


    public static RaycastResult raycast(
        Actor self,
        RaycastCondition condition
    ){
        if(self == null) return null;
        return raycast(self, self.getRotation(), condition);
    }





    

    public static RaycastResult raycast(
        double x,
        double y,
        int direction,
        int maxDistance,
        RaycastCondition condition
    ){
        int length = 0;
        double delta = Math.abs(Math.sin(direction % 180)) + Math.abs(Math.cos(direction % 180));

        boolean success = false;

        for(;length < maxDistance; length += delta){

            if(condition.collidingAt((int)x, (int)y, null, length)){
                success = true;
                break;
            }

            double radians = Math.toRadians(direction);
            x += Math.cos(radians) * delta;
            y += Math.sin(radians) * delta;
        }

        return new RaycastResult(
            (int)length,
            (int)x,
            (int)y,
            direction,
            null,
            success
        );
    }

    public static RaycastResult raycast(
        double x,
        double y,
        int direction,
        RaycastCondition condition
    ){
        return raycast(x, y, direction, DEFAULT_DISTANCE, condition);
    }




    /**
     * Creates and returns a raycast. The raycast is located in the given
     * world and starts at the location (x|y). It will check the distance
     * to the next actor in the given direction of the specified class
     * ignoring the listed actors.
     * <p>
     * If the distance to the next target object is greater the the maximum
     * distance or the world border was reached, a raycast result with the
     * maximum distance will be returned. The target actor will be null. If
     * the raycast was successful, the raycast result will return the
     * distance until the border of that actor(not the center) and return
     * that actor as the target actor.
     * 
     * @param world The world to do the raycast in. If null, then so will
     * be the return
     * @param x The x-coordinate of the raycayt start
     * @param y The y-coordinate of the raycast start
     * @param direction The direction the raycast should face
     * @param maxDistance The distance after which the raycast should be
     * canceled. Values less than 0 will mean endless(until world border)
     * @param width The width of the raycast
     * @param cls The class of the target object, or null for any class
     * @param ignore Actors to be ignored during the raycast
     * @return A RaycastResult containing the results, or null if the
     * world was
     */
    public static RaycastResult raycast(
        World world,
        int x,
        int y,
        int direction,
        int maxDistance,
        int width,
        Class<?> cls,
        Actor... ignore
    ){
        if(world == null) return null;

        Sensor sensor = new Sensor(direction, width);
        world.addObject(sensor, x, y);

        double length = 0;
        double delta = Math.abs(Math.sin(direction % 180)) + Math.abs(Math.cos(direction % 180));
        Actor current = null;


        while(!(sensor.isAtEdge() && length > 0) && length < maxDistance){

            current = sensor.getOneIntersectingObject(cls);
            if(current != null&& !includes(ignore, current)){
                break;
            }
            else current = null;

            length += delta;

            sensor.move(delta);
        }


        int[] loc = {sensor.getX(), sensor.getY()};
        world.removeObject(sensor);
        return new RaycastResult(
            (int)length,
            loc[0],
            loc[1],
            direction,
            current,
            current != null
        );
    }

    /**
     * Creates and returns a raycast. The raycast will start at the position
     * of the actor self in the specified direction. The actor self will be
     * ignored during the raycast. The raycast has the default length limit. If the
     * actor self is not in a world, the result will be null.
     * 
     * @param self The actor to start the raycast from
     * @param direction The direction the raycast should face
     * @param cls The class of the target object, or null for any class
     * @param ignore Actors to be ignored during the raycast
     * @return A RaycastResult containing the results, or null if the
     * actor self was not in a world
     */
    public static RaycastResult raycast(
        Actor self,
        int direction,
        Class<?> cls,
        Actor... ignore
    ){
        if(self.getWorld() == null) return null;
        Actor[] ignoreWithSelf = new Actor[ignore.length + 1];
        ignoreWithSelf[0] = self;
        for(int i=0; i<ignore.length; i++){
            ignoreWithSelf[i + 1] = ignore[i];
        }
        return raycast(
            self.getWorld(),
            self.getX(),
            self.getY(),
            direction,
            DEFAULT_DISTANCE,
            1,
            cls,
            ignoreWithSelf
        );
    }

    public static RaycastResult raycast(
        Actor self,
        int direction,
        int width,
        Class<?> cls,
        Actor... ignore
    ){
        if(self.getWorld() == null) return null;
        Actor[] ignoreWithSelf = new Actor[ignore.length + 1];
        ignoreWithSelf[0] = self;
        for(int i=0; i<ignore.length; i++){
            ignoreWithSelf[i + 1] = ignore[i];
        }
        return raycast(
            self.getWorld(),
            self.getX(),
            self.getY(),
            direction,
            DEFAULT_DISTANCE,
            width,
            cls,
            ignoreWithSelf
        );
    }
    
    /**
     * Creates and returns a raycast. The raycast will start at the position
     * of the actor self in the direction the actor is facing. The actor
     * self will be ignored during the raycast. The raycast has no length
     * limit and will search for any class. If the actor self is not in a
     * world, the result will be null.
     * 
     * @param self The actor to start the raycast from
     * @param ignore Actors to be ignored during the raycast
     * @return A RaycastResult containing the results, or null if the
     * actor self was not in a world
     */
    public static RaycastResult raycast(
        Actor self,
        Actor... ignore
    ){
        return raycast(self, null, ignore);
    }
    
    /**
     * Creates and returns a raycast. The raycast will start at the position
     * of the actor self in the direction the actor is facing. The actor
     * self will be ignored during the raycast. The raycast has no length
     * limit. If the actor self is not in a world, the result will be null.
     * 
     * @param self The actor to start the raycast from
     * @param cls The class of the target object, or null for any class
     * @param ignore Actors to be ignored during the raycast
     * @return A RaycastResult containing the results, or null if the
     * actor self was not in a world
     */
    public static RaycastResult raycast(
        Actor self,
        Class<?> cls,
        Actor... ignore
    ){
        if(self.getWorld() == null) return null;
        Actor[] ignoreWithSelf = new Actor[ignore.length + 1];
        ignoreWithSelf[0] = self;
        for(int i=0; i<ignore.length; i++){
            ignoreWithSelf[i + 1] = ignore[i];
        }
        return raycast(
            self.getWorld(),
            self.getX(),
            self.getY(),
            self.getRotation(),
            DEFAULT_DISTANCE,
            self.getImage().getHeight(),
            cls,
            ignoreWithSelf
        );
    }
    
    /**
     * Creates and returns a raycast. The raycast will start at the position
     * of the actor self in the specified direction. The actor self will be
     * ignored during the raycast. The raycast has no length limit and will
     * search for any class. If the actor self is not in a world, the result
     * will be null.
     * 
     * @param self The actor to start the raycast from
     * @param direction The direction the raycast should face
     * @param ignore Actors to be ignored during the raycast
     * @return A RaycastResult containing the results, or null if the
     * actor self was not in a world
     */
    public static RaycastResult raycast(
        Actor self,
        int direction,
        Actor... ignore
    ){
        return raycast(
            self,
            direction,
            null,
            ignore
        );
    }
    
    /**
     * Creates and returns a raycast. The raycast will start at the given x
     * and y coordinates in the specified world and face in the given
     * direcion. The raycast has no length limit and will search for any
     * class. If the given world is null, so will be the result.
     * 
     * @param world The world to do the raycast in. If null, then so will
     * be the return
     * @param x The x-coordinate of the raycayt start
     * @param y The y-coordinate of the raycast start
     * @param ignore Actors to be ignored during the raycast
     * @return A RaycastResult containing the results, or null if the
     * actor self was not in a world
     */
    public static RaycastResult raycast(
        World world,
        int x,
        int y,
        int direction,
        Actor... ignore
    ){
        return raycast(
            world,
            x,
            y,
            direction,
            DEFAULT_DISTANCE,
            1,
            null,
            ignore
        );
    }
    
    /**
     * Creates and returns a raycast. The raycast will start at the given x
     * and y coordinates in the specified world and face in the given
     * direcion. The raycast has no length limit and will search for the
     * given class. If the given world is null, so will be the result.
     * 
     * @param world The world to do the raycast in. If null, then so will
     * be the return
     * @param x The x-coordinate of the raycayt start
     * @param y The y-coordinate of the raycast start
     * @param direction The direction the raycast should face
     * @param width The width of the raycast
     * @param cls The class of the target object, or null for any class
     * @param ignore Actors to be ignored during the raycast
     * @return A RaycastResult containing the results, or null if the
     * actor self was not in a world
     */
    public static RaycastResult raycast(
        World world,
        int x,
        int y,
        int direction,
        int width,
        Class<?> cls,
        Actor... ignore
    ){
        return raycast(
            world,
            x,
            y,
            direction,
            DEFAULT_DISTANCE,
            width,
            cls,
            ignore
        );
    }
    
    
    
    //-----------------------------------------------------------------------
    
    /**
     * Checks weather the given list(array) of actors contains the given one.
     * 
     * @param list The list to search in
     * @param actor The actor to search for
     * @return Weather the actor is included in the list
     */
    private static boolean includes(Actor[] list, Actor actor){
        for(Actor a : list) if(a == actor) return true;
        return false;
    }



    public static class Sensor extends Actor{
        double x, y;
        public Sensor(int rotation, int width){
            setImage(new GreenfootImage(1, width));
            setRotation(rotation);
        }
        protected void addedToWorld(World w){
            x = getX();
            y = getY();
        }
        public Actor getOneIntersectingObject(Class<?> cls){
            return super.getOneIntersectingObject(cls);
        }
        public void move(double distance){
            double radians = Math.toRadians(getRotation());
            x += Math.cos(radians) * distance;
            y += Math.sin(radians) * distance;
            setLocation((int)(x + 0.5), (int)(y + 0.5));
        }
    }
}