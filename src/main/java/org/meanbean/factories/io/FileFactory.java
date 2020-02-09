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

package org.meanbean.factories.io;

import org.kohsuke.MetaInfServices;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryCollectionPlugin;
import org.meanbean.util.RandomValueGenerator;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

@MetaInfServices(FactoryCollectionPlugin.class)
public class FileFactory implements FactoryCollectionPlugin {

	@Override
	public void initialize(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator) {
		factoryCollection.addFactory(File.class, this::generateTempFile);
		factoryCollection.addFactory(File.class, this::generateTempPath);
	}

	private File generateTempFile() {
		return generateTempPath().toFile();
	}

	private Path generateTempPath() {
		try {
			Path path = Files.createTempFile("mean-bean-file-factory-", ".txt");
			Files.delete(path);
			return path;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
