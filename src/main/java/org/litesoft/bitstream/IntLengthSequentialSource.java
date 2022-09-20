package org.litesoft.bitstream;

import org.litesoft.annotations.PackageFriendlyForTesting;

/**
 * Convert a positive <code>int</code> into a <code>BitStreamSequentialSource</code> of utf8 significant bit "like" 6 bit chunks.
 */
public class IntLengthSequentialSource extends AbstractBitBufferStreamSequentialSource {
    @PackageFriendlyForTesting
    static final String LENGTH_NEGATIVE_PREFIX = "length may not be negative, but was: ";

    public IntLengthSequentialSource( int length ) {
        super( new SBS( length ) );
    }

    static class SBS extends SourceBitStream {
        private final byte[] chunksOf6bit = new byte[7]; // 31 / 5 -> 6+ -> 7 max chunks
        private int populatedChunks;

        public SBS( int length ) {
            super( 6 );
            if ( length < 0 ) {
                throw new IllegalStateException( LENGTH_NEGATIVE_PREFIX + length );
            }
            chunksOf6bit[populatedChunks++] = (byte)(length & 31); // 5 bits
            while ( (length = length >> 5) != 0 ) {
                chunksOf6bit[populatedChunks++] = (byte)(32 + (length & 31)); // 5 bits plus 6th indicating length has more bits!
            }
        }

        @Override
        public int availableBits() {
            return 6 * populatedChunks;
        }

        @Override
        public int removeBits() {
            return chunksOf6bit[--populatedChunks];
        }
    }
}
