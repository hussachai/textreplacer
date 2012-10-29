package hussachai.assignment.textreplacer.logging;

/**
 * 
 * The interface of Logger
 * 
 * @author hussachai
 *
 */
public interface Logger {

	/**
	 * Logs a {@link java.lang.Throwable} and optional message parts at level trace.
	 * @param thrown
	 * @param messages
	 */
	public void trace(Throwable thrown, Object... messages);
	
	/**
	 * Logs one or more message parts at level trace.
	 * @param messages
	 */
	public void trace(Object... messages);
	
	/**
	 * Logs a {@link java.lang.Throwable} and optional message parts at level debug.
	 * @param thrown
	 * @param messages
	 */
	public void debug(Throwable thrown, Object... messages);
	
	/**
	 * Logs one or more message parts at level debug.
	 * @param messages
	 */
	public void debug(Object... messages);
	
	/**
	 * Logs a {@link java.lang.Throwable} and optional message parts at level info.
	 * @param thrown
	 * @param messages
	 */
	public void info(Throwable thrown, Object... messages);
	
	/**
	 * Logs one or more message parts at level info.
	 * @param messages
	 */
	public void info(Object... messages);
	
	/**
	 * Logs a {@link java.lang.Throwable} and optional message parts at level warn.
	 * @param thrown
	 * @param messages
	 */
	public void warn(Throwable thrown, Object... messages);
	
	/**
	 * Logs one or more message parts at level warn.
	 * @param messages
	 */
	public void warn(Object... messages);
	
	/**
	 * Logs a {@link java.lang.Throwable} and optional message parts at level error.
	 * @param thrown
	 * @param messages
	 */
	public void error(Throwable thrown, Object... messages);
	
	/**
	 * Logs one or more message parts at level error.
	 * @param messages
	 */
	public void error(Object... messages);
	
	/**
	 * Logs a {@link java.lang.Throwable} and optional message parts at level fatal.
	 * @param thrown
	 * @param messages
	 */
	public void fatal(Throwable thrown, Object... messages);
	
	/**
	 * Logs one or more message parts at level fatal.
	 * @param messages
	 */
	public void fatal(Object... messages);
	
}
