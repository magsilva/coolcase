package coolcase.casetool.action;

import coolcase.veryhot.*;
import coolcase.veryhot.tool.*;
import coolcase.casetool.tool.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class ActorAction extends AbstractAction {

	DrawingPanel drawingPanel;

	ActorTool actorTool;

	public ActorAction(DrawingPanel dp, ActorTool at, String name, String filename)
	{
		super(name, new ImageIcon(AbstractAction.class.getResource("/images/" + filename)));
		drawingPanel = dp;
		actorTool = at;
	}

	public void actionPerformed(ActionEvent ae) {
		drawingPanel.setTool(actorTool);
	}
}
