
/**
   A note node that has a yellow background and text.
*/
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;

public class NoteNode extends AbstractNode {
		private static final int DEFAULT_WIDTH = 30;
		private static final int PADDING = 10;
		private static final Color DEFAULT_COLOR = new Color(225, 225, 150);
		private int width;  // The AbstractNode field size is used for the height, 
							// since the width changes when the label increases.
        /**
      Construct a square node with a given label.
      @param aColor the fill color
   */
        public NoteNode( String s )
		{
			setWidth(DEFAULT_WIDTH);
			setSize( getDefaultSize() );
			setColor( DEFAULT_COLOR );
			setLabel( "" );
		}
        
		public void setWidth( int w ) {
			width = w;
		}
		
		public int getWidth( ) {
			return width;
		}
		
		
		public void fitWidth(Graphics2D g) {
			Font font = g.getFont();
			FontRenderContext context = g.getFontRenderContext();
			Rectangle2D bounds = font.getStringBounds(getLabel(), context);
			double extent = bounds.getWidth();
			double ascent = bounds.getHeight();
			setWidth(new Double(extent).intValue() + PADDING);
			setSize(new Double(ascent).intValue() + PADDING / 2);
		}
		
        /**
      Draws a note node.
      @param g The Graphics2D object that does the drawing
	*/
        public void draw( Graphics2D g )
		{
			Font oldFont = g.getFont();
			g.setFont(getFont());
			if (getLabel() != "" ) fitWidth(g);
			Rectangle2D rect = new Rectangle2D.Double(
				getX(), getY(), width, getSize());
			Color oldColor = g.getColor();
			g.setColor(DEFAULT_COLOR);
			g.fill(rect);
			g.setColor(oldColor);
			g.draw(rect);
			g.drawString( getLabel(), (float)(getX() + PADDING / 2  ), (float)(getY() + getSize() / 2 + PADDING / 2) );
			g.setFont(oldFont);
		}

        /** Tests whether the note node contains a point
                @param p the point to test
                @return true if this note node contains p
        */
        public boolean contains( Point2D p )
		{
			Rectangle2D rect = new Rectangle2D.Double(
				getX(), getY(), width, getSize());
			return rect.contains(p);
		} 
		
		public Rectangle2D getBounds()
		{
			return new Rectangle2D.Double(
            getX(), getY(), width, getSize());
		}

        /**
      Get the best connection point to connect this node with another node. This should be a point on the boundary of the shape of this node.
      @param other an exterior point that is to be joined with this node
      @return the recommended connection point
   */
        public Point2D getConnectionPoint( Point2D other )
        { 
			return other;	
		}
}
