package coolcase.casetool.tool;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

import coolcase.veryhot.*;
import coolcase.veryhot.tool.*;

import coolcase.casetool.*;
import coolcase.casetool.figure.*;

/**
* Ferramenta manipuladora de atores
*/
public class PlainActorTool extends ActorTool {

	public PlainActorTool( DrawingPanel canvas ) {
		super( canvas );
	}
	
	/**
	* Cria a figura que representa o ator
	*/
	protected Figure newFigure( Point2D p, Figure f ) {
		Sistema.logger.info( "Creating actor at " + p.getX() + "," + p.getY() );
		ActorFigure figure = new ActorFigure( "teste" );
		figure.setLocation( p );
		return figure;
	}
}
