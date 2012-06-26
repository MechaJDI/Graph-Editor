import java.awt.*;
import java.awt.geom.*;

/**
   A class that supplies convenience implementations for
   a number of methods in the Edge interface type.
*/
public abstract class AbstractEdge implements Edge
{
   /**
    *Creates a copy of the given edge
    *@return a copied edge of type object\
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

   /**
    *Connects two nodes together via an edge
    *@param Node s, e, The nodes to be connected via an edge
    */
   public void connect(Node s, Node e)
   {
      start = s;
      end = e;
   }

   //Accessors And Mutators
   public Node getStart()
   {
      return start;
   }

   public Node getEnd()
   {
      return end;
   }

   public void setStart( Node n )
   {
      start = n;
   }

   public void setEnd( Node n )
   {
      end = n;
   }

/** Gets the rectangular bounds of this edge.
@param g2 the graphics object containing this egde.
@return the Rectangle2D bounds of this edge. 
*/
   public Rectangle2D getBounds(Graphics2D g2)
   {
      Line2D conn = getConnectionPoints();
      Rectangle2D r = new Rectangle2D.Double();
      r.setFrameFromDiagonal(conn.getX1(), conn.getY1(),
         conn.getX2(), conn.getY2());
      return r;
   }

   /**
    *Finds the best connection points between two nodes and draws an edge between them from that location.
    *@return Line2D, the line connecting those points
    */

   public Line2D getConnectionPoints()
   {
      Rectangle2D startBounds = start.getBounds();
      Rectangle2D endBounds = end.getBounds();
      Point2D startCenter = new Point2D.Double(
         startBounds.getCenterX(), startBounds.getCenterY());
      Point2D endCenter = new Point2D.Double(
         endBounds.getCenterX(), endBounds.getCenterY());
      return new Line2D.Double(
         start.getConnectionPoint(endCenter),
         end.getConnectionPoint(startCenter));
   }

   /**
    *Tells if this edge is equal to edge e by their connecting nodes.
    *@param Edge e, the edge this edge is being compared to.
    *@return boolean, true if equal; false if not.
    */
   public boolean equals(Object e)
   {
		boolean isEqual = false;
		if ( start.equals( ((Edge)e).getStart() ) && end.equals( ((Edge)e).getEnd() ) )
			isEqual = true;
		if ( start.equals( ((Edge)e).getEnd() ) && end.equals( ((Edge)e).getStart() ) )
			isEqual = true;
		return isEqual;
	}
   private Node start;
   private Node end;
}
