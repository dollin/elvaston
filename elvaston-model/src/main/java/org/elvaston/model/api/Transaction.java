package org.elvaston.model.api;

import java.io.Serializable;

/**
 * Transaction interface for all movements between parties. Extends {@code Serializable}
 * so it can be sent and received over Kafka.
 */
public interface Transaction extends Serializable {

    /**
     * Get a given Media representation of the Transaction.
     * @param media Media to use
     * @return Media
     */
    Media print(Media media);

    boolean is(String id);
}
