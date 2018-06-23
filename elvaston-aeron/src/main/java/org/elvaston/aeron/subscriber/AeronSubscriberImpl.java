package org.elvaston.aeron.subscriber;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.elvaston.aeron.common.AeronConfiguration.FRAME_LIMIT;
//import static org.elvaston.aeron.common.AeronConfiguration.STREAM_ID;

import io.aeron.Aeron;
import io.aeron.FragmentAssembler;
import io.aeron.Subscription;
import io.aeron.driver.MediaDriver;
import io.aeron.logbuffer.FragmentHandler;
import org.agrona.CloseHelper;
import org.agrona.LangUtil;
import org.agrona.concurrent.BusySpinIdleStrategy;
import org.agrona.concurrent.IdleStrategy;
import org.agrona.concurrent.SigInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class AeronSubscriberImpl<T> {

    private static final Logger LOG = LogManager.getLogger(AeronSubscriberImpl.class);

    ExecutorService executor = Executors.newFixedThreadPool(1);

    private MediaDriver driver;
    private Aeron aeron;
    private List<Subscription> subscriptions;

    AeronSubscriberImpl(MediaDriver driver, Aeron aeron, List<Subscription> subscriptions) {
        this.driver = driver;
        this.aeron = aeron;
        this.subscriptions = subscriptions;
    }

//    private AeronSubscriberImpl() {
//        MediaDriver.loadPropertiesFile("aeron-subscriber.properties");
//        withDriver();
//        withAeron();
//        withSubscription();
//    }
//
//    private void withDriver() {
//        driver = MediaDriver.launchEmbedded();
//    }
//
//    private void withAeron() {
//        Context ctx = new Aeron.Context()
//                .availableImageHandler(availableHandler)
//                .unavailableImageHandler(unavailableHandler);
//        ctx.aeronDirectoryName(driver.aeronDirectoryName());
//        aeron = Aeron.connect(ctx);
//    }
//
//    private void withSubscription() {
//        //TODO fix
//        // IntStream.range(0, NUMBER_OF_STREAMS)
////                .forEach(i -> subscriptions.add(CHANNEL, i));
////        subscription = aeron.addSubscription(CHANNEL, STREAM_ID);
//    }

//    private void subscribe() {
//        long ts = System.currentTimeMillis();
//        LOG.info("Starting subscription");
//        //TODO start(subscription);
//
//        LOG.info("Stopping subscription");
//        //TODO stop(subscription, aeron, driver);
//        LOG.info("Subscription took {} ms", System.currentTimeMillis() - ts);
//    }

    /**
     * Start listening on the subscription stream id(s).
     */
    public void start() {
        subscriptions.forEach(subscription -> {
            //subscription.streamId();
            AtomicBoolean running = new AtomicBoolean(true);
            SigInt.register(() -> running.set(false));
            LOG.info("Starting loop for streamId:{}", subscription.streamId());
            executor.submit(() -> {
                subscriberLoop(FRAGMENT, FRAME_LIMIT, running, new BusySpinIdleStrategy()).accept(subscription);
            });
        });
    }

    /**
     * Shutdown the subscriber(s), then aeron and then the driver.
     */
    private void stop() {
        subscriptions.get(0).close();
        aeron.close();
        CloseHelper.quietClose(driver);
    }

    private Consumer<Subscription> subscriberLoop(FragmentHandler fragmentHandler,
                                                  int limit,
                                                  AtomicBoolean running,
                                                  IdleStrategy idleStrategy) {
        return subscription -> {
            while (true) {
                try {
                    if (running.get()) {
                        idleStrategy.idle(subscription.poll(new FragmentAssembler(fragmentHandler), limit));
                        continue;
                    }
                } catch (Exception e) {
                    LangUtil.rethrowUnchecked(e);
                }
                return;
            }
        };
    }

    private static final FragmentHandler FRAGMENT = (buffer, offset, length, header) -> {
        byte[] data = new byte[length];
        buffer.getBytes(offset, data);
        LOG.info("Message received  - stream: {} length:{}, offset:{}, data: {}",
                header.streamId(),
                length,
                offset,
                new String(data, UTF_8));
    };

//    private static final AvailableImageHandler AVAILABLE_IMAGE = image -> {
//        Subscription subscription = image.subscription();
//        LOG.info("Available image on {} streamId={} sessionId={} from {}",
//                subscription.channel(),
//                subscription.streamId(),
//                image.sessionId(),
//                image.sourceIdentity());
//    };

//    private final UnavailableImageHandler unavailableHandler = image -> {
//        Subscription subscription = image.subscription();
//        LOG.info("Unavailable image on {} streamId={} sessionId={}",
//                subscription.channel(),
//                   subscription.streamId(),
//                image.sessionId());
//    };

    public int streamCount() {
        return subscriptions.size();
    }
}
