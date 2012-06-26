import java.awt.*;
import java.awt.geom.*;

/**
   A circular node that is filled with a color.
*/
public class CircularNode extends AbstractNode //implements Node
{
   /**
      Construct a circle node with a given size and color.
      @param aColor the fill color
   */
   public CircularNode(Color aColor)
   {
      setSize( getDefaultSize() );
      setTheX( 0 );
      setTheY( 0 );
      setColor( aColor );
	  setLabel( getDefaultLabel() );
   }

   public void draw(Graphics2D g2)
   {
      Ellipse2D circle = new Ellipse2D.Double(
            getX(), getY(), getSize(), getSize());
      Color oldColor = g2.getColor();
      g2.setColor(getColor());
      g2.fill(circle);
      g2.setColor(oldColor);
      g2.draw(circle);
     // if ( getLabel() != getDefaultLabel( getIndex() ) ) setLabel(getDefaultLabel( getIndex() ));
	  g2.drawString( getLabel(), (float)(getX() + getSize() / 4), (float)(getY() + getSize() / 2) );
   }


   public boolean contains(Point2D p)
   {
      Ellipse2D circle = new Ellipse2D.Double(
            getX(), getY(), getSize(), getSize());
      return circle.contains(p);
   }

   public Rectangle2D getBounds()
   {
      return new Rectangle2D.Double(
            getX(), getY(), getSize(), getSize());
   }

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
