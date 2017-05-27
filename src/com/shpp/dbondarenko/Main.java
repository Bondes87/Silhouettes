package com.shpp.dbondarenko;

import java.io.IOException;

/**
 * File: Main.java
 * Created by Dmitro Bondarenko on 22.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        Recognizer recognizer = new Recognizer();
        try {
            recognizer.recognize("man.jpg");
        } catch (IOException e) {
            System.out.println("Image not found.");
        }
    }
}
