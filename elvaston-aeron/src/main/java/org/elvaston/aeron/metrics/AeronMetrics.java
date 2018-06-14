package org.elvaston.aeron.metrics;

import static io.aeron.CncFileDescriptor.cncVersionOffset;
import static io.aeron.CncFileDescriptor.createCountersMetaDataBuffer;
import static io.aeron.CncFileDescriptor.createCountersValuesBuffer;
import static io.aeron.CncFileDescriptor.createMetaDataBuffer;
import static org.elvaston.aeron.common.AeronConfiguration.AERON_DIRECTORY;
import static org.elvaston.aeron.metrics.AeronKey.RCV_CHANNEL;
import static org.elvaston.aeron.metrics.AeronKey.SND_CHANNEL;

import org.agrona.IoUtil;
import org.agrona.concurrent.UnsafeBuffer;
import org.agrona.concurrent.status.CountersReader;

import java.io.File;
import java.io.PrintStream;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Metrics for the aeron interactions.
 */
public class AeronMetrics {
    private static final int COMMAND_AND_CONTROL_VERSION = 6;

    private final CountersReader counters;

    public AeronMetrics(CountersReader counters) {
        this.counters = counters;
    }

    public AeronMetrics() {
        this(AeronMetrics.mapCounters(AERON_DIRECTORY));
    }

    public AeronMetrics(String aeronDirectory) {
        this(AeronMetrics.mapCounters(aeronDirectory));
    }

    private static CountersReader mapCounters(String aeronDirectory) {
        MappedByteBuffer cncByteBuffer = IoUtil.mapExistingFile(new File(aeronDirectory, "cnc.dat"), "cnc");
        UnsafeBuffer cncMetaData = createMetaDataBuffer(cncByteBuffer);
        int cncVersion = cncMetaData.getInt(cncVersionOffset(0));
        if (COMMAND_AND_CONTROL_VERSION != cncVersion) {
            throw new IllegalStateException(String.format(
                    "Aeron CnC version does not match: version=%s required=%s",
                    cncVersion,
                    COMMAND_AND_CONTROL_VERSION));
        }
        return new CountersReader(
                createCountersMetaDataBuffer(cncByteBuffer, cncMetaData),
                createCountersValuesBuffer(cncByteBuffer, cncMetaData));

    }

    Stream<AeronMetric> metrics() {
        List<AeronMetric> metrics = new ArrayList<>();
        counters.forEach((key, typeId, keyBuffer, label) -> metrics.add(new AeronMetric(
                        key,
                        label,
                        counters.getCounterValue(key))));
        return metrics.stream();
    }

    public void print(PrintStream out) {
        metrics().sorted().forEach((aeronMetric) -> out.format("%3d: %,20d - %s%n",
                        aeronMetric.key(),
                        aeronMetric.value(),
                        aeronMetric.description()));
    }

    public long channelSent() {
        return get(SND_CHANNEL.key());
    }

    public long channelReceived() {
        return get(RCV_CHANNEL.key());
    }

    private long get(int key) {
        return metrics()
                .filter(aeronMetric -> aeronMetric.key() == key)
                .findFirst()
                .orElse(AeronMetric.NULL_METRIC)
                .value();
    }

}

