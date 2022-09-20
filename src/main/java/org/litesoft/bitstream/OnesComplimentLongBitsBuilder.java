package org.litesoft.bitstream;

/**
 * Support building TwosCompliment Long from a Ones-Compliment representation
 * (separate signbit from <code>value</code> bits - 0-Long.MAX_VALUE) by <code>add</code>ing (appending) bits.
 */
public class OnesComplimentLongBitsBuilder implements BitStreamBuilder<OnesComplimentLongBitsBuilder>,
                                                      BitConstants {
    private boolean negative;
    private int bitCount;
    private long value;

    /**
     * Set the <code>negative</code> flag (SignBit) to true.
     *
     * @return this for chaining
     */
    public OnesComplimentLongBitsBuilder negative() {
        return withNegative( true );
    }

    /**
     * Set the <code>negative</code> flag (SignBit) to false on <code>zeroMeansPositiveElseNegative</code> equal to zero, otherwise true.
     *
     * @param zeroMeansPositiveElseNegative 0 for SignBit positive, !0 for SignBit negative;
     * @return this for chaining
     */
    public OnesComplimentLongBitsBuilder withSignBit( int zeroMeansPositiveElseNegative ) {
        return withNegative( 0 != zeroMeansPositiveElseNegative );
    }

    /**
     * Set the <code>negative</code> flag (SignBit) to true or false based on parameter <code>negative</code>.
     *
     * @param negative new value for <code>negative</code> flag (SignBit)
     * @return this for chaining
     */
    public OnesComplimentLongBitsBuilder withNegative( boolean negative ) {
        this.negative = negative;
        return this;
    }

    /**
     * @return <code>negative</code> flag (SignBit)
     */
    public boolean isNegative() {
        return negative;
    }

    /**
     * @return current Twos Compliment value.
     */
    public long getValue() {
        if ( !negative ) {
            return value;
        }
        return (value == 0) ? Long.MIN_VALUE : -value; // 0 -> max negative value OR simply negate
    }

    /**
     * Add <code>N</code> bits.
     *
     * @param n    count of the bits to add (from 1 - 8, inclusive).
     * @param bits integer of the bits where only the least significant <code>N</code> are used (but in the most to the least significant order).
     * @return this for chaining
     * @throws IllegalStateException if <code>n</code> not between 1 - 8, inclusive OR there is insufficient room for the bits
     */
    @Override
    public OnesComplimentLongBitsBuilder addNbits( int n, int bits ) {
        int newBitCount = bitCount + n;
        if ( (1 <= n) && (n <= 8) && (newBitCount > 63) ) {
            int trialN = n - (newBitCount - 63);
            int trialBits = (bits & MASKS[trialN]);
            if ( trialBits == bits ) {
                if ( trialN == 0 ) {
                    return this;
                }
                n = trialN;
            }
        }
        bits = Nbits.addNbits( bitCount, 63, this, n, bits );
        long longBits = ((long)bits) << bitCount;
        value += longBits;
        bitCount += n;
        return this;
    }
}
