package org.litesoft.bitstream;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class UuidSequentialSourceSinkTest {

    @Test
    void testNullFails() {
        try {
            var instance = new UuidSequentialSource( null );
            fail( "expected an Exception, but got an instance: " + instance );
        }
        catch ( IllegalArgumentException e ) {
            assertEquals( "'uuid' is expected to be Not Null, but was: null", e.getMessage() );
        }
    }

    @Test
    void testHappyCases() {
        checkRT( UUID.randomUUID() );
        checkRT( UUID.randomUUID() );
        checkRT( UUID.randomUUID() );
        checkRT( UUID.randomUUID() );
    }

    private void checkRT( UUID toRT ) {
        UuidSequentialSource src = new UuidSequentialSource( toRT );
        int nBits = src.availableBits();
        UuidSequentialSink snk = new UuidSequentialSink();
        for ( int n = snk.bitsCurrentlyNeeded(); n > 0; n = snk.bitsCurrentlyNeeded() ) {
            snk.addNbits( n, src.removeNbits( n ) );
            assertEquals( nBits -= n, src.availableBits() );
        }
        assertEquals( toRT, snk.getValue() );
    }
}