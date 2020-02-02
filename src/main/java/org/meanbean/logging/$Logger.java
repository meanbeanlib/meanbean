package org.meanbean.logging;

/**
 * Internal facade around well-known logging facades
 */
public interface $Logger {
	public boolean isTraceEnabled();

	public void trace(String format, Object... arguments);

	public boolean isDebugEnabled();

	public void debug(String format, Object... arguments);

	public boolean isInfoEnabled();

	public void info(String format, Object... arguments);

	public boolean isWarnEnabled();

	public void warn(String format, Object... arguments);

	public boolean isErrorEnabled();

	public void error(String format, Object... arguments);

}
