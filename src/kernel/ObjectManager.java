package coolcase.kernel;

/**
* Keeps organized the common operations needed on repositories.
*/
public interface ObjectManager {
	public Object load( String key );
	public Object load( String key, String version );
	public void save( String key, Object o );
	public void save( String key, String version, Object o );
	public String getLog( String key );
	public String getLog( String beginKey, String endKey );
	public void delete( String key );
	public void undelete( String key );
}
	
	
	
