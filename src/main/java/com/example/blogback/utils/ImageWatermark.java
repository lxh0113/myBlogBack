package com.example.blogback.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWatermark {

    public static void setSY(String filepath, String mark)
    {
        try {
            File sourceImageFile = new File(filepath);
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);

            // 创建一个与源图片相同大小的新图片
            BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            // 将源图片绘制到新图片中
            Graphics2D graphics = outputImage.createGraphics();
            graphics.drawImage(sourceImage, 0, 0, null);

            // 添加水印
            Font font = new Font("Arial", Font.BOLD, 35); // 水印字体
            graphics.setFont(font);
            graphics.setColor(Color.WHITE); // 水印颜色
            graphics.drawString(mark, 10, 50); // 水印位置

            // 设置阴影颜色
            Color shadowColor = new Color(0, 0, 0, 128); // 半透明黑色阴影

            // 绘制阴影，偏移位置可以调整
            graphics.setColor(shadowColor);
            graphics.drawString(mark, 12, 52); // 阴影位置略微偏移

            // 绘制水印文字
            graphics.setColor(Color.WHITE); // 水印颜色
            graphics.drawString(mark, 10, 50); // 正常位置

            // 释放资源
            graphics.dispose();

            // 保存输出图片
            File outputImageFile = new File(filepath);
            ImageIO.write(outputImage, "jpg", outputImageFile);

            System.out.println("水印添加完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String sourceImagePath = "E:\\JDK\\blogBack\\src\\main\\java\\com\\example\\blogback\\utils\\11.jpg"; // 源图片路径
        String watermarkText = "Watermark"; // 水印文字
        String outputImagePath = "E:\\JDK\\blogBack\\src\\main\\java\\com\\example\\blogback\\utils\\11.jpg"; // 输出图片路径

        try {
            File sourceImageFile = new File(sourceImagePath);
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);

            // 创建一个与源图片相同大小的新图片
            BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            // 将源图片绘制到新图片中
            Graphics2D graphics = outputImage.createGraphics();
            graphics.drawImage(sourceImage, 0, 0, null);

            // 设置水印的字体和颜色
            Font font = new Font("Arial", Font.BOLD, 36); // 水印字体
            graphics.setFont(font);
            graphics.setColor(Color.WHITE); // 水印颜色

            // 设置阴影颜色
            Color shadowColor = new Color(0, 0, 0, 128); // 半透明黑色阴影

            // 绘制阴影，偏移位置可以调整
            graphics.setColor(shadowColor);
            graphics.drawString(watermarkText, 12, 52); // 阴影位置略微偏移

            // 绘制水印文字
            graphics.setColor(Color.WHITE); // 水印颜色
            graphics.drawString(watermarkText, 10, 50); // 正常位置

            // 释放资源
            graphics.dispose();

            // 保存输出图片
            File outputImageFile = new File(outputImagePath);
            ImageIO.write(outputImage, "jpg", outputImageFile);

            System.out.println("水印添加完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}