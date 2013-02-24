package de.wsdevel.tools.awt.model;

import java.util.AbstractList;
import java.util.List;

import javax.swing.ListModel;

/**
 * Created on 07.10.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author$ -- $Revision$ -- $Date: 2005/10/26
 *          16:56:23 $ \
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 * @param <V>
 * 
 */
public class ListWithListModelOverList<V> extends AbstractList<V> implements
        ListWithListModel<V> {

    /**
     * {@link MyListModel} COMMENT.
     */
    private MyListModel listModel;

    /**
     * {@link List} COMMENT.
     */
    private List<V> innerList;

    /**
     * COMMENT.
     * 
     * @param innerListVal
     *            {@link List}
     */
    public ListWithListModelOverList(final List<V> innerListVal) {
        this.listModel = new MyListModel(this);
        if (innerListVal == null) {
            throw new NullPointerException("Inner List may not be null");
        }
        this.innerList = innerListVal;
    }

    /**
     * Created on 26.10.2005.
     * 
     * for project: tools
     * 
     * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss -
     *         Weiss und Schmidt, Mediale Systeme GbR</a>
     * @version $Author$ -- $Revision$ -- $Date: 2005/10/26
     *          16:56:23 $ <br>
     *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
     *          reserved.
     * 
     */
    private class MyListModel extends AbstractListModel {

        /**
         * COMMENT.
         * 
         * @param source
         *            {@link Object}
         */
        public MyListModel(final Object source) {
            super(source);
        }

        /**
         * @return <code>int</code>
         * @see javax.swing.ListModel#getSize()
         */
        public int getSize() {
            return ListWithListModelOverList.this.innerList.size();
        }

        /**
         * @param index
         *            <code>int</code>
         * @return {@link Object}
         * @see javax.swing.ListModel#getElementAt(int)
         */
        public Object getElementAt(final int index) {
            return ListWithListModelOverList.this.innerList.get(index);
        }
    }

    /**
     * @param index
     *            <code>int</code>
     * @param element
     *            of type V
     * @see java.util.List#add(int, java.lang.Object)
     */
    @Override
    public final synchronized void add(final int index, final V element) {
        this.innerList.add(index, element);
        this.listModel.fireIntervalAdded(index, index);
    }

    /**
     * @param index
     *            <code>int</code>
     * @return element of type V
     * @see java.util.List#remove(int)
     */
    @Override
    public final synchronized V remove(final int index) {
        V o = this.innerList.remove(index);
        this.listModel.fireIntervalRemoved(index, index);
        return o;
    }

    /**
     * @param index
     *            <code>int</code>
     * @param element
     *            of type V
     * @return element of type V
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public final V set(final int index, final V element) {
        V oldValue = this.innerList.set(index, element);
        this.listModel.fireContentsChanged(index, index);
        return oldValue;
    }

    /**
     * @param index
     *            <code>int</code>
     * @return element of type V
     * @see java.util.List#get(int)
     */
    @Override
    public final V get(final int index) {
        return this.innerList.get(index);
    }

    /**
     * @return <code>int</code>
     * @see java.util.Collection#size()
     */
    @Override
    public final int size() {
        return this.innerList.size();
    }

    /**
     * @return {@link ListModel}
     * @see de.wsdevel.tools.awt.model.ListWithListModel#getListModel()
     */
    public final ListModel getListModel() {
        return this.listModel;
    }

}
/*
 * $Log$
 * Revision 1.1  2007-03-13 18:48:30  sweissTFH
 * new better performing ListWithListModel
 *
 * Revision 1.3  2006/11/12 19:01:18  sweissTFH
 * new setwithlistmodel
 *
 * Revision 1.2  2006/06/09 23:53:44  sweissTFH
 * smaller changes and adde squareroot function to TImeCriticalAnimation
 *
 * Revision 1.1  2006/05/02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.6  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * 
 * Revision 1.5 2005/12/27 16:06:01
 * sweissTFH moved to java 5 and very big clean up!
 * 
 * Revision 1.4 2005/10/26 16:56:23 mschneiderTFH start of very big clean up and
 * commenting! (sw)
 * 
 */
