package org.elvaston.aeron.exception;

/**
 * Class for handling exceptions from the application.
 */
public class AeronException extends RuntimeException {

    long code;
    String description;

    /**
     * Constructs a new exception with the specified code & description.
     * @param code the error code
     * @param description the description to use
     */
    public AeronException(long code, String description) {
        super("[" + code + "] " + description);
        this.code = code;
        this.description = description;
    }

    public AeronException(AeronErrorCode errorCode) {
        this(errorCode.code(), errorCode.desc());
    }
}
