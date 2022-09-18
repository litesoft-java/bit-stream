package org.litesoft.bitstream;

public interface BitStreamBuilderProvider<T> extends BitStreamBuilder<T>,
                                                     BitStreamProvider {
    /**
     * Returns <code>true</code> if this BitStreamProvider is also a BitStreamBuilder!
     */
    @Override
    default boolean canAddBits() {
        return true;
    }
}
