package org.litesoft.bitstream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringSequentialSourceSinkTest {

    @Test
    void testNullAndEmpty() {
        assertEquals( 0, new StringSequentialSource( (String)null ).availableBits() );
        assertEquals( 0, new StringSequentialSource( "" ).availableBits() );
        assertEquals( "", StringSequentialSink.ofBytes( 0 ).getValue() );
    }

    @Test
    void testHappyCases() {
        String tQBF = "Quick Brown Fox Jumped Over the Lazy Moon, The|~!@#$%^&*()_+╠`1234567890-=\\[]{}:;"; // non-ascii character!
        checkRecursiveSplit( tQBF );
    }

    private void checkRecursiveSplit( String s ) {
        checkRT( s );
        int splitAt = s.length() / 2;
        if ( splitAt != 0 ) {
            checkRecursiveSplit( s.substring( 0, splitAt ) );
            checkRecursiveSplit( s.substring( splitAt ) );
        }
    }

    private void checkRT( String toRT ) {
        StringSequentialSource src = new StringSequentialSource( toRT );
        int nBits = src.availableBits();
        StringSequentialSink snk = new StringSequentialSink( nBits );
        for ( int n = snk.bitsCurrentlyNeeded(); n > 0 ; n = snk.bitsCurrentlyNeeded() ) {
            snk.addNbits(n,  src.removeNbits(n) );
            assertEquals( nBits -= n, src.availableBits() );
        }
        assertEquals( toRT, snk.getValue() );
    }
}