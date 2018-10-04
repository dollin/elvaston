package org.elvaston.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.elvaston.kafka.common.KafkaMetrics;

import java.util.Map;

/**
 * KafkaProducerMetrics to periodically log the current metrics of the Producer.
 */
public class KafkaConsumerMetrics<K, V> extends KafkaMetrics {

    private final Consumer<K, V> consumer;

    KafkaConsumerMetrics(Consumer<K, V> consumer) {
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
