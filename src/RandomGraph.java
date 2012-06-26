import java.awt.*;
import java.util.*;
import java.awt.geom.*;

/**
 *Creates a graph with a random number of edges with respect to the number of nodes the user gives.
 */
public class RandomGraph extends SimpleGraph
{
	private int diameter;      //Specifies how large the invisible circle the nodes lie on is.
/**
 *Creates a new RandomGraph
 *@param int d, The diameter of the graph
 *@param int n, The number of nodes for the graph
 *@param isMoreRandom whether or not the graph randomizes colors and sizes.
 */
	public RandomGraph(int d, int n, boolean isMoreRandom)
	{
		Random random = new Random();
		diameter = d;
		int r = diameter/2;
		double x = r;
		double y = r;
		double theta = 2 * Math.PI / n;
		clearAll();
		while (getNodes().size() < n ) {
			CircleNode c = new CircleNode( Color.WHITE );
			if ( isMoreRandom ) {
				c.setColor( new Color( random.nextInt(256), random.nextInt(256),random.nextInt(256) ) );
			}
			x = r*Math.sin(-1*(getNodes().size())*theta + Math.PI);
			y = r*Math.cos(-1*(getNodes().size())*theta + Math.PI);
			if (isMoreRandom) c.setSize(15 + random.nextInt(25));
			add(c, new Point2D.Double(x + r, y + r));
			c.sendIndex( getNodes().size() - 1 );
		}


		randomize(isMoreRandom);
	}
/**
 *Sets and places the number of edges in the RandomGraph
 @param isMoreRandom whether or not the graph randomizes colors and sizes.
 */
	public void randomize(boolean isMoreRandom)
	{
		Random random = new Random();
		int n = getNodes().size();
		int edges = random.nextInt(n * ( n-1 ) / 2 );
		int startNdx = 0;
		int endNdx = 0;
		while ( getEdges().size() < edges ) {
			LineEdge e = new LineEdge(false);
			startNdx = random.nextInt( n );
			endNdx = random.nextInt( n );
			while (endNdx == startNdx)
				endNdx = random.nextInt( n );


			e.setStart(getNodes().get(startNdx));
		 	e.setEnd(getNodes().get(endNdx) );
			if ( isMoreRandom ) e.setColor( new Color( random.nextInt(256), random.nextInt(256),random.nextInt(256) ) );
			addEdge(e);
		}

	}

/**
 *Gets the array of the 3 default nodes
 *@return A Node[] that contains the 3 default nodes
 */
	public Node[] getNodePrototypes()
   {
      Node[] nodeTypes =
         {
            new CircleNode(Color.BLACK),
            new CircleNode(Color.WHITE),
			new SquareNode(Color.LIGHT_GRAY)
         };
      return nodeTypes;
   }

/**
 *Gets the array of the 2 default edges
 *@return An Edge[] that contains the 2 default edges
 */
   public Edge[] getEdgePrototypes()
   {
      Edge[] edgeTypes =
         {
            new LineEdge(),
			new LineEdge( true )
         };
      return edgeTypes;
   }

}
