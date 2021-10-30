package edu.school21.printer.logic;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;

public class ImagesToChar {
    public char[][] createImageArray(BufferedImage img, String black, String white) {
        int         height = img.getHeight();
        int         width = img.getWidth();
        char[][]    imageArray = new char[height][width];
        int         color;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                color = img.getRGB(x, y);
                
                if (color == -1) {
                    imageArray[y][x] = white.toCharArray()[0];
                } else {
                    imageArray[y][x] = black.toCharArray()[0];
                }
            }
        }
        return imageArray;
    }
}