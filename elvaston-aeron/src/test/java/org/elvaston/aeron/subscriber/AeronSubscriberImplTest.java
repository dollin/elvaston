package org.elvaston.aeron.subscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.aeron.driver.MediaDriver;
import org.elvaston.aeron.common.AeronMessage;
import org.elvaston.aeron.driver.AeronMediaDriverBuilder;
import org.elvaston.aeron.metrics.AeronMetrics;
import org.elvaston.aeron.publisher.AeronPublisherBuilder;
import org.elvaston.aeron.publisher.AeronPublisherImpl;
import org.junit.Test;

/**
 * Tests for the subscriber.
 */
public class AeronSubscriberImplTest {

    private static final String UDP_CHANNEL = "aeron:udp?endpoint=localhost:40124";
    @Test
    public void simpleSubscriptionWithString() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();

        AeronSubscriberImpl<String> aeronSubscriber = new AeronSubscriberBuilder<String>()
                .withDriver(mediaDriver)
                .withChannel(UDP_CHANNEL)
                .connect();
        aeronSubscriber.start();

        AeronPublisherImpl<String> aeronPublisher = new AeronPublisherBuilder<String>()
                .withDriver(mediaDriver)
                .withChannel(UDP_CHANNEL)
                .connect();
        assertNotNull(aeronPublisher);
        assertNotNull(aeronSubscriber);

        AeronMetrics aeronMetrics = new AeronMetrics(mediaDriver.aeronDirectoryName());
        aeronMetrics.print(System.out);

        aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));
        aeronPublisher.publish(new AeronMessage<String>().withPayload("STEVE is a nightmare"));
//        aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL1"));
//        aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));
//        aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));
//        aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));
//        aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));

        aeronMetrics.print(System.out);
//TODO replace with bytes sent
        assertEquals(1, aeronMetrics.channelSent());
        assertEquals(1, aeronMetrics.channelReceived());
    }
}
