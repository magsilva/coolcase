package coolcase.kernel;

public interface MemoryDevice extends ObjectManager, Service {
	public boolean isPersistent();
}
