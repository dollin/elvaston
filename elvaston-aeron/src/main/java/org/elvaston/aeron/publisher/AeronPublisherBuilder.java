package org.elvaston.aeron.publisher;

import io.aeron.Aeron;
import io.aeron.Publication;
import org.elvaston.aeron.common.AeronBaseBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Builder to create the necessary connected publishers.
 */
public class AeronPublisherBuilder<T> extends AeronBaseBuilder<AeronPublisherImpl<T>, T> {

    /**
     * Connect and return the publisher(s).
     * @return the publisher implementation
     */
    public AeronPublisherImpl<T> connect() {
        validate();

        if (null == context) {
            context = new Aeron.Context();
        }

        if (null == aeron) {
            context.aeronDirectoryName(driver.aeronDirectoryName());
            aeron = Aeron.connect(context);
        }

        List<Publication> publications = IntStream.range(0, streams)
                .mapToObj(value -> aeron.addPublication(channel, value))
                .collect(Collectors.toList());

        return new AeronPublisherImpl<>(driver, aeron, publications);
    }
}
