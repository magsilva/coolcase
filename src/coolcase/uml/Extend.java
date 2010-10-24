package coolcase.uml;

public class Extend extends Relationship {

	ExtensionPoint e;
		
	public Extend( String name, UseCase begin, UseCase end, ExtensionPoint e ) {
		super( name, begin, end );
		this.e = e;
	}
	
	public void setExtensionPoint( ExtensionPoint e ) {
		this.e = e;
	}
	
	public ExtensionPoint getExtensionPoint() {
		return e;
	}
}
