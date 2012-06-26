import java.awt.*;
import java.awt.geom.*;

/**
   An edge that is shaped like a straight line.
*/
public class LineEdge extends AbstractEdge
{
   private LineStyle lineStyle;
   private boolean isDirected;
   private double weight;
   private ArrowHead arrow;
   private static final double DEFAULT_WEIGHT = 0;
   private static final boolean DEFAULT_DIRECTED = false;
   private Color color;
   
   // Constructors
   
    /** Constructs a solid line from one node to another node
        */
   public LineEdge()
   {
      lineStyle = LineStyle.SOLID;
      weight = DEFAULT_WEIGHT;
      isDirected = DEFAULT_DIRECTED;
      arrow = ArrowHead.NONE; 
	  color = Color.BLACK;
   }
   
    /** Constructs a solid line from one node to another node, with a supplied direction
		@param boolean isItDirected whether or not the LineEdge is directed. 
        */
   public LineEdge(boolean isItDirected)
   {
      lineStyle = LineStyle.SOLID;
      weight = DEFAULT_WEIGHT;
      isDirected = isItDirected;
      arrow = ArrowHead.NONE; 
	  color = Color.BLACK;
   }

 /**
      Sets the line style property.
      @param newValue the new value
   */
   public void setLineStyle(LineStyle newValue) 
   { 
		lineStyle = newValue; 
   }

   /**
      Gets the line style property.
      @return the line style
   */
	public LineStyle getLineStyle() 
	{ 
		return lineStyle; 
	}
   
	/** Sets the color of this LineEdge.
	*/
	public void setColor(Color c) {
		color = c;
	}
	
	/** Gets the current color of this LineEdge.
	*/
	public Color getColor() {
		return color;
	}


   /**
      Gets whether or not this LineEdge is directed
      @return whether the LineEdge is directed
   */
   public boolean getDirected() {
		return isDirected;
   }

   /**
      Change whether LineEdge is directed.
      @param newDirection the new value of isDirected
   */
   public void setDirected( boolean newDirection )
	{
		isDirected = newDirection;
	}

	/**
      Reverses the direction of this LineEdge
   */
	public void swapDirection()
	{
		if (isDirected) {
			Node s = getStart();
			setStart( getEnd() );
			setEnd( s );		
		}
	}
	/**
      Get the weight of a line edge.
      @return the weight of a line edge
   */
   public double getWeight() 
   {
   		return weight;
   }
   /**
      Change the weight of a line edge.
      @param aWeight the weight of a line edge
   */
   public void setWeight( double newWeight )
   {
   		weight = newWeight;
   }

	/** Draws the line edge
		@param g the Graphics2D object that draws this LineEdge
    */
   public void draw(Graphics2D g2)
   {
		Color oldColor = g2.getColor();
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(lineStyle.getStroke());
		g2.setColor(color);
		g2.draw(getConnectionPoints());
		g2.setStroke(oldStroke);
		if (weight != 0) {
			g2.drawString(Double.toString(weight), new Double(getConnectionPoints().getBounds().getCenterX()).intValue(), 
						new Double(getConnectionPoints().getBounds().getCenterY()).intValue());
		}
		
		if (isDirected) {
			arrow = ArrowHead.V;			
			double theta = 
						Math.atan2( (getEnd().getY() + getEnd().getSize() / 2) - (getStart().getY() + getStart().getSize() / 2), 
						(getEnd().getX() + getEnd().getSize() / 2) - (getStart().getX() + getStart().getSize() / 2) );
			double dx = (getEnd().getSize() / 2)*Math.cos( theta );
			double dy = (getEnd().getSize() / 2)*Math.sin( theta );
			Point2D.Double p = new Point2D.Double( getStart().getX() + getStart().getSize()/2, getStart().getY() + getStart().getSize()/2 );
			Point2D.Double q = (Point2D.Double)getEnd().getConnectionPoint(p);
			p = (Point2D.Double)getStart().getConnectionPoint(q);
			arrow.draw(g2, p, q);
		}
		g2.setColor(oldColor);
	}

	/** Tests whether the line edge contains a point
                @param p the point to test
                @return true if this line edge contains p
    */
   public boolean contains(Point2D aPoint)
   {
		final double MAX_DIST = 2;
		return getConnectionPoints().ptSegDist(aPoint) < MAX_DIST;
   }   
   
}
