package org.elvaston.aeron.driver;

import static org.elvaston.aeron.common.AeronConfiguration.AERON_DIRECTORY;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.aeron.driver.MediaDriver;
import org.elvaston.aeron.exception.AeronException;
import org.junit.Test;

import java.io.IOException;

/**
 * Tests that we can create a media driver.
 */
public class AeronMediaDriverBuilderTest {

    @Test
    public void launchEmbeddedDriver() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().withEmbedded().launch();
        assertNotNull(mediaDriver);
    }

    @Test(expected = AeronException.class)
    public void exceptionWhenLaunchingSeparateDriverWithoutDirectory() {
        MediaDriver mediaDriver = new AeronMediaDriverBuilder().launch();
        assertNull(mediaDriver);
    }

    @Test
    public void launchSeparateDriverWithDirectory() throws IOException {
        String directory = System.getProperty("user.dir") + "/" + AERON_DIRECTORY;
        MediaDriver mediaDriver = new AeronMediaDriverBuilder()
                .withDirectory(directory)
                .launch();
        assertNotNull(mediaDriver);
    }
}
