package de.wsdevel.tools.common;

import java.net.UnknownHostException;

/**
 * Created on 08.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2005/10/31
 *          18:22:30 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public final class GUIDFactory {

    /**
     * '0xff'.
     */
    private static final int HEX_0XFF = 0xff;

    /**
     * 8.
     */
    private static final int BUFFER_SIZE_8 = 8;

    /**
     * 16.
     */
    private static final int BUFFER_SIZE_16 = 16;

    /**
     * 32.
     */
    private static final int BUFFER_SIZE_32 = 32;

    /**
     * hidden constructor.
     */
    private GUIDFactory() {
    }

    /**
     * Cached per JVM server IP.
     */
    private static String hexServerIP = null;

    /**
     * initialise the secure random instance.
     */
    private static final java.security.SecureRandom SEEDER = new java.security.SecureRandom();

    /**
     * A 32 byte GUID generator (Globally Unique ID). These artificial keys
     * SHOULD <strong>NOT </strong> be seen by the user, not even touched by the
     * DBA but with very rare exceptions, just manipulated by the database and
     * the programs.
     * 
     * @param o
     *            {@link Object} the object this GUID is generated for
     * @return {@link String} the generated GUID
     * @throws UnknownHostException
     *             if localhost couldn't be resolved!
     */
    public static String generateGUID(final Object o)
            throws UnknownHostException {
        StringBuffer tmpBuffer = new StringBuffer(BUFFER_SIZE_16);
        if (hexServerIP == null) {
            java.net.InetAddress localInetAddress = java.net.InetAddress
                    .getLocalHost();
            byte[] serverIP = localInetAddress.getAddress();
            hexServerIP = hexFormat(getInt(serverIP), BUFFER_SIZE_8);
        }
        String hashcode = hexFormat(System.identityHashCode(o), BUFFER_SIZE_8);
        tmpBuffer.append(hexServerIP);
        tmpBuffer.append(hashcode);

        long timeNow = System.currentTimeMillis();
        int timeLow = (int) timeNow & 0xFFFFFFFF;
        int node = SEEDER.nextInt();

        StringBuffer guid = new StringBuffer(BUFFER_SIZE_32);
        guid.append(hexFormat(timeLow, BUFFER_SIZE_8));
        guid.append(tmpBuffer.toString());
        guid.append(hexFormat(node, BUFFER_SIZE_8));
        return guid.toString();
    }

    /**
     * @param bytes
     *            <code>byte[]</code>
     * @return <code>int</code>
     */
    private static int getInt(final byte[] bytes) {
        int i = 0;
        int j = 24;
        for (int k = 0; j >= 0; k++) {
            int l = bytes[k] & HEX_0XFF;
            i += l << j;
            j -= BUFFER_SIZE_8;
        }
        return i;
    }

    /**
     * @param i
     *            <code>int</code>
     * @param j
     *            <code>int</code>
     * @return {@link String}
     */
    private static String hexFormat(final int i, final int j) {
        String s = Integer.toHexString(i);
        return padHex(s, j) + s;
    }

    /**
     * @param s
     *            {@link String}
     * @param i
     *            <code>int</code>
     * @return {@link String}
     */
    private static String padHex(final String s, final int i) {
        StringBuffer tmpBuffer = new StringBuffer();
        if (s.length() < i) {
            for (int j = 0; j < i - s.length(); j++) {
                tmpBuffer.append('0');
            }
        }
        return tmpBuffer.toString();
    }
}
/*
 * $Log: GUIDFactory.java,v $
 * Revision 1.1  2006-05-02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.4  2006/04/10 15:34:12  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.3 2005/10/31 18:22:30 sweissTFH clean
 * up and commenting Revision 1.2 2005/09/17 17:27:01 sweissTFH cleaned up
 * 
 */
