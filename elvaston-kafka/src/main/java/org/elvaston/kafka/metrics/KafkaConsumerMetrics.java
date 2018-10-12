package org.elvaston.kafka.metrics;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;

import java.util.Map;

/**
 * KafkaProducerMetrics to periodically log the current metrics of the Producer.
 */
public class KafkaConsumerMetrics<K, V> extends KafkaMetrics {

    private final Consumer<K, V> consumer;

    public KafkaConsumerMetrics(Consumer<K, V> consumer) {
        this.consumer = consumer;
    }

    @Override
    protected Map<MetricName, ? extends Metric> metrics() {
        return consumer.metrics();
    }

    @Override
    protected String type() {
        return "consumer";
    }
}
