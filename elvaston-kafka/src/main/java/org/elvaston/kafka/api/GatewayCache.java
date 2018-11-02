package org.elvaston.kafka.api;

import org.elvaston.kafka.gateway.GatewayVerticleImpl;
import org.elvaston.model.api.Transaction;

/**
 * Cache to store the {@link ProcessorState} of the activity sent to the {@link GatewayVerticleImpl}.
 */
public interface GatewayCache {

    ProcessorState stateOf(String id);

    void update(Transaction transaction, ProcessorState state);

    void add(Transaction transaction);
}
