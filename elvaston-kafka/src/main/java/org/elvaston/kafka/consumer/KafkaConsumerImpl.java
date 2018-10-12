package org.elvaston.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.elvaston.kafka.api.KafkaConsumer;
import org.elvaston.kafka.common.KafkaPayload;
import org.elvaston.kafka.common.KafkaProperties;
import org.elvaston.kafka.common.KafkaUtils;
import org.elvaston.kafka.metrics.KafkaMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * KafkaProducer to send the serialized KafkaPayload messages to our test topic.
 */
public class KafkaConsumerImpl implements KafkaConsumer<Long, KafkaPayload> {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerImpl.class);

    private final ExecutorService kafkaExecutorService = Executors.newCachedThreadPool();
    private KafkaMetrics kafkaMetrics;
    private volatile boolean running = true;

    /**
     * Main entry to the ConsumerImpl.
     * @param args {@code String[]} of arguments passed into the main method
     */
    public static void main(String[] args) {
        KafkaConsumerImpl kafkaConsumer = new KafkaConsumerImpl();
        kafkaConsumer.start(new KafkaConsumerContext<>());

        KafkaUtils.sleep(TimeUnit.SECONDS, 10);

        kafkaConsumer.stop();
    }

    @Override
    public void start(KafkaConsumerContext<Long, KafkaPayload> context) {
        startMetrics(context);
        startConsumer(context);
    }

    @Override
    public void stop() {
        running = false;
        if (Objects.nonNull(kafkaMetrics)) {
            kafkaMetrics.stop();
        }
        kafkaExecutorService.shutdownNow();
    }

    private void startMetrics(KafkaConsumerContext<Long, KafkaPayload> context) {
        Consumer<Long, KafkaPayload> producer = context.consumer();
        kafkaMetrics = context.withMetrics(producer);
        kafkaExecutorService.submit(kafkaMetrics);
    }

    private void startConsumer(final KafkaConsumerContext<Long, KafkaPayload> context) {

        final Consumer<Long, KafkaPayload> producer = context.consumer();

        kafkaExecutorService.submit(() -> {
            int emptyMessagesCount = 0;
            while (running) {
                ConsumerRecords<Long, KafkaPayload> consumerRecords = producer.poll(context.duration());

                if (consumerRecords.count() > 0) {
                    emptyMessagesCount = 0;
                    consumerRecords.forEach(record -> {

                        LOG.info("Record Key " + record.key());
                        System.out.println("Record value " + record.value());
                        System.out.println("Record partition " + record.partition());
                        System.out.println("Record offset " + record.offset());

                    });
                    producer.commitAsync();
                } else {
                    emptyMessagesCount++;
                    if (emptyMessagesCount > KafkaProperties.CONSUMER_NO_MESSAGE_FOUND_THRESHOLD) {
                        running = false;
                    } else {
                        KafkaUtils.sleep(TimeUnit.MILLISECONDS, KafkaProperties.CONSUMER_POLL_INTERVAL_IN_MS);
                    }
                }
            }
            producer.close();
        });
    }
}
