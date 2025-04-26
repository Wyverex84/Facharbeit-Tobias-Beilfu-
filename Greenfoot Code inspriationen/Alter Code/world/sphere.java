import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Graphics;
import java.awt.image.WritableRaster;


public class sphere extends Actor
{
    static GreenfootImage earth = new GreenfootImage("Map1.jpg");
    static GreenfootImage buffer = new GreenfootImage(300,300);
    Color getter;
    int[] color = {0,0,0,255};
    WritableRaster raster;
    Color red = new Color(255,0,0,100);
    Graphics image;
    double cx=0,cy=0,cz=4,r=1;
    final int width = earth.getWidth();
    final int height = earth.getHeight();
    boolean loaded=false;
    int loadLine=0;
    
    double[] renderedRayZ = new double[90000];
    double CosAngX,CosAngY,SinAngX,SinAngY;
    byte[][] imageRed   = new byte[width][height];
    byte[][] imageGreen = new byte[width][height];
    byte[][] imageBlue  = new byte[width][height];
    
    double d;
    double ax,ay,az;
    double x,y,z;
    double xt,yt,zt;
    double angy,angx;
    int offX=0,offY=0;
    double angz=0;
    
    int mx,my,originX,originY;
    boolean pressed=false;
    double quality = 1;
    
    public sphere(){
        setImage(buffer);
        raster = buffer.getAwtImage().getRaster();
        int index=0;
        for(int i=0;i<300;i++){
            for(int j=0;j<290;j++){
                ax = (i-150)/300.0;
                ay = (j-150)/300.0;
                renderedRayZ[300*(int)j + (int)i] = Math.sqrt(1- ax*ax - ay*ay);
            }
        }
        
    }
    
    public void load(int i){
        for(int j=0;j<height;j++){
            imageRed[i][j]   = (byte)(earth.getColorAt(i,j).getRed()-128);
            imageGreen[i][j] = (byte)(earth.getColorAt(i,j).getGreen()-128);
            imageBlue[i][j]  = (byte)(earth.getColorAt(i,j).getBlue()-128);
        }
    }

    
    
    public void act() 
    {
        buffer.clear();
        if(!loaded){
            for(int i=0;i<25;i++){
                load(loadLine);
                loadLine++;
            }
            buffer.drawString("Loading: "+100*((double)loadLine)/width+"%",2,40);
            if(loadLine==width)
                loaded=true;
            return;
        }   
        
        //off += 1;
        buffer.setColor(Color.black);
        buffer.fillRect(0,0,300,300);
        int index=0;
        
        CosAngX = Math.cos(angx);
        CosAngY = Math.cos(angy);
        SinAngX = Math.sin(angx);
        SinAngY = Math.sin(angy);
        
        
        for(double i=10;i<290;i+=quality){
            for(double j=10;j<290;j+=quality){    
                ax = (i-150)/300.0;
                ay = (j-150)/300.0;
                az = renderedRayZ[300*(int)j + (int)i];
                
                d = (cx*ax+cy*ay+cz*az)*(cx*ax+cy*ay+cz*az) - (cx*cx+cy*cy+cz*cz) + r*r;
                
                if(d>=0){
                    d = cx*ax+cy*ay+cz*az - Math.sqrt(d);
                    x = ax*d;
                    y = ay*d;
                    z = az*d - cz;
                    
                    
                    yt = y*CosAngX - z*SinAngX;
                    z = z*CosAngX + y*SinAngX;
                    xt = x*CosAngY - z*SinAngY;
                    z = z*CosAngY + x*SinAngY;
                    
                    y = yt;
                    x = xt;
                    
                    //ax = (int)(height*Math.atan2(x,z)/Math.PI);
                    ax = (int)(height*Math.atan(((double)x)/z)/Math.PI);
                    //ay = (int)(height*Math.acos(y)/Math.PI);
                    ay = (int)(height*arcos(y)/Math.PI);
                    if(z<0)ax += height;
                    while(ax>width-1){
                        ax-=width;
                    }
                    while(ax<0){
                        ax+=width;
                    }
                    while(ay>height-1){
                        ay-=height;
                    }
                    while(ay<0){
                        ay+=height;
                    }

                    color[0] = imageRed[(int)ax][(int)ay]+128;
                    color[1] = imageGreen[(int)ax][(int)ay]+128;
                    color[2] = imageBlue[(int)ax][(int)ay]+128;
                    for(int ix=0;ix<quality+1;ix++){
                        for(int iy=0;iy<quality+1;iy++){
                            raster.setPixel((int)(300-i)+ix,(int)(300-j)+iy,color);
                        }
                    }
                    //image.fillRect((int)(300-i),(int)(300-j),(int)quality+1,(int)quality+1);
                }
                
            }
        }
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse != null){
            mx = mouse.getX();
            my = mouse.getY();
        }
        if(Greenfoot.mousePressed(null))
            pressed = true;
        if(Greenfoot.mouseClicked(null))
            pressed = false;
        if(pressed){
            angy += cz*(mx - originX)/500.0;
            angx += cz*(my - originY)/500.0;
        }
        originX=mx;
        originY=my;
        
        if(Greenfoot.isKeyDown("up") && cz > 1.3)
            cz -= .05;
        if(Greenfoot.isKeyDown("down"))
            cz += .05;
        String str = Greenfoot.getKey();
        if(Greenfoot.isKeyDown("1") && quality > 1)
            quality -= .1; 
        if(Greenfoot.isKeyDown("2"))
            quality += .1; 
        buffer.setColor(Color.red);
        buffer.drawString("Quality: "+(((int)(quality*100))/100.0),2,40);
    }    
    
    
    public double arctan(double x){
        int itterations = 3;
        double result=0;
        int negator = 1;
        
        for(int n=0;n<itterations;n++){
            result += ((negator)/(2.0*n+1))*Math.pow(x,2*n+1);
            negator = -negator;
        }
        return result;
    }
    
    public double arcos(double x){
        return Math.PI/2.0 - (x + .1666666666*x*x*x + .25*x*x*x*x*x*x*x);
    }
}
