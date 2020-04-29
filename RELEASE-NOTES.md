# MeanBean Release Notes

## 3.0.0-M6
- Rename BeanVerification to BeanVerifier
- Add mechanism for suppressing warnings.
- Detect setter methods that have side-effects on other properties. e.g. a setter method that writes to two properties
Use Warning.SETTER_SIDE_EFFECT to disable detection and resulting failure.
- Add support for Optional, OptionalInt/Long/Double
- Add ability to suppress warning about dynamically created factory logging
- Build and test meanbean with Java 1.8 and 11

## 3.0.0-M5
- Fix bug running meanbean in Java 11 due to ConcurrentHashMap changes

## 3.0.0-M4
- Add BeanVerification interface to replace BeanVerifications class

## 3.0.0-M3
- Import Guava ClassPath for scanning classpath. Users can test all beans in a given package.
- Add BeanVerifications for fluent assertions
- Add simple toString method tester
- Fix bug in File/Path factory


## 3.0.0-M2

This is the first public release of meanbean v3. Maven groupId is now com.github.meanbeanlib and Java 8 is required.

Changes:
- Do not repeatedly warn about "dynamically created factory"
- Add BeanTesterBuilder for customizing BeanTester
- Add custom logger that delegates to slf4j or log4j2 if possible
- Add ServiceLoader mechanism for creating implementation instances of interfaces. Users may take advantage of that to
override default meanbean implementations.
- Add support for Array/Collection/Map of arbitrary element types and dimension. Previously the element type was always 
String and dimension was one. Support for more Collection/Map instance types.
- Add FactoryLookup interface for dynamically defining Factory at runtime. 
- Add ability to register Factory for a type hierarchy 
- Log warning if a property defines URL which requires .equals test that may be slow.
- Allow method references as property specifiers. (e.g. User::getName vs "Name")