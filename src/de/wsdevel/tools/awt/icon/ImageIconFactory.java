package de.wsdevel.tools.awt.icon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created on 25.06.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: ischmidt $ -- $Revision: 1.11 $ -- $Date: 2005/10/31
 *          18:22:30 $ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class ImageIconFactory {

    /** {@link HashMap} used as data base for created image icons. */
    private static HashMap<Dimension, HashMap<Color, ImageIcon>> dim2map = new HashMap<Dimension, HashMap<Color, ImageIcon>>();

    /** The system graphic configuration. */
    private static final GraphicsConfiguration GC = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getDefaultScreenDevice()
            .getDefaultConfiguration();

    /** Log4j. */
    private static final Log LOG = LogFactory.getLog(ImageIconFactory.class);

    /** Dimension 12 times 12 pixels. */
    public static final Dimension SIZE_12_X_12 = new Dimension(12, 12);

    /** Dimension 16 times 16 pixels. */
    public static final Dimension SIZE_16_X_16 = new Dimension(16, 16);

    /** Dimension 24 times 24 pixels. */
    public static final Dimension SIZE_24_X_24 = new Dimension(24, 24);

    /** Dimension 32 times 32 pixels. */
    public static final Dimension SIZE_32_X_32 = new Dimension(32, 32);

    /** Dimension 48 times 48 pixels. */
    public static final Dimension SIZE_48_X_48 = new Dimension(48, 48);

    /** Dimension 48 times 48 pixels. */
    public static final Dimension SIZE_64_X_64 = new Dimension(64, 64);

    /** Dimension 8 times 8 pixels. */
    public static final Dimension SIZE_8_X_8 = new Dimension(8, 8);

    static {
        dim2map.put(SIZE_8_X_8, new HashMap<Color, ImageIcon>());
        dim2map.put(SIZE_12_X_12, new HashMap<Color, ImageIcon>());
        dim2map.put(SIZE_16_X_16, new HashMap<Color, ImageIcon>());
        dim2map.put(SIZE_24_X_24, new HashMap<Color, ImageIcon>());
        dim2map.put(SIZE_32_X_32, new HashMap<Color, ImageIcon>());
        dim2map.put(SIZE_48_X_48, new HashMap<Color, ImageIcon>());
        dim2map.put(SIZE_64_X_64, new HashMap<Color, ImageIcon>());
    }

    /**
     * {@link BufferedImage} COMMENT.
     */
    // Has to be declared directly before ERROR_ICON! (sw)
    public static final BufferedImage ERROR_BUFFERED_IMAGE = createErrorBufferedImage();

    /**
     * Default icon that can be used for error handling.
     */
    // Has to be declared at last position! (sw)
    public static final ImageIcon ERROR_ICON = createErrorIcon();

    /**
     * Factory method for ImagIcon for a given path name.
     * 
     * @param iconInclasspath
     *            {@link String} containing the path in classpath to the
     *            resource of the wanted icon
     * @return {@link BufferedImage}
     */
    public static BufferedImage createBufferedImage(final String iconInclasspath) {
        URL url = ClassLoader.getSystemResource(iconInclasspath);
        if (url == null) {
            LOG.error("Resource for image icon could not be found! "
                    + iconInclasspath);
            // return default error icon
            return ImageIconFactory.ERROR_BUFFERED_IMAGE;
        }
        try {
            BufferedImage image = ImageIO.read(url);

            int transparency = image.getColorModel().getTransparency();

            BufferedImage gcCompatibleImage = ImageIconFactory.GC
                    .createCompatibleImage(image.getWidth(), image.getHeight(),
                            transparency);

            Graphics2D g2d = gcCompatibleImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();

            return gcCompatibleImage;
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), LOG.isDebugEnabled() ? e : null);
            return ImageIconFactory.ERROR_BUFFERED_IMAGE;
        }
    }

    /**
     * COMMENT.
     * 
     * @return {@link BufferedImage}
     */
    private static BufferedImage createErrorBufferedImage() {
        BufferedImage image = ImageIconFactory.GC.createCompatibleImage(
                SIZE_16_X_16.width, SIZE_16_X_16.height);

        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.RED);
        g2d.drawLine(0, 0, SIZE_16_X_16.width, SIZE_16_X_16.height);
        g2d.dispose();

        return image;
    }

    /**
     * @return {@link ImageIcon} an standard icon that can be used if an error
     *         at creation of foreign icon occurs
     */
    private static ImageIcon createErrorIcon() {
        return new ImageIcon(ImageIconFactory.ERROR_BUFFERED_IMAGE);
    }

    /**
     * @param size
     *            {@link Dimension}
     * @param color
     *            {@link Color}
     * @return {@link BufferedImage}
     */
    public static BufferedImage createImage(final Dimension size,
            final Color color) {
        BufferedImage image = ImageIconFactory.GC.createCompatibleImage(
                size.width, size.height, Transparency.TRANSLUCENT);

        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, size.width, size.height);
        g2d.dispose();

        return image;
    }

    /**
     * Factory method for ImagIcon for a given path name.
     * 
     * @param iconInclasspath
     *            {@link String} containing the path in classpath to the
     *            resource of the wanted icon
     * @return {@link ImageIcon}
     */
    public static ImageIcon createImageIcon(final String iconInclasspath) {
        URL url = ClassLoader.getSystemResource(iconInclasspath);
        if (url == null) {
            LOG.error("Resource for image icon could not be found! "
                    + iconInclasspath);
            // return default error icon
            return ERROR_ICON;
        }
        return new ImageIcon(url);
    }

    /**
     * @param size
     *            {@link Dimension}
     * @param color
     *            {@link Color}
     * @return {@link ImageIcon}
     */
    public static ImageIcon getImageIcon(final Dimension size, final Color color) {
        Object o = dim2map.get(size).get(color);
        if (o == null) {
            ImageIcon ico = new ImageIcon(createImage(size, color));
            dim2map.get(size).put(color, ico);
            return ico;
        }
        return (ImageIcon) o;
    }

    /**
     * hidden constructor.
     */
    private ImageIconFactory() {
    }

}
/*
 * $Log: ImageIconFactory.java,v $
 * Revision 1.11  2007-08-06 17:33:39  ischmidt
 * switched to commmons logging
 * Revision 1.10 2006/08/30 16:37:10 sweissTFH
 * added dummy image for liquidea info panel
 * 
 * Revision 1.9 2006/08/23 16:30:17 sweissTFH fixed transparency bug
 * 
 * Revision 1.8 2006/07/21 18:04:50 sweissTFH applet shows images as well
 * 
 * Revision 1.7 2006/06/02 12:06:30 sweissTFH *** empty log message ***
 * 
 * Revision 1.6 2006/06/02 12:04:30 sweissTFH bug fixing
 * 
 * Revision 1.5 2006/06/02 12:03:54 sweissTFH bug fixing
 * 
 * Revision 1.4 2006/06/02 11:56:17 sweissTFH improvements
 * 
 * Revision 1.3 2006/05/26 22:09:37 sweissTFH small cleanup
 * 
 * Revision 1.2 2006/05/23 16:21:10 sweissTFH ImageIconfactory now based upon
 * BufferedImages
 * 
 * Revision 1.1 2006/05/02 16:06:01 sweissTFH cleaned up tools and moved
 * everything to appropriate new packages
 * 
 * Revision 1.5 2006/04/05 18:19:35 sweissTFH cleaned up checkstyle errors
 * 
 * Revision 1.4 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean
 * up!
 * 
 * Revision 1.3 2005/10/31 18:22:30 sweissTFH clean up and commenting Revision
 * 1.2 2005/10/25 14:56:44 mschneiderTFH commenting
 * 
 */
