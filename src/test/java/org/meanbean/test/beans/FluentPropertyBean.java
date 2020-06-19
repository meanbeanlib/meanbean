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

package org.meanbean.test.beans;

import java.util.Objects;

public class FluentPropertyBean {

	private String data;

	public String getData() {
		return data;
	}

	public FluentPropertyBean setData(String data) {
		this.data = data;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FluentPropertyBean)) {
			return false;
		}
		FluentPropertyBean other = (FluentPropertyBean) obj;
		return Objects.equals(data, other.data);
	}

	@Override
	public int hashCode() {
		return Objects.hash(data);
	}
	
	@Override
	public String toString() {
		return data;
	}
}
