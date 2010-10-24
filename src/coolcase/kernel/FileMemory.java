package coolcase.kernel;

import java.io.*;
import java.util.*;

/**
* This memory manager uses the java native serialization mechanism. It's the simpliest
* one but is quiet efective and easy to use.
*/
public class FileMemory implements MemoryDevice, Serializable {

	/**
	* Directory where the objects will be saved.
	*/
	private String serializedObjectsDirectory = ".sph";

	/**
	* File extension used to saved objects files.
	*/
	private String serializedObjectExtension = ".sph";

	/**
	* Constructor. Initializes the persistence handler list.
	*/
	public FileMemory() {
		this( ".sph", ".sph" );
	}

	public FileMemory( String dirName, String extensionName ) {
		serializedObjectsDirectory = dirName;
		serializedObjectExtension = extensionName;
		
		// Try to create the directory.
		File prefixDir = new File( dirName );

		// Check if the dirName is valid, otherwise throws an exception.
		if ( prefixDir.isFile() ) {
			throw new IllegalArgumentException( dirName + " is a file, cannot create directory with the same name of an existing file" );
		}

		// If the directory does not exist, create it!
		if ( !prefixDir.isDirectory() ) {
			prefixDir.mkdir();
		}
	}

	/**
	* Checks if the object can be saved. The object must implement the interface
	* "Serializable".
	*/
	protected boolean canSave( String key, Object obj ) {
		if ( obj instanceof java.io.Serializable ) {
			return true;
		}
		return false;
	}

	/**
	* Save the object to the directory setted.
	*/
	public boolean save( String key, Object obj ) {
		try {
			ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( serializedObjectsDirectory + System.getProperty( "file.separator" ) + key + serializedObjectExtension ) );
			out.writeObject( obj );
			out.close();
			return true;
		} catch ( Exception e )	{
			return false;
		}
	}

	/**
	* Checks if the object can be loaded verifying if it exists and is readable.
	*/
	protected boolean canLoad( String key ) {
		try {
			File f = new File( serializedObjectsDirectory + System.getProperty( "file.separator" ) + key + serializedObjectExtension );
			return f.exists();
		} catch ( NullPointerException e ) {
			return false;
		} catch ( SecurityException e ) {
			return false;
		}
	}

	/**
	* Loads a serialized object.
	*/
	public Object load( String key ) {
		Object obj = null;
		try	{
			ObjectInputStream in = new ObjectInputStream( new FileInputStream( serializedObjectsDirectory +
			System.getProperty( "file.separator" ) + key + serializedObjectExtension ) );
			obj = in.readObject();
			in.close();
		} catch ( Exception e ) {
		}
		return obj;
	}

	/**
	* Checks if the object can be destroyed.
	*/
	protected boolean canDestroy( String key ) {	
		try {
			File f = new File( serializedObjectsDirectory + System.getProperty( "file.separator" ) + key + serializedObjectExtension );
			return f.canWrite();
		} catch ( NullPointerException e ) {
			return false;
		} catch ( SecurityException e ) {
			return false;
		}
	}

	/**
	* Delete the file that contains the object.
	*/
	public boolean destroy( String key ) {
		File f = new File( serializedObjectsDirectory + System.getProperty( "file.separator" ) + key + serializedObjectExtension );
		return f.delete();
	}

	/**
	* Gets the directory name where the files are being saved.
	*/
	public String getSerializedObjectsDirectory() {
		return serializedObjectsDirectory;
	}

	/**
	* Gets the serialized object's file extension.
	*/
	public String getSerializedObjectExtension() {
		return serializedObjectExtension;
	}
	
	public static void main( String[] args ) {
		FileMemory fm = new FileMemory();
		String teste = "abc";
		System.out.println( teste.hashCode() + " - " +  teste );
		fm.save( teste, teste );
		String teste2 = (String)fm.load( teste );
		System.out.println( teste2.hashCode() + " - " + teste2 );
	}
	
}
