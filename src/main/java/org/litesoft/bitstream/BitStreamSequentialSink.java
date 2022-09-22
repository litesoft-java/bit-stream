package org.litesoft.bitstream;

public interface BitStreamSequentialSink<T> extends BitStreamBuilder<BitStreamSequentialSink<T>> {

    T getValue();

    /**
     * @return 0-8, with 0 indicating that no more bits are needed
     * <p>
     * Note: a non-0 number means more bits are needed and the number can be used to do a removeNbits()
     * Note-2: Since the number of bits ultimately needed can be greater than 8, providing the needed bits indicating does not mean that that is all the bits ultimately needed!
     * Note-3: Under no circumstances should more bits be added then indicated by this method!
     */
    int bitsCurrentlyNeeded();
}
