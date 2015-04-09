
package org.zetool.math;

import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class Rotate extends TestCase {
	
	@Test
	public void testRotate() {
		double b = 6;
		double h = 5;
		double angle = 27;
		
		double maxA = Double.MIN_VALUE;
		double maxAp = Double.MIN_VALUE;
		
		System.out.println( "Grenze: " + computeCase2Bound( h, b ) );
		
		int m = 1000;
		for( int i = 0; i <= m; ++i ) {
			double p = i*(1./m) * h;
			double A = computeArea( h, b, angle, p );
			if( A >= maxA ) {
				maxA = A;
				maxAp = p;
			}
			//System.out.println( "p="+ p + "; A=" + A);
			
		}
		
		System.out.println( "p = " + maxAp );
		System.out.println( "A = " + maxA );
	}
	
	private double computeCase2Bound( double height, double width ) {
		
		double b = width;
		double h = height;
		double p = h/2;
		
		double dh = java.lang.Math.sqrt( p * p + b * b );
		
		double cosdelta = p / dh;
		double delta = java.lang.Math.acos( cosdelta );
		
		double alpha = java.lang.Math.PI - java.lang.Math.PI/2 - delta;
		
		
		// alternative
		double diag = java.lang.Math.sqrt(b*b + h*h);
		double cos = b/diag;
		double ret = java.lang.Math.acos( cos );
		System.out.println( ret*Conversion.DEG2ANGLE );
		
		return alpha * Conversion.DEG2ANGLE;
	}
	
	private double computeArea( double height, double width, double angle, double p ) {
		double b = width;
		double h = height;
		//Rectangle r = new Rectangle( 0, 0, b, h );
		double alpha = angle*Conversion.ANGLE2DEG;
//		System.out.println( Math.cos( alpha )  );
		//double p = h/2;
		
		double beta = java.lang.Math.PI - java.lang.Math.PI/2 - alpha;
//		System.out.println( "beta = " + beta * Conversion.DEG2ANGLE );
		double gamma = java.lang.Math.PI/2 - beta;
//		System.out.println( "gamma = " + gamma * Conversion.DEG2ANGLE );
		double delta = java.lang.Math.PI/2 - gamma;
//		System.out.println( "delta = " + delta * Conversion.DEG2ANGLE );
		
//		System.out.println( Math.cos( alpha + beta + gamma + delta ) );
		
		double g11 = p * java.lang.Math.sin( gamma );
//		System.out.println( "g1 = " + g11 );
		double a1 = java.lang.Math.sqrt(p*p-g11*g11);
//		System.out.println( "a1 = " + a1 );
		double g12 = java.lang.Math.tan( delta ) * a1;
		
		
		
//		System.out.println( "g2 = " + g12 );
		double l1 = g11+g12;
		
		double p1 = h - p;
		double g21 = java.lang.Math.sin( delta ) * p1;
//		System.out.println( "g1 = " + g21 );
		double a2 = java.lang.Math.sqrt( p1*p1-g21*g21 );
//		System.out.println( "a2 = " + a2 );
		double g22 = java.lang.Math.tan( gamma ) * a2;
//		System.out.println( "g2 = " + g22 );
		double l2 = g21 + g22;
		
		
		// recompute the first length, maybe we hit the left wall and not the upper!
		double r = java.lang.Math.sqrt( l2 * l2 - p1*p1 );
//		System.out.println( "r = " + r );
		double r1 = b - r;
//		System.out.println( "r1 = " + r1 );
		
		double newh = r1 / java.lang.Math.cos( alpha );
//		System.out.println( "New h = " + newh );
		
		l1 = java.lang.Math.min( newh, l1 );
		
		double A = l1 * l2;
//		System.out.println( "A = " + A );
		
		return A;
	}
}
