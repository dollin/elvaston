package org.elvaston.kafka.producer;

import org.elvaston.kafka.consumer.KafkaConsumerImpl;
import org.elvaston.kafka.processor.LoggerProcessorImpl;
import org.junit.Test;

public class KafkaServiceTest {

    @Test
    public void createConsumer() {
        new KafkaConsumerImpl(new LoggerProcessorImpl());
    }

}
