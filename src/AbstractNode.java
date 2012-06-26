import java.awt.*;
import java.awt.geom.*;

/**
 * A class that supplies convenience implementations for a number of methods in the Node interface type.
 */

public abstract class AbstractNode implements Node
{

	private int x;
	private int y;
	private double size;
	private int index;
	private String label;
	private Color color;
	private Font font;
	private static final double DEFAULT_SIZE = 20;
	private static final String DEFAULT_LABEL = "";
	private static final String[] ALPHABET = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
											   "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };


	/**
	 *Creates a clone of the Node
	 */
	public Object clone()
	{
      try
      {
       	return super.clone();
      }
      catch (CloneNotSupportedException exception)
      {
         return null;
      }
   }
   /*
	Accessors and mutators
   */
   public double getDefaultSize()
	{
		return DEFAULT_SIZE;
	}

	public String getDefaultLabel()
	{
		String defLabel = "";
		return defLabel;
	}

	public double getSize()
	{
		return size;
	}

	public void setSize( double newSize )
	{
		size = newSize;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setTheX( int newX)
	{
		x = newX;
	}

	public void setTheY( int newY )
	{
		y = newY;
	}

	public int getIndex()
	{
		return index;
	}

	public void sendIndex( int ndx )
	{
		index = ndx;
		if (ndx < 26) label = ALPHABET[ index ];
	}


	public String getLabel()
	{
		return label;
	}

	public void setLabel( String label )
	{
		this.label = label;
	}
	
	public Font getFont()
	{
		return font;
	}

	public void setFont(Font font )
	{
		this.font = font;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor( Color c )
	{
		color = c;
	}

	/** Gets the boundaries of the node
        @return The rectangle area that is the boundary
    */

	public Rectangle2D getBounds()
	{
      return new Rectangle2D.Double(
            getX(), getY(), getSize(), getSize());
	}

	/**
	 *Moves the node across the graph panel
	 *@param double dx, the change in the x direction
	 *@param double dy, the change in the y direction
	 */

	public void translate( double dx, double dy )
	{
		x+=dx;
		y+=dy;
	}
	/**
	 *Tells if this node is equal to node right using various properties of the node
	 *@param Node right, the node this node is being compared to
	 *@return boolean, true if equal, false if not.
	 */

	public boolean equals( Object right )
	{
		boolean isEqual = true;
		if ( x != ((Node)right).getX() || y != ((Node)right).getY() )
			isEqual = false;
		return isEqual;
	}

}
