package de.wsdevel.tools.awt.model;

import java.util.Comparator;
import java.util.Hashtable;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

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
 * @param <T>
 *            type of objects to be sorted
 */
public class SortedListModel<T> extends AbstractListModel implements ListModel {

    /**
     * <code>long</code> serial version unique id.
     */
    private static final long serialVersionUID = 4052477445696389360L;

    /**
     * {@link Comparator} COMMENT.
     */
    private Comparator<T> sorter;

    /**
     * {@link ListModel} COMMENT.
     */
    private ListModel innerListModel;

    /**
     * {@link Hashtable} COMMENT.
     */
    private Hashtable<Integer, Integer> indexes = new Hashtable<Integer, Integer>();

    /**
     * {@link Comparator}< {@link Object}> COMMENT.
     */
    public static final Comparator<Object> DEFAULT_OBJECT_SORTER = new Comparator<Object>() {
	public int compare(final Object o1, final Object o2) {
	    return o1.toString().compareToIgnoreCase(o2.toString());
	}
    };

    /**
     * COMMENT.
     * 
     * @param inner
     *            {@link ListModel}
     * @param sorterVal
     *            {@link Comparator}
     */
    public SortedListModel(final ListModel inner, final Comparator<T> sorterVal) {
	this.sorter = sorterVal;
	this.innerListModel = inner;
	this.innerListModel
		.addListDataListener(createListenereToInnerListModel());
	sort();
    }

    /**
     * COMMENT.
     * 
     * @return {@link ListDataListener}
     */
    private ListDataListener createListenereToInnerListModel() {
	return new ListDataListener() {
	    public void contentsChanged(final ListDataEvent e) {
		sort();
	    }

	    public void intervalAdded(final ListDataEvent e) {
		sort();
	    }

	    public void intervalRemoved(final ListDataEvent e) {
		sort();
	    }
	};
    }

    /**
     * COMMENT.
     * 
     */
    @SuppressWarnings("unchecked")
    private void sort() {
	if (this.indexes.size() > 0) {
	    int oldSize = this.indexes.size();
	    this.indexes.clear();
	    fireIntervalRemoved(this, 0, oldSize - 1);
	}
	for (int i = 0; i < this.innerListModel.getSize(); i++) {
	    this.indexes.put(Integer.valueOf(i), Integer.valueOf(i));
	}
	for (int i = 0; i < this.innerListModel.getSize(); i++) {
	    for (int j = i + 1; j < this.innerListModel.getSize(); j++) {
		if (this.sorter.compare((T) this.innerListModel
			.getElementAt((this.indexes.get(Integer.valueOf(i)))
				.intValue()), (T) this.innerListModel
			.getElementAt((this.indexes.get(Integer.valueOf(j)))
				.intValue())) > 0) {
		    swap(i, j);
		}
	    }
	}
	if (this.indexes.size() > 0) {
	    fireIntervalAdded(this, 0, this.indexes.size() - 1);
	}
    }

    /**
     * COMMENT.
     * 
     * @param i
     *            <code>int</code>
     * @param j
     *            <code>int</code>
     */
    public final void swap(final int i, final int j) {
	Integer intI = Integer.valueOf(i);
	Integer intJ = Integer.valueOf(j);
	Integer tmp = this.indexes.get(intI);
	this.indexes.put(intI, this.indexes.get(intJ));
	this.indexes.put(intJ, tmp);
    }

    /**
     * @return <code>int</code>
     * @see javax.swing.ListModel#getSize()
     */
    public final int getSize() {
	return this.indexes.size();
    }

    /**
     * @param index
     *            <code>int</code>
     * @return {@link Object}
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public final Object getElementAt(final int index) {
	return this.innerListModel.getElementAt((this.indexes.get(Integer
		.valueOf(index))));
    }

}
//
// $Log: SortedListModel.java,v $
// Revision 1.5  2009-02-10 08:58:57  sweiss
// bug fixing
//
// Revision 1.4 2009-02-09 16:54:25 sweiss
// bug fixing and cleanup
//
// Revision 1.3 2009-02-09 13:15:13 sweiss
// clean
// up of stuff
// 
// Revision 1.2 2006/10/10 15:04:57 sweissTFH
// cleanup
// 
// Revision 1.1 2006/05/02 16:06:00 sweissTFH
// cleaned up tools and moved
// everything to appropriate new packages
// 
// Revision 1.4 2006/04/05 18:19:34 sweissTFH
// cleaned up checkstyle errors
// 
// Revision 1.3 2005/12/27 16:06:01 sweissTFH
// moved to java 5 and very big clean
// up!
// 
// Revision 1.2 2005/10/26 16:56:23 mschneiderTFH
// start of very big clean up and
// commenting! (sw)
//
