package org.litesoft.bitstream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OnesComplimentLongBitsBuilderConsumerTest {

    @Test
    void testMIN_VALUE() {
        OnesComplimentLongBitsConsumer consumer = new OnesComplimentLongBitsConsumer( Long.MIN_VALUE );
        assertFalse( consumer.isValueNonZero() );
        assertTrue( consumer.wasNegative() );
        assertEquals( 1, consumer.getSignBit() );

        OnesComplimentLongBitsBuilder builder = new OnesComplimentLongBitsBuilder().negative();
        assertTrue( builder.isNegative() );
        assertEquals( Long.MIN_VALUE, builder.getValue() );
    }

    @Test
    void testZero() {
        OnesComplimentLongBitsConsumer consumer = new OnesComplimentLongBitsConsumer( 0 );
        assertFalse( consumer.isValueNonZero() );
        assertFalse( consumer.wasNegative() );
        assertEquals( 0, consumer.getSignBit() );

        OnesComplimentLongBitsBuilder builder = new OnesComplimentLongBitsBuilder();
        assertFalse( builder.isNegative() );
        assertEquals( 0, builder.getValue() );
    }

    @Test
    void testHappyCases() {
        OnesComplimentLongBitsConsumer consumer = new OnesComplimentLongBitsConsumer( Long.MAX_VALUE );
        OnesComplimentLongBitsBuilder builder = new OnesComplimentLongBitsBuilder().withSignBit( consumer.getSignBit() );

        builder.add8bits( validate( 255, consumer.remove8bits() ) );
        builder.add8bits( validate( 255, consumer.remove8bits() ) );
        builder.add8bits( validate( 255, consumer.remove8bits() ) );
        builder.add8bits( validate( 255, consumer.remove8bits() ) );
        builder.add8bits( validate( 255, consumer.remove8bits() ) );
        builder.add8bits( validate( 255, consumer.remove8bits() ) );
        builder.add8bits( validate( 255, consumer.remove8bits() ) );
        builder.add8bits( validate( 127, consumer.remove8bits() ) );

        assertEquals( Long.MAX_VALUE, builder.getValue() );

        check( Long.MIN_VALUE + 1 );
        check( -123456789987654321L );
        check( -1 );
        check( 1 );
        check( 983495873645781215L );
    }

    void check( long value ) {
        OnesComplimentLongBitsConsumer consumer = new OnesComplimentLongBitsConsumer( value );
        OnesComplimentLongBitsBuilder builder = new OnesComplimentLongBitsBuilder().withSignBit( consumer.getSignBit() );

        builder.add1bit( consumer.remove1bit() ) // 1-21
                .add2bits( consumer.remove2bits() )
                .add4bits( consumer.remove4bits() )
                .add6bits( consumer.remove6bits() )
                .add8bits( consumer.remove8bits() );

        builder.add2bits( consumer.remove2bits() ) // 22-42
                .add4bits( consumer.remove4bits() )
                .add1bit( consumer.remove1bit() )
                .add6bits( consumer.remove6bits() )
                .add8bits( consumer.remove8bits() );

        builder.add8bits( consumer.remove8bits() ) // 43-63
                .add6bits( consumer.remove6bits() )
                .add4bits( consumer.remove4bits() )
                .add2bits( consumer.remove2bits() )
                .add1bit( consumer.remove1bit() );

        assertEquals( value, builder.getValue() );
    }

    private int validate( int expected, int actual ) {
        assertEquals( expected, actual );
        return actual;
    }
}