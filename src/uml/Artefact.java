package coolcase.uml;

public interface Artefact extends Comparable {
	public String getDiff( Artefact a );
	public String getLog();
	public void setLog( String log );
	public void appendLog( String log );
	public String getName();
	public void setName( String name );
}
