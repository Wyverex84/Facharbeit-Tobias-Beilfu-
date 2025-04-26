PROJECT TITLE:             3-D Space Flight Simulation
PURPOSE OF PROJECT:        A nice 3-D space flight framework in Greenfoot
VERSION or DATE:           Dec 2008
HOW TO START THIS PROJECT: Run
AUTHORS:                   Michael Lastufka

USER INSTRUCTIONS

Two modes of 0-G RCS (Reaction Control System) movement, linear and rotation.
<enter> toggles the RCS movement mode.
Use the arrow keys and "," and "." for the 
6 directions as indicated on the initial info panel.
Right-click in space to activate mouse directed navigation.

Forward thrust: + or =, Retro thrust: -.

Click the rocks to get generic position data.
The scale factor turns to an offscreen code
telling how the object left the display.
-1 behind display
-2 outside field of view
-3 hit the windshield

To introduce new objects, subclass SpaceObject as I did for rocks.
They display flat - there's no approach aspecting 
(no object depth, only scene depth).
This is a consession to Greenfoot's 2-D nature - so far.
I made them tumble so it is less noticeable!

Haven't tried to pan the backgroud yet.

I was concerned that true 3-D would not be able to take advantage of
the Greenfoot framework. So far, there seems to be enough Greenfoot
functionallity to take advantage of without forcing the 3-D aspects.
The implementation is much cleaner than I thought it would be.

=========

3-D Space Flight

This is a demo project providing a 3-D cockpit-view "framework" that can still utilize most of Greenfoot's benefits for expansion.

Greenfoot is inherently a 2-D game framework. I wanted to see if it could support true 3-D and not lose its advantages like easy collision checking and synchronized "acts". So far, implementing 3-D movement is fairly clean!

Note: the SpaceObjects are flat - no 3-D approach aspecting (or whatever it's called these days). That is probably OK for many games that could be made. Here I rotate the rocks so it isn't so obvious. The starry background doesn't move with rotational movement. It shouldn't move for linear motion - in space the background is essentially at infinite distance. For warping, you'd have to create a Star class and elongate them in perspective as they swoosh by.
