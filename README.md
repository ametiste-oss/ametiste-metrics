#Ametiste metrics library

## Build Status

**CI Statuses**  

[![Build Status](https://travis-ci.org/ametiste-oss/ametiste-metrics.svg?branch=master)](https://travis-ci.org/ametiste-oss/ametiste-metrics)
[![Codacy Badge](https://api.codacy.com/project/badge/fdb7b072ff264edcb0bbc72c5ade3b53)](https://www.codacy.com/app/Ametiste-OSS/ametiste-metrics)
[![codecov.io](https://codecov.io/github/ametiste-oss/ametiste-metrics/coverage.svg?branch=master)](https://codecov.io/github/ametiste-oss/ametiste-metrics?branch=master)  

**Latest Stable**  
  
[![Download](https://api.bintray.com/packages/ametiste-oss/maven/metrics-boot/images/download.svg?version=0.1.0-RELEASE)](https://bintray.com/ametiste-oss/maven/metrics-boot/0.2.0-RELEASE)

**Current Development** 
  
[ ![Download](https://api.bintray.com/packages/ametiste-oss/maven/metrics-default-starter/images/download.svg) ](https://bintray.com/ametiste-oss/maven/metrics-default-starter/_latestVersion)

##Overview
Ametiste metrics is a library for easy metrics registration from any point of client code.

##Documentation 
Library at a glance is described here, for more detailed description view [wiki](https://github.com/ametiste-oss/ametiste-metrics/wiki)

##Usage example

To register a metric for a method, one of following annotations may be applied to it:

- _@Countable_ 
- _@ErrorCountable_
- _@Timeable_
- _@Chronable_
- _@Gaugeable_

_@Countable_ is used when incremental count is required - on any joint point, such is request count, method call count, and so on. Only counts number of successfully executed operations.

_@ErrorCountable_ is used when count of executions completed with exceptions is required - works similarly to @Countable but for joint points ended with errors.

_@Timeable_ is used when method execution time is required.

_@Chronable_ is used to chronate data from flow. Value of chronable data is regulated by its value or value expression, i.e. can be any event data that is to be saved in timeline (method arguments or execution result numerical values, counts or phase of moon, etc)

_@Gaugeable_ is used to measure executions with gauge. Only  successful operations are measured. 

Example:
```java
@Timeable(name="my.neat.metric")
public void justAMethod(String parameter) {
}
```

For further annotation usage details view  [Annotation examples](https://github.com/ametiste-oss/ametiste-metrics/wiki/Annotations-examples)

Metrics library can be used directly, without annotations, by injecting metrics service to client code.
For more detailed library description and custom configuration view [Ametiste metrics wiki](https://github.com/ametiste-oss/ametiste-metrics/wiki)

##Binaries
All non experimental dependencies is accessible at bintray central.

####Usage snippets

#####Gradle

```
repositories {
     mavenCentral()
     jcenter()
}
compile ""org.ametiste.metrics:metrics-default-starter:${metricsVersion}"
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
	<artifactId>metrics-default-starter</artifactId>
	<version>metrics.version</version>
</dependency>
```

Aspects and metric service should be configured to start using metrics annotations. There are several ways to use metrics service, from easiest to most specific. 

###Metrics service with spring boot

Adding `'org.ametiste.metrics:metrics-default-starter:{metricsVersion}'` dependency in classpath of spring boot project provides it with default most commonly used configuration, and if autoconfiguration is enabled, it requires no further actions for usage. For more details and alternative usages view [Installation and configuration](https://github.com/ametiste-oss/ametiste-metrics/wiki/Installation-and-configuration) wiki page

