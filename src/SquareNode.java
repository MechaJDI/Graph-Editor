
/**
   A square node that is filled with a color.
*/
import java.awt.*;
import java.awt.geom.*;

public class SquareNode extends AbstractNode {

        /**
      Construct a square node with a given size and color.
      @param aColor the fill color
   */
        public SquareNode( Color aColor )
		{
			setSize( getDefaultSize() );
			setColor( aColor );
			setLabel( getDefaultLabel() );
		}
        
        /**
      Draws a square node.
      @param g The Graphics2D object that does the drawing
	*/
        public void draw( Graphics2D g )
		{
			Rectangle2D square = new Rectangle2D.Double(
				getX(), getY(), getSize(), getSize());
			Color oldColor = g.getColor();
			g.setColor(getColor());
			g.fill(square);
			g.setColor(oldColor);
			g.draw(square);
			g.drawString( getLabel(), (float)(getX() + getSize() / 4), (float)(getY() + getSize() / 2) );
		}

        /** Tests whether the square node contains a point
                @param p the point to test
                @return true if this square node contains p
        */
        public boolean contains( Point2D p )
		{
			Rectangle2D rect = new Rectangle2D.Double(
				getX(), getY(), getSize(), getSize());
			return rect.contains(p);
		} 

        /**
      Get the best connection point to connect this node with another node. This should be a point on the boundary of the shape of this node.
      @param other an exterior point that is to be joined with this node
      @return the recommended connection point
   */
        public Point2D getConnectionPoint( Point2D other )
        { 
			Point2D answer;
			double centerX = getX() + getSize() / 2;
			double centerY = getY() + getSize() / 2;
			double dx = other.getX() - getX();
			double dy = other.getY() - getY();
			double slope = dy / dx;
			
			if ( dx == 0)
				if ( dy > 0 )
					answer = new Point2D.Double( other.getX(), getY());
				else
					answer = new Point2D.Double( other.getX(), getY() + getSize());
			
			else if ( dy > centerY && Math.abs(slope) > 1 )
				answer = new Point2D.Double( centerX + ( 1 / slope ) * ( getSize() / 2), centerY + getSize() / 2);
			else if ( dy < centerY && Math.abs(slope) > 1 )
				if ( other.getY() > centerY )
					answer = new Point2D.Double( centerX + ( 1 / slope ) * ( getSize() / 2), centerY + getSize() / 2);
				else
					answer = new Point2D.Double( centerX - ( 1 / slope ) * ( getSize() / 2), centerY - getSize() / 2);
			else if ( Math.abs(slope) < 1 && dx > 0 )
				answer = new Point2D.Double( centerX + getSize() / 2, centerY + slope * ( getSize() / 2) );
			else
				answer = new Point2D.Double( centerX - getSize() / 2, centerY - slope * ( getSize() / 2) );
			
			return answer;	
		}
}
