package org.elvaston.kafka.processor;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.elvaston.kafka.api.Processor;
import org.elvaston.kafka.common.KafkaPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple PoC of a {@link Processor} to log any payload received, that's it.
 */
public class LoggerProcessorImpl implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(LoggerProcessorImpl.class);

    @Override
    public void process(ConsumerRecords<Long, KafkaPayload> consumerRecords) {
        consumerRecords.forEach(record -> LOG.info("Record Key: {}, partition: {}, offset: {}, value: {}",
                record.key(),
                record.partition(),
                record.offset(),
                record.value()));
    }

    @Override
    public void stop() {
        LOG.info("stop processor");
    }
}
