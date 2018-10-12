package org.elvaston.kafka.common;

import org.elvaston.model.api.Transaction;

import java.io.Serializable;

/**
 * {@code Serializable} Payload of the objects we are sending and receiving to Kafka.
 */
public class KafkaPayload implements Serializable {
    private String name = "dollin";

    private String message;

    private Transaction transaction;

    public KafkaPayload(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
