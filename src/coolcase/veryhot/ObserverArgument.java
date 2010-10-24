package coolcase.veryhot;

import java.util.*;

/**
* Small class used to pass parameters when the figure is somehow changed.
*/
public class ObserverArgument {
	
	/**
	* The kind of change that happened.
	*/
	public String name;

	/**
	* The arguments. Usually just one is used, the figure's dimension before the action
	* the observer argument is about.
	*/
	public ArrayList arguments = null;

	/**
	* Creates the observer argument's. The string <emph>s</emph> identifies the action this
	* argument is about.
	*/
	public ObserverArgument( String s ) {
		name = s;
		arguments = new ArrayList(0);
	}
}
