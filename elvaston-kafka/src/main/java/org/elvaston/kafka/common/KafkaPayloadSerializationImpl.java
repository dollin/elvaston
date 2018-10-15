package org.elvaston.kafka.common;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Implementation of Serializer and Deserializer to handle the serialization and deserialization
 * of our KafkaPayloads.
 */
public class KafkaPayloadSerializationImpl implements Serializer<KafkaPayload>, Deserializer<KafkaPayload> {

    /**
     * Noop as using commons utils so nothing to configure.
     * @param configs not used as using apache commons
     * @param isKey  not used as using apache commons
     */
    @Override
    public void configure(Map configs, boolean isKey) {
    }

    @Override
    public KafkaPayload deserialize(String topic, byte[] data) {
        return SerializationUtils.deserialize(data);
    }

    @Override
    public byte[] serialize(String topic, KafkaPayload data) {
        return SerializationUtils.serialize(data);
    }

    /**
     * Noop as using commons utils so nothing to close.
     */
    @Override
    public void close() {
    }
}
