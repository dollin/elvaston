package org.elvaston.kafka.common;

import java.io.Serializable;

/**
 * TODO Add javadoc.
 */
public class KafkaPayload implements Serializable {
    private String name = "dollin";

    private String message;

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
