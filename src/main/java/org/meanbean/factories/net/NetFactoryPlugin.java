package org.meanbean.factories.net;

import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryCollectionPlugin;
import org.meanbean.util.RandomValueGenerator;

import java.net.URI;
import java.net.URL;

public final class NetFactoryPlugin implements FactoryCollectionPlugin {

	@Override
	public void initialize(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator) {
		UrlFactory urlFactory = new UrlFactory(randomValueGenerator);
		factoryCollection.addFactory(URL.class, urlFactory);
		factoryCollection.addFactory(URI.class, () -> URI.create(urlFactory.create().toString()));
	}

}