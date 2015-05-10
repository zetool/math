/* copyright 2010-2015
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.zetool.math;

/**
 * The class {@code Combinatorics} is a utility class that provides several combinatorial methods.
 *
 * @author Jan-Philipp Kappmeier
 */
public class Combinatorics {

  /**
   * Avoids instantiation of the utility class with combinatorial methods.
   */
  private Combinatorics() {
  }

  private static class FactorialComputation {
    private long N;

    private long compute( int n ) {
      if( n < 0 ) {
        throw new IllegalArgumentException( "n must not be at least zero." );
      }
      if( n < 2 ) {
        return 1;
      }

      long p = 1;
      long r = 1;
      N = 1;

      int log2n = Math.log2Floor( n );
      int h = 0, shift = 0, high = 1;

      while( h != n ) {
        shift += h;
        h = n >>> log2n--;
        int len = high;
        high = (h & 1) == 1 ? h : h - 1;
        len = (high - len) / 2;

        if( len > 0 ) {
          p *= factorialProduct( len );
          r *= p;
        }
      }
      return r << shift;
    }

    /**
     * Private auxiliary method used to compute the factorial.
     *
     * @param n
     * @return
     */
    private long factorialProduct( final int n ) {
      final int m = n / 2;
      if( m == 0 ) {
        N += 2;
        return N;
      }
      if( n == 2 ) {
        N += 4;
        return (N-2) * N;
      }
      return factorialProduct( n - m ) * factorialProduct( m );
    }
  }

  /**
   * Computes the factorial of a natural number {@code n}.
   *
   * @param n the number whose factorial is computed
   * @return the factorial of {@code n}
   */
  public static long factorial( final int n ) {
    return new FactorialComputation().compute( n );
  }

  /**
   * Computes the binomial coefficient {@code n} choose {@code k}. This is very slow!
   *
   * @param n parameter {@code n}
   * @param k parameter {@code k}
   * @return the binomial coefficient
   */
  public static long bico( final int n, final int k ) {
    if( k < 0 || k > java.lang.Math.abs( n ) ) {
      throw new IllegalArgumentException( "Numbers negative or larger than " + n + " not accepted." );
    }
    if( k == 1 || k == n - 1) {
      return n;
    }
    if( k == 0 || k == n ) {
      return 1;
    }
    if( n > 0 ) {
      return bicoRecursive( n, k );
    } else {
      throw new UnsupportedOperationException( "Negative n are not implemented yet." );
    }
  }
  
  private static long bicoRecursive( final int n, final int k ) {
    if( 2 * k > n ) {
      return bicoRecursive(n, n - k );
    }
    long ret = n;
    for( int i = 2; i <= k; i++ ) {
      ret *= (n + 1 - i);
      ret /= i;
    }
    return ret;
  }

  /**
   * Computes a rounded binomial coefficient.
   * 
   * @param n
   * @param k
   * @return rounded binomial coefficient.
   */
  public static double bicoRounded( final int n, final int k ) {
    return java.lang.Math.floor( 0.5 + java.lang.Math.exp( factln( n ) - factln( k ) - factln( n - k ) ) );
  }

  static double[] table = {1, 1, 2, 6, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  static int ntop = 4;

  private static final double[] cof = {
    76.18009172947146,
    -86.50532032941677,
    24.01409824083091,
    -1.231739572450155,
    0.1208650973866179e-2,
    -0.5395239384953e-5
  };

  public static double gammaln( final double xx ) {
    int j;
    double x, y, tmp = 0, ser;
    y = x = xx;
    tmp = x + 5.5;
    tmp -= (x + 0.5) * java.lang.Math.log( tmp );
    ser = 1.000000000190015;
    for( j = 0; j < 6; ++j ) {
      ser += cof[j] / ++y;
    }
    return -tmp + java.lang.Math.log( 2.5066282746310005 * ser / x );
  }

  /**
   * Approximation of the factorial.
   * 
   * @param n
   * @return 
   */
  public static double factrl( final int n ) {
    if( n > 32 ) {
      return java.lang.Math.exp( gammaln( n + 1.0 ) );
    }
    int j;
    while( ntop < n ) {
      j = ntop++;
      table[ntop] = table[j] * ntop;
    }
    return table[n];
  }

  static double[] a = new double[101];

  public static double factln( final int n ) {
    if( n <= 1 ) {
      return 0.0;
    }
    if( n <= 100 ) {
      return (a[n] != 0.0 ? a[n] : (a[n] = gammaln( n + 1.0 )));
    } else {
      return gammaln( n + 1.0 );
    }
  }
}
