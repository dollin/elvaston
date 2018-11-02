package org.elvaston.kafka.api;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.elvaston.kafka.common.KafkaPayload;

/**
 * Interface of the processors used by the KafkaConsumer to further
 * process the messages to any number of Producer implementations.
 */
public interface Processor {

    /**
     * Entry to the processor for it to execute.
     * @param record the records to process
     */
    void process(ConsumerRecords<Long, KafkaPayload> record);

    /**
     * Stop this processor.
     */
    void stop();
}
