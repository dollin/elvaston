package org.elvaston.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.elvaston.kafka.api.KafkaConsumer;
import org.elvaston.kafka.common.KafkaMetrics;
import org.elvaston.kafka.common.KafkaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * KafkaProducer to send the serialized KafkaPayload messages to our test topic.
 */
public class KafkaConsumerImpl implements KafkaConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerImpl.class);

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final LinkedBlockingQueue<Future<RecordMetadata>> queue = new LinkedBlockingQueue<>();
    private volatile boolean continueProcessing = true;
    private KafkaMetrics kafkaMetrics;

    /**
     * Main entry to the ConsumerImpl.
     * @param args {@code String[]} of arguments passed into the main method
     */
    public static void main(String[] args) {
        KafkaConsumerImpl kafkaConsumer = new KafkaConsumerImpl();
        kafkaConsumer.start(new KafkaConsumerBuilder<>());
        kafkaConsumer.stop();
    }

    @Override
    public void start(KafkaConsumerBuilder builder) {
        consumeMessages(builder);
    }

    @Override
    public void stop() {
        continueProcessing = false;
        if (Objects.nonNull(kafkaMetrics)) {
            kafkaMetrics.stop();
        }
        executorService.shutdownNow();
    }

    private void consumeMessages(KafkaConsumerBuilder<Long, byte[]> builder) {
        Consumer<Long, byte[]> producer = builder.build();
        kafkaMetrics = builder.withMetrics(producer);
        executorService.execute(kafkaMetrics);

        int noMessageFound = 0;
        
        while (true) {
            ConsumerRecords<Long, byte[]> consumerRecords = producer.poll(1000);

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
