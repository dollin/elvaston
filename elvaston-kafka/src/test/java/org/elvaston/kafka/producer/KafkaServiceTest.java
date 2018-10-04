package org.elvaston.kafka.producer;

import org.elvaston.kafka.consumer.KafkaConsumerImpl;
import org.junit.Test;

public class KafkaServiceTest {

    @Test
    public void createConsumer() {
        new KafkaConsumerImpl();
    }
}
