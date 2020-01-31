package org.meanbean.logging;

import java.io.PrintStream;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		return new ConsoleLogger(clazz, System.out);
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
			logger.debug(format, arguments);
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

	private static class ConsoleLogger implements $Logger {

		private Class<?> clazz;
		private PrintStream ps;

		public ConsoleLogger(Class<?> clazz, PrintStream ps) {
			this.clazz = clazz;
			this.ps = ps;
		}

		@Override
		public boolean isTraceEnabled() {
			return true;
		}

		@Override
		public void trace(String format, Object... arguments) {
			log(format, arguments);
		}

		@Override
		public boolean isDebugEnabled() {
			return true;
		}

		@Override
		public void debug(String format, Object... arguments) {
			log(format, arguments);
		}

		@Override
		public boolean isInfoEnabled() {
			return true;
		}

		@Override
		public void info(String format, Object... arguments) {
			log(format, arguments);
		}

		@Override
		public boolean isWarnEnabled() {
			return true;
		}

		@Override
		public void warn(String format, Object... arguments) {
			log(format, arguments);
		}

		@Override
		public boolean isErrorEnabled() {
			return true;
		}

		@Override
		public void error(String format, Object... arguments) {
			log(format, arguments);
		}

		private void log(String fmt, Object... args) {
			String msg = fmt;
			Object lastArg = null;
			for (Object arg : args) {
				lastArg = arg;
				if (msg.contains("{}")) {
					String replacement = Matcher.quoteReplacement(String.valueOf(arg));
					msg = msg.replaceFirst(Pattern.quote("{}"), replacement);
				}
			}

			ps.printf("%s - %s%n", clazz.getSimpleName(), msg);
			if (lastArg instanceof Throwable) {
				((Throwable) lastArg).printStackTrace(ps);
			}
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
			logger.debug(format, arguments);
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
}
