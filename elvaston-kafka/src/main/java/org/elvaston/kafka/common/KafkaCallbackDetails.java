package org.elvaston.kafka.common;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * Wrapper class to group the details returned from a Kafka call.
 */
public class KafkaCallbackDetails<K, V> {
    private ProducerRecord<K, V> producerRecord;
    private RecordMetadata recordMetadata;

    KafkaCallbackDetails(ProducerRecord<K, V> producerRecord, RecordMetadata recordMetadata) {
        this.producerRecord = producerRecord;
        this.recordMetadata = recordMetadata;
    }

    public K key() {
        return producerRecord.key();
    }

    public V value() {
        return producerRecord.value();
    }


    public String topic() {
        return recordMetadata.topic();
    }

    public int partition() {
        return recordMetadata.partition();
    }

    public long offset() {
        return recordMetadata.offset();
    }

    public int serializedKeySize() {
        return recordMetadata.serializedKeySize();
    }

    public int serializedValueSize() {
        return recordMetadata.serializedValueSize();
    }
}
