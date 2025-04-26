import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.*; 
import java.awt.Graphics;



public class mgworld extends World
{

  
//images


//screen background 
GreenfootImage bakground = new GreenfootImage(600,400);

//background pic
GreenfootImage basebg = new GreenfootImage("base.png");

//zbuffer pic
GreenfootImage zbuffer = new GreenfootImage(600,400);


//using different sized images:
//it depends on the size of the image you load into 'testimage'
//it automaticly gets resized if too big (see below), and auto resizes 'otherimage'

//just change these values if you need bigger 
//(try to not get carried away, it can get seriously slow)
final static int MAX_IMAGE_HEIGHT = 128;
final static int MAX_IMAGE_WIDTH = 128;

//heightmap image
GreenfootImage testimage = new GreenfootImage("testterra.png");

//color to draw with //or colormap 
GreenfootImage otherimage = new GreenfootImage("tex2.png"); 






//coords
float sx = 1;
float sy = 1;
float sz = -200;

//angles
float angle = 45;  //pitch
float angleb = 45; //yaw
float anglec = 90; //roll
    
   boolean update = false; //only true if user pressed a key
  
   int howToRender = 3;  //rendering mode
   
int[][][] scrxy ; // = new int[64][64][3]; /// [][][0] is x [][][1] is y

float[][] heightmap;

//float[][] zbuff = new float[600][400];

int imgHeight; //height of image
int imgWidth; //width image

float defaultscale = 8; //scale 3d coords by this

///////////////////////////////
    public mgworld()
    {    
        
        super(600, 400, 1);
        setBackground(bakground); 
  
        //get width and height of heightmap
    imgHeight = testimage.getHeight();
    imgWidth = testimage.getWidth();
        
    //dont get too big
    if (imgHeight > MAX_IMAGE_HEIGHT) imgHeight = MAX_IMAGE_HEIGHT;    
    if (imgWidth > MAX_IMAGE_WIDTH) imgWidth = MAX_IMAGE_WIDTH;

    //change render scale if image is too big 
    if (imgHeight > 64) {  defaultscale = 4; }
    if (imgHeight > 128) {  defaultscale = 2; }
    if (imgHeight > (128+64) ) {  defaultscale = 1; }
    
    //resize images just in case
      testimage.scale(imgWidth, imgHeight);
      otherimage.scale(imgWidth, imgHeight);
      
      //init array for screen coordinates
      scrxy = new int[imgWidth][imgHeight][3];
        
      //array for heightmap
      heightmap = new float[imgWidth][imgHeight];
        
      //inith the height map //duh//
      initHeightMap();
      
   //sy = -100;
      try3d(sx,sy,sz);
       bakground.drawImage(basebg,0,0);
      drawWithPolygons();
    }//mgworld
   
    
    public void act()
    {
     //////////////////
     
     //move
      if (Greenfoot.isKeyDown("A") )     {sx += 8; update = true;  }
       if (Greenfoot.isKeyDown("D") )     {sx -= 8; update = true;  }
       
        if (Greenfoot.isKeyDown("W") )     {sy += 8; update = true;   }
       if (Greenfoot.isKeyDown("S") )     {sy -= 8; update = true;  }  
       
         if (Greenfoot.isKeyDown("Q") )     {sz += 8; update = true;  }
       if (Greenfoot.isKeyDown("E") )     {sz -= 8; update = true;  }
       
       //rotate
        if (Greenfoot.isKeyDown("left") )     {angle += 8; update = true;  }
       if (Greenfoot.isKeyDown("right") )     {angle -= 8; update = true;  }  
       
        if (Greenfoot.isKeyDown("down") )     {angleb += 8; update = true;  }
       if (Greenfoot.isKeyDown("up") )     {angleb -= 8; update = true;  }
       
        if (Greenfoot.isKeyDown("x") )     {anglec += 8; update = true;  }
       if (Greenfoot.isKeyDown("c") )     {anglec -= 8; update = true;  }
       
       //change render mode
        if (Greenfoot.isKeyDown("1") )     {howToRender=1; update = true;  }
        if (Greenfoot.isKeyDown("2") )     {howToRender=2; update = true;  }
        if (Greenfoot.isKeyDown("3") )     {howToRender=3; update = true;  }
        if (Greenfoot.isKeyDown("4") )     {howToRender=4; update = true;  }
         if (Greenfoot.isKeyDown("5") )     {howToRender=5; update = true;  }
         if (Greenfoot.isKeyDown("6") )     {howToRender=6; update = true;  }
          if (Greenfoot.isKeyDown("7") )     {howToRender=7; update = true;  }
       
       //reset
       if (Greenfoot.isKeyDown("space") )    
       {
           angle = 45;
           angleb = 45;
           anglec = 90;
            sx = 1;
            sy = 1;
            sz = -200;
           update = true; 
        }//endif
       
  if (sz > 1) {sz = 1;}
  
  //anglec+=10; update = true;
        
        //limit angles' value
      //these arent really neccesary btw
       if (angle > 360) {angle %= 360;}
        if (angle < 0) {angle += 360;}
        
        if (angleb > 360) {angleb %= 360;}
        if (angleb < 0) {angleb += 360;}
        
      if (anglec > 360) {anglec %= 360;}
        if (anglec < 0) {anglec += 360;}
  
  //only update screen after a key was pressed
      if (update) 
      {
          try3d(sx,sy, sz); 
          bakground.drawImage(basebg,0,0);
          
          switch(howToRender)
            {
          case 1:          drawWithDots();      break;
          case 2:          drawWithLines();          break;
          case 3:          drawWithPolygons();          break;
          
          case 4:   drawZBuffer(false); break; //polygons with zbuffer
           case 5:   drawZBuffer(true); break; //draw only the zbuffer
           
           case 6:   drawZBuffer_points(false); break; //experimental - zbuffer using points
           case 7:   drawZBuffer_points(true); break;
            }        
          
          update = false;
        }//endif
      
       
     //////////////////
    }//act
///////////////////////////////////
  
private void initHeightMap()
{
Color gotColor;

for (int x = 0; x < imgWidth; x++)
     {
         for (int z = 0; z < imgHeight; z++)
         {
          //get color from testimage (aka the heightmap) and convert it to grayscale
             gotColor = testimage.getColorAt((int) x,(int) z);
             
             heightmap[x][z] =  ( ( (gotColor.getBlue()+gotColor.getRed() +gotColor.getGreen())/ 3)*0.1f ); 
            
            }//next z
        }//next x


}//initheightmap


    private void try3d(float vx, float vy, float vz)
    {
       // bakground.clear();
       
        int screenx, screeny;
         Color gotColor;
         
        float yy = 0;
        float xx = 0;
        float zz = 0;
        
        float y = 0;
        
        float ang = angle;
        ang =(float) Math.toRadians(ang);
        
        float ang2 = angleb;
        ang2 = (float) Math.toRadians(ang2);
        
        float ang3 = anglec;
        ang3 = (float) Math.toRadians(ang3);
        
        float scale = defaultscale;
        

                
        //ok so how this works is really primitve
        //it just transfer the coordinates x 0-63 z 0-63 y - based on height in image
        //to the screen
        
        //there is no z sorting yet (no idea how to do that yet), so valleys tend to appear in front mountains instead of behind them
        //and so i actually wanted to do something with voxels but turned out im too stopeed/no clue to do that - nevermind
        
        //pivot correction //so the pivot will be in the middle
        int pcX = imgWidth / 2;
        int pcZ = imgHeight / 2;
        
        
     for (float x = 0; x < imgWidth; x++)
     {
         for (float z = 0; z < imgHeight; z++)
         {
           
           
    //         //get color from testimage (aka the heightmap) and convert it to grayscale
  //           gotColor = testimage.getColorAt((int) x,(int) z);
      //       y = ((gotColor.getBlue()+gotColor.getRed() +gotColor.getGreen())/3)*0.1f; 
        y  =  heightmap[(int) x][(int) z];     
               
             //rotation transfer - yaw
               xx =  (float) ((x-pcX) * Math.cos(ang) - (z-pcZ)* Math.sin(ang)); 
               zz = (float) ((z-pcZ) * Math.cos(ang) + (x-pcX)* Math.sin(ang)); 
               
               float zb = zz;
               
               //transfer rotation - pitch
                yy = (float) ((y) * Math.cos(ang2) - (zb)* Math.sin(ang2));
                zz = (float) ((zb) * Math.cos(ang2) + (y)* Math.sin(ang2)); 
               
                float xb = xx;
                float yb = yy;
                
                //transfer rotation - roll
                xx = (float) ((yb) * Math.cos(ang3) - (xb)* Math.sin(ang3));
                yy = (float) ((xb) * Math.cos(ang3) + (yb)* Math.sin(ang3)); 
                
             //  xx -= 32;
              // zz -= 32;
               
              
              //scale to make it look bigger
                xx *=scale;
               zz *=scale;
               yy *= scale;
                       
             
               
               
               //ok so vx, vy, vz are the values we want to transfer/move this thing
               // *256 is the field of view thing -
               // 300 and 200 are the screen dimensions halved -- needed or things would start to draw at top left corner 
                screenx = (int) ( (1 + xx - vx) / (1 + zz + 256 -vz) * 256 + 300 );
                screeny = (int) ( (1 + yy - vy) / (1 + zz + 256 -vz) * 256 + 200 );

                
                
                scrxy[(int) x][(int) z][0] = 0;
                scrxy[(int) x][(int) z][1] =  0;
 
                if (screeny > 0 && screeny < 400 && screenx > 0 && screenx < 600) 
                {
                 //  scrxy[(int) x][(int) z][2]= (int) zz + 256;
                     
                   scrxy[(int) x][(int) z][2]= (int) (2048/ zz);
                 
                  // scrxy[(int) x][(int) z][2]= (int) Math.sqrt(square(vx-xx) + square(vy-yy) +square(vz-zz));
                   
                 
                    //store screen coordinates for drawing
                    scrxy[(int) x][(int) z][0] = screenx;
                    scrxy[(int) x][(int) z][1]=  screeny;
                
                //only coordinates that are not off screen get stored
                //feel free to comment out some code to see why
                }//endif
         }
    }//next xx
    
  // bakground.setColor(new Color(128,128,128));

    }//try3d
/////////////////////////////// 

private static float square(float n)
{  return n*n; }



////////////////////////////////   

private void drawWithDots()
    {
        Color gotColor;
   
      for (int x =0; x < imgWidth; x++)
     {
         for (int z = 0; z < imgHeight; z++)
         {
             gotColor = otherimage.getColorAt((int) x,(int) z);
             bakground.setColor(gotColor);
                if (scrxy[x][z][0] != 0 ) 
                {
                    bakground.fillRect(scrxy[x][z][0], scrxy[x][z][1],6,6); 
                }//endif
    }//next z
}//next x
    }//dots

    //////////////
    private void drawWithLines()
    {
        Color gotColor;
   
      for (int x =0; x < (imgWidth - 1); x++)
     {
         for (int z = 0; z < (imgHeight - 1) ; z++)
         {
             gotColor = otherimage.getColorAt((int) x,(int) z);
             bakground.setColor(gotColor);
                if (scrxy[x][z][0] != 0 && scrxy[x+1][z][0] != 0 && scrxy[x][z+1][0] != 0) 
                {
                    // bakground.drawLine(scrxy[x][z][0], scrxy[x][z][1],scrxy[x+1][z][0], scrxy[x+1][z][1]);
                    bakground.drawLine(scrxy[x][z][0], scrxy[x][z][1],scrxy[x][z+1][0], scrxy[x][z+1][1]); 
                }//endif
    }//next z
}//next x
    }//lines
 /////////////////////////////////////////   
     public void drawZBuffer_points(boolean drawToBg)
    {
        zbuffer.setColor(new Color(0));
        zbuffer.fill();
        
        Color zcolor;
        Color oldcolor;
        Color gotColor;
        
        int checkx;
        int checky;
      for (int x =0; x < (imgWidth - 1); x++)
       {
       for (int z = 0; z < (imgHeight - 1); z++)
        {
            
            checkx = scrxy[x][z][0];
             checky = scrxy[x][z][1];
            
            zcolor = new Color(scrxy[x][z][2]); 
            oldcolor = zbuffer.getColorAt(checkx,checky);
            
            if (zcolor.getRGB() > oldcolor.getRGB())   {
                
                zbuffer.setColor(zcolor);
                zbuffer.fillRect(checkx-2,checky-2, 6,6);    
                
              gotColor = otherimage.getColorAt((int) x,(int) z);
              //bakground.setColor(gotColor);
              
              int alpha = 64+(scrxy[x][z][2]/255);
              if (alpha < 0) {alpha = 0;}
              if (alpha > 255) {alpha = 255;}
              
              bakground.setColor(new Color (gotColor.getRed(), gotColor.getGreen(), gotColor.getBlue(),alpha) );
              
              

               bakground.fillRect(checkx-2,checky-2, 6,6);
                
            }//endif
        }//z
       }//x
       if (drawToBg) { bakground.drawImage(zbuffer,0,0);}
    }//zbuff points

//////////////////////////////////////////////
    public void drawZBuffer(boolean drawToBg)
    {
        //ok so the theory is that you draw every pixel you want to render
        //with the color of their z coordinate to a buffer
        //but only draw the closest pixels
        //umm.. i think this explains it better: http://en.wikipedia.org/wiki/Zbuffer
        
        
        //fill whole image with black
        zbuffer.setColor(new Color(0));
        zbuffer.fill();
        
        Color zcolor;
        Color oldcolor;
        
         Color gotColor;
        
        int checkx;
        int checky;
        int checkcolor;

        
        for (int x =0; x < (imgWidth - 1); x++)
     {
         for (int z = 0; z < (imgHeight - 1); z++)
         {
             if (scrxy[x][z][0] != 0 && scrxy[x+1][z][0] != 0 && scrxy[x][z+1][0] != 0 && scrxy[x+1][z+1][0] != 0)     {

                 
                checkx = (scrxy[x][z][0] + scrxy[x+1][z][0] + scrxy[x][z+1][0] + scrxy[x+1][z+1][0])/4;  //x1+x2+x3+x4  /4
               
                checky = (scrxy[x][z][1] + scrxy[x+1][z][1] + scrxy[x][z+1][1] + scrxy[x+1][z+1][1])/4; //y1+y2+y3+y4  /4
                
           //      checkcolor =(scrxy[x][z][2] + scrxy[x+1][z][2] + scrxy[x][z+1][2] + scrxy[x+1][z+1][2]); //z1+z2+z3+z4
   
   checkcolor =(scrxy[x][z][2] + scrxy[x+1][z][2] + scrxy[x][z+1][2] + scrxy[x+1][z+1][2]) /4;
         // checkcolor =(scrxy[x][z][2] | scrxy[x+1][z][2] | scrxy[x][z+1][2] | scrxy[x+1][z+1][2]);
     
    
   //   checkcolor =(scrxy[x][z][2]);
      
                 zcolor = new Color(checkcolor); 
                 oldcolor = zbuffer.getColorAt(checkx,checky);
                 

                 if (zcolor.getRGB() > oldcolor.getRGB())   {
            
                  //   zbuffer.fillRect(checkx,checky, 15,15);
               zbuffer.setColor(zcolor);
              zbuffer.fillPolygon( 
              new int[] { scrxy[x][z][0],scrxy[x+1][z][0],scrxy[x+1][z+1][0],scrxy[x][z+1][0]},
              new int[] {scrxy[x][z][1],scrxy[x+1][z][1],scrxy[x+1][z+1][1],scrxy[x][z+1][1]}
              ,4);   //fillpoly
              
//            
            if (!drawToBg) {   
              gotColor = otherimage.getColorAt((int) x,(int) z);
              bakground.setColor(gotColor);

               bakground.fillPolygon( 
              new int[] { scrxy[x][z][0],scrxy[x+1][z][0],scrxy[x+1][z+1][0],scrxy[x][z+1][0]},
              new int[] {scrxy[x][z][1],scrxy[x+1][z][1],scrxy[x+1][z+1][1],scrxy[x][z+1][1]}
              ,4);   //fillpoly
                 }//endif
                 
                  }//endif //zcolor
        
             }//endif 0
              
            }//next z          
     }//next x
     
  if (drawToBg) { bakground.drawImage(zbuffer,0,0);}
    
    }//zbuff
  ///////////////  
  
  
    private void drawWithPolygons()
    {
    // bakground.clear();
    Color gotColor;
   
      for (int x =0; x < (imgWidth - 1); x++)
     {
         for (int z = 0; z < (imgHeight - 1); z++)
         {
             gotColor = otherimage.getColorAt((int) x,(int) z);
              bakground.setColor(gotColor);
                      

              
              
              
         if (scrxy[x][z][0] != 0 && scrxy[x+1][z][0] != 0 && scrxy[x][z+1][0] != 0 && scrxy[x+1][z+1][0] != 0) 
         {
           // bakground.drawLine(scrxy[x][z][0], scrxy[x][z][1],scrxy[x+1][z][0], scrxy[x+1][z][1]);
           //  bakground.drawLine(scrxy[x][z][0], scrxy[x][z][1],scrxy[x][z+1][0], scrxy[x][z+1][1]);
     
             bakground.fillPolygon
             ( 
             new int[]
                {
                 scrxy[x][z][0],
                 scrxy[x+1][z][0],
                 scrxy[x+1][z+1][0],
                 scrxy[x][z+1][0],     
                },
             new int[]
             {
                 scrxy[x][z][1],
                 scrxy[x+1][z][1],
                 scrxy[x+1][z+1][1],
                 scrxy[x][z+1][1],     
             },
             4   
             );   //fillpoly
             
              }//endif
              
//                if (scrxy[x+1][z+1][0] != 0 && scrxy[x+1][z][0] != 0 && scrxy[x][z+1][0] != 0) 
//          {
//             bakground.fillPolygon
//              ( 
//              new int[]
//                 {
//                  scrxy[x+1][z+1][0],
//                  scrxy[x+1][z][0],
//                  scrxy[x][z+1][0],     
//                 },
//              new int[]
//              {
//                  scrxy[x+1][z+1][1],
//                  scrxy[x+1][z][1],
//                  scrxy[x][z+1][1],     
//              },
//              3   
//              );    //fillpoly
//             
//             }//endif                

         }//next z
    }//next x
    }//draw with polygons
  //////////////////////////////  
    
    
}//its the end of the world!!! :O   
    
   