package org.meanbean.factories;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueGeneratorProvider;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class ConcurrentFactoryPlugin implements FactoryCollectionPlugin {

    @Override
    public void initialize(FactoryCollection factoryCollection,
            RandomValueGeneratorProvider randomValueGeneratorProvider) {
        RandomValueGenerator randomValueGenerator = randomValueGeneratorProvider.getRandomValueGenerator();
        factoryCollection.addFactory(AtomicInteger.class, newFactory(randomValueGenerator::nextInt, AtomicInteger::new));
        factoryCollection.addFactory(AtomicLong.class, newFactory(randomValueGenerator::nextLong, AtomicLong::new));
        factoryCollection.addFactory(AtomicBoolean.class, () -> new AtomicBoolean(randomValueGenerator.nextBoolean()));
    }

    private <A extends Number, N extends Number> Factory<A> newFactory(Factory<N> factory, Function<N, A> fn) {
        return () -> fn.apply(factory.create());
    }
}