/*	File: 	h.java
	help frame for graph editor

	yanushka
 */
// http://java.sun.com/docs/books/tutorial/uiswing/examples/components/TreeDemoProject/src/components/TreeDemo.java

/**
 */
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import java.net.URL;
import java.io.IOException;
import java.awt.*;

/**	The class HelpPanel manages a help panel with a tree of topics
	on the west and associated help in the center.
 */
class HelpPanel extends JPanel implements TreeSelectionListener
{
    private final static String 	HELP_FILE = "help.html",
					HELP_STRING =
			"Help Menu                              ";
    private final static String [] 	CATEGORIES =
		{ "About", "Toolbar", "File", "Edit", "Graph" };
    private final static String [][] 	TOPICS = {
	{ "Lookbacks" },
	{ "Grabber", "Circular Node", "Square Node", 
		"Edge", "Directed Edge" },
	{ "Open", "Save", "Export" },
	{ "Clear", "Properties", "Delete", "Set Randomness" },
	{ "Complete", "Random", "Reverse", "Complement" } },
					FILES = {
	{ "lookbacks" },
	{ "grabber", "circularNode", "squareNode", 
		"edge", "directedEdge" },
	{ "open", "save", "export" },
	{ "clear", "properties", "delete", "setrandomness" },
	{ "complete", "random", "reverse", "complement" } };
    private final static Dimension 	OUTER_DIM = new Dimension( 700, 400 ),
					TREE_DIM = new Dimension( 250, 400 );

    private JEditorPane htmlPane;
    private JTree tree;
    private URL helpURL;

/**	Create a HelpPanel object.
 */
    public HelpPanel()
    {
	super();

        // Create the nodes.
        DefaultMutableTreeNode top = new DefaultMutableTreeNode( HELP_STRING );
        createNodes( top );

        // Create a tree that allows one selection at a time.
        tree = new JTree( top );
        tree.getSelectionModel().setSelectionMode
                ( TreeSelectionModel.SINGLE_TREE_SELECTION );

        // Listen for when the selection changes.
        tree.addTreeSelectionListener( this );

        // Create a scroll pane and add the tree to it.
        JScrollPane treeView = new JScrollPane( tree );
        treeView.setMinimumSize( TREE_DIM );

        // Create the HTML viewing pane.
        htmlPane = new JEditorPane();
        htmlPane.setEditable( false );
        initializeHelp();
        JScrollPane htmlView = new JScrollPane( htmlPane );

        // Add the scroll panes to an outer panel.
	JPanel	outerP = new JPanel( new BorderLayout() );
	outerP.add( treeView, "West" );
	outerP.add( htmlView, "Center" );
        outerP.setPreferredSize( OUTER_DIM );
	add( outerP );
    }

/** 	Required by TreeSelectionListener interface.
	Respond to a change of state in the tree.
 */
    public void valueChanged( TreeSelectionEvent e )
    {
        DefaultMutableTreeNode node = ( DefaultMutableTreeNode )
                           tree.getLastSelectedPathComponent();

        if ( node != null ) {
            Object nodeInfo = node.getUserObject();
            if ( ! node.isRoot() ) {
                HelpInfo info = ( HelpInfo )nodeInfo;
                displayURL( info.helpURL );
            } else {
                displayURL( helpURL );
            }
	}
    }

/**	The class HelpInfo manages an item for help as a pair
	consisting of a name and a URL.
 */
    private class HelpInfo
    {
        public String 	helpName;
        public URL 	helpURL;

        public HelpInfo( String topic, String filename )
	{
            helpName = topic;
            helpURL = getClass().getResource( filename );
            if ( helpURL == null )
                System.err.println( "Couldn't find file: " + filename );
        }

        public String toString()
	{
            return helpName;
        }
    }

/**	Initialize the root of the help tree.
 */
    private void initializeHelp()
    {
        helpURL = getClass().getResource( HELP_FILE );
        if ( helpURL == null ) {
            System.err.println( "Couldn't open help file: " + HELP_FILE );
        }

        displayURL( helpURL );
    }

/**	Display a help file associated with the parameter.
	@param url, URL for a help file
 */
    private void displayURL( URL url )
    {
        try {
            if ( url != null ) {
                htmlPane.setPage( url );
            } else { // null url
		htmlPane.setText( "File Not Found" );
            }
        } catch ( IOException e ) {
            System.err.println( "Attempted to read a bad URL: " + url );
        }
    }

/**	Create nodes for the help tree rooted at top.
	@param top, the root of the tree of help nodes
 */
    private void createNodes( DefaultMutableTreeNode top )
    {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode topic = null;

	for ( int i = 0; i < CATEGORIES.length; i++ ) {
            category = new DefaultMutableTreeNode( new HelpInfo(
				CATEGORIES[ i ],
				CATEGORIES[ i ].toLowerCase() + ".html" ) );
            top.add( category );
	    for ( int j = 0; j < TOPICS[ i ].length; j++ ) {
        	topic = new DefaultMutableTreeNode( new HelpInfo(
			TOPICS[ i ][ j ], FILES[ i ][ j ] + ".html" ) );
        	category.add( topic );
	    }
	}
    }
}

/**	The class HelpFrame manages a JFrame containing a HelpPanel object.
 */
class HelpFrame extends JFrame
{
    /** Create the GUI and show it. For thread safety,
     *  this method should be invoked from the event dispatch thread.
     */
    public HelpFrame()
    {
        // Create and set up the window.
	super( "Help" );
        setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
        // Add content to the window.
        add( new HelpPanel() );
		setLocation(700,0);
        // Display the window.
        pack();
        setVisible( true );
    }
}
