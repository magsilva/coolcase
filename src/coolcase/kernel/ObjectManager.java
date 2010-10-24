package coolcase.kernel;

/**
 * Keeps organized the common operations needed on repositories.
 */
public interface ObjectManager
{
	Object load(String key);

	Object load(String key, String version);

	boolean save(String key, Object o);

	boolean save(String key, String version, Object o);

	String getLog(String key);

	String getLog(String beginKey, String endKey);

	void delete(String key);

	void undelete(String key);
}
