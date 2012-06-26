import java.awt.*;
import java.awt.geom.*;

/**
   An invisible node that is used in the toolbar to draw an
   edge.
*/
public class PointNode extends AbstractNode
{
   /**
      Constructs a point node with coordinates (0, 0)
   */
   public PointNode()
   {
      point = new Point2D.Double();
   }
/**
 *Draws the point node
 */
   public void draw(Graphics2D g2)
   {
   }

/**
 *Allows for the moving of a point node
 *@param double dx, the change in the x position
 *@param double dy, the change in the y position
 */
   public void translate(double dx, double dy)
   {
      point.setLocation(point.getX() + dx,
         point.getY() + dy);
   }

   /** Returns if point p is contained within this node.
   @param p the point to be tested
   @return false always, since it has no size.
     */
   public boolean contains(Point2D p)
   {
      return false;
   }

/**
 *Gets the Boundaries of the Rectangle
 *@return Rectangle2D, the rectangle to be gotten
 */
   public Rectangle2D getBounds()
   {
      return new Rectangle2D.Double(point.getX(),
         point.getY(), 0, 0);
   }
/**
 *Gets the best connected point between this Point2D and other Point2D.
 *@param Point2D other, the point this point is trying to connect to
 *@return Point2D, the best connected point
 */
   public Point2D getConnectionPoint(Point2D other)
   {
      return point;
   }



   private Point2D point; //a Point on a graph
}
