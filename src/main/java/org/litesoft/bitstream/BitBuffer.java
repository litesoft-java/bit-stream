package org.litesoft.bitstream;

/**
 * Supports variable bit count buffering: removing from the 'head' and adding to the 'tail'.
 */
public class BitBuffer implements BitStreamBuilderProvider<BitBuffer> {
    private int bits = 0;
    private int bitCount = 0; // max 31 (keep bits positive)

    /**
     * Returns the current number of bits in the Buffer (initially 0)
     */
    @Override
    public int availableBits() {
        return bitCount;
    }

    /**
     * Add <code>N</code> bits to the Buffer.
     *
     * @param n    count of the bits to add (from 1 - 8, inclusive).
     * @param bits integer of the bits where only the least significant <code>N</code> are used (but in the most to the least significant order).
     * @return this for chaining
     * @throws IllegalStateException if <code>n</code> not between 1 - 8, inclusive OR there is insufficient room for the bits
     */
    @Override
    public BitBuffer addNbits( int n, int bits ) {
        bits = Nbits.addNbits( bitCount, 31, this, n, bits );
        bitCount += n;
        this.bits = (this.bits << n) + bits;
        return this;
    }

    /**
     * Remove <code>N</code> bits from the Buffer.
     *
     * @param n count of the bits to remove (from 1 - 8, inclusive).
     * @return int with range 0 thru 2^Nth - 1
     * @throws IllegalStateException if <code>n</code> not between 1 - 8, inclusive OR there are insufficient bits available
     */
    @Override
    public int removeNbits( int n ) {
        int mask = Nbits.removeNbits( bitCount, this, n );
        int shift = bitCount - n;
        if ( shift != 0 ) {
            mask = mask << shift;
        }
        int valueToReturn = bits & mask;
        bits -= valueToReturn; // remove selected (masked) bits
        bitCount -= n;
        if ( shift != 0 ) {
            valueToReturn = valueToReturn >> shift; // shift to just the selected (masked) bits
        }
        return valueToReturn;
    }

    @Override
    public String toString() {
        return Integer.toHexString( bits );
    }
}
