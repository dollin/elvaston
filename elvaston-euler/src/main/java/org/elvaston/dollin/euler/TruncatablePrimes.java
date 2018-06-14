package org.elvaston.dollin.euler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.LongFunction;
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
public class TruncatablePrimes {

    private static final Logger LOG = LogManager.getLogger(TruncatablePrimes.class);

    private LongFunction dropRight = value -> value / 10L;

    private LongFunction dropLeft = value -> {
        long minus = 1L;
        while (value > minus) {
            minus *= 10L;
        }
        return value % (minus / 10L);
    };

    /**
     * Solution for euler 37.
     * @param value the number to solve
     * @return true if it is a truncatable prime
     */
    public boolean solve(long value) {

        if (firstOrLastAreNotPrime(value)) {
            return false;
        }
        if (isPrimeRecursive(value, dropRight) && isPrimeRecursive(value, dropLeft)) {
            LOG.info("{}", value);
            return true;
        }
        return false;
    }

    private boolean firstOrLastAreNotPrime(long value) {
        long first = value;
        while (first >= 10L) {
            first /= 10L;
        }
        return !is235or7(first) && !is235or7(value % 10);
    }

    private boolean isPrimeRecursive(long value, LongFunction function) {
        if (value <= 10) {
            return is235or7(value);
        } else if (value % 2 != 0 && isPrime(value)) {
            return isPrimeRecursive((Long) function.apply(value), function);
        }
        return false;
    }

    private boolean is235or7(long value) {
        return value == 2L || value == 3L || value == 5L || value == 7L;
    }

    private boolean isPrime(long value) {
        return value > 1 && LongStream
                .rangeClosed(2, (long)(Math.sqrt(value)))
                .noneMatch(n -> value % n == 0);
    }
}
