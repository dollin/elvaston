package org.elvaston.kafka.metrics;

import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.elvaston.kafka.common.KafkaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * KafkaMetrics w/ common logic used by both Producer and Consumer.
 */
public abstract class KafkaMetrics implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaMetrics.class);

    private volatile boolean running = true;

    protected abstract Map<MetricName, ? extends Metric> metrics();

    protected abstract String type();

    /**
     * Stop metrics thread from reporting and logging metrics.
     */
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            logMetrics();
            KafkaUtils.sleep(TimeUnit.SECONDS, 10);
        }
        logMetrics();
    }

    private void logMetrics() {
        metrics().forEach((metricName, metric) ->
                LOG.info("{} metric name: {} value: {}", type(), metricName.name(), metric.metricValue()));
    }
}
