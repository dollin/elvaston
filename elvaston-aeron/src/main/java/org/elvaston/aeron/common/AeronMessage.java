package org.elvaston.aeron.common;

import net.openhft.chronicle.wire.AbstractMarshallable;

/**
 * Message to send and receive over Aeron.
 * @param <T> payload type to send/receive
 */
public class AeronMessage<T> extends AbstractMarshallable {
    private int sessionId;
    private int timeStamp;
    private T payload;

    public int getSessionId() {
        return sessionId;
    }

    public AeronMessage<T> withSessionId(int sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public AeronMessage<T> withTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public T getPayload() {
        return payload;
    }

    public AeronMessage<T> withPayload(T payload) {
        this.payload = payload;
        return this;
    }
}