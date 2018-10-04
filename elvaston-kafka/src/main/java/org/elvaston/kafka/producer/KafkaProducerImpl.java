package org.elvaston.kafka.producer;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.elvaston.kafka.api.KafkaService;
import org.elvaston.kafka.common.KafkaCallback;
import org.elvaston.kafka.common.KafkaMetrics;
import org.elvaston.kafka.common.KafkaPayload;
import org.elvaston.kafka.common.KafkaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * KafkaProducer to send the serialized KafkaPayload messages to our test topic.
 */
public class KafkaProducerImpl implements KafkaService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerImpl.class);

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final LinkedBlockingQueue<Future<RecordMetadata>> queue = new LinkedBlockingQueue<>();
    private KafkaMetrics kafkaMetrics;
    //private KafkaCallback kafkaCallback;

    KafkaProducerImpl() {
//        kafkaCallback = new KafkaCallback(queue, "Producer");
//        executorService.execute(kafkaCallback);
    }

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
        processMessages(new KafkaProducerBuilder<>(), KafkaProperties.MESSAGE_COUNT);
    }

    @Override
    public void stop() {
        kafkaMetrics.stop();
        executorService.shutdownNow();
    }

    private void processMessages(KafkaProducerBuilder<Long, byte[]> builder, long msgCount) {
        Producer<Long, byte[]> producer = builder.build();
        kafkaMetrics = builder.withMetrics(producer);
        executorService.execute(kafkaMetrics);

        for (long index = 0; index < msgCount; index++) {
            byte[] payload = SerializationUtils.serialize(new KafkaPayload("This is record " + index));
            ProducerRecord<Long, byte[]> record = new ProducerRecord<>(KafkaProperties.TOPIC_NAME, index, payload);
            LOG.info("Sending record: [key: {}, topic: {}]", record.key(), record.topic());
            producer.send(record, new KafkaCallback<>(record, this::onError, this::onSuccess));
        }
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void onSuccess(ProducerRecord<Long, byte[]> payload, RecordMetadata metadata) {
        LOG.info("Record key: {}, topic: {}, partition: {}, offset: {}, keySize: {}, valueSize: {}",
                payload.key(),
                metadata.topic(),
                metadata.partition(),
                metadata.offset(),
                metadata.serializedKeySize(),
                metadata.serializedValueSize());
    }

    private void onError(RecordMetadata recordMetadata, Exception exception) {
    }

}
