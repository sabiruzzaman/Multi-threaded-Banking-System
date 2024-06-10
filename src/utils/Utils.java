package utils;

import java.util.Scanner;

public class Utils {

    private static final Scanner sc = new Scanner(System.in);

    public static String readLine() {
        return sc.nextLine();
    }

    public static void print(Object o) {
        System.out.print(o);
    }

    public static void println(Object o) {
        System.out.println(o);
    }

    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid double.");
            }
        }
    }

    public static String transactionType(int transactionType) {
        String result = null;
        if (transactionType == 1) {
            result = "DEPOSIT";
        } else if (transactionType == 2) {
            result = "WITHDRAWAL";
        } else if (transactionType == 3) {
            result = "TRANSFER";
        }
        return result;
    }

}
