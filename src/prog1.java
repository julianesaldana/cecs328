// julian saldana 018462169

import java.util.Random;
import java.util.Scanner;

public class prog1 {
    // main program
    // to access test, uncomment line below
    // test()
    public static void main(String[]arg){
        Scanner in = new Scanner(System.in);
        boolean loop = true;
        while (loop){
            System.out.println("1. Quit the program\n2. Time Freddy's algorithm\n3. Time Sophie's algorithm\n4. Time " +
                    "Johnny's algorithm\n5. Time Sally's algorithm");
            int option = in.nextInt();

            if (option == 1)
                loop = false;
            else{
                System.out.print("Enter seed value: ");
                int seed = in.nextInt();
                System.out.print("Enter input size: ");
                int size = in.nextInt();

                int [] numbers = randomNumberGenerator(seed, size);
                int numbersLeft = 0;
                int numbersRight = numbers.length - 1;

                switch (option){
                    case 2:
                        for (int i = 0; i < 20; i++)    // warming up
                            freddy(numbers);

                        long startTime = System.nanoTime();
                        freddy(numbers);
                        long endTime = System.nanoTime();
                        System.out.println("Max is: " + freddy(numbers));
                        System.out.println("Freddy's took " + (endTime - startTime) + " nanoseconds");
                        break;
                    case 3:
                        for (int i = 0; i < 20; i++)    // warming up
                            sophie(numbers);

                        startTime = System.nanoTime();
                        sophie(numbers);
                        endTime = System.nanoTime();
                        System.out.println("Max is: " + sophie(numbers));
                        System.out.println("Sophie's took " + (endTime - startTime) + " nanoseconds");
                        break;
                    case 4:
                        for (int i = 0; i < 20; i++)    // warming up
                            johnny(numbers, numbersLeft, numbersRight);

                        startTime = System.nanoTime();
                        johnny(numbers, numbersLeft, numbersRight);
                        endTime = System.nanoTime();
                        System.out.println("Max is: " + johnny(numbers, numbersLeft, numbersRight));
                        System.out.println("Johnny's took " + (endTime - startTime) + " nanoseconds");
                        break;
                    case 5:
                        for (int i = 0; i < 20; i++)    // warming up
                            sally(numbers);

                        startTime = System.nanoTime();
                        sally(numbers);
                        endTime = System.nanoTime();
                        System.out.println("Max is: " + sally(numbers));
                        System.out.println("Sally's took " + (endTime - startTime) + " nanoseconds");
                        break;
                    default:
                        System.out.println("Wrong input, please try again");
                }
            }
        }

    }
    public static int [] randomNumberGenerator(int s, int n){
        Random rand = new Random();
        rand.setSeed(s);
        int [] numbers = new int [n];
        for (int i = 0; i < numbers.length; i++)
            numbers[i] = rand.nextInt(100 - -100 + 1) + -100;   // (max - min + 1) + min
        return numbers;
    }



    // four subarray algorithms
    public static int freddy(int [] numbers){
        if (numbers.length == 0)
            return 0;

        int max = 0;
        for (int i = 0; i < numbers.length; i++){
            for (int j = i; j < numbers.length; j++){
                int thisSum = 0;
                for (int k = i; k <= j; k++)
                    thisSum += numbers[k];
                if (thisSum > max)
                    max = thisSum;
            }
        }

        return max;
    }

    public static int sophie(int [] numbers){
        if (numbers.length == 0)
            return 0;

        int max = 0;
        for (int i = 0; i< numbers.length; i++){
            int thisSum = 0;
            for (int j = i; j < numbers.length; j++){
                thisSum += numbers[j];
                if (thisSum > max)
                    max = thisSum;
            }
        }

        return max;
    }

    public static int johnny(int [] numbers, int left, int right){
        if (numbers.length == 0)
            return 0;

        if (left == right){
            if (numbers[left] > 0)
                return numbers[left];
            return 0;
        }

        //split the array in 2 and find each half's max sum
        int center = (left + right) / 2;
        int maxLeftSum = johnny(numbers, left, center);
        int maxRightSum = johnny(numbers, (center + 1), right);

        //find the max sum starting at center and moving left
        int maxLeftBorder = 0;
        int leftBorder = 0;
        for (int i = center; i >= left; i--){
            leftBorder += numbers[i];
            if (leftBorder > maxLeftBorder)
                maxLeftBorder = leftBorder;
        }

        //find the max starting at center and moving right
        int maxRightBorder = 0;
        int rightBorder = 0;
        for (int i = center + 1; i <= right; i++){
            rightBorder += numbers[i];
            if (rightBorder > maxRightBorder)
                maxRightBorder = rightBorder;
        }

        //the max sum is the largest of the three sums found
        return Math.max(Math.max(maxLeftSum, maxRightSum), (maxLeftBorder + maxRightBorder));
    }

    public static int sally(int [] numbers){
        if (numbers.length == 0)
            return 0;

        int max = 0;
        int thisSum = 0;
        for (int i = 0; i < numbers.length; i++){
            thisSum += numbers[i];
            if (thisSum > max)
                max = thisSum;
            else if (thisSum < 0)
                thisSum = 0;
        }

        return max;
    }



    // test main
    public static void test(){
        Random rand = new Random();

        System.out.println("\nan array of 10 values, some negative, some positive");
        int [] numbers = new int [10];
        for (int i = 0; i < numbers.length; i++){
            numbers[i] = rand.nextInt(5 - (-5) + 1) + (-5);   // (max - min + 1) + min
            System.out.printf("%d ", numbers[i]);
        }
        System.out.println();
        int numbersLeft = 0;
        int numbersRight = numbers.length - 1;

        System.out.println("Freddy: " + freddy(numbers));
        System.out.println("Sophie: " + sophie(numbers));
        System.out.println("Johnny: " + johnny(numbers, numbersLeft, numbersRight));
        System.out.println("Sally: " + sally(numbers));


        System.out.println("\nan array of 11 values, some negative, some positive");
        numbers = new int [11];
        for (int i = 0; i < numbers.length; i++){
            numbers[i] = rand.nextInt(6 - -5 + 1) + -5;
            System.out.printf("%d ", numbers[i]);
        }
        System.out.println();
        numbersLeft = 0;
        numbersRight = numbers.length - 1;

        System.out.println("Freddy: " + freddy(numbers));
        System.out.println("Sophie: " + sophie(numbers));
        System.out.println("Johnny: " + johnny(numbers, numbersLeft, numbersRight));
        System.out.println("Sally: " + sally(numbers));


        System.out.println("\nan array with no values");
        numbers = new int [0];

        System.out.println("Freddy: " + freddy(numbers));
        System.out.println("Sophie: " + sophie(numbers));
        System.out.println("Johnny: " + johnny(numbers, numbersLeft, numbersRight));
        System.out.println("Sally: " + sally(numbers));


        System.out.println("\nan array of 10 values, all negative.");
        numbers = new int [10];
        for (int i = 0; i < numbers.length; i++){
            numbers[i] = rand.nextInt(-1 - -10) + -10;
            System.out.printf("%d ", numbers[i]);
        }
        System.out.println();
        numbersLeft = 0;
        numbersRight = numbers.length - 1;

        System.out.println("Freddy: " + freddy(numbers));
        System.out.println("Sophie: " + sophie(numbers));
        System.out.println("Johnny: " + johnny(numbers, numbersLeft, numbersRight));
        System.out.println("Sally: " + sally(numbers));


        System.out.println("\nan array of 10 values, all 0");
        numbers = new int [10];
        for (int i = 0; i < numbers.length; i++){
            numbers[i] = 0;
            System.out.printf("%d ", numbers[i]);
        }
        System.out.println();
        numbersLeft = 0;
        numbersRight = numbers.length - 1;

        System.out.println("Freddy: " + freddy(numbers));
        System.out.println("Sophie: " + sophie(numbers));
        System.out.println("Johnny: " + johnny(numbers, numbersLeft, numbersRight));
        System.out.println("Sally: " + sally(numbers));


        System.out.println("\nan array of 10 values, all positive");
        numbers = new int [10];
        for (int i = 0; i < numbers.length; i++){
            numbers[i] = rand.nextInt(10 - 1 + 1) + 1;
            System.out.printf("%d ", numbers[i]);
        }
        System.out.println();
        numbersLeft = 0;
        numbersRight = numbers.length - 1;

        System.out.println("Freddy: " + freddy(numbers));
        System.out.println("Sophie: " + sophie(numbers));
        System.out.println("Johnny: " + johnny(numbers, numbersLeft, numbersRight));
        System.out.println("Sally: " + sally(numbers));
    }
}
