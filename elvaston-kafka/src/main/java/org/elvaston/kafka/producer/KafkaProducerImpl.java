package org.elvaston.kafka.producer;

import static org.elvaston.kafka.common.KafkaProperties.MESSAGE_COUNT;
import static org.elvaston.kafka.common.KafkaProperties.TOPIC_NAME;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.elvaston.kafka.api.KafkaProducer;
import org.elvaston.kafka.common.KafkaCallbackDetails;
import org.elvaston.kafka.common.KafkaCallbackImpl;
import org.elvaston.kafka.common.KafkaMetrics;
import org.elvaston.kafka.common.KafkaPayload;
import org.elvaston.kafka.common.KafkaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * KafkaProducer to send the serialized KafkaPayload messages to our test topic.
 */
public class KafkaProducerImpl implements KafkaProducer<Long, KafkaPayload> {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerImpl.class);

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private KafkaMetrics kafkaMetrics;

    /**
     * Main entry to the ProducerImpl.
     * @param args args passed into main
     */
    public static void main(String[] args) {
        KafkaProducerImpl kafkaProducer = new KafkaProducerImpl();
        kafkaProducer.start();
        kafkaProducer.stop();
    }

    @Override
    public void start() {
        start(new KafkaProducerContext<>(), MESSAGE_COUNT);
    }

    @Override
    public void start(KafkaProducerContext<Long, KafkaPayload> context, int count) {
        processMessages(context, count);
    }

    @Override
    public void stop() {
        kafkaMetrics.stop();
        executorService.shutdownNow();
    }

    private void processMessages(KafkaProducerContext<Long, KafkaPayload> context, long msgCount) {
        Producer<Long, KafkaPayload> producer = context.producer();
        kafkaMetrics = context.withMetrics(producer);
        executorService.execute(kafkaMetrics);

        for (long index = 0; index < msgCount; index++) {
            KafkaPayload payload = new KafkaPayload("This is record " + index);

            ProducerRecord<Long, KafkaPayload> record = new ProducerRecord<>(TOPIC_NAME, index, payload);
            LOG.info("Sending record: [key: {}, topic: {}]", record.key(), record.topic());
            producer.send(record, new KafkaCallbackImpl<>(record, this::onError, this::onSuccess));
        }
        KafkaUtils.sleep(TimeUnit.SECONDS, 10);
    }

    private void onSuccess(KafkaCallbackDetails<Long, KafkaPayload> callback) {
        LOG.info("Record key: {}, topic: {}, partition: {}, offset: {}, keySize: {}, valueSize: {}",
                callback.key(),
                callback.topic(),
                callback.partition(),
                callback.offset(),
                callback.serializedKeySize(),
                callback.serializedValueSize());
    }

    private void onError(RecordMetadata metadata, Exception exception) {
        //TODO: RETRY???? so will need to
        LOG.warn("Exception w/ topic: {}, partition: {}, offset: {}, keySize: {}, valueSize: {}",
                metadata.topic(),
                metadata.partition(),
                metadata.offset(),
                metadata.serializedKeySize(),
                metadata.serializedValueSize(),
                exception);
    }
}
