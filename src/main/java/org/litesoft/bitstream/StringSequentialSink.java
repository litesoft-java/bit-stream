package org.litesoft.bitstream;

import java.nio.charset.StandardCharsets;

import org.litesoft.annotations.NotNull;

/**
 * Convert a Stream of utf8 <code>bytes</code>, presumably from a <code>BitStreamSequentialSource</code>, into a <code>String</code>.
 * <p>
 * Note: the <code>toString</code> (which should only be called once all the bytes are added) will never return a null, but may return a <code>null String</code>!
 */
public class StringSequentialSink extends AbstractBitBufferStreamSequentialSink<String> {
    public StringSequentialSink( int expectedBits ) {
        super( new SBS( expectedBits ) );
    }

    public static StringSequentialSink ofBytes( int utf8BytesLength ) {
        return new StringSequentialSink( utf8BytesLength * 8 );
    }

    @Override @NotNull
    public String getValue() {
        return sinkBitStream.getValue();
    }

    static class SBS extends SinkBitStream<String> implements BitConstants {
        private final byte[] utf8Bytes;
        private int byteOffset;

        SBS( int expectedBits ) {
            super( 8 );
            if ( (expectedBits < 0) || (0 != (expectedBits & 7)) ) {
                throw new IllegalStateException( "expectedBits not negative or not divisible by 8, was: " + expectedBits );
            }
            int byteCount = expectedBits >> 3; // /8
            utf8Bytes = new byte[byteCount];
        }

        public String getValue() {
            if (byteOffset < utf8Bytes.length) {
                throw new IllegalStateException( VALUE_NOT_POPULATED );
            }
            return new String( utf8Bytes, StandardCharsets.UTF_8 );
        }

        @Override
        public int bitsCurrentlyNeeded( BitBuffer buffer ) {
            return (utf8Bytes.length <= byteOffset) ? 0 : (8 - buffer.availableBits());
        }

        @Override
        public void addBits( int bits ) { // 8 bits
            utf8Bytes[byteOffset++] = (byte)bits;
        }
    }
}
