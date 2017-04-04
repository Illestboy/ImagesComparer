package com.illestboy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigInteger;

/**
 * Created by illestboy on 03.04.17.
 */
public class Comparer {

    public static BufferedImage scale(BufferedImage image) {
        final int SCALE_WIDTH = 8;
        final int SCALE_HEIGHT = 8;

        BufferedImage scaled = new BufferedImage(SCALE_WIDTH, SCALE_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();
        g.drawImage(image, 0, 0, SCALE_WIDTH, SCALE_HEIGHT, null);
        g.dispose();
        return scaled;
    }

    public static BufferedImage toGray(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color bitmapColor = new Color(image.getRGB(x, y));
                int colorGray = (int)(bitmapColor.getRed() * 0.299 +
                        bitmapColor.getGreen() * 0.587 + bitmapColor.getBlue() * 0.114);
                image.setRGB(x, y, new Color(colorGray, colorGray, colorGray).getRGB());
            }
        }
        return image;
    }

    public static int getAverage(BufferedImage image) {
        long all = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                all += new Color(image.getRGB(x, y)).getRed();
            }
        }
        return (int)all / (image.getHeight() * image.getWidth());
    }

    public static BigInteger getHash(BufferedImage image) {
        BufferedImage grayImage = toGray(scale(image));
        int avg = getAverage(grayImage);
        StringBuilder bits = new StringBuilder();
        for (int x = 0; x < grayImage.getWidth(); x++) {
            for (int y = 0; y < grayImage.getHeight(); y++) {
                bits.append((new Color(grayImage.getRGB(x, y)).getRed() > avg) ? "1" : "0");
            }
        }
        BigInteger hash = new BigInteger(bits.toString(), 2);
        return hash;
    }

    public static int getHammingDistance(BigInteger firstHash, BigInteger secondHash) {
        String firstBits = firstHash.toString(2);
        String secondBits = secondHash.toString(2);
        int minLength = Math.min(firstBits.length(), secondBits.length());
        int distance = 0;
        for (int i = 0; i < minLength; i++) {
            if (firstBits.charAt(i) != secondBits.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

}
