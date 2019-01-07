package org.elvaston;


import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * Incubating Tests.
 */
public class IncubatingTest {

    @Test
    public void closestPal() {
        Assert.assertEquals(9, pal(9));
        Assert.assertEquals(484, pal(489));
        Assert.assertEquals(282, pal(285));
        Assert.assertEquals(191, pal(189));
        Assert.assertEquals(33, pal(29));
        Assert.assertEquals(1_001, pal(1_002));
        Assert.assertEquals(1_221, pal(1_234));
        Assert.assertEquals(123_454_321, pal(123_456_789));
    }

    private int pal(int i) {
        if (i < 10) {
            return i;
        }

        int lower_pal = i;
        int upper_pal = i;

        while (lower_pal > 0 && upper_pal < 999_999_999) {
            if (isPal(lower_pal)) {
                return lower_pal;
            } else if (isPal(upper_pal)) {
                return upper_pal;
            }
            lower_pal--;
            upper_pal++;
        }
        return 0;
    }

    private static boolean isPal(int i) {
        int original_number = i;
        int reversed_number = 0;
        while (i > 0) {
            reversed_number = reversed_number * 10 + (i % 10);
            i /= 10;
        }
        return reversed_number == original_number;
    }

    @Test
    public void boundsTest() {
        Assert.assertEquals(35, bounds(3, 100, 250));
        Assert.assertEquals(1120, bounds(2, 10_000, 12_345));
        Assert.assertEquals(0, bounds(0, 20, 21));
        Assert.assertEquals(120, bounds(9, 899, 1_000));
    }

    private int bounds(int i, int lower, int upper) {
        return IntStream.range(lower + 1, upper).map(operand -> {
            int count = 0;
            while (operand > 0) {
                if (operand % 10 == i) {
                    count++;
                }
                operand /= 10;
            }
            return count;
        }).sum();
    }

    @Test
    public void t2() {

        int[] Array1 = new int[] {0,0,0,0,0,0,0,0,1};
        IntStream.range(0, Array1.length).mapToObj(i -> Array1[i] + (i > 0 && i % 7 ==0 ? "\n" : ",")).forEach(System.out::print);

        //5, 15, 1, 3
        //5, 10, 5, 4
        Assert.assertEquals(5, IncubatingTest.median(5));
        Assert.assertEquals(10, IncubatingTest.median(5, 15));
        Assert.assertEquals(5, IncubatingTest.median(5, 15, 1));
        Assert.assertEquals(4, IncubatingTest.median(5, 15, 1, 3));
        IncubatingTest.sort(new int[]{4,3,2,6,1,5});
    }

    private static int median(int... numbers) {
        if (numbers.length == 1) return numbers[0];
        if (numbers.length == 2) return (numbers[0] + numbers[1]) / 2;

        int[] ranksNeeded = new int[]{((numbers.length - 1)/ 2), 0};
        if (numbers.length % 2 == 0) {
            ranksNeeded[1] = ranksNeeded[0] + 1;
        } else {
            ranksNeeded[1] = ranksNeeded[0];
        }

        return (valueOf(numbers, ranksNeeded[0]) + valueOf(numbers, ranksNeeded[1])) / 2;
    }

    private static int valueOf(int[] numbers, int rank) {
        int first = Integer.MIN_VALUE;
        int smallest = Integer.MAX_VALUE;
        for (int i = 0; i <= rank; i++) {
            for (int number : numbers) {
                if (number < smallest && number > first) {
                    smallest = number;
                }
            }
            first = smallest;
            smallest = Integer.MAX_VALUE;
        }
        return first;
    }

    private static void sort(int[] numbers) {
        int left_index = 0;
        int right_index = numbers.length - 1;

        reSort(numbers, left_index, right_index);
    }

    private static void reSort(int[] numbers, int left_index, int right_index) {

        int i = left_index;
        int j = right_index;
        int pivot = numbers[left_index + (right_index - left_index) / 2];

        System.out.println("pivot: " + pivot + ", left_index: " + left_index + ", right_index: " + right_index);
        IntStream.of(numbers).forEach(value -> System.out.print(value + ","));
        System.out.print("\n");

        while (i <= j) {
            while (numbers[i] < pivot) {
                i++;
            }
            while (numbers[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(numbers, i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        IntStream.of(numbers).forEach(value -> System.out.print(value + ","));

        if (left_index < j) {
            reSort(numbers, left_index, j);
        }
        if (i < right_index) {
            reSort(numbers, i, right_index);
        }
    }

    private static void swap(int[] numbers, int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }
}
