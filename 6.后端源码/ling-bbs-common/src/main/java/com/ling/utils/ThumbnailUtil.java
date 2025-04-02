package com.ling.utils;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 缩略图工具类
 */
public class ThumbnailUtil {

    /**
     * 根据指定的宽高生成缩略图（源图片为 String 路径）
     *
     * @param srcPath  源图片路径
     * @param destPath 缩略图保存路径（文件后缀决定图片格式，如 jpg、png 等）
     * @param width    缩略图宽度
     * @param height   缩略图高度
     * @throws IOException 如果读取或写入图片出错，则抛出异常
     */
    public static void createThumbnail(String srcPath, String destPath, int width, int height) throws IOException {
        createThumbnail(new File(srcPath), destPath, width, height);
    }

    /**
     * 根据指定的宽高生成缩略图（源图片为 File 对象）
     *
     * @param srcFile  源图片文件
     * @param destPath 缩略图保存路径（文件后缀决定图片格式，如 jpg、png 等）
     * @param width    缩略图宽度
     * @param height   缩略图高度
     * @throws IOException 如果读取或写入图片出错，则抛出异常
     */
    public static void createThumbnail(File srcFile, String destPath, int width, int height) throws IOException {
        BufferedImage srcImg = ImageIO.read(srcFile);
        if (srcImg == null) {
            throw new IOException("无法读取源图片，请检查文件路径或文件格式是否正确");
        }
        int originalWidth = srcImg.getWidth();
        int originalHeight = srcImg.getHeight();

        if (originalWidth <= width && originalHeight <= height) {
            ImageIO.write(srcImg, getFormatName(destPath), new File(destPath));
            return;
        }
        double scaleWidth = (double) width / originalWidth;
        double scaleHeight = (double) height / originalHeight;
        double scale = Math.min(scaleWidth, scaleHeight);
        int newWidth = (int) Math.round(originalWidth * scale);
        int newHeight = (int) Math.round(originalHeight * scale);
        createThumbnail(srcImg, destPath, newWidth, newHeight);
    }

    /**
     * 根据缩放比例生成缩略图（保持原图比例，源图片为 String 路径）
     *
     * @param srcPath  源图片路径
     * @param destPath 缩略图保存路径
     * @param scale    缩放比例（例如 0.5 表示缩小 50%）
     * @throws IOException 如果读取或写入图片出错，则抛出异常
     */
    public static void createThumbnail(String srcPath, String destPath, double scale) throws IOException {
        createThumbnail(new File(srcPath), destPath, scale);
    }

    /**
     * 根据缩放比例生成缩略图（保持原图比例，源图片为 File 对象）
     *
     * @param srcFile  源图片文件
     * @param destPath 缩略图保存路径
     * @param scale    缩放比例（例如 0.5 表示缩小 50%）
     * @throws IOException 如果读取或写入图片出错，则抛出异常
     */
    public static void createThumbnail(File srcFile, String destPath, double scale) throws IOException {
        BufferedImage srcImg = ImageIO.read(srcFile);
        if (srcImg == null) {
            throw new IOException("无法读取源图片，请检查文件路径或文件格式是否正确");
        }
        int width = (int) (srcImg.getWidth() * scale);
        int height = (int) (srcImg.getHeight() * scale);
        createThumbnail(srcImg, destPath, width, height);
    }

    /**
     * 根据最大宽度生成缩略图（保持原图比例，源图片为 String 路径）
     * 如果原图宽度小于等于 maxWidth，则直接保存原图
     *
     * @param srcPath  源图片路径
     * @param destPath 缩略图保存路径
     * @param maxWidth 最大宽度
     * @throws IOException 如果读取或写入图片出错，则抛出异常
     */
    public static void createThumbnail(String srcPath, String destPath, int maxWidth) throws IOException {
        createThumbnail(new File(srcPath), destPath, maxWidth);
    }

    /**
     * 根据最大宽度生成缩略图（保持原图比例，源图片为 File 对象）
     * 如果原图宽度小于等于 maxWidth，则直接保存原图
     *
     * @param srcFile  源图片文件
     * @param destPath 缩略图保存路径
     * @param maxWidth 最大宽度
     * @throws IOException 如果读取或写入图片出错，则抛出异常
     */
    public static void createThumbnail(File srcFile, String destPath, int maxWidth) throws IOException {
        BufferedImage srcImg = ImageIO.read(srcFile);
        if (srcImg == null) {
            throw new IOException("无法读取源图片，请检查文件路径或文件格式是否正确");
        }
        int originalWidth = srcImg.getWidth();
        int originalHeight = srcImg.getHeight();

        if (originalWidth <= maxWidth) {
            // 如果原图宽度不大于 maxWidth，则直接写入原图
            ImageIO.write(srcImg, getFormatName(destPath), new File(destPath));
            return;
        }
        double scale = (double) maxWidth / originalWidth;
        int newWidth = maxWidth;
        int newHeight = (int) (originalHeight * scale);
        createThumbnail(srcImg, destPath, newWidth, newHeight);
    }

    /**
     * 私有方法，根据 BufferedImage 生成缩略图
     *
     * @param srcImg   源图片对象
     * @param destPath 缩略图保存路径
     * @param width    缩略图宽度
     * @param height   缩略图高度
     * @throws IOException 如果写入图片出错，则抛出异常
     */
    private static void createThumbnail(BufferedImage srcImg, String destPath, int width, int height) throws IOException {
        BufferedImage destImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = destImg.createGraphics();
        // 设置高质量渲染参数
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 绘制缩放后的图像
        g2d.drawImage(srcImg, 0, 0, width, height, null);
        g2d.dispose();
        String formatName = getFormatName(destPath);
        ImageIO.write(destImg, formatName, new File(destPath));
    }

    /**
     * 从文件名中获取图片格式
     *
     * @param filename 文件名
     * @return 图片格式（不含点号），如 "jpg" 或 "png"
     */
    private static String getFormatName(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1);
        }
        return "jpg"; // 默认格式
    }
}
