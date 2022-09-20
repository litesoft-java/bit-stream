package org.litesoft.bitstream;

import org.litesoft.annotations.PackageFriendlyForTesting;

public class BitStreamSequentialSourceComposite extends AbstractBitStreamSequentialSource {
    @PackageFriendlyForTesting final BitStreamSequentialSource s1, s2;

    public BitStreamSequentialSourceComposite( BitStreamSequentialSource s1, BitStreamSequentialSource s2 ) {
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public int availableBits() {
        return s1.availableBits() + s2.availableBits();
    }

    @Override
    public int removeNbits( int n ) {
        int a1 = s1.availableBits();
        if ( n <= a1 ) { // <1 will error
            return s1.removeNbits( n );
        }
        if ( (a1 == 0) || (n > 8) ) { // > 8 will error
            return s2.removeNbits( n );
        }
        int from_s2 = n - a1;
        int bits1 = s1.removeNbits( a1 ); // empty s1
        int bits2 = s2.removeNbits( from_s2 );
        return (bits1 << from_s2) + bits2;
    }
}
