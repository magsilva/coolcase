package coolcase.casetool.action;

import coolcase.veryhot.*;
import coolcase.veryhot.tool.*;
import coolcase.casetool.tool.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class AssociationAction extends AbstractAction {
	
	DrawingPanel drawingPanel;
	
	AssociationTool associationTool;

	public AssociationAction( DrawingPanel dp, AssociationTool at ) {
		super( "Actor Action", new ImageIcon( "coolcase" + File.separator + "casetool" + File.separator + "image" + File.separator + "association.gif" ) );
		drawingPanel = dp;
		associationTool = at;
	}
	
	public void actionPerformed( ActionEvent ae ) {
		drawingPanel.setTool( associationTool );
	}
} 
