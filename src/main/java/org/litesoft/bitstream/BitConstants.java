package org.litesoft.bitstream;

public interface BitConstants {

    int[] MASKS = {0, 1, 3, 7, 15, 31, 63, 127, 255};
    // . indexes:  0, 1, 2, 3,  4,  5,  6,  7,   8

    String POST_CLASS_NAME_MID_SECTION = " error ";
    String ERROR_NOT_1_8 = " not between 1 and 8 (inclusive)";
    String ERROR_OVER_FLOW_MID_SECTION = " would exceed maximum allowed Buffer size of ";
    String ERROR_OVER_FLOW_SUFFIX = " bits";
    String ERROR_UNDER_FLOW_MID_SECTION = ", but only ";
    String ERROR_UNDER_FLOW_SUFFIX = " are available";

}
