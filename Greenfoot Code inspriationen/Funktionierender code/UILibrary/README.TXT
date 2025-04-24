MSG (Michael's Simple GUI) Toolkit

This scenario provides some reusable GUI components. To use any of these components in your own
scenario, copy the class to your scenario.

If the component uses custom images (such as the switch) then the used images must also be copied. A
component's class comment will state if the component relies on image files. Some components (such
as Button or Checkbox) do not use image files, but draw the images directly.

For components that have an associated action (such as Button), the associated listener interface
(in this case: ButtonListener) must also be copied, and the world subclass must implement that 
interface.

License: Classes in this scenario are put into the public domain. Feel free to reuse and modify.
Author: Michael Kölling
Version: 1.2