package org.elvaston.kafka.common;

import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
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
            LOG.info("polling {} metrics: {}", type(), metrics());
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
        LOG.info("shutdown {} metrics: {}", type(), metrics());
    }
}
