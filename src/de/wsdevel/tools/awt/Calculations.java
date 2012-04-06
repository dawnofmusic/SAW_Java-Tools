package de.wsdevel.tools.awt;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created on 28.09.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.3 $ -- $Date: 2005/10/31
 *          18:22:30 $ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class Calculations {

    /**
     * COMMENT.
     */
    private Calculations() {
    }

    /**
     * COMMENT.
     * 
     * @param rect
     *            {@link Rectangle2D}
     * @param point
     *            {@link Point2D}
     * @return {@link Point2D}
     */
    public static Point2D calculateNearestCorner(final Rectangle2D rect,
            final Point2D point) {

        Point2D topLeft = new Point2D.Double(rect.getX(), rect.getY());
        Point2D topRight = new Point2D.Double(rect.getX() + rect.getWidth(),
                rect.getY());
        Point2D bottomLeft = new Point2D.Double(rect.getX(), rect.getY()
                + rect.getHeight());
        Point2D bottomRight = new Point2D.Double(rect.getX() + rect.getWidth(),
                rect.getY() + rect.getHeight());

        double disTL = point.distance(topLeft);
        double disTR = point.distance(topRight);
        double disBL = point.distance(bottomLeft);
        double disBR = point.distance(bottomRight);

        if (disTL <= disTR) {
            if (disTL <= disBL) {
                return topLeft;
            }
            return bottomLeft;
        } else if (disTR <= disBR) {
            return topRight;
        } else {
            return bottomRight;
        }
    }

    /**
     * calculates a drawable selection rectangle.
     * 
     * @param pointA
     *            {@link Point2D}
     * @param pointB
     *            {@link Point2D}
     * @return {@link Rectangle2D}
     */
    public static Rectangle2D createRect(final Point2D pointA,
            final Point2D pointB) {
        double rectMinX, rectMaxX, rectMinY, rectMaxY;
        if (pointA.getX() < pointB.getX()) {
            rectMinX = pointA.getX();
            rectMaxX = pointB.getX();
        } else {
            rectMinX = pointB.getX();
            rectMaxX = pointA.getX();
        }
        if (pointA.getY() < pointB.getY()) {
            rectMinY = pointA.getY();
            rectMaxY = pointB.getY();
        } else {
            rectMinY = pointB.getY();
            rectMaxY = pointA.getY();
        }
        return new Rectangle2D.Double(rectMinX, rectMinY, rectMaxX - rectMinX,
                rectMaxY - rectMinY);
    }

    /**
     * COMMENT.
     *
     * @param value <code>int</code>
     * @param min <code>int</code>
     * @param max <code>int</code>
     * @return <code>int</code>
     */
    public static int limitValue(final int value, final int min, final int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
}
/*
 * $Log: Calculations.java,v $
 * Revision 1.3  2006-07-24 09:18:07  sweissTFH
 * fixed selection rectangle in JdragZoomPanel
 *
 * Revision 1.2  2006/06/19 12:56:52  sweissTFH
 * added auto magnetism and fixed nearest item
 *
 * Revision 1.1  2006/05/02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.5  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * 
 * Revision 1.4 2005/11/20 15:19:47 sweissTFH just
 * returned to old state
 * 
 * Revision 1.3 2005/11/18 15:16:07 sweissTFH some improvements
 * 
 * Revision 1.2 2005/10/31 18:22:30 sweissTFH clean up and commenting
 * 
 */
