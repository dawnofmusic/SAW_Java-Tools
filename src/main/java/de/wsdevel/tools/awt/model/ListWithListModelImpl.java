package de.wsdevel.tools.awt.model;

import java.util.AbstractList;

import javax.swing.ListModel;

import de.wsdevel.tools.awt.model.ListDataListenerSupport.ObserverType;

/**
 * Created on 13.03.2007.
 * 
 * for project: Java_Tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.9 $ -- $Date: 2007-09-01 21:00:14
 *          $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 * @param <V>
 *            Type of contained Elements
 * 
 */
public class ListWithListModelImpl<V> extends AbstractList<V> implements
	ListWithListModel<V> {

    /**
     * {@link AbstractListModel} COMMENT.
     */
    private AbstractListModel innerLM;

    /**
     * {@link HashMapList< V>} COMMENT.
     */
    private HashMapList<V> elements;

    /**
     * COMMENT.
     */
    public ListWithListModelImpl() {
	this.elements = new HashMapList<V>();
	this.innerLM = new AbstractListModel(this) {

	    public Object getElementAt(final int index) {
		return ListWithListModelImpl.this.elements.get(index);
	    }

	    public int getSize() {
		return ListWithListModelImpl.this.elements.size();
	    }
	};
    }

    /**
     * COMMENT.
     * 
     * @param observerType
     *            {@link ObserverType}
     */
    public ListWithListModelImpl(final ObserverType observerType) {
	this.elements = new HashMapList<V>();
	this.innerLM = new AbstractListModel(this, observerType) {

	    public Object getElementAt(final int index) {
		return ListWithListModelImpl.this.elements.get(index);
	    }

	    public int getSize() {
		return ListWithListModelImpl.this.elements.size();
	    }
	};
    }

    /**
     * @param index
     *            <code>int</code>
     * @param element
     *            of type V
     * @see java.util.List#add(int, java.lang.Object)
     */
    @Override
    public final void add(final int index, final V element) {
	synchronized (this.elements) {
	    this.elements.add(index, element);
	}
	this.innerLM.fireIntervalAdded(index, index);
    }

    /**
     * @param index
     *            <code>int</code>
     * @return element of type V
     * @see java.util.List#remove(int)
     */
    @Override
    public final V remove(final int index) {
	V o = null;
	synchronized (this.elements) {
	    o = this.elements.remove(index);
	}
	this.innerLM.fireIntervalRemoved(index, index);
	return o;
    }

    /**
     * @see java.util.AbstractList#clear()
     */
    @Override
    public final void clear() {
	int oldSize = 0;
	synchronized (this.elements) {
	    oldSize = this.elements.size();
	    if (oldSize > 0) {
		this.elements.clear();
	    }
	}
	if (oldSize > 0) {
	    this.innerLM.fireIntervalRemoved(0, oldSize - 1);
	}
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
	V oldValue = null;
	synchronized (this.elements) {
	    oldValue = this.elements.set(index, element);
	}
	this.innerLM.fireContentsChanged(index, index);
	return oldValue;
    }

    /**
     * @param index
     *            <code>int</code>
     * @return V
     * @see java.util.AbstractList#get(int)
     */
    @Override
    public final V get(final int index) {
	return this.elements.get(index);
    }

    /**
     * @return <code>int</code>
     * @see java.util.AbstractCollection#size()
     */
    @Override
    public final int size() {
	return this.elements.size();
    }

    /**
     * @return {@link ListModel}
     * @see de.wsdevel.tools.awt.model.ListWithListModel#getListModel()
     */
    public final ListModel getListModel() {
	return this.innerLM;
    }

}
/*
 * $Log: ListWithListModelImpl.java,v $ Revision 1.9 2007-09-01 21:00:14 sweiss
 * fixed bug firing remove events if list is empty
 * 
 * Revision 1.8 2007-05-31 14:02:18 sweiss a lot of bug fixes and performance
 * issues
 * 
 * Revision 1.7 2007-05-30 17:02:25 sweiss some ui improvements on
 * listwithlistmodel
 * 
 * Revision 1.6 2007-03-18 18:30:03 sweissTFH a lot of event consuming stuff for
 * listDataListeners
 * 
 * Revision 1.5 2007/03/13 18:56:08 sweissTFH bug fix
 * 
 * Revision 1.4 2007/03/13 18:48:30 sweissTFH new better performing
 * ListWithListModel
 */
