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

public class UseCaseTool extends ConstructionTool {

	/**
	* Construtura da ferramenta
	*/
	public UseCaseTool( DrawingPanel canvas ) {
		super( canvas );
	}

	/**
	* Cria uma figura (um caso de uso)
	*/
	protected Figure newFigure( Point2D p, Figure f ) {
		UseCaseFigure figure = new UseCaseFigure( "Sem nome" );
		figure.setLocation( p );
		return figure;
	}
}
