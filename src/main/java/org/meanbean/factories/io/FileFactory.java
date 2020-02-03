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
