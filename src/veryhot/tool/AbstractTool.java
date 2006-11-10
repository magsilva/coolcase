package coolcase.veryhot.tool;

import java.awt.*;
import java.awt.event.*;
import coolcase.veryhot.*;

/**
* Basic tools class.
*/
public abstract class AbstractTool implements Tool {
	/**
	* Every tool must be related to a DrawingPanel.
	*/
	protected DrawingPanel canvas;

	public AbstractTool( DrawingPanel canvas ) {
		this.canvas = canvas;
	}

	/**
	* Method that _must_ be called before exiting the tool.
	*/
	public void toolExit() {
	}

	/**
	* Method that _must_ be called before entering the tool.
	*/
	public void toolEnter() {
		canvas.requestFocus();
	};
}
