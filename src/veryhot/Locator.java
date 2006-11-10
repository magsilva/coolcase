package coolcase.veryhot;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
* A screen position is denoted by this class. Besides this, a object reference
* is also saved. This usually keeps the reference to the Figure staying at this
* location.
*
* In the framework, it's standart the use of Point2D. The Locator is used just in
* LineFigures.
*/
public class Locator extends Point2D implements Comparable, Comparator {
	
	/**
	* Figure this locator has.
	*/
	protected Figure figure = null;

	/**
	* The X position.
	*/
	protected double x;

	/**
	* The Y position.
	*/
	protected double y;

	/**
	* The constructor. THis one doesn't save a reference to a figure, just the straight positions.
	*/
	public Locator( double x, double y ) {
		this( x, y, null );
	}

	/**
	* The constructor. THis one doesn't save a reference to a figure, just the straight positions. 
	* It's like Locator( double x, double y ), but a bit easier to use.
	*/
	public Locator( Point2D p ) {
		this( p, null );
	}

	/**
	* Local constructor, besides the point there is the Figure that will be holded at that point.
	*/
	public Locator( double x, double y, Figure f ) {
		this( new Point2D.Double( x, y ), f );
	}


	/**
	* The real, full feature, constructor.
	*/
	public Locator( Point2D p, Figure f ) {
		x = p.getX();
		y = p.getY();
		figure = f;
	}

	/**
	* Returns the figure being holded.
	*/
	public Figure getFigure() {
		return figure;
	}
	
	/**
	* Sets the figure being holded at the point.
	*/
	public void setFigure( Figure f ) {
		figure = f;
	}

	/**
	* Compares the point with other object.
	*/
	public int compareTo( Object o ) {
		try {
			Point2D p = (Point2D)o;
			if ( p.getX() == x && p.getY() == y ) {
				return 0;
			} else {
				return 1;
			}
		} catch ( ClassCastException e ) {
			return 1;
		}
	}

	/**
	* Compares a point with another object.
	*/
	public int compare( Object o1, Object o2 ) {
		try {
			Point2D p1 = (Point2D)o1;
			Point2D p2 = (Point2D)o2;
			if ( p1.getX() == p2.getX() && p1.getY() == p2.getY() ) {
				return 0;
			} else {
				return 1;
			}
		} catch ( ClassCastException e ) {
			return 1;
		}
	}
	
	/**
	* Checks if the Locator is equal to the object.
	*/
	public boolean equals( Object o ) {
		try {
			Locator l = (Locator)o;
			if ( l.getX() == x && l.getY() == y ) {
				return true;
			} else {
				return false;
			}
		} catch ( ClassCastException e ) {
			return false;
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX( double x ) {
		this.x = x;
	}

	public void setY( double y ) {
		this.y = y;
	}

	public void setLocation( double x, double y ) {
		setX( x );
		setY( y );
	}
	
	public void setLocation( Point2D p ) {
		setX( p.getX() );
		setY( p.getY() );
	}

	public Point2D getLocation() {
		return new Point2D.Double( x, y );		
	}
	
	public String toString() {
		String result = "(" + x + "," + y + "," + figure + ")";
		return result;
	}
}
