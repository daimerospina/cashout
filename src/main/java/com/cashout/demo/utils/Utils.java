package com.cashout.demo.utils;

import java.util.Random;

public class Utils {

    public static String generateRandomValue(){
        Random random = new Random();
        return Integer.toString(random.nextInt(1000));
    }

    public static String randomPaymentResponse(){
        Random random = new Random();
        int randomIndex = random.nextInt(2);
        return randomIndex == 0 ? "Approved" : "Rejected";
    }
}
