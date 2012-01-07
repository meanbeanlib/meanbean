package org.meanbean.bean.info;

import java.lang.reflect.Method;

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

	public String getName() {
		return name;
	}

	public boolean isReadable() {
		return isReadable;
	}

	public boolean isWritable() {
		return isWritable;
	}

	public boolean isReadableWritable() {
		return isReadable() && isWritable();
	}

	public Method getReadMethod() {
		return readMethod;
	}

	public Method getWriteMethod() {
		return writeMethod;
	}

	public Class<?> getReadMethodReturnType() {
		return readMethodReturnType;
	}

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
		StringBuilder str = new StringBuilder();
		str.append("name=").append(getName()).append(",");
		str.append("isReadable=").append(isReadable()).append(",");
		str.append("isWritable=").append(isWritable()).append(",");
		str.append("readMethod=").append(getReadMethod()).append(",");
		str.append("writeMethod=").append(getWriteMethod()).append(",");
		str.append("readMethodReturnType=").append(getReadMethodReturnType()).append(",");
		str.append("writeMethodParameterType=").append(getWriteMethodParameterType());
		str.append("]");
		return str.toString();
	}
}