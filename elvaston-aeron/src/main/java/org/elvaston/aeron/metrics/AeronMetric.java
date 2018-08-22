package org.elvaston.aeron.metrics;

import static org.elvaston.aeron.metrics.AeronMetricKey.NULL;

import org.jetbrains.annotations.NotNull;

/**
 * Pojo for the AeronMetrics.
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
    public int compareTo(@NotNull AeronMetric that) {
        if (this.equals(that)) {
            return 0;
        }

        return this.key() - that.key();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AeronMetric that = (AeronMetric) obj;

        return key == that.key;
    }

    @Override
    public int hashCode() {
        return key;
    }
}
