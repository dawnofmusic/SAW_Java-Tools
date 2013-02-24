package de.wsdevel.tools.awt.model;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;

import de.wsdevel.tools.common.interfaces.Filter;

/**
 * Created on 28.12.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:weiss@dawnofmusic.com">Sebastian A. Weiss - dawn of
 *         music</a>
 * @version $Revision: 1.7 $
 * 
 *          (c) dawn of music 2003 - All rights reserved.
 * 
 */
public class TestFilteredListModel extends ListModelTest {

    /**
     * COMMENT.
     * 
     * @param arg0
     *            {@link String}[]
     */
    public TestFilteredListModel(final String arg0) {
	super(arg0);
    }

    /**
     * {@link ListModel} COMMENT.
     */
    private ListModel listModel;

    /**
     * {@link ListWithListModelOverList} COMMENT.
     */
    private ListWithListModelImpl<String> inner;

    /**
     * {@link Filter<T>} COMMENT.
     */
    private Filter<String> filter = new Filter<String>() {
	public boolean accept(final String element) {
	    return element.length() == 3;
	}
    };

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public final void setUp() {

	this.contentsChangedCalled = 0;
	this.intervallAddedCalled = 0;
	this.intervallRemovedCalled = 0;

	this.inner = new ListWithListModelImpl<String>();
	this.inner.add("ABC");
	this.inner.add("BCD");
	this.inner.add("CDE");
	this.inner.add("JHAKS");
	this.inner.add("AGSSJH");
	this.inner.add("SMSSMS");
	this.inner.add("ASJKSKLSLK");
	this.inner.add("SKJSKS");
	this.inner.add("H");
	this.inner.add("SJ");
	this.listModel = this.inner.getListModel();
    }

    /**
     * COMMENT.
     * 
     */
    public final void testFiltering() {
	FilteredListModel<String> filtered = new FilteredListModel<String>(
		this.listModel, this.filter);
	testDefaultElements(filtered);
    }

    /**
     * COMMENT. SEBASTIAN todo: test contents changed!
     */
    public final void testEventHandlingForAdding() {
	FilteredListModel<String> filtered = new FilteredListModel<String>(
		this.listModel, this.filter);
	filtered.addListDataListener(this.ldl);

	this.inner.add("Q");
	// nothing should happen in filtered
	testDefaultElements(filtered);

	this.inner.add("ghji");
	this.inner.add("oo");

	testDefaultElements(filtered);

	this.inner.add("123");
	assertEquals("incorrect number of elements", 4, filtered.getSize());
	assertEquals("incorrect element in model", "ABC", filtered
		.getElementAt(0));
	assertEquals("incorrect element in model", "BCD", filtered
		.getElementAt(1));
	assertEquals("incorrect element in model", "CDE", filtered
		.getElementAt(2));
	assertEquals("incorrect element in model", "123", filtered
		.getElementAt(3));

	checkEvent(filtered, 3, 3, ListDataEvent.INTERVAL_ADDED);

	this.inner.remove("BCD");

	assertEquals("incorrect number of elements", 3, filtered.getSize());
	assertEquals("incorrect element in model", "ABC", filtered
		.getElementAt(0));
	assertEquals("incorrect element in model", "CDE", filtered
		.getElementAt(1));
	assertEquals("incorrect element in model", "123", filtered
		.getElementAt(2));
	checkEvent(filtered, 1, 1, ListDataEvent.INTERVAL_REMOVED);

	this.inner.set(10, "XYZ");

	assertEquals("incorrect number of elements", 4, filtered.getSize());
	assertEquals("incorrect element in model", "ABC", filtered
		.getElementAt(0));
	assertEquals("incorrect element in model", "CDE", filtered
		.getElementAt(1));
	assertEquals("incorrect element in model", "123", filtered
		.getElementAt(2));
	assertEquals("incorrect element in model", "XYZ", filtered
		.getElementAt(3));
	checkEvent(filtered, 3, 3, ListDataEvent.INTERVAL_ADDED);

	this.inner.set(10, "ZYX");

	assertEquals("incorrect number of elements", 4, filtered.getSize());
	assertEquals("incorrect element in model", "ABC", filtered
		.getElementAt(0));
	assertEquals("incorrect element in model", "CDE", filtered
		.getElementAt(1));
	assertEquals("incorrect element in model", "123", filtered
		.getElementAt(2));
	assertEquals("incorrect element in model", "ZYX", filtered
		.getElementAt(3));
	checkEvent(filtered, 3, 3, ListDataEvent.CONTENTS_CHANGED);

	this.inner.set(10, "1234");

	assertEquals("incorrect number of elements", 3, filtered.getSize());
	assertEquals("incorrect element in model", "ABC", filtered
		.getElementAt(0));
	assertEquals("incorrect element in model", "CDE", filtered
		.getElementAt(1));
	assertEquals("incorrect element in model", "123", filtered
		.getElementAt(2));
	checkEvent(filtered, 3, 3, ListDataEvent.INTERVAL_REMOVED);

	this.inner.remove("ABC");
	this.inner.remove("CDE");
	this.inner.remove("123");

	assertEquals("incorrect number of elements", 0, filtered.getSize());
	checkEvent(filtered, 0, 0, ListDataEvent.INTERVAL_REMOVED);

    }

    /**
     * COMMENT.
     * 
     * @param filtered
     *            {@link FilteredListModel}
     */
    private void testDefaultElements(final FilteredListModel<String> filtered) {
	assertEquals("incorrect number of elements", 3, filtered.getSize());
	assertEquals("incorrect element in model", "ABC", filtered
		.getElementAt(0));
	assertEquals("incorrect element in model", "BCD", filtered
		.getElementAt(1));
	assertEquals("incorrect element in model", "CDE", filtered
		.getElementAt(2));
    }

}
/*
 * $Log: TestFilteredListModel.java,v $
 * Revision 1.7  2009-02-09 13:15:14  sweiss
 * clean up of stuff
 * Revision 1.6 2007-08-03 13:13:19 sweiss
 * added FilteredCollection
 * 
 * Revision 1.5 2007/03/13 18:48:30 sweissTFH new better performing
 * ListWithListModel
 * 
 * Revision 1.4 2006/07/21 12:58:39 sweissTFH comment clean up
 * 
 * Revision 1.3 2006/05/23 10:26:05 sweissTFH added functionality to config and
 * cleaned up a bit
 * 
 * Revision 1.2 2006/05/12 15:36:43 sweissTFH cleanup
 * 
 * Revision 1.1 2006/05/02 16:06:00 sweissTFH cleaned up tools and moved
 * everything to appropriate new packages
 * 
 * Revision 1.4 2006/04/10 15:34:12 sweissTFH cleaned up checkstyle errors
 * 
 * Revision 1.3 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
 * Revision 1.2 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean
 * up!
 */
