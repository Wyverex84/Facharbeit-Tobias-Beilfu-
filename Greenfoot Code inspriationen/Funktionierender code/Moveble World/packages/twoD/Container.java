package packages.twoD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import greenfoot.*;
import packages.physics.AdvancedActor;

public class Container extends AdvancedActor {
    ArrayList<Class<?>> paintOrder = new ArrayList<>();
    ArrayList<Class<?>> actOrder = new ArrayList<>();


    final World manageWorld;
    final boolean bounded;



    public Container(int width, int height, int cellSize){
        this(width, height, cellSize, true);
    }
    public Container(int width, int height, int cellSize, boolean bounded){
        manageWorld = new World(width, height, cellSize, bounded) {
            @Override
            public void repaint() {
                //Do nothing: This world is never displayed, so we don't have to calculate how it would look like
            }
        };
        this.bounded = bounded;
        setBackground((GreenfootImage)null);
        repaint();
    }





    @Override
    public final void physicsUpdate() {
        for(Actor a : objectsInActOrder()) a.act();
        repaint();
    }










    public void setPaintOrder(Class<?>... classes){
        paintOrder = new ArrayList<Class<?>>();
        for(Class<?> cls : classes) paintOrder.add(cls);
    }

    public void setActOrder(Class<?>... classes){
        actOrder = new ArrayList<Class<?>>();
        for(Class<?> cls : classes) actOrder.add(cls);
    }

    



    public void setBackground(GreenfootImage background){
        manageWorld.setBackground(background);
    }

    public void setBackground(String filename){
        manageWorld.setBackground(filename);
    }

    public void addObject(Actor object, int x, int y){
        manageWorld.addObject(object, x, y);
        repaint();
    }

    public void removeObject(Actor object){
        manageWorld.removeObject(object);
        repaint();
    }

    public void removeObjects(Collection<? extends Actor> objects){
        manageWorld.removeObjects(objects);
        repaint();
    }



    //World getters

    public GreenfootImage getBackground() {
        return manageWorld.getBackground();
    }

    public int getWidth() {
        return manageWorld.getWidth();
    }

    public int getHeight() {
        return manageWorld.getHeight();
    }

    public int getCellSize() {
        return manageWorld.getCellSize();
    }

    public Color getColorAt(int x, int y){
        return manageWorld.getColorAt(x, y);
    }

    public <A> List<A> getObjects(Class<A> cls){
        return manageWorld.getObjects(cls);
    }

    public <A> List<A> getObjectsAt(int x, int y, Class<A> cls){
        return manageWorld.getObjectsAt(x, y, cls);
    }

    public int numberOfObjects(){
        return manageWorld.numberOfObjects();
    }



    












    public void repaint(){

        GreenfootImage displayed = new GreenfootImage(getWidth(), getHeight());
        displayed.drawImage(getBackground(), 0, 0);

        for(Actor object : objectsInPaintOrder()){

            int objX = object.getX(), objY = object.getY();
            if(bounded){
                if(objX < 0) objX = 0;
                else if(objX >= getWidth()) objX = getWidth() - 1;
                if(objY < 0) objY = 0;
                else if(objY >= getHeight()) objY = getHeight() - 1;
            }

            //create rotated image of actors image
            GreenfootImage objectsImage = object.getImage();
            int diagonal = (int)Math.hypot(objectsImage.getWidth(), objectsImage.getHeight());
            GreenfootImage image = new GreenfootImage(diagonal, diagonal);
            image.drawImage(objectsImage, diagonal / 2 - objectsImage.getWidth() / 2, diagonal / 2 - objectsImage.getHeight() / 2);
            image.rotate((int)object.getRotation());

            //draw rotated image onto displayed image
            int objectX = (int)objX * getCellSize() + (int)(0.5 * getCellSize());
            int objectY = (int)objY * getCellSize() + (int)(0.5 * getCellSize());
            displayed.drawImage(image, objectX - diagonal / 2, objectY - diagonal / 2);

            setImage(image);
        }

        setImage(displayed);
    }
    


    







    List<Actor> objectsInPaintOrder(){
        ArrayList<Actor> ordered = new ArrayList<>();

        //Add listed class objects
        for(Class<?> cls : paintOrder){
            objectLoop:
            for(Actor current : getObjects(Actor.class)) {
                if(current.getClass() != cls) continue objectLoop;
                ordered.add(current);
            }
        }

        //Add non-listed class objects
        for(Actor current : getObjects(Actor.class)){
            if(paintOrder.contains(current.getClass())) continue;
            ordered.add(current);
        }
        
        return ordered;
    }

    List<Actor> objectsInActOrder(){
        ArrayList<Actor> ordered = new ArrayList<>();

        //Add listed class objects
        for(Class<?> cls : actOrder){
            objectLoop:
            for(Actor current : getObjects(Actor.class)) {
                if(current.getClass() != cls) continue objectLoop;
                ordered.add(current);
            }
        }

        //Add non-listed class objects
        for(Actor current : getObjects(Actor.class)){
            if(actOrder.contains(current.getClass())) continue;
            ordered.add(current);
        }
        
        return ordered;
    }
}