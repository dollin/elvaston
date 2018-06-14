package org.elvaston.aeron.publisher;

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
 * Test class for the Publisher builder.
 */
public class AeronPublisherBuilderTest {

    @Test(expected = AeronException.class)
    public void driverCannotBeNull() {
        AeronPublisherImpl aeronPublisher = new AeronPublisherBuilder<>().connect();
        assertNull(aeronPublisher);
    }

    @Test(expected = AeronException.class)
    public void channelCannotBeNull() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronPublisherImpl aeronPublisher = new AeronPublisherBuilder<>()
                .withDriver(mediaDriver)
                .connect();
        assertNull(aeronPublisher);
    }

    @Test(expected = AeronException.class)
    public void channelHasToStartWithAeronColon() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronPublisherImpl aeronPublisher = new AeronPublisherBuilder<>()
                .withDriver(mediaDriver)
                .withChannel("aeron")
                .connect();
        assertNull(aeronPublisher);
    }

    @Test
    public void createDefaultPublisherOverIpc() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronPublisherImpl aeronPublisher = new AeronPublisherBuilder<>()
                .withDriver(mediaDriver)
                .withChannel("aeron:ipc")
                .connect();
        assertNotNull(aeronPublisher);
    }

    @Test
    public void createDefaultPublisherOverUdp() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronPublisherImpl aeronPublisher = new AeronPublisherBuilder<>()
                .withDriver(mediaDriver)
                .withChannel("aeron:udp?endpoint=localhost:40123")
                .connect();
        assertNotNull(aeronPublisher);
    }

    @Test
    public void createPublisherWithAeron() throws IOException {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        Aeron aeron = Aeron.connect(new Aeron.Context().aeronDirectoryName(mediaDriver.aeronDirectoryName()));

        AeronPublisherImpl aeronPublisher = new AeronPublisherBuilder<>()
                .withDriver(mediaDriver)
                .withAeron(aeron)
                .withChannel("aeron:ipc")
                .connect();
        assertNotNull(aeronPublisher);
    }

    @Test
    public void createPublisherWithContextOverIpc() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        Aeron.Context context = new Aeron.Context();

        AeronPublisherImpl aeronPublisher = new AeronPublisherBuilder<>()
                .withDriver(mediaDriver)
                .withContext(context)
                .withChannel("aeron:ipc")
                .connect();
        assertNotNull(aeronPublisher);
    }

    @Test
    public void createDefaultPublisherWithOneStream() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronPublisherImpl aeronPublisher = new AeronPublisherBuilder<>()
                .withDriver(mediaDriver)
                .withChannel("aeron:ipc")
                .withStreams(1)
                .connect();
        assertNotNull(aeronPublisher);
        assertEquals(1, aeronPublisher.streamCount());
    }

    @Test
    public void createDefaultPublisherWithMultipleStreams() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        AeronPublisherImpl aeronPublisher = new AeronPublisherBuilder<>()
                .withDriver(mediaDriver)
                .withChannel("aeron:ipc")
                .withStreams(10)
                .connect();
        assertNotNull(aeronPublisher);
        assertEquals(10, aeronPublisher.streamCount());
    }
}
