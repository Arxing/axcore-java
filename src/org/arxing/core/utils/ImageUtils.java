package org.arxing.core.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

    public static BufferedImage readBuffer(File file) throws IOException {
        return ImageIO.read(file);
    }

    public static void resizeAndSave(File inputFile, File outputFile, String format, int width, int height) throws IOException {
        BufferedImage bufferedInput = ImageIO.read(inputFile);
        BufferedImage bufferedOutput = resize(bufferedInput, width, height);
        ImageIO.write(bufferedOutput, format, outputFile);
    }

    public static BufferedImage resize(String imgPath, int width, int height) throws IOException {
        return resize(ImageIO.read(new File(imgPath)), width, height);
    }

    public static BufferedImage resize(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
