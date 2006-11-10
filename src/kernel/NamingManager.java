package coolcase.kernel;

import java.util.StringTokenizer;
import java.util.Date;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.ORBPackage.InvalidName;

/**
* Used mainly to get object references (using the CORBA
* Name Service and the Trading Service).
*/
public class NamingManager implements Service {

	/**
	* Naming Service URL.
	*/
	String nsURL = new String( "127.0.0.1:2001" );
	
	/**
	* Constant that defines the POA's name.
	*/
	private static String ROOT_POA = "RootPOA";

	/**
	* Constant that defines the CORBA Name Service's name.
	*/
	private static String NAME_SERVICE = "NameService";

	/**
	* ORB to be used.
	*/
	private ORB orb = null;

	/**
	* The POA being used.
	*/
	private POA poa = null;

	/**
	* Naming Service reference.
	*/
	private NamingContextExt nameService = null;

	/**
	* Service name
	*/ 
	private String name = "NamingManager";
	
	/**
	* Service description.
	*/
	private String description = "Naming manager, based on CORBA Name Service.";
	
	/**
	* Service version.
	*/
	private String version = "0.0.1";

	/**
	* Service author.
	*/
	private String author = "Marco Aurélio Graciotto Silva";

	/**
	* Service release date.
	*/
	private String releaseDate = new Date().toString();

	
	/**
	* Constructor. Need a ORB already configured to work properly.
	*/
	public NamingManager( ORB orb ) {
		String ns = new String( "corbaloc:iiop:" + nsURL + "/NameService" );
		org.omg.CORBA.Object obj = null;
		// Setup the ORB to this service.
		this.orb = orb;
		
		try {
			// Resolve the CORBA Name Service.
			obj = orb.resolve_initial_references( NAME_SERVICE );
			// obj = orb.string_to_object( ns );
		} catch ( InvalidName e ) {
		}

		// Take the naming context.
		nameService = NamingContextExtHelper.narrow( obj );
	}	

	public boolean addObject( String name, org.omg.CORBA.Object obj ) {
		// Transforms the string into an array of name components.
		NameComponent[] nc = stringToNameComponent( name );

		// Not a valid string
		if ( nc == null ) {
			return false;
		}

		// Try to find the object.
		try {
			// If found, returns the reference.
			nameService.rebind( nc, obj );
			return true;
		} catch ( Exception e ) {
			// Not found
      		return false;
		}

	}

	/**
	* Search for an object with the given name. The string used must be with the
	* following syntax: name1:kind/name2:kind2/...
	* Ps: the "kind" is optional.
	*/
	public org.omg.CORBA.Object getObject( String name ) {
		// Transforms the string into an array of name components.
		NameComponent[] nc = stringToNameComponent( name );

		// Not a valid string
		if ( nc == null ) {
			return null;
		}

		// Try to find the object.
		try {
			// If found, returns the reference.
			return nameService.resolve( nc );
		} catch ( Exception e ) {
			// Not found
      		return null;
		}
	}
  
	/**
	* Transforms the string from name1:kind1/name2:kind2/.../nameN:kindN to
	* an array of NameComponent.
	*/
	protected NameComponent[] stringToNameComponent( String str ) {
    	if ( str == null ) {
			// Not a valid string.
			return null;
		}

		StringTokenizer names = new StringTokenizer(str,"/");
		NameComponent[] nc = new NameComponent[ names.countTokens() ];
		for ( int i = 0; names.hasMoreTokens(); i++ ) {
			StringTokenizer nk = new StringTokenizer( names.nextToken(), ":" );
			String name = ( nk.hasMoreTokens() )? nk.nextToken() : "";
			String kind = ( nk.hasMoreTokens() )? nk.nextToken() : "";
			nc[i] = new NameComponent();
			nc[i].id = name;
			nc[i].kind = kind;
		}
		return nc;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}
	
	public static void main( String[] args ) {
		
		org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init( args, null );
		NamingManager nm = new NamingManager( orb );
		System.out.println( nm.getName() + " " + nm.getVersion() + " (" + nm.getReleaseDate() + ")" );
		System.out.println( nm.getAuthor() );
		System.out.println( nm.getDescription() );
	}
}
