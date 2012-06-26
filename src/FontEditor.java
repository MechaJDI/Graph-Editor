/** 	The class FontEditor manages an editor for available and viewable
	fonts. It customizes some of the methods of the PropertyEditor
	interface that the class PropertyEditorSupport implements, in
	particular, a constructor, supportsCustomEditor(), getCustomEditor(),
	isPaintable(), paintValue().
	updateFont() assists the construction of the editor in 
	getCustomEditor().

	The editor is a JPanel object with a JLabel object, a JComboBox 
	object containing names for fonts, a JTextField object for the 
	size and a JComboBox object for the style of the current Font object.

	The class has as its attribute an array of String objects for the
	the names of the available and viewable fonts.

	See first edition of text p. 313-4.

	yanushka
 */

import java.beans.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.font.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class FontEditor extends PropertyEditorSupport
{
/** 	Create a FontEditor object with a finite set of String objects.
	Get the available fonts in fontArray.
	Retain the ones that display in the ArrayList fontList.
	Then transfer fontList into the arrays names and fonts.
 */
    public FontEditor()
    {
        GraphicsEnvironment ge =
                        GraphicsEnvironment.getLocalGraphicsEnvironment();
        String []       fontArray = ge.getAvailableFontFamilyNames();
        ArrayList< String > fontList = 
			new ArrayList< String >( fontArray.length );

        for ( int i = 0; i < fontArray.length; i++ )
            if ( new Font( fontArray[ i ], Font.PLAIN, 12 ).canDisplayUpTo(
                        fontArray[ i ] ) == -1 ) 
                fontList.add( fontArray[ i ] );

	names = new String[ fontList.size() ];
	for ( int i = 0; i < fontList.size(); i++ )
	    names[ i ] = fontList.get( i );
    }

    public boolean supportsCustomEditor()
    {
        return true;
    }

    public boolean isPaintable()
    {
        return true;
    }

/** 	Define a custom editor for the Font class.
	@return a JPanel object

	The panel consists of a JLabel, a JComboBox object containing names
	for fonts, a JTextField object for the size and a JComboBox object 
	for the style of the current Font object.
	Define an anonymous controller for each combo box and for the 
	text field.
 */
    public Component getCustomEditor()
    {
	final JComboBox		fonts = new JComboBox( names ),
				styles = new JComboBox( STYLES );
	final JTextField	sizeTF = new JTextField( "12" );
	final JLabel		label = new JLabel( "Abcd0123." );

	fonts.setSelectedItem( "Arial" );
	styles.setSelectedItem( "Plain" );

	JPanel	panel = new JPanel( new GridLayout( 1, 4 ) );
	panel.add( label );
	panel.add( fonts );
	panel.add( sizeTF );
	panel.add( styles );

	JComboBox []	widgets = { fonts, styles };
        for ( int i = 0; i < 2; i++ )
	    widgets[ i ].addActionListener( new ActionListener()
                {
                    public void actionPerformed( ActionEvent event )
                    {
			updateFont( fonts, styles, sizeTF, label );
		    }
                });

	sizeTF.getDocument().addDocumentListener( new DocumentListener()
            {
               public void insertUpdate( DocumentEvent e )
               {
		    try {
			updateFont( fonts, styles, sizeTF, label );
			setAsText( sizeTF.getText() );
		    } catch( IllegalArgumentException exception ){ }
               }
               public void removeUpdate( DocumentEvent e )
               {
                   try {
			updateFont( fonts, styles, sizeTF, label );
			setAsText( sizeTF.getText() );
                   } catch( IllegalArgumentException exception ){}
               }
               public void changedUpdate( DocumentEvent e ) {}
            });

	return panel;
    }

/** 	Update selected Font object.
	@param fonts, a JComboBox object of available fonts
	@param style, a JComboBox object of plain, bold and italic
	@param sizeTF, a JTextField object for the size of the font
	@param label, a JLabel object to display the selected font
 */
    private void updateFont( JComboBox fonts, JComboBox styles,
			     JTextField sizeTF, JLabel label )
    {
	String  strF = ( String )fonts.getSelectedItem();
	int     style = Font.PLAIN,
		size = Integer.parseInt( ( String )sizeTF.getText() );

	if ( ( ( String )styles.getSelectedItem() ).equals( "Bold" ) )
	    style = Font.BOLD;
	else if ( ( ( String )styles.getSelectedItem() ).equals( "Italic" ) )
	    style = Font.ITALIC;

	Font    font = new Font( strF, style, size );
	label.setFont( font );
	setValue( font );
    }

/** 	Customize paintValue() to fill box with the current color.
        @param g, a Graphics object
        @param box, a Rectangle object for painting

	Draw the name of the font centered in box.
 */
    public void paintValue( Graphics g, Rectangle box )
    {
        Graphics2D      g2 = ( Graphics2D )g;
	Font		font = ( Font )getValue();
	String		name = font.getName();
        FontRenderContext frc = g2.getFontRenderContext();
        Rectangle2D     b = font.getStringBounds( name, frc );
        double	xSlack = Math.max( box.getWidth() - b.getWidth(), 0 ),
                xStart = box.getX() + xSlack / 2,
                yTop = box.getY() + ( box.getHeight() - b.getHeight() ) / 2,
                yStart = yTop - b.getY();
        
	g2.setColor( Color.BLACK );
        g2.drawString( name, ( float )xStart, ( float )yStart );
    }

    private String []	names;

    private static final String [] STYLES = { "Plain", "Bold", "Italic" };
}
