package coolcase.veryhot;

import java.awt.*;
import java.util.*;
import java.awt.geom.*;

/**
* Line figure. It's composed of Locators. The figures of each Locator has the line added as observer.
* Any event such as dimension or location change, dipose, are notified to the line, allowing it to keep in
* sync with the figures.
*
* A Line figure must have at least two locators. If a figure, referenced at a begin locator or last
* locator, is disposed, the line disposes itself.
*/
public class LineFigure extends VectorFigure implements Observer {

	/**
	* The locators that defines the line.
	*/
	protected LinkedList points;

	/**
	* Used to represent the line to be drawn at the screen. It's also used in some methods (getBounds,
	* getGeneralPath).
	*/
	protected GeneralPath generalPath;

	/**
	* When comparing the distance from a point to the line, this is the max error permited.
	*/
	protected double tolerance = 5;
	
	/**
	* Line constructor. Just the things needed to create a locator.
	*/
	public LineFigure( Locator begin ) {
		points = new LinkedList();
		lineTo( begin );
	}

	/**
	* Move the last locator to the given coordinate. If a new figure is set into the given
	* locator, the old one is dismissed. Please note: a null reference _does not_ change the figure
	* referenced at WorkingLocator.
	*/
	public void moveTo( Locator l ) {
		// First of all, we need a valid locator.
		if ( l == null ) {
			throw new IllegalArgumentException();
		}
		
		Locator workingLocator = (Locator)points.getLast();
		
		if ( l.getFigure() != null ) {
			// Precaution to not delete yourself from a figure referenced twice or more in the line.
			if ( contains( workingLocator.getFigure() ) == 1 ) {
				workingLocator.getFigure().deleteObserver( this );
			}
			workingLocator.setFigure( l.getFigure() );
			workingLocator.getFigure().addObserver( this );
		}
		ObserverArgument oa = prepareChange( "Dimension" );
		workingLocator.setLocation( l );
		commitChange( oa );		
	}

	/**
	* Adds a point to the line's end. This is usually used just when drawing the line the very first time.
	*/
	public void lineTo( Locator l ) {
		// We cannot add a locator if none has yet been added or the locator is invalid.
		if ( l == null ) {
			throw new IllegalArgumentException();
		}
		add( l );
	}
	
	/**
	* Checks if the line contains the point 'p'. 
	*/
	public boolean contains( Locator l ) {
		for ( int i = 0; i < segmentCount(); i++ ) {
			if ( insideSegment( i, l ) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	* Removes the given point (if it exists at all). The first and last points cannot be removed.
	*/
	public void remove( Locator l ) {
		ListIterator i = points.listIterator();
		while ( i.hasNext() ) {
			Locator current = (Locator)i.next();
			if ( current.equals( l ) ) {
				ObserverArgument oa = prepareChange( "Dimension" );
				points.remove( i );
				commitChange( oa );
			}
		}
		// Check if the line is valid. If not, dispose yourself.
		if ( isValid() == false ) {
			dispose();
		}
	}

	/**
	* Adds the giver locator to the line (if it's in the line segment).
	*/
	protected Locator addIntoLine( Locator l ) {
		// If the point doesn't belong to the line., do not add locator and throw exception.
		if ( contains( l ) == false ) {
			throw new IllegalArgumentException();
		}
		
		for ( int i = 0; i < segmentCount(); i++ ) {
			if ( insideSegment( i, l ) ) {
				// Extra care needed to not include a point before the first or after the last one.
				if ( i == 0 ) {
					return add( i + 1, l );
				} else {
					return add( i, l );
				}
			}
		}
		return null;
	}
	
	/**
	* Appends a point to the line.
	*/
	protected Locator add( Locator l ) {
		return add( size(), l );
	}
	
	/**
	* Adds the giver locator to the line at the index choosen (if valid).
	*/
	protected Locator add( int i, Locator l  ) {
		if ( l == null || i > size() ) {
			throw new IllegalArgumentException();
		}
		
		ObserverArgument oa = prepareChange( "Dimension" );
		Locator clonedLocator = (Locator)l.clone();
		points.add( i, clonedLocator );
		commitChange( oa );
		if ( clonedLocator.getFigure() != null ) {
			clonedLocator.getFigure().addObserver( this );
		}
		return clonedLocator;
	}
	
	/**
	* Returns the total of points in the line.
	*/
	public int size() {
		return points.size();
	}
	
	/**
	* Paints the line. As you can see, the only thing a specialized line implementation
	* shoul do is the getPolygon().
	*/
	public void paint( Graphics2D g ) {
		super.paint( g );
		if ( generalPath == null ) {
			reloadGeneralPath();
		}
		g.draw( generalPath );
	}

	/**
	* Method needed by the interface Observer. When you are notified by an object, this is the
	* method called. A line, in our case, must observe just the location, zoom and dimension.
	*/
	public void update( Observable source, Object o ) {
		// First of all, the observable object must be an Figure.
		if ( source instanceof Figure && o instanceof ObserverArgument ) {
			ObserverArgument arg = (ObserverArgument)o;
			Figure f = (Figure)source;

			// The figure has changed its location, change the point that has a reference to her.
			if ( arg.name.equals( "Location" ) ) {
				ListIterator i = points.listIterator();
				while ( i.hasNext() ) {
					Locator l = (Locator)i.next();
					if ( l.getFigure() == f ) {
						ObserverArgument oa = prepareChange( "Dimension" );
						Point2D connectionPoint = f.getConnectionPoint();
						l.setLocation( connectionPoint );
						commitChange( oa );
					}
				}
				return;
			}

			// Ups, the figure will be deleted (or dislike beloging to a line). If the figure
			// belongs to the first of last line's points, dispose the line too. Otherwise, just
			// removes the points that has a reference to it.
			if ( arg.name.equals( "Dispose" ) ) {
				Locator first = (Locator)points.getFirst();
				Locator last = (Locator)points.getLast();
				if ( f == first.getFigure() || f == last.getFigure() ) {
					dispose();
				} else {
					ListIterator i = points.listIterator();
					while ( i.hasNext() ) {
						Locator l = (Locator)i.next();
						if ( l.getFigure() == f ) {
							remove( l );
						}
					}
				}
				return;
			}
		}
	}

	/**
	* If the line has been deleted, we have to advise the pictures about that.
	*/
	public void dispose() {
		super.dispose();
		points = null;
		generalPath = null;
	}

	/**
	* Returns the first line point.
	*/
	public Point2D getLocation() {
		Locator l = (Locator)points.get( 0 );
		return l.getLocation();
	}

	/**
	* The same as move line. This setLocation is against the first line locator.
	*/
	public void setLocation( Point2D p ) {
		Locator first = (Locator)points.getFirst();
		ListIterator i = points.listIterator();
		ObserverArgument oa = prepareChange( "Location" );
		while ( i.hasNext() ) {
			Locator l = (Locator)i.next();
			if ( l.getFigure() == null ) {
				l.setLocation( new Point2D.Double( getX() + p.getX() - first.getX(), getY() + p.getY() - first.getY() ) );
			}
		}
		commitChange( oa );
	}		

	/**
	* Returns the first point X coordinate.
	*/
	public double getX() {
		Locator l = (Locator)points.get( 0 );
		return l.getX();
	}

	/**
	* Returns the first point Y coordinate.
	*/
	public double getY() {
		Locator l = (Locator)points.get( 0 );
		return l.getY();
	}

	/**
	* Returns the line's bounds.
	*/
	public Rectangle2D getBounds2D() {
		if ( generalPath == null) {
			reloadGeneralPath();
		}
		return generalPath.getBounds2D();
	}

	/**
	* Returns the line's bounds.
	*/
	public Rectangle getBounds() {
		if ( generalPath == null) {
			reloadGeneralPath();
		}
		return generalPath.getBounds();
	}

	/**
	* Returns the line's path iterator.
	*/
	public PathIterator getPathIterator( AffineTransform at ) {
		if ( generalPath == null) {
			reloadGeneralPath();
		}
		return generalPath.getPathIterator( at );
	}
	
	/**
	* Reloads the line path.
	*/
	public void reloadGeneralPath() {
		// The first point.
		generalPath = new GeneralPath();
		Point2D current = getNewConnectionPoint( getLocation(), -1 );
		System.out.println( "here" );
		generalPath.moveTo( (float)current.getX(), (float)current.getY() );
		
		ListIterator i = points.listIterator( 1 );
		while ( i.hasNext() ) {
			Locator l = (Locator)i.next();
			current = getNewConnectionPoint( l, 1 );
		System.out.println( "there" );
			generalPath.lineTo( (float)current.getX(), (float)current.getY() );
		}
	}		
	
	/**
	* Prepare figure for upcoming update (because of observer events).
	*/
	public void commitChange( ObserverArgument oa ) {
		if ( isValid() ) {
			generalPath = null;
			super.commitChange( oa );
		}
	}
	
	/**
	* TODO: Review and fix code, LineTool is throwing exceptions because of it.
	*
	* Returns the exact point the line cross the given figure. This point is solved by basing on
	* the locator taken as argument.
	*
	* Direction is 1 if the sourceLocator is before the targetLocator, otherwise is -1.
	* The direction can be:
	* -1 - right to left
	* 1 - left to right
	*/
	protected Point2D getNewConnectionPoint( Point2D targetPoint, int direction ) {
	
		// Fist of all, target must be present at the line.
		if ( contains( targetPoint ) == false ) {
			throw new IllegalArgumentException( "targetPoint does not exist" );
		}

		Locator sourceLocator = null;
		Locator targetLocator = null;
		ListIterator i = points.listIterator();
		while ( i.hasNext() &&  targetLocator != null ) {
			Locator l = (Locator)i.next();
			// Target found, now let's define the source and target locators.
			if ( l.equals( targetPoint ) ) {
				if ( direction == 1 ) {
					// If we are looking left to right and the previous index is 0, meaning that our
					// targetPoint equals the first line's locator, we have an illegal argument.
					if ( i.previousIndex() == 0 ) {
						throw new IllegalArgumentException( "---------------------------" );
					}
					targetLocator = l;
					i.previous();
					sourceLocator = (Locator)i.previous(); 
				} else {
					// If we are looking right  to left and there isn't a next index, meaning that our
					// targetPoint equals the last line's locator, we have an illegal argument.
					i.previous();
					if ( i.hasNext() == false ) {
						throw new IllegalArgumentException( "sfjalsdjlfkdsaflkjfsdak" );
					} else {
						i.next();
						targetLocator = l;
						sourceLocator = (Locator)i.next();
					}
				}
			}
		}
		
		// If there isn't a figure at the point, the connection point to that target will be the target.
		if ( targetLocator.getFigure() == null ) {
			return targetLocator.getLocation();
		}
		
		// Gets the opposite catet and hipo, gets the angle
		double deltaX = targetLocator.getX() - sourceLocator.getX();
		double deltaY = targetLocator.getY() - sourceLocator.getY();
		double tan = deltaY / deltaX;
		double distance = 0;
		double result = 0;
		
		if ( tan > 0 ) {
			distance = targetLocator.getFigure().getWidth() / 2;
			result = - ( ( distance / ( tan * targetLocator.getY() ) ) / tan );
			return new Point2D.Double( targetLocator.getX() - distance, targetLocator.getY() - result );
		} else {
			distance = targetLocator.getFigure().getHeight() / 2;
			result = - ( ( distance * tan ) - targetLocator.getX() );
			return new Point2D.Double( targetLocator.getX() - result, targetLocator.getY() - distance );
		}
	}
	
	/**
	* Moves the locators that don't have a figure associated.
	*/
	public void moveLocation( Point2D referencePoint, double x, double y ) {
		// First tries to find a suitable point.
		ListIterator i = points.listIterator();
		Locator target = null;
		while ( i.hasNext() ) {
			Locator current = (Locator)i.next();
			if ( current.distance( referencePoint ) < tolerance ) {
				target = current;
			}
		}
		
		if ( target != null ) {
			ObserverArgument oa = prepareChange( "Dimension" );
			target.setLocation( target.getX() + x, target.getY() + y );
			commitChange( oa );
		} else {
			target = new Locator( referencePoint );
			try {
				ObserverArgument oa = prepareChange( "Dimension" );
				target = addIntoLine( target );
				target.setLocation( target.getX() + x, target.getY() + y );
				commitChange( oa );
			} catch ( IllegalArgumentException e ) {
			}
		}
	}
	
	/**
	* Counts how many references to the given figure the line has.
	*/
	public int contains( Figure f ) {
		int result = 0;
		
		if ( f == null ) {
			return 0;
		}
		
		for ( int i = 0; i < size(); i++ ) {
			Locator l = (Locator)points.get( i );
			if ( l.getFigure() == f ) {
				result++;
			}
		}
		return result;
	}
	
	/**
	* Just for info, returns the points as String. Ex: (0,0) (23,11).
	*/
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for ( int i = 0; i < size(); i++ ) {
			Locator l = (Locator)points.get( i );
			sb.append( l.toString() );
			sb.append( " " );
		}
		return sb.toString();
	}
	
	/**
	* Checks if the line current state is a valid one.
	*/
	public boolean isValid() {
		if ( size() >= 2 ) {
			return true;
		}
		return false;
	}
	
	/**
	* Returns the line's segment count.
	*/
	public int segmentCount() {
		return ( size() - 1 );
	}
	
	/**
	* Checks if the locator is inside the specified line segment.
	*/
	public boolean insideSegment( int segmentNumber, Locator l ) {
		try {
			Line2D.Double line = new Line2D.Double( (Locator)points.get( segmentNumber ), (Locator)points.get( segmentNumber + 1 ) );
			if ( line.ptSegDist( l ) <= tolerance ) {
				return true;
			} else {
				return false;
			}
		} catch ( IndexOutOfBoundsException e ) {
			return false;
		}
	}
	
	/**
	* Prepare the object for a change. If the figure is invallid, do nothing.
	*/
	protected ObserverArgument prepareChange( String command ) {
		if ( isValid() ) {
			return super.prepareChange( command );
		} else {
			return null;
		}
	}
	
	/**
	* This is the old code for getting the connection point. It works, but it's slower and less precise.
	*
	protected Point2D getConnectionPoint( Point2D targetPoint ) {
		// Fist of all, target must be present at the line.
		if ( contains( targetPoint ) == false ) {
			throw new IllegalArgumentException();
		}

		// Direction is 1 if the sourceLocator is before the targetLocator, otherwise is -1.
		int direction = 0;
		Locator sourceLocator = null;
		Locator targetLocator = null;
		for ( int i = 0; i < points.size() && targetLocator != null; i++ ) {
			Locator l = (Locator)points.get( i );
			if ( l.equals( targetPoint ) ) {
				targetLocator = l;
				if ( i == 0 ) {
					sourceLocator = (Locator)points.get( i - 1 );
					direction = -1;
				} else {
					sourceLocator = (Locator)points.get( i + 1 );
					direction = 1;
				}
			}
		}
		
		// If there isn't a figure at the point, the connection point to that target will be the target.
		if ( targetLocator.getFigure() == null ) {
			return targetLocator.getLocation();
		}



		//pega o tamanho da linha, atraves de pitagoras
        double figureSize = Math.sqrt( Math.pow( sourceLocator.getX() - targetLocator.getX(), 2 ) + Math.pow( sourceLocator.getY() - targetLocator.getY() ) );

		// cria o ponto de interseccao da linha com a figura
		Point2D.Double intersect = new Point2D.Double( targetLocator.getX(), targetLocator.getY() );
		
		// cria o vetor diretor da reta e o normaliza
        Vector arrow = new Vector(points[1].x - points[0].x, points[1].y - 
          points[0].y);
        arrow = arrow.adjustPattern();
        
        //aplica o metodo do meio intervalo para encontrar o vetor diretor
        //da linha
	for(int increment=figureSize/2; increment>0; increment /= 2){
           if(end.contains(intersect))
                figureSize -= increment;
            else
                figureSize += increment;

	   intersect.setLocation((int)(arrow.x * figureSize) + points[0].x,
	     (int)(arrow.y * figureSize) + points[0].y);
        }

	//monta a seta!
	int[] px = new int[3];
	int[] py = new int[3];
	//ponta da seta
        px[0] = (int)intersect.getX();
	py[0] = (int)intersect.getY();
        //ponto esquerdo
        px[1] = (int)(arrow.rotate(160).x*ARROW_SIZE) + px[0];
	py[1] = (int)(arrow.rotate(160).y*ARROW_SIZE) + py[0];
        //ponto direito
        px[2] = (int)(arrow.rotate(-160).x*ARROW_SIZE) + px[0];
	py[2] = (int)(arrow.rotate(-160).y*ARROW_SIZE) + py[0];

	//retorna o poligono
        return new Polygon(px,py,3);		
	}
	*/
	
}	
