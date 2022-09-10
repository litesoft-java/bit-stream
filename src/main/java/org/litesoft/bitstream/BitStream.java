package org.litesoft.bitstream;

import org.litesoft.annotations.PackageFriendlyForTesting;

/**
 * Supports bit stream like interface: removing from the 'head' and adding to the 'tail'.
 */
@SuppressWarnings("UnusedReturnValue")
public class BitStream {
    @PackageFriendlyForTesting
    static final String ERROR_PREFIX = "BitStream error ";
    static final String ERROR_OVER_FLOW_SUFFIX = " would exceed maximum allowed Stream size of 31 bits";

    private int bits = 0;
    private int bitCount = 0; // max 31 (keep bits positive)

    /**
     * @return the current number of bits in the Stream (initially 0)
     */
    public int bitCount() {
        return bitCount;
    }

    /**
     * Add 1 bit to the Stream.
     *
     * @param bits integer of the bits where only the least significant 1 is used.
     * @return post-added bit count
     */
    public int add1bit( int bits ) {
        return extend( 1, bits );
    }

    /**
     * Remove 1 bit from the Stream.
     *
     * @return int of either 0 ot 1
     */
    public int remove1bit() {
        return extract( 1 );
    }

    /**
     * Add 2 bits to the Stream.
     *
     * @param bits integer of the bits where only the least significant 2 are used (but in the most to the least significant order).
     * @return post-added bit count
     */
    public int add2bits( int bits ) {
        return extend( 2, bits );
    }

    /**
     * Remove 2 bits from the Stream.
     *
     * @return int with range 0 thru 3
     */
    public int remove2bits() {
        return extract( 2 );
    }

    /**
     * Add 4 bits to the Stream.
     *
     * @param bits integer of the bits where only the least significant 4 are used (but in the most to the least significant order).
     * @return post-added bit count
     */
    public int add4bits( int bits ) {
        return extend( 4, bits );
    }

    /**
     * Remove 4 bits from the Stream.
     *
     * @return int with range 0 thru 15
     */
    public int remove4bits() {
        return extract( 4 );
    }

    /**
     * Add 6 bits to the Stream.
     *
     * @param bits integer of the bits where only the least significant 6 are used (but in the most to the least significant order).
     * @return post-added bit count
     */
    public int add6bits( int bits ) {
        return extend( 6, bits );
    }

    /**
     * Remove 6 bits from the Stream.
     *
     * @return int with range 0 thru 63
     */
    public int remove6bits() {
        return extract( 6 );
    }

    /**
     * Add 8 bits to the Stream.
     *
     * @param bits integer of the bits where only the least significant 8 are used (but in the most to the least significant order).
     * @return post-added bit count
     */
    public int add8bits( int bits ) {
        return extend( 8, bits );
    }

    /**
     * Remove 8 bits from the Stream.
     *
     * @return int with range 0 thru 255
     */
    public int remove8bits() {
        return extract( 8 );
    }

    private int extract( int count ) {
        if ( bitCount < count ) {
            throw error( "remove", count, underflowSuffix() );
        }
        int shift = bitCount - count;
        int mask = MASKS[count];
        if ( shift != 0 ) {
            mask = mask << shift;
        }
        int valueToReturn = bits & mask;
        bits -= valueToReturn; // remove selected (masked) bits
        bitCount -= count;
        if ( shift != 0 ) {
            valueToReturn = valueToReturn >> shift; // shift to just the selected (masked) bits
        }
        return valueToReturn;
    }

    private int extend( int count, int newBits ) {
        if ( bitCount + count > 31 ) {
            throw error( "add", count, ERROR_OVER_FLOW_SUFFIX );
        }
        bitCount += count;
        bits = (bits << count) + (MASKS[count] & newBits);
        return bitCount();
    }

    private IllegalStateException error( String direction, int count, String message ) {
        StringBuilder sb = new StringBuilder();
        sb.append( ERROR_PREFIX ).append( direction ).append( count ).append( "bit" );
        if ( count > 1 ) {
            sb.append( 's' );
        }
        sb.append( message );
        throw new IllegalStateException( sb.toString() );
    }

    @PackageFriendlyForTesting
    String underflowSuffix() {
        return ", but only " + bitCount() + " are available";
    }

    @Override
    public String toString() {
        return Integer.toHexString( bits );
    }

    public static final int[] MASKS = {0, 1, 3, 7, 15, 31, 63, 127, 255};
    // . . . . . . . . . . . . . . . . 0, 1, 2, 3,  4,  5,  6,  7,   8 
}
