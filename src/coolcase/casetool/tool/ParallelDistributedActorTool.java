package coolcase.casetool.tool;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

import coolcase.veryhot.*;
import coolcase.veryhot.tool.*;

import coolcase.casetool.*;
import coolcase.casetool.figure.*;

import coolcase.uml.*;

/**
* Ferramenta manipuladora de atores
*/
public class ParallelDistributedActorTool extends ActorTool {

	public ParallelDistributedActorTool( DrawingPanel canvas ) {
		super( canvas );
	}
	
	/**
	* Cria a figura que representa o ator
	*/
	protected Figure newFigure( Point2D p, Figure f ) {
		Sistema.logger.info( "Creating actor at " + p.getX() + "," + p.getY() );
		ActorFigure figure = new ActorFigure( "Paral.Dist." );
		figure.setLocation( p );
		Actor actor = figure.getActor();
		actor.setDistributed( true );
		actor.setParallel( true );
		return figure;
	}
}
