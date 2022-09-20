package org.litesoft.bitstream;

public abstract class AbstractBitStreamSequentialSource implements BitStreamSequentialSource {
    public final String toString() {
        return toStringName() + ": " + availableBits() + " available bits";
    }

    protected String toStringName() {
        return getClass().getSimpleName();
    }

    protected static abstract class SourceBitStream {
        private final int bitsSize;

        /**
         * @param bitsSize 1-8 based on underlying storage.
         */
        public SourceBitStream( int bitsSize ) {
            this.bitsSize = bitsSize;
        }

        public final int getBitSize() {
            return bitsSize;
        }

        public final boolean hasAvailableBits() {
            return availableBits() > 0;
        }

        public abstract int availableBits();

        public abstract int removeBits(); // # of bits is from the bitsSize above
    }
}
