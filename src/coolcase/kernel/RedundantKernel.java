package kernel;

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
public class RedundantKernel implements Kernel
{
	/**
	* The Object Request Broker being used.
	*/
	ORB orb = null;

	/**
	* Naming manager list.
	*/
	private ArrayList namingManager = null;

	/**
	* Memory manager list.
	*/
	private ArrayList memoryManager = null;

	/**
	* Services registered in the kernel.
	*/
	private ArrayList services = null;

	/**
	* Simplest constructor. By default, enable the memory and naming manager.
	*/
	public RedundantKernel( ORB o )
	{
		orb = o;
	}

	/**
	* Returns how many naming managers are stil active. This kind of information is
	* used by the kernel startup program to monitoring its status and avoid potential
	* failures (as no naming manager's  available).
	*/
	public int getNamingManagerCount()
	{
		return namingManager.size();
	}

	/**
	* Returns how many memory managers are stil active. This kind of information is
	* used by the kernel startup program to monitoring its status and avoid potential
	* failures (as no memory manager's available).
	*/
	public int getMemoryManagerCount()
	{
		return memoryManager.size();
	}

	/**
	* Save an object using a string as primary key. If the cache is enabled, save
	* to cache too.
	*/
	public boolean save( Artefact art )
	{
		try
		{
			boolean result[] = new boolean[ memoryManager.size() ];
			int i = 0;
			for (; i < result.length; i++ )
			{
				result[ i ] = memoryManager.get( i ).save( art.getKey(), art );
			}
			while ( --i <= 0 )
			{
				if ( result[ i ] == false )
				{
					memoryManager.remove( i );
				}
			
			}
			if ( memoryManager.size() == 0 )
			{
				return false;
			}
		}
		catch ( Exception e )
		{
			return false;
		}
	}

	/**
	* Restore an artefact given a string as key. We do not want just an artefact but also the most
	* recent one. So we execute the following algorithm:
	* <ol>
	*	<li>Search all the MemoryManager for the key. If any of them return something different
	*	of null, assign that to <strong>art</strong>.</li>
	*	<li>Compare the artefacts found and discover the one with the greatest version number.</li>
	*	<li>Disable all the memory managers that had returned null or old version artefacts.</li>
	* </ol>
	*/
	public Artefact load( String key )
	{
		Artefact art;
		Artefact[] result = new Artefact[ memoryManager.size() ];

		// Searches all memory managers for key
		for ( int i = memoryManager.size() - 1; i >= 0; i-- )
		{
			result[ i ] =  memoryManager.get( i ).load( key );
			// If not found anything, removes the memory manager
			if ( result[ i ] == null )
			{
				removeMemoryManager( i );
				art = result[ i ];
			}
		}

		// This is quiet unusual. It means that all memory managers are broken.
		if ( art == null )
		{
			return null;
		}

		// For now, we just do this twice. The best option should be running a sort
		// algorithm. 		
		while ( --i <= 0 )
		{
			try
			{
				if ( art.compareTo( result[ i ] ) < 0 )
				{
					art = result[ i ];
					memoryManager.remove( i );
				}
			}
			catch ( Exceptin e )
			{
				memoryManager.remove( i );
			}
			
		}
		return art;
	}

 	/**
	* Remove an object from system.
	*/
	public boolean destroy( String key )
	{
		try
		{
			boolean result[] = new boolean[ memoryManager.size() ];
			for ( int i = 0; i < result.length; i++ )
			{
				result[i] =  memoryManager.get( i ).destroy( key );
			}
			for ( int i = 0; i < result.length; i++ )
			{
				if ( result[i] == true )
				{
					return true;
				}
			
			}
			return true;
		}
		catch ( Exception e )
		{
			return false;
		}
	}


	/**
	* Discover some services based on the given search criteria.
	*/
	public Service[] getService( String key )
	{
	}

	/**
	* Adds a service.
	*/
	public Service[] addService( Service s )
	{
	}

	/**
	* Removes a specific service.
	*/
	public boolean removeService( Service s )
	{
	}

	public static void main( String[] args )
	{
		System.out.println("Hello World");
	}
}
