package com.datayes.cloud.util;

import java.util.Date;
import java.util.Random;

public class CommonUtil {
    
    private static Random random;
    
    static{
        random = new Random(new Date().getTime());
    }
    
    public static String generatePassword(int length){
        String password = "";
        
        for (int i=0;i<length;i++){
            //from 33(!) to 126(~)
            password = password + (char)(random.nextInt(94)+33);
        }
        return password;
    }

    public static String getRandomIP() {
        return String.format("%d.%d.%d.%d", random.nextInt(256), random.nextInt(256), random.nextInt(256), random.nextInt(256));
        
    }

    public static long getRandom() {
        return random.nextLong();
    }
}
