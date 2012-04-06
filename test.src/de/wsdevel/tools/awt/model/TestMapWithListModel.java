package de.wsdevel.tools.awt.model;

/**
 * Created on 08.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:weiss@dawnofmusic.com">Sebastian A. Weiss - dawn of
 *         music</a>
 * @version $Revision: 1.4 $ -- $Date: 2006-07-21 11:11:00 $
 * 
 * (c) dawn of music 2004 - All rights reserved.
 * 
 */
public class TestMapWithListModel extends ListModelTest {

    /**
     * COMMENT.
     * 
     * @param arg0 {@link String}[]
     */
    public TestMapWithListModel(final String arg0) {
        super(arg0);
    }

    /**
     * {@link MapWithListModel}< {@link String}, {@link String}> COMMENT.
     */
    private MapWithListModel<String, String> map;

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public final void setUp() {
        this.map = new MapWithListModelImpl<String, String>();
        this.map.put("a", "A");
        this.map.put("b", "B");
        this.map.put("c", "C");
        this.map.put("d", "D");
        this.map.put("e", "E");
    }

    /**
     * COMMENT.
     */
    public final void testIt() {
        this.map.getListModel().addListDataListener(this.ldl);

        assertEquals("incorrect number of elements", 5, this.map.getListModel()
                .getSize());
        assertEquals("incorrect element in buffer", "A", this.map
                .getListModel().getElementAt(0));
        assertEquals("incorrect element in buffer", "B", this.map
                .getListModel().getElementAt(1));
        assertEquals("incorrect element in buffer", "C", this.map
                .getListModel().getElementAt(2));
        assertEquals("incorrect element in buffer", "D", this.map
                .getListModel().getElementAt(3));
        assertEquals("incorrect element in buffer", "E", this.map
                .getListModel().getElementAt(4));

        this.map.put("f", "F");
        // checkEvent(this.map.getListModel(), 5, 5,
        // ListDataEvent.INTERVAL_ADDED);
        checkEventCalls(0, 1, 0);
        assertEquals("incorrect number of elements", 6, this.map.getListModel()
                .getSize());
        assertEquals("incorrect element in buffer", "F", this.map
                .getListModel().getElementAt(5));

        this.map.remove("c");
        // checkEvent(this.map.getListModel(), 2, 2,
        // ListDataEvent.INTERVAL_REMOVED);
        checkEventCalls(0, 1, 1);
        assertEquals("incorrect number of elements", 5, this.map.getListModel()
                .getSize());
        assertEquals("incorrect element in buffer", "F", this.map
                .getListModel().getElementAt(4));

    }

}
