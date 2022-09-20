package org.litesoft.bitstream;

public abstract class AbstractBitStreamSequentialSink implements BitStreamSequentialSink {
    public static final String VALUE_NOT_POPULATED = "Stream error value not fully populated";

    public final String toString() {
        int bitsNeeded = bitsCurrentlyNeeded();
        return toStringName() + ": " +
               ((bitsNeeded == 0) ? "Ready" : ("at least " + bitsNeeded + " bits needed"));
    }

    protected String toStringName() {
        return getClass().getSimpleName();
    }
}
