//34567890123456789012345678901234567890123456789012345678
import greenfoot.*;



/*********************************************************
This is a driver class that illustrates how to use the 
Menu class to create and service custom GUI menus. For
more information, see the Scenario Information in the IDE.
@author Dick Baldwin
@version 07/19/08
*********************************************************/
public class Driver extends Actor implements MenuListener{
  boolean firstPass = true;
  Menu leftMenu;
  Menu rightMenu;
  
  //References to images.
  private GreenfootImage grapes;
  private GreenfootImage bee;
  private GreenfootImage butterfly;
  private GreenfootImage frog;
  
  private GreenfootImage smallGrapes;
  private GreenfootImage smallBee;
  private GreenfootImage smallButterfly;
  private GreenfootImage smallFrog;
  
  //References to objects that are manipulated when menu
  // items are clicked in the right menu.
  Labels rightTargetPlain;
  Labels rightTargetBold;
  Labels rightTargetItalic;
  Labels rightTargetBoldItalic;
  
  //References to arrays that contain descriptions of the
  // menu elements.
  Description[] leftDescrip;
  Description[] rightDescrip;
  
  //Position information.
  int leftMargin = 20;
  int topMargin = 10;
  int separation = 25;
  
  //A very simple target object that is manipulated by
  // clicking the items in the left menu.
  LeftTarget leftTarget;
  //----------------------------------------------------//
  
  public Driver(){//constructor
    //Make this object invisible.
    setImage(new GreenfootImage(1,1));
    
    //Prepare the images that will be used in the 
    // scenario
    grapes = new GreenfootImage("grapes.gif");
    grapes.scale(
                grapes.getWidth()/3,grapes.getHeight()/3);
    
    bee = new GreenfootImage("bee.gif");
    smallBee = new GreenfootImage("bee.gif");;
    smallBee.scale(bee.getWidth()/5,bee.getHeight()/5);
    
    butterfly = new GreenfootImage("butterfly.gif");
    smallButterfly = new GreenfootImage("butterfly.gif");
    smallButterfly.scale(
          butterfly.getWidth()/4,butterfly.getHeight()/4);
    
    frog = new GreenfootImage("frog.gif");
    smallFrog = new GreenfootImage("frog.gif");
    smallFrog.scale(
              frog.getWidth()/4,frog.getHeight()/4);
    
    //Create an array that contains descriptions of the 
    // left menu elements.
    leftDescrip = new Description[4];
    //Create menu header using simplest constructor
    leftDescrip[0] = new Description();
    leftDescrip[0].text = "Left Menu";
    leftDescrip[0].style = Font.BOLD;
    //Create remaining menu items using a different 
    // constructor.
    leftDescrip[1] = new Description("Frog");
    leftDescrip[2] = new Description("Bee");
    leftDescrip[3] = new Description("Butterfly");

    //Create an array that contains descriptions of the 
    // right menu elements.
    rightDescrip = new Description[5];

    //Create menu header using even a different 
    // constructor.
    rightDescrip[0] = new Description("Right Menu",
                                      "SansSerif",
                                      Font.BOLD,
                                      24,
                                      Color.BLACK,
                                      Color.GREEN,
                                      grapes,
                                      true,
                                      true,
                                      false,
                                      Color.BLACK,null);

    //Create menu items using the same constructor.
    rightDescrip[1] = new Description("Bold Bees",
      "SansSerif",Font.BOLD,24,Color.BLACK,Color.GREEN,
      smallBee,true,true,false,Color.BLACK,null);

    rightDescrip[2] = new Description("Italic Butterflies",
      "SansSerif",Font.ITALIC,24,Color.BLACK,Color.GREEN,
      smallButterfly,true,true,false,Color.BLACK,null);

    rightDescrip[3] = new Description("Plain Frogs",
      "SansSerif",Font.PLAIN,24,Color.BLACK,Color.GREEN,
      smallFrog,true,true,false,Color.BLACK,null);
    
    rightDescrip[4] = new Description("Bold-Italic Bees",
      "SansSerif",
      (Font.BOLD) | (Font.ITALIC),//Note bitwise OR
      24,Color.BLACK,Color.GREEN,smallBee,true,true,
      false,Color.BLACK,null);
              
    //Create the target objects that will be manipulated 
    // by the right menu.
    rightTargetPlain = new Labels(
        "Right Target",//Text displayed in the label
        "SansSerif",//Font family to be used.
        Font.PLAIN,//BOLD, ITALIC, PLAIN, etc
        24,//Text size in points
        Color.RED,//Text color
        smallFrog,//Picture for left and/or right
        true,//Left or right justify
        true,//Pictures on both sides of text
        true,//Solid outline
        Color.BLACK,//Solid outline color
        //Border piicure, overrides solid outline
        smallFrog);

    rightTargetBold = new Labels("Right Target",
      "SansSerif",Font.BOLD,24,Color.GREEN,smallBee,true,
      true,true,Color.BLACK,smallBee);
        
    rightTargetItalic = new Labels("Right Target",
      "SansSerif",Font.ITALIC,24,Color.BLUE,
      smallButterfly,true,true,true,Color.BLACK,
      smallButterfly);
      
    rightTargetBoldItalic = new Labels("Right Target",
      "SansSerif",(Font.BOLD) | (Font.ITALIC),24,
      Color.GREEN,smallBee,true,true,true,Color.BLACK,
      smallBee);

    //Instantiate an object for the target of the left
    // menu.
    leftTarget = new LeftTarget(butterfly);
  }//end constructor
  //----------------------------------------------------//

  public void act(){
    if(firstPass){
      firstPass = false;

      //Now create the leftMenu object and add it to the 
      // world.
      leftMenu = new Menu(
        leftDescrip,leftMargin,topMargin,getWorld(),this);
      //Add the menu object to the world so that the act 
      // method on the object will be called.
      getWorld().addObject(leftMenu,1,1);

      //Now create the rightMenu object and add it to the
      // world. You probably need to leave at least 10 
      // pixels between the headers so that a mouse moved
      // event will be fired on the world as the mouse
      // moves from one menu header to the next.
      rightMenu = new Menu(rightDescrip,
            leftMargin + separation + leftMenu.maxWidth(),
            topMargin,getWorld(),this);
      //Add the menu object to the world so that the act 
      // method on the object will be called.
      getWorld().addObject(rightMenu,1,1);
      
      //Add the right target to the world.
      getWorld().addObject(rightTargetPlain,
            leftMargin + separation +leftMenu.maxWidth() +
                            rightTargetPlain.getWidth()/2,
            topMargin + rightMenu.totalHeight() + 
                            rightTargetPlain.getHeight());

      //Add the left target to the world
      getWorld().addObject(leftTarget,
                     leftMargin + leftTarget.getWidth()/2,
                     topMargin + leftMenu.totalHeight() +
                                  leftTarget.getHeight());
    }//end if
  }//end act method
  //----------------------------------------------------//
  
  //This method is called when the mouse is clicked on a
  // menu item. The code in this method manipulates the
  // target objects depending on which item in which 
  // menu fired the click event.
  public void clickCallback(Menu source,int item){
    if((source == leftMenu) && (item == 1)){
      leftTarget.setImage(frog);
    }else if((source == leftMenu) && (item == 2)){
      leftTarget.setImage(bee);
    }else if((source == leftMenu) && (item == 3)){
      leftTarget.setImage(butterfly);
    }else if ((source == rightMenu) && (item == 1)){
      changeRightTarget(rightTargetBold);
    }else if ((source == rightMenu) && (item == 2)){
      changeRightTarget(rightTargetItalic);
    }else if ((source == rightMenu) && (item == 3)){
      changeRightTarget(rightTargetPlain);
    }else if ((source == rightMenu) && (item == 4)){
      changeRightTarget(rightTargetBoldItalic);
    }//end else If
  }//end clickCallback method
  //----------------------------------------------------//
  
  //This method is called when a menu item switches from
  // the primary color to the rollover color, or when the
  // mouse moves from the menu to the world background.
  //If the value of item is 1000, the interpretation 
  // is that the mouse was moved from the menu to the 
  // world background.
  public void moveCallback(Menu source,int item){
    if((source == leftMenu) && (item == 1000)){
      leftTarget.setImage(butterfly);
      //Note: The sound effects seemed to interfere with
      // the online performance so I disabled them in the
      // online version at the Greenfoot gallery.
//      Greenfoot.playSound("bong.wav");
    }else if ((source == rightMenu) && (item == 1000)){
      changeRightTarget(rightTargetPlain);
//      Greenfoot.playSound("bong.wav");
    }else{
//      Greenfoot.playSound("ding.wav");
    }//end else 
  }//end clickCallback method
  //----------------------------------------------------//
  
  //The purpose of this method is to replace the current
  // right target object with  the same or a different 
  // target object.
  private void changeRightTarget(Labels target){
    //First remove all the target objects.
    getWorld().removeObject(rightTargetPlain);
    getWorld().removeObject(rightTargetItalic);
    getWorld().removeObject(rightTargetBold);
    getWorld().removeObject(rightTargetBoldItalic);
    //Now add the correct target object.
    getWorld().addObject(target,
            leftMargin + separation +leftMenu.maxWidth() +
                            rightTargetPlain.getWidth()/2,
            topMargin + rightMenu.totalHeight() +
                            rightTargetPlain.getHeight());
  }//end removeAllTargets
  //----------------------------------------------------//
  
}//end class Driver
