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

import org.meanbean.lang.EquivalentFactory;

/**
 * Factory that creates logically equivalent SelfReferencingBean instances. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class SelfReferencingBeanFactory implements EquivalentFactory<SelfReferencingBean> {

	private static final SelfReferencingBean parent = new SelfReferencingBean();
	static {
		parent.setFirstName("PARENT_FIRST_NAME");
	}

	@Override
    public SelfReferencingBean create() {
		SelfReferencingBean bean = new SelfReferencingBean();
		bean.setFirstName("TEST_FIRST_NAME");
		bean.setLastName("TEST_LAST_NAME");
		bean.setParent(parent);
		return bean;
	}
}
