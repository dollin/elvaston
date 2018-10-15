package org.elvaston.kafka.metrics;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;

import java.util.Map;

/**
 * KafkaProducerMetrics to periodically log the current metrics of the Producer.
 *
 * @param <K> key used in the service implementation
 * @param <V> value used in the service implementation
 */
public class KafkaConsumerMetrics<K, V> extends KafkaMetrics {

    private final Consumer<K, V> consumer;

    /**
     * Create a KafkaConsumerMetrics w/ the provided Consumer.
     * @param consumer whose metrics we are interested in
     */
    public KafkaConsumerMetrics(Consumer<K, V> consumer) {
        this.consumer = consumer;
    }

    /**
     * Get the current metrics from the consumer indexed by MetricName.
     * @return Map of metrics by MetricName
     */
    @Override
    protected Map<MetricName, ? extends Metric> metrics() {
        return consumer.metrics();
    }

    /**
     * Used by the abstract KafkaMetrics to distinguish between the type of metrics we are reporting on.
     * @return String "consumer" as the type of metric
     */
    @Override
    protected String type() {
        return "consumer";
    }
}
