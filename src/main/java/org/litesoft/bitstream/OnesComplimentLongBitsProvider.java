package org.litesoft.bitstream;

/**
 * From a Twos Compliment Long, support extracting the <code>SignBit</code> and
 * the lower to higher significant bits (repeatedly) of the value of an internal
 * Ones-Compliment Long representation (separate signbit from <code>value</code> bits - 0-Long.MAX_VALUE).
 */
public class OnesComplimentLongBitsProvider implements BitStreamProvider {
    private final boolean wasNegative;
    private long value;
    private int remainingSignificantBits;

    /**
     * Constructor.
     *
     * @param value Twos Compliment
     */
    public OnesComplimentLongBitsProvider( long value ) {
        wasNegative = (value < 0);
        if ( wasNegative ) {
            value = (value == Long.MIN_VALUE) ? 0 : -value; // max negative value -> 0 OR simply switch to positive!
        }
        this.value = value;
        long mask = 1;
        for ( int i = 1; i < 64; i++ ) {
            if ( 0 != (value & mask) ) {
                remainingSignificantBits = i;
            }
            mask += mask; // next bit
        }
    }

    /**
     * Returns the remaining significant bits of the positive value (0 to 63)!
     */
    @Override
    public int availableBits() {
        return remainingSignificantBits;
    }

    /**
     * @return if the original Twos Compliment Long was <code>negative</code>
     */
    public boolean wasNegative() {
        return wasNegative;
    }

    /**
     * @return <code>1</code>, if the original Twos Compliment Long was <code>negative</code>, otherwise <code>0</code>
     */
    public int getSignBit() {
        return wasNegative ? 1 : 0;
    }

    /**
     * @return if the Ones-Compliment Long <code>value</code> bits are all <code>0</code>
     */
    public boolean isValueNonZero() {
        return 0 != value;
    }

    /**
     * Remove <code>N</code> 'lower' bits from the Long.
     *
     * @param n count of the bits to remove (from 1 - 8, inclusive).
     * @return int with range 0 thru 2^Nth - 1
     * @throws IllegalStateException if <code>n</code> not between 1 - 8, inclusive
     */
    @Override
    public int removeNbits( int n ) {
        int mask = Nbits.removeNbits( 63, this, n );
        long bits = value & mask;
        value = value >> n; // drop low bits
        remainingSignificantBits = Math.max( 0, remainingSignificantBits - n );
        return (int)bits;
    }
}
