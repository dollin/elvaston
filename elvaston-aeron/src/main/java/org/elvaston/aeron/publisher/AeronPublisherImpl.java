package org.elvaston.aeron.publisher;

import static org.agrona.BufferUtil.allocateDirectAligned;

import io.aeron.Aeron;
import io.aeron.Publication;
import io.aeron.driver.MediaDriver;
import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.wire.Wire;
import net.openhft.chronicle.wire.WireType;
import org.agrona.BitUtil;
import org.agrona.CloseHelper;
import org.agrona.concurrent.UnsafeBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elvaston.aeron.common.AeronMessage;
import org.elvaston.aeron.exception.AeronErrorCode;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Publisher implementation for publication of messages.
 * @param <T> message type for publication
 */
public class AeronPublisherImpl<T> {

    private static final Logger LOG = LogManager.getLogger(AeronPublisherImpl.class);

    private static int bufferLength = 256;
    private static final ByteBuffer buffer = allocateDirectAligned(bufferLength, BitUtil.CACHE_LINE_LENGTH);
    private static final UnsafeBuffer publisherBuffer = new UnsafeBuffer(buffer);

    private final MediaDriver driver;
    private final Aeron aeron;
    private final List<Publication> publications;

    AeronPublisherImpl(MediaDriver driver, Aeron aeron, List<Publication> publications) {
        this.driver = driver;
        this.aeron = aeron;
        this.publications = publications;
    }

    //TODO move to a publisherloop
//    private void start(ExecutorService service, List<Publication> publications) {
//
//        Future<Void> future = service.submit(() -> {
//
//            publish(publications);
//            return null;
//        });
//
//        try {
//            future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            LOG.error("Exception while getting future", e);
//        }
//    }

    /**
     * Stop the publisher.
     */
    public void stop() {
        //TODO STOP ALL
        publications.get(0).close();
        aeron.close();
        CloseHelper.quietClose(driver);
    }

    /**
     * Publish to Aeron.
     * @param message message to send
     */
    public long publish(AeronMessage<T> message) {
        Bytes<ByteBuffer> bytes = Bytes.elasticByteBuffer();
        WireType wireType = WireType.TEXT;
        Wire wire = wireType.apply(bytes);

        wire.padToCacheAlign();
        wire.write(() -> "aeronMessage:").marshallable(message);
        byte[] msgInBtyes = wire.bytes().toByteArray();
        int length = msgInBtyes.length;
        publisherBuffer.putBytes(0, msgInBtyes);

//        byte[] messageBytes = message.toString().getBytes(Charset.defaultCharset());

  //      publisherBuffer.putBytes(0, messageBytes);
        int stream = (int) (Math.random() * 1_000 % streamCount());
        LOG.info("Offer to streamId: {}, length: {}, msg: {}", stream, length, message);

        long result = publications.get(stream).offer(publisherBuffer, 0, length);
        logResult(publications.get(stream).isConnected(), stream, result);

        return result;
    }


    private void logResult(boolean isConnected, int stream, long result) {
        if (result < 0L) {
            LOG.warn(AeronErrorCode.descForCode(result));
        } else {
            LOG.info("Published on streamId: {}", stream);
        }

        if (!isConnected) {
            LOG.warn("No active subscribers detected on streamId: {}", stream);
        }
    }

    public int streamCount() {
        return publications.size();
    }
}
