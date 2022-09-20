package org.litesoft.bitstream;

public abstract class AbstractBitBufferStreamSequentialSource extends AbstractBitStreamSequentialSource {
    protected final BitBuffer buffer = new BitBuffer();
    // public for testing
    public final SourceBitStream sourceBitStream;

    protected AbstractBitBufferStreamSequentialSource( SourceBitStream sourceBitStream ) {
        this.sourceBitStream = sourceBitStream;
    }

    @Override
    public final int availableBits() {
        return buffer.availableBits() + sourceBitStream.availableBits();
    }

    @Override
    public final int removeNbits( int n ) {
        if ( n <= 8 ) {
            while ( (buffer.availableBits() < n) && sourceBitStream.hasAvailableBits() ) {
                buffer.addNbits( sourceBitStream.getBitSize(), sourceBitStream.removeBits() );
            }
        }
        Nbits.removeNbits( buffer.availableBits(), this, n );
        return buffer.removeNbits( n );
    }
}
