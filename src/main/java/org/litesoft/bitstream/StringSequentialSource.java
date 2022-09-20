package org.litesoft.bitstream;

import java.nio.charset.StandardCharsets;

/**
 * Convert a <code>String</code> into a <code>BitStreamSequentialSource</code> of utf8 bytes.
 * <p>
 * Note: a <code>null String</code> will be treated as an empty <code>String</code>!
 */
public class StringSequentialSource extends AbstractBitBufferStreamSequentialSource {

    public StringSequentialSource( String str ) {
        super( new SBS( str ) );
    }

    static class SBS extends SourceBitStream {
        private final byte[] utf8Bytes;
        private int byteOffset;

        public SBS( String str ) {
            super( 8 );
            utf8Bytes = (str == null) ? new byte[0] : str.getBytes( StandardCharsets.UTF_8 );
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
