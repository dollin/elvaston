package org.elvaston.aeron.driver;

import io.aeron.driver.MediaDriver;
import io.aeron.driver.ThreadingMode;
import org.agrona.concurrent.SleepingIdleStrategy;
import org.elvaston.aeron.exception.AeronErrorCode;
import org.elvaston.aeron.exception.AeronException;

import java.util.concurrent.TimeUnit;

/**
 * Builder to create and launch a mediaDriver.
 */
public class AeronMediaDriverBuilder {

    private boolean embedded;
    private String directory;

    public AeronMediaDriverBuilder withDirectory(String directory) {
        this.directory = directory;
        return this;
    }

    public AeronMediaDriverBuilder withEmbedded() {
        this.embedded = true;
        return this;
    }

    /**
     * Launch the MediaDriver.
     * @return MediaDriver
     */
    public MediaDriver launch() {
        if (embedded) {
            return MediaDriver.launchEmbedded();
        }

        validate();

        MediaDriver.Context ctx = new MediaDriver.Context()
                .threadingMode(ThreadingMode.SHARED)
                .conductorIdleStrategy(new SleepingIdleStrategy(TimeUnit.MILLISECONDS.toNanos(1)))
                .receiverIdleStrategy(new SleepingIdleStrategy(TimeUnit.MILLISECONDS.toNanos(1)))
                .senderIdleStrategy(new SleepingIdleStrategy(TimeUnit.MILLISECONDS.toNanos(1)))
                .warnIfDirectoriesExist(false)
                .dirsDeleteOnStart(true)
                .aeronDirectoryName(directory);
        return MediaDriver.launch(ctx);
    }

    private void validate() {
        if (!embedded && null == directory) {
            throw new AeronException(AeronErrorCode.NULL_DIRECTORY);
        }
    }
}
