package org.meanbean.beans.factories;

import org.meanbean.beans.Gender;
import org.meanbean.factories.basic.RandomFactoryBase;
import org.meanbean.util.RandomValueGenerator;

public class GenderFactory extends RandomFactoryBase<Gender> {
	
	private static final long serialVersionUID = 1L;

	public GenderFactory(RandomValueGenerator randomValueGenerator) {
		super(randomValueGenerator);
	}
	
	@Override
	public Gender create() {
        double random = getRandomValueGenerator().nextDouble();
        int ordinal = ((int) ((Gender.values().length - 1) * random));
		return Gender.values()[ordinal];
	}
}