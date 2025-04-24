//34567890123456789012345678901234567890123456789012345678

import greenfoot.*;


/*********************************************************
The purpose of this class is to encapsulate a set of 
values that can be used to instantiate two Labels objects
which have different text colors, but which are otherwise
the same.<p>

An object of this class contains one field for each of the
parameters required to instantiate a Labels object (plus 
an extra field for the rollover text color).<p>

All of the fields are intialized with usable values.
However, multiple overloaded constructors are provided to
allow for the initialization of the field values in a
variety of different ways.

@author Dick Baldwin 
@version 07/19/08
@see MenuListener, Labels, Menu
*********************************************************/
public class Description{
  //Text displayed in the label
  public String text = "Text";
  public String font = "SansSerif";//Font or font family
  //Font.BOLD, ITALIC, PLAIN, etc
  public int style = Font.PLAIN;
  public int size = 24;//Text size in points
  public Color primaryColor = Color.BLACK;//Text color
  //This color is intended to be used when a menu item is 
  // touched by the mouse
  public Color rolloverColor = Color.GREEN;
  //Picture for left end, right end, or both ends.
  public GreenfootImage picture = null;
  //Left or right justify
  public boolean leftJustifyPicture = true;
  //Pictures on both ends of text
  public boolean bothSides = false;
  public boolean outline = false;//Solid outline
  //Solid outline color if present
  public Color outlineColor = Color.BLACK;
  //Border image or null for no image borders
  public GreenfootImage borderImage = null;
  
  //This class provides several overloaded constructors
  // that let you choose the amount of detail that you
  // want to include in the call to the constructor. Note
  // that you can always modify the values of the above
  // variables with separate individual statements.
  
  /*******************************************************
  Use this constructor when you don't want to provide
  any detail at all in the call to the constructor.
  *******************************************************/
  public Description(){
  }//end overloaded constructor

  /*******************************************************  
  Use this constructor when you only want to provide
  the text that is to appear on the menu element in the
  call to the constructor.
  *******************************************************/
  public Description(String text){
    this.text = text;
  }//end overloaded constructor

  /*******************************************************  
  Use this constructor when you want to include all of
  the detail in the call to the constructor.
  *******************************************************/
  public Description(
    String text,//Text displayed in the label
    String font,//Font or family
    int style,//Font.BOLD, ITALIC, PLAIN, etc
    int size,//Text size in points
    Color primaryColor,//Text color
    //This color is used when a menu item is touched 
    // by the mouse
    Color rolloverColor,
    GreenfootImage picture,//Picture for end(s)
    boolean leftJustifyPicture,//Left or right justify
    boolean bothSides,//Pictures on both ends of text
    boolean outline,//Solid outline
    Color outlineColor,//Solid outline color
    GreenfootImage borderImage)//Border image or null
  {
    this.text = text;
    this.font = font;
    this.style = style;
    this.size = size;
    this.primaryColor = primaryColor;
    this.rolloverColor = rolloverColor;
    this.picture = picture;
    this.leftJustifyPicture = leftJustifyPicture;
    this.bothSides = bothSides;
    this.outline = outline;
    this.outlineColor = outlineColor;
    this.borderImage = borderImage;
  }//end overloaded constructor
  //----------------------------------------------------//
}//end class description