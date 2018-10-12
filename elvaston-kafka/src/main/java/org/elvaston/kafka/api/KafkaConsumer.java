package org.elvaston.kafka.api;

import org.elvaston.kafka.consumer.KafkaConsumerContext;

/**
 * Interface for our service to consume messages from Kafka.
 *
 * @param <K> the type of keys maintained by this consumer
 * @param <V> the type of values we are consuming from Kafka
 */
public interface KafkaConsumer<K, V> {

    /**
     * Method used to start the consumer for a given {@link KafkaConsumerContext}.
     * @param context instance of a context used to create our Consumer
     */
    void start(KafkaConsumerContext<K, V> context);

    /**
     * Stops the KafkaService.
     */
    void stop();
}
