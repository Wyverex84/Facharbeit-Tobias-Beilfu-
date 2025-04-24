//34567890123456789012345678901234567890123456789012345678
import greenfoot.*;



/*********************************************************
This class can be used by objects of other classes to
place any number of custom GUI menus in a Greenfoot world.
<p>

The menu elements are created as objects of the class
named Labels.  Therefore, any configuration of text and
images supported by the Labels class can be used to create
the header and the items contained in the menu.
<p>

Each menu element is described by an incoming object of
the class named Description, which is simply a container
for the various parameters required to instantiate the
required objects of the Labels class that represent the
menu element.
<p>

Two versions of each menu element are created, one in a
primary color and the other in a rollover color.  When
the mouse touches a menu item, it changes to the rollover
color.  When the mouse retreats from the menu item, it
changes back into the primary color.
<p>

Any object that instantiates an object of this class must
be an object of a class the implements the MenuListener
interface, which declares the following two methods:
<p>clickCallback
<br>moveCallback
<p>

When a Menu object is instantiated, it registers a 
reference to the object that instantiated the Menu object
as a listener object.
<p>

When the mouse is clicked on a menu item, the 
clickCallback method is called on the listener object to 
notify it that a click event has occurred on a menu item.
A reference to the Menu object and the numeric index of 
the menu item are passed as parameters to the callback
method.
<p>

Whenever the mouse touches a primary menu item causing it
to switch to the rollover version, the method named 
moveCallback is called on the listener, once again 
passing a reference to the Menu object and the numeric 
index of the menu item. Note that sometimes due to timing 
considerations, multiple calls to the method may occur 
when the switch occurs.
<p>

Whenever the mouse retreats from the menu to the world 
background, causing the menu items to disappear, the 
moveCallback method is called on the listener.  In this 
special case, a value of 1000 is passed as the numeric 
index value. Once again, because of timing considerations,
it is possible that multiple calls may occur when this 
event occurs.<p>

Several different size metrics can be obtained by calling
appropriate methods on the Menu object.
 
@author Dick Baldwin 
@version 07/19/08
@see MenuListener, Labels, Description
*********************************************************/
public class Menu extends Actor{

  //Indentation of menu items relative to menu header.
  int indent = 10;
  //Array to contain refrences to menu elements that are 
  // displayed in the primary color.
  Labels[] primary;
  //Array to contain references to menu elements that are
  // displayed in the rollover color.
  Labels[] rollover;
  //Coordinates in pixels of upper left corner of the 
  // menu header.
  int xCoor;
  int yCoor;
  //Reference to the world in which the menu is to be
  // placed.
  World world;
  //Reference to the object that instantiated this menu 
  // object. Note that this reference is saved an an
  // interface type.
  MenuListener menuListener;
  //Reference to this Menu object
  Menu thisMenu;
  //Flag to prevent excessive computer loading as a result
  // of mouse movement on the world background.
  boolean clean = true;
  
  /*******************************************************
  Construct an object of the Menu class.<p><p>
  
  The first element in the incoming array contains a  
  reference to a Description object containing
  information about the menu header. Each remaining 
  element contains information for a menu item in the 
  order from top to bottom of the expanded menu.<p><p>
  
  The x and y parameters specify the coordinates of 
  the upper-left corner of the menu header. The World 
  parameter is a reference to the world in which the 
  menu will be placed. The last parameter is a 
  reference back to the object that instantiated this
  object. This reference is used to notify that 
  object about mouse move and click events.<p><p>
  
  It is recommended that the menu header be separated
  from the top of the world by at least five pixels to
  make it unlikely that the mouse will escape from the
  menu without causing the menu items to be hidden.
  *******************************************************/
  public Menu(Description[] descrip,int x,int y,
                   World world,MenuListener menuListener){
    //Save the coordinates of the upper-left corner of the
    // menu header.
    xCoor = x;
    yCoor = y;
    //Save a reference to the world.
    this.world = world;
    //Save a reference to the menuListener.
    this.menuListener = menuListener;
    //Save a reference to this Menu object.
    thisMenu = this;
    
    //Make the Menu object invisible.  Note, however that
    // the labels that make up the menu are not invisible.
    setImage(new GreenfootImage(1,1));
    
    //Construct two arrays that will contain references to
    // menu elements, including the header, in both the 
    // primary and rollover colors.
    primary = new Labels[descrip.length];
    rollover = new Labels[descrip.length];

    //Construct and save the menu elements, including the
    // header, in both the primary and rollover colors. 
    // Also place the header in the world.
    for(int cnt = 0;cnt < descrip.length;cnt++){
      //Primary color.
      primary[cnt] = new Labels(
                          descrip[cnt].text,
                          descrip[cnt].font,
                          descrip[cnt].style,
                          descrip[cnt].size,
                          descrip[cnt].primaryColor,
                          descrip[cnt].picture,
                          descrip[cnt].leftJustifyPicture,
                          descrip[cnt].bothSides,
                          descrip[cnt].outline,
                          descrip[cnt].outlineColor,
                          descrip[cnt].borderImage);

      if(cnt == 0){
        //This is the header. Place it in the world.
        world.addObject(primary[cnt],
                      xCoor + primary[cnt].getWidth()/2,
                      yCoor + primary[cnt].getHeight()/2);
      }//end if

      //Rollover color.
      rollover[cnt] = new Labels(
                          descrip[cnt].text,
                          descrip[cnt].font,
                          descrip[cnt].style,
                          descrip[cnt].size,
                          descrip[cnt].rolloverColor,
                          descrip[cnt].picture,
                          descrip[cnt].leftJustifyPicture,
                          descrip[cnt].bothSides,
                          descrip[cnt].outline,
                          descrip[cnt].outlineColor,
                          descrip[cnt].borderImage);
    }//end for loop
                    
  }//end constructor

  /*******************************************************
  The purpose of the act method in this class is to
  cause the menu to behave properly as a result of
  mouse moves and mouse clicks on the header and the
  items in the menu.  Note that when the rollover
  versions of the header and the menu items appear,
  they simply cover up the primary versions. The
  primary versions are not removed when the rollover
  versions appear. However, the rollover and primary
  versions are removed when the mouse retreats to the
  world background.<p><p> 

  The Menu object registers a listener object when it is 
  instantiated.<p><p>

  When the mouse is clicked on a menu item, the 
  clickCallback method is called on the listener object to 
  notify it that a click event has occurred on a menu 
  item.  A reference to the Menu object and the numeric 
  index of the menu item are passed as parameters to the 
  callback method.<p><p>

  Whenever the mouse touches a primary menu item causing 
  it to switch to the rollover version, the method named 
  moveCallback is called on the listener, once again 
  passing a reference to the Menu object and the numeric 
  index of the menu item. Note that sometimes due to 
  timing considerations, multiple calls to the method may
  occur when the switch occurs.<p><p>

  Whenever the mouse retreats from the menu to the world 
  background, causing the menu items to disappear, the 
  moveCallback method is called on the listener.  In this 
  special case, a value of 1000 is passed as the numeric 
  index value. Once again, because of timing 
  considerations, it is possible that multiple calls may 
  occur when this event occurs.
  *******************************************************/
  public void act(){
    //Process a mouseMoved event on the header in the
    // primary color.
    if(Greenfoot.mouseMoved(primary[0])){
      //Cover the primary version with a rollover version.
      world.addObject(rollover[0],
                        xCoor + primary[0].getWidth()/2,
                        yCoor + primary[0].getHeight()/2);
      //Expand the menu showing all menu items in the
      // primary color. Indent each menu item by the
      // prescribed amount relative to the left edge of
      // the menu header.  Note that the placement 
      // coordinates of the addObject method refer to the
      // center of the object being added.  Compute and
      //save the y-coordinate of the bottom of each menu
      // element when it is constructed and added to the
      // menu.
      //First, compute the bottom of the menu header.
      int bottom = yCoor + primary[0].getHeight();
      for(int cnt = 1;cnt < primary.length;cnt++){
        world.addObject(primary[cnt],
               xCoor + indent + primary[cnt].getWidth()/2,
               bottom + primary[cnt].getHeight()/2);
        bottom += primary[cnt].getHeight();
      }//end for loop
      //Set flag requiring cleanup.
      clean = false;
    }//end if
                    
    //Make the menu items switch to the rollover color
    // when they are touched by the mouse. Also notify the
    // menuListener that a menuItem has been switched to 
    // the rollover color.
    for(int cnt = 1;cnt < primary.length;cnt++){
      if(Greenfoot.mouseMoved(primary[cnt])){
        //Hide all exposed rollover menu items.
        for(int index = 1;index < primary.length;index++){
          world.removeObject(rollover[index]);
        }//end for loop
        //Now expose the rollover version for the menu
        // item that was touched with the mouse. Note that
        // this writes the rollover version on top of the
        // primary version. It does not remove the
        // primary version.
        world.addObject(rollover[cnt],primary[cnt].getX(),
                                     primary[cnt].getY());

        //Notify the menuListener that a menuItem has been
        // switched to the rollover color.
        menuListener.moveCallback(thisMenu,cnt);
      }//end if

    }//end for loop
    
    //Notify the menuListener when a rollover menu item is
    // clicked. Pass a reference to this menu object
    // along with the index of the menu item that was
    // clicked.
    for(int cnt = 1;cnt < primary.length;cnt++){
      if(Greenfoot.mouseClicked(rollover[cnt])){
        menuListener.clickCallback(thisMenu,cnt);
      }//end if
    }//end for loop
    
    //Hide all menu items and the rollover version of the
    // header when the mouse retreats to the world. Also
    // notify the menuListener.
    if((Greenfoot.mouseMoved(world)) && !clean){
      world.removeObject(rollover[0]);//rollover header
      for(int cnt = 1;cnt < primary.length;cnt++){
        //Primary and rollover menu items.
        world.removeObject(primary[cnt]);
        world.removeObject(rollover[cnt]);
      }//end for loop
      //Set a flag to prevent excessive computer loading
      // as a result of mouse movement in the world
      // background.
      clean = true;
      menuListener.moveCallback(thisMenu,1000);
    }//end if
  }//end act method

  /*******************************************************
  The purpose of this method is to return the width of the
  menu header in pixels.
  *******************************************************/
  public int headerWidth(){
    return primary[0].getWidth();
  }//end headerWidth

  /*******************************************************
  The purpose of this method is to return the height of
  the menu header in pixels.
  *******************************************************/
  public int headerHeight(){
    return primary[0].getHeight();
  }//end headerHeight

  /*******************************************************
  The purpose of this method is to return the width of 
  the element immediately below the header. The intended
  usage of this method is to make it possible to 
  place the header for another menu far enough to the 
  right that it won't be touching the top edge of the
  first element in the menu to its left.
  *******************************************************/
  public int firstElementWidth(){
    return primary[1].getWidth() + indent;
  }//end firstElementWidth

  /*******************************************************
  The purpose of this method is to return the width of 
  the longest menu element including the header and 
  including the indentation as part of the length.
  *******************************************************/
  public int maxWidth(){
    int width = primary[0].getWidth();
    for(int cnt = 1;cnt < primary.length;cnt++){
      if((primary[cnt].getWidth() + indent) > width){
        width = primary[cnt].getWidth() + indent;
      }
    }//end for loop
    return width;
  }//end maxWidth
  
  /*******************************************************
  The purpose of this method is to return the total
  height of the menu including the header.
  *******************************************************/
  public int totalHeight(){
    int height = 0;
    for(int cnt = 0;cnt < primary.length;cnt++){
      height += primary[cnt].getHeight();
    }//end for loop
    return height;
  }//end gotalHeight
  //----------------------------------------------------//
}//end class Menu