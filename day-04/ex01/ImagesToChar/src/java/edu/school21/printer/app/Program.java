package edu.school21.printer.app;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import edu.school21.printer.logic.ImagesToChar;
import java.net.URL;

class Program {
    private static String[] checkArgs(String[] args) {
        if (args.length != 2) {
            System.err.print("Wrong number of arguments. ");
            System.err.println("Example: java programName '0' '.'");
            System.exit(1);
        }

        if ((args[0].length() > 1) || (args[1].length() > 1)) {
            System.err.println("Invalid argument(s). Expected 2 chars");
            System.exit(1);
        }
        return args;
    }

    private static void printImageArray(BufferedImage img, char[][] imageArray) {
        int height = img.getHeight();
        int width = img.getWidth();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(imageArray[y][x]);
            }
            System.out.print("\n");
        }
    }

    private static BufferedImage getImageFromFile(ImagesToChar imagesToChar) {
        BufferedImage   image;
        InputStream     is;
        URL             imagePath;
        
        imagePath = imagesToChar.getImagePath("/resources/image.bmp");
        
        try {
            image = ImageIO.read(imagePath);
            return image;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    };

    public static void main(String[] args) {
        ImagesToChar    imagesToChar = new ImagesToChar();
        BufferedImage   image;
        String[]        data;
        char[][]        imageArray;

        data = checkArgs(args);
        image = getImageFromFile(imagesToChar);
        imageArray = imagesToChar.createImageArray(image, args[0], args[1]);
        printImageArray(image, imageArray);
    }
}