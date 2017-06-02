package com.shpp.dbondarenko;

import java.io.IOException;

/**
 * File: Main.java
 * The program that looks for the silhouettes of people in the image.
 * The class that runs the program.
 * Created by Dmitro Bondarenko on 22.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        Recognizer recognizer = new Recognizer();
        int countSilhouettes;
        try {
            if (args.length == 0) {
                throw new Exception("No path to file specified.");
            } else {
                countSilhouettes = recognizer.recognize(args[0]);
                if (countSilhouettes == 1) {
                    System.out.println(countSilhouettes + " silhouette");
                } else {
                    System.out.println(countSilhouettes + " silhouettes");
                }
            }
        } catch (IOException e) {
            System.out.println("Image not found.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
