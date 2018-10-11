package org.elvaston.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.elvaston.kafka.common.KafkaMetrics;
import org.elvaston.kafka.common.KafkaPartitionerImpl;
import org.elvaston.kafka.common.KafkaProperties;

import java.util.Properties;

/**
 * Builder class to help create a KafkaProducer.
 */
public class KafkaProducerContext<K, V> {

    /**
     * Creates a Producer using the ProducerConfig and KafkaProperties.
     * @return Producer
     */
    public Producer<K, V> producer() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_BROKERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaProperties.CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaProperties.KEY_SERIALIZER);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProperties.VALUE_SERIALIZER);

        props.put("compression.type", "snappy");
        props.put("partitioner.class", KafkaPartitionerImpl.class.getName());
        props.put("enable.idempotence", true);
        props.put("linger.ms", 100);
        props.put("batch.size", 16000);

        return new KafkaProducer<>(props);
    }

    public KafkaMetrics withMetrics(Producer<K, V> producer) {
        return new KafkaProducerMetrics<>(producer);
    }
}
