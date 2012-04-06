package de.wsdevel.tools.awt;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Created on 10.12.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.2 $ -- $Date: 2005/10/31
 *          18:22:30 $ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 * @deprecated use {@link GBC} instead!
 */
@Deprecated
public final class GridBagHelper {

    /**
     * hidden constructor.
     */
    private GridBagHelper() {
    }

    /**
     * COMMENT.
     * 
     * @param posx
     *            <code>int</code>
     * @param posy
     *            <code>int</code>
     * @param anchor
     *            <code>int</code>
     * @return {@link GridBagConstraints}
     */
    public static GridBagConstraints get(final int posx, final int posy,
            final int anchor) {
        GridBagConstraints gbc = new GBC().pos(posx, posy);
        gbc.anchor = anchor;
        return gbc;
    }

    /**
     * COMMENT.
     * 
     * @param posx
     *            <code>int</code>
     * @param posy
     *            <code>int</code>
     * @param anchor
     *            <code>int</code>
     * @param insets
     *            {@link Insets}
     * @return {@link GridBagConstraints}
     */
    public static GridBagConstraints get(final int posx, final int posy,
            final int anchor, final Insets insets) {
        GridBagConstraints gbc = get(posx, posy, anchor);
        gbc.insets = insets;
        return gbc;
    }

    /**
     * COMMENT.
     * 
     * @param posx
     *            <code>int</code>
     * @param posy
     *            <code>int</code>
     * @param fill
     *            <code>int</code>
     * @param weightx
     *            <code>double</code>
     * @param weighty
     *            <code>double</code>
     * @return {@link GridBagConstraints}
     */
    public static GridBagConstraints get(final int posx, final int posy,
            final int fill, final double weightx, final double weighty) {
        GridBagConstraints gbc = new GBC().pos(posx, posy);
        gbc.fill = fill;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        return gbc;
    }

    /**
     * COMMENT.
     * 
     * @param posx
     *            <code>int</code>
     * @param posy
     *            <code>int</code>
     * @param fill
     *            <code>int</code>
     * @param weightx
     *            <code>double</code>
     * @param weighty
     *            <code>double</code>
     * @param anchor
     *            <code>int</code>
     * @param insets
     *            {@link Insets}
     * @return {@link GridBagConstraints}
     */
    public static GridBagConstraints get(final int posx, final int posy,
            final int fill, final double weightx, final double weighty,
            final int anchor, final Insets insets) {
        GridBagConstraints gbc = get(posx, posy, fill, weightx, weighty, anchor);
        gbc.insets = insets;
        return gbc;
    }

    /**
     * COMMENT.
     * 
     * @param posx
     *            <code>int</code>
     * @param posy
     *            <code>int</code>
     * @param fill
     *            <code>int</code>
     * @param weightx
     *            <code>double</code>
     * @param weighty
     *            <code>double</code>
     * @param anchor
     *            <code>int</code>
     * @return {@link GridBagConstraints}
     */
    public static GridBagConstraints get(final int posx, final int posy,
            final int fill, final double weightx, final double weighty,
            final int anchor) {
        GridBagConstraints gbc = get(posx, posy, fill, weightx, weighty);
        gbc.anchor = anchor;
        return gbc;
    }

}
/*
 * $Log: GridBagHelper.java,v $
 * Revision 1.2  2006-06-10 13:00:32  sweissTFH
 * cleanup and smaller changes due to new compiler settings
 *
 * Revision 1.1  2006/05/02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.6  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * 
 * Revision 1.5 2005/12/27 16:06:01 sweissTFH moved
 * to java 5 and very big clean up!
 * 
 * Revision 1.4 2005/10/31 18:22:30 sweissTFH clean up and commenting
 * 
 */
