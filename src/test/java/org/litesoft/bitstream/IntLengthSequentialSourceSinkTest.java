package org.litesoft.bitstream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntLengthSequentialSourceSinkTest {

    @Test
    void testErrors() {
        try {
            var wt = new IntLengthSequentialSource( -1 );
            fail( "expected exception but got: " + wt );
        }
        catch ( IllegalStateException expected ) {
            assertTrue( expected.getMessage().startsWith( IntLengthSequentialSource.LENGTH_NEGATIVE_PREFIX ) );
        }

        var snk = new IntLengthSequentialSink();
        try {
            var value = snk.getValue();
            fail( "expected exception but got: " + value );
        }
        catch ( IllegalStateException expected ) {
            assertEquals( IntLengthSequentialSink.VALUE_NOT_POPULATED, expected.getMessage() );
        }
        snk.add6bits( 32 + 3 ); // More bit AND bits: 32, 31
        snk.add6bits( 32 + 9 ); // More bit AND bits: 30-26
        snk.add6bits( 32 + 8 ); // More bit AND bits: 25-21
        snk.add6bits( 32 + 7 ); // More bit AND bits: 20-16
        snk.add6bits( 32 + 6 ); // More bit AND bits: 15-11
        snk.add6bits( 32 + 5 ); // More bit AND bits: 10-6

        try {
            var wt = snk.add6bits( 31 ); // NO More bit AND bits: 5-1
            fail( "expected exception but got: " + wt );
        }
        catch ( IllegalStateException expected ) {
            assertEquals( IntLengthSequentialSink.LENGTH_WENT_NEGATIVE, expected.getMessage() );
        }
    }

    @Test
    void testHappyCases() {
        checkRT( 1, 0 );
        int twoToTheNth = 1 << 5;
        checkRT( 1, twoToTheNth - 1 );
        checkRT( 2, twoToTheNth );
        twoToTheNth = twoToTheNth << 5;
        checkRT( 2, twoToTheNth - 1 );
        checkRT( 3, twoToTheNth );
        twoToTheNth = twoToTheNth << 5;
        checkRT( 3, twoToTheNth - 1 );
        checkRT( 4, twoToTheNth );
        twoToTheNth = twoToTheNth << 5;
        checkRT( 4, twoToTheNth - 1 );
        checkRT( 5, twoToTheNth );
        twoToTheNth = twoToTheNth << 5;
        checkRT( 5, twoToTheNth - 1 );
        checkRT( 6, twoToTheNth );
        twoToTheNth = twoToTheNth << 5;
        checkRT( 6, twoToTheNth - 1 );
        checkRT( 7, twoToTheNth );
        // just MAX_VALUE
        checkRT( 7, Integer.MAX_VALUE );
    }

    private void checkRT( int expected6bitChunks, int toRT ) {
        IntLengthSequentialSource src = new IntLengthSequentialSource( toRT );
        assertEquals( expected6bitChunks * 6, src.sourceBitStream.availableBits() );

        IntLengthSequentialSink snk = new IntLengthSequentialSink();
        for ( int n = snk.bitsCurrentlyNeeded(); n > 0; n = snk.bitsCurrentlyNeeded() ) {
            snk.addNbits( n, src.removeNbits( n ) );
        }
        assertEquals( toRT, snk.getValue() );
    }
}