import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.filechooser.*;

/**
   This frame shows the toolbar and the graph.
*/
public class GraphFrame extends JFrame
{
   /**
      Constructs a graph frame that displays a given graph.
      @param graph the graph to display
   */
   public GraphFrame(final Graph graph, boolean isMR)
   {
   	  setTitle("Advanced Graph Editor");
      setSize(FRAME_WIDTH, FRAME_HEIGHT);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  setLocation(5,5);
      this.graph = graph;
	  isMoreRandom = isMR;
      constructFrameComponents();
      // set up menus

      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      JMenu fileMenu = new JMenu("File");
      menuBar.add(fileMenu);

      JMenuItem openItem = new JMenuItem("Open");
      openItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               openFile();
            }
         });
      fileMenu.add(openItem);

      JMenuItem saveItem = new JMenuItem("Save");
      saveItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               saveFile();
            }
         });
      fileMenu.add(saveItem);

		JMenuItem importItem = new JMenuItem("Import from adjacency list");
      importItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               importList();
            }
         });
      fileMenu.add(importItem);
		
		JMenuItem exportItem = new JMenuItem("Export");
      exportItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               export();
            }
         });
      fileMenu.add(exportItem);


      JMenuItem exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               System.exit(0);
            }
         });
      fileMenu.add(exitItem);


      JMenuItem deleteItem = new JMenuItem("Delete");
      deleteItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               panel.removeSelected();
            }
         });

      JMenuItem propertiesItem
         = new JMenuItem("Properties");
      propertiesItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               panel.editSelected();
            }
         });

       JMenuItem reverseItem
         = new JMenuItem("Reverse");
      reverseItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               panel.reverseEdges();
            }
         });
		JMenuItem randomItem
         = new JMenuItem("Random");
     	randomItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               panel.random(isMoreRandom);
            }
         });
		 JMenuItem complementItem
         = new JMenuItem("Complement");
     	complementItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               panel.complement();
            }
         });
         JMenuItem completeItem
         = new JMenuItem("Complete");
     	completeItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               panel.complete();
            }
         });
		
        JMenuItem helpItem = new JMenuItem("Help...");
     	helpItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               panel.showHelp();
            }
         });
         	JMenuItem clearItem = new JMenuItem("Clear");
      clearItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               panel.clearAll();
            }
         });
		 JMenuItem randomnessItem = new JMenuItem("Set Randomness");
      randomnessItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
				int selectedIndex = 0;
				if (isMoreRandom) selectedIndex = 1;
				Object[] possibleValues = { false, true };
				Object selectedValue = null;
				selectedValue = JOptionPane.showInputDialog(null,
				"Randomize colors and sizes?", "Set Randomness",
				JOptionPane.INFORMATION_MESSAGE, null,
				possibleValues, possibleValues[selectedIndex]);
				if (selectedValue == null) isMoreRandom = isMoreRandom;
				else if (selectedValue.equals(true)) isMoreRandom = true;
				else isMoreRandom = false;
			}
         });

      JMenu editMenu = new JMenu("Edit");
      JMenu graphMenu = new JMenu("Graph");
      JMenu helpMenu = new JMenu("Help");
      editMenu.add(clearItem);
      editMenu.add(deleteItem);
      editMenu.add(propertiesItem);
	  editMenu.add(randomnessItem);
      graphMenu.add(complementItem);
      graphMenu.add(completeItem);
      graphMenu.add(randomItem);
	  graphMenu.add(reverseItem);
      helpMenu.add(helpItem);
      menuBar.add(editMenu);
      menuBar.add(graphMenu);
      menuBar.add(helpMenu);
   }

   /**
      Constructs the tool bar and graph panel.
   */
   private void constructFrameComponents()
   {
      toolBar = new ToolBar(graph);
      panel = new GraphPanel(toolBar, graph);
      scrollPane = new JScrollPane(panel);
      this.add(toolBar, BorderLayout.NORTH);
      this.add(scrollPane, BorderLayout.CENTER);
   }
/** 	Export the current finite graph as a graphics file in png format.
  */
    private void export()
    {
		JFileChooser fileChooser = new JFileChooser( CURR_DIR );
		// Not supported in pre Java 6. Comment out if running Java 5 or earlier.
		javax.swing.filechooser.FileFilter 	pingFilter = new FileNameExtensionFilter( "ping graphics files", "png" );
		fileChooser.addChoosableFileFilter( pingFilter );

		int choice  = fileChooser.showDialog( null, "Export" );
		if ( choice == JFileChooser.APPROVE_OPTION ) {
	   	 try {
				File file = fileChooser.getSelectedFile();
				RenderedImage image = createGraphImage();
				ImageIO.write( image, "png", file );
	 	     }
	 	     catch (IOException exception) {
					JOptionPane.showMessageDialog( null, exception );
	    	}
		}
    }

/** 	Create a RenderedImage object for the current Graph object.

	http://www.exampledepot.com/egs/javax.imageio/Graphic2File.html
 */
    private RenderedImage createGraphImage()
    {
      BufferedImage bufferedImage = new BufferedImage(
                panel.getWidth(), panel.getHeight(),
                BufferedImage.TYPE_INT_RGB );

        //      Create a graphics contents on the buffered image.
        Graphics2D g2d = bufferedImage.createGraphics();

        //      Draw graphics.
        g2d.setColor( Color.white );
        g2d.fillRect( 0, 0, panel.getWidth(), panel.getHeight() );
        g2d.setColor( Color.black );

		panel.paintComponent( g2d );

        g2d.dispose();

        return bufferedImage;
    }

   /**
      Asks the user to open a graph file.
   */
   private void openFile()
   {
      // let user select file

      JFileChooser fileChooser = new JFileChooser();
      int r = fileChooser.showOpenDialog(this);
      if (r == JFileChooser.APPROVE_OPTION)
      {
         // open the file that the user selected
         try
         {
            File file = fileChooser.getSelectedFile();
            ObjectInputStream in = new ObjectInputStream(
               new FileInputStream(file));
            graph = (Graph) in.readObject();
            in.close();
            this.remove(scrollPane);
            this.remove(toolBar);
            constructFrameComponents();
            validate();
            repaint();
         }
         catch (IOException exception)
         {
            JOptionPane.showMessageDialog(null,
               exception);
         }
         catch (ClassNotFoundException exception)
         {
            JOptionPane.showMessageDialog(null,
               exception);
         }
      }
   }

   /**
      Saves the current graph in a file.
   */
   private void saveFile()
   {
      JFileChooser fileChooser = new JFileChooser();
      if (fileChooser.showSaveDialog(this)
         == JFileChooser.APPROVE_OPTION)
      {
         try
         {
            File file = fileChooser.getSelectedFile();
            ObjectOutputStream out = new ObjectOutputStream(
               new FileOutputStream(file));
            out.writeObject(graph);
            out.close();
         }
         catch (IOException exception)
         {
            JOptionPane.showMessageDialog(null,
               exception);
         }
      }
   }
   /**
    * Reads a text file for input
    */
	public void importList() {
		JFileChooser fileChooser = new JFileChooser();
		int r = fileChooser.showOpenDialog(this);
		if (r == JFileChooser.APPROVE_OPTION)
		{
        // open the file that the user selected
			try
			{
				File file = fileChooser.getSelectedFile();
				graph = new AdjacencyListGraph(file);
				this.remove(scrollPane);
				this.remove(toolBar);
				constructFrameComponents();
				validate();
				repaint();
			}
			finally
			{
             
			}
       }
	}
	
   /** Returns whether or not RandomGraphs have randomized colors and sizes.
   @return if the graph is set to display more random RandomGraphs.
   */
	public boolean getIsMoreRandom() 
	{
		return isMoreRandom;
	}
	/** Sets whether or not RandomGraphs have randomized colors and sizes.
	@param isMR the new value of isMoreRandom
   */
	public void setIsMoreRandom(boolean isMR) 
	{
		 isMoreRandom  = isMR;
	}


   private Graph graph;
   private GraphPanel panel;
   private JScrollPane scrollPane;
   private ToolBar toolBar;
   private boolean isMoreRandom;

   private static final String 	CURR_DIR = ".";
   public static final int FRAME_WIDTH = 600;
   public static final int FRAME_HEIGHT = 400;
}
