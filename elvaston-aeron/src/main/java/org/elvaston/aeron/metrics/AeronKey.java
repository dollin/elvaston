package org.elvaston.aeron.metrics;

/**
 * Helper class of the keys used to retrieve a particular metric. This isn't the complete set
 * (yagni) as we are only adding the keys we are actually using for cleaner code.
 */
public enum AeronKey {
    NULL(-1), SND_CHANNEL(24), RCV_CHANNEL(25);

    private int key;

    public int key() {
        return key;
    }

    AeronKey(int key) {
        this.key = key;
    }
}
