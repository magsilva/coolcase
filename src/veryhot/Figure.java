package coolcase.veryhot;

import java.awt.*;
import java.util.*;
import java.awt.geom.*;

/**
* Class that defines the figures usable in the toolkit.
*/
public abstract class Figure extends Observable implements Shape {
	
	/**
	* Sets a picture as selected or not. THis matters mainly when painting and cut/pasteing by
	* now, but some tools may use this attribute to select which object it must apply something.
	*/
	protected boolean selected;

	/**
	* Place where the figure is located.
	*/
	protected Point2D location;

	/**
	* The figure width.
	*/
	protected double width;
	
	/**
	* The figure height.
	*/
	protected double height;
	
	/**
	* A figure always has a location. Default is 0,0.
	*/
	public Figure() {
		this( new Point2D.Double( 0.0, 0.0 ) );
	}

	/**
	* Creates the figure and sets its location.
	*/
	public Figure( Point2D p ) {
		super();
		location = p;
		width = 0.0;
		height = 0.0;
	}

	/**
	* Rectangle representing the bounds of the figure.
	*/
	public Rectangle getBounds() {
		return new Rectangle( (int)location.getX(), (int)location.getY(), (int)width, (int)height );
	}
		
	/**
	* By convention, every figure is represented by a rectangle. And this method returns
	* this rectangle.
	*/
	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double( location.getX(), location.getY(), width, height );
	}
	
	/**
	* Check if the figure contains a point into the given coordinates.
	*/
	public boolean contains( double x, double y ) {
		return contains( new Point2D.Double( x, y ) );
	}

	/**
	* Checks if the figure contains the point 'p'.
	*/
	public boolean contains( Point2D p ) {
		return getBounds2D().contains( p );
	}
	
	/**
	* Check if the figure contains the rectangle with the given coordinates.
	*/
	public boolean contains( double x, double y, double w, double h ) {
		return getBounds2D().contains( x, y, w, h );
	}
	
	/**
	* Check if the figure contains the rectangle.
	*/
	public boolean contains( Rectangle2D r ) {
		return getBounds2D().contains( r );
	}
	
	/**
	* Generates the path itarator.
	*/
	public abstract PathIterator getPathIterator( AffineTransform at );
	
	/**
	* Generates the path itarator.
	*/
	public PathIterator getPathIterator( AffineTransform at, double flatness ) {
		return new FlatteningPathIterator( getPathIterator( at ), flatness );
	}
	
	/**
	* Checks if the given rectangles coordinates intersects the figure bounds.
	*/
	public boolean intersects( double x, double y, double w, double h ) {
		Rectangle2D.Double r = new Rectangle2D.Double( x, y, w, h );
		return intersects( r );
	}

	/**
	* Checks if the given rectangles intersects the figure bounds.
	*/
	public boolean intersects( Rectangle2D r ) {
		return getBounds2D().intersects( r );
	}
	
	/**
	* Returns the point that represents the location of the figure (usually the
	* upper-left point of the rectangle that represents the figure).
	*/
	public Point2D getLocation() {
		return (Point2D)location.clone();
	}

	/**
	* Changes the figures location.
	*/
	public void setLocation( Point2D p ) {	
		if ( p == null ) {
			return;
		}
		if ( location.getX() != p.getX() || location.getY() != p.getY() ) {
			ObserverArgument oa = prepareChange( "Location" );
			location.setLocation( p.getX(), p.getY() );
			commitChange( oa );
		}
	}

	/**
	* Draws the figure in the Graphics2D 'g'.
	*/
	public abstract void paint( Graphics2D g );

	/**
	* Returns the locator to be used to connect to the figure. We must send the
	* figure request the connection because it may be a two-way relationship, one
	* may have to know the existence each other.
	*/
	public abstract Point2D getConnectionPoint();
	
	/**
	* Returns the X coordinate.
	*/
	public double getX() {
		return location.getX();
	}

	/**
	* Returns the Y coordinate.
	*/
	public double getY() {
		return location.getY();
	}
	
	/**
	* Sets the X coordinate of the figure.
	*/
	public void setX( double x ) {
		setLocation( new Point2D.Double( x, location.getY() ) );
	}
	
	/**
	* Sets the X coordinate of the figure.
	*/
	public void setY( double y ) {
		setLocation( new Point2D.Double( location.getX(), y ) );
	}

	/**
	* Set the figure selected. This is usually used to highlight it.
	*/
	public void setSelected( boolean flag ) {
		if ( selected != flag )	{
			selected = flag;
			commitChange( prepareChange( "Selection" ) );
		}
	}

	/**
	* Checks if the figure is selected.
	*/
	public boolean isSelected() {
		return selected;
	}

	/**
	* Unfortunatly, we cannot warranty taht finalize() will be executed when we want it to.
	* So, we must call this function when we don't need the object anymore.
	*/
	public void dispose() {
		commitChange( prepareChange( "Dispose" ) );
		deleteObservers();
	}
	
	/**
	* Gets the figure's width.
	*/
	public double getWidth() {
		return width;
	}
	
	/**
	* Gets the figure's height.
	*/
	public double getHeight() {
		return height;
	}

	/**
	* Change the figure's dimension. This one is better than setWidth,setHeight because just creates
	* one update event.
	*/
	public void setDimension( double w, double h ) {
		if ( width != w || height != h ) {
			ObserverArgument oa = prepareChange( "Dimension" );
			width = w;
			height = h;
			commitChange( oa );
		}
	}
		
	/**
	* Change the figure's location. This one is better than setX,setY because just creates
	* one update event.
	*/
	public void moveLocation( Point2D referencePoint, double x, double y ) {
		setLocation( new Point2D.Double( getX() + x, getY() + y ) );
	}
	
	/**
	* Called when the figure changes its state.
	*/
	protected void commitChange( ObserverArgument oa ) {
		if ( countObservers() > 0 ) {
			setChanged();
			notifyObservers( oa );
		}
	}
	
	/**
	* Prepare the observer argument for the given change type.
	*/
	protected ObserverArgument prepareChange( String changeType ) {

		ObserverArgument oa = new ObserverArgument( changeType );

		if ( changeType.equals( "Dimension" ) ) {
			Rectangle2D bounds = getBounds2D();
			oa.arguments.add( bounds );
			return oa;
		}
		
		if ( changeType.equals( "Location" ) ) {
			oa.arguments.add( getBounds2D() );
			return oa;
		}
		
		if ( changeType.equals( "Selection" ) ) {
			return oa;
		}

		if ( changeType.equals( "Dispose" ) ) {
			return oa;
		}
		
		return oa;
	}
}



