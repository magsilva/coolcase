package coolcase.veryhot.tool;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import coolcase.veryhot.*;

public class SelectionTool extends AbstractTool implements MouseListener, KeyListener, MouseMotionListener {
	
	/**
	* Figures that have been selected. Actually, we don't have to save them here, we could just grab all the
	* drawingPanel's figures and check the ones selected. This is just a way to speed things up a bit.
	*/
	protected LinkedList selectedFigures = null;

	/**
	* A figure has a point, the upper left one, that determines its position. However, in an
	* event, the point returned is never that one. referencePoint saves this point so that you
	* can solve, with that, the difference between the figure's point and the event's point,
	* correcting the picture positioning.
	*/
	protected Point2D referencePoint = null;

	/**
	* Used mainly when selecting a region. It's the upper left point that helps defining the
	* selectionArea rectangle.
	*/
	protected Point2D startPoint = null;
	
	/**
	* Used mainly when selecting a region. It's the bottom right point that helps defining the
	* selectionArea rectangle.
	*/
	protected Point2D endPoint = null;
	
	/**
	* The rectangle that will be used to select the figures in the drawingPanel.
	*/
	protected Rectangle2D selectionArea = null;
	
	/**
	* Called when created the tool. The DrawingPanel where it will work need to be informed too.
	*/
	public SelectionTool( DrawingPanel canvas ) {
		super( canvas );
	}

	/**
	* Can be overuled in order to just allow a certain type of figure to be selected.
	*/
	protected boolean checkFigure( Figure f ) {
		return true;
	}
	
	/**
	* Check if any figure is being selected.
	*/
	protected boolean hasSelected() {
		if ( selectedFigures != null && selectedFigures.size() > 0 ) {
			return true;
		}
		return false;
	}
	
	/**
	* Deselects all the figures currently selected.
	*/
	protected void deselectAll() {
		// None selected, just returns.
		if ( selectedFigures == null ) {
			return;
		}
		
		ListIterator i = selectedFigures.listIterator();
		while ( i.hasNext() ) {
			Figure figure = (Figure)i.next();
			figure.setSelected( false );
		}
		selectedFigures = null;
	}
	
	/**
	* Selects a figure.
	*/
	protected void select( Figure f ) {
		// Needs a valid figure.
		if ( f == null ) {
			throw new IllegalArgumentException();
		}
		
		if ( selectedFigures == null ) {
			selectedFigures = new LinkedList();
		}
		f.setSelected( true );
		if ( selectedFigures.contains( f ) == false ) {
			selectedFigures.add( f );
		}
	}
	
	/**
	* Deselects a figure.
	*/
	protected void deselect( Figure f ) {
		// Needs a valid figure.
		if ( f == null ) {
			throw new IllegalArgumentException();
		}
		
		if ( selectedFigures.contains( f ) ) {
			f.setSelected( false );
			selectedFigures.remove( f );
		}
	}
	
	/**
	* Takes the figure at the point where the mouse was pressed.
	*/
	public void mousePressed( MouseEvent e ) {
		canvas.requestFocus();
		
		Figure figure = canvas.getFigureAt( e.getPoint() );
		
		// Changes the mouse cursor back to normal.
		((Component)canvas).setCursor( Cursor.getDefaultCursor() );

		referencePoint = e.getPoint();

		// Control holded down means that more figures are being selected at the same time.
		if ( e.isControlDown() ) {
			if ( figure != null ) {
				if ( figure.isSelected() ) {
					deselect( figure );
				} else {
					select( figure );
				}
			}
		} else {
			if ( figure == null ) {
				deselectAll();
				setStartPoint( e.getPoint() );
			} else {
				if ( selectedFigures != null && selectedFigures.contains( figure ) == false ) {
					deselectAll();
				}
				
				// Selects the figure.	
				select( figure );
			}
		}
	}

	/**
	* Moves the figure. This method is called when a mouse button is pressed and then the mouse is
	* "moved".
	*/
	public void mouseDragged( MouseEvent e ) {
		// If no picture was selected, nothing to do.
		if ( hasSelected() ) {
			// Change cursor image.
			((Component)canvas).setCursor( Cursor.getPredefinedCursor( Cursor.MOVE_CURSOR ) );
			
			double deltaX = e.getPoint().getX() - referencePoint.getX(); 
			double deltaY =	e.getPoint().getY() - referencePoint.getY();
			moveSelected( deltaX, deltaY );
			referencePoint = e.getPoint();
		} else {
			setEndPoint( e.getPoint() );
		}
	}
	
	/**
	* Sets the new figure location then the figure is released.
	*/
	public void mouseReleased( MouseEvent e ) {
		referencePoint = null;
		if ( hasSelected() ) {
			// Changes the mouse cursor back to normal.
			((Component)canvas).setCursor( Cursor.getDefaultCursor() );
		} else {
			setEndPoint( e.getPoint() );
			selectArea( startPoint, endPoint );
			repaintSelectionArea();
			selectionArea = null;
		}
	}

	/**
	* Selects a figure or more figures. Rembember, mouseClicked() is click and release, just that. See
	* that mousePressed() and mouseReleased() are called when a mouseClicked() happens.
	*/
	public void mouseClicked( MouseEvent e ) {
	}

	/**
	* Method called when the mouse enters a listener component.
	*/
	public void mouseEntered( MouseEvent e ) {}

	/**
	* Method called when the mouse exists a listener component.
	*/
	public void mouseExited( MouseEvent e ) {}

	/**
	* Method to be called when the mouse is moved with no pressed button.
	*/
	public void mouseMoved( MouseEvent e ) {}

	/**
	* If Delete is pressed with a figure selected, remove it.
	*/
	public void keyPressed( KeyEvent e ) {
		if ( hasSelected() ) {
			if ( e.getKeyCode() == KeyEvent.VK_DELETE ) {
				ListIterator i = selectedFigures.listIterator();
				while ( i.hasNext() ) {
					Figure f = (Figure)i.next();
					i.remove();
					f.dispose();
				}
				selectedFigures = null;
			}
		}
	}

	/**
	* Method called when a key is typed. Not actually used, just required by the KeyListener interface.
	*/
	public void keyTyped( KeyEvent e ) {
	}
	
	/**
	* Method called when a key is released. Not actually used, just required by the KeyListener interface.
	*/
	public void keyReleased( KeyEvent e ) {}

	/**
	* If a picture is selected, unselect it.
	*/
	public void toolExit() {
		if ( hasSelected() ) {
			deselectAll();
		}
		super.toolExit();
	}
	
	/**
	* If a picture is selected, unselect it.
	*/
	public void toolEnter() {
		super.toolEnter();
		if ( hasSelected() ) {
			deselectAll();
		}
	}

	/**
	* Changes the selection area start point.
	*/	
	public void setStartPoint( Point2D p ) {
		startPoint = p;
	}
	
	/**
	* Changes the selection area end point, repainting it.
	*/	
	public void setEndPoint( Point2D p ) {
		repaintSelectionArea();
		
		endPoint = p;
		
		Graphics2D g = (Graphics2D)canvas.getGraphics();
		float[] dashPattern = { 7, 3 };
		Stroke oldStroke = g.getStroke();
		g.setStroke( new BasicStroke( 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10, dashPattern, 5 ) );
		selectionArea = createRectangle( startPoint, endPoint );
		g.draw( selectionArea );
		g.setStroke( oldStroke );
	}
	
	/**
	* Selects all the figures inside the rectangle defined by the two given points.
	*/
	public void selectArea( Point2D p1, Point2D p2 ) {
		Iterator i = canvas.getFigures();
		Rectangle2D bounds = createRectangle( p1, p2 );
		while ( i.hasNext() ) {
			Figure f = (Figure)i.next();
			if ( bounds.contains( f.getLocation() ) ) {
				select( f );
			}
		}
	}
	
	/**
	* Move the selected figures (actually translades it).
	*/
	public void moveSelected( double x, double y ) {
		ListIterator i = selectedFigures.listIterator();
		while ( i.hasNext() ) {
			Figure figure = (Figure)i.next();
			figure.moveLocation( referencePoint, x, y );
		}
	}
	
	/**
	* Selects all drawingPanel's figures.
	*/
	public void selectAll() {
		Iterator i = canvas.getFigures();
		while ( i.hasNext() ) {
			Figure f = (Figure)i.next();
			f.setSelected( true );
			selectedFigures.add( f );
		}
	}
	
	/**
	* Creates the rectangle that represents the selection area. It's used at setEndPoint().
	*/
	protected Rectangle2D createRectangle( Point2D p1, Point2D p2 ) {
		double maxX = Math.max( p1.getX(), p2.getX() );
		double maxY = Math.max( p1.getY(), p2.getY() );
		double minX = Math.min( p1.getX(), p2.getX() );
		double minY = Math.min( p1.getY(), p2.getY() );
		Rectangle2D.Double bounds = new Rectangle2D.Double( minX, minY, maxX - minX, maxY - minY );
		return bounds;
	}
	
	/**
	* Repaints the selection area (if necessary).
	*/
	protected void repaintSelectionArea() {
		if ( startPoint == null || endPoint == null ) {
			return;
		}
		
		int extraSpace = 1;
		Rectangle2D bounds = createRectangle( startPoint, endPoint );
		canvas.repaint();
		// canvas.repaint(  (int)bounds.getX() - extraSpace,  (int)bounds.getY() - extraSpace,  (int)bounds.getWidth() + extraSpace, (int)bounds.getHeight() + extraSpace );
	}
}
