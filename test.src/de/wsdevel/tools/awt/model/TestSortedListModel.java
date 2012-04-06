package de.wsdevel.tools.awt.model;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;

/**
 * Created on 28.12.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:weiss@dawnofmusic.com">Sebastian A. Weiss - dawn of
 *         music</a>
 * @version $Revision: 1.5 $
 * 
 *          (c) dawn of music 2003 - All rights reserved.
 * 
 */
public class TestSortedListModel extends ListModelTest {

    /**
     * COMMENT.
     * 
     * @param name
     *            {@link String}
     */
    public TestSortedListModel(final String name) {
	super(name);
    }

    /**
     * {@link ListModel} COMMENT.
     */
    private ListModel inner = null;

    /**
     * {@link ListWithListModelOverList}< {@link java.lang.String}> COMMENT.
     */
    private ListWithListModelImpl<String> list = null;

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public final void setUp() {
	this.list = new ListWithListModelImpl<String>();
	this.list.add("Gunther");
	this.list.add("Joachim");
	this.list.add("Michael");
	this.list.add("Adam");
	this.list.add("Xaver");
	this.list.add("Alexander");
	this.list.add("petra");
	this.list.add("Petra");
	this.list.add("Zacharias");
	this.list.add("Doedel");
	this.inner = this.list.getListModel();
    }

    /**
     * COMMENT.
     * 
     */
    public final void testSorting() {
	SortedListModel<Object> sortedLM = new SortedListModel<Object>(
		this.inner, SortedListModel.DEFAULT_OBJECT_SORTER);
	assertEquals("incorrect size", 10, sortedLM.getSize());
	assertEquals("incorrect element in place", "Adam", sortedLM
		.getElementAt(0));
	assertEquals("incorrect element in place", "Alexander", sortedLM
		.getElementAt(1));
	assertEquals("incorrect element in place", "Doedel", sortedLM
		.getElementAt(2));
	assertEquals("incorrect element in place", "Gunther", sortedLM
		.getElementAt(3));
	assertEquals("incorrect element in place", "Joachim", sortedLM
		.getElementAt(4));
	assertEquals("incorrect element in place", "Michael", sortedLM
		.getElementAt(5));
	assertEquals("incorrect element in place", "Petra", sortedLM
		.getElementAt(6));
	assertEquals("incorrect element in place", "petra", sortedLM
		.getElementAt(7));
	assertEquals("incorrect element in place", "Xaver", sortedLM
		.getElementAt(8));
	assertEquals("incorrect element in place", "Zacharias", sortedLM
		.getElementAt(9));
    }

    /**
     * COMMENT.
     * 
     */
    public final void testEventHandling() {
	SortedListModel<Object> sortedLM = new SortedListModel<Object>(
		this.inner, SortedListModel.DEFAULT_OBJECT_SORTER);
	sortedLM.addListDataListener(this.ldl);

	this.list.add("Otto");

	checkEvent(sortedLM, 0, 10, ListDataEvent.INTERVAL_ADDED);
	checkEventCalls(0, 1, 1);

	assertEquals("incorrect size", 11, sortedLM.getSize());
	assertEquals("incorrect element in place", "Adam", sortedLM
		.getElementAt(0));
	assertEquals("incorrect element in place", "Alexander", sortedLM
		.getElementAt(1));
	assertEquals("incorrect element in place", "Doedel", sortedLM
		.getElementAt(2));
	assertEquals("incorrect element in place", "Gunther", sortedLM
		.getElementAt(3));
	assertEquals("incorrect element in place", "Joachim", sortedLM
		.getElementAt(4));
	assertEquals("incorrect element in place", "Michael", sortedLM
		.getElementAt(5));
	assertEquals("incorrect element in place", "Otto", sortedLM
		.getElementAt(6));
	// SEBASTIAN ???
	assertEquals("incorrect element in place", "petra", sortedLM
		.getElementAt(7));
	assertEquals("incorrect element in place", "Petra", sortedLM
		.getElementAt(8));
	assertEquals("incorrect element in place", "Xaver", sortedLM
		.getElementAt(9));
	assertEquals("incorrect element in place", "Zacharias", sortedLM
		.getElementAt(10));

	this.list.remove("petra");

	checkEvent(sortedLM, 0, 9, ListDataEvent.INTERVAL_ADDED);
	checkEventCalls(0, 2, 2);

	assertEquals("incorrect size", 10, sortedLM.getSize());
	assertEquals("incorrect element in place", "Adam", sortedLM
		.getElementAt(0));
	assertEquals("incorrect element in place", "Alexander", sortedLM
		.getElementAt(1));
	assertEquals("incorrect element in place", "Doedel", sortedLM
		.getElementAt(2));
	assertEquals("incorrect element in place", "Gunther", sortedLM
		.getElementAt(3));
	assertEquals("incorrect element in place", "Joachim", sortedLM
		.getElementAt(4));
	assertEquals("incorrect element in place", "Michael", sortedLM
		.getElementAt(5));
	assertEquals("incorrect element in place", "Otto", sortedLM
		.getElementAt(6));
	assertEquals("incorrect element in place", "Petra", sortedLM
		.getElementAt(7));
	assertEquals("incorrect element in place", "Xaver", sortedLM
		.getElementAt(8));
	assertEquals("incorrect element in place", "Zacharias", sortedLM
		.getElementAt(9));

	this.list.remove("Adam");
	this.list.remove("Alexander");
	this.list.remove("Doedel");
	this.list.remove("Gunther");
	this.list.remove("Joachim");
	this.list.remove("Michael");
	this.list.remove("Otto");
	this.list.remove("Petra");
	this.list.remove("Xaver");
	this.list.remove("Zacharias");

	checkEvent(sortedLM, 0, 0, ListDataEvent.INTERVAL_REMOVED);
	checkEventCalls(0, 11, 12);
	assertEquals("incorrect size", 0, sortedLM.getSize());
    }

}
