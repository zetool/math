package org.zetool.math;

import static org.zetool.math.BitOperations.bitLen;

/**
 * The class {@code Math} is a utility class that provides some additional
 * mathematical methods.
 * @author Jan-Philipp Kappmeier
 */
public class Math {
	/** Avoid instantiation of {@code Math}. */
	private Math() { }

	/**
	 * Computes the rounded down logarithm to the basis 2 of an integral number.
	 * @param n the integral number
	 * @return the logarithm to the basis 2 rounded down
	 */
	public static int log2Floor( int n ) {
		if( n <= 0 )
			throw new IllegalArgumentException( "n > 0 required" );
		return 31 - Integer.numberOfLeadingZeros( n );
	}

	/**
	 * Computes the rounded up logarithm to the basis 2 of an integral number.
	 * @param n the integral number
	 * @return the logarithm to the basis 2 rounded up
	 */
	public static int log2Ceil( int n ) {
		if( n <= 0 )
			throw new IllegalArgumentException( "n > 0 required" );
		return 32 - Integer.numberOfLeadingZeros( n - 1 );
	}

	public static int sqrt( int n ) {
		if( n < 0 )
			throw new IllegalArgumentException( "n has to be non-negative" );
		if( n == 0 )
			return 0;

		int lower, upper = bitLen( n );

		do {
			lower = n / upper;
			upper += lower;
			upper >>= 1; //upper = upper/2;
		} while( upper > lower );

		return upper*upper > n ? upper-1 : upper;
	}
}
