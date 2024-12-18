package com.ling.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class ImageCode {
    private Integer width = 160;    // 图片宽度
    private Integer height = 40;    // 图片高度
    private Integer codeCount = 4;  // 验证码字符个数
    private Integer lineCount = 4;  // 验证码干扰线个数
    private String code;            // 验证码
    private BufferedImage bImg;     // 验证码图片缓存
    Random random = new Random();

    public ImageCode() {
        createImage();
    }

    public ImageCode(Integer width, Integer height) {
        this.width = width;
        this.height = height;
        createImage();
    }

    public ImageCode(Integer width, Integer height, Integer codeCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        createImage();
    }

    public ImageCode(Integer width, Integer height, Integer codeCount, Integer lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        createImage();
    }

    private void createImage() {
        int fontWidth = width / codeCount;  //字体宽度
        int fontHeight = height - 5;        //字体宽度
        int codeY = height - 8;

        // 图像缓存
        bImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bImg.getGraphics();
        // 设置背景颜色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 设置字体
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        g.setFont(font);

        // 设置干扰线
        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(height);
            int ye = ys + random.nextInt(height);
            g.setColor(getRandColor(1, 255));
            g.drawLine(xs, ys, xe, ye);
        }

        // 添加噪点
        float noiseRate = 0.01f;     // 噪声率
        int area = (int) (noiseRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            bImg.setRGB(x, y, random.nextInt(255));
        }

        String rs = randomStr(codeCount);   // 生成随机字符
        this.code = rs;
        for (int i = 0; i < codeCount; i++) {
            String strRand = rs.substring(i, i + 1);
            g.setColor(getRandColor(1, 255));
            /*
             * g.drawString(a,x,y),a为要画出来的东西，x和y表示画出的东西最左侧字符的基线位于此图形上下文坐标系的(x,y)位置处
             * */
            g.drawString(strRand, i * fontWidth + 3, codeY);
        }
    }

    // 生成随机字符
    private String randomStr(int n) {
        String baseStr = "ABCDEFGHIGKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        String rs = "";
        int len = baseStr.length() - 1;
        double r;
        for (int i = 0; i < n; i++) {
            r = (Math.random()) * len;
            rs = rs + baseStr.charAt((int) r);
        }
        return rs;
    }

    // 生成随机颜色
    private Color getRandColor(int fc, int bc) {
        fc = Math.min(fc, 255);
        bc = Math.min(bc, 255);
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    // 生成随机字体
    private Font getFont(int size) {
        Font[] fonts = new Font[5];
        fonts[0] = new Font("Ravie", Font.PLAIN, size);
        fonts[1] = new Font("Antique Olive Compact", Font.PLAIN, size);
        fonts[2] = new Font("Fixedsys", Font.PLAIN, size);
        fonts[3] = new Font("Wide Latin", Font.PLAIN, size);
        fonts[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, size);
        return fonts[random.nextInt(5)];
    }

    private void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private void shearX(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(2);
        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);
        for (int i = 0; i < h1; i++) {
            double d = ((double) (period >> 1) * Math.sin((double) i / (double) period + (6.283185307179862D * (double) phase))) / frames;
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    private void shearY(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(40) + 10;   //50
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = ((double) (period >> 1) * Math.sin((double) i / (double) period + (6.283185307179862D * (double) phase))) / frames;
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    public void write(OutputStream os) throws IOException {
        ImageIO.write(bImg, "png", os);
        os.close();
    }

    public BufferedImage getBuffImg() {
        return bImg;
    }

    public String getCode() {
        return code.toLowerCase();
    }
}