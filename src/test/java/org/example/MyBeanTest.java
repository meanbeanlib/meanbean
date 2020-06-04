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

package org.example;

import org.example.Message.MessageData;
import org.junit.Test;
import org.meanbean.test.BeanVerifier;
import org.meanbean.util.RandomValueGenerator;

public class MyBeanTest {

	@Test
	public void test() {
		BeanVerifier.forClass(MyBean.class)
				.editSettings()
				.registerFactory(MessageData.class, () -> {
					RandomValueGenerator randomValueGenerator = RandomValueGenerator.getInstance();
					byte[] randomBytes = randomValueGenerator.nextBytes(100);
					return new MessageData(randomBytes);
				})
				.edited()
				.verify();
	}
}
