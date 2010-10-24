package coolcase.kernel;

import java.io.*;
import java.util.*;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;


/**
 * This kernel applies some redundance, running iddentical services in parallel Its algorithm is "keep
 * working on failure". Of course we can just ignore errors, we must do something. So, deppending on the
 * kind of service, we can disable it or just ignore. Untill now, we have just two kinds of services: basic
 * and accessory. The first one cannot fail in any hypothesis. If so, it is automatically disabled. The other
 * is just marked as bad but can be used yet.
 */
public class SimpleKernel implements Kernel {

	/**
	* Naming manager.
	*/
	private NamingManager namingManager;

	/**
	* Memory manager.
	*/
	private MemoryManager memoryManager;

	/**
	* Services registered in the kernel.
	*/
	private Hashtable services = null;

	/**
	* Simplest constructor. By default, enable the memory and naming manager.
	*/
	public SimpleKernel()	{
	}

	/**
	* Save an object using a string as primary key.
	*/
	public void save( Object o )	{
		try {
			return memoryManager.save( o );
		} catch ( Exception e ) {
		}
	}

	/**
	* Save an object using a string as primary key.
	*/
	public void save( String version, Object o )	{
		try {
			return memoryManager.save( version, o );
		} catch ( Exception e ) {
		}
	}
	
	/**
	* Restore an artefact given a string as key.
	*/
	public Object load( String key ) {
		Object o = memoryManager.load( key );
		return o;
	}

	/**
	* Restore an artefact given a string as key.
	*/
	public Object load( String key, String version ) {
		Object o = memoryManager.load( key, version );
		return o;
	}

 	/**
	* Remove an object from system.
	*/
	public void delete( String key ) {
		try	{
			return memoryManager.delete( key );
		} catch ( Exception e )	{
		}
	}
 	/**
	* Remove an object from system.
	*/
	public void undelete( String key ) {
		try	{
			return memoryManager.undelete( key );
		} catch ( Exception e )	{
		}
	}

	/**
	* Discover some services based on the given search criteria.
	*/
	public Service getService( String name )
	{
	}

	/**
	* Discover some services based on the given search criteria.
	*/
	public Service[] getServices( String name ) {
	}

	
	/**
	* Adds a service.
	*/
	public void registerService( Service s ) {
	}

	/**
	* Removes a specific service.
	*/
	public void unregisterService( Service s ) {
	}

	public static void main( String[] args )
	{
		System.out.println("Hello World");
	}
}
