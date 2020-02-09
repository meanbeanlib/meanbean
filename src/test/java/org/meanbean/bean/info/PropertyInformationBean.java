/*-
 * ​​​
 * meanbean
 * ⁣⁣⁣
 * Copyright (C) 2010 - 2020 the original author or authors.
 * ⁣⁣⁣
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ﻿﻿﻿﻿﻿
 */

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
