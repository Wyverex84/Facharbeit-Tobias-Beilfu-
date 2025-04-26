import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)



public class raster extends World
{
    //RASTER DATA
    Object[] stack = new Object[256];   //max # of obects.  Is arbitrary, can be
    int top=0,next;                     //any number sufficiently large to hold
    double distance,a,b,l;              //all your objects
    double[] dist = new double[1024];   //same with max # of polygons
    int[][][] points = new int[1024][4][4];
    Color[] colors = new Color[1024];
    
    //CAMERA DATA
    double camera=0;
    double x=0,y=0,z=-9;
    
    //OBJECT DATA
    double[] t1 = {1,1,1,-1,-1,-1,-1,1};    //X coordinates of all points
    double[] t2 = {1,-1,-1,-1,-1,1,1,1};    //Y coordinates of all points
    double[] t3 = {1,1,-1,-1,1,1,-1,-1};    //Z coordinates of all points
    int[][] t4 = {{1,2,3,4},    //polygons:
                  {1,4,5,0},    //conects point 1,4,5,and 0 to create a square
                  {1,0,7,2},
                  {4,3,6,5},
                  {6,7,0,5},
                  {2,3,6,7}};
    Color[] c ={Color.blue,     //colors for cooresponding polygons
                Color.green,    //could be expanded to includeborder colors
                Color.red,
                Color.yellow,
                Color.white,
                Color.black,};
                
                
    Object test1 = new Object(t1,t2,t3,t4,c);    //creates an object from object data
    Object test2 = test1.duplicate();            //creates a ne duplicate object
    Object test3 = test1.duplicate();            //location is set to 0,0,0
    Object test4 = test1.duplicate();           //rotation is set to 0,0                 
    Object test5 = test1.duplicate(); 
    
    public raster()
    {  
        super(400,400,1);
        addObject(new FPS(),54,8);
        
        addToRaster(test1);
        addToRaster(test2);
        addToRaster(test3);
        addToRaster(test4);
        addToRaster(test5);
        
        
        test2.setLocation(1,0,3); 
        test3.setLocation(-4,-2,-1); 
        test4.setLocation(2,3,8); 
        test5.setLocation(6,1,0); 
    }
    
    public void act(){
        display();
        test1.rotate(0,.1);
        test2.rotate(.01,.03);
        test3.rotate(.2,.1);
        test4.rotate(.01,.09);
    }
    
    public void display() 
    {
        //BASIC CAMERA MOVEMENT
        if(Greenfoot.isKeyDown("left")) //camera rotation could be added with 
            x=x-.1;                     //modification to the Object class
        if(Greenfoot.isKeyDown("up"))
            y=y-.1;
        if(Greenfoot.isKeyDown("down"))
            y=y+.1;
        if(Greenfoot.isKeyDown("right"))
            x=x+.1;
        
        int dim=0;        
        getBackground().setColor(Color.white);
        getBackground().fillRect(0,0,400,400);

        for(int s=0;s<top;s++){         //z-buffer.  Note that it merely uses z-sorting
            Object now = stack[s];      //so z-buffering will not work for all objects
            now.getPoints(x,y,z);
            for(int i=0;i<now.numPoly();i++){
                points[dim] = now.getPolygon(i);
                if(now.renderable(i)){        //only adds renderable obects to z-buffer
                    dist[dim] = now.distance(i);
                    colors[dim] = now.getColor(i);
                    dim++;
                }
            }
        }
        
        distance = 1000000000; 
        for(int f = 0;f<dim;f++){
            a = distance + .1;
            distance = 0;  
       
            for(int g = 0;g<dim;g++){            //finds next farthest object to render
                l = dist[g];         
                if( l < a  && l > distance) {
                    distance = l;
                    b = g;
                }        
            }
            next = (int)b;
            dist[next] = 10000000;
            getBackground().setColor(colors[next]);
            getBackground().fillPolygon(points[next][0],points[next][1],points[next][0].length);
            getBackground().setColor(Color.black);
            getBackground().drawPolygon(points[next][0],points[next][1],points[next][0].length);
        }
        getBackground().setColor(Color.red);
        getBackground().drawString("Polygons:"+dim,3,32);
    }
         
    //OBECTS NOT IN THE RENDER STACK CAN STILL BE ACTIVE, BUT WILL NOT BE DRAWN
    public void addToRaster(Object s){  //adds objects to the render stack
        stack[top] = s;                 //note that you CAN add an obect twice, and this
        top++;                          //will result in it being drawn twice
        System.out.println(s + " added to slot " + top);
    }
    
    public void removeFromRaster(Object s){ //removes ALL instances of an objects 
        int i=0;                            //from the render stack
        System.out.println("Ship " + s + " removed from raster");
        for(int f=0;f<top;f++){
            if(stack[f]!=s){
                stack[i] = stack[f];
                i++;
            }  
        }
        top--;
    }
    
}
