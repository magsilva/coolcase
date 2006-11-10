package coolcase.veryhot;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

import coolcase.veryhot.tool.*;

public class VeryhotTest extends JFrame {
	
	DrawingPanel drawingPanel;

	JMenuBar mainMenuBar;
	JToolBar drawingToolBar, operationsToolBar;
	SelectionTool selectionTool;
	
	public VeryhotTest( String name ) {
		super( name );
		drawingPanel = new DrawingPanel( 500.0, 800.0 );
		selectionTool = new SelectionTool( drawingPanel );
		getContentPane().add( drawingPanel, BorderLayout.CENTER );
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
            	}
        	});
		pack();
		setVisible(true);
		drawingPanel.setTool( selectionTool );
	}

	public static void main( String[] args ) {
		VeryhotTest vt = new VeryhotTest( "Veryhot Toolkit Test" );
	}
}
