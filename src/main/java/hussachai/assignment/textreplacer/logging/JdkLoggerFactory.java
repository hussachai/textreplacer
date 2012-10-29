package hussachai.assignment.textreplacer.logging;

import hussachai.assignment.textreplacer.utils.StringUtils;

import java.util.logging.Level;
import java.util.logging.LogManager;


/**
 * 
 * This object factory create the implementation of {@link Logger} that is the adapter
 * for JDK logging API. 
 * 
 * @author hussachai
 *
 */
public class JdkLoggerFactory {
	
	static{
		try {
			LogManager.getLogManager().readConfiguration(
					JdkLoggerFactory.class.getResourceAsStream("/logging.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JdkLoggerFactory(){}
	
	public static Logger getLogger(Class<?> targetClass){
		return new JdkLoggerImpl(targetClass.getName());
	}
	
	public static class JdkLoggerImpl implements Logger {
		
		private java.util.logging.Logger logImpl; 
		
		public JdkLoggerImpl(String className){
			logImpl = java.util.logging.Logger.getLogger(className);
		}
		
		@Override
		public void trace(Throwable thrown, Object... messages) {
			logImpl.log(Level.FINEST, StringUtils.combine(messages), thrown);
		}
		
		@Override
		public void trace(Object... messages) {
			logImpl.log(Level.FINEST, StringUtils.combine(messages));
		}

		@Override
		public void debug(Throwable thrown, Object... messages) {
			logImpl.log(Level.FINE, StringUtils.combine(messages), thrown);
		}

		@Override
		public void debug(Object... messages) {
			logImpl.log(Level.FINE, StringUtils.combine(messages));
		}

		@Override
		public void info(Throwable thrown, Object... messages) {
			logImpl.log(Level.INFO, StringUtils.combine(messages), thrown);
		}

		@Override
		public void info(Object... messages) {
			logImpl.log(Level.INFO, StringUtils.combine(messages));
		}

		@Override
		public void warn(Throwable thrown, Object... messages) {
			logImpl.log(Level.WARNING, StringUtils.combine(messages), thrown);
		}

		@Override
		public void warn(Object... messages) {
			logImpl.log(Level.WARNING, StringUtils.combine(messages));
		}

		@Override
		public void error(Throwable thrown, Object... messages) {
			logImpl.log(Level.SEVERE, StringUtils.combine(messages), thrown);
		}
		
		@Override
		public void error(Object... messages) {
			logImpl.log(Level.SEVERE, StringUtils.combine(messages));
		}
		
		@Override
		public void fatal(Throwable thrown, Object... messages) {
			logImpl.log(Level.SEVERE, StringUtils.combine(messages), thrown);
		}

		@Override
		public void fatal(Object... messages) {
			logImpl.log(Level.SEVERE, StringUtils.combine(messages));
		}
		
	}
}
