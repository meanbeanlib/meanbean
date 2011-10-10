package org.meanbean.factories;

import java.math.BigDecimal;
import java.util.Date;

import org.meanbean.factories.basic.BigDecimalFactory;
import org.meanbean.factories.basic.BooleanFactory;
import org.meanbean.factories.basic.ByteFactory;
import org.meanbean.factories.basic.CharacterFactory;
import org.meanbean.factories.basic.DateFactory;
import org.meanbean.factories.basic.DoubleFactory;
import org.meanbean.factories.basic.FloatFactory;
import org.meanbean.factories.basic.IntegerFactory;
import org.meanbean.factories.basic.LongFactory;
import org.meanbean.factories.basic.ShortFactory;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueGeneratorProvider;

/**
 * Concrete FactoryCollectionPlugin that registers Factories that create basic Java wrapper objects.
 * 
 * @author Graham Williamson
 */
public class ObjectFactoryPlugin implements FactoryCollectionPlugin {

	/**
	 * Initialize the plugin, adding Factories to the FactoryCollection.
	 * 
	 * @param factoryCollection
	 *            A FactoryCollection that Factory objects can be added to.
	 * @param randomValueGeneratorProvider
	 *            A RandomValueGeneratorProvider that provides access to a RandomValueGenerator that can be used by
	 *            Factory objects.
	 */
	@Override
	public void initialize(FactoryCollection factoryCollection,
	        RandomValueGeneratorProvider randomValueGeneratorProvider) {
		RandomValueGenerator randomValueGenerator = randomValueGeneratorProvider.getRandomValueGenerator();
		factoryCollection.addFactory(Boolean.class, new BooleanFactory(randomValueGenerator));
		factoryCollection.addFactory(Byte.class, new ByteFactory(randomValueGenerator));
		factoryCollection.addFactory(Short.class, new ShortFactory(randomValueGenerator));
		factoryCollection.addFactory(Integer.class, new IntegerFactory(randomValueGenerator));
		factoryCollection.addFactory(Long.class, new LongFactory(randomValueGenerator));
		factoryCollection.addFactory(Float.class, new FloatFactory(randomValueGenerator));
		factoryCollection.addFactory(Double.class, new DoubleFactory(randomValueGenerator));
		factoryCollection.addFactory(BigDecimal.class, new BigDecimalFactory(randomValueGenerator));
		factoryCollection.addFactory(Character.class, new CharacterFactory(randomValueGenerator));
		factoryCollection.addFactory(String.class, new StringFactory(randomValueGenerator));
		factoryCollection.addFactory(Date.class, new DateFactory(randomValueGenerator));
	}
}