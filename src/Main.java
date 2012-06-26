import javax.swing.*;
import java.beans.*;
import java.awt.*;

/**
   A program for editing UML diagrams.
*/
//public class SimpleGraphEditor
public class Main
{
   public static void main(String[] args)
   {
	  boolean isMoreRandom = false;
	  if ( args.length > 0  && args[0].equals("y") ) isMoreRandom = true;
      JFrame frame = new GraphFrame(new SimpleGraph(), isMoreRandom);
      PropertyEditorManager.registerEditor( Font.class, FontEditor.class ); 
	  PropertyEditorManager.registerEditor( Color.class, ColorEditor.class ); 
	  frame.setVisible(true);
   }
}
/* Lookback 5

*/