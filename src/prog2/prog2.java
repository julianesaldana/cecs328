package prog2;

import java.util.Random;
import java.util.Scanner;

public class prog2 {
    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        int[] original = new int[100000];
        int[] warmUp;
        Random rand = new Random();

        boolean run = true;
        while (run) {
            System.out.println("1) sorted\n2) random\n3) reverse");
            switch (in.nextInt()) {
                case 1:
                    // creating ordered array
                    for (int i = 0; i < original.length; i++)
                        original[i] = i + 1;

                    // warm up
                    for (int i = 0; i < 5; i++) {
                        warmUp = original.clone();
                        insertionSort(warmUp);
                        warmUp = original.clone();
                        quickSort(warmUp, 0, warmUp.length - 1);
                    }

                    // gathering data
                    for (int i = 0; i < 10; i++) {
                        int[] dupe = original.clone();

                        long startTime = System.nanoTime();
                        insertionSort(dupe);
                        long endTime = System.nanoTime();
                        if (isOrdered(dupe))
                            System.out.printf("Insertion sort took %d nanoseconds\n", endTime - startTime);

                        dupe = original.clone();

                        startTime = System.currentTimeMillis();
                        quickSort(dupe, 0, dupe.length - 1);
                        endTime = System.currentTimeMillis();
                        if (isOrdered(dupe))
                            System.out.printf("Quick sort took %d milliseconds\n", endTime - startTime);
                        System.out.println();
                    }
                    break;
                case 2:
                    // creating random array
                    for (int i = 0; i < original.length; i++)
                        original[i] = rand.nextInt(100000) + 1;

                    // warm up
                    for (int i = 0; i < 5; i++) {
                        warmUp = original.clone();
                        insertionSort(warmUp);
                        warmUp = original.clone();
                        quickSort(warmUp, 0, warmUp.length - 1);
                    }

                    // gathering data
                    for (int i = 0; i < 10; i++) {
                        int[] dupe = original.clone();

                        long startTime = System.currentTimeMillis();
                        insertionSort(dupe);
                        long endTime = System.currentTimeMillis();
                        if (isOrdered(dupe))
                            System.out.printf("Insertion sort took %d milliseconds\n", endTime - startTime);

                        dupe = original.clone();

                        startTime = System.currentTimeMillis();
                        quickSort(dupe, 0, dupe.length - 1);
                        endTime = System.currentTimeMillis();
                        if (isOrdered(dupe))
                            System.out.printf("Quick sort took %d milliseconds\n", endTime - startTime);
                        System.out.println();
                    }

                    break;
                case 3:
                    // creating reverse array
                    for (int i = original.length - 1; i >= 0; i--)
                        original[i] = i;

                    // warm up
                    for (int i = 0; i < 5; i++) {
                        warmUp = original.clone();
                        insertionSort(warmUp);
                        warmUp = original.clone();
                        quickSort(warmUp, 0, warmUp.length - 1);
                    }

                    // gathering data
                    for (int i = 0; i < 10; i++) {
                        int[] dupe = original.clone();

                        long startTime = System.currentTimeMillis();
                        insertionSort(dupe);
                        long endTime = System.currentTimeMillis();
                        if (isOrdered(dupe))
                            System.out.printf("Insertion sort took %d milliseconds\n", endTime - startTime);

                        dupe = original.clone();

                        startTime = System.currentTimeMillis();
                        quickSort(dupe, 0, dupe.length - 1);
                        endTime = System.currentTimeMillis();
                        if (isOrdered(dupe))
                            System.out.printf("Quick sort took %d milliseconds\n", endTime - startTime);
                        System.out.println();
                    }
                    break;
                default:
                    run = false;
                    break;
            }
        }
    }

    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {   // i = 1
            int j, temp = arr[i];   // making a copy of a[i]
            for (j = i - 1; j >= 0; j--) {     // starting moving right numbers to the left
                if (arr[j] > temp)
                    arr[j + 1] = arr[j];  // inversion detected; move a[j] to the right
                else
                    break;  // index j is one spot to the left of where temp belongs
            }
            arr[j + 1] = temp;
        }
    }

    public static void quickSort(int[] arr, int low, int high) {
        // do insertion sort if 10 or smaller
        if (high - low  <= 10)
            insertionSort(arr);
        else {
            int pivot = median (arr, low, high);
            swap(arr[pivot], arr[high]);
            int store = low;
            for (int i = low; i < high; i++) {
                if (arr[i] <= arr[pivot]) {
                    swap(arr[store], arr[i]);
                    store++;
                }
            }

            swap(arr[store], arr[high]);
            quickSort(arr, low, store - 1);
            quickSort(arr, store + 1, high);
        }
    }

    public static boolean isOrdered(int[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i - 1] > numbers[i])
                return false;
        }
        return true;
    }

    public static void swap(int left, int right){
        int temp = left;
        left = right;
        right = temp;
    }

    public static int median(int[] arr, int left, int right) {
        int middle = (left + right) / 2;
        if ((arr[left] < arr[middle] && arr[middle] < arr[right]) || (arr[right] < arr[middle] && arr[middle] < arr[left]))
            return middle;
        else if ((arr[middle] < arr[left] && arr[left] < arr[right]) || (arr[right] < arr[left] && arr[left] < arr[middle]))
            return left;
        else
            return right;
    }
}
