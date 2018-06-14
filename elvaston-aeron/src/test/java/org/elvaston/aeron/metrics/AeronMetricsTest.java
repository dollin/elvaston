package org.elvaston.aeron.metrics;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests to check the AeronMetrics.
 */
public class AeronMetricsTest {

    @Test
    public void printMetrics() {
        AeronMetrics aeronMetrics = new AeronMetrics();
        aeronMetrics.print(System.out);

        aeronMetrics.metrics().forEach(am -> System.out.println(am.toString()));
    }

    @Test
    public void allMetricsAreZero() throws Exception {
        AeronMetrics aeronMetrics = new AeronMetrics();
        aeronMetrics.metrics().forEach(aeronMetric -> Assert.assertEquals(0, aeronMetric.value()));
    }
}
