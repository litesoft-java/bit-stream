package org.litesoft.bitstream;

/**
 * From a Twos Compliment Long, support extracting the <code>SignBit</code> and
 * the lower to higher significant bits (repeatedly) of the value of an internal
 * Ones-Compliment Long representation (separate signbit from <code>value</code> bits - 0-Long.MAX_VALUE).
 */
public class OnesComplimentLongBitsConsumer {
    private final boolean wasNegative;
    private long value;

    /**
     * Constructor.
     *
     * @param value Twos Compliment
     */
    public OnesComplimentLongBitsConsumer( long value ) {
        wasNegative = (value < 0);
        if ( wasNegative ) {
            value = (value == Long.MIN_VALUE) ? 0 : -value; // max negative value -> 0 OR simply switch to positive!
        }
        this.value = value;
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
     * Remove 1 bit from the Stream.
     *
     * @return int of either 0 ot 1
     */
    public int remove1bit() {
        return extractLowerBits( 1 );
    }

    /**
     * Remove 2 bits from the Stream.
     *
     * @return int with range 0 thru 3
     */
    public int remove2bits() {
        return extractLowerBits( 2 );
    }

    /**
     * Remove 4 bits from the Stream.
     *
     * @return int with range 0 thru 15
     */
    public int remove4bits() {
        return extractLowerBits( 4 );
    }

    /**
     * Remove 6 bits from the Stream.
     *
     * @return int with range 0 thru 63
     */
    public int remove6bits() {
        return extractLowerBits( 6 );
    }

    /**
     * Remove 8 bits from the Stream.
     *
     * @return int with range 0 thru 255
     */
    public int remove8bits() {
        return extractLowerBits( 8 );
    }

    private int extractLowerBits( int count ) {
        long bits = value & BitStream.MASKS[count];
        value = value >> count; // drop low bits
        return (int)bits;
    }
}
