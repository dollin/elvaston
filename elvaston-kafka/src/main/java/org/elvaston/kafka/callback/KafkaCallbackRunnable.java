package org.elvaston.kafka.callback;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Callback to read futures from a KafkaService and log the details.
 */
public class KafkaCallbackRunnable implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaCallbackRunnable.class);

    private final LinkedBlockingQueue<Future<RecordMetadata>> callBackQueue;
    private final String serviceType;
    private volatile boolean running = true;

    public KafkaCallbackRunnable(LinkedBlockingQueue<Future<RecordMetadata>> queue, String serviceType) {
        this.callBackQueue = queue;
        this.serviceType = serviceType;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Future<RecordMetadata> future = callBackQueue.poll(2, TimeUnit.SECONDS);
                if (future != null) {
                    RecordMetadata metadata = future.get();

                    LOG.info("Record {} to topic: {}, partition: {}, offset: {}, keySize: {}, valueSize: {}",
                            serviceType,
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset(),
                            metadata.serializedKeySize(),
                            metadata.serializedValueSize());
                }
            } catch (InterruptedException | ExecutionException e) {
                LOG.info("Caught an exception which is fine. Will re-try if still running: {}", running);
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
