package org.springblade.common.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 图片裁剪工具类
 *
 */
public class ImageUtil {
    private static Logger log = LoggerFactory.getLogger(ImageUtil.class);


    public static BufferedImage getBufferedImage(byte[] imageData) {
        if (imageData == null || imageData.length == 0) {
            return null;
        }

        ByteArrayInputStream inStream = null;
        BufferedImage bi = null;
        try {
            inStream = new ByteArrayInputStream(imageData);
            bi = ImageIO.read(inStream);
        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                    inStream = null;
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        return bi;
    }

    public static byte[] cutByteImage(BufferedImage bi, int x, int y, int width, int height) throws Exception {
        byte[] newImageData = null;
        ByteArrayOutputStream outStream = null;
        try {
            // 读取源图像
            // inStream = new ByteArrayInputStream(imageData);
            // BufferedImage bi = ImageIO.read(inStream);
            int srcWidth = bi.getWidth();// 原图宽度
            int srcHeight = bi.getHeight();// 原图高度
            // 若原图大小大于大于切片大小，则进行切割
            if (srcWidth >= width && srcHeight >= height) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();
                outStream = new ByteArrayOutputStream();
                ImageIO.write(tag, "JPEG", outStream);
                newImageData = outStream.toByteArray();
                // test start
                // IOUtils.write(newImageData, new
                // FileOutputStream("f:\\223.jpg"));
                // test end
            } else {
                log.error("叠加图片切分长宽计算错误...");
            }
        } catch (IOException e) {
            log.error("", e);
            throw e;
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                    outStream = null;
                }
            } catch (Exception ex) {
                log.error("", ex);
                throw ex;
            }
        }
        return newImageData;
    }


    /**
     * java切图
     *
     * @param imgByte
     * @param tempx
     * @param tempy
     * @param tempw
     * @param temph
     * @return
     * @throws Exception
     */
    public static String javaCrop(byte[] imgByte, int tempx, int tempy, int tempw, int temph) throws Exception {
        BufferedImage bi = ImageUtil.getBufferedImage(imgByte);
        byte[] resultReduce;
        try {
            resultReduce = ImageUtil.cutByteImage(bi, tempx, tempy, tempw, temph);
            BASE64Encoder encoder = new BASE64Encoder();
            return "data:image/jpeg;base64,"+encoder.encode(resultReduce);
        } catch (Exception e1) {
            log.warn("java切图失败");
        }
        return null;
    }

}