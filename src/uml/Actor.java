package coolcase.uml;

public class Actor extends AbstractArtefact {
	
	/**
	* This we will save as a tagged value, so we keep compatible with UML. This tag indicates
	* if the actor does the use cases available sequentialy.
	*/
	private boolean exclusive;
	
	/**
	* Well, this is just the opposite of a exclusive actor, launching several task in 
	* parallel.
	*/
	private boolean parallel;
	
	/**
	* A distributed actor may be at differents systems nodes (remember, this is for a 
	* distributed system modelling).
	*/
	private boolean distributed;

	/**
	* Checks whether our actor is distributed or not.
	*/
	public boolean isDistributed() {
		return distributed;
	}

	/**
	* Sets if our actor is distributed or not.
	*/
	public void setDistributed( boolean distributed ) {
		if ( this.distributed != distributed ) {
			this.distributed = distributed;
			// FIXME: notify the change
		}
	}


	/**
	* Checks whether our actor can run things in parallel or not.
	*/
	public boolean isParallel() {
		return parallel;
	}

	/**
	* Sets if our actor is parallel or not.
	*/
	public void setParallel( boolean parallel ) {
		if ( this.parallel != parallel ) {
			this.parallel = parallel;
			// FIXME: notify the change
		}
	}


	/**
	* Checks whether our actor is exclusive or not.
	*/
	public boolean isExclusive() {
		return exclusive;
	}

	/**
	* Sets if our actor is exclusive or not.
	*/
	public void setExclusive( boolean exclusive ) {
		if ( this.exclusive != exclusive ) {
			this.exclusive = exclusive;
			// FIXME: notify the change
		}
	}
	
	public Actor( String name ) {
		super( name );
	}
}
