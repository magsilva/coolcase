package coolcase.uml;

/**
* The begin is the UseCase that is specialized, the end the generalized use case.
*/
public class Generalization extends Relationship {
	
	public Generalization( String name, UseCase son, UseCase father ) {
		super( name, son, father );
	}
	
	public UseCase getFather() {
		return (UseCase)end;
	}
	
	public void setFather( UseCase u ) {
		end = u;
	}
	
	public UseCase getSon() {
		return (UseCase)begin;
	}
	
	public void setSon( UseCase u ) {
		begin = u;
	}
}
