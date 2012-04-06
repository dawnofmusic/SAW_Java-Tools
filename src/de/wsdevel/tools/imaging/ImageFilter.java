package de.wsdevel.tools.imaging;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Created on 06.10.2005.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.6 $ -- $Date: 2006-09-01 14:42:09 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public final class ImageFilter {

    /** The system graphic configuration. */
    private static final GraphicsConfiguration GC = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getDefaultScreenDevice()
            .getDefaultConfiguration();

    /**
     * COMMENT.
     *
     * @param orgImage 
     *          {@link Image}
     * @param newMaxWidth 
     *          <code>int</code>
     * @param newMaxHeight 
     *          <code>int</code> 
     * @return  {@link Image} 
     */
    public static Image getScaledImageCopy(final Image orgImage,
            final int newMaxWidth, final int newMaxHeight) {
        BufferedImage thumbnail = GC.createCompatibleImage(newMaxWidth,
                newMaxHeight, Transparency.TRANSLUCENT);
        Graphics2D g2d = thumbnail.createGraphics();
        setRenderingHints(g2d);
        g2d.drawImage(orgImage, 0, 0, newMaxWidth, newMaxHeight, null);
        g2d.dispose();
        return thumbnail;
    }

    /**
     * COMMENT.
     *
     * @param g2d {@link Graphics2D}
     */
    private static void setRenderingHints(final Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING,
                RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    }

    /**
     * COMMENT.
     *
     * @param image {@link BufferedImage}
     * @param widthScaleFactor <code>double</code>
     * @param heightScaleFactor <code>double</code>
     * @return {@link BufferedImage}
     */
    public static BufferedImage getScaledBufferedImageCopy(
            final BufferedImage image, final double widthScaleFactor,
            final double heightScaleFactor) {

        int destWidth = (int) (image.getWidth() * widthScaleFactor);
        int destHeight = (int) (image.getHeight() * heightScaleFactor);

        BufferedImage resizedImage = GC.createCompatibleImage(destWidth,
                destHeight, Transparency.TRANSLUCENT);
        Graphics2D g2d = resizedImage.createGraphics();
        setRenderingHints(g2d);
        g2d.drawImage(image, 0, 0, destWidth, destHeight, null);

        return resizedImage;
    }

    /**
     * @param imageToResize
     *            {@link File}
     * @param newMaxWidth
     *            <code>int</code>
     * @param newMaxHeight
     *            <code>int</code>
     * @return {@link BufferedImage} or <code>null</code> if image could not
     *         be resized!
     * @throws IOException
     *             if image could not be read!
     */
    public static Image resizeImage(final File imageToResize,
            final int newMaxWidth, final int newMaxHeight) throws IOException {

        BufferedImage orgImage = ImageIO.read(imageToResize);
        if (orgImage == null) {
            return null;
        }
        return getScaledImageCopy(orgImage, newMaxWidth, newMaxHeight);
    }

    /**
     * @param bi
     *            {@link BufferedImage}
     * @return <code>byte</code>[] or <code>null</code> if an error
     *         occurred!
     */
    public static byte[] writeBufferedImageAsJPGAsByteArray(
            final BufferedImage bi) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "jpg", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            return null;
        } finally {
            try {
                baos.close();
            } catch (IOException e1) {
                // forget it (sw)
            }
        }
    }

    /**
     * private constructor.
     */
    private ImageFilter() {
    }

    /**
     * COMMENT.
     *
     * @param image {@link BufferedImage}
     * @param maxDim <code>int</code>
     * @return <code>double</code>
     */
    public static double calcScaleForMaxDim(final BufferedImage image,
            final int maxDim) {
        double scale = 1;
        if (image.getWidth() > maxDim || image.getHeight() > maxDim) {
            scale = (double) maxDim / (double) image.getWidth();
            if (image.getHeight() * scale > maxDim) {
                scale = (double) maxDim / (double) image.getHeight();
            }
        }
        return scale;
    }

}
/*
 * $Log: ImageFilter.java,v $
 * Revision 1.6  2006-09-01 14:42:09  sweissTFH
 * added scaling functionality
 *
 * Revision 1.5  2006/06/10 17:57:30  sweissTFH
 * clean up
 *
 * Revision 1.4  2006/06/10 16:30:14  sweissTFH
 * clean up
 *
 * Revision 1.3  2006/06/02 12:18:50  sweissTFH
 * improvements
 *
 * Revision 1.2  2006/05/23 16:21:10  sweissTFH
 * ImageIconfactory now based upon BufferedImages
 *
 * Revision 1.1  2006/05/02 16:06:01  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.4  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * 
 * Revision 1.3 2005/10/26 16:56:23 mschneiderTFH start of very big clean
 * up and commenting! (sw)
 * 
 * Revision 1.2 2005/10/12 17:32:38 mschneiderTFH *** empty log message ***
 * 
 * Revision 1.1 2005/10/06 14:15:24 sweissTFH new
 * 
 */
