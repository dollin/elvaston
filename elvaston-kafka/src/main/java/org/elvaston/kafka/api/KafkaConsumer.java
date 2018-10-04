package org.elvaston.kafka.api;

import org.elvaston.kafka.consumer.KafkaConsumerBuilder;

/**
 * Interface for our service to consume messages from Kafka.
 */
public interface KafkaConsumer<K, V> {

    /**
     * Method used to start the consumer given a ConsumerBuilder.
     * @param builder instance of a builder to creation Consumer
     */
    void start(KafkaConsumerBuilder<K, V> builder);

    /**
     * Stops the KafkaService.
     */
    void stop();
}
