package coolcase.veryhot.tool;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import coolcase.veryhot.*;

/**
* This is a simple line constructor tool. Not very useful for diagrams, but someone may need them...
*/
public abstract class LineTool extends AbstractTool implements MouseMotionListener, KeyListener, MouseListener {
	
	/**
	* Line being edited.
	*/
	protected LineFigure lineFigure = null;

	/**
	* Constructor.
	*/
	public LineTool( DrawingPanel canvas ) {
		super( canvas );
	}

	/**
	* Method called when the line is created and the first point will be added. This way we can check
	* for errors easier (throwing RuntimeExceptions, for example).
	*/	
	protected abstract Locator getBeginLocator( Point2D p );
	
	/**
	* Method called when the last point will be added. This way we can check
	* for errors easier (throwing RuntimeExceptions, for example).
	*/	
	protected abstract Locator getEndLocator( Point2D p );
	
	/**
	* Method called when the line is created and points are added (but not first neither last line's
	* point). This way we can check for errors easier (throwing RuntimeExceptions, for example).
	*/	
	protected abstract Locator getBetweenLocator( Point2D p );
	
	/**
	* Creates the line. We need this to create differente types of line (AssociationLine, IncludeLine,
	* GeneralizationLine, etc).
	*/
	protected LineFigure createLine( Locator begin ) {
		return new LineFigure( begin );
	}
	
	/**
	* A mouse click consist of a mouse pressed and mouse released event. It just happens if, between this
	* two events, no mouse movement occurs. Using it to create figures isn't very confortable, mousePressed
	* is much simpler. So we just have this method because the MouseListener interface mandates it.
	*/
	public void mouseClicked( MouseEvent e ) {}
	
	/**
	* When editing a closed line, moves the line's workingLocator.
	*/
	public void mouseReleased( MouseEvent e ) {}

	/**
	* Creates a line or add/edit a locator to it.
	*/
	public void mousePressed( MouseEvent e ) {
		// There isn't a line yet, so construct it.
		if ( lineFigure == null ) {
			try {
				// We are starting a line.
				Locator beginLocator = getBeginLocator( e.getPoint() );
				lineFigure = createLine( beginLocator );
				beginLocator.setFigure( null );
				lineFigure.lineTo( beginLocator );
				
				// We just add the figure now because otherwise it would have enought data to be drawn.
				canvas.addFigure( lineFigure );
			} catch ( IllegalArgumentException iae ) {
			}
		} else {
			if ( e.isShiftDown() ) {
				try {
					// Holding down Shift, we just add points and do not finish the line.
					Locator betweenLocator = getBetweenLocator( e.getPoint() );
					lineFigure.moveTo( betweenLocator );
					lineFigure.lineTo( betweenLocator );
				} catch ( IllegalArgumentException iae ) {
					lineFigure.dispose();
					lineFigure = null;
				}
			} else {
				try {
					// Line's end
					Locator endLocator = getEndLocator( e.getPoint() );
					lineFigure.moveTo( endLocator );
					lineFigure = null;
				} catch ( IllegalArgumentException iae ) {
				}
			}
		}
	}
	
	/**
	* Change the last point location if a line is being constructed.
	*/
	public void mouseMoved( MouseEvent e ) {
		if ( lineFigure != null ) {
			lineFigure.moveTo( new Locator( e.getPoint() ) );
		}
	}

	public void mouseDragged( MouseEvent e ) { }
	
	public void mouseExited( MouseEvent e ) { }

	public void mouseEntered( MouseEvent e ) { }

	/**
	* If we press ESC while drawing the figure, delete the figure.
	*/
	public void keyPressed( KeyEvent e ) {
		if ( lineFigure != null ) {
			if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
				lineFigure.dispose();
				lineFigure = null;
			}
		}
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}
	
	/** 
	* Usually, this kind of tool doesn't require some special action before losing focus.
	* So we default to nothing.
	*/  
	public void toolEnter() {
		super.toolEnter();
		lineFigure = null;
	}

	public void toolExit() {
		lineFigure = null;
		super.toolExit();
	}
}
