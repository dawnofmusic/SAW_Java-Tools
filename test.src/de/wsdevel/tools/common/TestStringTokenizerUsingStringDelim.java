package de.wsdevel.tools.common;

import junit.framework.TestCase;

/**
 * Created on 19.05.2004.
 * 
 * for project: __tools
 * 
 * @author <a href="mailto:weiss@dawnofmusic.com">Sebastian A. Weiss - dawn of
 *         music</a>
 * @version $Author: sweiss $ -- $Revision: 1.4 $ -- $Date: 2005/10/25 13:18:15
 *          $
 * 
 *          (c) dawn of music 2004 - All rights reserved.
 * 
 */
public class TestStringTokenizerUsingStringDelim extends TestCase {

    /**
     * COMMENT.
     */
    private static final String TOKEN_4 = "|#";

    /**
     * COMMENT.
     */
    private static final String TOKEN_3 = "jkhdsjkfsdfjksdfjsjk";

    /**
     * COMMENT.
     */
    private static final String TOKEN_2 = "kdksljlsdkfjsldkfjksdjflsd";

    /**
     * COMMENT.
     */
    private static final String TOKEN_1 = "ndsdffjksjkf";

    /**
     * COMMENT.
     */
    private static final String DELIM = "|#|";

    /**
     * COMMENT.
     * 
     * @param arg0
     *            {@link String}[]
     */
    public TestStringTokenizerUsingStringDelim(final String arg0) {
	super(arg0);
    }

    /**
     * COMMENT.
     */
    public final void testIt() {
	testTokenizer(TOKEN_1 + DELIM + TOKEN_2 + DELIM + DELIM + TOKEN_3
		+ DELIM + TOKEN_4);
	testTokenizer(DELIM + TOKEN_1 + DELIM + TOKEN_2 + DELIM + DELIM
		+ TOKEN_3 + DELIM + TOKEN_4);
    }

    /**
     * @param testSring
     *            {@link String}
     */
    private void testTokenizer(final String testSring) {
	StringTokenizerUsingStringDelim t = new StringTokenizerUsingStringDelim(
		testSring, DELIM);

	assertTrue(t.hasMoreElements());
	String nextElement = t.nextElement();
	System.out.println("next element: " + nextElement);
	assertEquals(TOKEN_1, nextElement);

	assertTrue(t.hasMoreElements());
	nextElement = t.nextElement();
	System.out.println("next element: " + nextElement);
	assertEquals(TOKEN_2, nextElement);

	assertTrue(t.hasMoreElements());
	nextElement = t.nextElement();
	System.out.println("next element: " + nextElement);
	assertEquals(TOKEN_3, nextElement);

	assertTrue(t.hasMoreElements());
	nextElement = t.nextElement();
	System.out.println("next element: " + nextElement);
	assertEquals(TOKEN_4, nextElement);

	assertTrue(!t.hasMoreElements());
    }

}
//
// $Log: TestStringTokenizerUsingStringDelim.java,v $
// Revision 1.4  2009-02-10 08:58:57  sweiss
// bug fixing
//
// Revision 1.3 2009-02-09 16:54:26 sweiss
// bug fixing and cleanup
//
// Revision 1.2 2006-05-12
// 15:36:43 sweissTFH cleanup
// 
// Revision 1.1 2006/05/02 16:06:00 sweissTFH
// cleaned up tools and moved
// everything to appropriate new packages
// 
// Revision 1.3 2006/04/05 18:19:35 sweissTFH
// cleaned up checkstyle errors
// 
// Revision 1.2 2005/10/25 13:18:15 sweissTFH
// clean up and commenting
//
