package org.meanbean.factories;

import java.util.Date;

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
import org.meanbean.util.RandomNumberGenerator;
import org.meanbean.util.RandomNumberGeneratorProvider;

/**
 * Concrete FactoryCollectionPlugin that registers Factories that create basic Java wrapper objects.
 * 
 * @author Graham Williamson
 */
public class ObjectFactoryPlugin implements FactoryCollectionPlugin {

	@Override
	public void initialize(FactoryCollection factoryCollection, RandomNumberGeneratorProvider randomNumberGeneratorProvider) {
		RandomNumberGenerator randomNumberGenerator = randomNumberGeneratorProvider.getRandomNumberGenerator();
		factoryCollection.addFactory(Boolean.class, new BooleanFactory(randomNumberGenerator));
        factoryCollection.addFactory(Byte.class, new ByteFactory(randomNumberGenerator));
        factoryCollection.addFactory(Short.class, new ShortFactory(randomNumberGenerator));
        factoryCollection.addFactory(Integer.class, new IntegerFactory(randomNumberGenerator));
        factoryCollection.addFactory(Long.class, new LongFactory(randomNumberGenerator));
        factoryCollection.addFactory(Float.class, new FloatFactory(randomNumberGenerator));
        factoryCollection.addFactory(Double.class, new DoubleFactory(randomNumberGenerator));
        factoryCollection.addFactory(Character.class, new CharacterFactory(randomNumberGenerator));
        factoryCollection.addFactory(String.class, new StringFactory(randomNumberGenerator));
        factoryCollection.addFactory(Date.class, new DateFactory(randomNumberGenerator));
	}
}