package org.elvaston.aeron.metrics;

import static org.elvaston.aeron.metrics.AeronKey.NULL;

/**
 * TODO Add javadoc.
 */
public class AeronMetric implements Comparable<AeronMetric> {
    public static final AeronMetric NULL_METRIC = new AeronMetric(NULL.key(), "null", -1L);

    private final int key;
    private final long value;
    private final String description;

    AeronMetric(int key, String description, long value) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    long value() {
        return value;
    }

    int key() {
        return key;
    }

    String description() {
        return description;
    }

    @Override
    public String toString() {
        return  description + "=" + value;
    }

    @Override
    public int compareTo(AeronMetric that) {
        return this.key() - that.key();
    }
}
