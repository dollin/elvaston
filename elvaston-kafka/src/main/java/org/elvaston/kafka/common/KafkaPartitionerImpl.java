package org.elvaston.kafka.common;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Default implementation of {@code Partitioner} interface to drive the load balancing onto the Kafka queue.
 */
public class KafkaPartitionerImpl implements Partitioner {

    @Override
    public void configure(Map<String, ?> configs) {

    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        Integer keyInt = Integer.parseInt(key.toString());
        return keyInt % KafkaProperties.KAFKA_PARTITIONS;
    }

    @Override
    public void close() {
    }
}
