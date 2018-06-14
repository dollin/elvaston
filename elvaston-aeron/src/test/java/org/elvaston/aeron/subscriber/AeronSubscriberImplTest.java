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
 * Test for the subscriber.
 */
public class AeronSubscriberImplTest {

    @Test
    public void simpleSubscriptionWithString() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();

        AeronSubscriberImpl<String> aeronSubscriber = new AeronSubscriberBuilder<String>()
                .withDriver(mediaDriver)
                .withChannel("aeron:udp?endpoint=localhost:40123")
                .connect();
        aeronSubscriber.start();

        AeronPublisherImpl<String> aeronPublisher = new AeronPublisherBuilder<String>()
                .withDriver(mediaDriver)
                .withChannel("aeron:udp?endpoint=localhost:40123")
                .connect();
        assertNotNull(aeronPublisher);
        assertNotNull(aeronSubscriber);

        AeronMetrics aeronMetrics = new AeronMetrics(mediaDriver.aeronDirectoryName());
        aeronMetrics.print(System.err);

        long result = aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));
        System.out.println(result);
        aeronPublisher.publish(new AeronMessage<String>().withPayload("STEVE is a nightmare"));
        System.out.println(result);
//        aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));
//        aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));
//        aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));
//        aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));

        aeronMetrics.print(System.err);


        assertEquals(1, aeronMetrics.channelSent());
        assertEquals(1, aeronMetrics.channelReceived());
    }
}
