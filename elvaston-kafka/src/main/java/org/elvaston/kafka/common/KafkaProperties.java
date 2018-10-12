package org.elvaston.kafka.common;

import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.UUID;

/**
 * Kafka properties used by the publisher and subscriber.
 */
public class KafkaProperties {
    public static final String KAFKA_BROKERS = "localhost:9092";
    public static final int KAFKA_PARTITIONS = 50;
    public static final int MESSAGE_COUNT = 1_000;
    public static final String CLIENT_ID = "client1";
    public static final String TOPIC_NAME = "test";
//    public static final String GROUP_ID_CONFIG = "consumerGroup1";
    public static final String GROUP_ID_CONFIG = UUID.randomUUID().toString();
    public static final int MAX_NO_MESSAGE_FOUND_COUNT = 100;
    public static final String OFFSET_RESET_LATEST = "latest";
    public static final String OFFSET_RESET_EARLIER = "earliest";
    public static final int MAX_POLL_RECORDS = 1;
    public static final String KEY_DESERIALIZER = LongDeserializer.class.getName();
    public static final String VALUE_DESERIALIZER = KafkaPayloadSerializationImpl.class.getName();
    public static final String KEY_SERIALIZER = LongSerializer.class.getName();
    public static final String VALUE_SERIALIZER = KafkaPayloadSerializationImpl.class.getName();
    public static final long CONSUMER_POLL_INTERVAL_IN_MS = 10_000;
    public static final int CONSUMER_NO_MESSAGE_FOUND_THRESHOLD = 100;
}
