# MeanBean

Automated JavaBean Testing

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.meanbeanlib/meanbean/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.meanbeanlib/meanbean)
 [![javadoc](https://javadoc.io/badge2/com.github.meanbeanlib/meanbean/javadoc.svg)](https://javadoc.io/doc/com.github.meanbeanlib/meanbean) 
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
	BeanVerifications.verifyBean(MyBean.class);
		
	// verify beans defined in the same package
	BeanVerifications.verifyBeansIn(MyBean.class.getPackage());
	
	// directly verify only bean getters/setters for a single bean type
	new BeanTester().testBean(MyDomainObject.class);

### Where do I get it?

Mean Bean can be acquired from the <a href="https://maven-badges.herokuapp.com/maven-central/com.github.meanbeanlib/meanbean">Maven Central repo</a>:

    <dependency>
        <groupId>com.github.meanbeanlib</groupId>
        <artifactId>meanbean</artifactId>
        <version>[latest version]</version>
    </dependency>

### More info?

See [User Guide in wiki](https://github.com/meanbeanlib/meanbean/wiki)
