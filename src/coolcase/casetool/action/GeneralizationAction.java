package coolcase.casetool.action;

import coolcase.veryhot.*;
import coolcase.veryhot.tool.*;
import coolcase.casetool.tool.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class GeneralizationAction extends AbstractAction {

	DrawingPanel drawingPanel;

	GeneralizationTool generalizationTool;

	public GeneralizationAction(DrawingPanel dp, GeneralizationTool gt) {
		super("Actor Action", new ImageIcon(AbstractAction.class.getResource("images/generalization.gif")));
		drawingPanel = dp;
		generalizationTool = gt;
	}

	public void actionPerformed(ActionEvent ae) {
		drawingPanel.setTool(generalizationTool);
	}
}
