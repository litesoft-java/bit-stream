package org.litesoft.bitstream;

public abstract class AbstractBitBufferStreamSequentialSink<T> extends AbstractBitStreamSequentialSink<T> {
    protected final BitBuffer buffer = new BitBuffer();
    // public for testing
    public final SinkBitStream<T> sinkBitStream;

    protected AbstractBitBufferStreamSequentialSink( SinkBitStream<T> sinkBitStream ) {
        this.sinkBitStream = sinkBitStream;
    }

    @Override
    public final int bitsCurrentlyNeeded() {
        return sinkBitStream.bitsCurrentlyNeeded(buffer);
    }

    @Override
    public final BitStreamSequentialSink<T> addNbits( int n, int bits ) {
        buffer.addNbits( n, Nbits.addNbits( 0, bitsCurrentlyNeeded(), this, n, bits ) );
        while ( sinkBitStream.getBitSize() <= buffer.availableBits() ) {
            sinkBitStream.addBits( buffer.removeNbits(sinkBitStream.getBitSize()) );
        }
        return this;
    }

    protected static abstract class SinkBitStream<T> {
        private final int bitsSize;

        /**
         * @param bitsSize 1-8 based on underlying storage.
         */
        public SinkBitStream( int bitsSize ) {
            this.bitsSize = bitsSize;
        }

        public abstract T getValue();

        public final int getBitSize() {
            return bitsSize;
        }

        // public final boolean hasAvailableBits() {

        public abstract int bitsCurrentlyNeeded(BitBuffer buffer);

        public abstract void addBits(int bits); // # of bits is from the bitsSize above
    }

}
