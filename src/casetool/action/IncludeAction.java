package coolcase.casetool.action;

import coolcase.veryhot.*;
import coolcase.veryhot.tool.*;
import coolcase.casetool.tool.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class IncludeAction extends AbstractAction {
	
	DrawingPanel drawingPanel;
	
	IncludeTool includeTool;

	public ActorAction( DrawingPanel dp, includeTool it ) {
		super( "Actor Action", new ImageIcon( "coolcase" + File.separator + "casetool" + File.separator + "image" + File.separator + "include.gif" ) );
		drawingPanel = dp;
		includeTool = it;
	}
	
	public void actionPerformed( ActionEvent ae ) {
		drawingPanel.setTool( includeTool );
	}
} 
