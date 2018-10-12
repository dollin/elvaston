package org.elvaston.kafka.api;

import org.elvaston.kafka.producer.KafkaProducerContext;

/**
 * Interface for our service to send messages to Kafka.
 *
 * @param <K> the type of keys maintained by this producer
 * @param <V> the type of values we are sending to Kafka
 */
public interface KafkaProducer<K, V> {

    /**
     * Starts the KafkaProducer w/ default a KafkaProducerBuilder and default {@code KafkaProperties.MESSAGE_COUNT}.
     */
    void start();

    /**
     * Start the KafkaProducer w/ provided {@link KafkaProducerContext} and message count.
     * @param context used to create the Producer
     * @param count number of messages to send
     */
    void start(KafkaProducerContext<K, V> context, int count);

    /**
     * Stops the KafkaProducer.
     */
    void stop();
}
