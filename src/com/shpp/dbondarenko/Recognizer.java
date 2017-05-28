package com.shpp.dbondarenko;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * File: Recognizer.java
 * The class that is responsible for the logic of the program to search for silhouettes.
 * Created by Dmitro Bondarenko on 27.05.2017.
 */
public class Recognizer {
    // The constant that is responsible for the ratio of the minimum and maximum silhouette.
    private static final int RATIO_OF_MINIMUM_TO_MAXIMUM_AREA_OF_SILHOUETTES = 7;
    // The constant which is responsible for the number of attempts to find the silhouettes.
    private static final int RATIO_OF_ATTEMPTS_TO_THE_HEIGHT_OF_PICTURE = 25;
    // The constant responsible for the numeric value of black color.
    private static final int BLACK = 0;
    // The constant responsible for the numeric value of white color.
    private static final int WHITE = 255;
    // The constant that corresponds to the boundary between black and white.
    private static final int BORDER_BETWEEN_WHITE_AND_BLACK = 127;

    /**
     * Recognize the number of silhouettes in the image.
     *
     * @param imagePath The path of Image.
     * @return Returns the number of silhouettes.
     */
    public int recognize(String imagePath) throws IOException {
        int[][] pixelsColor = readFile(imagePath);
        ArrayList<Integer> silhouettes = searchSilhouettes(pixelsColor);
        return analyzeSilhouettes(silhouettes);
    }

    /**
     * Read the image into an array of bytes.
     *
     * @param imagePath The path of Image.
     * @return Returns an array of bytes.
     */
    private int[][] readFile(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int[][] pixelsColor = new int[imageHeight][imageWidth];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                Color color = new Color(image.getRGB(j, i));
                pixelsColor[i][j] = color.getRed() <= BORDER_BETWEEN_WHITE_AND_BLACK ? BLACK : WHITE;
            }
        }
        return pixelsColor;
    }

    /**
     * Search silhouettes in the image.
     *
     * @param pixelsColor The array of bytes of the image.
     * @return Returns the list of areas of silhouettes.
     */
    private ArrayList<Integer> searchSilhouettes(int[][] pixelsColor) {
        ArrayList<Integer> silhouettes = new ArrayList<>();
        int attemptsNumber = pixelsColor.length / RATIO_OF_ATTEMPTS_TO_THE_HEIGHT_OF_PICTURE;
        while (attemptsNumber > 0) {
            int row = new Random().nextInt(pixelsColor.length);
            for (int i = 1; i < pixelsColor[row].length; i++) {
                if (pixelsColor[row][i] != pixelsColor[row][i - 1]) {
                    silhouettes.add(calculateSilhouetteArea(pixelsColor, row, i));
                }
            }
            attemptsNumber--;
        }
        return silhouettes;
    }

    /**
     * Calculate the area of the silhouette.
     *
     * @param pixelsColor The array of bytes of the image.
     * @param row         The line number.
     * @param col         The column number.
     * @return Returns the area of the silhouette.
     */
    private int calculateSilhouetteArea(int[][] pixelsColor, int row, int col) {
        int silhouetteArea = 1;
        ArrayList<int[]> motionCoordinates = new ArrayList<>();
        motionCoordinates.add(new int[]{row, col});
        pixelsColor[row][col] = pixelsColor[row][col - 1];
        while (!motionCoordinates.isEmpty()) {
            int y = motionCoordinates.get(0)[0];
            int x = motionCoordinates.get(0)[1];
            if (y != 0 && pixelsColor[y][x] != pixelsColor[y - 1][x]) {
                silhouetteArea++;
                motionCoordinates.add(new int[]{y - 1, x});
                pixelsColor[y - 1][x] = pixelsColor[y][x];
            }
            if (x != pixelsColor[0].length - 1 && pixelsColor[y][x] != pixelsColor[y][x + 1]) {
                silhouetteArea++;
                motionCoordinates.add(new int[]{y, x + 1});
                pixelsColor[y][x + 1] = pixelsColor[y][x];
            }
            if (y != pixelsColor.length - 1 && pixelsColor[y][x] != pixelsColor[y + 1][x]) {
                silhouetteArea++;
                motionCoordinates.add(new int[]{y + 1, x});
                pixelsColor[y + 1][x] = pixelsColor[y][x];
            }
            if (x != 0 && pixelsColor[y][x] != pixelsColor[y][x - 1]) {
                silhouetteArea++;
                motionCoordinates.add(new int[]{y, x - 1});
                pixelsColor[y][x - 1] = pixelsColor[y][x];
            }
            motionCoordinates.remove(0);
        }
        return silhouetteArea;
    }

    /**
     * Analyze the areas of silhouettes..
     *
     * @param silhouettes The list of areas of possible silhouettes.
     * @return Returns the number of silhouettes.
     */
    private int analyzeSilhouettes(ArrayList<Integer> silhouettes) {
        int silhouettesNumber = silhouettes.size();
        int maxSilhouetteArea = findMaxNumber(silhouettes);
        for (int silhouetteArea : silhouettes) {
            if (silhouetteArea < maxSilhouetteArea / RATIO_OF_MINIMUM_TO_MAXIMUM_AREA_OF_SILHOUETTES) {
                silhouettesNumber--;
            }
        }
        return silhouettesNumber;
    }

    /**
     * Search for the maximum number.
     *
     * @param numbers The list of numbers.
     * @return Returns the maximum number.
     */
    private int findMaxNumber(ArrayList<Integer> numbers) {
        int max = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            if (max < numbers.get(i)) {
                max = numbers.get(i);
            }
        }
        return max;
    }
}
