package coolcase.casetool.action;

import coolcase.veryhot.*;
import coolcase.casetool.tool.*;
import javax.swing.*;
import java.awt.event.*;

public class ManipulationAction extends AbstractAction {

	DrawingPanel drawingPanel;

	ManipulationTool manipulationTool;

	public ManipulationAction(DrawingPanel dp, ManipulationTool mt) {
		super("Manipulation Action", new ImageIcon(AbstractAction.class.getResource("/images/manipulation.gif")));
		drawingPanel = dp;
		manipulationTool = mt;
	}

	public void actionPerformed(ActionEvent ae) {
		drawingPanel.setTool(manipulationTool);
	}
}
