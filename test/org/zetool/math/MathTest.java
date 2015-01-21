package org.zetool.math;

import junit.framework.TestCase;

/**
 * The class {@code MathTest} ...
 * @author Jan-Philipp Kappmeier
 */
public class MathTest extends TestCase {

	/** Creates a new instance of {@code MathTest}. */
	public MathTest() { }

	public void testFactorial() {

		for( int i = 63; i < 101; ++i ) {
			System.out.println( Math.sqrt( i ) + " - " + (int) java.lang.Math.floor( Math.sqrt( i ) ) + " - " + org.zetool.math.Math.sqrt( i ) );
		}

		long start;
		long end;
		start = System.nanoTime();
		for( int i = 0; i < Integer.MAX_VALUE/8; ++i ) {
			int k = (int) java.lang.Math.floor( Math.sqrt( i ) );
		}
		end = System.nanoTime();
		System.out.println( "Runden:     " + (end - start) );

		start = System.nanoTime();
		for( int i = 0; i < Integer.MAX_VALUE/8; ++i ) {
			int k = org.zetool.math.Math.sqrt( i );
		}
		end = System.nanoTime();
		System.out.println( "Ganzzahlig: " + (end - start) );


	}

}
