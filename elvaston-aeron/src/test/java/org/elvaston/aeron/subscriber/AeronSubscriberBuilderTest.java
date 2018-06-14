package org.elvaston.aeron.subscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import io.aeron.Aeron;
import io.aeron.driver.MediaDriver;
import org.elvaston.aeron.driver.AeronMediaDriverBuilder;
import org.elvaston.aeron.exception.AeronException;
import org.junit.Test;

import java.io.IOException;

/**
 * Test class for the Subscriber builder.
 */
public class AeronSubscriberBuilderTest {

    @Test(expected = AeronException.class)
    public void driverCannotBeNull() {
        AeronSubscriberImpl aeronSubscriber = new AeronSubscriberBuilder<>().connect();
        assertNull(aeronSubscriber);
    }

    @Test(expected = AeronException.class)
    public void channelCannotBeNull() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronSubscriberImpl aeronSubscriber = new AeronSubscriberBuilder<>()
                .withDriver(mediaDriver)
                .connect();
        assertNull(aeronSubscriber);
    }

    @Test(expected = AeronException.class)
    public void channelHasToStartWithAeronColon() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronSubscriberImpl aeronSubscriber = new AeronSubscriberBuilder<>()
                .withDriver(mediaDriver)
                .withChannel("aeron")
                .connect();
        assertNull(aeronSubscriber);
    }

    @Test
    public void createDefaultSubscriberOverIpc() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronSubscriberImpl aeronSubscriber = new AeronSubscriberBuilder<>()
                .withDriver(mediaDriver)
                .withChannel("aeron:ipc")
                .connect();
        assertNotNull(aeronSubscriber);
    }

    @Test
    public void createDefaultSubscriberOverUdp() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronSubscriberImpl aeronSubscriber = new AeronSubscriberBuilder<>()
                .withDriver(mediaDriver)
                .withChannel("aeron:udp?endpoint=localhost:40123")
                .connect();
        assertNotNull(aeronSubscriber);
    }

    @Test
    public void createSubscriberWithAeron() throws IOException {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        Aeron aeron = Aeron.connect(new Aeron.Context().aeronDirectoryName(mediaDriver.aeronDirectoryName()));

        AeronSubscriberImpl aeronSubscriber = new AeronSubscriberBuilder<>()
                .withDriver(mediaDriver)
                .withAeron(aeron)
                .withChannel("aeron:ipc")
                .connect();
        assertNotNull(aeronSubscriber);
    }

    @Test
    public void createSubscriberWithContext() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        Aeron.Context context = new Aeron.Context();

        AeronSubscriberImpl aeronSubscriber = new AeronSubscriberBuilder<>()
                .withDriver(mediaDriver)
                .withContext(context)
                .withChannel("aeron:ipc")
                .connect();
        assertNotNull(aeronSubscriber);
    }

    @Test
    public void createDefaultSubscriberWithOneStream() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronSubscriberImpl aeronSubscriber = new AeronSubscriberBuilder<>()
                .withDriver(mediaDriver)
                .withChannel("aeron:ipc")
                .withStreams(1)
                .connect();
        assertNotNull(aeronSubscriber);
        assertEquals(1, aeronSubscriber.streamCount());
        }

    @Test
    public void createDefaultSubscriberWithMultipleStreams() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronSubscriberImpl aeronSubscriber = new AeronSubscriberBuilder<>()
                .withDriver(mediaDriver)
                .withChannel("aeron:ipc")
                .withStreams(10)
                .connect();
        assertNotNull(aeronSubscriber);
        assertEquals(10, aeronSubscriber.streamCount());
    }
}
