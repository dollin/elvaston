package org.elvaston.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.elvaston.kafka.api.KafkaConsumer;
import org.elvaston.kafka.common.KafkaMetrics;
import org.elvaston.kafka.common.KafkaPayload;
import org.elvaston.kafka.common.KafkaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * KafkaProducer to send the serialized KafkaPayload messages to our test topic.
 */
public class KafkaConsumerImpl implements KafkaConsumer<Long, KafkaPayload> {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerImpl.class);

    private final ExecutorService kafkaMetricsService = Executors.newCachedThreadPool();
    private KafkaMetrics kafkaMetrics;
    private volatile boolean running;

    /**
     * Main entry to the ConsumerImpl.
     * @param args {@code String[]} of arguments passed into the main method
     */
    public static void main(String[] args) {
        KafkaConsumerImpl kafkaConsumer = new KafkaConsumerImpl();
        kafkaConsumer.start(new KafkaConsumerContext<>());
        kafkaConsumer.stop();
    }

    @Override
    public void start(KafkaConsumerContext<Long, KafkaPayload> context) {
        consumeMessages(context);
    }

    @Override
    public void stop() {
        running = false;
        if (Objects.nonNull(kafkaMetrics)) {
            kafkaMetrics.stop();
        }
        kafkaMetricsService.shutdownNow();
    }

    private void consumeMessages(KafkaConsumerContext<Long, KafkaPayload> context) {
        Consumer<Long, KafkaPayload> producer = context.consumer();
        kafkaMetrics = context.withMetrics(producer);

        int noMessageFound = 0;
        
        while (running) {

            ConsumerRecords<Long, KafkaPayload> consumerRecords = producer.poll(context.duration());

            // 1000 is the time in milliseconds consumer will wait if no record is found at broker.

            if (consumerRecords.count() == 0) {

                noMessageFound++;

                if (noMessageFound > KafkaProperties.MAX_NO_MESSAGE_FOUND_COUNT) {

                    // If no message found count is reached to threshold exit loop.

                    break;

                } else {

                    continue;

                }
            }
            //print each record.

            consumerRecords.forEach(record -> {

                LOG.info("Record Key " + record.key());
                System.out.println("Record value " + record.value());
                System.out.println("Record partition " + record.partition());
                System.out.println("Record offset " + record.offset());

            });

            // commits the offset of record to broker.
            producer.commitAsync();
        }
        producer.close();
    }
}
