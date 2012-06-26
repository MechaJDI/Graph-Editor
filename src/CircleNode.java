import java.awt.*;
import java.awt.geom.*;

/**
   A circular node that is filled with a color.
*/
public class CircleNode extends AbstractNode //implements Node
{
   /**
      Construct a circle node with a given size and color.
      @param aColor the fill color
   */
   public CircleNode(Color aColor)
   {
      setSize( getDefaultSize() );
      setTheX( 0 );
      setTheY( 0 );
      setColor( aColor );
	  setLabel( getDefaultLabel() );
   }

   /** Draws the node to the graph.
   @param g2 the graphics object to which the circle node is drawn.
   */
   public void draw(Graphics2D g2)
   {
      Ellipse2D circle = new Ellipse2D.Double(
            getX(), getY(), getSize(), getSize());
      Color oldColor = g2.getColor();
      g2.setColor(getColor());
      g2.fill(circle);
      g2.setColor(oldColor);
      g2.draw(circle);
	  Font oldFont = g2.getFont();
	  g2.setFont(getFont());
	  g2.drawString( getLabel(), (float)(getX() + getSize() / 4), (float)(getY() + getSize() / 2) );
	  g2.setFont(oldFont);
  }

	/** Tests if this CircleNode contains a point.
	@param p the other point
	@return whether p is inside this CircleNode
	*/
   public boolean contains(Point2D p)
   {
      Ellipse2D circle = new Ellipse2D.Double(
            getX(), getY(), getSize(), getSize());
      return circle.contains(p);
   }
/**
      Get the best connection point to connect this node with another node. This should be a point on the boundary of the shape of this node.
      @param other an exterior point that is to be joined with this node
      @return the recommended connection point
   */
   public Point2D getConnectionPoint(Point2D other)
   {
      double centerX = getX() + getSize() / 2;
      double centerY = getY() + getSize() / 2;
      double dx = other.getX() - centerX;
      double dy = other.getY() - centerY;
      double distance = Math.sqrt(dx * dx + dy * dy);
      if (distance == 0) return other;
      else return new Point2D.Double(
            centerX + dx * (getSize() / 2) / distance,
            centerY + dy * (getSize() / 2) / distance);
   }
}
