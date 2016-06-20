package org.zetool.math;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.zetool.math.Combinatorics.bico;
import static org.zetool.math.Combinatorics.factorial;
import static org.zetool.math.Combinatorics.factrl;


/**
 * Units tests for {@link Combinatorics}.
 *
 * @author Jan-Philipp Kappmeier
 */
public class CombinatoricsTest {

  /**
   * Creates a new instance of {@code CombinatoricsTest}.
   */
  public CombinatoricsTest() {
  }

  /**
   * Tests factorial for the 21 factorial values that are possible to represent as long.
   */
  @Test
  public void testFactorial() {
    long expected = 1;
    for( int i = 0; i < 21; ++i ) {
      final long res = Combinatorics.factorial( i );
      assertThat( i + " factorial", res, is( equalTo( expected ) ) );
      expected *= i + 1;
    }
  }

  /**
   * Tests that the rounded factorial is equal to the actual factorial for small values.
   */
  @Test
  public void testRoundedEqualForLowNumbers() {
    for( int i = 1; i < 21; ++i ) {
      assertThat( i + "!", (long) factrl( i ), is( equalTo( factorial( i ) ) ) );
    }
  }
  
  @Test( expected = IllegalArgumentException.class)
  public void testBinomialCoefficientNotAcceptedNegative() {
    bico( 3, -1 );
  }
  
  @Test( expected = IllegalArgumentException.class)
  public void testBinomialCoefficientNotAcceptedLarge() {
    bico( 3, 4 );
  }
  
  @Test( expected = UnsupportedOperationException.class )
  public void testBinomialCoefficientNegative() {
    bico( -4, 2 );
  }
  
  @Test
  public void testBinomialCoefficientBoundary() {
    for( int i = 1; i < 20; ++i ) {
      assertThat( "left boundary for " + i, bico( i, 0 ), is( equalTo( 1L ) ) );
      assertThat( "left next value for " + i, bico( i, 1 ), is( equalTo( (long)i ) ) );
      assertThat( "right next value for " + i, bico( i, i-1 ), is( equalTo( (long)i ) ) );
      assertThat( "right boundary for " + i, bico( i, i ), is( equalTo( 1L ) ) );
    }
  }

  @Test
  public void testBinomialCoefficient() {
    long[] nChoose2k = {2, 6, 20, 70, 252, 924, 3432, 12870, 48620, 184756, 705432, 2704156, 10400600, 40116600,
      155117520L, 601080390L, 2333606220L, 9075135300L};

    for( int i = 0; i < nChoose2k.length; ++i ) {
      assertThat( bico( 2 * (i + 1), i + 1 ), is( equalTo( nChoose2k[i] ) ) );
    }
  }

  public static long naiveBinomial( int n, int k ) {
    long fn = Combinatorics.factorial( n );
    long fk = Combinatorics.factorial( k );
    long fnk = Combinatorics.factorial( n - k );
    return fn / (fk * fnk);
  }

  public static long fastBinomial( int n, int k ) {
    if( 0 > k || k > n ) {
      throw new IllegalArgumentException( "Binomial: 0 <= k and k <= n required, but n was " + n + " and k was " + k );
    }

    if( (k == 0) || (k == n) ) {
      return 1;
    }

    if( k > n / 2 ) {
      k = n - k;
    }

    int fi = 0, nk = n - k;

    int rootN = org.zetool.math.Math.sqrt( n );

    PrimeSieve primeSieve = new PrimeSieve( n );
    primeSieve.compute();
    int[] primes = primeSieve.getPrimes();

    for( int i = 0; i < primeSieve.getPrimeCount(); ++i ) {
      if( primes[i] > nk ) {
        primes[fi++] = primes[i];
        continue;
      }

      if( primes[i] > n / 2 ) {
        continue;
      }

      if( primes[i] > rootN ) {
        if( n % primes[i] < k % primes[i] ) {
          primes[fi++] = primes[i];
        }
        continue;
      }

      int r = 0, N = n, K = k, p = 1;

      while( N > 0 ) {
        r = ((N % primes[i]) < (K % primes[i] + r)) ? 1 : 0;
        if( r == 1 ) {
          p *= primes[i];
        }
        N /= primes[i];
        K /= primes[i];
      }
      primes[fi++] = p;
    }

    long ret = 1;
    for( int i = 0; i < fi; ++i ) {
      ret *= primes[i];
    }
    return ret;
  }

  public static long betterBinomial( int n, int k ) {
    if( k < 0 || k > java.lang.Math.abs( n ) ) {
      return 0;
    }
    if( k == 1 ) {
      return n;
    }
    if( k == 0 ) {
      return 1;
    }
    if( n > 0 ) {
      if( 2 * k > n ) {
        return betterBinomial( n, n - k );
      }
      long ret = n;
      for( int i = 2; i <= k; i++ ) {
        ret *= (n + 1 - i);
        ret /= i;
      }
      return ret;
    } else {
      throw new IllegalArgumentException( "Negative n are not implemented yet." );
    }
  }

  public void testBinomial() {
    long bcounter = 0;
    long fcounter = 0;
    long ncounter = 0;
    int max = 61;
    long start;
    long end;
    for( int n = 1; n <= max; ++n ) {
      for( int k = 0; k <= n; ++k ) {
        bcounter = 0;
        fcounter = 0;
        for( int i = 1; i < 10000; i++ ) {
          start = System.nanoTime();
          //long nb = naiveBinomial( n, k );
          end = System.nanoTime();
          ncounter += (end - start);

          start = System.nanoTime();
          long bb = betterBinomial( n, k );
          end = System.nanoTime();
          bcounter += (end - start);

          start = System.nanoTime();
          long fb = fastBinomial( n, k );
          end = System.nanoTime();
          fcounter += (end - start);
          //System.out.println( n + " über " + k + ": " + nb + " - " + bb + " - " + fb );
          //System.out.println( n + " über " + k + ": " + bb + " - " + fb );
        }
        //System.out.println( "Better: " + bcounter );
        //System.out.println( "Fast:   " + fcounter );
        System.out.println( n + " über " + k + " ratio: " + ((double) fcounter / (double) bcounter) );
      }
    }
    System.out.println( "Naive:  " + ncounter );

//		System.out.println( naiveBinomial( 4, 2 ) );
//		System.out.println( fastBinomial( 4, 2 ) );
//		System.out.println( betterBinomial( 4, 2 ) );
  }

}
