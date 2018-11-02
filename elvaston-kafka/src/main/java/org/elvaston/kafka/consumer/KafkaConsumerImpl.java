package org.elvaston.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.elvaston.kafka.api.KafkaConsumer;
import org.elvaston.kafka.api.Processor;
import org.elvaston.kafka.common.KafkaPayload;
import org.elvaston.kafka.common.KafkaProperties;
import org.elvaston.kafka.common.KafkaUtils;
import org.elvaston.kafka.metrics.KafkaMetrics;
import org.elvaston.kafka.processor.GatewayProcessorImpl;
import org.elvaston.kafka.processor.LoggerProcessorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * KafkaProducer to send the serialized KafkaPayload messages to our test topic.
 */
public class KafkaConsumerImpl implements KafkaConsumer<Long, KafkaPayload> {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerImpl.class);

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private KafkaMetrics kafkaMetrics;
    private List<Processor> processors = new ArrayList<>();
    private volatile boolean running = true;

    public KafkaConsumerImpl(Processor... processors) {
        this.processors.addAll(Arrays.asList(processors));
    }

    /**
     * Main entry to the ConsumerImpl.
     * @param args {@code String[]} of arguments passed into the main method
     */
    public static void main(String[] args) {
        KafkaConsumerImpl kafkaConsumer = new KafkaConsumerImpl(new LoggerProcessorImpl(), new GatewayProcessorImpl());
        kafkaConsumer.start(new KafkaConsumerContext<>());

        KafkaUtils.sleep(TimeUnit.SECONDS, 30);

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
        processors.forEach(Processor::stop);
        executorService.shutdownNow();
    }

    private void startMetrics(KafkaConsumerContext<Long, KafkaPayload> context) {
        Consumer<Long, KafkaPayload> producer = context.consumer();
        kafkaMetrics = context.withMetrics(producer);
        executorService.submit(kafkaMetrics);
    }

    private void startConsumer(final KafkaConsumerContext<Long, KafkaPayload> context) {
        executorService.submit(new KafkaConsumerRunnable(context));
    }

    class KafkaConsumerRunnable implements Runnable {

        private final Consumer<Long, KafkaPayload> consumer;
        private final Duration duration;

        KafkaConsumerRunnable(final KafkaConsumerContext<Long, KafkaPayload> context) {
            this.consumer = context.consumer();
            this.duration = context.duration();
        }

        @Override
        public void run() {
            int emptyMessagesCount = 0;
            while (running) {
                ConsumerRecords<Long, KafkaPayload> consumerRecords = consumer.poll(duration);

                if (consumerRecords.count() > 0) {
                    LOG.info("received {} records to process", consumerRecords.count());
                    emptyMessagesCount = 0;
                    processors.forEach(processor -> processor.process(consumerRecords));
                    consumer.commitAsync();
                } else {
                    emptyMessagesCount++;
                    LOG.info("zero messages count: {}", emptyMessagesCount);
                    if (emptyMessagesCount > KafkaProperties.CONSUMER_NO_MESSAGE_FOUND_THRESHOLD) {
                        running = false;
                    } else {
                        KafkaUtils.sleep(TimeUnit.MILLISECONDS, KafkaProperties.CONSUMER_POLL_INTERVAL_IN_MS);
                    }
                }
            }
            consumer.close();
        }
    }
}


