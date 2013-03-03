package de.wsdevel.tools.awt.model;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * Created on 29.12.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:weiss@dawnofmusic.com">Sebastian A. Weiss - dawn of
 *         music</a>
 * @version $Revision: 1.3 $
 * 
 *          (c) dawn of music 2003 - All rights reserved.
 * 
 */
public class ListModelTest extends TestCase {

    /**
     * <code>int</code> COMMENT.
     */
    protected int contentsChangedCalled = 0;

    /**
     * <code>int</code> COMMENT.
     */
    protected int intervallAddedCalled = 0;

    /**
     * <code>int</code> COMMENT.
     */
    protected int intervallRemovedCalled = 0;

    /**
     * <code>int</code> COMMENT.
     */
    private ListDataEvent lastEventFired;

    /**
     * {@link ListDataListener} COMMENT.
     */
    ListDataListener ldl = new ListDataListener() {
	public void contentsChanged(final ListDataEvent e) {
	    ListModelTest.this.contentsChangedCalled++;
	    ListModelTest.this.lastEventFired = e;
	    // System.out.println("Caught event in "
	    // + Thread.currentThread().getName());
	}

	public void intervalAdded(final ListDataEvent e) {
	    ListModelTest.this.intervallAddedCalled++;
	    ListModelTest.this.lastEventFired = e;
	    // System.out.println("Caught event in "
	    // + Thread.currentThread().getName());
	}

	public void intervalRemoved(final ListDataEvent e) {
	    ListModelTest.this.intervallRemovedCalled++;
	    ListModelTest.this.lastEventFired = e;
	    // System.out.println("Caught event in "
	    // + Thread.currentThread().getName());
	}
    };

    /**
     * COMMENT.
     * 
     * @param arg0
     *            {@link String}
     */
    public ListModelTest(final String arg0) {
	super(arg0);
    }

    /**
     * COMMENT.
     * 
     * @param source
     *            {@link Object}
     * @param index0
     *            <code>int</code>
     * @param index1
     *            <code>int</code>
     * @param type
     *            <code>int</code>
     */
    void checkEvent(final Object source, final int index0, final int index1,
	    final int type) {
	if (this.lastEventFired != null) {
	    assertEquals("Correct source in event", source,
		    this.lastEventFired.getSource());
	    assertEquals("invalid indexes", index0,
		    this.lastEventFired.getIndex0());
	    assertEquals("invalid indexes", index1,
		    this.lastEventFired.getIndex1());
	    assertEquals("invalid event type", type,
		    this.lastEventFired.getType());
	} else {
	    throw new AssertionFailedError("no event registered yet!");
	}
    }

    /**
     * COMMENT.
     * 
     * @param contentCount
     *            <code>int</code>
     * @param addedCount
     *            <code>int</code>
     * @param removedCount
     *            <code>int</code>
     */
    final void checkEventCalls(final int contentCount, final int addedCount,
	    final int removedCount) {
	assertEquals("mismatching number of contentChanged events",
		contentCount, this.contentsChangedCalled);
	assertEquals("mismatching number of intervallAdded events", addedCount,
		this.intervallAddedCalled);
	assertEquals("mismatching number of intervallRemoved events",
		removedCount, this.intervallRemovedCalled);
    }

    /**
     * COMMENT.
     * 
     */
    void myTestNoEventsFired() {
	checkEventCalls(0, 0, 0);
    }

    /**
     * This test case is only needed for compatibility reasons. If not present
     * maven surefire will throw an AssertException.
     */
    public void testNothing() {
	// needed for compatibility reasons (maven surefire)
	assertTrue(true);
    }

}
/*
 * $Log: ListModelTest.java,v $ Revision 1.3 2007-07-27 15:01:08 sweiss added
 * test config
 * 
 * Revision 1.2 2006-05-23 10:26:05 sweissTFH added functionality to config and
 * cleaned up a bit
 * 
 * Revision 1.1 2006/05/02 16:06:00 sweissTFH cleaned up tools and moved
 * everything to appropriate new packages
 * 
 * Revision 1.4 2006/04/10 15:34:12 sweissTFH cleaned up checkstyle errors
 * Revision 1.3 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
 * 
 * Revision 1.2 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean
 * up!
 */
