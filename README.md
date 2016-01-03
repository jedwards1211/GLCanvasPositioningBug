This demonstrates a bug with JOGL's AWT GLCanvas.

**Note**: this has only been tested on Mac OS X 10.11.1, on a MacBook Pro (15-inch, Late 2011)

This is an Eclipse Maven project.  When run, it first shows a JFrame that was setVisible(true) on
the main thread.  The graphics drawn by JOGL remain properly aligned with the GLCanvas, even when
it changes position.

After 7 seconds, it shows a second JFrame by calling its setVisible(true) on the AWT Event Dispatch 
Thread.  After that, the graphics drawn by JOGL aren't aligned with the GLCanvases when they 
change position.
