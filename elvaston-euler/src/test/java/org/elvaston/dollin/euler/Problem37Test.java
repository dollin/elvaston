package org.elvaston.dollin.euler;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.LongStream;

/**
 * The number 3797 has an interesting property. Being prime itself,
 * it is possible to continuously remove digits from left to right,
 * and remain prime at each stage: 3797, 797, 97, and 7.
 * Similarly we can work from right to left: 3797, 379, 37, and 3.
 * Find the sum of the only eleven primes that are both truncatable from left to right
 * and right to left.
 * NOTE: 2, 3, 5, and 7 are not considered to be truncatable primes.
 */
public class Problem37Test {

    @Test
    public void truncatablePrimes() {

        Assert.assertEquals(748_317L, LongStream.rangeClosed(10, 1_000_000)
                .filter(x -> new TruncatablePrimes().solve(x))
                .sum());
    }
}
