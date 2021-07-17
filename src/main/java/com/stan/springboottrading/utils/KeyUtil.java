package com.stan.springboottrading.utils;

import java.util.Random;

public class KeyUtil {
    public static synchronized String genUniqueKey(){

        Random random = new Random();
        Integer key = random.nextInt(900000)+100000;

        return System.currentTimeMillis() + String.valueOf(key);
    }
}
