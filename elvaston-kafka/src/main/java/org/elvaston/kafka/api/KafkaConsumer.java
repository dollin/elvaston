package org.elvaston.kafka.api;

import org.elvaston.kafka.common.KafkaPayload;
import org.elvaston.kafka.consumer.KafkaConsumerContext;

/**
 * Interface for our service to consume messages from Kafka.
 */
public interface KafkaConsumer<K, V> {

    /**
     * Method used to start the consumer given a ConsumerBuilder.
     * @param context instance of a context to creation Consumer
     */
    void start(KafkaConsumerContext<Long, KafkaPayload> context);

    /**
     * Stops the KafkaService.
     */
    void stop();
}
