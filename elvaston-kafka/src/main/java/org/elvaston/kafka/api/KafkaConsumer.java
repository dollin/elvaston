package org.elvaston.kafka.api;

import org.elvaston.kafka.consumer.KafkaConsumerBuilder;

/**
 * Interface for our KafkaServices that will be either a producer or consumer.
 * From directory C:\Users\elvastooon5\kafka_2.11-2.0.0
 * Start ZOOKEEPER: bin\windows\zookeeper-server-processMessages.bat config\zookeeper.properties
 * Start SERVER: bin\windows\kafka-server-processMessages.bat config\server.properties
 * Create TOPIC:
 * bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 100 --topic test
 * bin\windows\kafka-topics.bat --list --zookeeper localhost:2181
 * PRODUCER FROM CMD:
 * bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test
 * CONSUMER FROM CMD:
 * bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning
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
