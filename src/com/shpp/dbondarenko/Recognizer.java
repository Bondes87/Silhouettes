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
 * Created by Dmitro Bondarenko on 27.05.2017.
 */
public class Recognizer {
    public int recognize(String imagePath) throws IOException {
        int[][] pixelsColor = readFile(imagePath);
        System.out.println(pixelsColor.length + " " + pixelsColor[0].length);
        ArrayList<Integer> silhouettes = searchSilhouettes(pixelsColor);
        return 0;
    }

    private int[][] readFile(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int[][] pixelsColor = new int[imageHeight][imageWidth];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                Color color = new Color(image.getRGB(j, i));
                pixelsColor[i][j] = color.getRed() <= 127 ? 0 : 255;
                //System.out.println(pixelsColor[i][j]);
            }
        }
        return pixelsColor;
    }

    private ArrayList<Integer> searchSilhouettes(int[][] pixelsColor) {
        ArrayList<Integer> silhouettes = new ArrayList<>();
        int attemptsNumber = pixelsColor.length / 25;
        while (attemptsNumber > 0) {
            int row = new Random().nextInt(pixelsColor.length);
            for (int i = 1; i < pixelsColor[row].length; i++) {
                if (pixelsColor[row][i] != pixelsColor[row][i - 1]) {
                    silhouettes.add(calculateSilhouetteArea(pixelsColor, row, i));
                }
            }
            attemptsNumber--;
        }
        System.out.println(silhouettes);
        return silhouettes;
    }

    private int calculateSilhouetteArea(int[][] pixelsColor, int row, int col) {
        int silhouetteArea = 0;
        ArrayList<int[]> motionCoordinates = new ArrayList<>();
        silhouetteArea++;
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
        System.out.println(silhouetteArea);
        return silhouetteArea;
    }
}
