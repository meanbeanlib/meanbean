package org.meanbean.factories;

import org.meanbean.factories.basic.BooleanFactory;
import org.meanbean.factories.basic.ByteFactory;
import org.meanbean.factories.basic.CharacterFactory;
import org.meanbean.factories.basic.DoubleFactory;
import org.meanbean.factories.basic.FloatFactory;
import org.meanbean.factories.basic.IntegerFactory;
import org.meanbean.factories.basic.LongFactory;
import org.meanbean.factories.basic.ShortFactory;
import org.meanbean.util.RandomNumberGenerator;
import org.meanbean.util.RandomNumberGeneratorProvider;

/**
 * Concrete FactoryCollectionPlugin that registers Factories that create Java primitives.
 * 
 * @author Graham Williamson
 */
public class PrimitiveFactoryPlugin implements FactoryCollectionPlugin {

	@Override
	public void initialize(FactoryCollection factoryCollection, RandomNumberGeneratorProvider randomNumberGeneratorProvider) {
		RandomNumberGenerator randomNumberGenerator = randomNumberGeneratorProvider.getRandomNumberGenerator();
		factoryCollection.addFactory(boolean.class, new BooleanFactory(randomNumberGenerator));
		factoryCollection.addFactory(byte.class, new ByteFactory(randomNumberGenerator));
		factoryCollection.addFactory(short.class, new ShortFactory(randomNumberGenerator));
		factoryCollection.addFactory(int.class, new IntegerFactory(randomNumberGenerator));
		factoryCollection.addFactory(long.class, new LongFactory(randomNumberGenerator));
		factoryCollection.addFactory(float.class, new FloatFactory(randomNumberGenerator));
		factoryCollection.addFactory(double.class, new DoubleFactory(randomNumberGenerator));
		factoryCollection.addFactory(char.class, new CharacterFactory(randomNumberGenerator));
	}
}