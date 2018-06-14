package org.elvaston.dollin.euler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.LongStream;

/**
 * Double-base palindromes
 * Problem 36
 * The decimal number, 585 = 1001001001 (binary), is palindromic in both bases.
 * Find the sum of all numbers, less than one million, which are palindromic in base 10 and base 2.
 * (Please note that the palindromic number, in either base, may not include leading zeros.)
 */
public class DoubleBasePalindromes {

    private static final Logger LOG = LogManager.getLogger(DoubleBasePalindromes.class);

    /**
     * Solve problem 36 for decimal numbers in the 1 to 1_000_000 range.
     * @return long for the sum of the double-base palindromes
     */
    public long solve() {
        return LongStream.range(1, 1_000_000)
                .filter(i -> Long.parseLong(
                        new StringBuilder("" + i)
                                .reverse()
                                .toString()) % i == 0)
                .filter(i -> Long.toBinaryString(i).equals(
                        new StringBuilder(Long.toBinaryString(i))
                        .reverse()
                        .toString()))
                .peek(i -> LOG.info(i + "=|=" + Long.toBinaryString(i)))
                .sum();
    }
}
