package org.meanbean.beans.factories;

import org.meanbean.beans.Gender;
import org.meanbean.factories.basic.FactoryBase;
import org.meanbean.util.RandomNumberGenerator;

public class GenderFactory extends FactoryBase<Gender> {
	
	private static final long serialVersionUID = 1L;

	public GenderFactory(RandomNumberGenerator randomNumberGenerator) {
		super(randomNumberGenerator);
	}
	
	@Override
	public Gender create() {
        double random = getRandomNumberGenerator().nextDouble();
        int ordinal = ((int) ((Gender.values().length - 1) * random));
		return Gender.values()[ordinal];
	}
}