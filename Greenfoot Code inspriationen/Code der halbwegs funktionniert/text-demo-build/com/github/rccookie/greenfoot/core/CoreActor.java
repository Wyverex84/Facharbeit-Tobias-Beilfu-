package com.github.rccookie.greenfoot.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import greenfoot.Actor;
import greenfoot.ActorVisitor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import greenfoot.World;

import com.github.rccookie.greenfoot.core.CoreActor;
import com.github.rccookie.common.geometry.Transform2D;
import com.github.rccookie.common.geometry.Vector;
import com.github.rccookie.common.geometry.Vector2D;
import com.github.rccookie.common.util.Updatable;
import com.github.rccookie.greenfoot.event.Input;
import com.github.rccookie.common.data.json.JsonField;
import com.github.rccookie.common.data.json.JsonSerializable;
import com.github.rccookie.common.event.Time;

/**
 * The CoreActor is an improved version of the actor, sharing all its features and adding some more:
 * <ul>
 * <li>Double based localization with smooth movement and options to execute some code for each step moved over longer distances
 * <li>Vector and transform compatible movement methods
 * <li>Framerate dependent movement speed (see {@code fixedMove(int)}) to be independent of the framerate
 * <li>Included {@code Time} instance that is automaticly being updated
 * <li>Information about the mouse state on this object and methods that may be executed when clicked
 * </ul>
 * Like the actor, the CoreActor is abstract, as its purpose is to be used for a specific implementation. It does not contain
 * any abstract methods though that may need to be implemented.
 * 
 * @author RcCookie
 * @version 1.1
 * @see Actor
 * @see Time
 * @see Vector2D
 * @see Transform2D
 */
@JsonSerializable
public abstract class CoreActor extends Actor implements Updatable {

    /**
     * Indicates weather the current session is online or on the Greenfoot application.
     * Offline the code runs plain java ansuring that any java functionallity will work.
     * Online however the code gets converted to javascript which is not very reliable
     * and does not have all classes that java has. Therefore special handling when
     * operating online max be helpful or neccecary.
     */
    public static final boolean IS_ONLINE = CoreWorld.IS_ONLINE;

    static {
        CoreWorld.initializeConsole();
    }

    /**
     * The location of the actor stored in double coordinates.
     */
    @JsonField
    protected Transform2D transform = new Transform2D();

    /**
     * The velocity of the actor.
     */
    private Vector velocity = new Vector2D();

    /**
     * The time object of this actor. It is updated once per frame and can be accessed by extending classes.
     */
    protected final Time time = new NoExternalUpdateTime();



    private boolean hovered = false;
    private boolean pressed = false;

    private String id = null;

    private final List<Runnable> updateListeners = new ArrayList<>();

    private final List<Runnable> clickActions = new ArrayList<>();
    private final List<Runnable> pressActions = new ArrayList<>();
    private final List<Runnable> releaseActions = new ArrayList<>();

    private final List<Consumer<World>> addedToWorldActions = new ArrayList<>();



    /**
     * Constructs a new CoreActor.
     */
    public CoreActor() {
        addUpdateListener(() -> ((NoExternalUpdateTime)time).actualUpdate());
        addUpdateListener(() -> fixedMove(velocity));
    }

    /**
     * This method cannot be overridden as it is neccecary for the CoreActor to function. Instead, the method {@code addedIntoWorld(greenfoot.World)}
     * can be used.
     * 
     * @param w The world the actor got added to
     */
    @Override
    protected final void addedToWorld(World w){
        transform.location = getLocation();
        for(Consumer<World> action : addedToWorldActions) action.accept(w);
    }
    
    /**
     * Set the image for this actor to the specified image. If the default collider object is used, the scale of that collider will be set to the new bounds.
     */
    @Override
    public void setImage(GreenfootImage image){
        super.setImage(image);
    }


    /**
     * Neccecary for the CoreActor to work. Use {@code update()}, {@code earlyUpdate()} and {@code lateUpdate()} instead.
     */
    public final void act(){
        earlyUpdate();
        internalUpdate();
        update();
        physicsUpdate();
        lateUpdate();
    }
    
    /**
     * Called once per frame before internal things are updated and {@code physicsUpdate()} is called.
     * <p>The default implementation does nothing.
     */
    public void earlyUpdate() { }

    /**
     * Called once per frame.
     * <p>The default implementation does nothing.
     */
    @Override
    public void update() { }

    /**
     * Called once per frame. Is intented to contain physical operations (but also things like button functionallity)
     * and is therefore called after the {@code update()} method.
     * <p>Make sure to always call the super implementation of this method when overriding it!
     */
    protected void physicsUpdate() { }

    /**
     * Called once per frame after the {@code update()} and {@code physicsUpdate()} method have been called.
     * <p>The default implementation does nothing.
     */
    public void lateUpdate() { }



    /**
     * Returns an optional {@link CoreWorld} that this CoreActor lives in.
     * 
     * @return An optional containing the CoreWorld this CoreActor lives in, or an empty Optional if this CoreActor
     * is not in a CoreWorld.
     */
    public Optional<CoreWorld> getCoreWorld() {
        return Optional.ofNullable(getWorldOfType(CoreWorld.class));
    }



    /**
     * Returns an optional graphically intersecting actor of the given class.
     * 
     * @param <A> The type of actor to find
     * @param clazz The class of the actor to find
     * @return An optional containing an intersecting actor of the given class, or empty
     */
    @SuppressWarnings("unchecked")
    protected <A> Optional<A> findIntersecting(Class<A> clazz) {
        return Optional.ofNullable((A)getOneIntersectingObject(clazz));
    }



    /**
     * Removes this object from its world, if it is in one.
     * 
     * @return Weather the object was in a world before
     */
    public boolean remove() {
        World world = getWorld();
        if(world == null) return false;
        world.removeObject(this);
        return true;
    }



    /**
     * Called whenever the mouse clicked onto this object (released the mouse on the object after it had been pressed down on it).
     * 
     * @param mouse Information about the mouse that clicked onto the object
     */
    protected void onClick() {
        for(Runnable action : clickActions) action.run();
    }

    /**
     * Called whenever the mouse pressed down(only in the frame that the press started in) on the actors collider or itself, if there is no.
     * 
     * @param mouse Information about the mouse that clicked onto the object
     */
    protected void onPress(){
        pressed = true;
        for(Runnable action : pressActions) action.run();
    }

    /**
     * Called whenever the mouse is released(only in the frame that the press ended) on the actors collider or itself, if there is no.
     * 
     * @param mouse Information about the mouse that clicked onto the object
     */
    protected void onRelease(){
        pressed = false;
        if(hovered) {
            onClick();
        }
        for(Runnable action : releaseActions) action.run();
    }


    /**
     * Virtually presses and releases the mouse from this object within one frame, having the same result as if the mouse actually
     * clicked on this object.
     */
    public void click() {
        boolean realHovered = hovered;
        hovered = true;
        onPress();
        onRelease();
        hovered = realHovered;
    }


    /**
     * Returns weather the mouse presses this actor right now. This is considered to be the case if the mouse was pressed down on the actor
     * or its collider and since then not released.
     * 
     * @return Weather the mouse is pressed onto the actor
     */
    public boolean pressed() {
        return pressed;
    }

    public boolean hovered() {
        return hovered;
    }
    
    /**
     * Called on each step when moving in steps using {@code moveInSteps()} or {@code fixedMoveInSteps()}. If false is being returned, the steps movement will be stoped.
     * 
     * @param stepNumber The number of the current step
     * @return Weather the movement should be continued
     */
    protected boolean onStep(int stepNumber) { return true; }



    /**
     * Does some stuff necessary to function.
     */
    private void internalUpdate() {
        handleMouseInteractions();
        handleUpdateListeners();
    }

    private void handleMouseInteractions() {
        MouseInfo mouse = Input.mouseInfo();
        hovered = mouse != null ? ActorVisitor.containsPoint(this, mouse.getX() * getWorld().getCellSize(), mouse.getY() * getWorld().getCellSize()) : false;
        if(hovered && Greenfoot.mousePressed(this)) onPress();
        else if(pressed && Greenfoot.mouseClicked(null)) onRelease();
    }

    private void handleUpdateListeners() {
        for(Runnable listener : updateListeners) listener.run();
    }



    /**
     * Returns the id of this CoreActor. You can set it's id using
     * {@link #setId(String)}.
     * 
     * @return The id of this CoreActor, defaulted to {@code null}
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of this CoreActor. By default the id is {@code null}.
     * You can request an CoreActor's id using {@link #getId()}.
     * 
     * @param id The new id for this CoreActor
     * @return This CoreActor
     */
    public CoreActor setId(String id) {
        this.id = id;
        return this;
    }



    //fixed movement

    /**
     * Moves the actor the specified distance multiplied by the current time delta. This means that if this method is called once per frame with the parameter {@code x}
     * the actor will in sum move the length of {@code x} per second, independent of the framerate.
     * 
     * @param movement The distance to movee in pixels/second
     */
    public void fixedMove(Vector movement) {
        move(movement.scaled(time.deltaTime()));
    }

    /**
     * Moves the actor the specified distance multiplied by the current time delta. This means that if this method is called once per frame with the parameter {@code x}
     * the actor will in sum move the length of {@code x} per second, independent of the framerate.
     * 
     * @param distance The distance to movee in pixels/second
     */
    public void fixedMove(double distance){
        fixedMove(Vector2D.angledVector(transform.rotation, distance));
    }

    /**
     * Moves and turns the actor the specified distance and rotation multiplied by the current time delta. This means that if this method is called once per frame with the parameter {@code x}
     * the actor will in sum move and turn the length/rotation of {@code x} per second, independent of the framerate.
     * 
     * @param movement The distance and rotation to move stored insode of a transforme in pixels/second and degrees/second
     */
    public void fixedMove(Transform2D movement){
        movement = new Transform2D(movement);
        movement.location.scale(time.deltaTime());
        movement.rotation *= time.deltaTime();
        move(movement);
    }

    /**
     * Moves the actor the specified distance parallel to the x axis multiplied by the current time delta. This means that if this method is called once per frame with the parameter {@code x}
     * the actor will in sum move the length of {@code x} per second, independent of the framerate.
     * 
     * @param distance The distance to movee in pixels/second
     */
    public void fixedMoveX(double distance){
        fixedMove(Vector2D.angledVector(0, distance));
    }

    /**
     * Moves the actor the specified distance parallel to the y axis multiplied by the current time delta. This means that if this method is called once per frame with the parameter {@code x}
     * the actor will in sum move the length of {@code x} per second, independent of the framerate.
     * 
     * @param distance The distance to move in pixels/second
     */
    public void fixedMoveY(double distance){
        fixedMove(Vector2D.angledVector(90, distance));
    }

    /**
     * Moves the actor the specified distance multiplied by the current time delta. Works just like {@code moveInSteps(Vector, double)} but scaled by the time delta like in {@code fixedMove(Vector)}.
     * 
     * @see #moveInSteps(Vector2D, double)
     * @see #fixedMove(Vector2D)
     * @param movement The distance to move in pixels/second
     * @param stepSize The size of each step
     * @return The vector that describes the move that actually happened
     */
    public Vector2D fixedMoveInSteps(Vector movement, double stepSize){
        movement = new Vector2D(movement).scale(time.deltaTime());
        return moveInSteps(movement, stepSize);
    }

    /**
     * Moves the actor the specified distance multiplied by the current time delta. Works just like {@code moveInSteps(Vector, double)} but scaled by the time delta like in {@code fixedMove(Vector)}.
     * 
     * @see #moveInSteps(double, double)
     * @see #fixedMove(Vector2D)
     * @param movement The distance to move in pixels/second
     * @param stepSize The size of each step
     * @return The distance the actor actually moved
     */
    public double fixedMoveInSteps(double distance, double stepSize){
        return fixedMoveInSteps(Vector2D.angledVector(transform.rotation, distance), stepSize).abs();
    }

    /**
     * Turns the actor the specified amount of degrees multiplied by the current time delta. This means that if this method is called once per frame with the parameter {@code x}
     * the actor will in sum turn {@code x} degrees per second, independent of the framerate.
     * 
     * @param angle The turning speed in degrees/second
     */
    public void fixedTurn(double angle){
        turn(angle * time.deltaTime());
    }
    
    

    //smooth movement

    /**
     * Moves the actor the specified distance.
     * 
     * @param movement The vector that describes the movement of the actor
     */
    public void move(Vector movement){
        setLocation(transform.location.add(movement));
    }

    /**
     * Moves the actor the specified distance in the direction it is currently facing.
     * 
     * @param distance The distance in pixels to move the vector
     */
    public void move(double distance){
        move(Vector2D.angledVector(transform.rotation, distance));
    }

    @Override
    public void move(int distance){
        move((double)distance);
    }

    /**
     * Moves the actor the specified distance and turns if the specified amount of degrees.
     * 
     * @param movement The move in pixels and turn in degrees of the actor, contained in a transform
     */
    public void move(Transform2D movement){
        move(movement.location);
        turn(movement.rotation);
    }

    /**
     * Moves the actor the specified number of pixels parallel to the x axis.
     * 
     * @param distance The distance in pixels for the actor to move
     */
    public void moveX(double distance){
        move(Vector2D.angledVector(0, distance));
    }

    /**
     * Moves the actor the specified number of pixels parallel to the y axis.
     * 
     * @param distance The distance in pixels for the actor to move
     */
    public void moveY(double distance){
        move(Vector2D.angledVector(90, distance));
    }

    /**
     * Moves the whole distance step by step, executing {@code onStep(int)} with the current number of steps
     * as parameter. If {@code onStep()} does not return {@code true}, the movement will be abbored.
     * <p>If the movement won't be interrupted, the final movement will always by the full length of {@code movement},
     * even if {@code movement.abs() % stepSize != null} is {@code true}.
     * <p>The method will return the distance that was actually traveled as a vector. If there was no interferance,
     * {@code moveInSteps(movement, x) == movement} and {@code movement.equals(moveInSteps(movement, x))} will be {@code true}.
     * If there was interferance, {@code movement.equals(moveInSteps(movement, x))} may still be {@code true}, while
     * {@code moveInSteps(movement, x) == movement} will be {@code false}.
     * 
     * @param movement The movement to move
     * @param stepSize The size of each step
     * @return The distance that was actually moved
     */
    public Vector2D moveInSteps(Vector movement, double stepSize){
        for(double i=0; i<movement.abs()-stepSize; i+= stepSize){
            move(movement.normed().scale(stepSize));
            if(!onStep((int)(i / stepSize))) return Vector2D.angledVector(movement.angle(), i);
        }
        move(movement.normed().scale(movement.abs() % stepSize));
        if(!onStep((int)(movement.abs() / stepSize))) return Vector2D.angledVector(movement.angle(), (int)movement.abs());
        return movement.get2D();
    }

    /**
     * Moves the actor the specified distance in the direction the actor is facing using the specified step size.
     * 
     * @see #moveInSteps(Vector2D, double)
     * @param distance
     * @param stepSize
     * @return
     */
    public Vector2D moveInSteps(double distance, double stepSize){
        return moveInSteps(Vector2D.angledVector(transform.rotation, distance), stepSize);
    }

    /**
     * Sets the velocity of this CoreActor.
     * 
     * @param velocity The new velocity for this actor. Must not be {@code null}
     */
    public void setVelocity(Vector velocity) {
        Objects.requireNonNull(velocity);
        this.velocity = new Vector2D(velocity);
    }

    /**
     * Returns this CoreActor's velocity. Modifications to this vector <b>will</b> effect is!
     * 
     * @return This actor's velocity
     */
    public Vector velocity() {
        return velocity;
    }



    /**
     * Moves the actor to the specified x coordinate.
     * 
     * @param x The new x coordinate
     */
    public void setX(double x){
        setLocation(new Vector2D(x, transform.location.y()));
    }

    /**
     * Moves the actor to the specified y coordinate.
     * 
     * @param y The new y coordinate
     */
    public void setY(double y){
        setLocation(new Vector2D(transform.location.x(), y));
    }

    @Override
    public void setLocation(int x, int y){
        setLocation((double)x, (double)y);
    }

    /**
     * Moves the actor to the specified x and y coordinate. While floating point values may not be visible, they will be saved.
     * 
     * @param x The new x coordinate of the actor
     * @param y The new y coordinate of the actor
     */
    public void setLocation(double x, double y){
        setLocation(new Vector2D(x, y));
    }

    /**
     * Moves the actor to the specified location (the location the vector points at starting at the origin).
     * 
     * @param location The new location of the actor
     */
    public void setLocation(Vector location){
        transform.location = new Vector2D(location);
        updateTransform();
    }

    /**
     * Moves the actor to the location of the specified actor. If that actor is an CoreActor, also floating point
     * location will be transfered.
     * 
     * @param toActorsLocation The actor to move to
     */
    public void setLocation(Actor toActorsLocation) {
        if(toActorsLocation instanceof CoreActor) setLocation(((CoreActor)toActorsLocation).getLocation());
        else setLocation(toActorsLocation.getX(), toActorsLocation.getY());
    }

    @Override
    public void setRotation(int angle){
        setRotation((double)angle);
    }

    /**
     * Sets the rotations of the actor. While floating point values may not be visible, they will be saved.
     * 
     * @param angle The rotation in degrees
     */
    public void setRotation(double angle){
        transform.rotation = angle;
        updateTransform();
    }

    @Override
    public void turn(int angle){
        turn((double)angle);
    }

    /**
     * Turns the actor the specified amount of degrees.
     * 
     * @param angle The angle to turn the actor in degrees
     */
    public void turn(double angle){
        setRotation(transform.rotation + angle);
    }

    /**
     * Turn this actor facing towards the given target.
     * 
     * @param target The CoreActor to look at
     */
    public void turnTowards(CoreActor target) {
        turnTowards(target.getLocation());
    }

    /**
     * Turn this actor facing towards the given target.
     * 
     * @param target The location to look at
     */
    public void turnTowards(Vector target) {
        setRotation(Vector2D.between(getLocation(), target.get2D()).angle());
    }

    /**
     * Turn this actor facing towards the given target.
     * 
     * @param target The actor to look at
     */
    public void turnTowards(Actor target) {
        turnTowards(target.getX(), target.getY());
    }

    /**
     * Sets the location and rotation of this actor to the specified one.
     * 
     * @param transform The new transform of the actor
     */
    public void setTransform(Transform2D transform) {
        setLocation(transform.location);
        setRotation(transform.rotation);
    }

    /**
     * Actually updates the actors location and, if needed, the colliders locaiton.
     */
    protected void updateTransform(){
        super.setLocation((int)(transform.location.x() + 0.5), (int)(transform.location.y() + 0.5));
        super.setRotation((int)(transform.rotation + 0.5));
    }



    @Override
    public int getX(){
        return (int)x();
    }

    @Override
    public int getY(){
        return (int)y();
    }

    /**
     * Returns the exact x coordinate of the actor.
     * 
     * @return The exact x coordinate of the actor
     */
    public double x(){
        return transform.location.x();
    }

    /**
     * Returns the exact y coordinate of the actor.
     * 
     * @return The exact y coordinate of the actor
     */
    public double y(){
        return transform.location.y();
    }

    /**
     * Returns the locaiton of the actor as a vector.
     * 
     * @return The location of the actor
     */
    public Vector2D getLocation(){
        return new Vector2D(transform.location);
    }

    @Override
    public int getRotation(){
        return (int)getAngle();
    }

    /**
     * Returns the exact angle of rotation of this actor.
     * 
     * @return The exact angle of this actor
     */
    public double getAngle(){
        return transform.rotation;
    }

    /**
     * Returns the transform of this actor.
     * 
     * @return A transform representing the actors transform
     */
    public Transform2D getTransform(){
        return new Transform2D(transform);
    }



    /**
     * Adds the given action to those that will be executed whenever the object
     * was clicked on.
     * <p>As parameter the the current information about the mouse will be passed.
     * 
     * @param mouse The action to add
     * @return This object
     */
    public CoreActor addClickAction(Runnable action) {
        if(action == null) return this;
        clickActions.add(action);
        return this;
    }

    public CoreActor addPressAction(Runnable action) {
        if(action == null) return this;
        pressActions.add(action);
        return this;
    }
    
    public CoreActor addReleaseAction(Runnable action) {
        if(action == null) return this;
        releaseActions.add(action);
        return this;
    }

    public CoreActor removeClickAction(Runnable action) {
        clickActions.remove(action);
        return this;
    }

    public CoreActor removePressAction(Runnable action) {
        pressActions.remove(action);
        return this;
    }

    public CoreActor removeReleaseAction(Runnable action) {
        releaseActions.remove(action);
        return this;
    }

    
    public CoreActor addAddedAction(Consumer<World> world) {
        if(world == null) return this;
        addedToWorldActions.add(world);
        return this;
    }

    public CoreActor removeAddedAction(Consumer<World> action) {
        addedToWorldActions.remove(action);
        return this;
    }


    public CoreActor addUpdateListener(Runnable listener) {
        if(listener == null) return this;
        updateListeners.add(listener);
        return this;
    }

    public CoreActor addUpdatable(Updatable updatable) {
        if(updatable == null) return this;
        return addUpdateListener(() -> updatable.update());
    }

    public CoreActor removeUpdateListener(Runnable listener) {
        updateListeners.remove(listener);
        return this;
    }



    /**
     * Sets the time scale for all CoreActors in the current world to the specified factor.
     * 
     * @see #setTimeScale(World, double)
     * @param scale The scale of time to use
     */
    public static void setTimeScale(double scale){
        setTimeScale(greenfoot.core.WorldHandler.getInstance().getWorld(), scale);
    }

    /**
     * Sets the time scale for all CoreActors in the given world to the specified factor. This has an impact on the
     * distance moved in the "fixed" methods:
     * <ul>
     * <li>If the scale is 1 (default), the actors will move with normal speed.
     * <li>If the scale is higher than 1, the actors will move quicker, the time is speed up.
     * <li>If the scale is less than 1 but more than 0, the actors will move in "slow motion".
     * <li>If the scale is 0, the actors will not move.
     * <li>(Not reccomented) If the scale is negative, the actors will move backwards
     * </ul>
     * All this only has an effect on the "fixed" methods
     * 
     * @see #fixedMove(Vector2D)
     * @param world The world to change the CoreActors time scale in
     * @param scale The scale of time to use
     */
    public static void setTimeScale(World world, double scale){
        if(world == null) return;
        for(CoreActor a : world.getObjects(CoreActor.class)){
            a.time.timeScale = scale;
        }
    }
}