import java.util.Arrays;

/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    public static int[] betterCountingSort(int[] arr) {
        if (arr.length == 1 || arr.length == 0) {
            return arr;
        }
        int negCount = 0, posCount = 0;
        for (int i : arr) {
            if (i < 0) {
                negCount++;
            } else {
                posCount++;
            }
        }
        if (negCount == 0) {
            return naiveCountingSort(arr);
        }
        if (posCount == 0) {
            return negCountingSort(arr);
        }
        int[] neg = new int[negCount];
        int[] pos = new int[posCount];
        int negIndex = 0, posIndex = 0;
        for (int i : arr) {
            if (i < 0) {
                neg[negIndex] = i;
                negIndex++;
            } else {
                pos[posIndex] = i;
                posIndex++;
            }
        }
        int[] sortedNeg = negCountingSort(neg);
        int[] sortedPos = naiveCountingSort(pos);
        int[] sorted = new int[sortedNeg.length + sortedPos.length];
        int sortedIndex = 0;
        for (int i : sortedNeg) {
            sorted[sortedIndex] = i;
            sortedIndex++;
        }

        for (int i : sortedPos) {
            sorted[sortedIndex] = i;
            sortedIndex++;
        }
        return sorted;
    }

    private static int[] negCountingSort(int[] arr) {
        int[] absArr = naiveCountingSort(flip(arr));
        reverse(absArr);
        return flip(absArr);
    }

    private static int[] flip(int[] arr) {
        int[] flipped = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            flipped[i] = -arr[i];
        }
        return flipped;
    }

    private static void reverse(int[] arr) {
        int i = 0, j = arr.length - 1;
        while (i < j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }
}
