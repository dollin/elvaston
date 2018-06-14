package org.elvaston.dollin.euler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Double-base palindromes
 * Problem 36
 * The decimal number, 585 = 1001001001 (binary), is palindromic in both bases.
 * Find the sum of all numbers, less than one million, which are palindromic in base 10 and base 2.
 * (Please note that the palindromic number, in either base, may not include leading zeros.)
 */
public class Problem36Test {

    @Test
    public void doubleBasePalindrome() {
        assertEquals(872_187, new DoubleBasePalindromes().solve());
    }
}
