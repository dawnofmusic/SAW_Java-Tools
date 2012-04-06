package de.wsdevel.tools.awt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageProducer;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created on 18.08.2005.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.8 $ -- $Date: 2005/11/17
 *          19:09:45 $ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class GUIToolkit {

    /**
     * This is the greatest zip code possible in germany.
     */
    private static final int MAX_ZIP_CODE = 99999;

    /**
     * default inset used for slider.
     */
    private static final int DEFAULT_INSET = 5;

    /**
     * maximum steps in slider.
     */
    private static final int MAX_STEPS = 10;

    /**
     * hidden constructor.
     */
    private GUIToolkit() {
    }

    /**
     * Created on 18.08.2005.
     * 
     * for project: tools
     * 
     * @author <a href="mailto:sweiss@teamforhire.de">Sebastian A. Weiss - team
     *         for hire</a>
     * @version $Author: sweiss $ -- $Revision: 1.8 $ -- $Date: 2005/07/14
     *          10:24:45 $ <br>
     *          (c) team for hire 2005 - All rights reserved.
     * 
     */
    public static interface PropertyHolder {

        /**
         * @return <code>double</code>
         */
        double getVal();

        /**
         * @param val
         *            <code>double</code>
         */
        void setVal(double val);
    }

    /**
     * @param caption
     *            {@link String}
     * @param ph
     *            {@link PropertyHolder}
     * @param minVal
     *            <code>double</code>
     * @param maxVal
     *            <code>double</code>
     * @return {@link JPanel} a panel containing a slider
     */
    public static JPanel createSliderItem(final String caption,
            final PropertyHolder ph, final double minVal, final double maxVal) {

        double calcMult = 1.0;
        if ((maxVal - minVal) < MAX_STEPS) {
            calcMult = 10;
        }
        final double mult = calcMult;

        JPanel item = new JPanel(new GridBagLayout());
        final JSlider slid = new JSlider(SwingConstants.HORIZONTAL, (int) Math
                .round(minVal * mult), (int) Math.round(maxVal * mult),
                (int) Math.round(ph.getVal() * mult));
        slid.setMajorTickSpacing((int) Math
                .round((mult * (maxVal - minVal)) / 2));
        slid.setMinorTickSpacing((int) Math.round((mult * (maxVal - minVal))
                / MAX_STEPS));

        slid.setSnapToTicks(true);
        slid.setPaintLabels(true);
        slid.setPaintTicks(true);

        final JLabel label = new JLabel();
        label.setText(caption + ": " + slid.getValue());
        item.add(label, new GBC().pos(0, 0).anchorNorthWest().insets(
                DEFAULT_INSET, DEFAULT_INSET, DEFAULT_INSET, DEFAULT_INSET));
        slid.addChangeListener(new ChangeListener() {
            public void stateChanged(final ChangeEvent e) {
                label.setText(caption + ": " + slid.getValue());
                ph.setVal(slid.getValue() / mult);
            }
        });
        item.add(slid, new GBC().pos(0, 1).anchorNorthWest().insets(0,
                DEFAULT_INSET, DEFAULT_INSET, DEFAULT_INSET).fillBoth(1.0, 0));
        return item;
    }

    /**
     * @param caption
     *            {@link java.lang.String}
     * @param ph
     *            {@link PropertyHolder}
     * @return {@link JPanel} a panel containing a slider from 0 to 1
     */
    public static JPanel create0to1SliderItem(final String caption,
            final PropertyHolder ph) {
        return createSliderItem(caption, ph, 0, 1.0);
    }

    /**
     * @return {@link JSpinner} a spinner for german zip codes
     */
    public static JSpinner createZipCodeSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(Integer.valueOf(0),
                Integer.valueOf(0), Integer.valueOf(MAX_ZIP_CODE), Integer.valueOf(1)));
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "00000"));
        return spinner;
    }

    /**
     * @param comp
     *            {@link Component}
     */
    public static void center(final Component comp) {
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        comp.setLocation((w - comp.getWidth()) / 2, (h - comp.getHeight()) / 2);
    }

    /**
     * @param comp
     *            {@link Component}
     * @param bounds
     *            {@link Rectangle}
     * @return {@link JFrame}
     */
    public static JFrame createFrameOverComponent(final Component comp,
            final Rectangle bounds) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(comp, BorderLayout.CENTER);
        frame.pack();
        frame.setBounds(bounds);
        return frame;
    }

    /**
     * Loads an image.
     * 
     * @param fileName
     *            {@link String} the file name
     * @return an {@link Image}
     * @throws IOException
     *             if the file could not be read
     */
    public static Image loadImage(final String fileName) throws IOException {
        if (fileName == null) {
            throw new NullPointerException("filename may not be null!");
        }
        URL url = GUIToolkit.class.getResource(fileName);
        if (url != null) {
            Object objImageProducer = url.getContent();
            if (objImageProducer instanceof ImageProducer) {
                return Toolkit.getDefaultToolkit().createImage(
                        (ImageProducer) objImageProducer);
            } else if (objImageProducer instanceof BufferedInputStream) {
                BufferedInputStream imageStream = (BufferedInputStream) objImageProducer;
                byte[] arrImageBytes = new byte[imageStream.available()];
                imageStream.read(arrImageBytes);
                return Toolkit.getDefaultToolkit().createImage(arrImageBytes);
            } else {
                throw new IllegalArgumentException("cannot handle image: "
                        + fileName);
            }
        }
        throw new IllegalArgumentException("filename not in classpath! "
                + fileName);
    }

    /**
     * @param width
     *            <code>int</code>
     * @param height
     *            <code>int</code>
     * @param newMaxWidth
     *            <code>int</code>
     * @param newMaxHeight
     *            <code>int</code>
     * @return <code>double</code> the scale used for scaling
     */
    public static double calculateScale(final int width, final int height,
            final int newMaxWidth, final int newMaxHeight) {
        double scale = 1.0;
        scale = (double) newMaxWidth / (double) width;
        if (height * scale > newMaxHeight) {
            scale = (double) newMaxHeight / (double) height;
        }
        return scale;
    }

    /**
     * Adds a simple {@link java.awt.event.WindowListener} listening for 
     * {@link java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)}
     * responsible for shutting down the application.
     *
     * @param frame {@link JFrame}
     */
    public static void addSystemExitListener(final JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * COMMENT.
     *
     * @param panel {@link JPanel}
     * @param bounds {@link Rectangle}
     * @return {@link JFrame}
     */
    public static JFrame createMainFramOverPanel(final Component panel,
            final Rectangle bounds) {
        JFrame frame = createFrameOverComponent(panel, bounds);
        center(frame);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                System.exit(0);
            }
        });
        return frame;
    }
}
/*
 * $Log: GUIToolkit.java,v $
 * Revision 1.8  2009-02-09 16:54:25  sweiss
 * bug fixing and cleanup
 *
 * Revision 1.7  2008-03-28 14:17:26  sweiss
 * smaller change
 *
 * Revision 1.6  2007-08-03 14:06:32  sweiss
 * renaming method
 *
 * Revision 1.5  2007-05-30 17:46:09  sweiss
 * *** empty log message ***
 *
 * Revision 1.4  2006/07/15 15:57:59  sweissTFH
 * *** empty log message ***
 *
 * Revision 1.3  2006/06/10 17:57:30  sweissTFH
 * clean up
 *
 * Revision 1.2  2006/06/10 13:00:32  sweissTFH
 * cleanup and smaller changes due to new compiler settings
 *
 * Revision 1.1  2006/05/02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.11  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.10 2005/12/27 16:06:01 sweissTFH moved
 * to java 5 and very big clean up!
 * 
 * Revision 1.9 2005/11/17 19:09:45 sweissTFH some smaller changes and addons
 * 
 * Revision 1.8 2005/10/31 18:22:30 sweissTFH clean up and commenting
 * 
 * Revision 1.7 2005/08/18 09:48:29 mschneiderTFH added comments
 * 
 * Revision 1.6 2004/10/02 18:03:14 sweiss *** empty log message ***
 */
