package org.meanbean.factories.util;

import org.kohsuke.MetaInfServices;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryCollectionPlugin;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueSampler;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@MetaInfServices(FactoryCollectionPlugin.class)
public class LocaleFactory implements Factory<Locale>, FactoryCollectionPlugin {

	private RandomValueGenerator randomValueGenerator = RandomValueGenerator.getInstance();
	private RandomValueSampler randomValueSampler = new RandomValueSampler(randomValueGenerator);

	@Override
	public Locale create() {
		List<Locale> locales = Arrays.asList(Locale.getAvailableLocales());
		return randomValueSampler.getFrom(locales);
	}

	@Override
	public void initialize(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator) {
		factoryCollection.addFactory(Locale.class, this);
	}
}
