package hussachai.assignment.textreplacer;


/**
 * 
 * The TextReplacer implementation must implement this interface.
 * 
 * @author hussachai
 *
 */
public interface TextReplacer {

	
	/**
	 * This method does not modified the original input text. The modified text will be set
	 * to the <code>{@link Result#setOutput(java.lang.String)}</code>
	 * 
	 * @param input
	 * @param search
	 * @param replacement
	 * @return
	 */
	public Result replaceAll(String input, String search, String replacement);
	
}
