package org.zetool.math;

import org.junit.Test;

/**
 * The class {@code MathTest} ...
 * @author Jan-Philipp Kappmeier
 */
public class MathTest {

    public void testFactorial() {
        for( int i = 63; i < 101; ++i ) {
            System.out.println( java.lang.Math.sqrt( i ) + " - " + (int) java.lang.Math.floor( Math.sqrt( i ) ) + " - " + org.zetool.math.Math.sqrt( i ) );
        }
        
        int count = 100;
        long start;
        long end;
        start = System.nanoTime();
        for( int i = 0; i < count; ++i ) {
            int k = (int) java.lang.Math.floor( Math.sqrt( i ) );
        }
        end = System.nanoTime();
        System.out.println( "Runden:     " + (end - start) );

        start = System.nanoTime();
        for( int i = 0; i < count; ++i ) {
            int k = org.zetool.math.Math.sqrt( i );
        }
        end = System.nanoTime();
        System.out.println( "Ganzzahlig: " + (end - start) );
    }
    
    @Test
    public void logistic() {
        // find a value such that logistic(0) is almost 0.
        for( int i = 0; i < 32; ++i) {
            double val = Math.logistic(0, 1, 0.5, i);
            double vala = Math.logistic(1, 1, 0.5, i);
            System.out.println("l(" + i + ") = " + val);
            System.out.println("u(" + i + ") = " + vala);
        }
    }

}
