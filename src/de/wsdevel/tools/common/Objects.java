package de.wsdevel.tools.common;

/**
 * Created on 20.10.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.2 $ -- $Date: 2005/10/31
 *          18:22:30 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public final class Objects {

    /**
     * private constructor.
     */
    private Objects() {
    }

    /**
     * @param o1
     *            {@link Object}
     * @param o2
     *            {@link Object}
     * @return <code>boolean</code> <code>true</code> if objects are equal
     */
    public static boolean compareTwoObjects(final Object o1, final Object o2) {
        return ((o1 != null) ? o1.equals(o2) : o2 == null);
    }

    /**
     * Simple convertion from an Object[] to a String represantation.
     *
     * @param objects {@link Object}[]
     * @return lin String
     */
    public static String getObjectArrayAsString(final Object[] objects) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (int i = 0; i < objects.length; i++) {
            stringBuffer.append(objects[i].toString());
            if (i < objects.length - 1) {
                stringBuffer.append(", ");
            }
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
/*
 * $Log: Objects.java,v $
 * Revision 1.2  2007-02-15 15:54:07  sweissTFH
 * new method for printing Object[]
 *
 * Revision 1.1  2006/05/02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.5  2006/04/05 18:19:35  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.4 2005/10/31 18:22:30 sweissTFH clean up
 * and commenting
 * 
 */
