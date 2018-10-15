package org.elvaston.kafka.metrics;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;

import java.util.Map;

/**
 * KafkaProducerMetrics to periodically log the current metrics of the Producer.
 *
 * @param <K> key used in the service implementation
 * @param <V> value used in the service implementation
 */
public class KafkaProducerMetrics<K, V> extends KafkaMetrics {

    private final Producer<K, V> producer;

    /**
     * Create a KafkaConsumerMetrics w/ the provided Producer.
     * @param producer whose metrics we are interested in
     */
    public KafkaProducerMetrics(Producer<K, V> producer) {
        this.producer = producer;
    }

    /**
     * Get the current metrics from the producer indexed by MetricName.
     * @return Map of metrics by MetricName
     */
    @Override
    protected Map<MetricName, ? extends Metric> metrics() {
        return producer.metrics();
    }


    /**
     * Used by the abstract KafkaMetrics to distinguish between the type of metrics we are reporting on.
     * @return String "producer" as the type of metric
     */
    @Override
    protected String type() {
        return "producer";
    }
}


