package de.wsdevel.tools.common;

import junit.framework.TestCase;

/**
 * Created on 17.06.2004.
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
public abstract class ExtendedTestCase extends TestCase {

    /**
     * COMMENT.
     * 
     * @param name
     *            {@link String}
     */
    public ExtendedTestCase(final String name) {
        super(name);
    }

    /**
     * COMMENT.
     * 
     * @param ints
     *            <code>int[]</code>
     * @param toBeTested
     *            <code>int[]</code>
     */
    public static void assertEquals(final int[] ints, final int[] toBeTested) {
        assertEquals("incorrect size", ints.length, toBeTested.length);
        for (int i = 0; i < ints.length; i++) {
            assertEquals("incorrect element", ints[i], toBeTested[i]);
        }
    }

    /**
     * COMMENT.
     * 
     * @param objects
     *            {@link Object}[]
     * @param toBeTested
     *            {@link Object}[]
     */
    public static void assertEquals(final Object[] objects,
            final Object[] toBeTested) {
        assertEquals("incorrect size", objects.length, toBeTested.length);
        for (int i = 0; i < objects.length; i++) {
            assertEquals("incorrect element", objects[i], toBeTested[i]);
        }
    }
}
/*
 * $Log: ExtendedTestCase.java,v $
 * Revision 1.1  2006-05-02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.3  2006/04/05 18:19:35  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.2 2005/10/31 18:22:30 sweissTFH
 * clean up and commenting
 * 
 */
