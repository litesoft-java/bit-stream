package org.litesoft.bitstream;

public interface BitStreamProvider {
    /**
     * Returns <code>true</code> if this BitStreamProvider is also a BitStreamBuilder!
     */
    default boolean canAddBits() {
        return false;
    }

    /**
     * The current bits that can be removed.
     *
     * Note: For BitStreamProviders that are not also BitStreamBuilders, the initial available bits must indicate the minimum bits extracted!
     *
     * @return never negative, but can be zero!
     */
    int availableBits();

    /**
     * Remove 1 bit from the Stream.
     *
     * @return int of either 0 ot 1
     */
    default int remove1bit() {
        return removeNbits( 1 );
    }

    /**
     * Remove 2 bits from the Stream.
     *
     * @return int with range 0 thru 3
     */
    default int remove2bits() {
        return removeNbits( 2 );
    }

    /**
     * Remove 3 bits from the Stream.
     *
     * @return int with range 0 thru 7
     */
    default int remove3bits() {
        return removeNbits( 3 );
    }

    /**
     * Remove 4 bits from the Stream.
     *
     * @return int with range 0 thru 15
     */
    default int remove4bits() {
        return removeNbits( 4 );
    }

    /**
     * Remove 5 bits from the Stream.
     *
     * @return int with range 0 thru 31
     */
    default int remove5bits() {
        return removeNbits( 5 );
    }

    /**
     * Remove 6 bits from the Stream.
     *
     * @return int with range 0 thru 63
     */
    default int remove6bits() {
        return removeNbits( 6 );
    }

    /**
     * Remove 7 bits from the Stream.
     *
     * @return int with range 0 thru 127
     */
    default int remove7bits() {
        return removeNbits( 7 );
    }

    /**
     * Remove 8 bits from the Stream.
     *
     * @return int with range 0 thru 255
     */
    default int remove8bits() {
        return removeNbits( 8 );
    }

    /**
     * Remove <code>N</code> bits from the Stream.
     *
     * @param n count of the bits to remove (from 1 - 8, inclusive).
     * @return int with range 0 thru 2^Nth - 1
     * @throws IllegalStateException if <code>n</code> not between 1 - 8, inclusive OR there are insufficient bits available
     */
    int removeNbits( int n );
}
