# MeanBean

Automated JavaBean Testing

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.meanbeanlib/meanbean/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.meanbeanlib/meanbean)
 [![javadoc](https://javadoc.io/badge2/com.github.meanbeanlib/meanbean/javadoc.svg)](https://javadoc.io/doc/com.github.meanbeanlib/meanbean) [![Join the chat at https://gitter.im/meanbeanhq/community](https://badges.gitter.im/meanbeanhq/community.svg)](https://gitter.im/meanbeanhq/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) 
![Java CI](https://github.com/meanbeanlib/meanbean/workflows/Java%20CI/badge.svg)

## Welcome

### What is it?

Mean Bean is an open source Java test library that helps you rapidly and reliably test fundamental objects within your <br/>
software system, namely your domain and data objects. Mean Bean:

  - Tests that the getter and setter method pairs of a JavaBean/POJO function correctly.
  - Verifies that the equals and hashCode methods of a class comply with the Equals Contract and HashCode Contract respectively.
  - Verifies property significance in object equality.

### Why should I use it?

Mean Bean helps you rapidly and reliably test fundamental objects within your project, namely your domain and data objects.
With just a single line of code, you can be confident that your beans are well behaved…

	// verify bean getters/setters, equals, hashCode and toString for a single bean type
	BeanVerifier.verifyBean(Company.class);
	
	// verify multiple beans
	BeanVerifier.verifyBeans(Company.class, Employee.class);
	
	// verify beans in the same package
	BeanVerifier.verifyBeansIn(Company.class.getPackage())
	
	// configure settings
	BeanVerifier.forClass(Company.class)
		// override default setting which tests the bean with random values 100 times
		.withSettings(settings -> settings.setDefaultIterations(12))
		// exclude name property in bean getter/setter test
		.withSettings(settings -> settings.addIgnoredProperty(Company::getName))
		.verifyGettersAndSetters()

### Where do I get it?

Mean Bean can be acquired from the <a href="https://maven-badges.herokuapp.com/maven-central/com.github.meanbeanlib/meanbean">Maven Central</a>:

    <dependency>
        <groupId>com.github.meanbeanlib</groupId>
        <artifactId>meanbean</artifactId>
        <version>3.0.0-M6</version>
    </dependency>

### More info?

See [User Guide in wiki](https://github.com/meanbeanlib/meanbean/wiki)

The following shows more usage examples:

	// alternative way to configure settings
	BeanVerifier.forClass(Company.class)
			.editSettings()
			.setDefaultIterations(12)
			.addIgnoredProperty(Company::getId)
			.edited()
			.verifyGettersAndSetters()
			.verifyEqualsAndHashCode();
	
	// ignore Company's Id property from equals and hashCode test
	BeanVerifier.forClass(Company.class)
			.withSettings(settings -> settings.addEqualsInsignificantProperty(Company::getId))
			.verifyEqualsAndHashCode();
	
	// ignore Company's Address property from getter/setter test
	BeanVerifier.forClass(Company.class)
			.withSettings(settings -> settings.addIgnoredProperty(Company::getAddress))
			.verifyEqualsAndHashCode();
	
	// ignore Company's Id property from equals and hashCode test
	BeanVerifier.forClass(Company.class)
			.withSettings(settings -> settings.addEqualsInsignificantProperty(Company::getId))
			.verifyEqualsAndHashCode();
			
	// how to register factory for a non-bean type (e.g. one without zero-arg constructor) 
	BeanVerifier.forClass(Company.class)
			.withSettings(settings -> settings.registerFactory(NonBean.class, nonBeanFactory))
			.verifyGettersAndSetters();
	
	// how to re-use the same settings across your team
	BeanVerifier.forClass(Company.class)
			.withSettings(myTeamsSharedSettings()) // use the team's shared settings
			.verify();

Users familiar with meanbean v2 may continue using that api:

	// meanbean v2 api to verify bean getters/setters
	new BeanTester().testBean(User.class);
	
### License

MeanBean is released under the Apache 2.0 license.

```
Copyright 2010-2020.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
