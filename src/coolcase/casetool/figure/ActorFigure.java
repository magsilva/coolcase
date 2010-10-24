package coolcase.casetool.figure;

import coolcase.veryhot.*;
import coolcase.uml.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.font.*;

/**
* Actors class. It implements the visual part as well as the UML one.
*/
public class ActorFigure extends VectorFigure {

	/**
	* Actor
	*/
	private Actor actor;
	
	/**
	* The shapes used to draw the actor.
	*/
	private Shape leftArm, rightArm, leftLeg, rightLeg, chest, head;
	
	/**
	* Draw the actor.
	*/
	public void paint( Graphics2D g ) {
		super.paint( g );
		
		Stroke oldLineStroke = getLineStroke();
		g.setStroke( getLineStroke() );
		
		Font f = g.getFont();
		FontRenderContext frc = g.getFontRenderContext();
		TextLayout label = new TextLayout( actor.getName(), f, frc );
 		
		// Then we get the font to be used, take some information from it and calculates the
		// string height and width. Knowing the font, we get its metrics and, with this data,
		// gets the actor's name width and height.
		FontMetrics fm = g.getFontMetrics( f );
		int stringWidth = fm.stringWidth( actor.getName() );
		int stringHeight = fm.getAscent();

		// Draw the string.
		label.draw( g, (float)( location.getX() + ( ( width - stringWidth ) / 2 ) ), (float)( location.getY() + ( height - stringHeight ) ) );
		
		// The line stroke now on will be set based on the taged values.
		if ( actor.isParallel() ) {
			g.setStroke( new BasicStroke( 4.0f ) );
		} else {
			g.setStroke( new BasicStroke( 2.0f ) );
		}
		
		// We will fill the actor's head first so that the line appears (you will understand
		// that, believe me ;)
		if ( actor.isDistributed() ) {
			Color oldColor = g.getColor();
			g.setColor( Color.gray );
			g.fill( head );
			g.setColor( oldColor );
		}

		// Draw the Actor head.
		g.draw( head );
		
		// Draw the actor body.
		g.draw( chest );
		
		// Draw the arms.
		g.draw( leftArm );
		g.draw( rightArm );
		
		// Draw the left leg.
		g.draw( leftLeg );
		
		// Draw the right leg.
		g.draw( rightLeg );
		
		g.setStroke( oldLineStroke );
	}

	public PathIterator getPathIterator( AffineTransform at ) {
		return null;
	}
	
	private void createShapes() {
		// We need to setup several things before write the actor's name. First, we set
		// how far the text will be from the actor itself.
		double iconTextSpacing = 4.0;
 		double iconHeight = height - iconTextSpacing;
		double armWidth = iconHeight / 3;
		double armHeight = ( iconHeight / 4 ) + ( iconHeight / 8 );
		
		leftArm = new Line2D.Double( location.getX() + ( width / 2 - armWidth / 2 ), location.getY() + armHeight, location.getX() + ( width / 2 ), location.getY() + armHeight );

		rightArm = new Line2D.Double( location.getX() + ( width / 2 ), location.getY() + armHeight, location.getX() + ( width / 2 + armWidth / 2 ), location.getY() + armHeight );

		leftLeg = new Line2D.Double( location.getX() + ( width / 2 ), location.getY() + ( iconHeight / 2 ), location.getX() + ( width / 2 - iconHeight / 4 ), location.getY() + ( iconHeight / 2 + iconHeight / 4 ) );

		rightLeg = new Line2D.Double( location.getX() + ( width / 2 ), location.getY() + ( iconHeight / 2), location.getX() + ( width / 2 + iconHeight / 4), location.getY() + ( iconHeight / 2 + iconHeight / 4 ) );
		
		chest = new Line2D.Double( location.getX() + ( width / 2 ), location.getY() + ( iconHeight / 4 ), location.getX() + ( width / 2 ), location.getY() + ( iconHeight / 2 ) );
		
		head = new Ellipse2D.Double( location.getX() + ( ( width - iconHeight / 4 ) / 2) + 1, location.getY() + 2, iconHeight / 4 - 1, iconHeight / 4 - 2);
	}
	
	public void setLocation( Point2D p ) {
		super.setLocation( p );
		createShapes();
	}

	public Actor getActor() {
		return actor;
	}
		
	/**
	* Actor's constructor.
	*/
	public ActorFigure( String s ) {
		width = 80;
		height = 90;
		actor = new Actor( s );
		createShapes();
	}
}
