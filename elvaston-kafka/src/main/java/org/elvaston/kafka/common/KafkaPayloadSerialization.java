package org.elvaston.kafka.common;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * TODO Add javadoc.
 */
public class KafkaPayloadSerialization implements Serializer<KafkaPayload>, Deserializer<KafkaPayload> {

    @Override
    public void configure(Map configs, boolean isKey) {

    }

    //TODO Implement
    @Override
    public KafkaPayload deserialize(String topic, byte[] data) {
        return null;
    }

    //TODO: Implement
    @Override
    public byte[] serialize(String topic, KafkaPayload data) {
        return new byte[0];
    }


    @Override
    public void close() {

    }
}
