/**
* TODO: Change createOperationsToolbar() and createMenu() to use actions (instead of the hardcoded form). 
*/

package coolcase.casetool;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.logging.*;

import coolcase.veryhot.*;

import coolcase.casetool.tool.*;
import coolcase.casetool.action.*;

/**
* Nosso programa principal
* Isso aqui � apenas um sistema para teste...
*/
public class Sistema extends JFrame {
	JMenuBar mainMenuBar;
	JToolBar drawingToolBar, operationsToolBar;
	DrawingPanel drawingPanel;
	
	SequentialUseCaseTool sequentialUseCaseTool;
	DistributedUseCaseTool distributedUseCaseTool;
	
	ExclusiveActorTool exclusiveActorTool;
	ParallelActorTool parallelActorTool;
	DistributedActorTool distributedActorTool;
	ParallelDistributedActorTool parallelDistributedActorTool;
	
	ManipulationTool manipulationTool;
	AssociationTool associationTool;
	
	public static Logger logger = Logger.getLogger("coolcase.casetool");

	/**
	Cria os menus do sistema
	*/
	private void createMenu() {
		JMenu menu;
		JMenuItem menuItem;

		// Cria a barra de menu
		mainMenuBar = new JMenuBar();

		/** 
		Cria o menu de gerenciamento de cen�rios
		*/
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("Manage scenarios");
		mainMenuBar.add(menu);

		// Cria os itens do menu File
		menuItem = new JMenuItem("New");
		menuItem.getAccessibleContext().setAccessibleDescription("Create a new scenario");
		menu.add(menuItem);

		menuItem = new JMenuItem("Open");
		menuItem.getAccessibleContext().setAccessibleDescription("Open an existing scenario");
		menu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.getAccessibleContext().setAccessibleDescription("Save the active scenario");
		menu.add(menuItem);

		menuItem = new JMenuItem("Close");
		menuItem.getAccessibleContext().setAccessibleDescription("Close the active scenario");
		menu.add(menuItem);


		/** 
		Cria o menu de edi��o
		*/
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_E);
		menu.getAccessibleContext().setAccessibleDescription("Handles copying, pasting, etc...");
		mainMenuBar.add(menu);

		// Cria os itens do menu Edit
		menuItem = new JMenuItem("Cut");
		menuItem.getAccessibleContext().setAccessibleDescription("Move the selected object(s) to the transfer area");
		menu.add(menuItem);

		menuItem = new JMenuItem("Copy");
		menuItem.getAccessibleContext().setAccessibleDescription("Copy the selected object(s) to the transfer area");
		menu.add(menuItem);

		menuItem = new JMenuItem("Paste");
		menuItem.getAccessibleContext().setAccessibleDescription("Copy the objects from the transfer area to the active scenario)");
		menu.add(menuItem);

		/** 
		Cria o menu de ajuda
		*/
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		menu.getAccessibleContext().setAccessibleDescription("Handles copying, pasting, etc...");
		mainMenuBar.add(Box.createHorizontalGlue());
		mainMenuBar.add(menu);

		// Cria os itens do menu Edit
		menuItem = new JMenuItem("Main help");
		menuItem.getAccessibleContext().setAccessibleDescription("Help");
		menu.add(menuItem);

		menuItem = new JMenuItem("About");
		menuItem.getAccessibleContext().setAccessibleDescription("Some miscelaneous informations about this software");
		menu.add(menuItem);
	}	

	/**
	* Cria a barra de ferramentas para opera��es r�pidas
	*/
	private void createOperationsToolBar() {
		operationsToolBar = new JToolBar( SwingConstants.HORIZONTAL );
		JButton button = null;

		operationsToolBar.setFloatable( false );

		//New scenario button
		button = new JButton( new ImageIcon(Sistema.class.getResource("/images/new.gif")));
		button.setToolTipText( "New" );
		operationsToolBar.add( button );

		//Open existing scene button
		button = new JButton( new ImageIcon(Sistema.class.getResource("/images/open.gif" )) );
		button.setToolTipText( "Open" );
		operationsToolBar.add( button );

		// Save active scene button
		button = new JButton(new ImageIcon(Sistema.class.getResource("/images/save.gif")));
		button.setToolTipText( "Save" );
		operationsToolBar.add( button );

		//Copy selected objects button
		button = new JButton( new ImageIcon(Sistema.class.getResource("/images/copy.gif" ) ));
		button.setToolTipText( "Copy" );
		operationsToolBar.add( button );

		//Cut selected objects button
		button = new JButton( new ImageIcon(Sistema.class.getResource("/images/cut.gif")));
		button.setToolTipText( "Cut" );
		operationsToolBar.add( button );

		//Paste transfer area objects button
		button = new JButton( new ImageIcon(Sistema.class.getResource("/images/paste.gif")));
		button.setToolTipText( "Paste" );
		operationsToolBar.add( button );

		//Help button
		button = new JButton(new ImageIcon(Sistema.class.getResource("/images/help.gif")));
		button.setToolTipText("Help");
		operationsToolBar.add(button);
	}

	
	/**
	*Cria a barra de ferramentas de desenho
	*/
	private void createDrawToolBar() {
		SequentialUseCaseAction sequentialUseCaseAction = new SequentialUseCaseAction( drawingPanel, sequentialUseCaseTool );
		DistributedUseCaseAction distributedUseCaseAction = new DistributedUseCaseAction( drawingPanel, distributedUseCaseTool );
		
		ExclusiveActorAction exclusiveActorAction = new ExclusiveActorAction( drawingPanel, exclusiveActorTool );
		ParallelActorAction parallelActorAction = new ParallelActorAction( drawingPanel, parallelActorTool );
		DistributedActorAction distributedActorAction = new DistributedActorAction( drawingPanel, distributedActorTool );
		ParallelDistributedActorAction parallelDistributedActorAction = new ParallelDistributedActorAction( drawingPanel, parallelDistributedActorTool );
		ManipulationAction manipulationAction = new ManipulationAction( drawingPanel, manipulationTool );
		AssociationAction associationAction = new AssociationAction( drawingPanel, associationTool );
		
		// A barra de ferramenta ser� na vertical e n�o poder� flutuar na tela 
 		drawingToolBar = new JToolBar( SwingConstants.VERTICAL );
		drawingToolBar.setFloatable( false );
		drawingToolBar.add( sequentialUseCaseAction );
		drawingToolBar.add( distributedUseCaseAction );
		
		drawingToolBar.add( exclusiveActorAction );
		drawingToolBar.add( parallelActorAction );
		drawingToolBar.add( distributedActorAction );
		drawingToolBar.add( parallelDistributedActorAction );

		drawingToolBar.add( manipulationAction );
		drawingToolBar.add( associationAction );
	}

	/**
	* M�todo privado utilizado para organizar a cria��o das coisas...
	*/
	private void createComponents() {
		createMenu();
		createOperationsToolBar();
		createDrawToolBar();
	}

	private void createTools() {
		sequentialUseCaseTool = new SequentialUseCaseTool( drawingPanel );
		distributedUseCaseTool = new DistributedUseCaseTool( drawingPanel );
	
		exclusiveActorTool = new ExclusiveActorTool( drawingPanel );
		parallelActorTool = new ParallelActorTool( drawingPanel );
		distributedActorTool = new DistributedActorTool( drawingPanel );
		parallelDistributedActorTool = new ParallelDistributedActorTool( drawingPanel );

		manipulationTool = new ManipulationTool( drawingPanel );
		associationTool = new AssociationTool( drawingPanel );
	}

	/**
	* Cria o nosso sistema
	*/
	Sistema(String name) {
		super(name);
		
		drawingPanel = new DrawingPanel( 100.0, 100.0 );
		logger.info( "DrawingPanel created" );

		createTools();
		logger.info( "Tools created" );
		
		createComponents();
		setJMenuBar( mainMenuBar );
		getContentPane().add( operationsToolBar, BorderLayout.NORTH );
		getContentPane().add( drawingToolBar, BorderLayout.WEST );
		getContentPane().add( drawingPanel, BorderLayout.CENTER );
		logger.info( "Graphical components created" );

		setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
		pack();
		setVisible(true);
		logger.info( "Tool ready, now standing at frame thread" );
	}

	public static void main(String[] args) {
		Sistema.logger.setLevel( Level.ALL );
		logger.info( "Initializing the tool" );
		Sistema app = new Sistema( "Use Case Drawer" );
		logger.info( "Finish tool initilization" );
	}
}
