------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE: GUI components
PURPOSE OF PROJECT: Display and test GUI components.
VERSION or DATE: 1.0
HOW TO START THIS PROJECT: Press the run button.
AUTHORS: Ed Parrish
USER INSTRUCTIONS:
This project emulates the Java GUI components for Buttons, Labels and
TextFields. The component system includes:
- ActionListener style callback support for Buttons and TextFields
- a focus subsystem (important for components with text input)
- ability to change the look by setting sizes, colors and borders

To use the components, copy the GUIComponent superclass into your project along
with any components you want to use.

You can change the look of any component by changing sizes, fonts, foreground
and background colors and borders. The GUIWorld class has many examples of how
to use various features of the components.

This GUI collection grew out of my need for text components for my Greenfoot Jeopardy game (see http://greenfootgallery.org/scenarios/2370).

DESIGN NOTES:
The GUIComponent superclass contains the methods and properties common across
all components. This includes methods and properties to:
- manage ActionListener callbacks
- change focus automatically by clicking on a button or text field
- change the look by setting sizes, colors and borders
- paint the components

The paint system emulates the Java Swing system and painting is broken into
three paint methods:
- paintComponent(Graphics g)
- paintBorder(Graphics g)
- paintText(Graphics g) // paintChildren() in Java Swing

To initiate a repaint of a component, call the repaint() method.

You can override any of the methods, such as paintComponent(), in a subclass.
To maintain functionality call super.methodName() from the overridden method.
For instance:

super.paintComponent(g);

The GUIComponent file also houses the ActionListener interface, Border
interface and a LineBorder class. The reason these are included in the
GUIComponent file, rather than residing in separate files, is to make setting
up projects easier with fewer files to copy and arrange. However, the downside
is the lack of Javadoc documentation inside Greenfoot. To overcome this, I
supplied the Javadoc in a subfolder named, "Javadoc". Also, you can read the
source code to see the documentation for these interfaces and class.

This version includes a LineBorder implementation. The LineBorder class lets
you specify line thickness and color for any component. You can change the
border in other ways by writing you won border class. Your border class must
implement the Border interface.
