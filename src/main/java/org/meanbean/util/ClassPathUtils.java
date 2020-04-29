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

package org.meanbean.util;

import org.meanbean.test.BeanVerifier;
import org.meanbean.util.ClassPath.ClassInfo;

import java.lang.reflect.Modifier;
import java.util.Set;

public final class ClassPathUtils {

	public static Class<?>[] findClassesIn(String packageName) {
		ClassPath classPath = ClassPath.from(BeanVerifier.class);
		Set<ClassInfo> classInfoSet = classPath.getTopLevelClassesRecursive(packageName);
		return classInfoSet.stream()
				.map(ClassInfo::load)
				.filter(clazz -> !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()))
				.toArray(Class<?>[]::new);
	}
}
