package coolcase.veryhot.tool;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import coolcase.veryhot.*;

/**
* This tool just simplifies the creation of tools that adds figures to the DrawingPanel.
*/
public abstract class ConstructionTool extends AbstractTool implements MouseListener {
	
	/**
	* The BasicTool constructor is used.
	*/
	public ConstructionTool( DrawingPanel canvas ) {
		super( canvas );
	}
	
	/** 
	* This is the method that should be overrided to create new figures.
	*/ 
	protected abstract Figure newFigure( Point2D p, Figure f );  

	/**
	* Adds a new figure in the DrawingPanel. The subclasses should alter the method
	* newFigure() instead of this one.
	*/
	public void mouseClicked( MouseEvent e ) {
	}

	/** 
	* Usually, this kind of tool doesn't require some special action before losing focus.
	* So we default to nothing.
	*/  
	public void toolEnter() {
		super.toolEnter();
	}

	public void toolExit() {
		super.toolExit();
	}

	public void mousePressed( MouseEvent e ) {
		Figure figure = newFigure( e.getPoint(), null );
		figure.setLocation( e.getPoint() );
		canvas.addFigure( figure );
	}
	
	public void mouseReleased( MouseEvent e ) {};
	public void mouseEntered( MouseEvent e ) {};
	public void mouseExited( MouseEvent e ) {};
}
