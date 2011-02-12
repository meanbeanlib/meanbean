package org.meanbean.factories;

import org.meanbean.factories.basic.BooleanFactory;
import org.meanbean.factories.basic.ByteFactory;
import org.meanbean.factories.basic.CharacterFactory;
import org.meanbean.factories.basic.DoubleFactory;
import org.meanbean.factories.basic.FloatFactory;
import org.meanbean.factories.basic.IntegerFactory;
import org.meanbean.factories.basic.LongFactory;
import org.meanbean.factories.basic.ShortFactory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueGeneratorProvider;

/**
 * Concrete FactoryCollectionPlugin that registers Factories that create Java primitives.
 * 
 * @author Graham Williamson
 */
public class PrimitiveFactoryPlugin implements FactoryCollectionPlugin {

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
		factoryCollection.addFactory(boolean.class, new BooleanFactory(randomValueGenerator));
		factoryCollection.addFactory(byte.class, new ByteFactory(randomValueGenerator));
		factoryCollection.addFactory(short.class, new ShortFactory(randomValueGenerator));
		factoryCollection.addFactory(int.class, new IntegerFactory(randomValueGenerator));
		factoryCollection.addFactory(long.class, new LongFactory(randomValueGenerator));
		factoryCollection.addFactory(float.class, new FloatFactory(randomValueGenerator));
		factoryCollection.addFactory(double.class, new DoubleFactory(randomValueGenerator));
		factoryCollection.addFactory(char.class, new CharacterFactory(randomValueGenerator));
	}
}