package hussachai.assignment.textreplacer;

/**
 * 
 * The object that is the result of search and replace operation.
 * 
 * @author hussachai
 *
 */
public class Result {
	
	/**
	 * The number of texts or patterns found in the file.
	 */
	private int occurrences = 0;
	
	/**
	 * The result of replacing
	 */
	private String output = null;
	
	/**
	 * This flag is true if the file is modified.
	 */
	private boolean modified = false;
	
	public int getOccurrences() {
		return occurrences;
	}
	
	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
}
