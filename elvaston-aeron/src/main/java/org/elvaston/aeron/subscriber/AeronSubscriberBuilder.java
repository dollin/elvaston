package org.elvaston.aeron.subscriber;

import io.aeron.Aeron;
import io.aeron.Subscription;
import org.elvaston.aeron.common.AeronBaseBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Builder to create the necessary connected subscribers.
 */
public class AeronSubscriberBuilder<T> extends AeronBaseBuilder<AeronSubscriberImpl<T>, T> {

    /**
     * Connect and return the subscribers(s).
     * @return the subscriber implementation
     */
    public AeronSubscriberImpl<T> connect() {
        validate();

        if (null == context) {
            context = new Aeron.Context();
        }

        if (null == aeron) {
            context.aeronDirectoryName(driver.aeronDirectoryName());
            aeron = Aeron.connect(context);
        }

        List<Subscription> subscriptions = IntStream.range(0, streams)
                .mapToObj(value -> aeron.addSubscription(channel, value))
                .collect(Collectors.toList());

        return new AeronSubscriberImpl<>(driver, aeron, subscriptions);
    }
}
