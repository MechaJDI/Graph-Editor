import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
   A graph consisting of selectable nodes and edges.
*/
public abstract class Graph implements Serializable
{
   /**
      Constructs a graph with no nodes or edges.
   */
   public Graph()
   {
      nodes = new ArrayList<Node>();
      edges = new ArrayList<Edge>();
   }

   /**
      Adds an edge to the graph that joins the nodes containing
      the given points. If the points aren't both inside nodes,
      then no edge is added.
      @param e the edge to add
      @param p1 a point in the starting node
      @param p2 a point in the ending node
   */
   public boolean connect(Edge e, Point2D p1, Point2D p2)
   {
      Node n1 = findNode(p1);
      Node n2 = findNode(p2);
      if (n1 != null && n2 != null &&  (!n1.getClass().equals(new NoteNode("").getClass())) 
			&& (!n2.getClass().equals(new NoteNode("").getClass())) )
      {
         e.connect(n1, n2);
         edges.add(e);
         return true;
      }
      return false;
   }

	/**
		Reverses all directed edges in the graph.
   */
   public void reverse() {
   	for (int i = 0; i < edges.size(); i++ ) {
   		Node temp = edges.get(i).getStart();
   		edges.get(i).setStart(edges.get(i).getEnd());
   		edges.get(i).setEnd(temp);
   	}
   }

   /**
    *Takes this graph and switches all of the edges and non edges.
    */

	public void complement() {
		ArrayList<Edge> allEdges = new ArrayList<Edge>();
		int i = 0;
		int j = 1;
		while (i < nodes.size()) {
			while (nodes.get(i).getClass().equals(new NoteNode("").getClass()) && i < (nodes.size() - 1)) i++;
			while (j < nodes.size()) {
				while (nodes.get(j).getClass().equals(new NoteNode("").getClass()) && j < (nodes.size() - 1)) j++;
				if (!nodes.get(i).getClass().equals( new NoteNode("").getClass() ) &&
					!nodes.get(j).getClass().equals( new NoteNode("").getClass() )) { 
					LineEdge l = new LineEdge(false);
					l.setStart(nodes.get(i));
					l.setEnd(nodes.get(j));
					allEdges.add(l);
				} // Only fails if a note is at last index.
				
				j++;
			}
			i++; j = i+1;
		}
		allEdges.removeAll(edges);
		edges = allEdges;
	}

   /**
      Adds a node to the graph so that the top left corner of
      the bounding rectangle is at the given point.
      @param n the node to add
      @param p the desired location
	  @return that the node was added.
   */
   public boolean add(Node n, Point2D p)
   {
      Rectangle2D bounds = n.getBounds();
      n.translate(p.getX() - bounds.getX(),
         p.getY() - bounds.getY());
      
	  nodes.add(n);
      return true;
   }
/** Adds a specified edge to the graph.
@param e the edge to be added.
*/
   public void addEdge(Edge e) {
   		if (e.getStart() != null && e.getEnd() != null) edges.add(e);
   }

   /**
      Finds a node containing the given point.
      @param p a point
      @return a node containing p or null if no nodes contain p
   */
   public Node findNode(Point2D p)
   {
      for (int i = nodes.size() - 1; i >= 0; i--)
      {
         Node n =  nodes.get(i);
         if (n.contains(p)) return n;
      }
      return null;
   }

   /**
      Finds an edge containing the given point.
      @param p a point
      @return an edge containing p or null if no edges contain p
   */
   public Edge findEdge(Point2D p)
   {
      for (int i = edges.size() - 1; i >= 0; i--)
      {
         Edge e = edges.get(i);
         if (e.contains(p)) return e;
      }
      return null;
   }

   /**
      Draws the graph
      @param g2 the graphics context
   */
   public void draw(Graphics2D g2)
   {
      for (Node n : nodes)
         n.draw(g2);

      for (Edge e : edges)
         e.draw(g2);

   }

   /**
      Removes a node and all edges that start or end with that node
      @param n the node to remove
   */
   public void removeNode(Node n)
   {
      for (int i = edges.size() - 1; i >= 0; i--)
      {
         Edge e =  edges.get(i);
         if (e.getStart() == n || e.getEnd() == n)
            edges.remove(e);
      }
      nodes.remove(n);
   }

   /**
      Removes an edge from the graph.
      @param e the edge to remove
   */
   public void removeEdge(Edge e)
   {
      edges.remove(e);
   }

   /**
      Gets the smallest rectangle enclosing the graph
      @param g2 the graphics context
      @return the bounding rectangle
   */
   public Rectangle2D getBounds(Graphics2D g2)
   {
      Rectangle2D r = null;
      for (Node n : nodes)
      {
         Rectangle2D b = n.getBounds();
         if (r == null) r = b;
         else r.add(b);
      }
      for (Edge e : edges)
         r.add(e.getBounds(g2));
      return r == null ? new Rectangle2D.Double() : r;
   }

   /**
      Gets the node types of a particular graph type.
      @return an array of node prototypes
   */
   public abstract Node[] getNodePrototypes();

   /**
      Gets the edge types of a particular graph type.
      @return an array of edge prototypes
   */
   public abstract Edge[] getEdgePrototypes();

   /**
      Gets the nodes of this graph.
      @return an unmodifiable list of the nodes
   */
   public List<Node> getNodes()
   {
      return Collections.unmodifiableList(nodes);
   }

   /**
      Gets the edges of this graph.
      @return an unmodifiable list of the edges
   */
   public List<Edge> getEdges()
   {
      return Collections.unmodifiableList(edges);
   }

   /** 
   Removes all nodes and edges from this graph.
   */
	public void clearAll() {
		nodes.clear();
		edges.clear();
	}
   private ArrayList<Node> nodes;
   private ArrayList<Edge> edges;
}
