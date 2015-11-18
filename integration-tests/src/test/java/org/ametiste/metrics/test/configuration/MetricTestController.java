package org.ametiste.metrics.test.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by atlantis on 11/7/15.
 */
@RestController
public class MetricTestController {

    @Autowired
    private Service service;

    @RequestMapping(value = "/")
    public void get() {
        service.time();
    }

}
