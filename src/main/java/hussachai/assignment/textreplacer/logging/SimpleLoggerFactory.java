package hussachai.assignment.textreplacer.logging;

import hussachai.assignment.textreplacer.utils.StringUtils;

import java.util.concurrent.ConcurrentHashMap;


/**
 * 
 * The class is the factory class of 
 * hussachai.assignment.textreplacer.logging.SimpleLogger which is the simple implementation of 
 * hussachai.assignment.textreplacer.logging.Logger interface.
 * It redirects all messages to standard output. In most case is console. 
 * 
 * 
 * @author hussachai
 * @version 1.0
 */
public class SimpleLoggerFactory {
	
	private static final ConcurrentHashMap<String, Logger> LOGGER_POOL= new ConcurrentHashMap<String, Logger>();
	
	private SimpleLoggerFactory(){}
	
	
	public static Logger getLogger(Class<?> clazz){
		String key = clazz.getName();
		Logger logger = LOGGER_POOL.putIfAbsent(key, new SimpleLoggerImpl());
		if(logger==null) logger = LOGGER_POOL.get(key);
		return logger;
	}
	
	static class SimpleLoggerImpl implements Logger {
		
		/**
		 * We may implement logging filter here.
		 * For the sake of simplicity, I'll leave it out because
		 * I'm not implementing the full logging framework but I'm doing the test which is all
		 * about replacing text not logging.
		 * 
		 * @param e
		 * @param level
		 * @param messages
		 */
		private void print(Throwable e, Level level, Object[] messages){
			switch (level){
				case Trace: System.out.print("TRACE: "); break;
				case Debug: System.out.print("DEBUG: "); break;
				case Info: System.out.print("INFO: "); break;
				case Warn: System.out.print("WARN: "); break;
				case Fatal: System.out.print("FATAL: ");
			}
			System.out.println(StringUtils.combine(messages));
			
			/*
			 *The reason to move printStackTrace method to here
			 *is because when we want to implement logging filter we can
			 *change the only this method.
			 *For example, e.printStackTrace(newWriter);
			 */
			if(e!=null) e.printStackTrace();
		}
		
		@Override
		public void trace(Throwable thrown, Object... messages) {
			print(thrown, Level.Trace, messages);
		}
		
		@Override
		public void trace(Object... messages) {
			print(null, Level.Trace, messages);
		}

		@Override
		public void debug(Throwable thrown, Object... messages) {
			print(thrown, Level.Debug, messages);
		}

		@Override
		public void debug(Object... messages) {
			print(null, Level.Debug, messages);
		}

		@Override
		public void info(Throwable thrown, Object... messages) {
			print(thrown, Level.Info, messages);
		}

		@Override
		public void info(Object... messages) {
			print(null, Level.Info, messages);
		}

		@Override
		public void warn(Throwable thrown, Object... messages) {
			print(thrown, Level.Warn, messages);
		}

		@Override
		public void warn(Object... messages) {
			print(null, Level.Warn, messages);
		}

		@Override
		public void error(Throwable thrown, Object... messages) {
			print(thrown, Level.Error, messages);
		}

		@Override
		public void error(Object... messages) {
			print(null, Level.Error, messages);
		}

		@Override
		public void fatal(Throwable thrown, Object... messages) {
			print(thrown, Level.Fatal, messages);
		}

		@Override
		public void fatal(Object... messages) {
			print(null, Level.Fatal, messages);
		}
		
	}
	
}
