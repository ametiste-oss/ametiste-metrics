package org.ametiste.metrics.test.configuration;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Import;

/**
 * Created by atlantis on 11/7/15.
 */

@Import(MetricTestConfiguration.class)
@SpringBootApplication
public class MetricTestBoot {

    public static void main(String[] args) {
        SpringApplication.run(MetricTestBoot.class, args);
    }

}
