package org.elvaston.kafka.common;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * A callback implementation that the user can use to allow code to execute when the request is complete.
 * This callback will generally execute in the background I/O thread so it should be fast.
 * @param <K> key used in the service implementation
 * @param <V> value used in the service implementation
 */
public class KafkaCallback<K, V> implements Callback {

    private ProducerRecord<K, V> record;
    private BiConsumer<RecordMetadata, Exception> onError;
    private BiConsumer<ProducerRecord<K, V>, RecordMetadata> onSuccess;

    /**
     * Constructor to create an instance of a KafkaCallback.
     * @param record the record we are calling back on
     * @param onError the {@code @functionalInterface} we will call given an error
     * @param onSuccess the {@code @functionalInterface} we will call given a successful send
     */
    public KafkaCallback(ProducerRecord<K, V> record,
                         BiConsumer<RecordMetadata, Exception> onError,
                         BiConsumer<ProducerRecord<K, V>, RecordMetadata> onSuccess) {
        this.record = record;
        this.onError = onError;
        this.onSuccess = onSuccess;
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if (Objects.nonNull(exception)) {
            onError.accept(metadata, exception);
        } else {
            onSuccess.accept(record, metadata);
        }
    }
}
