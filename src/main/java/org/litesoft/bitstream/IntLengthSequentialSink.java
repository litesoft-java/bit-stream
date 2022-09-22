package org.litesoft.bitstream;

import org.litesoft.annotations.NotNull;
import org.litesoft.annotations.PackageFriendlyForTesting;

/**
 * Convert a Stream of six bit chunks, presumably from a <code>BitStreamSequentialSource</code>, into a positive <code>int</code> (length).
 */
public class IntLengthSequentialSink extends AbstractBitBufferStreamSequentialSink<Integer> {
    @PackageFriendlyForTesting
    static final String LENGTH_WENT_NEGATIVE = "Stream error length went negative";

    public IntLengthSequentialSink() {
        super( new SBS() );
    }

    @Override @NotNull
    public Integer getValue() {
        return sinkBitStream.getValue();
    }

    static class SBS extends SinkBitStream<Integer> implements BitConstants {
        private int value;
        private boolean full;

        SBS() {
            super( 6 );
        }

        public Integer getValue() {
            if ( full ) {
                return value;
            }
            throw new IllegalStateException( VALUE_NOT_POPULATED );
        }

        @Override
        public int bitsCurrentlyNeeded( BitBuffer buffer ) {
            return full ? 0 : (6 - buffer.availableBits());
        }

        @Override
        public void addBits( int bits ) { // 6 bits are More (6th) bit and 5 bits of value
            int b5 = bits & MASKS[5];
            value = (value << 5) + b5;
            full = (0 == (bits - b5)); // More bit was 0 -> no More (Full)
            if ( value < 0 ) {
                throw new IllegalStateException( LENGTH_WENT_NEGATIVE );
            }
        }
    }
}
