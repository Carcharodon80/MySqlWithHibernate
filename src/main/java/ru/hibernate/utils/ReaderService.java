package ru.hibernate.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReaderService {
    private static BufferedReader reader;

    public static void openReader(){
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static String inputString(String message){
        String result = "";

        System.out.println(message);
        try {
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void closeReader(){
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
