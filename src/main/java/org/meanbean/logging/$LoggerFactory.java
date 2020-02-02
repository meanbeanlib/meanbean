package org.meanbean.logging;

import java.util.function.Function;

/**
 * Creates internal logger, favoring slf4j and log4j2, before falling back to null/no-op logger
 */
public class $LoggerFactory {

	private static $LoggerFactory INSTANCE = new $LoggerFactory();

	public static $Logger getLogger(Class<?> clazz) {
		return INSTANCE.createLogger(clazz);
	}

	protected $Logger createLogger(Class<?> clazz) {
		return createLogger(clazz,
				Slf4jLogger::new,
				Log4j2Logger::new);
	}

	@SafeVarargs
	private static $Logger createLogger(Class<?> clazz, Function<Class<?>, $Logger>... factories) {
		for (Function<Class<?>, $Logger> factory : factories) {
			try {
				return factory.apply(clazz);
			} catch (NoClassDefFoundError ignore) {
				// no op
			}
		}
		return new NullLogger();
	}

	private static class Slf4jLogger implements $Logger {

		private final org.slf4j.Logger logger;

		public Slf4jLogger(Class<?> clazz) {
			this.logger = org.slf4j.LoggerFactory.getLogger(clazz);
		}

		@Override
		public boolean isTraceEnabled() {
			return logger.isTraceEnabled();
		}

		@Override
		public void trace(String format, Object... arguments) {
			logger.trace(format, arguments);
		}

		@Override
		public boolean isDebugEnabled() {
			return logger.isDebugEnabled();
		}

		@Override
		public void debug(String format, Object... arguments) {
		}

		@Override
		public boolean isInfoEnabled() {
			return logger.isInfoEnabled();
		}

		@Override
		public void info(String format, Object... arguments) {
			logger.info(format, arguments);
		}

		@Override
		public boolean isWarnEnabled() {
			return logger.isWarnEnabled();
		}

		@Override
		public void warn(String format, Object... arguments) {
			logger.warn(format, arguments);
		}

		@Override
		public boolean isErrorEnabled() {
			return logger.isErrorEnabled();
		}

		@Override
		public void error(String format, Object... arguments) {
			logger.error(format, arguments);
		}

	}

	private static class Log4j2Logger implements $Logger {

		private final org.apache.logging.log4j.Logger logger;

		public Log4j2Logger(Class<?> clazz) {
			this.logger = org.apache.logging.log4j.LogManager.getLogger(clazz);
		}

		@Override
		public boolean isTraceEnabled() {
			return logger.isTraceEnabled();
		}

		@Override
		public void trace(String format, Object... arguments) {
			logger.trace(format, arguments);
		}

		@Override
		public boolean isDebugEnabled() {
			return logger.isDebugEnabled();
		}

		@Override
		public void debug(String format, Object... arguments) {
		}

		@Override
		public boolean isInfoEnabled() {
			return logger.isInfoEnabled();
		}

		@Override
		public void info(String format, Object... arguments) {
			logger.info(format, arguments);
		}

		@Override
		public boolean isWarnEnabled() {
			return logger.isWarnEnabled();
		}

		@Override
		public void warn(String format, Object... arguments) {
			logger.warn(format, arguments);
		}

		@Override
		public boolean isErrorEnabled() {
			return logger.isErrorEnabled();
		}

		@Override
		public void error(String format, Object... arguments) {
			logger.error(format, arguments);
		}

	}

	private static class NullLogger implements $Logger {

		@Override
		public boolean isTraceEnabled() {
			return false;
		}

		@Override
		public void trace(String format, Object... arguments) {
		}

		@Override
		public boolean isDebugEnabled() {
			return false;
		}

		@Override
		public void debug(String format, Object... arguments) {
		}

		@Override
		public boolean isInfoEnabled() {
			return false;
		}

		@Override
		public void info(String format, Object... arguments) {
		}

		@Override
		public boolean isWarnEnabled() {
			return false;
		}

		@Override
		public void warn(String format, Object... arguments) {
		}

		@Override
		public boolean isErrorEnabled() {
			return false;
		}

		@Override
		public void error(String format, Object... arguments) {
		}
	}

}
