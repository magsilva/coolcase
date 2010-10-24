package coolcase.kernel;

public interface ServiceManager {
	/**
	* Discover the active services under the name provided.
	*/
	public Service[] getServices( String name );
	
	/**
	* Return an active service.
	*/
	public Service getService( String name );

	/**
	* Adds a service.
	*/
	public void startService( String name );

	/**
	* Removes a specific service.
	*/
	public void finishService( String name );
	
	public void registerService( Service s );
	
	public void unregisterService( Service s );
}
