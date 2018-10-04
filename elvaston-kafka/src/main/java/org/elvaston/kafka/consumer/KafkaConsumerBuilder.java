package org.elvaston.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.elvaston.kafka.common.KafkaMetrics;
import org.elvaston.kafka.common.KafkaProperties;

import java.util.Collections;
import java.util.Properties;

/**
 * Builder class to help create a KafkaConsumer.
 */
public class KafkaConsumerBuilder<K, V> {

    /**
     * Creates a Consumer using the ConsumerConfig and KafkaProperties.
     * @return Consumer
     */
    public Consumer<K, V> build() {

        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_BROKERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaProperties.GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaProperties.KEY_DESERIALIZER);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaProperties.VALUE_DESERIALIZER);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, KafkaProperties.MAX_POLL_RECORDS);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaProperties.OFFSET_RESET_EARLIER);

        Consumer<K, V> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(KafkaProperties.TOPIC_NAME));

        return consumer;
    }

    public KafkaMetrics withMetrics(Consumer<K, V> consumer) {
        return new KafkaConsumerMetrics<>(consumer);
    }
}