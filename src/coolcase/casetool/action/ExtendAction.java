package coolcase.casetool.action;

import coolcase.veryhot.*;
import coolcase.veryhot.tool.*;
import coolcase.casetool.tool.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class ExtendAction extends AbstractAction {

	DrawingPanel drawingPanel;

	ExtendTool extendTool;

	public ExtendAction(DrawingPanel dp, ExtendTool et) {
		super("Actor Action", new ImageIcon(AbstractAction.class.getResource("/images/extend.gif")));
		drawingPanel = dp;
		extendTool = et;
	}

	public void actionPerformed(ActionEvent ae) {
		drawingPanel.setTool(extendTool);
	}
}
