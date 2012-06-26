import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
    A graph in which all nodes make every connection to all other nodes with undirected edges
*/

//completely new graph
public class CompleteGraph extends SimpleGraph
{
    private int diameter;    // The diameter of the invisible circle on which the nodes lie

    /**
        Constructor, adds the number of nodes to the graph on the circle and calls connectAll()
     * @param d diameter of circle to place nodes
     * @param  n numbers of nodes to place
    */
    public CompleteGraph(int d, int n)
    {
		diameter = d;
		int r = diameter/2;
		double x = r;
		double y = r;
		double theta = 2 * Math.PI / n;
		clearAll();
		while (getNodes().size() < n ) {
			CircleNode c = new CircleNode(Color.WHITE);
			x = r*Math.sin(-1*(getNodes().size())*theta + Math.PI);
			y = r*Math.cos(-1*(getNodes().size())*theta + Math.PI);
			add(c, new Point2D.Double(x + r, y + r));
			c.sendIndex( getNodes().size() - 1 );
		}
		connectAll();

    }

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

   public Edge[] getEdgePrototypes()
   {
      Edge[] edgeTypes =
         {
            new LineEdge(),
			new LineEdge( true )
         };
      return edgeTypes;
   }

    /**
        Connects all the points together in a graph with undirected edges
    */
    private void connectAll()
    {
		int i = 0;
		int j = 1;
		while (i < getNodes().size()) {
			while (j < getNodes().size()) {
				LineEdge l = new LineEdge(false);
				l.setStart(getNodes().get(i));
				l.setEnd(getNodes().get(j));
				addEdge(l);
				j++;
			}
			i++; j = i+1;
		}

    }

}
