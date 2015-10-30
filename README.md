#Ametiste metrics library

## Build Status

[![Build Status](https://travis-ci.org/ametiste-oss/ametiste-metrics.svg?branch=master)](https://travis-ci.org/ametiste-oss/ametiste-metrics)
[![Codacy Badge](https://api.codacy.com/project/badge/fdb7b072ff264edcb0bbc72c5ade3b53)](https://www.codacy.com/app/Ametiste-OSS/ametiste-metrics)

##Overview

Ametiste metrics is a library for easy metrics registration from any point of client code.

## Table Of Contents
* [Documentation](#documentation)
* [Usage example](#usage-example)
* [Binaries](#binaries)
  * [Usage snippets](#usage-snippets)
    * [Gradle](#gradle)
    * [Maven](#maven)
  * [Metrics service with spring boot](#metrics-service-with-spring-boot)
  * [Metrics service without aop support](#metrics-service-without-aop-support)
  * [Metrics service with aop support](#metrics-service-with-aop-support)
  * [Child projects and libraries with only annotations required](#child-projects-and-libraries-with-only-annotations-required)
* [Annotation examples](#annotation-examples)

##Documentation 
Library at a glance is described here, for more detailed description view [wiki](https://github.com/ametiste-oss/ametiste-metrics/wiki)

##Usage example

To register a metric for a method, one of following annotations may be applied to it:

- _@Countable_ 
- _@ErrorCountable_
- _@Timeable_
- _@Chronable_
- _@Gaugeable_

_@Countable_ - `org.ametiste.metrics.annotations.Countable` is used when incremental count is required - on any joint point, such is request count, method call count, and so on. Only counts number of successfully executed operations.

_@ErrorCountable_ - `org.ametiste.metrics.annotations.ErrorCountable` is used when count of executions completed with exceptions is required - works similarly to @Countable but for joint points ended with errors.

_@Timeable_ - `org.ametiste.metrics.annotations.Timeable` is used when method execution time is required.

_@Chronable_ - `org.ametiste.metrics.annotations.Chronable` is used to chronate data from flow. Value of chronable data is regulated by its value or value expression, i.e. can be any event data that is to be saved in timeline (method arguments or execution result numerical values, counts or phase of moon, etc)

_@Gaugeable_ `org.ametiste.metrics.annotations.Gaugeable` is used to measure executions with gauge. Only  successful operations are measured. 

Example:
```java
@Timeable(name=“my.neat.metric")
public void justAMethod(String parameter) {
}
```

For further annotation usage details view  [Annotation examples](#annotation-examples)

Metrics library can be used directly, without annotations, by injecting metrics service to client code.
For more detailed library description and custom configuration view [Ametiste metrics wiki](https://github.com/ametiste-oss/ametiste-metrics/wiki)

##Binaries
All mentioned dependencies is accessible at bintray central.

####Usage snippets

#####Gradle

```
repositories {
     mavenCentral()
     jcenter()
}
compile "org.ametiste.metrics:metrics-boot:${metricsVersion}"
```

#####Maven

```
<repositories>
    <repository>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
      <id>central</id>
      <name>bintray</name>
      <url>http://jcenter.bintray.com</url>
    </repository> 
</repositories>

<dependency>
	<groupId>org.ametiste.metrics</groupId>
	<artifactId>metrics-boot</artifactId>
	<version>metrics.version</version>
</dependency>
```

Aspects and metric service should be configured to start metrics annotations usage There are several ways to use metrics service, from easiest to most specific. 

###Metrics service with spring boot

Adding `'org.ametiste.metrics:metrics-boot:{metricsVersion}'` dependency in classpath of spring boot project provides it with default most commonly used configuration, and if autoconfiguration is enabled, it requires no further actions for usage. For more details view [Installation and configuration](https://github.com/ametiste-oss/ametiste-metrics/wiki/Installation-and-configuration) wiki page

###Metrics service without aop support

In case if metrics service is required without aop support, `'org.ametiste.metrics:metrics-service:{metricsVersion}'` is satisfying dependency, however it has no default configuration included.

###Metrics service with aop support

For annotations usage out of spring boot context, dependency  `'org.ametiste.metrics:metrics-aop:{metricsVersion}'` is satisfying, however required aspects and metrics service should be configured, no default configuration is provided.

###Child projects and libraries with only annotations required

When library or separate part of project requires annotations only, then import of `'org.ametiste.metrics:metrics-annotations:{metricsVersion}'` may be used.


##Annotation examples

Parameter-dependent count metric, with parameter="cat" passed in method registers metric with name "my.neat.metric.cat", while with parameter="dog", metric is registered with name "my.neat.metric.dog"
```java
@Countable(name="my.neat.metric", nameSuffixExpression="args[0]")
public void letsCount(String parameter) {
}
```

Registers method execution time with name "my.amazing.metric"
```java
@Timeable(name="my.amazing.metric")
public void letsTime(String parameter) {
}
```

Counts error happen in method and save them with metric name (if parameter="cat") "cat.my.useful.metric".
Note, that though name and name suffix are separated by point automatically, in expression it should be expression consern to provide readable and good-looking name.
```java
@ErrorCountable(nameSuffixExpression="args[0] + '.my.useful.metric'")
public void letsError(String parameter) {
}
```

_@Chronable_ is described more detailed.

With parameter "cat” registers value "3" for metric with name  "cat.my.amazing.metric"
```java
@Chronable(valueExpression="args[0].length()", nameSuffixExpression="args[0] + '.my.amazing.metric'")
public String letsChron(String parameter) {
    return "mew mew";
}
```

Does not register metric when parameter equals “dog", with any other parameter registers value "6" (7 as length of returned sting - 1) for metric with name  "my.cats.metric"
```java
@Chronable(valueExpression="result.length() - 1", name="my.cats.metric", condition="args[0]!='dog'")
public String letsChron(String parameter) {
    return "mew mew";
}
```

 With parameter “dog" registers value "3" to metric with name "my.cats.metric.exceptions”. If method ends normally, no metric is registered.
```java
@Chronable(valueExpression="args[0].length()", name="my.cats.metric.exceptions", exceptionClass=DogException.class)
public String letsChron(String parameter) {
    if(parameter.equals(“dog")) {
        throw new CatException("Oh noes, its a cat!");
    }
    return "mew mew";
}
```

