package coolcase.casetool.action;

import coolcase.veryhot.*;
import coolcase.veryhot.tool.*;
import coolcase.casetool.tool.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class UseCaseAction extends AbstractAction {
	
	DrawingPanel drawingPanel;
	
	UseCaseTool useCaseTool;

	public UseCaseAction( DrawingPanel dp, UseCaseTool at, String name, String filename ) {
		super( name, new ImageIcon( "coolcase" + File.separator + "casetool" + File.separator + "image" + File.separator + filename ) );
		drawingPanel = dp;
		useCaseTool = at;
	}
	
	public void actionPerformed( ActionEvent ae ) {
		drawingPanel.setTool( useCaseTool );
	}
} 
