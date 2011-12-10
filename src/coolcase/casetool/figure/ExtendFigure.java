package coolcase.casetool.figure;

import java.awt.*;
import java.awt.event.*;

import coolcase.veryhot.Figure;

/**
* ExtendFigure represents the Extend relationship. This relationship
* indicates that an instance of the use case may be augmented (subject
* to specific conditions specified in the extension) by the behavior
* specified by the use case being extended. This behavior is inserted
* at the location defined by the extension point in B, referenced by this
* relationship.
*/
public class ExtendFigure extends RelationshipFigure {

	/**
	* Holds the extension points.
	*/
	private java.util.ArrayList ExtensionPoints = new java.util.ArrayList(0);
	
	public ExtendFigure( Point p, Figure f )
	{
		super( p, f );
		if ( !( f instanceof UseCaseFigure ) )
		{
			throw new IllegalArgumentException("You can only extend a use case");
		}
	}

	/**
	* Sets a new/modify an old extension point. The endFigure _must_ have that extension
	* point in order to add it to this relationship.
	*/
	public void setExtensionPoint( String ExtensionPointName )
	{
		if ( ( (UseCaseFigure)getEndFigure() ).hasExtensionPoint( ExtensionPointName ) )
		{
			if ( ExtensionPoints.contains( ExtensionPointName ) )
			{
				String ExtensionPoint = (String)ExtensionPoints.get( ExtensionPoints.indexOf( ExtensionPointName) );
				ExtensionPoint = ExtensionPointName;
			}
			else
			{
				ExtensionPoints.add( ExtensionPointName );
			}
		}
	}
	
	/**
	* Removes an extension point.
	*/
	public void removeExtensionPoint( String ExtensionPointName )
	{
		if ( ExtensionPoints.contains( ExtensionPointName ) )
		{
			ExtensionPoints.remove( ExtensionPoints.indexOf( ExtensionPointName) );
		}
	}


	/**
	* Its shown as a dashed arrow with an open arrow-head from the use case providing the
	* extension bo the base use case. The arrow is labeled with the keyword <<extend>>. The
	* relationship's condition is optionally presented close to the keyword.
	*/
	public Polygon getPolygon() {
		//implementar a seta aqui
		int i = 1;
		int npoints = points.length + (points.length - 2);
		int xpoints[] = new int[npoints];
		int ypoints[] = new int[npoints];

		// takes the line sizes using Pitagoras Theorem
		int figureSize = (int)(Math.sqrt(Math.pow(points[0].x - points[1].x,2) + Math.pow(points[0].y - points[1].y,2)));

		// creates the figure-line intersection point
		Point intersection = new Point((int)points[1].getX(), (int)points[1].getY());

		// cria o vetor diretor da reta e o normaliza
		Vector arrow = new Vector(points[1].x - points[0].x, points[1].y - points[0].y);
		arrow = arrow.adjustPattern();

		//aplica o metodo do meio intervalo para encontrar o vetor diretor da linha
		for (int increment = figureSize / 2; increment > 0; increment /= 2)
		{
			if ( getEndFigure().contains(intersection) )
			{
				figureSize -= increment;
			}
			else
			{
				figureSize += increment;
			}
			intersection.setLocation((int)(arrow.x * figureSize) + points[0].x, (int)(arrow.y * figureSize) + points[0].y);
		}

		xpoints[0] = (int)intersection.getX();
		ypoints[0] = (int)intersection.getY();
		for (; i < points.length; i++) {
			xpoints[i] = points[i].x;
			ypoints[i] = points[i].y;
		}
		for (int j=2; i < npoints; i++, j++) {
			xpoints[i] = points[points.length-j].x;
			ypoints[i] = points[points.length-j].y;
		}
		polygon = new Polygon(xpoints, ypoints, npoints);
		return polygon;
	}

	public BasicStroke getLineStroke()
	{
		float[] dashPattern = { 5, 10 };
		return new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1, dashPattern, 0);
	}
}
