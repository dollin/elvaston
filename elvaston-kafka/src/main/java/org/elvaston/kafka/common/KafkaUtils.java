package org.elvaston.kafka.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Utilities class for static general purpose functions.
 */
public class KafkaUtils {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaUtils.class);

    /**
     * Sleep and ignore if interrupted.
     * @param unit the {@code TimeUnit} to use
     * @param duration the duration of the unit to sleep for
     */
    public static void sleep(TimeUnit unit, long duration) {
        try {
            unit.sleep(duration);
        } catch (InterruptedException e) {
            LOG.info("Interrupted, but ok as we were only sleeping for {} {} anyway", duration, unit);
        }
    }
}
