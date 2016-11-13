package org.zetool.math;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class Conversion {
    
    public static final long SEC_TO_NANO_SECONDS = 1000000000L;
    public static final double NANO_SECONDS_TO_SEC = 1.0 / SEC_TO_NANO_SECONDS;
    public static final double ANGLE2DEG = java.lang.Math.PI / 180.0;
    public static final double DEG2ANGLE = 180.0 / java.lang.Math.PI;

    /** Avoid instantiation of utility class. */
    private Conversion() {
    }
}
