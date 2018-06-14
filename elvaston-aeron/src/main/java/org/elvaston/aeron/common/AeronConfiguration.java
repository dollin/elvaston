package org.elvaston.aeron.common;

import static java.lang.System.getProperty;

public class AeronConfiguration {
    public static final String AERON_DIRECTORY = "build/aeron";
    public static final String CHANNEL = getProperty("aeron.channel", "aeron:udp?endpoint=localhost:40123");
    public static final int STREAM_ID = Integer.getInteger("aeron.streamId", 10);
    public static final int NUMBER_OF_MESSAGES = Integer.getInteger("aeron.messages", 1000000);
    public static final int NUMBER_OF_STREAMS = Integer.getInteger("aeron.streams", 1);
    public static final int FRAME_LIMIT = Integer.getInteger("aeron.frameCountLimit", 20);
}
