package com.cms.card.utils;

import java.util.Random;

/**
 * @author Divakar Verma
 * @created_at : 22/02/2024 - 1:02 pm
 * @mail_to: vermadivakar2022@gmail.com
 */
public class Util {

    public static String getRandomCardNumber(){
        String cardNumber ="";
        Random random = new Random();
        int count = 0;
        for(int i=0;i<16;i++){
            int temp = random.nextInt(10);
            count++;
            cardNumber = cardNumber+ temp;
            if(count>3 && i !=15){
                cardNumber = cardNumber+"-";
                count = 0;
            }
        }
        return cardNumber;
    }

    public static String getRandomCardId(){
        StringBuilder cardId = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<5;i++){
            int temp = random.nextInt(10);
            cardId.append(temp);
        }
        return cardId.toString();
    }

    public static String getRandomCVV(){
        String cvv = "";
        Random random = new Random();
        for(int i=0;i<3;i++){
            int temp = random.nextInt(10);
            cvv = cvv+temp;
        }
        return cvv;
    }
}
