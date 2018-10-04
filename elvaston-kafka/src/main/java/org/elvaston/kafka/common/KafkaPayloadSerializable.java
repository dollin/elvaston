package org.elvaston.kafka.common;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * TODO Add javadoc.
 */
public class KafkaPayloadSerializable implements Serializer<KafkaPayload>, Deserializer<KafkaPayload> {
    /**
     * Configure this class.
     *
     * @param configs configs in key/value pairs
     * @param isKey   whether is for key or value
     */
    @Override
    public void configure(Map configs, boolean isKey) {

    }


    /**
     * Deserialize a record value from a byte array into a value or object.
     *
     * @param topic topic associated with the data
     * @param data  serialized bytes; may be null; implementations are recommended
     *              to handle null by returning a value or null rather than throwing an exception.
     * @return deserialized typed data; may be null
     */
    @Override
    public KafkaPayload deserialize(String topic, byte[] data) {
        return null;
    }

    /**
     * Convert {@code data} into a byte array.
     *
     * @param topic topic associated with data
     * @param data  typed data
     * @return serialized bytes
     */
    @Override
    public byte[] serialize(String topic, KafkaPayload data) {
        return new byte[0];
    }

    /**
     * Close this serializer.
     * This method must be idempotent as it may be called multiple times.
     */
    @Override
    public void close() {

    }
}
