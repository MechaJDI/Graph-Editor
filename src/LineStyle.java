import java.awt.*;
import java.io.*;

/**
   This class defines line styles of various shapes.
*/
public enum LineStyle
{
   SOLID, DASHED, THICK, THICKDASHED;

   /**
      Gets a stroke with which to draw this line style.
      @return the stroke object that strokes this line style
   */
   public Stroke getStroke()
   {
      if (this == DASHED) return DASHED_STROKE;
      if (this == SOLID) return SOLID_STROKE;
      if (this == THICK) return THICK_STROKE;
      if (this == THICKDASHED) return THICKDASHED_STROKE;
      return null;
   }
/**
 *Creates new strokes, either Solid, dashed, or their thick versions.
 */
   private static Stroke SOLID_STROKE = new BasicStroke();
   private static Stroke THICK_STROKE = new BasicStroke(3.0f);
   private static Stroke DASHED_STROKE = new BasicStroke(
      1.0f,
      BasicStroke.CAP_SQUARE,
      BasicStroke.JOIN_MITER,
      10.0f,
      new float[] { 3.0f, 3.0f },
      0.0f);
   private static Stroke THICKDASHED_STROKE = new BasicStroke(
      3.0f,
      BasicStroke.CAP_SQUARE,
      BasicStroke.JOIN_MITER,
      10.0f,
      new float[] { 6.0f, 6.0f },
      0.0f);
}