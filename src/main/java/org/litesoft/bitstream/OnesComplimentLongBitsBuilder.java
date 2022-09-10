package org.litesoft.bitstream;

/**
 * Support building TwosCompliment Long from a Ones-Compliment representation
 * (separate signbit from <code>value</code> bits - 0-Long.MAX_VALUE) by <code>add</code>ing (appending) bits.
 */
public class OnesComplimentLongBitsBuilder {
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
     * Add 1 bit.
     *
     * @param bits integer of the bits where only the least significant 1 is used.
     * @return this for chaining
     */
    public OnesComplimentLongBitsBuilder add1bit( int bits ) {
        return append( 1, bits );
    }

    /**
     * Add 2 bits.
     *
     * @param bits integer of the bits where only the least significant 2 are used (but in the most to the least significant order).
     * @return this for chaining
     */
    public OnesComplimentLongBitsBuilder add2bits( int bits ) {
        return append( 2, bits );
    }

    /**
     * Add 4 bits.
     *
     * @param bits integer of the bits where only the least significant 4 are used (but in the most to the least significant order).
     * @return this for chaining
     */
    public OnesComplimentLongBitsBuilder add4bits( int bits ) {
        return append( 4, bits );
    }

    /**
     * Add 6 bits.
     *
     * @param bits integer of the bits where only the least significant 6 are used (but in the most to the least significant order).
     * @return this for chaining
     */
    public OnesComplimentLongBitsBuilder add6bits( int bits ) {
        return append( 6, bits );
    }

    /**
     * Add 8 bits.
     *
     * @param bits integer of the bits where only the least significant 8 are used (but in the most to the least significant order).
     * @return this for chaining
     */
    public OnesComplimentLongBitsBuilder add8bits( int bits ) {
        return append( 8, bits );
    }

    private OnesComplimentLongBitsBuilder append( int count, int bits ) {
        long longBits = ((long)(bits & BitStream.MASKS[count])) << bitCount;
        value += longBits;
        bitCount += count;
        return this;
    }
}
