package coolcase.uml;

public abstract class AbstractArtefact implements Artefact {
	
	protected String name;
	
	protected String log;

	public AbstractArtefact( String name ) {
		this.name = name;
	}
	
	public int compareTo( Object o ) {
		if ( o instanceof Artefact ) {
			Artefact a = (Artefact)o;
			return name.compareTo( a.getName() );
		} else {
			return 1;
		}
	}
	
	public String getDiff( Artefact a ) {
		return "";
	}
	
	public String getLog() {
		return log;
	}
	
	public void setLog( String log ) {
		this.log = log;
	}
	
	public void appendLog( String log ) {
		this.log.concat( "\n" );
		this.log.concat( log );
	}
		
	public String getName() {
		return name;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
}
