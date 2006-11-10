package coolcase.uml;

public abstract class Relationship extends AbstractArtefact {
	
	protected Artefact begin, end;
	
	protected boolean parallel;
	
	public Relationship( String name, Artefact a1, Artefact a2 ) {
		super( name );
		begin = a1;
		end = a2;
	}
	
	public Artefact getBegin() {
		return begin;
	}
	
	public Artefact getEnd() {
		return end;
	}
	
	public boolean isConsistent() {
		if ( begin instanceof Actor && end instanceof Actor ) {
			return false;
		}
		return true;
	}
	
	public boolean contains( Artefact a  ) {
		if ( a == begin || a == end ) {
			return true;
		}
		return false;
	}
	
	public boolean isParallel() {
		return parallel;
	}
	
	public void setParallel( boolean parallel ) {
		this.parallel = parallel;
	}
}
