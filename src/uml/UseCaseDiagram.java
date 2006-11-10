package coolcase.uml;

import java.util.*;

public class UseCaseDiagram extends AbstractArtefact {
	
	private TreeSet useCases;
	
	private TreeSet actors;
	
	private TreeSet relationships;
	
	public UseCaseDiagram( String name ) {
		super( name );
		useCases = new TreeSet();
		actors = new TreeSet();
		relationships = new TreeSet();
	}
	
	public void add( Artefact a ) {
		if ( a instanceof UseCase ) {
			useCases.add( a );
		}
		
		if ( a instanceof Actor ) {
			actors.add( a );
		}
		
		if ( a instanceof Relationship ) {
			relationships.add( a );
		}
	}
	
	public void remove( Artefact a ) {
		if ( a instanceof UseCase ) {
			useCases.remove( a );
		}
		
		if ( a instanceof Actor ) {
			actors.remove( a );
		}
		
		if ( a instanceof Relationship ) {
			relationships.remove( a );
		}
	}
	
	public Set getOrphanUseCases() {
		Iterator i = relationships.iterator();
		TreeSet nonOrphanUseCases = new TreeSet();
		TreeSet orphanUseCases = (TreeSet)useCases.clone();
		
		// Gets all use cases involved into a relationship.
		while ( i.hasNext() ) {
			Relationship r = (Relationship)i.next();
			Iterator j = useCases.iterator();
			while ( j.hasNext() ) {
				UseCase u = (UseCase)j.next();
				if ( r.contains( u ) ) {
					nonOrphanUseCases.add( u );
				}
			}
		}
		
		orphanUseCases.removeAll( nonOrphanUseCases );
		return orphanUseCases;
	}  

	public Set getOrphanActors() {
		Iterator i = relationships.iterator();
		TreeSet nonOrphanActors = new TreeSet();
		TreeSet orphanActors = (TreeSet)actors.clone();
		
		// Gets all actors involved into a relationship.
		while ( i.hasNext() ) {
			Relationship r = (Relationship)i.next();
			Iterator j = actors.iterator();
			while ( j.hasNext() ) {
				Actor a = (Actor)j.next();
				if ( r.contains( a ) ) {
					nonOrphanActors.add( a );
				}
			}
		}
		
		orphanActors.removeAll( nonOrphanActors );
		return orphanActors;
	}  

	public Set getOrphanRelationships() {
		Iterator i = relationships.iterator();
		TreeSet nonOrphanRelationships = new TreeSet();
		TreeSet orphanRelationships = (TreeSet)relationships.clone();
		
		while ( i.hasNext() ) {
			Iterator ucIterator = useCases.iterator();
			Iterator aIterator = actors.iterator(); 
			Relationship r = (Relationship)i.next();
			while ( ucIterator.hasNext() ) {
				UseCase uc = (UseCase)ucIterator.next();
				if ( r.contains( uc ) ) {
					nonOrphanRelationships.add( r );
				}
			}
			while ( aIterator.hasNext() ) {
				Actor a = (Actor)aIterator.next();
				if ( r.contains( a ) ) {
					nonOrphanRelationships.add( r );
				}
			}
		}
		
		orphanRelationships.removeAll( nonOrphanRelationships );
		return orphanRelationships;
	}  
	
	public boolean isConsistent() {
		Set orphanActors = getOrphanActors();
		if ( orphanActors.isEmpty() == false ) {
			System.out.println( "Orphan actors detected" );
			Iterator i = orphanActors.iterator();
			while ( i.hasNext() ) {
				Actor a = (Actor)i.next();
				System.out.println( a.getName() );
			}
			return false;
		}

		Set orphanUseCases = getOrphanUseCases();
		if ( orphanUseCases.isEmpty() == false ) {
			System.out.println( "Orphan use cases detected" );
			Iterator i = orphanUseCases.iterator();
			while ( i.hasNext() ) {
				UseCase uc = (UseCase)i.next();
				System.out.println( uc.getName() );
			}
			return false;
		}
			
		Set orphanRelationships = getOrphanRelationships();
		if ( orphanRelationships.isEmpty() == false ) {
			System.out.println( "Orphan relationships detected" );
			Iterator i = orphanRelationships.iterator();
			while ( i.hasNext() ) {
				Relationship r = (Relationship)i.next();
				System.out.println( r.getName() );
			}
			return false;
		}
		
		return true;
	}
	
	public static void main( String[] args ) {
		UseCaseDiagram ucd = new UseCaseDiagram( "Teste" );
		Actor a1 = new Actor( "Marco Aurélio" );
		Actor a2 = new Actor( "Discovery" );
		UseCase uc1 = new UseCase( "Trabalhar" );
		UseCase uc2 = new UseCase( "Estudar" );
		UseCase uc3 = new UseCase( "Escrever TG" );
		Generalization g1 = new Generalization( "", uc2, uc1 );
		Include i1 = new Include( "", uc2, uc3 );
		ExtensionPoint ep1 = new ExtensionPoint( "No computador" );
		ExtensionPoint ep2 = new ExtensionPoint( "Na biblioteca" );
		uc3.addExtensionPoint( ep1 );
		uc3.addExtensionPoint( ep2 );
		UseCase uc4 = new UseCase( "Compilar TeX" );
		Extend e1 = new Extend( "", uc3, uc4, ep1 );
		Association as1 = new Association( "", a1, a2 );
		Association as2 = new Association( "", a1, uc1 );
		Association as3 = new Association( "", a2, uc3 );
		
		ucd.add( a1 );
		ucd.add( a2 );
		ucd.add( uc1 );
		ucd.add( uc2 );
		ucd.add( uc3 );
		ucd.add( g1 );
		ucd.add( i1 );
		ucd.add( ep1 );
		ucd.add( ep2 );
		ucd.add( uc4 );
		ucd.add( e1 );
		ucd.add( as1 );
		ucd.add( as2 );
		ucd.add( as3 );

		System.out.print( "Checking consistency..." );
		if ( ucd.isConsistent() ) {
			System.out.println( "OK" );
		} else {
			System.out.println( "Error detected" );
		}
	}
}
