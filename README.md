# MeanBean

Automated JavaBean Testing

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

    new BeanTester().testBean(MyDomainObject.class);

### Where do I get it?

Mean Bean can be acquired from the Maven Central repo.

    <dependency>
        <groupId>org.meanbean</groupId>
        <artifactId>meanbean</artifactId>
        <version>2.0.3</version>
    </dependency>
