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
            if (args.length == 0) {
                throw new Exception("No path to file specified.");
            } else {
                System.out.println(recognizer.recognize(args[0]));
            }
        } catch (IOException e) {
            System.out.println("Image not found.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
