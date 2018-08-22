package org.elvaston.aeron.metrics;

import static io.aeron.CncFileDescriptor.cncVersionOffset;
import static io.aeron.CncFileDescriptor.createCountersMetaDataBuffer;
import static io.aeron.CncFileDescriptor.createCountersValuesBuffer;
import static io.aeron.CncFileDescriptor.createMetaDataBuffer;
import static org.elvaston.aeron.common.AeronConfiguration.AERON_DIRECTORY;
import static org.elvaston.aeron.metrics.AeronMetricKey.BYTES_RECEIVED;
import static org.elvaston.aeron.metrics.AeronMetricKey.BYTES_SENT;
import static org.elvaston.aeron.metrics.AeronMetricKey.RCV_CHANNEL;
import static org.elvaston.aeron.metrics.AeronMetricKey.SND_CHANNEL;

import org.agrona.IoUtil;
import org.agrona.concurrent.UnsafeBuffer;
import org.agrona.concurrent.status.CountersReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Metrics for the aeron interactions.
 */
public class AeronMetrics {
    private static final Logger LOG = LoggerFactory.getLogger(AeronMetrics.class);

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

    /**
     * log the metrics.
     */
    public void log() {
        metrics().sorted().forEach((aeronMetric) -> LOG.info("[{}] {} = {}",
                aeronMetric.key(),
                aeronMetric.description(),
                aeronMetric.value()
        ));
    }

    public long sndChannel() {
        return get(SND_CHANNEL);
    }

    public long rcvChannel() {
        return get(RCV_CHANNEL);
    }

    public long bytesSent() {
        return get(BYTES_SENT);
    }

    public long bytesReceived() {
        return get(BYTES_RECEIVED);
    }

    private long get(AeronMetricKey aeronMetricKey) {
        return metrics()
                .filter(aeronMetric -> aeronMetric.key() == aeronMetricKey.key())
                .findFirst()
                .orElse(AeronMetric.NULL_METRIC)
                .value();
    }
}