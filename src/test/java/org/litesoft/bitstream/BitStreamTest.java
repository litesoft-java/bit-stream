package org.litesoft.bitstream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class BitStreamTest {

    BitStream stream = new BitStream();

    @Test
    void testUnderflow() {
        try {
            int bit = stream.remove1bit();
            fail( "expected exception, but got result of: " + bit );
        }
        catch ( IllegalStateException e ) {
            assertEquals( BitStream.ERROR_PREFIX + "remove1bit" + stream.underflowSuffix(), e.getMessage() );
        }
    }

    @Test
    void testOverflow() {
        stream.add8bits( 8 );
        stream.add8bits( 16 );
        stream.add8bits( 24 );
        try {
            stream.add8bits( 32 );
            fail( "expected exception with the attempted of 32 bits" );
        }
        catch ( IllegalStateException e ) {
            assertEquals( BitStream.ERROR_PREFIX + "add8bits" + BitStream.ERROR_OVER_FLOW_SUFFIX, e.getMessage() );
        }
    }

    @Test
    void testHappyCases() {
        stream.add1bit( 0b1 ); // . . . . . 1
        stream.add2bits( 0b10 ); // . . . . 3
        stream.add2bits( 0b10 ); // . . . . 5
        stream.add4bits( 0b1011 ); // . . . 9
        stream.add6bits( 0b111111 ); //. . 15
        stream.add8bits( 0b00001111 ); //. 23
        stream.add8bits( 0b11110001 ); //. 31 (full)
        // 1101010111111110000111111110001
        assertEquals( 0b11, stream.remove2bits() ); // . . . 30
        assertEquals( 0b0, stream.remove1bit() ); // . . . . 28
        assertEquals( 0b101011, stream.remove6bits() ); // . 22
        assertEquals( 0b111111, stream.remove6bits() ); // . 16
        assertEquals( 0b0000, stream.remove4bits() ); // . . 12
        assertEquals( 0b11111111, stream.remove8bits() ); //. 4
        assertEquals( 0b00, stream.remove2bits() ); //. . . . 2
        assertEquals( 0b01, stream.remove2bits() ); //. . . . 0
    }
}