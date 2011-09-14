package com.googlecode.coss.common.utils.lang;

import java.util.Random;
import java.util.UUID;

/**
 * <p>
 * Random Operation
 * </p>
 */
public class RandomUtils {

    private static String allNumericChars = "0123456789";
    private static String allLetterChars  = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String allChars        = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * <p>
     * Generate a new UUID String
     * </p>
     * 
     * @return UUID String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * <p>
     * Generate a new UUID String of UpperCase
     * </p>
     * 
     * @return UUID String of UpperCase
     */
    public static String getUUIDUpperCase() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    /**
     * <p>
     * Generate a positive Integer number for appointing minNum, maxNum &
     * baseNum
     * </p>
     * 
     * @param minNum the min number
     * @param maxNum the max number
     * @param baseNum the baseNumber etc.16 the result can be 0 16 32 48 ...
     * @return
     */
    public static int randomIntPositive(int minNum, int maxNum, int baseNum) {
        if (minNum < 0) {
            throw new IllegalArgumentException("minNum must bigger than ZERO");
        } else if (minNum > maxNum) {
            throw new IllegalArgumentException("maxNum must bigger than minNum");
        } else if (baseNum <= 0) {
            throw new IllegalArgumentException("baseNum must bigger than ZERO");
        } else if (baseNum > 0 && maxNum < baseNum) {
            throw new IllegalArgumentException("Illegal maxNum");
        } else if (maxNum == baseNum && minNum > 0) {
            return maxNum;
        }
        Random random = new Random();// use System.currentMillSeconds();
        int i = random.nextInt();
        if (i == Integer.MIN_VALUE) {
            i = Integer.MAX_VALUE;
        }
        return (minNum + (Math.abs(i) % (maxNum - minNum + 1))) / baseNum * baseNum;
    }

    /**
     * <p>
     * Generate a positive Integer number for appointing minNum & maxNum
     * </p>
     * 
     * @param minNum the min number
     * @param maxNum the max number
     * @return
     */
    public static int randomIntPositive(int minNum, int maxNum) {
        if (minNum < 0) {
            throw new IllegalArgumentException("minNum must bigger than ZERO");
        } else if (minNum > maxNum) {
            throw new IllegalArgumentException("maxNum must bigger than minNum");
        }
        Random random = new Random();// use System.currentMillSeconds();
        int i = random.nextInt();
        if (i == Integer.MIN_VALUE) {
            i = Integer.MAX_VALUE;
        }
        return minNum + (Math.abs(i) % (maxNum - minNum + 1));
    }

    /**
     * <p>
     * Generate a positive Integer number for appointing maxNum
     * </p>
     * 
     * @param maxNum the max number
     * @return
     */
    public static int randomIntPositive(int maxNum) {
        if (maxNum < 0) {
            throw new IllegalArgumentException("maxNum must bigger than ZERO");
        }
        Random random = new Random();// use System.currentMillSeconds();
        int i = random.nextInt();
        if (i == Integer.MIN_VALUE) {
            i = Integer.MAX_VALUE;
        }
        return Math.abs(i) % (maxNum + 1);
    }

    /**
     * <p>
     * Creates a random string whose length is the number of characters
     * specified.
     * </p>
     * <p>
     * Characters will be chosen from the set of alphabetic characters.
     * </p>
     * 
     * @param length the length of random string to create
     * @return the random string
     */
    public static String randomAlphabetic(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allLetterChars.charAt(random.nextInt(allLetterChars.length())));
        }
        return sb.toString();
    }

    /**
     * <p>
     * Creates a random string whose length is the numeric of characters
     * specified.
     * </p>
     * <p>
     * Characters will be chosen from the set of numeric characters.
     * </p>
     * 
     * @param length the length of random string to create
     * @return the random string
     */
    public static String randomNumeric(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allNumericChars.charAt(random.nextInt(allNumericChars.length())));
        }
        return sb.toString();
    }

    /**
     * <p>
     * Creates a random string whose length is the all of characters specified.
     * </p>
     * <p>
     * Characters will be chosen from the set of all characters.
     * </p>
     * 
     * @param length the length of random string to create
     * @return the random string
     */
    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        return sb.toString();
    }

    /**
     * <p>
     * Get random result from result list by appointing percentages
     * </p>
     * 
     * @param results All results Enum
     * @param percentages All pectntage note. the totol must be 1
     * @return
     */
    public static Object random(Object[] results, double[] percentages) {
        if (results.length != percentages.length) {
            throw new IllegalArgumentException(
                    "retults number and percentages number are not match");
        }
        // percentages double array to int array increased step by step
        // double array: 0.1 0.2 0.3 0.4
        // int array: 1000 3000 6000 10000
        int p = 0;
        int[] points = new int[percentages.length];
        for (int i = 0; i < percentages.length; i++) {
            p += (int) (10000 * percentages[i]);
            points[i] = p;
        }
        if (p != 10000) {
            throw new IllegalArgumentException("Total percentages must be One");
        }

        Random random = new Random();
        int c = Math.abs(random.nextInt() % 10000);
        int pre = 0;
        // etc c=2000 1000<c<3000
        for (int i = 0; i < points.length; i++) {
            if (c < points[i] && c >= pre) {
                return results[i];
            }
            pre = points[i];
        }
        throw new RuntimeException("error happens,please check percentages parameters");
    }
}
