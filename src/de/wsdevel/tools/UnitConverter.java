package de.wsdevel.tools;

/**
 * Created on 18.11.2005.
 * 
 * for project: StreamMonitor
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.2 $ -- $Date: 2007-09-18 15:19:13 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public final class UnitConverter {

    /**
     * <code>int</code> COMMENT.
     */
    private static final int MILLIS_IN_SECOND = 1000;

    /**
     * <code>int</code> COMMENT.
     */
    private static final int BYTES_IN_KILOBYTE = 1024;

    /**
     * <code>int</code> COMMENT.
     */
    private static final int SECONDS_IN_MINUTE = 60;

    /**
     * <code>int</code> COMMENT.
     */
    private static final int MINUTES_IN_HOUR = 60;

    /**
     * Hidden constructor.
     */
    private UnitConverter() {
    }

    /**
     * COMMENT.
     * 
     * @param bandWidth
     *            <code>int</code>
     * @return <code>int</code>
     */
    public static int getBytesForKiloBytes(final int bandWidth) {
        return bandWidth * BYTES_IN_KILOBYTE;
    }

    /**
     * COMMENT.
     * 
     * @param hours
     *            <code>int</code>
     * @return <code>int</code>
     */
    public static int getSecondsForHours(final int hours) {
        return getMinutesForHours(hours) * SECONDS_IN_MINUTE;
    }

    /**
     * COMMENT.
     * 
     * @param hours
     *            <code>int</code>
     * @return <code>int</code>
     */
    public static int getMinutesForHours(final int hours) {
        return hours * MINUTES_IN_HOUR;
    }

    /**
     * COMMENT.
     * 
     * @param seconds
     *            <code>int</code>
     * @return <code>int</code>
     */
    public static int getMillisForSeconds(final int seconds) {
        return seconds * MILLIS_IN_SECOND;
    }

    /**
     * COMMENT.
     * 
     * @param hours
     *            <code>int</code>
     * @return <code>long</code>
     */
    public static long getMillisForHours(final int hours) {
        return getMillisForSeconds(getSecondsForHours(hours));
    }

    /**
     * COMMENT.
     * 
     * @param millis
     *            <code>long</code>
     * @return <code>int</code>
     */
    public static int getMinutesForMillis(final long millis) {
        return (int) (millis / MILLIS_IN_SECOND / SECONDS_IN_MINUTE);
    }

    /**
     * COMMENT.
     *
     * @param millisToListen <code>long</code>
     * @return <code>int</code>
     */
    public static int getSecondsForMillis(final long millisToListen) {
        return (int) (millisToListen / 1000);
    }

}
//
// $Log: UnitConverter.java,v $
// Revision 1.2  2007-09-18 15:19:13  sweiss
// added conversion helper for degree minute second to decimal degree
//
// Revision 1.1  2007/01/08 15:05:49  sweissTFH
// *** empty log message ***
//
// Revision 1.1  2005/11/18 15:16:25  sweissTFH
// ckecking connection time now!
//
//
