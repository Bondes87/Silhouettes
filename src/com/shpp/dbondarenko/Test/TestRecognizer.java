package com.shpp.dbondarenko.Test;

import com.shpp.dbondarenko.Recognizer;

/**
 * File: TestRecognizer.java
 * Created by Dmitro Bondarenko on 28.05.2017.
 */
public class TestRecognizer {
    public static void main(String[] args) {
        Recognizer recognizer = new Recognizer();
        String[] pathsToImages = new String[]{
                "man.jpg",
                "man2.jpg",
                "1w.jpg",
                "2.jpg",
                "2w.jpg",
                "3w.jpg",
                "4.jpg",
                "5.jpg",
                "6.jpg",
                "6.png",
                "7.jpg",
                "7w.jpg",
                "8.jpg",
                "11.jpg",
                "12.gif",
                "15.jpg",
                "25.jpg",
                "34.jpg"
        };
        int[] silhouettesNumbers = new int[]{
                1,
                1,
                1,
                2,
                2,
                3,
                4,
                5,
                6,
                6,
                7,
                7,
                8,
                11,
                12,
                15,
                25,
                34
        };
        for (int i = 0; i < pathsToImages.length; i++) {
            try {
                System.out.println((i + 1) + ": " + pathsToImages[i] +
                        ": Expected result " +
                        silhouettesNumbers[i] + ", received " +
                        recognizer.recognize(pathsToImages[i]));
            } catch (Exception e) {
                System.out.println((i + 1) + ": " + "Image not found.");
            }
        }
    }
}
