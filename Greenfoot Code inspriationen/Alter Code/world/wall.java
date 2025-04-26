import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Graphics;
import java.awt.image.WritableRaster;


public class wall extends Actor
{
    static GreenfootImage earth = new GreenfootImage("EYE.png");
    static GreenfootImage buffer = new GreenfootImage(300,300);
    Color getter;
    int[] color = {0,0,0,255};
    WritableRaster raster;
    Color red = new Color(255,0,0,100);
    Graphics image;
    double p3x=0,p3y=0,p3z=2;
    final int width = earth.getWidth();
    final int height = earth.getHeight();
    
    double[] renderedRayZ = new double[90000];
    double CosAngX,CosAngY,SinAngX,SinAngY;
    byte[][] imageRed   = new byte[width][height];
    byte[][] imageGreen = new byte[width][height];
    byte[][] imageBlue  = new byte[width][height];
    
    double u;
    double nx,ny,nz;
    double p1x,p1y,p1z,p2x,p2y,p2z;
    
    double x,y,z;
    double xt,yt,zt;
    double angy,angx;
    int offX=0,offY=0;
    double angz=0;
    
    int mx,my,originX,originY;
    boolean pressed=false;
    double quality = 2;
    
    public wall(){
        setImage(buffer);
        raster = buffer.getAwtImage().getRaster();
        int index=0;
        for(int i=1;i<280;i++){
            for(int j=1;j<280;j++){
                
            }
        }
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                imageRed[i][j]   = (byte)(earth.getColorAt(i,j).getRed()-128);
                imageGreen[i][j] = (byte)(earth.getColorAt(i,j).getGreen()-128);
                imageBlue[i][j]  = (byte)(earth.getColorAt(i,j).getBlue()-128);
            }
        }
    }

    
    
    public void act() 
    {
        //off += 1;
        buffer.setColor(Color.black);
        buffer.fillRect(0,0,300,300);
        int index=0;
        
        CosAngX = Math.cos(angx);
        CosAngY = Math.cos(angy);
        SinAngX = Math.sin(angx);
        SinAngY = Math.sin(angy);
        
        ny = 0*CosAngX - 1*SinAngX;
        nz = 1*CosAngX + 0*SinAngX;
        nx = 0*CosAngY -  nz*SinAngY;
        nz = nz*CosAngY + 0*SinAngY;
        
        
        for(double i=1;i<280;i+=quality){
            for(double j=1;j<280;j+=quality){    
                p1x = 0;
                p1y = 0;
                p1z = 0;
                p2x = (i-150)/150.0;
                p2y = (j-150)/150.0;
                p2z = 1;
                
                u = ( nx*(p3x-p1x) + ny*(p3y-p1y) + nz*(p3z-p1z) ) / ( nx*(p2x-p1x) + ny*(p2y-p1y) + nz*(p2z-p1z) );
                
                if(u<10 && u>0){
                    x = p2x*u - p3x;
                    y = p2y*u - p3y;
                    z = p2z*u - p3z;
                    
                   
                    xt = x*CosAngY + z*SinAngY;
                    z = z*CosAngY - x*SinAngY;
                    yt = y*CosAngX + z*SinAngX;
                    z = z*CosAngX - y*SinAngX;
                    
                    
                    
                    y = yt;
                    x = xt;
                    
                    if(x>-1 && x<1 && y<1 && y>-1){
                        x = (int)(x*100) + 100;
                        y = (int)(y*100) + 100;
                        
                        color[0] = imageRed[(int)x][(int)y]+128;
                        color[1] = imageGreen[(int)x][(int)y]+128;
                        color[2] = imageBlue[(int)x][(int)y]+128;
                        for(int ix=0;ix<quality+1;ix++){
                            for(int iy=0;iy<quality+1;iy++){
                                raster.setPixel((int)i+ix,(int)j+iy,color);
                            }
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
            angy += 5*(mx - originX)/500.0;
            angx += 5*(my - originY)/500.0;
        }
        originX=mx;
        originY=my;
        if(Greenfoot.isKeyDown("up"))
            p3z -= .1;
        if(Greenfoot.isKeyDown("down"))
            p3z += .1;
        
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
}
