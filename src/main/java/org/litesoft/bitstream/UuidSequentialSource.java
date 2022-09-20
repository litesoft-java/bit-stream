package org.litesoft.bitstream;

import java.util.UUID;

import org.litesoft.annotations.NotNull;
import org.litesoft.utils.Hex;

import static org.litesoft.utils.UuidNonDashCharOffsets.HEX_DIGIT_OFFSETS;

/**
 * Convert a <code>UUID</code> into a <code>BitStreamSequentialSource</code> of 8 bit bytes.
 * <p>
 * Note: a <code>null UUID</code> is NOT supported!
 */
public class UuidSequentialSource extends AbstractBitBufferStreamSequentialSource {
    public UuidSequentialSource( @NotNull UUID uuid ) {
        super( new SBS( NotNull.AssertArgument.namedValue( "uuid", uuid ).toString() ) );
    }

    static class SBS extends SourceBitStream {
        private final String uuid;
        private int nibleOffset;

        public SBS( String uuid ) {
            super( 4 );
            this.uuid = uuid;
        }

        @Override
        public int availableBits() {
            return 4 * (HEX_DIGIT_OFFSETS.length - nibleOffset);
        }

        @Override
        public int removeBits() {
            return Hex.from( uuid.charAt( HEX_DIGIT_OFFSETS[nibleOffset++] ) );
        }
    }
}
