package coolcase.kernel;

import java.io.*;
import java.util.*;
import java.sql.*;

/**
* This is a more sofistified persistence mechanism, based on PostgreSQL's serialization.
* It uses the database to save the data, that is much more efficient.
* This persistence handler save the objects using the serialization features from
* PostgreSQL. All the objects are saved in a database, what is far more eficient than
* saving to files, like SerializerPesistenceHandler.
*/
public class DBMemory extends Serialize implements MemoryDevice {
	
	/**
	* Constructor. Initializes the persistence handler list.
	*/
	public DBMemory( String url, String username, String password, String type ) throws ClassNotFoundException, SQLException {
		// Loads the postgresql driver, it will automatically register itself with JDBC
		Class.forName("org.postgresql.Driver");
		Connection c = DriverManager.getConnection( url, username, password );
		this( orb, c, type );
	}

	public DBMemory( org.postgresql.Connection c, String type) throws SQLException {
		super( c, type );
	}

	/**
	* Searches an object given a string as key. If finds the persistence handler
	* of the object and sends this message to it. If any persistence handler has
	* it, returns null.
	*/
	public Object load( String key )
	{
		Object obj = null;
		try
		{
                	obj =  fetch( Integer.parseInt( key ) );
			return obj;
		}
		catch ( Exception e )
		{
			return null;
		}
	}

	/**
	* Saves an object and associate it with a key. First of all, it needs
	* to find a persistence handler that can save that object. After this,
	* you delegate this job to it. If no persistence handler is found,
	* just return false.
	*/
	public boolean save( String key, Object obj )
	{
		try
		{
			store(  obj );
			return true;
		}
		catch ( Exception e )
		{
			return false;
		}
	}

	/**
	* Destroy an object. Well, not really, an object is indestrutible..
	*/
	public boolean destroy( String key )
	{
		return false;
	}

	/**
	* Checks if the object can be saved. The object must implement the interface
	* "Serializable".
	*/
	public boolean canSave( String key, Object obj )
	{
                if ( obj instanceof java.io.Serializable )
			return true;
		return false;
	}

	/**
	* Checks if the object can be loaded verifying if it exists in the dababase's table.
	*/
	public boolean canLoad( String key )
	{
		return true;
	}

	/**
	* Checks if the object can be destroyed.
	*/
	public boolean canDestroy( String key )
	{	
		return true;
	}
}
