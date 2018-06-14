package org.elvaston.aeron.common;

import static org.elvaston.aeron.exception.AeronErrorCode.INVALID_CHANNEL;
import static org.elvaston.aeron.exception.AeronErrorCode.NULL_DRIVER;

import io.aeron.Aeron;
import io.aeron.driver.MediaDriver;
import org.elvaston.aeron.exception.AeronException;

import java.util.Objects;

/**
 * Base builder to create the necessary connected publishers and subscribers.
 */
public abstract class AeronBaseBuilder<R, T> {
    protected MediaDriver driver;
    protected Aeron.Context context;
    protected Aeron aeron;
    protected String channel;
    protected int streams = 1;

    public AeronBaseBuilder<R, T> withDriver(MediaDriver driver) {
        this.driver = driver;
        return this;
    }

    public AeronBaseBuilder<R, T> withAeron(Aeron aeron) {
        this.aeron = aeron;
        return this;
    }

    public AeronBaseBuilder<R, T> withContext(Aeron.Context context) {
        this.context = context;
        return this;
    }

    public AeronBaseBuilder<R, T> withChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public AeronBaseBuilder<R, T> withStreams(int streams) {
        this.streams = streams;
        return this;
    }

    protected void validate() {
        if (Objects.isNull(driver)) {
            throw new AeronException(NULL_DRIVER);
        }
        if (channel == null || !channel.startsWith("aeron:")) {
            throw new AeronException(NULL_DRIVER.code(), INVALID_CHANNEL.format(channel));
        }
    }

    public abstract R connect();
}