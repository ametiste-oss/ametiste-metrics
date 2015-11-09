package org.ametiste.metrics.test

import org.ametiste.metrics.mock.MockMetricsService
import org.ametiste.metrics.test.configuration.MetricTestConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

/**
 * Created by atlantis on 11/7/15.
 */

//@ContextConfiguration(loader = SpringApplicationContextLoader.class,
//        classes = MetricTestConfiguration.class)
//@SpringApplicationConfiguration(classes =MetricTestConfiguration.class)
//@WebAppConfiguration
//@TestExecutionListeners( DependencyInjectionTestExecutionListener.class )
//@IntegrationTest
class MetricsIntegrationTest extends Specification {
//
//    @Autowired
//    private WebApplicationContext wac
//
//    @Autowired
//    @Qualifier("mockService")
//    private MockMetricsService service
//
//    @Shared
//    private MockMvc mockMvc
//
//
//    def setup() {
//       mockMvc = webAppContextSetup(this.wac).build()
//    }
//
//    def test(){
//        given:""
//        when: ""
//            mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk())
//        then: ""
//         //   service.verify("time_metric").registered();
//
//    }
}
