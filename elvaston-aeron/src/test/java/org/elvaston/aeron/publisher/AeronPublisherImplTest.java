package org.elvaston.aeron.publisher;

import static io.aeron.samples.AeronStat.mapCounters;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.aeron.driver.MediaDriver;
import org.elvaston.aeron.common.AeronMessage;
import org.elvaston.aeron.driver.AeronMediaDriverBuilder;
import org.junit.Test;

/**
 * TODO Add javadoc.
 */
public class AeronPublisherImplTest {

    @Test
    public void publishOffersButFailsWhenNoSubscribers() throws Exception {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronPublisherImpl<String> aeronPublisher = new AeronPublisherBuilder<String>()
                .withDriver(mediaDriver)
                .withChannel("aeron:ipc")
                .connect();
        assertNotNull(aeronPublisher);

        long result = aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));
        assertEquals(-1, result);
    }

    @Test
    public void publishOffersButFailsWhenNoSubscribersWithAeronStat() throws Exception {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronPublisherImpl<String> aeronPublisher = new AeronPublisherBuilder<String>()
                .withDriver(mediaDriver)
                .withChannel("aeron:ipc")
                .connect();
        assertNotNull(aeronPublisher);

        long result = aeronPublisher.publish(new AeronMessage<String>().withPayload("NEIL"));
        assertEquals(-1, result);
    }
}
