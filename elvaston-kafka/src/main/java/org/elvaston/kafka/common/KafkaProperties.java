package org.elvaston.kafka.common;

import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.UUID;

/**
 * Kafka properties used by the producer and consumer.
 */
public class KafkaProperties {
    public static final String KAFKA_BROKERS = "localhost:9092";
    public static final String KAFKA_CLIENT_ID = "client1";
    public static final String KAFKA_GROUP_ID_CONFIG = UUID.randomUUID().toString();
    public static final int KAFKA_PARTITIONS = 50;
    public static final String KAFKA_TOPIC_NAME = "test";

    public static final String KEY_DESERIALIZER = LongDeserializer.class.getName();
    public static final String KEY_SERIALIZER = LongSerializer.class.getName();
    public static final String VALUE_DESERIALIZER = KafkaPayloadSerializationImpl.class.getName();
    public static final String VALUE_SERIALIZER = KafkaPayloadSerializationImpl.class.getName();

    public static final int CONSUMER_MAX_POLL_RECORDS = 1;
    public static final int CONSUMER_NO_MESSAGE_FOUND_THRESHOLD = 100;
    public static final String CONSUMER_OFFSET_RESET_EARLIER = "earliest";
    public static final long CONSUMER_POLL_INTERVAL_IN_MS = 10_000;

    public static final int METRICS_LOG_INTERVAL_IN_MS = 10_000;

    public static final int PRODUCER_MESSAGE_COUNT = 1_000;
}
