package coolcase.casetool.tool;

/**
* Ferramenta manipuladora de caso de usos
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

import coolcase.veryhot.*;
import coolcase.veryhot.tool.*;

import coolcase.casetool.*;
import coolcase.casetool.figure.*;

import coolcase.uml.*;

public class DistributedUseCaseTool extends UseCaseTool {

	/**
	* Construtura da ferramenta
	*/
	public DistributedUseCaseTool( DrawingPanel canvas ) {
		super( canvas );
	}

	/**
	* Cria uma figura (um caso de uso)
	*/
	protected Figure newFigure( Point2D p, Figure f ) {
		Sistema.logger.info( "Creating use case at " + p.getX() + "," + p.getY() );
		UseCaseFigure figure = new UseCaseFigure( "Distributed" );
		figure.setLocation( p );
		UseCase useCase = figure.getUseCase();
		useCase.setDistributed( true );
		return figure;
	}
}
