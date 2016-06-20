package org.zetool.math;

import static org.junit.Assert.assertThat;
import static org.zetool.math.BitOperations.bitLen;
import static org.zetool.math.BitOperations.bitTest;
import static org.zetool.math.BitOperations.maxNumber;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 * The class {@code BitOperationsTest} ...
 *
 * @author Jan-Philipp Kappmeier
 */
public class BitOperationsTest {

    /**
     * Returns the length of an integer in its binary represantation. Explicit implementation of the bisection/binary
     * search procedure. The function is aware of two complement and thus returns 32 for all negative integers. However,
     * the second represantation of zero (-0 in two complement) still returns 0.
     *
     * @param n the integer
     * @return the number of bits necessary to represent
     */
    public static int bitLenExplicit(int n) {
        return n > 0 ? n >= 1 << 15 ? n >= 1 << 23 ? n >= 1 << 27 ? n >= 1 << 29 ? n >= 1 << 30 ? 31 : 30
                : n >= 1 << 28 ? 29 : 28
                : n >= 1 << 25 ? n >= 1 << 26 ? 27 : 26
                        : n >= 1 << 24 ? 25 : 24
                : n >= 1 << 19 ? n >= 1 << 21 ? n >= 1 << 22 ? 23 : 22
                                : n >= 1 << 20 ? 21 : 20
                        : n >= 1 << 17 ? n >= 1 << 18 ? 19 : 18
                                : n >= 1 << 16 ? 17 : 16
                : n >= 1 << 7 ? n >= 1 << 11 ? n >= 1 << 13 ? n >= 1 << 14 ? 15 : 14
                                        : n >= 1 << 12 ? 13 : 12
                                : n >= 1 << 9 ? n >= 1 << 10 ? 11 : 10
                                        : n >= 1 << 8 ? 9 : 8
                        : n >= 1 << 3 ? n >= 1 << 5 ? n >= 1 << 6 ? 7 : 6
                                        : n >= 1 << 4 ? 5 : 4
                                : n >= 1 << 1 ? n >= 1 << 2 ? 3 : 2
                                        : 1
                : (n == 0) ? 0 : 32;
    }

    /**
     * Returns the length of an integer in its binary represantation. Algorithmically the maximum index which holds a
     * '1' is searched for using a binary search bisection procedure. The function is aware of two complement and thus
     * returns 32 for all negative integer. However, the second represantation of zero (-0 in two complement) still
     * returns 0.
     *
     * @param n the integer
     * @return the number of bits necessary to represent
     */
    public static int bitLenBisection(int n) {
        if (n < 0) {
            return 32;
        }
        if (n == 0) {
            return 0;
        }
    // Have two pointers, l and r pointing to bit indices of the integer
        // when returning, because indices are counted from 0, we have to add 1
        // therefore left index start is 30 -> longest bit length is 31
        int l = 30;
        int r = 0;
        while (true) {
            if (l - r <= 1) {
                return 1 + (n >= 1 << l ? l : r); // add 1 here, because l and r are indices
            }
            final int mid = (l + r) / 2;
            if (n >= 1 << mid) {
                r = mid;
            } else {
                l = mid - 1;
            }
        }
    }

    /**
     * Returns the length of an integer in its binary represantation. Algorithmically the maximum index which holds a
     * '1' is searched for by a simple loop checking all indices. The function is aware of two complement and thus
     * returns 32 for all negative integers. However, the second represantation of zero (-0 in two complement) still
     * returns 0.
     *
     * @param n the integer
     * @return the number of bits necessary to represent
     */
    public static int bitLenTrivial(int n) {
        for (int i = 31; i >= 0; --i) {
            if ((n & 1 << i) != 0) {
                return i + 1;
            }
        }
        return n == 0 ? 0 : 32;
    }

    /**
     * Returns the length of an integer in its binary represantation. Uses the internal function to subtract the
     * trailing zeros.
     *
     * @param n the integer
     * @return the number of bits necessary to represent
     */
    public static int bitLenIntern(int n) {
        return 32 - Integer.numberOfLeadingZeros(n);
    }

    @Test
    public void testBitLen() {
        // Tests
        for (int i = 0; i <= 31; i++) {
            int maxNumber = maxNumber(i);
            assertThat("Bisection fails for " + maxNumber, bitLen(maxNumber), is(equalTo(i)));
            assertThat("Bisection fails for " + maxNumber, bitLenBisection(maxNumber), is(equalTo(i)));
            assertThat("Trivial fails for " + maxNumber, bitLenTrivial(maxNumber), is(equalTo(i)));
            assertThat("Intern for " + maxNumber, bitLenIntern(maxNumber), is(equalTo(i)));
            assertThat("Explicit for " + maxNumber, bitLenExplicit(maxNumber), is(equalTo(i)));
        }

        for (int i = 0; i <= 31; i++) {
            assertThat("Bisection fails for input " + (1 << i), bitLenBisection(1 << i), is(equalTo(i + 1)));
            assertThat("Trivial fails", bitLenTrivial(1 << i), is(equalTo(i + 1)));
            assertThat("Intern", bitLenIntern(1 << i), is(equalTo(i + 1)));
            assertThat("Explicit", bitLenExplicit(1 << i), is(equalTo(i + 1)));
        }

        for (int i = 1; i < 32; i++) {
            assertThat("Bisection fails for input " + ((1 << i) - 1), bitLenBisection((1 << i) - 1), is(equalTo(i)));
            assertThat("Trivial fails for input " + ((1 << i) - 1), bitLenTrivial((1 << i) - 1), is(equalTo(i)));
            assertThat("Intern for input " + ((1 << i) - 1), bitLenIntern((1 << i) - 1), is(equalTo(i)));
            assertThat("Explicit for input " + ((1 << i) - 1), bitLenExplicit((1 << i) - 1), is(equalTo(i)));
        }
    }

    @Test
    public void testBitTestAll() {
        int test = 0xFFFFFFFF;
        for (byte i = 0; i < Integer.SIZE; ++i) {
            assertThat(bitTest(test, i), is(true));
        }
    }

    @Test
    public void testBitTestNone() {
        for (byte i = 0; i < Integer.SIZE; ++i) {
            assertThat(bitTest(0, i), is(false));
        }
    }

    @Test
    public void testBitTestPowerOfTwo() {
        for (byte i = 0; i < Integer.SIZE; ++i) {
            assertExactlyBit(1 << i, i);
        }
    }

    /**
     * Asserts that exactly one bit of a given integer is set to {@literal 1}.
     *
     * @param test the number to be tested
     * @param bit the bit's index
     */
    private void assertExactlyBit(int test, byte bit) {
        for (byte i = 0; i < bit; ++i) {
            assertThat("Test for " + test + " at index " + i, bitTest(test, i), is(false));
        }
        assertThat("Test for " + test + " at index " + bit, bitTest(test, bit), is(true));
        for (byte i = (byte) (bit + 1); i < Integer.SIZE; ++i) {
            assertThat("Test for " + test + " at index " + i, bitTest(test, i), is(false));
        }
    }
}
