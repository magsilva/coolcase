package coolcase.casetool.figure;

import coolcase.casetool.Sistema;
import coolcase.veryhot.*;
import coolcase.uml.*;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.font.*;

/**
* Use Case class.
*/
public class UseCaseFigure extends VectorFigure {
	
	private Ellipse2D ellipse;
	
	double ellipseWidth;
	
	double ellipseHeight;
	
	double interIconSpace;
	
	private UseCase useCase;
	
	/**
	* Draws the use case.
	*/
	public void paint( Graphics2D g ) {
		super.paint( g );
		
		Font f = g.getFont();
		FontRenderContext frc = g.getFontRenderContext();
		TextLayout label = new TextLayout( useCase.getName(), f, frc );
		Rectangle2D labelBounds = label.getBounds();
		
		double stringXDelta = (width / 2 ) - ( labelBounds.getWidth() / 2 );
		double stringYDelta = height - labelBounds.getHeight();				

		// Draw the string.
		label.draw( g, (float)( location.getX() + stringXDelta ), (float)( location.getY() + stringYDelta ) );
		
		// We will fill the use case head first so that the line appears (you will understand
		// that, believe me ;)
		if ( useCase.isDistributed() ) {
			Color oldColor = g.getColor();
			g.setColor( Color.gray );
			g.fill( ellipse );
			g.setColor( oldColor );
		}

		// Draw the oval.
		g.draw( ellipse );
		
		
		if ( ( labelBounds.getWidth() + ( width / 2 ) - ( labelBounds.getWidth() / 2 ) ) > width ) {
			setDimension( labelBounds.getWidth() + ( width / 2 ) - ( labelBounds.getWidth() / 2 ), ellipseHeight + interIconSpace + labelBounds.getHeight() );
		} else {
			setDimension( width, ellipseHeight + interIconSpace + labelBounds.getHeight() );
		}
	}

	public PathIterator getPathIterator( AffineTransform af ) {
		return ellipse.getPathIterator( af );
	}
	
	public void setLocation( Point2D p ) {
		super.setLocation( p );
		Sistema.logger.info( "Bounds: " + location.getX() + "-" + ( location.getX() + width ) + "," + location.getY() + "-" + ( location.getY() + height ) );
		createEllipse();	
	}
	
	public void createEllipse() {
		double ellipseX = location.getX() + ( width / 2 ) - ( ellipseWidth / 2 );
		double ellipseY = location.getY();
		ellipse = new Ellipse2D.Double( ellipseX + 1, ellipseY + 1, ellipseWidth - 2, ellipseHeight - 2 );
		Sistema.logger.info( "Ellipse: " + ellipseX + "-" + ( ellipseX + ellipseWidth ) + "," + ellipseY + "-" + ( ellipseY + ellipseHeight ) );
	}
	
	public UseCase getUseCase() {
		return useCase;
	}
	
	/**
	* Constructor.
	*/
	public UseCaseFigure( String name ) {
		super();
		width = 70;
		height = 55;
		interIconSpace = 10;
		ellipseWidth = width;
		ellipseHeight = height - interIconSpace;
		useCase = new UseCase( name );
		createEllipse();
	}
}
