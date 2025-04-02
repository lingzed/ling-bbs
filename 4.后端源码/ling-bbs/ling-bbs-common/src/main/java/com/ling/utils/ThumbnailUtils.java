package com.ling.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ThumbnailUtils {

    // 支持的图片格式映射
    private static final Map<String, String> SUPPORTED_FORMATS;

    static {
        Map<String, String> formats = new HashMap<>();
        formats.put("jpg", "JPEG");
        formats.put("jpeg", "JPEG");
        formats.put("png", "PNG");
        formats.put("bmp", "BMP");
        formats.put("wbmp", "WBMP");
        formats.put("gif", "GIF");
        SUPPORTED_FORMATS = Collections.unmodifiableMap(formats);
    }

    /**
     * 生成缩略图（保持宽高比）
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @param maxWidth   最大宽度
     * @param maxHeight  最大高度
     * @param quality    图片质量（0.0-1.0）
     */
    public static void generateThumbnail(File sourceFile, File targetFile,
                                         int maxWidth, int maxHeight, float quality)
            throws IOException {

        // 参数校验
        if (maxWidth <= 0 || maxHeight <= 0) {
            throw new IllegalArgumentException("Invalid dimension values");
        }
        if (quality < 0 || quality > 1) {
            throw new IllegalArgumentException("Quality must be between 0 and 1");
        }

        // 获取文件格式
        String formatName = getFormatName(targetFile.getName());
        if (!SUPPORTED_FORMATS.containsKey(formatName)) {
            throw new IllegalArgumentException("Unsupported image format");
        }

        // 读取原始图片
        BufferedImage sourceImage = ImageIO.read(sourceFile);
        if (sourceImage == null) {
            throw new IOException("Unsupported image type");
        }

        // 计算缩放比例
        double widthRatio = (double) maxWidth / sourceImage.getWidth();
        double heightRatio = (double) maxHeight / sourceImage.getHeight();
        double scale = Math.min(widthRatio, heightRatio);

        // 计算目标尺寸
        int targetWidth = (int) (sourceImage.getWidth() * scale);
        int targetHeight = (int) (sourceImage.getHeight() * scale);

        // 创建目标图片
        BufferedImage thumbnail = new BufferedImage(targetWidth, targetHeight,
                formatName.equalsIgnoreCase("png") ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);

        // 高质量缩放
        Graphics2D g = thumbnail.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // 处理透明背景（针对PNG）
            if (formatName.equalsIgnoreCase("png")) {
                g.setComposite(AlphaComposite.Src);
            } else {
                thumbnail = g.getDeviceConfiguration().createCompatibleImage(
                        targetWidth, targetHeight, Transparency.OPAQUE);
                g.dispose();
                g = thumbnail.createGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, targetWidth, targetHeight);
            }

            AffineTransform transform = AffineTransform.getScaleInstance(scale, scale);
            g.drawRenderedImage(sourceImage, transform);
        } finally {
            g.dispose();
        }

        // 写入文件
        ImageIO.write(thumbnail, SUPPORTED_FORMATS.get(formatName), targetFile);

        // 设置JPEG质量（如果需要）
        if (formatName.equalsIgnoreCase("jpg") || formatName.equalsIgnoreCase("jpeg")) {
            compressJPEG(thumbnail, targetFile, quality);
        }
    }

    private static String getFormatName(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1) return "";
        return filename.substring(dotIndex + 1).toLowerCase();
    }

    private static void compressJPEG(BufferedImage image, File outputFile, float quality)
            throws IOException {

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No JPEG writer found");
        }

        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), param);
        } finally {
            writer.dispose();
        }
    }

    // 方法重载
    public static void generateThumbnail(String sourcePath, String targetPath,
                                         int maxWidth, int maxHeight) throws IOException {
        generateThumbnail(new File(sourcePath), new File(targetPath),
                maxWidth, maxHeight, 0.9f);
    }

    public static void generateThumbnail(File sourceFile, File targetFile,
                                         double scaleRatio) throws IOException {
        BufferedImage sourceImage = ImageIO.read(sourceFile);
        int maxWidth = (int) (sourceImage.getWidth() * scaleRatio);
        int maxHeight = (int) (sourceImage.getHeight() * scaleRatio);
        generateThumbnail(sourceFile, targetFile, maxWidth, maxHeight, 0.9f);
    }
}