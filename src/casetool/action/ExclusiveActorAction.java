package coolcase.casetool.action;

import coolcase.veryhot.*;
import coolcase.veryhot.tool.*;
import coolcase.casetool.tool.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class ExclusiveActorAction extends ActorAction {
	
	public ExclusiveActorAction( DrawingPanel dp, ExclusiveActorTool eat ) {
		super( dp, eat, "Plain Actor Action", "plainActor.gif" );
	}
} 
