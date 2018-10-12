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

    @Override
    public void close() {

    }
}
