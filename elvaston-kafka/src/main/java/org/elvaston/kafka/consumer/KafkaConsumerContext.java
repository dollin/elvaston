package org.elvaston.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.elvaston.kafka.common.KafkaProperties;
import org.elvaston.kafka.metrics.KafkaConsumerMetrics;
import org.elvaston.kafka.metrics.KafkaMetrics;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Builder class to help create a KafkaConsumer.
 *
 * @param <K> key used in the service implementation
 * @param <V> value used in the service implementation
 */
public class KafkaConsumerContext<K, V> {

    private static final long ONE = 1L;

    /**
     * Creates a Consumer using the ConsumerConfig and KafkaProperties and
     * triggers a subscribe w/ the {@code KafkaProperties.TOPIC_NAME}.
     * @return Consumer
     */
    public Consumer<K, V> consumer() {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_BROKERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaProperties.KAFKA_GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaProperties.KEY_DESERIALIZER);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaProperties.VALUE_DESERIALIZER);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, KafkaProperties.CONSUMER_MAX_POLL_RECORDS);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaProperties.CONSUMER_OFFSET_RESET_EARLIER);

        Consumer<K, V> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(KafkaProperties.KAFKA_TOPIC_NAME));
        return consumer;
    }

    /**
     * To create metrics for the provided Consumer.
     * @param consumer used to pass to the KafkaProducerMetrics constructor
     * @return KafkaProducerMetrics
     */
    public KafkaMetrics withMetrics(Consumer<K, V> consumer) {
        return new KafkaConsumerMetrics<>(consumer);
    }

    /**
     * A Duration wait for when the Consumer is polling Kafka.
     * @return a {@code Duration}, of one second.
     */
    public Duration duration() {
        return Duration.ofSeconds(ONE);
    }
}