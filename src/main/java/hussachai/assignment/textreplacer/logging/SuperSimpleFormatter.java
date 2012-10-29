package hussachai.assignment.textreplacer.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * 
 * This is the super simple formatter for java.util.logging
 * The output should look like loggerName|INFO: foo bar foo bar
 * @author hussachai
 *
 */
public class SuperSimpleFormatter extends Formatter {
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * 
	 */
	public String format(LogRecord record) {
		String loggerName = record.getLoggerName();
		if(loggerName == null) {
			loggerName = "$";
		}
		StringBuilder output = new StringBuilder()
			.append(loggerName).append("|")
			.append(record.getLevel()).append(':')
			.append(record.getMessage()).append(' ')
			.append(LINE_SEPARATOR);
		return output.toString();		
	}
 
}
