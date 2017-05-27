package com.shpp.dbondarenko;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * File: Recognizer.java
 * Created by Dmitro Bondarenko on 27.05.2017.
 */
public class Recognizer {
    public int recognize(String imagePath) throws IOException {
        int[][] pixelsColor = readFile(imagePath);
        return 0;
    }

    private int[][] readFile(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int[][] pixelsColor = new int[imageWidth][imageHeight];
        for (int i = 0; i < imageWidth; i++)
            for (int j = 0; j < imageHeight; j++) {
                Color color = new Color(image.getRGB(i, j));
                pixelsColor[i][j] = color.getRed();
            }
        return pixelsColor;
    }
}
