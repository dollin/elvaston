package org.elvaston.kafka.api;

import org.elvaston.kafka.producer.KafkaProducerBuilder;

/**
 * Interface for our service to produce messages to Kafka.
 */
public interface KafkaProducer<K, V> {

    /**
     * Starts the KafkaProducer w/ default {@code KafkaProducerBuilder} and {@code KafkaProperties.MESSAGE_COUNT}.
     */
    void start();

    /**
     * Start the KafkaProducer w/ provided builder and count.
     * @param builder to use to create the Producer
     * @param count number of messages to send
     */
    void start(KafkaProducerBuilder<K, V> builder, int count);

    /**
     * Stops the KafkaProducer.
     */
    void stop();
}
