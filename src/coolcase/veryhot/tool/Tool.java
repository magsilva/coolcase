package coolcase.veryhot.tool;

public interface Tool {
	/**
	* Method that _must_ be called before exiting the tool.
	*/
	public void toolExit();

	/**
	* Method that _must_ be called before entering the tool.
	*/
	public void toolEnter();
}
