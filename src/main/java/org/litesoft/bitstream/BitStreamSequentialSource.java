package org.litesoft.bitstream;

public interface BitStreamSequentialSource extends BitStreamProvider {
    default BitStreamSequentialSource plus( BitStreamSequentialSource source ) {
        return (source == null) ? this : new BitStreamSequentialSourceComposite( this, source );
    }
}
