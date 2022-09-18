package org.litesoft.bitstream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BitBufferTest implements BitConstants {

    BitBuffer buffer = new BitBuffer();

    @Test
    void testUnderflow() {
        try {
            int bit = buffer.remove1bit();
            fail( "expected exception, but got result of: " + bit );
        }
        catch ( IllegalStateException e ) {
            assertEquals( "BitBuffer" + POST_CLASS_NAME_MID_SECTION + "remove1bit" + ERROR_UNDER_FLOW_MID_SECTION + 0 + ERROR_UNDER_FLOW_SUFFIX, e.getMessage() );
        }
    }

    @Test
    void testOverflow() {
        buffer.add8bits( 8 );
        buffer.add8bits( 16 );
        buffer.add8bits( 24 );
        try {
            buffer.add8bits( 32 );
            fail( "expected exception with the attempted add of 32nd bit" );
        }
        catch ( IllegalStateException e ) {
            assertEquals( "BitBuffer" + POST_CLASS_NAME_MID_SECTION + "add8bits" + ERROR_OVER_FLOW_MID_SECTION + 31 + ERROR_OVER_FLOW_SUFFIX, e.getMessage() );
        }
    }

    @Test
    void testHappyCases() {
        assertTrue( buffer.canAddBits() );

        assertEquals( 0, buffer.availableBits() );

        buffer.add1bit( 0b1 ); //. . . . . . . . . . . . .  1
        buffer.add2bits( 0b10 ); //. . . . . . . . . . . .  3
        buffer.add3bits( 0b101 ); // . . . . . . . . . . .  6
        buffer.add4bits( 0b0111 ); //. . . . . . . . . . . 10
        buffer.add5bits( 0b11111 ); // . . . . . . . . . . 15
        buffer.add6bits( 0b000011 ); //. . . . . . . . . . 21
        buffer.add7bits( 0b1111110 ); // . . . . . . . . . 28

        assertEquals( 28, buffer.availableBits() );

        // 1 10 101 0111 11111 000011 1111110

        verifyBits( 0b11010, buffer.removeNbits( 5 ) ); //. . 23

        assertEquals( 23, buffer.availableBits() );

        // 10111111110000111111110

        buffer.add8bits( 0b10001111 ); //. . . . . . . . . 31 (full)

        // 1011111111000011111111010001111

        assertEquals( 31, buffer.availableBits() );

        // 1 01 111 1111 00001 111111 1010001 111

        verifyBits( 0b1, buffer.remove1bit() ); // . . . . 30
        verifyBits( 0b01, buffer.remove2bits() ); // . . . 28
        verifyBits( 0b111, buffer.remove3bits() ); //. . . 25
        verifyBits( 0b1111, buffer.remove4bits() ); // . . 21
        verifyBits( 0b00001, buffer.remove5bits() ); //. . 16
        verifyBits( 0b111111, buffer.remove6bits() ); // . 10
        verifyBits( 0b1010001, buffer.remove7bits() ); // . 3

        // 111

        assertEquals( 3, buffer.availableBits() );

        buffer.addNbits( 5, 0b11010 );

        // 11111010

        assertEquals( 8, buffer.availableBits() );

        verifyBits( 0b11111010, buffer.remove8bits() ); //. 0

        assertEquals( 0, buffer.availableBits() );
    }

    void verifyBits( int expected, int actual ) {
        assertEquals( expected, actual );
    }
}