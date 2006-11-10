package coolcase.casetool.action;

import coolcase.veryhot.*;
import coolcase.veryhot.tool.*;
import coolcase.casetool.tool.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class ManipulationAction extends AbstractAction {
	
	DrawingPanel drawingPanel;
	
	ManipulationTool manipulationTool;

	public ManipulationAction( DrawingPanel dp, ManipulationTool mt ) {
		super( "Manipulation Action", new ImageIcon( "coolcase" + File.separator + "casetool" + File.separator + "image" + File.separator + "manipulation.gif" ) );
		drawingPanel = dp;
		manipulationTool = mt;
	}
	
	public void actionPerformed( ActionEvent ae ) {
		drawingPanel.setTool( manipulationTool );
	}
} 
