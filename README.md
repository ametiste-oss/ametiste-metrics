#Ametiste metrics library

##Overview

Ametiste metrics is a library for easy metrics registration from any point of client code.

## Table Of Contents
  * [Ametiste metrics library](#ametiste-metrics-library)
    * [Overview](#overview)
    * [Usage example](#usage-example)
    * [Glossary](#glossary)
    * [Binaries](#binaries)
        * [Usage snippets](#usage-snippets)
          * [Gradle](#gradle)
          * [Maven](#maven)
      * [Metrics service with spring boot](#metrics-service-with-spring-boot)
      * [Metrics service without aop support](#metrics-service-without-aop-support)
      * [Metrics service with aop support](#metrics-service-with-aop-support)
      * [Child projects and libraries with only annotations required](#child-projects-and-libraries-with-only-annotations-required)
    * [Optional properties customisation](#optional-properties-customisation)
    * [Default configuration further tuning](#default-configuration-further-tuning)
    * [Annotations usage](#annotations-usage)
    * [Annotation examples](#annotation-examples)
    * [Identifier resolving](#identifier-resolving)
    * [Metrics routing](#metrics-routing)
    * [Integration Testing](#integration-testing)
          * [Usage snippet](#usage-snippet)

##Usage example

To register a metric for a method, one of following annotations may be applied to it:

- _@Countable_ 
- _@ErrorCountable_
- _@Timeable_
- _@Chronable_

_@Countable_ - `org.ametiste.metrics.annotations.Countable` is used when incremental count is required - on any joint point, such is request count, method call count, and so on. Only counts   number of successfully executed operations.

_@ErrorCountable_ - `org.ametiste.metrics.annotations.ErrorCountable` is used when count of executions completed with exceptions is required - works similarly to @Countable but for joint points ended with errors.

_@Timeable_ - `org.ametiste.metrics.annotations.Timeable` is used when method execution time is required.

_@Chronable_ - `org.ametiste.metrics.annotations.Chronable` is used to chronate data from flow. Value of chronable data is regulated by its value or value expression, i.e. can be any event data that is to be saved in timeline (method arguments or execution result numerical values, counts or phase of moon, etc)

Example:
```java
@Timeable(name=“my.neat.metric")
public void justAMethod(String parameter) {
}
```

For further annotation usage details view [Annotations usage](#annotations-usage) and [Annotation examples](#annotation-examples)

Metrics library can be used directly, without annotations, by injecting metrics service to client code.

##Glossary
- **service** - main point of metrics registration in library
- **aggregator** - in default configuration one of metrics endpoints (by default statsd and jmx)
- **annotations** - method annotations that enable metrics registration for that method
- **name** -  metrics logical role or description in boundaries of separate service
- **prefix** - metrics common domain for separate configured service
- **identifier** -  name with that metrics is registered in endpoints

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

Adding `'org.ametiste.metrics:metrics-boot:{metricsVersion}'` dependency in classpath of spring boot project provides it with default most commonly used configuration, and if autoconfiguration is enabled, it requires no further actions for usage. For more details view [Optional properties customisation] (#optional-properties-customisation) and [Default configuration further tuning] (#default-configuration-further-tuning) sections

###Metrics service without aop support

In case if metrics service is required without aop support, `'org.ametiste.metrics:metrics-service:{metricsVersion}'` is satisfying dependency, however it has no default configuration included.

###Metrics service with aop support

For annotations usage out of spring boot context, dependency  `'org.ametiste.metrics:metrics-aop:{metricsVersion}'` is satisfying, however required aspects and metrics service should be configured, no default configuration is provided.

###Child projects and libraries with only annotations required

For annotations only `'org.ametiste.metrics:metrics-annotations:{metricsVersion}'` may be used.

##Optional properties customisation

Metrics prefix is prefix to all the metrics in a client service. Its both useful to separate different service’s metrics if they are registered in same external place (statsd for example) and to ease the naming. (instead of `org.ametiste.myservice.mymetric` the metric name in annotations or in any other metrics service usage may be shortened to `mymetric`). The default value is empty. You might want to change it:

```
org.ametiste.metrics.prefix=org.ametiste.myservice
```

By default metrics service is configured with jmx and statsd aggregators. They can be disabled by property:

```
org.ametiste.metrics.statsd.enabled=false
org.ametiste.metrics.jmx.enabled=false
```

You might want to disable jmx aggregator in production.

Default statsd port and host are localhost:8125. This can be changed by providing properties:
```
org.ametiste.metrics.statsd.host=graphite.mysite.org
org.ametiste.metrics.statsd.port=8222
```

Default domain for jmx metrics is `org.ametiste.metrics`. It doesnt change prefix though, in jmx each metric is named with full name (prefix + name). Way to customize domain is:

```
org.ametiste.metrics.jmx.domain=mymetrics
```

##Default configuration further tuning

Default metrics service implementation is AggregatingMetricsService, and it is confgured with only default router and two mentioned aggregators. Changing routing and extend aggregators is possible by redefining *metricRoutingMap* and/or *aggregatorsList* beans. 
Its important to remember that *metricRoutingMap* map should contain *"__default"* route key, even if it points to empty aggregators list. More information about metrics routing and identifier resolving is at [Identifier resolving](#identifier-resolving) and [Metrics routing](#metrics-routing)

##Annotations usage

Each annotation contains fields:

| Field name | Type | Default Value | Description |
|------------|-------|---------------|-------------|
| name | String | "" | Constant part of metric name, that doesnt require to be changed. Empty value is valid, and only means that metric name should be built from nameSuffixExpression part. However at least one of these fields should have value. |
| nameSuffixExpression | String | "" | Changeable part of metric name. Empty value is valid. Value represents SpEL expression. Context parts can be used in expression. Context described further |


_@ErrorCountable_ doesn’t contain any other fields.

_@Timeable_ besides name fields also contains:

| Field name | Type | Default Value | Description |
|------------|-------|---------------|-------------|
|mode| MetricsMode | MetricsMode.ERROR_FREE | Defines whether time is registered only for successfully executed method or both for successful and ended with exception |

_@Countable_  specific field is:

| Field name | Type | Default Value | Description |
|------------|-------|---------------|-------------|
| value | int | 1 | Optional field that defines delta of increment for metrics |

_@Chronable_ in addition may contain next fields: 

| Field name | Type | Default Value | Description |
|------------|-------|---------------|-------------|
| value | String | "" | Value that is to be chronated. If value presents, no valueExpression is calculated. Its not integer to allow it to have default value without confusion with real value. At least one of value defines, value or value expression, should not be empty|
| valueExpression | String | "" | SpEL expression of value.The result of expression parsing should be integer, otherwise no metric is registered, however no error happens. At least one of value defines, value or value expression, should not be empty |
| exceptionClass | Class<? extends Exception> | NO_EXCEPTION.class | Defines after throwing of what exception type should metric be taken - required if some data need to be saved in error situation. If Chronable is used for normal method call, should be left to default. If all exceptions should be processed, should be set to other suitable class |
| conditionExpression | String | "'true'" | Condition for metric to be recorded, if false - no name, value or any other context-based operations are done, as well as metric record itself - should be SpEL expression, and required if metric is not taken on every method call |

Context that expressions are evaluated on contains:

**args** - massive of arguments, that target method receives

**target** - object that contains method wrapped with annotation. Via this context part interface of object can be accessed

**result** - result of method execution. Should be used with caution since ErrorCountable, Chronable with exception defined and all of annotations for void methods have null as result, and usage of it in such cases leads to NPE

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


##Identifier resolving

In default configuration registered metrics identifier consists of prefix and value resolved by PlainMetricsIdentifierResolver - that is always metrics name itself.
However there are two other implementations for id resolving are accessible.
`MappingMetricsIdentifierResolver` is useful when metrics should be registered by id different from its name and there’s no access or desire to change metrics name itself.
`PathMetricsIdentifierResolver` is useful for http request paths transformation to metrics identifiers.

##Metrics routing

Different metrics may be routed to different aggregators, since there are often cases when some metrics are required in current time only, and some are useful to be retrospected.
By default metric library uses `MappingAggregatorRouter`, that works with map of metrics identifiers to list of routers. Metric identifier key in router also may contain wildcards, so that gives ability for metrics with definite id or metrics starting with definite id part to be registered with alternative collection of metrics aggregators.
Example of routing redefining:
```java
@Bean
public MapContainer metricRoutingMap() {
    Map<String, ListContainer> map = new HashMap<>();
    map.put("__default", aggregatorsContainer());
    map.put("my.cats.metric.exceptions", jmxAggregatorOnlyContainer());
    map.put(“my.neat.metrics.*", statsdAggregatorOnlyContainer());
    return new MapContainer(map);
}
```
With this routing map all metrics starting from ‘my.neat.metrics.’ are routed to collection with statsd only, while metrics with name ‘my.cats.metric.exceptions’ are routed to aggregator collection named jmxAggregatorOnlyContainer(), and any metric that didnt match those patterns, are routed to default route.

##Integration Testing

For integration testing `MockMetricsService` implementation of MetricsService interface should be used. Its located in separate package for easy test compile include.
To add it to project classpath, use:
```
testCompile "org.ametiste.metrics:metrics-service-mock:${metricsVersion}"
```

Among with interface methods, MockMetricsService provides **`verify(..)`** and **`resetData()`** methods specifically for testing.

**`verify()`**  accepts name of metric that is about to be verified as parameter, and  creates MockMetricVerifier object. On this testing step no assertions are performed and no errors are expected.
After **verify()** was once called, no MetricsService interface methods calls are allowed (i.e no metrics are gathered) and only **verify()** methods could be called before **resetData()** is invoked. This behaviour tends to guarantee metrics gathered are only for one exact integration run, and herefore could be checked for number and for existance in definite circumstances. Its adviced to use **resetData()** invoke in @Before or @After test methods.

#####Usage snippet:

```java
@Autowired
private MockMetricsService metricsService;

@Before
public void setup() throws IOException, URISyntaxException {
    this.mockMvc = webAppContextSetup(this.wac).build();
    metricsService.resetData();
}

@Test
public void testMetrics() throws Exception {
    MockRestServiceServer mockService = MockRestServiceServer.createServer(template);
    mockService
            .expect(org.springframework.test.web.client.match.MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(withSuccess().contentType(MediaType.APPLICATION_JSON).body(reply));

    this.mockMvc
            .perform( get("/query?q=white cat").accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    metricsService.verify(“some.method.timing").registered().event().event(1);
    metricsService.verify("some.other.metric.timing").registered().event().event(1);
    metricsService.verify(“error.counts").registered().increment().increment(1);
    metricsService.verify(“results.number.counts").registered().valueIncrement().valueIncrement(1).valueIncrement(Arrays.asList(15));
    metricsService.verify(“absent.metrics").notRegistered();

}
```


MockMetricsVerifier has several methods accessible to be called in chain, and are to be described in details:

- **registered()** - assert that metric with name indicated in verify() is registered by service. No assertion of metric type happens in this call, only fact it was somehow registered.
- **notRegistered()** - on opposite, asserts that metric with name indicated in verify() not registered by service. It guarantees no metric of ANY type is registered.

- **increment()** - asserts that metric is registered by service as single increment value (Countable or ErrorCountable mostly). No number of calls or values are checked.
- **increment(int times)**  - asserts that metric was registered by service as single increment value times times, no values are checked.
- **increment(List<Long> values)**  - asserts that metric was registered by service as single increment value with given values.

- **valueIncrement()** - asserts that metric is registered by service as definite increment value( Countable with value)
- **valueIncrement(int times)** - asserts that metric is registered by service as definite increment value times times
- **valueIncrement(List<Long> values)** -  asserts that metric was registered by service as definite increment value with given values.

- **event()** - asserts that metric is registered by service as time or chrone measurement (Timeable, Chronable)
- **event(int times)** - asserts that metric is registered by service as time or chrone measurement times times
- **event(List<Long> values)** -  asserts that metric is registered by service as time or chrone measurement with given values. Almost impossible to apply to assert time values, however is useful for chronable values assertion.

Verify methods could be called in chain. i.e. its possible to make step-by-step check by `verify("myMetric").registered().event().event(1).event(Arrays.asList(12));`
That is recommended way to do, since it provides more clear trace in case of assertion errors.
However same result is guaranteed(though less clear and obvious) by  `verify("myMetric").event(Arrays.asList(12));`

There are 4 methods that considered as 'endpoint' ones:
- **notRegistered()**
- **increment(List<Long> values)**
- **valueIncrement(List<Long> values)**
- **event(List<Long> values)**

No futher calls could be done after it. In case of other calls no order is defined, however its obsolete to call registered after concrete metric type verify, or just type verify after times call verify. (i.e call `verify("myMetric").increment().registered();` is obsolete, so as `verify("myMetric").increment(1).increment();`

To avoid test mess there's advice not to check different types of metrics in one chain even if they are named equally.
