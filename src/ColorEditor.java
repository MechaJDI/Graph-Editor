/** 	The class ColorEditor manages colors with a JColorChooser.
	It customizes some of the methods of the PropertyEditor interface
	that the class PropertyEditorSupport implements, in particular,
	supportsCustomEditor(), getCustomEditor(), isPaintable(),
	paintValue().
	
	See text pp. 313-4.

	yanushka
 */

import java.beans.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class ColorEditor extends PropertyEditorSupport
{
    private static final long serialVersionUID = 1;

    public boolean supportsCustomEditor()
    {
	return true;
    }

    public boolean isPaintable()
    {
	return true;
    }

/** 	Customize paintValue() to fill box with the current color.
	@param g, a Graphics object
	@param box, a Rectangle object for painting
 */
    public void paintValue( Graphics g, Rectangle box )
    {
	Graphics2D	g2 = ( Graphics2D )g;

	g2.setColor( ( Color )getValue() );
	g2.fill( box );
    }

/** 	Define a CustomEditor using a JColorChooser object.
	@return a JColorChooser object with an anonymous controller
 */
    public Component getCustomEditor()
    {
        final JColorChooser     jcc = new JColorChooser();
                                                                                
        jcc.getSelectionModel().addChangeListener( new
            ChangeListener()
            {
                public void stateChanged( ChangeEvent ce )
                {
                    setValue( jcc.getColor() );
                }
            });

        return jcc;
    }
}
