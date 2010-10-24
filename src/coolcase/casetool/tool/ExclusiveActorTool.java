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
public class ExclusiveActorTool extends ActorTool {

	public ExclusiveActorTool( DrawingPanel canvas ) {
		super( canvas );
	}
	
	/**
	* Cria a figura que representa o ator
	*/
	protected Figure newFigure( Point2D p, Figure f ) {
		ActorFigure figure = new ActorFigure( "Exclusive" );
		figure.setLocation( p );
		return figure;
	}
}
