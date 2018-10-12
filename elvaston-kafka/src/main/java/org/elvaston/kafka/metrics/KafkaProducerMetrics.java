package org.elvaston.kafka.metrics;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;

import java.util.Map;

/**
 * KafkaProducerMetrics to periodically log the current metrics of the Producer.
 */
public class KafkaProducerMetrics<K, V> extends KafkaMetrics {

    private final Producer<K, V> producer;

    public KafkaProducerMetrics(Producer<K, V> producer) {
        this.producer = producer;
    }

    @Override
    protected Map<MetricName, ? extends Metric> metrics() {
        return producer.metrics();
    }

    @Override
    protected String type() {
        return "producer";
    }
}
