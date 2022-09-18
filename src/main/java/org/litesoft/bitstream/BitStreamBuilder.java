package org.litesoft.bitstream;

@SuppressWarnings("UnusedReturnValue")
public interface BitStreamBuilder<T> {
    /**
     * Add 1 bit to the Buffer.
     *
     * @param bits integer of the bits where only the least significant 1 is used.
     * @return this for chaining
     * @throws IllegalStateException if there is insufficient room for the bits
     */
    default T add1bit( int bits ) {
        return addNbits( 1, bits );
    }

    /**
     * Add 2 bits to the Buffer.
     *
     * @param bits integer of the bits where only the least significant 2 are used (but in the most to the least significant order).
     * @return this for chaining
     * @throws IllegalStateException if there is insufficient room for the bits
     */
    default T add2bits( int bits ) {
        return addNbits( 2, bits );
    }

    /**
     * Add 3 bits to the Buffer.
     *
     * @param bits integer of the bits where only the least significant 3 are used (but in the most to the least significant order).
     * @return this for chaining
     * @throws IllegalStateException if there is insufficient room for the bits
     */
    default T add3bits( int bits ) {
        return addNbits( 3, bits );
    }

    /**
     * Add 4 bits to the Buffer.
     *
     * @param bits integer of the bits where only the least significant 4 are used (but in the most to the least significant order).
     * @return this for chaining
     * @throws IllegalStateException if there is insufficient room for the bits
     */
    default T add4bits( int bits ) {
        return addNbits( 4, bits );
    }

    /**
     * Add 5 bits to the Buffer.
     *
     * @param bits integer of the bits where only the least significant 5 are used (but in the most to the least significant order).
     * @return this for chaining
     * @throws IllegalStateException if there is insufficient room for the bits
     */
    default T add5bits( int bits ) {
        return addNbits( 5, bits );
    }

    /**
     * Add 6 bits to the Buffer.
     *
     * @param bits integer of the bits where only the least significant 6 are used (but in the most to the least significant order).
     * @return this for chaining
     * @throws IllegalStateException if there is insufficient room for the bits
     */
    default T add6bits( int bits ) {
        return addNbits( 6, bits );
    }

    /**
     * Add 7 bits to the Buffer.
     *
     * @param bits integer of the bits where only the least significant 7 are used (but in the most to the least significant order).
     * @return this for chaining
     * @throws IllegalStateException if there is insufficient room for the bits
     */
    default T add7bits( int bits ) {
        return addNbits( 7, bits );
    }

    /**
     * Add 8 bits to the Buffer.
     *
     * @param bits integer of the bits where only the least significant 8 are used (but in the most to the least significant order).
     * @return this for chaining
     * @throws IllegalStateException if there is insufficient room for the bits
     */
    default T add8bits( int bits ) {
        return addNbits( 8, bits );
    }

    /**
     * Add <code>N</code> bits to the Stream.
     *
     * @param n    count of the bits to add (from 1 - 8, inclusive).
     * @param bits integer of the bits where only the least significant <code>N</code> are used (but in the most to the least significant order).
     * @return this for chaining
     * @throws IllegalStateException if <code>n</code> not between 1 - 8, inclusive OR there is insufficient room for the bits
     */
    T addNbits( int n, int bits );
}
