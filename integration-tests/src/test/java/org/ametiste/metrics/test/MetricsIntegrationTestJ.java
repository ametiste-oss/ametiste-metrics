package org.ametiste.metrics.test;

import org.ametiste.metrics.mock.aggregator.MockMetricsAggregator;
import org.ametiste.metrics.test.configuration.MetricTestBoot;
import org.ametiste.metrics.test.configuration.MetricTestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import spock.lang.Shared;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by atlantis on 11/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("server.port:0")
@SpringApplicationConfiguration(classes = {MetricTestBoot.class, MetricTestConfiguration.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@TestPropertySource(properties = "org.ametiste.metrics.prefix=prefix")
public class MetricsIntegrationTestJ {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    @Qualifier("first")
    private MockMetricsAggregator firstAggregator;

    @Autowired
    @Qualifier("second")
    private MockMetricsAggregator secondAggregator;

    @Shared
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        firstAggregator.resetData();
        secondAggregator.resetData();
    }

    @Test
    public void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk());
        firstAggregator.verify("prefix.time_metric").registered();
        secondAggregator.verify("prefix.time_metric").notRegistered(); // should not be in configuration of service
    }
}
