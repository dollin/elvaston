package org.elvaston.kafka.common;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Default implementation of {@code Partitioner} interface to drive the load balancing onto the Kafka queue.
 */
public class KafkaPartitionerImpl implements Partitioner {

    /**
     * Noop as a very simple partitioner implementation.
     * @param configs not used as
     */
    @Override
    public void configure(Map<String, ?> configs) {

    }

    /**
     * Gets the hashCode value of the key and then does a MOD w/ the number of partitions set
     * in KafkaProperties.KAFKA_PARTITIONS
     * @param topic not presently used
     * @param key used to get the hashCode to determine the partition number
     * @param keyBytes not presently used
     * @param value not presently used
     * @param valueBytes not presently used
     * @param cluster not presently used
     * @return the partition to use for this message
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        int keyHashCode = key.hashCode();
        return keyHashCode % KafkaProperties.KAFKA_PARTITIONS;
    }

    /**
     * Noop as nothing to close.
     */
    @Override
    public void close() {
    }
}
