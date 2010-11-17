package org.meanbean.bean.info;

import java.lang.reflect.Method;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Simple JavaBean implementation of the PropertyInformation interface. This should only be used in testing.
 * 
 * @author Graham Williamson
 */
public class PropertyInformationBean implements PropertyInformation {

	private String name;

	private boolean isReadable;

	private boolean isWritable;

	private Method readMethod;

	private Method writeMethod;

	private Class<?> readMethodReturnType;

	private Class<?> writeMethodParameterType;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isReadable() {
		return isReadable;
	}

	@Override
	public boolean isWritable() {
		return isWritable;
	}

	@Override
	public boolean isReadableWritable() {
		return isReadable() && isWritable();
	}

	@Override
	public Method getReadMethod() {
		return readMethod;
	}

	@Override
	public Method getWriteMethod() {
		return writeMethod;
	}

	@Override
	public Class<?> getReadMethodReturnType() {
		return readMethodReturnType;
	}

	@Override
	public Class<?> getWriteMethodParameterType() throws IllegalArgumentException {
		return writeMethodParameterType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReadable(boolean isReadable) {
		this.isReadable = isReadable;
	}

	public void setWritable(boolean isWritable) {
		this.isWritable = isWritable;
	}

	public void setReadMethod(Method readMethod) {
		this.readMethod = readMethod;
	}

	public void setWriteMethod(Method writeMethod) {
		this.writeMethod = writeMethod;
	}

	public void setReadMethodReturnType(Class<?> readMethodReturnType) {
		this.readMethodReturnType = readMethodReturnType;
	}

	public void setWriteMethodParameterType(Class<?> writeMethodParameterType) {
		this.writeMethodParameterType = writeMethodParameterType;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(name).append(isReadable)
		        .append(isWritable).append(readMethod).append(writeMethod).toString();
	}
}