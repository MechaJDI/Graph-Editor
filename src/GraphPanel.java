import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.lang.reflect.*;


/**
   A panel to draw a graph
*/
public class GraphPanel extends JComponent
{
   /**
      Constructs a graph.
      @param aToolBar the tool bar with the node and edge tools
      @param aGraph the graph to be displayed and edited
   */
   public GraphPanel(ToolBar aToolBar, Graph aGraph)
   {
      toolBar = aToolBar;
      graph = aGraph;
      setBackground(Color.WHITE);

      addMouseListener(new
         MouseAdapter()
         {
            public void mousePressed(MouseEvent event)
            {
               Point2D mousePoint = event.getPoint();
               Node n = graph.findNode(mousePoint);
               Edge e = graph.findEdge(mousePoint);
               Object tool = toolBar.getSelectedTool();
               if (tool == null) // select
               {
                  if (e != null)
                  {
                     selected = e;
                  }
                  else if (n != null)
                  {
                     selected = n;
                     dragStartPoint = mousePoint;
                     dragStartBounds = n.getBounds();
                  }
                  else
                  {
                     selected = null;
                  }
               }
               else if (tool instanceof Node)
               {
                  Node prototype = (Node) tool;
                  Node newNode = (Node) prototype.clone();
                  boolean added = graph.add(newNode, mousePoint);

                  try {
         			if (newNode.getClass().getMethod("setLabel", new Class[] {String.class} ) != null) {
         				Method m = newNode.getClass().getMethod("setLabel", new Class[] {String.class} );
     	    			Method me = newNode.getClass().getMethod("getDefaultLabel");
     	    			try {
    	     				String s = (String)me.invoke(newNode, new Object[] {});
   		      				m.invoke(newNode, new Object[] {s});
     	    			}
         				catch (IllegalAccessException exception)
            		    {
            		       exception.printStackTrace();
             		    }
              		    catch (InvocationTargetException exception)
              		    {
                	   	  exception.printStackTrace();
                	    }
      	 			}
         		}
         		catch (NoSuchMethodException exception) {
         			exception.printStackTrace();
         		}

                  if (added)
                  {
					 if (!newNode.getClass().equals(new NoteNode("").getClass()))
						newNode.sendIndex( graph.getNodes().size() - 1 );
                     selected = newNode;
                     dragStartPoint = mousePoint;
                     dragStartBounds = newNode.getBounds();
                  }
                  else if (n != null)
                  {
                     selected = n;
                     dragStartPoint = mousePoint;
                     dragStartBounds = n.getBounds();
                  }
               }
               else if (tool instanceof Edge)
               {
                  if (n != null) rubberBandStart = mousePoint;
               }
               lastMousePoint = mousePoint;
               repaint();
            }

            public void mouseReleased(MouseEvent event)
            {
               Object tool = toolBar.getSelectedTool();
               if (rubberBandStart != null)
               {
                  Point2D mousePoint = event.getPoint();
                  Edge prototype = (Edge) tool;
                  Edge newEdge = (Edge) prototype.clone();
                  if (graph.connect(newEdge,
                         rubberBandStart, mousePoint))
                     selected = newEdge;
               }

               revalidate();
               repaint();

               lastMousePoint = null;
               dragStartBounds = null;
               rubberBandStart = null;
            }
         });

      addMouseMotionListener(new
         MouseMotionAdapter()
         {
            public void mouseDragged(MouseEvent event)
            {
               Point2D mousePoint = event.getPoint();
               if (dragStartBounds != null)
               {
                  if (selected instanceof Node)
                  {
                     Node n = (Node) selected;
                     Rectangle2D bounds = n.getBounds();
                     n.translate(
                        dragStartBounds.getX() - bounds.getX()
                        + mousePoint.getX() - dragStartPoint.getX(),
                        dragStartBounds.getY() - bounds.getY()
                        + mousePoint.getY() - dragStartPoint.getY());
                  }
               }
               lastMousePoint = mousePoint;
               repaint();
            }
         });
   }
/** Paints the graph to the panel.
@paraam g the graphics object on which the graph will be drawn.
*/
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      Rectangle2D bounds = getBounds();
      Rectangle2D graphBounds = graph.getBounds(g2);
      graph.draw(g2);

      if (selected instanceof Node)
      {
         Rectangle2D grabberBounds = ((Node) selected).getBounds();
         drawGrabber(g2, grabberBounds.getMinX(), grabberBounds.getMinY());
         drawGrabber(g2, grabberBounds.getMinX(), grabberBounds.getMaxY());
         drawGrabber(g2, grabberBounds.getMaxX(), grabberBounds.getMinY());
         drawGrabber(g2, grabberBounds.getMaxX(), grabberBounds.getMaxY());
      }

      if (selected instanceof Edge)
      {
         Line2D line = ((Edge) selected).getConnectionPoints();
         drawGrabber(g2, line.getX1(), line.getY1());
         drawGrabber(g2, line.getX2(), line.getY2());
      }

      if (rubberBandStart != null)
      {
         Color oldColor = g2.getColor();
         g2.setColor(PURPLE);
         g2.draw(new Line2D.Double(rubberBandStart, lastMousePoint));
         g2.setColor(oldColor);
      }
   }

   /**
      Removes the selected node or edge.
   */
   public void removeSelected()
   {
      if (selected instanceof Node)
      {
         graph.removeNode((Node) selected);
      }
      else if (selected instanceof Edge)
      {
         graph.removeEdge((Edge) selected);
      }
      selected = null;
      repaint();
   }

   /**
    Reverses the all Edges
   */

   public void reverseEdges()
   {
      graph.reverse();
      selected = null;
      repaint();
   }

   /**
    Takes the complement of the current graph.
   */

   public void complement()
   {
      graph.complement();
      selected = null;
      repaint();
   }

   /**
    Creates a new random graph.
	@param isMR whether or not this RandomGraph randomizes colors and sizes.
   */

   public void random(boolean isMR)
   {
   	int d = 0;
	int n = 0;
	try {
		d = Integer.parseInt( JOptionPane.showInputDialog("Enter diameter: ") );
    }
	catch (NumberFormatException e) {
	}
	try {
		n = Integer.parseInt( JOptionPane.showInputDialog("Enter number of nodes: ") );
    }
	catch (NumberFormatException e) {
	}
	
	if ( d > 0 && n > 0 )
		graph = new RandomGraph(d, n, isMR);
    selected = null;
    repaint();
   }

   /**
    Creates a new complete graph.
   */

   public void complete()
   {
   	int d = 0;
	int n = 0;
	try {
		d = Integer.parseInt( JOptionPane.showInputDialog("Enter diameter: ") );
    }
	catch (NumberFormatException e) {
	}
	try {
		n = Integer.parseInt( JOptionPane.showInputDialog("Enter number of nodes: ") );
    }
	catch (NumberFormatException e) {
	}
	
	if ( d > 0 && n > 0 )
		graph = new CompleteGraph(d, n);
    selected = null;
    repaint();
   }
  /**
   *Displays the Help Frame
   */

   public void showHelp() {
  	 javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HelpFrame();
            }
        });
   }

   /**
      Edits the properties of the selected graph element.
   */
   public void editSelected()
   {
   	if (selected != null) {
      PropertySheet sheet = new PropertySheet(selected);
      sheet.addChangeListener(new
         ChangeListener()
         {
            public void stateChanged(ChangeEvent event)
            {
               repaint();
            }
         });
      JOptionPane.showMessageDialog(null,
         sheet,
         "Properties",
         JOptionPane.QUESTION_MESSAGE);
   	}
   }

   /**
      Draws a single "grabber", a filled square
      @param g2 the graphics context
      @param x the x coordinate of the center of the grabber
      @param y the y coordinate of the center of the grabber
   */
   public static void drawGrabber(Graphics2D g2, double x, double y)
   {
      final int SIZE = 5;
      Color oldColor = g2.getColor();
      g2.setColor(PURPLE);
      g2.fill(new Rectangle2D.Double(x - SIZE / 2,
         y - SIZE / 2, SIZE, SIZE));
      g2.setColor(oldColor);
   }

   /** Returns the prefered size of the graph.
   @returns the Dimension object representing the bounds of the graph.
   */
   public Dimension getPreferredSize()
   {
      Rectangle2D bounds
         = graph.getBounds((Graphics2D) getGraphics());
      return new Dimension(
         (int) bounds.getMaxX(),
         (int) bounds.getMaxY());
   }
   /**
   	Clears all nodes and edges in this panel's graph.
   */
   public void clearAll() {
   		graph.clearAll();
   		selected = null;
   		repaint();
   }

   private Graph graph;
   private ToolBar toolBar;
   private Point2D lastMousePoint;
   private Point2D rubberBandStart;
   private Point2D dragStartPoint;
   private Rectangle2D dragStartBounds;
   private Object selected;
   private static final Color PURPLE = new Color(0.7f, 0.4f, 0.7f);
}
