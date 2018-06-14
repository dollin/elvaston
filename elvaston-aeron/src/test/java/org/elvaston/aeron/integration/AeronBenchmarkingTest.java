package org.elvaston.aeron.integration;

import io.aeron.Aeron;
import io.aeron.Publication;
import io.aeron.Subscription;
import io.aeron.driver.MediaDriver;
import io.aeron.driver.ThreadingMode;
import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import org.agrona.DirectBuffer;
import org.agrona.concurrent.BusySpinIdleStrategy;
import org.agrona.concurrent.IdleStrategy;
import org.agrona.concurrent.UnsafeBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elvaston.aeron.common.AeronConfiguration;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Timings on a quad-core 2:
 * 1 stream: 10.1M messages / second
 * 10 streams: 6.1M messages / second
 * 50 streams: 3.1M messages / second
 * 100 streams: 1.5M messages / second
 *
 *
 */
public class AeronBenchmarkingTest {
    private static final Logger LOG = LogManager.getLogger(AeronBenchmarkingTest.class);

    public static final int MESSAGE_BYTES = 40;
    public static final int NUM_MESSAGES = 10_000_000;
    public static final int NUM_STREAMS = 10;

    private MediaDriver driver;
    private Aeron aeron;

    public AeronBenchmarkingTest() {

    }

    /**
     * Main function to run the benchmarking.
     * @throws InterruptedException when thread is interrupted
     */
    public void runBenchmarking() throws InterruptedException {
        MediaDriver.loadPropertiesFile("aeron-benchmarking.properties");

        MediaDriver.Context driverContext = new MediaDriver.Context();
        driverContext.threadingMode(ThreadingMode.DEDICATED);
        driver = MediaDriver.launchEmbedded(driverContext);

        Aeron.Context aeronContext = new Aeron.Context();
        aeronContext.aeronDirectoryName(driver.aeronDirectoryName());
        aeron = Aeron.connect(aeronContext);

        Thread subscriber = new Thread(() -> {
            try {
                runSubscriber();
            } catch (InterruptedException e) {
                LOG.warn("Subscribe has been interrupted", e);
            }
        });
        Thread publisher = new Thread(() -> {
            try {
                runPublisher();
            } catch (InterruptedException e) {
                LOG.warn("Publish has been interrupted", e);
            }
        });
        subscriber.start();
        publisher.start();
        subscriber.join();
        publisher.join();

        aeron.close();
        driver.close();
    }


    public static void main(String... args) throws InterruptedException, IOException {
        AeronBenchmarkingTest aeronBenchmarking = new AeronBenchmarkingTest();
        aeronBenchmarking.runBenchmarking();
    }

    /**
     * Method to run the subscriber.
     * @throws InterruptedException when interrupted
     */
    public void runSubscriber() throws InterruptedException {
        Subscription[] subs = new Subscription[NUM_STREAMS];
        final AtomicLong numReads = new AtomicLong();
        FragmentHandler handler = new FragmentHandler() {
            @Override
            public void onFragment(DirectBuffer buffer, int offset, int length, Header header) {
                long reads = numReads.getAndIncrement();
                if (reads % 1000000 == 0) {
                    System.err.println("read message " + reads);
                }
            }
        };

        for (int i = 0; i < NUM_STREAMS; i++) {
            subs[i] = aeron.addSubscription(AeronConfiguration.CHANNEL, i);
        }

        long  startTime = System.currentTimeMillis();
        IdleStrategy idler = new BusySpinIdleStrategy();
        while (numReads.get() < NUM_MESSAGES) {
            int receivedFragments = 0;
            for (int i = 0; i < NUM_STREAMS; i++) {
                receivedFragments += subs[i].poll(handler, 1_000_000);
            }
            idler.idle(receivedFragments);
        }
        System.err.println("Elapsed milliseconds for "
                + NUM_MESSAGES + " messages is "
                + (System.currentTimeMillis() - startTime));
    }

    /**
     * Run the publisher.
     * @throws InterruptedException when itnterrupted
     */
    public void runPublisher() throws InterruptedException {
        Publication[] pubs = new Publication[NUM_STREAMS];
        for (int i = 0; i < NUM_STREAMS; i++) {
            pubs[i] = aeron.addPublication(AeronConfiguration.CHANNEL, i);
        }

        Random rand = new Random();
        long attempts = 0;
        long writes = 0;
        UnsafeBuffer buff = new UnsafeBuffer(new byte[MESSAGE_BYTES]);
        for (int i = 0; i < NUM_MESSAGES; i++) {
            Publication publisher = pubs[rand.nextInt(NUM_STREAMS)];
            do {
                attempts++;
            }
            while (publisher.offer(buff) < 0);

            writes++;
            if (writes % 1_000_000 == 0) {
                System.err.println("wrote message " + writes + " in " + attempts + " attempts");
            }
        }
    }
}