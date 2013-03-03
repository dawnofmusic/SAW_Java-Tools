package de.wsdevel.tools.awt.model;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.wsdevel.tools.common.interfaces.Filter;

/**
 * Created on 28.12.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.6 $ -- $Date: 2005/10/26 16:56:23
 *          $ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * @param <T>
 *            type of objects to be filtered
 */
public class FilteredListModel<T> extends AbstractListModel implements
	ListModel {

    /**
     * <code>long</code> serial version unique id.
     */
    private static final long serialVersionUID = -262426259791744343L;

    /**
     * {@link ListModel} COMMENT.
     */
    private ListModel innerListModel;

    /**
     * {@link Hashtable} COMMENT.
     */
    private Hashtable<Integer, Integer> indexes = new Hashtable<Integer, Integer>();

    /**
     * {@link Filter} COMMENT.
     */
    private Filter<T> filter;

    /**
     * COMMENT.
     * 
     * @param inner
     *            {@link ListModel}
     * @param filterVal
     *            {@link Filter}
     */
    public FilteredListModel(final ListModel inner, final Filter<T> filterVal) {
	this.filter = filterVal;
	this.innerListModel = inner;
	this.innerListModel
		.addListDataListener(createListenerToInnerListModel());
	filterElements();
    }

    /**
     * COMMENT.
     * 
     */
    @SuppressWarnings("unchecked")
    private void filterElements() {
	if (this.indexes.size() > 0) {
	    int oldSize = this.indexes.size();
	    this.indexes.clear();
	    fireIntervalRemoved(this, 0, oldSize - 1);
	}
	int newIndex = 0;
	for (int i = 0; i < this.innerListModel.getSize(); i++) {
	    if (this.filter.accept((T) this.innerListModel.getElementAt(i))) {
		this.indexes.put(Integer.valueOf(newIndex), Integer.valueOf(i));
		fireIntervalAdded(this, newIndex, newIndex);
		newIndex++;
	    }
	}
    }

    /**
     * COMMENT.
     * 
     * @return {@link ListDataListener}
     */
    private ListDataListener createListenerToInnerListModel() {
	return new ListDataListener() {
	    @SuppressWarnings("unchecked")
	    public void contentsChanged(final ListDataEvent e) {
		Integer innerIndex;
		for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
		    innerIndex = Integer.valueOf(i);
		    if (FilteredListModel.this.indexes
			    .containsValue(innerIndex)) {
			if (!FilteredListModel.this.filter
				.accept((T) FilteredListModel.this.innerListModel
					.getElementAt(i))) {
			    removeInnerIndex(innerIndex);
			} else {
			    updateInnerIndex(innerIndex);
			}
		    } else if (FilteredListModel.this.filter
			    .accept((T) FilteredListModel.this.innerListModel
				    .getElementAt(i))) {
			addInnerIndex(innerIndex);
		    }
		}
		// filterElements();
	    }

	    @SuppressWarnings("unchecked")
	    public void intervalAdded(final ListDataEvent e) {
		Integer innerIndex;
		increaseIndex(e.getIndex0(), e.getIndex1() - e.getIndex0() + 1);
		for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
		    innerIndex = Integer.valueOf(i);
		    if (FilteredListModel.this.filter
			    .accept((T) FilteredListModel.this.innerListModel
				    .getElementAt(i))) {
			addInnerIndex(innerIndex);
		    }
		    // filterElements();
		}
	    }

	    public void intervalRemoved(final ListDataEvent e) {
		Integer innerIndex;
		LinkedList<Integer> removedEntries = new LinkedList<Integer>();
		for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
		    innerIndex = Integer.valueOf(i);
		    if (FilteredListModel.this.indexes
			    .containsValue(innerIndex)) {
			Iterator<Entry<Integer, Integer>> it = FilteredListModel.this.indexes
				.entrySet().iterator();
			while (it.hasNext()) {
			    Entry<Integer, Integer> entry = it.next();
			    if (entry.getValue().equals(innerIndex)) {
				Integer key = entry.getKey();
				FilteredListModel.this.indexes.remove(key);
				for (int j = key.intValue() + 1; j <= FilteredListModel.this.indexes
					.size(); j++) {
				    Integer val = FilteredListModel.this.indexes
					    .remove(Integer.valueOf(j));
				    FilteredListModel.this.indexes.put(
					    Integer.valueOf(j - 1), val);
				}
				removedEntries.add(key);
				break;
			    }
			}
		    }
		}
		decreaseIndex(e.getIndex0(), e.getIndex1() - e.getIndex0() + 1);
		int remIndex;
		for (int j = removedEntries.size() - 1; j >= 0; j--) {
		    remIndex = removedEntries.get(j).intValue();
		    fireIntervalRemoved(FilteredListModel.this, remIndex,
			    remIndex);
		}
		// filterElements();
	    }
	};
    }

    /**
     * COMMENT.
     * 
     * @param startIndex
     *            <code>int</code>
     * @param amount
     *            <code>int</code>
     */
    private void increaseIndex(final int startIndex, final int amount) {
	Iterator<Entry<Integer, Integer>> it = this.indexes.entrySet()
		.iterator();
	Integer val;
	while (it.hasNext()) {
	    Map.Entry<Integer, Integer> entry = it.next();
	    val = entry.getValue();
	    if (val.intValue() >= startIndex) {
		this.indexes.put(entry.getKey(),
			Integer.valueOf(val.intValue() + amount));
	    } else {
		this.indexes.put(entry.getKey(), entry.getValue());
	    }
	}
    }

    /**
     * COMMENT.
     * 
     * @param startIndex
     *            <code>int</code>
     * @param amount
     *            <code>int</code>
     */
    private void decreaseIndex(final int startIndex, final int amount) {
	Iterator<Entry<Integer, Integer>> it = this.indexes.entrySet()
		.iterator();
	Integer val;
	while (it.hasNext()) {
	    Map.Entry<Integer, Integer> entry = it.next();
	    val = entry.getValue();
	    if (val.intValue() >= startIndex) {
		this.indexes.put(entry.getKey(),
			Integer.valueOf(val.intValue() - amount));
	    } else {
		this.indexes.put(entry.getKey(), entry.getValue());
	    }
	}
    }

    /**
     * COMMENT.
     * 
     * @param innerIndex
     *            {@link Integer}
     */
    private void updateInnerIndex(final Integer innerIndex) {
	Iterator<Entry<Integer, Integer>> it = this.indexes.entrySet()
		.iterator();
	while (it.hasNext()) {
	    Map.Entry<Integer, Integer> entry = it.next();
	    int outerIndex = entry.getKey().intValue();
	    if (entry.getValue().equals(innerIndex)) {
		fireContentsChanged(this, outerIndex, outerIndex);
		break;
	    }
	}
    }

    /**
     * COMMENT.
     * 
     * @param innerIndex
     *            {@link Integer}
     */
    private void addInnerIndex(final Integer innerIndex) {
	int newIndex = this.indexes.size();
	this.indexes.put(Integer.valueOf(newIndex), innerIndex);
	fireIntervalAdded(this, newIndex, newIndex);
    }

    /**
     * COMMENT.
     * 
     * @param innerIndex
     *            {@link Integer}
     */
    private void removeInnerIndex(final Integer innerIndex) {
	Iterator<Entry<Integer, Integer>> it = this.indexes.entrySet()
		.iterator();
	while (it.hasNext()) {
	    Map.Entry<Integer, Integer> entry = it.next();
	    if (entry.getValue().equals(innerIndex)) {
		Integer key = entry.getKey();
		this.indexes.remove(key);
		for (int j = key.intValue() + 1; j <= this.indexes.size(); j++) {
		    Integer val = this.indexes.remove(new Integer(j));
		    this.indexes.put(new Integer(j - 1), val);
		}
		fireIntervalRemoved(this, key.intValue(), key.intValue());
		break;
	    }
	}
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
// $Log: FilteredListModel.java,v $
// Revision 1.6 2009-02-10 08:58:56 sweiss
// bug fixing
//
// Revision 1.5 2009-02-09 16:54:25 sweiss
// bug fixing and cleanup
//
// Revision 1.4 2009-02-09 13:15:13 sweiss
// clean up of stuff
//
// Revision 1.3 2007-08-03 13:13:19 sweiss
// added
// FilteredCollection
//
// Revision 1.2 2007/02/27 19:50:27 mschneiderTFH
// empty log message
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
