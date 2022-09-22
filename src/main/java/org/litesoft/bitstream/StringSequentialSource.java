package org.litesoft.bitstream;

import java.nio.charset.StandardCharsets;

/**
 * Convert a <code>String</code> into a <code>BitStreamSequentialSource</code> of utf8 bytes.
 * <p>
 * Note: a <code>null String</code> will be treated as an empty <code>String</code>!
 */
public class StringSequentialSource extends AbstractBitBufferStreamSequentialSource {
    public static byte[] utf8Bytes( String str ) {
        return (str == null) ? new byte[0] : str.getBytes( StandardCharsets.UTF_8 );
    }

    public StringSequentialSource( String str ) {
        this( utf8Bytes( str ) );
    }

    public StringSequentialSource( byte[] utf8Bytes ) {
        super( new SBS( utf8Bytes ) );
    }

    static class SBS extends SourceBitStream {
        private final byte[] utf8Bytes;
        private int byteOffset;

        public SBS( byte[] utf8Bytes ) {
            super( 8 );
            this.utf8Bytes = utf8Bytes;
        }

        @Override
        public int availableBits() {
            return 8 * (utf8Bytes.length - byteOffset);
        }

        @Override
        public int removeBits() {
            return utf8Bytes[byteOffset++];
        }
    }
}
