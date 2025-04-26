


public class Object  
{
    //POINT DATA
    double[] x;
    double[] y;
    double[] z;
    int[][] shapes;
    int[][] points;
    double[] distance;
    Color[] colors;
    
    //TEMPORARY VARIABLES
    double xTemp1;
    double yTemp1;
    double zTemp1;
    
    //OBJECT DATA
    double X=0,Y=0,Z=0,xa=0,ya=0;  //default location set to 0,0,0
                                   //default rotation set to 0,0
    
    public Object(double[] xp,double[] yp,double[] zp,int[][] sp,Color[] c)  //CREATS A NEW OBJECT
    {
        x=xp;
        y=yp;
        z=zp;
        shapes=sp;
        points = new int[2][x.length];
        distance = new double[x.length];
        colors = c;
    }
    
    public void getPoints(double dx, double dy, double dz){     //calculates the 2D coordinates
        double a1 = Math.cos(ya);                               //of all the points
        double a2 = Math.sin(ya);
        double a3 = Math.cos(xa);
        double a4 = Math.sin(xa);                       //accounts for object rotation/location
        for(int i=0;i<x.length;i++){                    //as well as camera location
            xTemp1 = x[i]*a1 - z[i]*a2;                 //camera rotation could be added with a modification
            zTemp1 = z[i]*a1 + x[i]*a2;                 //of this getPoints method
            
            yTemp1 = y[i]*a3 - zTemp1*a4;
            zTemp1 = zTemp1*a3 + y[i]*a4;
            
            xTemp1 = xTemp1 + X - dx;
            yTemp1 = yTemp1 + Y - dy;
            zTemp1 = zTemp1 + Z - dz;
            
            distance[i] = Math.pow(xTemp1,2)+Math.pow(yTemp1,2)+Math.pow(zTemp1+9,2);       //calculates the 'distance' 
                                                                                            //to each point to be used int
            points[0][i] = 200+(int)(350*Math.atan2(xTemp1,zTemp1));                        //the z-buffer
            points[1][i] = 200+(int)(350*Math.atan2(yTemp1,zTemp1));
        }
        //return points;
    }
    
    public int[][] getPolygon(int num){     //returns the coordinates of a specific polygon       
        int tr1 = shapes[num][0];           
        int tr2 = shapes[num][1];           //polygons of 3 and 4 sides are currently
        int tr3 = shapes[num][2];           //supported, but more could be added
        if(shapes[num].length==4){          //with a simple change
            int tr4 = shapes[num][3];
            int[][] triangle ={{points[0][tr1],points[0][tr2],points[0][tr3],points[0][tr4]},
                               {points[1][tr1],points[1][tr2],points[1][tr3],points[1][tr4]}};              
            return triangle;
        }else{
            int[][] triangle ={{points[0][tr1],points[0][tr2],points[0][tr3]},
                               {points[1][tr1],points[1][tr2],points[1][tr3]}};
            return triangle;
        }
    }
    
    public boolean renderable(int num){ //determines whether or not a polygon is off of the screen
        int tr1 = shapes[num][0];       //used to simplify rendering
        int tr2 = shapes[num][1];
        int tr3 = shapes[num][2];
        if(shapes[num].length==4){
            int tr4 = shapes[num][3];
            int x1=points[0][tr1],x2=points[0][tr2],x3=points[0][tr3],x4=points[0][tr4];
            int y1=points[1][tr1],y2=points[1][tr2],y3=points[1][tr3],y4=points[1][tr4];              
            return !((x1<0 && x2<0 && x3<0 && x4<0) || (y1<0 && y2<0 && y3<0 && y4<0) || (x1>400 && x2>400 && x3>400 && x4>400) || (y1>400 && y2>400 && y3>400 && y4>400));
        }else{
            int x1=points[0][tr1],x2=points[0][tr2],x3=points[0][tr3];
            int y1=points[1][tr1],y2=points[1][tr2],y3=points[1][tr3];              
            return !((x1<0 && x2<0 && x3<0) || (y1<0 && y2<0 && y3<0) || (x1>400 && x2>400 && x3>400) || (y1>400 && y2>400 && y3>400));
        }
    }
    
    public int numPoly(){       //number of polygons in this object
        return shapes.length;
    }
    
    public int numPoints(int num){  //number of points in this object   
        return shapes[num].length;

    }
    
    public double distance(int num){    //distance to a polygon
        int tr1 = shapes[num][0];
        int tr2 = shapes[num][1];       //takes the average of all the distances
        int tr3 = shapes[num][2];       //to each point on a polygon
        if(shapes[num].length==4){
            int tr4 = shapes[num][3];   //used in z-buffering
            return (distance[tr1]+distance[tr2]+distance[tr3]+distance[tr4])/4.0;
        }else{
            return (distance[tr1]+distance[tr2]+distance[tr3])/3.0;
        }
    }
    
    
    
    public Color getColor(int num){     //returns the color of a polygon
        return colors[num];             //could be expanded to include border color
    }
    
    public Object duplicate(){               //method to create a duplicate
        double[] xn = new double[x.length];  //object with new instance data
        for(int i=0;i<x.length;i++){
            xn[i] = x[i];
        }
        double[] yn = new double[y.length];
        for(int i=0;i<y.length;i++){
            yn[i] = y[i];
        }
        double[] zn = new double[z.length];
        for(int i=0;i<z.length;i++){
            zn[i] = z[i];
        }
        int[][] sn = shapes;
        Color[] cn = new Color[colors.length];
        for(int i=0;i<colors.length;i++){
            cn[i] = colors[i];
        }
        return new Object(xn,yn,zn,sn,cn);
    }
    
    public void setLocation(double a, double b, double c){  //sets the location
        X=a;                                                //of the object
        Y=b;                                                //in 3D space
        Z=c;
    }
    
    public void move(double a, double b, double c){         //moves the object
        X=X+a;                                              //a certain amount in
        Y=Y+b;                                              //3D space
        Z=Z+c;
    }
    
    public void setRotation(double a, double b){            //sets the rotation
        xa=a;                                               //of the obect in
        ya=b;                                               //3D spcae
    }
    
    public void rotate(double a, double b){                 //rotates the object
        xa=xa+a;                                            //a certain amount
        ya=ya+b;                                            //in 3D space
    }
    
    public double[] getLocation(){          //returns the location of this object
        double[] pos = {X,Y,Z};
        return pos;
    }
    
    public double[] getRotation(){          //returns the rotation of this object
        double[] rot = {xa,ya};
        return rot;
    }

}
