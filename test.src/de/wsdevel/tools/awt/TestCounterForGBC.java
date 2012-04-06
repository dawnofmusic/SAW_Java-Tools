package de.wsdevel.tools.awt;

import junit.framework.TestCase;

/**
 * Created on 30.09.2004.
 * 
 * for project: __tools
 * 
 * @author <a href="mailto:weiss@dawnofmusic.com">Sebastian A. Weiss - dawn of
 *         music</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.3 $ -- $Date: 2005/10/31
 *          18:22:30 $
 * 
 * (c) dawn of music 2004 - All rights reserved.
 * 
 */
public class TestCounterForGBC extends TestCase {

    /**
     * COMMENT.
     * 
     * @param name
     *            {@link String}
     */
    public TestCounterForGBC(final String name) {
        super(name);
    }

    /**
     * {@link CounterForGBC} COMMENT.
     */
    private CounterForGBC counter;

    /**
     * @throws Exception
     *             COMMENT
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected final void setUp() throws Exception {
        super.setUp();

        this.counter = new CounterForGBC();
    }

    /**
     * COMMENT.
     */
    public final void testNext() {
        assertTrue(this.counter.next() == 0);
        assertTrue(this.counter.next() == 1);
        assertTrue(this.counter.next() == 2);
        assertTrue(this.counter.next() == 3);
        assertTrue(this.counter.next() == 4);
    }

}
/*
 * $Log: TestCounterForGBC.java,v $
 * Revision 1.3  2006-06-10 13:00:32  sweissTFH
 * cleanup and smaller changes due to new compiler settings
 *
 * Revision 1.2  2006/05/12 15:36:43  sweissTFH
 * cleanup
 *
 * Revision 1.1  2006/05/02 16:06:01  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.4  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.3 2005/12/27 16:06:01 sweissTFH
 * moved to java 5 and very big clean up!
 * 
 * Revision 1.2 2005/10/31 18:22:30 sweissTFH clean up and commenting
 * 
 */
