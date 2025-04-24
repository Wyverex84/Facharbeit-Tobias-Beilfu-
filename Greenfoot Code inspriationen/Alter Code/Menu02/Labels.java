//34567890123456789012345678901234567890123456789012345678
import greenfoot.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;
import java.awt.font.TextLayout;


/*********************************************************
The purpose of this class is to make it possible to
create rectangular Actor objects containing various
combinations of text and images.  These objects can be
used as labels, menu items, buttons, etc. Because they
are Actor objects, they can be added to a Greenfoot
world and manipulated in the same way that most other
Actor objects can be manipulated, including movement
rotation, responding to mouse events, etc.<p>

The user can specify:<br>
A. The text to be displayed. If the text is an empty
   string, the result is an object that can be used
   as a picture button.<br>
B. The font in which the text will be displayed.<br>
C. The style (BOLD, ITALIC, etc.) in which the text will
   be displayed.<br>
D. The size of the text in points.<br>
E. The color of the text.<br>
F. A picture to optionally be displayed on the left side,
   the right side, or on both sides of the text.<br>
G. A border consisting of either:<br>
1.   A solid rectangle with a specified color.<br>
2.   Tiled horizontal lines of pictures above and below
     the text.

@author Dick Baldwin 
@version 07/11/08
*********************************************************/
public class Labels extends Actor{
  /*******************************************************
  This class needs to extend Actor to make it possible 
  to add it to a World object, among other things.
  *******************************************************/
  public Labels(
    String text,//Text to be displayed in the label
    String fontFamily,//Can be specific font or family
    int style,//BOLD, ITALIC, PLAIN, etc
    int size,//Text size in points
    Color color,//Text color
    GreenfootImage picture,//Picture for left and/or right
    boolean leftJustifyPicture,//Left or right justify
    boolean bothSides,//Pictures on both sides of text
    boolean outline,//Solid outline
    Color outlineColor,//Solid outline color
    GreenfootImage borderImage){//Border image or null
                   
    GreenfootImage image = null;//Ref to output image
    
    //Create the initial version of the output image,
    // which may, or may not contain text. Will not
    // contain text if text parameter is an empty string.
    if(text.length() > 0){
      //Get a transparent image containing the text in the
      // correct font, style, size, and color.
      image = makeTextImage(
                        text,fontFamily,style,size,color);
    }else{
      //The string is empty. Create an empty image one
      // pixel square.
      image = new GreenfootImage(1,1);
    }//end else

    //Add one or two pictures to the output image if
    // picture parameter is not null.
    if(picture != null){
      image = makePictureImage(
              image,picture,leftJustifyPicture,bothSides);
    }//end if
    
    //Draw a solid outline or a tiled border around the
    // text if outline is true and borderImage is null.
    if(outline && (borderImage == null)){
      //Draw a solid border.
      image = drawOutline(image,outlineColor);
    }else if(outline && (borderImage != null)){
      //Draw a line of images across the top and the
      // bottom.
      image = drawTiledBorder(image,borderImage);
    }//end if

    setImage(image);

  }//end constructor

  /*******************************************************  
  The purpose of this method is to draw a line of images
  across the top and the bottom of an incoming image.
  *******************************************************/
  private GreenfootImage drawTiledBorder(
    GreenfootImage image,//Image on which to draw images
    GreenfootImage borderImage){//Image to draw as border
    //Output image width and height.
    int width = image.getWidth();//output same as input
    int height = 
          image.getHeight() + 2*(borderImage.getHeight());
    
    //Width and height of images to draw as border.
    int borderImageWidth = borderImage.getWidth();
    int borderImageHeight = borderImage.getHeight();
    
    //Create a blank transparent version of output image.
    GreenfootImage outputImage = 
                         new GreenfootImage(width,height);
    
    //Used for horizontal spacing of the border images.
    int extraSpace = width%borderImageWidth;
    //Draw row of images across the top of output image.
    for(int cnt = 0;cnt < width/borderImageWidth;cnt++){
      outputImage.drawImage(borderImage,
                   cnt*borderImageWidth + extraSpace/2,0);
    }//end for loop
    
    //Draw row of images across the bottom of output image
    for(int cnt = 0;cnt < width/borderImageWidth;cnt++){
      outputImage.drawImage(borderImage,
                      cnt*borderImageWidth + extraSpace/2,
                      height - borderImageHeight);
    }//end for loop
    
    //Draw the imcoming image on the output image between
    // the two rows of border images.
    outputImage.drawImage(
                         image,0,borderImage.getHeight());
    
    return outputImage;
  }//end drawTiledBorder

  /*******************************************************  
  The purpose of this method is to draw a solid
  rectangular border with a specified color around the
  incoming image.
  *******************************************************/
  private GreenfootImage drawOutline(
    GreenfootImage image,//Image on which to draw border.
    Color color){//Border color

    //Create blank transparent version of output image.
    GreenfootImage outputImage = new GreenfootImage(
            image.getWidth() + 11,image.getHeight() + 11);
            
    //Draw the solid border as a set of three concentric
    // rectangles.
    outputImage.setColor(color);//set drawing color
    outputImage.drawRect(0,0,outputImage.getWidth() - 1,
                             outputImage.getHeight() - 1);
    outputImage.drawRect(1,1,outputImage.getWidth() - 3,
                             outputImage.getHeight() - 3);
    outputImage.drawRect(2,2,outputImage.getWidth() - 5,
                             outputImage.getHeight() - 5);

    //Draw the incoming image inside the solid border.
    outputImage.drawImage(image,5,5);
    return outputImage;
  }//end drawOutline

  /*******************************************************  
  The purpose of this method is to draw a picture on
  either or both sides of an incoming image.<p><p>
  
  The height of the picture may be smaller or larger 
  than the height of the text.  Vertical centering
  accommodates both cases reasonably well.<p><p>
  
  A space of one pixel is inserted between the
  picture and the incoming image.
  *******************************************************/
  private GreenfootImage makePictureImage(
    GreenfootImage image,//Draw picture(s) on this image.
    GreenfootImage picture,//Picture to draw.
    boolean left,//Left justify if true, right otherwise.
    boolean both){//Draw picture on both sides if true.
      
    //Declare and initialize working variables.
    int pictureWidth = picture.getWidth();
    int pictureHeight = picture.getHeight();
    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();
    int outputImageWidth = 0;
    int outputImageHeight = 0;
    
    //Space between the text and the picture
    final int pictureSpace = 1;

    GreenfootImage outputImage = null;//output image ref
    
    //Set the height of the output image to the maximum
    // height of the picture or the incoming image.
    if(pictureHeight >= imageHeight){
      outputImageHeight = pictureHeight;
    }else{
      outputImageHeight = imageHeight;
    }//end else
    
    //Set the width of the output image based on whether
    // the output image contains one or two pictures plus
    // the incoming image.
    if(!both){
      //Space for one picture required.
      outputImageWidth = 
                 imageWidth + pictureWidth + pictureSpace;
    }else{
      //Space for two pictures required.
      outputImageWidth = 
             imageWidth + 2*(pictureWidth + pictureSpace);
    }//end else
    
    //Create an empty transparent version of the output
    // image.
    outputImage = new GreenfootImage(
                      outputImageWidth,outputImageHeight);

    //Place the picture(s) and the incoming image in the
    // correct positions on the output image.
    if(both){
      //Center the incoming image between two pictures.
      // Must deal with vertical positioning of the
      // pictures and the incoming image depending on
      // which is taller.
      if(pictureHeight >= imageHeight){
        outputImage.drawImage(picture,0,0);
        outputImage.drawImage(image,
             pictureWidth + pictureSpace,pictureHeight/4);
        outputImage.drawImage(picture,
            pictureWidth + 2*pictureSpace + imageWidth,0);
      }else{
        outputImage.drawImage(picture,0,imageHeight/4);
        outputImage.drawImage(image,
                           pictureWidth + pictureSpace,0);
        outputImage.drawImage(picture,
               pictureWidth + 2*pictureSpace + imageWidth,
               imageHeight/4);
      }//end else
    }else{
      //Not both. Only one picture required. Place the
      // picture on the correct side of the incoming
      // image.
      if(left){
        //Left justify one picture dealing with relative
        // heights.
        if(pictureHeight >= imageHeight){
          outputImage.drawImage(picture,0,0);
          outputImage.drawImage(image,
             pictureWidth + pictureSpace,pictureHeight/4);
        }else{
          outputImage.drawImage(picture,0,imageHeight/4);
          outputImage.drawImage(image,
                           pictureWidth + pictureSpace,0);
        }//end else
      }else{
        //Right justify one picture dealing with relative
        // heights.
        if(pictureHeight >= imageHeight){
          outputImage.drawImage(
                     picture,imageWidth + pictureSpace,0);
          outputImage.drawImage(image,0,pictureHeight/4);
        }else{
          outputImage.drawImage(picture,
                 imageWidth + pictureSpace,imageHeight/4);
          outputImage.drawImage(image,0,0);
        }//end else
      }//end else
    }//end else not both

    //Delete the following statement when all testing is
    // complete. The purpose of this statement is to show
    // the outline of the image and the way in which its
    // size correlates with the size of the incoming image.
//    outputImage.drawRect(0,0,outputImageWidth - 1,
//                                 outputImageHeight - 1);
                                
    return outputImage;       
  }//end makePictureImage

  /*******************************************************  
  The purpose of this method is to draw the text on a
  transparent image that is barely large enough to
  contain the text in the specified font. Note that the
  specified font can either be the name of a general
  font family (such as SansSerif), or can be the name
  of a specific font (such as English157 BT).  I can't
  tell you for sure what will happen if the specified
  font is not available, but I believe that a system-
  dependent default font will be used instead.
  *******************************************************/
  //Example font family names are:
  //  Dialog
  //  SansSerif
  //  Serif
  //  Monospaced
  //  DialogInput
  //Example font names are:
  //  Symbol
  //  Tahoma
  //  Times New Roman
  //  Trebuchet MS
  private GreenfootImage makeTextImage(
    String text,//Text to draw on the output image
    String fontName,//Name of the font or font family
    int style,//BOLD, ITALIC, etc.
    int size,//Font size in points
    Color color){//Color in which to draw the text.
      
    //Create a new Font object matching the name, style,
    // and size of the specified font.
    Font font = new Font(fontName,style,size);
    
    //Make a trial run to get a FontRenderContext object
    // describing this text in this font.
    GreenfootImage image = new GreenfootImage(1,1);
    image.setFont(font);
    image.drawString(text,1,1);
    FontRenderContext frc = ((Graphics2D)image.
      getAwtImage().getGraphics()).getFontRenderContext();
    
    //Get a TextLayout object that can be used to get the
    // critical sizing metrics.
    TextLayout textLayout = new TextLayout(text,font,frc);
    //Get the critical sizing metrics and convert from
    // float to int in the process.
    int ascent = (int)(textLayout.getAscent() + 1);
    int descent = (int)(textLayout.getDescent() + 1);
    int advance = (int)(textLayout.getAdvance() + 1);
    
    //Create a new transparent image object with the
    // dimensions required to barely contain the text.
    // Instantiation of this object causes the trial
    // object to become eligible for garbage collection.
    image = new GreenfootImage(advance,ascent + descent);
    
    //Delete the following statement when all testing is
    // complete. The purpose of this statement is to show
    // the outline of the image and the way in which its
    // size correlates with the size of the text.
//    image.drawRect(0,0,advance - 1,ascent + descent - 1);
    
    //Draw the text on the transparent image in the
    // correct font and color.
    image.setFont(font);
    image.setColor(color);
    image.drawString(text,0,(int)(ascent));
    return image;
                   
  }//end makeTextImage
  //----------------------------------------------------//
}//end class Labels


