package org.elvaston.kafka.gateway;

import org.elvaston.kafka.api.GatewayCache;
import org.elvaston.kafka.api.ProcessorState;
import org.elvaston.model.api.Transaction;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of the {@link GatewayCache} used to store the state of activity
 * received by this run of the service.
 */
public class GatewayCacheImpl implements GatewayCache {

    private final BlockingQueue<Transaction> queue = new LinkedBlockingQueue<>();
    private final ConcurrentMap<Transaction, ProcessorState> cache = new ConcurrentHashMap<>();

    @Override
    public ProcessorState stateOf(String id) {
        return cache.entrySet()
                .stream()
                .filter(e -> e.getKey().is(id))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(ProcessorState.UNKNOWN);
    }

    @Override
    public void update(Transaction transaction, ProcessorState state) {
        cache.put(transaction, state);
    }

    @Override
    public void add(Transaction transaction) {
        queue.add(transaction);
        cache.put(transaction, ProcessorState.PENDING);
    }
}
