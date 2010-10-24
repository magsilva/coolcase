package coolcase.veryhot;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.print.*;
import java.util.*;
import javax.swing.*;

import coolcase.veryhot.tool.Tool;


/*
* This is the root of the framework. The ideia is that the figures stay at this JPanel. The main
* feature of the DrawingPanel is its tools support.
*/
public class DrawingPanel extends JPanel implements Observer, Printable {

	/**
	* The tool being used.
	*/
	protected Tool tool;

	/**
	* The list that keeps the figures in the DrawingPanel. This _has_ to beimproved to a somehow better
	* structure. One ideia is keep a BTree of the figures sorted by figure draw priority (a TreeMap) and
	* a BTreeSet sorted by figura name (TreeSet) in just one new object (a figuresCollection, for
	* example).
	*
	* Currently it's used the figures's OID (or whatever is returned with its toString()'s method) as key.
	*/
	protected TreeMap figures;

	public DrawingPanel() {
		this( 200.0, 200.0 );
	}
	
	/**
	* DrawingPanel constructor. This is the simplest one, do not draw the
	* grid and sets its witdh and height to one.
	*/
	public DrawingPanel( double pw, double ph ) {
		figures = new TreeMap();
		setBackground( Color.white );
		setRequestFocusEnabled( true );
		setPreferredSize( new Dimension( (int)pw, (int)ph ) );
	}

	/**
	* THis method is called when switching tool. Exposing it here allow children classes to
	* overrule this method, disabling any aditional interface it may uses.
	*/
	protected void disableTool( Tool tool ) {
		// We have do know which interfaces the tool implements (only those interfaces
		// belonging to the Java SDK).
		if ( tool instanceof KeyListener ) {
			removeKeyListener( (KeyListener)tool );
		}
		if ( tool instanceof MouseListener) {
			removeMouseListener( (MouseListener)tool );
		}
		if ( tool instanceof MouseMotionListener ) {
			removeMouseMotionListener( (MouseMotionListener)tool );
		}
	}
	
	/**
	* THis method is called when switching tool. Exposing it here allow children classes to
	* overrule this method, enabling any aditional interface it may uses.
	*/
	protected void enableTool( Tool t ) {
		if ( t instanceof KeyListener ) {
			addKeyListener( (KeyListener)t );
		}
		if ( t instanceof MouseListener ) {
			addMouseListener( (MouseListener)t );
		}
		if ( t instanceof MouseMotionListener ) {
			addMouseMotionListener( (MouseMotionListener)t );
		}
	}
	
	/**
	* Sets the tool being used so that it can receive the events. First we need to remove the listeners
	* from the previous tool being used. If there is no tool being used, we don't have to remove the
	* listeners (of course). As you can see in the source, we always call <tool>.toolExit() when
	* disabling a tool and <tool>.toolEnter() when enabling. This is a convention that _must_ be
	* followed.
	*
	* If you need to enable/disable any aditional interface, change the <enable/disable>Tool() method.
	*/
	public void setTool( Tool t ) {
		// Doesn't have to setup anything if the tool is already selected.
		if ( t == tool ) {
			return;
		}

		if ( tool != null ) {
			tool.toolExit();
			disableTool( tool );
		}
		tool = t;
		if ( tool != null ) {
			enableTool( tool );
			tool.toolEnter();
		}
	}

	/**
	* Returns the bool being used.
	*/
	public Tool getTool() {
		return tool;
	}

	/**
	* Returns the first figure at the point 'p'.
	*/
	public Figure getFigureAt( Point2D p ) {
		return getFigureAt( p, 0 );
	}

	/**
	* Returns the jth figure at the point 'p'.
	*/
	public Figure getFigureAt( Point2D p, int j ) {
		int k = 0;
		Iterator i = figures.values().iterator();
		Figure f = null;
		
		while ( i.hasNext() ) {
			f = ( Figure )i.next();
			if ( f.contains( p ) ) {
				if ( k == j ) {
					return f;
				}
				k++;
			}
		}
		return null;
	}

	/**
	* Returns the ammount of figures at a given point.
	*/
	public int getFigureCountAt( Point2D p ) {
		int k = 0;
		Iterator i = figures.values().iterator();
		Figure f = null;
		
		while ( i.hasNext() ) {
			f = ( Figure )i.next();
			if ( f.contains( p ) ) {
				k++;
			}
		}
		return k;
	}
	
	public ArrayList getFiguresAt( Point2D p ) {
		Iterator i = figures.values().iterator();
		ArrayList figures = new ArrayList();
		
		while ( i.hasNext() ) {
			Figure f = (Figure)i.next();
			if ( f.contains( p ) ) {
				figures.add( f );
			}
		}
		return figures;
	}
	
	public Iterator getFigures() {
		return figures.values().iterator();
	}
	
	/**
	* Adds a figure to the DrawingPanel. Uses its Java OID as key.
	*/
	public void addFigure( Figure f ) {
		figures.put( f.toString(), f );
		f.addObserver( this );
		repaint( (Rectangle)f.getBounds() );
	}

	/**
	* Removes the given figure, clears its observers list.
	*/
	public Figure removeFigure( Figure f ) {
		figures.remove( f.toString() );
		f.deleteObserver( this );
		return f;
	}

	/**
	* Used to draw something at drawing panel's background.
	*/
	protected void paintBackground( Graphics2D g ) {
	}

	/**
	* Paints all the components.
	*/
	protected void paintComponent( Graphics g ) {
		super.paintComponent( g );
		Graphics2D g2 = (Graphics2D)g;
		// paintBackground( g2 );

		// Draw the figures (if any).
		if ( figures != null ) {
			Iterator i = figures.values().iterator();
			while ( i.hasNext() ) {
				Figure f = (Figure)i.next();
				if ( f.intersects( g2.getClipBounds() ) ) {
					f.paint( g2 );
				}
			}
		}
	}

	/**
	* We implements the interface Observer, remember? ;)
	* We use this mainly to advise the DrawingPanel that we need to repaint somewhere. Here
	* the miracle happens.
	*/
	public void update( Observable source, Object o ) {
		int extraSpace = 0;
		if ( source instanceof Figure && o instanceof ObserverArgument ) {
			try {
			
			ObserverArgument arg = (ObserverArgument)o;
			Figure f = (Figure)source;

			if ( arg.name.equals( "Selection" ) ) {
				System.out.println("Updating figure - Selection");
				Rectangle2D bounds = (Rectangle2D)f.getBounds2D();
				repaint( (int)bounds.getX() - extraSpace, (int)bounds.getY() - extraSpace, (int)bounds.getWidth() + extraSpace, (int)bounds.getHeight()  + extraSpace);
				return;
			}
			
			if ( arg.name.equals( "Location" ) ) {
				System.out.println("Updating figure - Location");
				Rectangle2D bounds = (Rectangle2D)arg.arguments.get( 0 );
				bounds = bounds.createUnion( (Rectangle2D)f.getBounds2D() );
				repaint( (int)bounds.getX() - extraSpace, (int)bounds.getY() - extraSpace, (int)bounds.getWidth() + extraSpace, (int)bounds.getHeight()  + extraSpace);
				return;
			}

			if ( arg.name.equals( "Dimension" ) ) {
				System.out.println("Updating figure - Dimension");
				Rectangle2D bounds = (Rectangle2D)arg.arguments.get( 0 );
				bounds = bounds.createUnion( f.getBounds2D() );
				repaint( (int)bounds.getX() - extraSpace, (int)bounds.getY() - extraSpace, (int)bounds.getWidth() + extraSpace, (int)bounds.getHeight()  + extraSpace);
				return;
			}

			if ( arg.name.equals( "Dispose" ) ) {
				System.out.println("Updating figure - Dispose");
				Rectangle2D bounds = (Rectangle2D)f.getBounds2D();
				removeFigure( f );
				repaint( (int)bounds.getX() - extraSpace, (int)bounds.getY() - extraSpace, (int)bounds.getWidth() + extraSpace, (int)bounds.getHeight()  + extraSpace);
				return;
			}

			} catch ( Exception e ) {
				System.out.println( e.getMessage() );
				e.printStackTrace();
			}
		}
	}

	public int print( Graphics g, PageFormat format, int numPage ) {
		return 0;
	}
}
