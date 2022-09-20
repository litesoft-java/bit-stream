package org.litesoft.bitstream;

class Nbits implements BitConstants {
    public static int addNbits( int currentBitCount, int maxBitCount, BitStreamBuilder<?> builder, int n, int bits ) {
        if ( (n < 1) || (8 < n) ) {
            throw error( builder, "add", n, ERROR_NOT_1_8 );
        }
        if ( currentBitCount + n > maxBitCount ) {
            throw error( builder, "add", n,
                         ERROR_OVER_FLOW_MID_SECTION + maxBitCount + ERROR_OVER_FLOW_SUFFIX );
        }
        return MASKS[n] & bits;
    }

    public static int removeNbits( int currentBitCount, BitStreamProvider provider, int n ) {
        if ( (n < 1) || (8 < n) ) {
            throw error( provider, "remove", n, ERROR_NOT_1_8 );
        }
        if ( currentBitCount < n ) {
            throw error( provider, "remove", n,
                         ERROR_UNDER_FLOW_MID_SECTION + currentBitCount + ERROR_UNDER_FLOW_SUFFIX );
        }
        return MASKS[n];
    }

    public static IllegalStateException error( Object builderOrProvider, String direction, int count, String message ) {
        StringBuilder sb = new StringBuilder();
        sb.append( builderOrProvider.getClass().getSimpleName() )
                .append( POST_CLASS_NAME_MID_SECTION )
                .append( direction ).append( count ).append( "bit" );
        if ( count > 1 ) {
            sb.append( 's' );
        }
        sb.append( message );
        throw new IllegalStateException( sb.toString() );
    }
}
