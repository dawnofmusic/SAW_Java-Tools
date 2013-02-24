package de.wsdevel.tools.awt.model;

import javax.swing.event.ListDataEvent;

/**
 * Created on 30.12.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:weiss@dawnofmusic.com">Sebastian A. Weiss - dawn of
 *         music</a>
 * @version $Revision: 1.4 $
 * 
 * (c) dawn of music 2003 - All rights reserved.
 * 
 */
public class TestBufferdListModel extends ListModelTest {

    /**
     * COMMENT.
     * 
     * @param arg0
     *            {@link String}[]
     */
    public TestBufferdListModel(final String arg0) {
        super(arg0);
    }

    /**
     * {@link ListWithListModelImpl< {@link String}>} COMMENT.
     */
    private ListWithListModelImpl<String> list;

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public final void setUp() {
        this.list = new ListWithListModelImpl<String>();
        this.list.add("A");
        this.list.add("B");
        this.list.add("C");
        this.list.add("D");
        this.list.add("E");
    }

    /**
     * COMMENT.
     */
    public final void testIt() {
        BufferedListModel buffered = new BufferedListModel(this.list
                .getListModel());
        buffered.addListDataListener(this.ldl);

        assertEquals("incorrect number of elements", 5, buffered.getSize());
        assertEquals("incorrect element in buffer", "A", buffered
                .getElementAt(0));
        assertEquals("incorrect element in buffer", "B", buffered
                .getElementAt(1));
        assertEquals("incorrect element in buffer", "C", buffered
                .getElementAt(2));
        assertEquals("incorrect element in buffer", "D", buffered
                .getElementAt(3));
        assertEquals("incorrect element in buffer", "E", buffered
                .getElementAt(4));

        this.list.add("F");
        checkEvent(buffered, 5, 5, ListDataEvent.INTERVAL_ADDED);
        checkEventCalls(0, 1, 0);
        assertEquals("incorrect number of elements", 6, buffered.getSize());
        assertEquals("incorrect element in buffer", "F", buffered
                .getElementAt(5));

        this.list.remove("C");
        checkEvent(buffered, 2, 2, ListDataEvent.INTERVAL_REMOVED);
        checkEventCalls(0, 1, 1);
        assertEquals("incorrect number of elements", 5, buffered.getSize());
        assertEquals("incorrect element in buffer", "F", buffered
                .getElementAt(4));

        this.list.set(1, "Q");
        checkEvent(buffered, 1, 1, ListDataEvent.CONTENTS_CHANGED);
        checkEventCalls(1, 1, 1);
        assertEquals("incorrect number of elements", 5, buffered.getSize());
        assertEquals("incorrect element in buffer", "F", buffered
                .getElementAt(4));
        assertEquals("incorrect element in buffer", "Q", buffered
                .getElementAt(1));

    }

}
/*
 * $Log: TestBufferdListModel.java,v $
 * Revision 1.4  2006-07-21 11:11:00  sweissTFH
 * some comment fixes
 *
 * Revision 1.3  2006/05/23 10:26:05  sweissTFH
 * added functionality to config and cleaned up a bit
 *
 * Revision 1.2  2006/05/12 15:36:43  sweissTFH
 * cleanup
 *
 * Revision 1.1  2006/05/02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.4  2006/04/10 15:34:12  sweissTFH
 * cleaned up checkstyle errors
 *
 * Revision 1.3  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.2 2005/12/27 16:06:01
 * sweissTFH moved to java 5 and very big clean up!
 * 
 */
