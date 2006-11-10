package coolcase.uml;

import java.util.*;

public class UseCase extends AbstractArtefact {
	
	protected TreeSet extensionPoints;
	
	protected boolean distributed;
	
	protected boolean sequential;
	
	public UseCase( String name ) {
		super( name );
		extensionPoints = new TreeSet();
	}
	
	public void addExtensionPoint( ExtensionPoint ep ) {
		extensionPoints.add( ep );
	}
	
	public void removeExtensionPoint( ExtensionPoint ep ) {
		extensionPoints.remove( ep );
	}
		
	public Iterator getExtensionPoints() {
		return extensionPoints.iterator();
	}
	
	public boolean isDistributed() {
		return distributed;
	}
	
	public void setDistributed( boolean distributed ) {
		if ( isSequential() && distributed ) {
			setSequential( false );
		}
		this.distributed = distributed;
	}
	
	public boolean isSequential() {
		return sequential;
	}
	
	public void setSequential( boolean sequential ) {
		if ( isDistributed() && sequential ) {
			setDistributed( false );
		}
		this.sequential = sequential;
	}
}
