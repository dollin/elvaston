package org.elvaston.aeron.exception;

import io.aeron.Publication;

import java.util.Arrays;

public enum AeronErrorCode {
    BACK_PRESSURE(Publication.BACK_PRESSURED, "Offer failed due to back pressure"),
    NOT_CONNECTED(Publication.NOT_CONNECTED,
            "Offer failed because publisher is not connected to subscriber"),
    ADMIN_ACTION(Publication.ADMIN_ACTION,
            "Offer failed because of an administration action in the system"),
    CLOSED(Publication.CLOSED, "Offer failed publication is closed"),
    UNKNOWN(-5L, "Offer failed due to unknown reason"),
    NO_ACTIVE_SUBSCRIBERS(10L, "No active subscribers detected"),
    NULL_DRIVER(100L, "Driver is null"),
    INVALID_CHANNEL(101L, "Channel %s does not start w/ 'aeron:'"),
    NULL_DIRECTORY(102L, "Null directory w/ separate mediaDriver");

    private long code;
    private String desc;

    AeronErrorCode(long code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     *
     * @param code long code value to get the description for.
     * @return String description for the code provided
     */
    public static String descForCode(long code) {
        return Arrays.stream(AeronErrorCode.values())
                .filter(aeronErrorCode -> aeronErrorCode.code() == code)
                .findFirst()
                .orElse(UNKNOWN).desc();
    }

    public long code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public String format(Object... args) {
        return String.format(desc(), args);
    }
}
