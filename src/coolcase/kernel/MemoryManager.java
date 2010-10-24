package coolcase.kernel;

import java.util.*;

public class MemoryManager implements Service {
	
	protected TreeMap memory;

	protected MemoryDevice firstLevel;

	public MemoryManager( org.omg.CORBA.ORB orb ) {
		memory = new TreeMap();
	}

	public boolean addMemory( Memory m ) {
		return addMemory( m, memory.size() );
	}

	public boolean addMemory( Memory m, int level ) {
		Integer l = new Integer( level );
		if ( memory.containsKey( l ) ) {
			return false;
		}
		memory.put( l, m );
		if ( level == 1 ) {
			firstLevel = m;
		}
		return true;
	}

	public boolean removeMemory( int level ) {
		Integer l = new Integer( level );
		Object result = memory.remove( l );
		if ( level == 1 ) {
			firstLevel = null;
		}
		return true;
	}

	public boolean save( String key, Object o ) {
		return firstLevel.save( key, o );
	}

	public boolean save( Object o ) {
		return firstLevel.save( o.toString(), o );
	}

	public Object load( String key ) {
		Object result = null;
		for ( int level = 0; level < memory.size(); level++ ) {
			Memory m = (Memory)memory.get( new Integer( level ) );
			result = m.load( key );
			if ( result != null ) {
				firstLevel.save( key, result );
				return result;
			}
		}
		return result;
	}

	public boolean destroy( String key ) {
		boolean result = false;
		for ( int level = 0; level < memory.size(); level++ ) {
			Memory m = (Memory)memory.get( new Integer( level ) );
			result = m.destroy( key );
			if ( result != false ) {
				return result;
			}
		}
		return result;
	}
}
