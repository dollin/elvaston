package org.elvaston.kafka.api;

/**
 * Enum used to capture the state of a message sent to a {@link Processor}.
 */
public enum ProcessorState {
    UNKNOWN, PENDING, SENT, FAILED
}
