package org.litesoft.bitstream;

import java.util.UUID;

import org.litesoft.annotations.NotNull;
import org.litesoft.utils.Hex;

import static org.litesoft.utils.UuidNonDashCharOffsets.HEX_DIGIT_OFFSETS;

/**
 * Convert a Stream of six bit chunks, presumably from a <code>BitStreamSequentialSource</code>, into a positive <code>int</code> (length).
 */
public class UuidSequentialSink extends AbstractBitBufferStreamSequentialSink<UUID> {
    public UuidSequentialSink() {
        super( new SBS() );
    }

    @Override @NotNull
    public UUID getValue() {
        return sinkBitStream.getValue();
    }

    static class SBS extends SinkBitStream<UUID> implements BitConstants {
        private final StringBuilder value = new StringBuilder();
        private int lastOffset = -1;
        private int hexDigitsIndex;
        private boolean full;

        SBS() {
            super( 4 );
        }

        public UUID getValue() {
            if ( !full ) {
                throw new IllegalStateException( VALUE_NOT_POPULATED );
            }
            return UUID.fromString( value.toString() );
        }

        @Override
        public int bitsCurrentlyNeeded( BitBuffer buffer ) {
            return full ? 0 : (4 - buffer.availableBits());
        }

        @Override
        public void addBits( int bits ) { // 4 bits
            int offset = HEX_DIGIT_OFFSETS[hexDigitsIndex++];
            if ( ++lastOffset != offset ) {
                value.append( '-' );
                lastOffset = offset;
            }
            value.append( Hex.charFrom( bits ) );
            full = !(hexDigitsIndex < HEX_DIGIT_OFFSETS.length);
        }
    }
}
