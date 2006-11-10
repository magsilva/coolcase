package coolcase.casetool.figure;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import coolcase.veryhot.*;

/**
* This is a connecting line. The difference is that it's a binary
* relation.
*/
public class RelationshipFigure extends LineFigure {
	
	/**
	* Simple constructor.
	*/
	public RelationshipFigure( Locator begin ) {
		super( begin );
	}
	
	/**
	* Retorna a figura que está se relacionando
	*/
	public Figure getBeginFigure() {
		Locator l = (Locator)points.getFirst(); 
		return l.getFigure();
	}

	/**
	* Retorna a figura que está sendo relacionada
	*/
	public Figure getEndFigure() {
		Locator l = (Locator)points.getLast();
		return l.getFigure();
	}

	/**
	* Muda a figura que está se relacionando
	*/
	public void setBeginFigure( Figure begin ) {
		if ( begin == null ) {
			throw new IllegalArgumentException();
		}
		
		Locator l = (Locator)points.getFirst();
		l.setFigure( begin );
		
	}

	/**
	* Muda a figura que está sendo relacionada
	*/
	public void setEndFigure( Figure end ) {
		if ( end == null ) {
			throw new IllegalArgumentException();
		}

		Locator l = (Locator)points.getLast();
		l.setFigure( end );
	}
	
	
}
