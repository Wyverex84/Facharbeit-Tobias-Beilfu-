package packages.physics;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import packages.twoD.Vector;

public class Cylinder extends PhysicalActor{
    static final double DEFAULT_GROUND_GRAG = 1000;

    double spinSpeed = 0;



    //Constructors

    public Cylinder(int radius, int width, int density){
        super(density, 0.4, DEFAULT_GROUND_GRAG, 0);
        GreenfootImage image = new GreenfootImage(2 * radius, width);
        image.setColor(Color.DARK_GRAY);
        image.fill();
        setImage(image);
    }
    public Cylinder(int radius, int width){
        this(radius, width, 1);
    }







    //phyiscs


    @Override
    protected void applyGroundDrag(){
        cw = 0;
        //accelerate the object
        Vector slideVel = Vector.between(velocity(), Vector.angledVector(getAngle(), spinSpeed));
        double drag = groundDrag * gripLevel(slideVel);
        if(drag * time.deltaTime() > getSpeed()) setVelocity(new Vector());
        else addFixedForce(Vector.angledVector(getMovementDirection() + 180, drag));

        //System.out.println(drag);
        //System.out.println(Vector.angledVector(getMovementDirection() + 180, drag));
        //System.out.println(velocity());

        //Slow down the spin

    }












    //additional get/set methods

    public void setSpinSpeed(double spinSpeed) {
        this.spinSpeed = spinSpeed;
    }
    public double getSpinSpeed() {
        return spinSpeed;
    }
    public double getAngularSpeed(){
        return spinSpeed / getImage().getWidth();
    }
}
