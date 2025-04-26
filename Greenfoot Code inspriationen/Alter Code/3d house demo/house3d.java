import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.*;
/**
 * Write a description of class house3d here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class house3d extends Actor
{
    ArrayList polygons;
    GreenfootImage im;
    Graphics2D g;
    PolygonRenderer polygonRenderer;
    ViewWindow viewWindow;

    private int numFrames;
    private long startTime;
    private float frameRate;
    /**
     * Act - do whatever the house3d wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public house3d(PolygonRenderer polygonRenderer, ViewWindow viewWindow){
        // create polygons
        polygons = new ArrayList();
        createPolygons();

        im=new GreenfootImage(600,400);
        this.setImage(im);

        g=(Graphics2D)im.getAwtImage().getGraphics();

        this.polygonRenderer=polygonRenderer;
        this.viewWindow=viewWindow;

    }

    public void act() 
    {

        checkKeys();

        //draw the 3d house by draw every polygon of the house
        polygonRenderer.startFrame((Graphics2D)g);
        for (int i=0; i<polygons.size(); i++) {
            polygonRenderer.draw((Graphics2D)g, (Polygon3D)polygons.get(i));
        }
        polygonRenderer.endFrame((Graphics2D)g);

        drawText(g);
    }    

    public void drawText(Graphics g) {

        // draw text
        g.setColor(Color.WHITE);
        g.drawString("Use the arrow keys to move. " 
        , 5, 20);
        calcFrameRate();
        g.drawString("FPS: "+frameRate , 5,
            im.getHeight() - 5);
    }

    public void calcFrameRate() {
        numFrames++;
        long currTime = System.currentTimeMillis();

        // calculate the frame rate every 500 milliseconds
        if (currTime > startTime + 500) {
            frameRate = (float)numFrames * 1000 /
            (currTime - startTime);
            startTime = currTime;
            numFrames = 0;
        }
    }

    public void setViewBounds(int width, int height) {
        width = Math.min(width, im.getWidth());
        height = Math.min(height, im.getHeight());
        width = Math.max(64, width);
        height = Math.max(48, height);
        viewWindow.setBounds((im.getWidth() - width) /2,
            (im.getHeight() - height) /2, width, height);
    }

    public void checkKeys() {

        // check options
        if (Greenfoot.isKeyDown("i")) {
            setViewBounds(viewWindow.getWidth() + 64,
                viewWindow.getHeight() + 48);
        }
        else if (Greenfoot.isKeyDown("o")) {
            setViewBounds(viewWindow.getWidth() - 64,
                viewWindow.getHeight() - 48);
        }

        float angleChange = 0.0002f*10;
        float distanceChange = .5f*20;

        Transform3D camera = polygonRenderer.getCamera();
        Vector3D cameraLoc = camera.getLocation();

        // apply movement
        if (Greenfoot.isKeyDown("up")) {
            cameraLoc.x -= distanceChange * camera.getSinAngleY();
            cameraLoc.z -= distanceChange * camera.getCosAngleY();
        }
        if (Greenfoot.isKeyDown("down")) {
            cameraLoc.x += distanceChange * camera.getSinAngleY();
            cameraLoc.z += distanceChange * camera.getCosAngleY();
        }
        if (Greenfoot.isKeyDown("left")) {
            cameraLoc.x -= distanceChange * camera.getCosAngleY();
            cameraLoc.z += distanceChange * camera.getSinAngleY();
        }
        if (Greenfoot.isKeyDown("right")) {
            cameraLoc.x += distanceChange * camera.getCosAngleY();
            cameraLoc.z -= distanceChange * camera.getSinAngleY();
        }
        if (Greenfoot.isKeyDown("q")) {
            cameraLoc.y += distanceChange;
        }
        if (Greenfoot.isKeyDown("z")) {
            cameraLoc.y -= distanceChange;
        }

        // look up/down (rotate around x)
        if (Greenfoot.isKeyDown("w")) {
            camera.rotateAngleX(5*angleChange);
        }
        if (Greenfoot.isKeyDown("s")) {
            camera.rotateAngleX(-5*angleChange);
        }

        // turn (rotate around y)
        if (Greenfoot.isKeyDown("a")) {
            camera.rotateAngleY(5*angleChange);
        }
        if (Greenfoot.isKeyDown("d")) {
            camera.rotateAngleY(-5*angleChange);
        }

        // tilet head left/right (rotate around z)
        if (Greenfoot.isKeyDown("x")) {
            camera.rotateAngleZ(10*angleChange);
        }
        if (Greenfoot.isKeyDown("c")) {
            camera.rotateAngleZ(-10*angleChange);
        }
    }

    public void createPolygons() {
        SolidPolygon3D poly;

        // walls
        poly = new SolidPolygon3D(
            new Vector3D(-200, 0, -1000),
            new Vector3D(200, 0, -1000),
            new Vector3D(200, 250, -1000),
            new Vector3D(-200, 250, -1000));
        poly.setColor(Color.WHITE);
        polygons.add(poly);
        poly = new SolidPolygon3D(
            new Vector3D(-200, 0, -1400),
            new Vector3D(-200, 250, -1400),
            new Vector3D(200, 250, -1400),
            new Vector3D(200, 0, -1400));
        poly.setColor(Color.WHITE);
        polygons.add(poly);
        poly = new SolidPolygon3D(
            new Vector3D(-200, 0, -1400),
            new Vector3D(-200, 0, -1000),
            new Vector3D(-200, 250, -1000),
            new Vector3D(-200, 250, -1400));
        poly.setColor(Color.GRAY);
        polygons.add(poly);
        poly = new SolidPolygon3D(
            new Vector3D(200, 0, -1000),
            new Vector3D(200, 0, -1400),
            new Vector3D(200, 250, -1400),
            new Vector3D(200, 250, -1000));
        poly.setColor(Color.GRAY);
        polygons.add(poly);

        // door and windows
        poly = new SolidPolygon3D(
            new Vector3D(0, 0, -1000),
            new Vector3D(75, 0, -1000),
            new Vector3D(75, 125, -1000),
            new Vector3D(0, 125, -1000));
        poly.setColor(new Color(0x660000));
        polygons.add(poly);
        poly = new SolidPolygon3D(
            new Vector3D(-150, 150, -1000),
            new Vector3D(-100, 150, -1000),
            new Vector3D(-100, 200, -1000),
            new Vector3D(-150, 200, -1000));
        poly.setColor(new Color(0x660000));
        polygons.add(poly);

        // roof
        poly = new SolidPolygon3D(
            new Vector3D(-200, 250, -1000),
            new Vector3D(200, 250, -1000),
            new Vector3D(75, 400, -1200),
            new Vector3D(-75, 400, -1200));
        poly.setColor(new Color(0x660000));
        polygons.add(poly);
        poly = new SolidPolygon3D(
            new Vector3D(-200, 250, -1400),
            new Vector3D(-200, 250, -1000),
            new Vector3D(-75, 400, -1200));
        poly.setColor(new Color(0x330000));
        polygons.add(poly);
        poly = new SolidPolygon3D(
            new Vector3D(200, 250, -1400),
            new Vector3D(-200, 250, -1400),
            new Vector3D(-75, 400, -1200),
            new Vector3D(75, 400, -1200));
        poly.setColor(new Color(0x660000));
        polygons.add(poly);
        poly = new SolidPolygon3D(
            new Vector3D(200, 250, -1000),
            new Vector3D(200, 250, -1400),
            new Vector3D(75, 400, -1200));
        poly.setColor(new Color(0x330000));
        polygons.add(poly);

        //ground
        poly = new SolidPolygon3D(
            new Vector3D(-200, 0, -1400),
            new Vector3D(200, 0, -1400),
            new Vector3D(200, 0, -1000),
            new Vector3D(-200, 0, -1000));
         poly.setColor(Color.GRAY);
         polygons.add(poly);
    }
}
