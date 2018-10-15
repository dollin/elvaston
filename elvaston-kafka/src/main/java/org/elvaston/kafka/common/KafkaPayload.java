package org.elvaston.kafka.common;

import org.elvaston.model.api.Transaction;

import java.io.Serializable;

/**
 * {@code Serializable} Payload of the objects we are sending and receiving to Kafka.
 */
public class KafkaPayload implements Serializable {
    private String id;
    private Transaction transaction;

    public KafkaPayload(String id, Transaction transaction) {
        this.id = id;
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "KafkaPayload [id: " + id + ", transaction: " + transaction + "]";
    }
}
