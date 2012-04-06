package de.wsdevel.tools.awt;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Created on 10.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:weiss@dawnofmusic.com">Sebastian A. Weiss - dawn of
 *         music</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.2 $ -- $Date: 2005/01/23
 *          12:38:15 $
 * 
 * (c) dawn of music 2004 - All rights reserved.
 * 
 */
public class GBC extends GridBagConstraints {

    /**
     * <code>long</code> COMMENT.
     */
    private static final long serialVersionUID = -490139233506678897L;

    /**
     * @param x
     *            <code>int</code>
     * @param y
     *            <code>int</code>
     * @return {@link GBC} this instance
     */
    public final GBC pos(final int x, final int y) {
        this.gridx = x;
        this.gridy = y;
        return this;
    }

    /**
     * @param x
     *            <code>int</code>
     * @param y
     *            <code>int</code>
     * @return {@link GBC} this instance
     */
    public final GBC size(final int x, final int y) {
        this.gridwidth = x;
        this.gridheight = y;
        return this;
    }

    /**
     * @param weight
     *            <code>int</code>
     * @return {@link GBC} this instance
     */
    public final GBC fillHorizontal(final double weight) {
        this.fill = GridBagConstraints.HORIZONTAL;
        this.weightx = weight;
        return this;
    }

    /**
     * @param weight
     *            <code>int</code>
     * @return {@link GBC} this instance
     */
    public final GBC fillVertical(final double weight) {
        this.fill = GridBagConstraints.VERTICAL;
        this.weighty = weight;
        return this;
    }

    /**
     * @param weightxVal
     *            <code>int</code>
     * @param weightyVal
     *            <code>int</code>
     * @return {@link GBC} this instance
     */
    public final GBC fillBoth(final double weightxVal, final double weightyVal) {
        this.fill = GridBagConstraints.BOTH;
        this.weightx = weightxVal;
        this.weighty = weightyVal;
        return this;
    }

    /**
     * @return {@link GBC} this instance
     */
    public final GBC anchorNorth() {
        this.anchor = GridBagConstraints.NORTH;
        return this;
    }

    /**
     * @return {@link GBC} this instance
     */
    public final GBC anchorNorthEast() {
        this.anchor = GridBagConstraints.NORTHEAST;
        return this;
    }

    /**
     * @return {@link GBC} this instance
     */
    public final GBC anchorEast() {
        this.anchor = GridBagConstraints.EAST;
        return this;
    }

    /**
     * @return {@link GBC} this instance
     */
    public final GBC anchorSouthEast() {
        this.anchor = GridBagConstraints.SOUTHEAST;
        return this;
    }

    /**
     * @return {@link GBC} this instance
     */
    public final GBC anchorSouth() {
        this.anchor = GridBagConstraints.SOUTH;
        return this;
    }

    /**
     * @return {@link GBC} this instance
     */
    public final GBC anchorSouthWest() {
        this.anchor = GridBagConstraints.SOUTHWEST;
        return this;
    }

    /**
     * @return {@link GBC} this instance
     */
    public final GBC anchorWest() {
        this.anchor = GridBagConstraints.WEST;
        return this;
    }

    /**
     * @return {@link GBC} this instance
     */
    public final GBC anchorNorthWest() {
        this.anchor = GridBagConstraints.NORTHWEST;
        return this;
    }

    /**
     * @return {@link GBC} this instance
     */
    public final GBC anchorCenter() {
        this.anchor = GridBagConstraints.CENTER;
        return this;
    }

    /**
     * @param t
     *            <code>int</code> top
     * @param l
     *            <code>int</code> left
     * @param b
     *            <code>int</code> bottom
     * @param r
     *            <code>int</code> right
     * @return {@link GBC} this instance
     */
    public final GBC insets(final int t, final int l, final int b, final int r) {
        this.insets = new Insets(t, l, b, r);
        return this;
    }

    /**
     * @param insetsVal
     *            {@link Insets}
     * @return {@link GBC} this instance
     */
    public final GBC insets(final Insets insetsVal) {
        this.insets = insetsVal;
        return this;
    }

    /**
     * @return {@link GBC} this instance
     */
    public final GBC gridWidthRemainder() {
        this.gridwidth = GridBagConstraints.REMAINDER;
        return this;
    }

    /**
     * @return {@link GBC} this instance
     */
    public final GBC gridHeightRemainder() {
        this.gridheight = GridBagConstraints.REMAINDER;
        return this;
    }
}
/*
 * $Log: GBC.java,v $
 * Revision 1.2  2006-06-10 13:00:32  sweissTFH
 * cleanup and smaller changes due to new compiler settings
 *
 * Revision 1.1  2006/05/02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.7  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * 
 * Revision 1.6 2005/12/27 16:06:01 sweissTFH moved to java 5
 * and very big clean up!
 * 
 * Revision 1.5 2005/10/31 18:22:30 sweissTFH clean up and commenting
 * 
 * Revision 1.4 2005/08/18 09:48:29 mschneiderTFH added comments
 * 
 */
