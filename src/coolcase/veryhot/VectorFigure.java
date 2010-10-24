package coolcase.veryhot;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.awt.geom.*;

/**
* VectorFigure is a implementation of a figure. But it cannot be used directly as one,
* as many features (like how to draw) as not especified. In fact, this class is here 
* just to agregate the usual methods implements by a figure. We had do generelize it
* in order to do something useful.
* <BR>
* This class define objects observable. The objects sent on a modification are following
* strings:
* <UL>
*	<LI>Dimension</LI>
*	<LI>Location</LI>
*	<LI>Zoom</LI>
*	<LI>Selection</LI>
*	<LI>Dispose</LI>
* </UL>
*/

public abstract class VectorFigure extends Figure {
	/**
	* This is the stroke we will use to draw the figure. The default it's a gray.
	*/
	protected BasicStroke lineStroke = new BasicStroke( 2.0f );
	
	/**
	* This is the color we will use when the figure is selected.
	*/
	protected Color focusLineColor = Color.red;
	
	/**
	* This is the color we will use when the figure isn't selected.
	*/
	protected Color nonFocusLineColor = Color.black;


	/**
	* Creates the figure. The Figure's constructor is called.
	*/
	public VectorFigure() {
		super();
	}

	/**
	* Returns a Locator correspoding to a point that will be used as connection point. By
	* default, it's the middle point.
	*/
	public Point2D getConnectionPoint() {
		Rectangle2D r = getBounds2D();
		Point2D p = new Point2D.Double( r.getX() + ( r.getWidth() / 2 ), r.getY() + ( r.getHeight() / 2 ) );
		return p;
	}

	public void paint( Graphics2D g ) {
		g.setStroke( getLineStroke() );

		if ( isSelected() ) {
			g.setColor( getFocusLineColor() );
		} else {
			g.setColor( getNonFocusLineColor() );
		}
	}

	/**
	* Returns the stroke used to paint. The default is a 2mm thick solid stroke.
	*/
	public BasicStroke getLineStroke() {
		return lineStroke;
	}

	/**
	* Changes the stroke used to paint.
	*/
	public void setLineStroke( BasicStroke stroke ) {
		lineStroke = stroke;
	}

	/**
	* Gets the color used by the stroke to paint the figure when it is selected (on focus)
	* (default is Color.black).
	*/
	public Color getFocusLineColor() {
		return focusLineColor;
	}

	/**
	* Changes the color used by the stroke to paint the figure when it is selected (focused).
	*/
	public void setFocusLineColor( Color c ) {
		focusLineColor = c;
	}

	/**
	* Gets the color used by the stroke to paint the figure when it isn't selected (not onfocus)
	* (default is Color.red).
	*/
	public Color getNonFocusLineColor() {
		return nonFocusLineColor;
	}

	/**
	* Changes the color used by the stroke to paint the figure when it is not selected (non focused).
	*/
	public void setNonFocusLineColor( Color c ) {
		nonFocusLineColor = c;
	}
	
	/**
	* Changes the figure's width. This <emph>may<emph> change the figure's size (if the paint method
	* uses the width when drawing).
	*/
	public void setWidth( double w ) {
		width = w;
	}
	
	/**
	* Returns the figure's width.
	*/
	public double getWidth() {
		return width;
	}
	
	/**
	* Changes the figure's height. This <emph>may<emph> change the figure's size (if the paint method
	* uses the height when drawing).
	*/
	public void setHeight( double h ) {
		height = h;
	}
	
	/**
	* Returns the figure's height.
	*/
	public double getHeight() {
		return height;
	}
}
