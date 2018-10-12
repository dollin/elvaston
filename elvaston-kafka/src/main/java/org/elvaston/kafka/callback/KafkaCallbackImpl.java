package org.elvaston.kafka.callback;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A callback implementation that the user can use to allow code to execute when the request is complete.
 * This callback will generally execute in the background I/O thread so it should be fast.
 * @param <K> key used in the service implementation
 * @param <V> value used in the service implementation
 */
public class KafkaCallbackImpl<K, V> implements Callback {

    private final ProducerRecord<K, V> record;
    private final BiConsumer<RecordMetadata, Exception> onError;
    private final Consumer<KafkaCallbackDetails<K, V>> onSuccess;

    /**
     * Constructor to create an instance of a Callback.
     * @param record the record we are calling back on
     * @param onError the {@code @functionalInterface} we will call given an error
     * @param onSuccess the {@code @functionalInterface} we will call given a successful send
     */
    public KafkaCallbackImpl(ProducerRecord<K, V> record,
                             BiConsumer<RecordMetadata, Exception> onError,
                             Consumer<KafkaCallbackDetails<K, V>> onSuccess) {
        this.record = record;
        this.onError = onError;
        this.onSuccess = onSuccess;
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if (Objects.nonNull(exception)) {
            onError.accept(metadata, exception);
        } else {
            onSuccess.accept(new KafkaCallbackDetails<>(record, metadata));
        }
    }
}
