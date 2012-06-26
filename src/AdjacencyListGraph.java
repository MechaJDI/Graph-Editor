import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JDI
 */
public class AdjacencyListGraph extends SimpleGraph
{
    private int n = 0; // number of nodes
    private int d = 0; // diameter

    /** Constructs new AdjacencyListGraph.
     * @param f a file to parse
    */
    public AdjacencyListGraph(File f)
    {
        clearAll();
        parse(f);
    }

    /**
        Parses the text file into a new graph
     * @param f a file
        @return Graph a graph reflecting the format of the text file
    */
    private void parse(File f)
    {
        try
        {
            Scanner s = new Scanner(f); // use for counting purposes
            Scanner s2 = new Scanner(f); // data purposes
            String line;
            boolean first = false; //check if first line of text
            boolean wt = false; // weighted or unweighted
            boolean dir = true; // directed or undirected
            boolean made = false; // check if node already made
            double theta;
            double x;
            double y;
            int r;
            //StringTokenizer st;
            int k = 0;
            line = s.next();
            while(s.hasNext()) // if the field isn't blank
            {
                k++;
                line = s.next();

            }
            String[] list = new String[k];
            k = 0;
            while(k < list.length)
            {
                list[k] = s2.next();
                k++;
            }
            n = Integer.parseInt(list[0]); // convert string to integer for number of nodes
            d = Integer.parseInt(list[1]); // convert string to integer for diameter
            if(list[2].matches("weighted"))
               wt = true;
            if(list[3].contains("undirected"))
               dir = false;
            theta = 2 * Math.PI / n;
            r = d/2;
            CircleNode c = new CircleNode(Color.WHITE);
            CircleNode c2 = new CircleNode(Color.WHITE);
            c.setLabel(String.valueOf(list[4].charAt(0))); // very first node, doesnt return int for now..
            System.out.println(c.getLabel());
            x = r*Math.sin(-1*(getNodes().size())*theta + Math.PI);
            y = r*Math.cos(-1*(getNodes().size())*theta + Math.PI);
            add(c, new Point2D.Double(x + r, y + r));
            //c.sendIndex( getNodes().size() - 1 );
            int i = 5; // second node
            while(i < list.length)
            {
                if(!wt) // if unweighted
                {
                    if(list[i].length() > 1) // if the substring is more than one character
                    {
                        //i++; // advance position to next node
                        c = new CircleNode(Color.WHITE); //
                        c.setLabel(String.valueOf(list[i].charAt(0))); // reset head node             *******
                        System.out.println(c.getLabel());

                        x = r*Math.sin(-1*(getNodes().size())*theta + Math.PI);
                        y = r*Math.cos(-1*(getNodes().size())*theta + Math.PI);
                        add(c, new Point2D.Double(x + r, y + r));

                        c2 = new CircleNode(Color.WHITE); //
                        c2.setLabel(String.valueOf(list[i].charAt(1)));


                        //i++; // advance again
                    }
                    else
                    {
                        c2 = new CircleNode(Color.WHITE);
                        c2.setLabel(list[i]);

                    }

                     x = r*Math.sin(-1*(getNodes().size())*theta + Math.PI);
                     y = r*Math.cos(-1*(getNodes().size())*theta + Math.PI);
                     add(c2, new Point2D.Double(x + r, y + r));

                    System.out.println(c2.getLabel());

                    if(!dir) // if undirected
                    {
                        LineEdge l = new LineEdge(false);
                        l.connect(c, c2);
                        addEdge(l);
                    }
                    else
                    {
                        LineEdge l = new LineEdge(true);
                        l.connect(c, c2);
                        addEdge(l);
                    }
                    i++;

               }
               else // if weighted
               {
                    if(list[i].length() > 1) // if the substring is more than one character
                    {
                        //i++; // advance position to next node
                        c = new CircleNode(Color.WHITE); //
                        c.setLabel(String.valueOf(list[i].charAt(0))); // reset head node             *******

                        System.out.println(c.getLabel());

                        x = r*Math.sin(-1*(getNodes().size())*theta + Math.PI);
                        y = r*Math.cos(-1*(getNodes().size())*theta + Math.PI);
                        add(c, new Point2D.Double(x + r, y + r));

                        c2 = new CircleNode(Color.WHITE); //
                        c2.setLabel(String.valueOf(list[i].charAt(1)));
                        //i++; // advance again
                    }
                    else
                    {
                        c2 = new CircleNode(Color.WHITE);
                        c2.setLabel(list[i]);
                    }

                    System.out.println(c2.getLabel());

                    x = r*Math.sin(-1*(getNodes().size())*theta + Math.PI);
                    y = r*Math.cos(-1*(getNodes().size())*theta + Math.PI);
                    add(c2, new Point2D.Double(x + r, y + r));

                    if(!dir) // if undirected
                    {
                        LineEdge l = new LineEdge(false);
                        l.connect(c, c2);
                        l.setWeight(Double.parseDouble(list[i+1]));
                        addEdge(l);
                    }
                    else
                    {
                        LineEdge l = new LineEdge(true);
                        l.connect(c, c2);
                        l.setWeight(Double.parseDouble(list[i+1]));
                        addEdge(l);
                    }
                    i+=2;
                }

               
           }


        }
        catch(FileNotFoundException exception)
        {
            n = 0;
            d = 0;
        }
        //return g;
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

}


