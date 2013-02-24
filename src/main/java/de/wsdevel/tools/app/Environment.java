package de.wsdevel.tools.app;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.wsdevel.tools.awt.applet.AppletHelper;
import de.wsdevel.tools.awt.icon.ImageIconFactory;

/**
 * Created on 27.06.2007.
 * 
 * for project: Java__Liquifire
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author$ -- $Revision$ -- $Date$
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public final class Environment {

    /**
     * {@link Log} COMMENT.
     */
    private static final Log LOG = LogFactory.getLog(Environment.class);

    /**
     * {@link JApplet} COMMENT.
     */
    private static JApplet enclosingApplet = null;

    /** The system graphic configuration. */
    private static GraphicsConfiguration GC = null;

    static {
        try {
            GC = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice().getDefaultConfiguration();
        } catch (HeadlessException he) {
            // no X11 on Unix systems
            LOG
                    .warn(he.getLocalizedMessage(), LOG.isDebugEnabled() ? he
                            : null);
        }
    }

    /**
     * @return {@link JApplet} the enclosingApplet.
     */
    public static JApplet getEnclosingApplet() {
        return enclosingApplet;
    }

    /**
     * COMMENT.
     * 
     * @param path
     *            {@link String} SEBASTIAN needed?
     */
    public static BufferedImage getImageFromClasspath(final String path) {
        BufferedImage bufferedImage;
        if (Environment.isRunningAsApplet()) {
            URL url = Environment.getEnclosingApplet().getClass().getResource(
                    "/" + path);
            if (url == null) {
                bufferedImage = ImageIconFactory.ERROR_BUFFERED_IMAGE;
            } else {
                try {
                    BufferedImage tempImg = ImageIO.read(url);

                    int transparency = tempImg.getColorModel()
                            .getTransparency();

                    if (GC != null) {
                        BufferedImage gcCompatibleImage = GC
                                .createCompatibleImage(tempImg.getWidth(),
                                        tempImg.getHeight(), transparency);

                        Graphics2D g2d = gcCompatibleImage.createGraphics();
                        g2d.drawImage(tempImg, 0, 0, null);
                        g2d.dispose();

                        bufferedImage = gcCompatibleImage;
                    } else {
                        bufferedImage = tempImg;
                    }

                } catch (IOException e) {
                    bufferedImage = ImageIconFactory.ERROR_BUFFERED_IMAGE;
                }
            }
        } else {
            bufferedImage = ImageIconFactory.createBufferedImage(path);
        }
        return bufferedImage;
    }

    /**
     * COMMENT.
     * 
     * @param path
     *            {@link String}
     * @return {@link BufferedImage}
     */
    public static BufferedImage getImage(final String path) {
        try {
            return ImageIO.read(Environment.getURLForFile(path));
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage(), LOG.isDebugEnabled() ? e : null);
        }
        return ImageIconFactory.ERROR_BUFFERED_IMAGE;
    }

    /**
     * COMMENT.
     * 
     * @param string
     *            {@link String}
     * @return {@link URL}
     */
    public static URL getURLForFile(final String string) {
        URL url = null;
        if (enclosingApplet != null) {
            try {
                url = AppletHelper.getURL(enclosingApplet, string);
            } catch (MalformedURLException e) {
                LOG.error(e.getLocalizedMessage(), LOG.isDebugEnabled() ? e
                        : null);
            }
        } else {
            try {
                url = new File(string).toURI().toURL();
            } catch (IOException e) {
                LOG.error(e.getLocalizedMessage(), LOG.isDebugEnabled() ? e
                        : null);
            }
        }
        return url;
    }

    /**
     * @return {@link boolean} the runningAsApplet.
     */
    public static boolean isRunningAsApplet() {
        return enclosingApplet != null;
    }

    /**
     * @param enclosingAppletRef
     *            JApplet the enclosingApplet to set.
     */
    public static void setEnclosingApplet(final JApplet enclosingAppletRef) {
        Environment.enclosingApplet = enclosingAppletRef;
    }

    /**
     * Hidden constructor.
     * 
     */
    private Environment() {
    }

}
/*
 * $Log$
 * Revision 1.4  2007-08-09 10:12:08  ischmidt
 * fixed image retrieving on headless systems
 * Revision 1.3 2007-08-06 17:33:39 ischmidt switched to commmons logging
 * Revision 1.2 2007-08-03 21:41:21 sweiss image loading
 * 
 * Revision 1.1 2007-08-03 18:48:23 sweiss *** empty log message ***
 * 
 * Revision 1.1 2007-07-02 08:10:44 sweiss integrating applet
 * 
 */
