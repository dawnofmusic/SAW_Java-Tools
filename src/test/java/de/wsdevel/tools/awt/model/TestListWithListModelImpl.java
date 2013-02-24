package de.wsdevel.tools.awt.model;

import junit.framework.TestCase;

/**
 * Created on 13.03.2007.
 *
 * for project: Java_Tools
 *
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss und Schmidt, Mediale Systeme GbR</a>
 * @version $Author$ -- $Revision$ -- $Date$
 *
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 *
 */
public class TestListWithListModelImpl extends TestCase {

    private static final String EEE = "EEE";

    /**
     * {@link String} COMMENT.
     */
    private static final String DDD = "DDD";

    /**
     * {@link String} COMMENT.
     */
    private static final String CCC = "CCC";

    /**
     * {@link String} COMMENT.
     */
    private static final String BBB = "BBB";

    /**
     * {@link String} COMMENT.
     */
    private static final String AAA = "AAA";

    /**
     * COMMENT.
     *
     * @param name {@link String}
     */
    public TestListWithListModelImpl(final String name) {
        super(name);
    }

    /**
     * COMMENT.
     */
    public final void test1() {

        ListWithListModelImpl<String> list = new ListWithListModelImpl<String>();

        list.add(TestListWithListModelImpl.AAA);
        list.add(TestListWithListModelImpl.BBB);
        list.add(TestListWithListModelImpl.CCC);

        assertEquals("contains 3 elements", 3, list.size());

        assertEquals("first is AAA", TestListWithListModelImpl.AAA, list.get(0));
        assertEquals("second is BBB", TestListWithListModelImpl.BBB, list
                .get(1));
        assertEquals("third is CCC", TestListWithListModelImpl.CCC, list.get(2));

        list.add(2, TestListWithListModelImpl.DDD);
        assertEquals("second is BBB", TestListWithListModelImpl.BBB, list
                .get(1));
        assertEquals("third is DDD", TestListWithListModelImpl.DDD, list.get(2));
        assertEquals("fourth is CCC", TestListWithListModelImpl.CCC, list
                .get(3));
        assertEquals("contains 4 elements", 4, list.size());

        list.set(2, TestListWithListModelImpl.EEE);
        assertEquals("second is BBB", TestListWithListModelImpl.BBB, list
                .get(1));
        assertEquals("third is EEE", TestListWithListModelImpl.EEE, list.get(2));
        assertEquals("fourth is CCC", TestListWithListModelImpl.CCC, list
                .get(3));
        assertEquals("contains 4 elements", 4, list.size());

        list.remove(2);
        assertEquals("first is AAA", TestListWithListModelImpl.AAA, list.get(0));
        assertEquals("second is BBB", TestListWithListModelImpl.BBB, list
                .get(1));
        assertEquals("third is CCC", TestListWithListModelImpl.CCC, list.get(2));
        assertEquals("contains 3 elements", 3, list.size());

    }

}
/*
 * $Log$
 * Revision 1.2  2007-03-13 18:56:08  sweissTFH
 * bug fix
 *
 * Revision 1.1  2007/03/13 18:48:30  sweissTFH
 * new better performing ListWithListModel
 *
 */
