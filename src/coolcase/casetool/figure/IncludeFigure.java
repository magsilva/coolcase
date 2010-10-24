package coolcase.casetool.figure;


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import coolcase.veryhot.*;


/**
* Represents the include relationship. Means that an instance of the use case
* will contain the behaviour of other use case. This behaviour is included at
* the location specified in the use case.
*/
public class IncludeFigure extends RelationshipFigure {

	//tamanho da seta na ponta da linha
	public static final double ARROW_SIZE = 15;

	public IncludeFigure( Point2D p, Figure f ) {
		super(p, f);
	}

	public BasicStroke getLineStroke() {
		float[] dashPattern = { 20, 5 };
		return new BasicStroke(8, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10, dashPattern, 0);
    
	}

	/**
	* The generalization is represented by a solid line with a closed, hollow arrow head
	* pointing at the parent use case.
	*/
	public Polygon getPolygon() {
		//implementar a seta aqui
		int i = 1;
		int npoints = points.length + (points.length - 2);
		int xpoints[] = new int[npoints];
		int ypoints[] = new int[npoints];

		// takes the line sizes using Pitagoras Theorem
		int figureSize = (int)( Math.sqrt( Math.pow(points[0].x - points[1].x,2) + Math.pow( points[0].y - points[1].y,2 ) ) );

		// creates the figure-line intersection point
		Point intersection = new Point( (int)points[1].getX(), (int)points[1].getY() );

		// cria o vetor diretor da reta e o normaliza
		Vector arrow = new Vector( points[1].x - points[0].x, points[1].y - points[0].y );
		arrow = arrow.adjustPattern();

		//aplica o metodo do meio intervalo para encontrar o vetor diretor da linha
		for ( int increment = figureSize / 2; increment > 0; increment /= 2 ) {
			if ( getEndFigure().contains(intersection) ) {
				figureSize -= increment;
			} else {
				figureSize += increment;
			}
			intersection.setLocation( (int)( arrow.x * figureSize ) + points[0].x, (int)( arrow.y * figureSize ) + points[0].y );
		}

		xpoints[0] = (int)intersection.getX();
		ypoints[0] = (int)intersection.getY();
		for ( ; i < points.length; i++ ) {
			xpoints[i] = points[i].x;
			ypoints[i] = points[i].y;
		}
		for ( int j = 2; i < npoints; i++, j++ ) {
			xpoints[i] = points[points.length-j].x;
			ypoints[i] = points[points.length-j].y;
		}
		polygon = new Polygon( xpoints, ypoints, npoints );
		return polygon;
	}
}
