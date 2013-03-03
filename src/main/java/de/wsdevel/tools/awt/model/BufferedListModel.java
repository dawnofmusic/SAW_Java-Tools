package de.wsdevel.tools.awt.model;

import java.util.LinkedList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.wsdevel.tools.awt.model.ListDataListenerSupport.ObserverType;

/**
 * Created on 30.12.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: mschneiderTFH $ -- $Revision: 1.2 $ -- $Date: 2005/10/26
 *          16:56:23 $ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public class BufferedListModel implements ListModel {

    /**
     * {@link ListModel} COMMENT.
     */
    private ListModel inner;

    /**
     * {@link LinkedList} COMMENT.
     */
    private LinkedList<Object> buffer = new LinkedList<Object>();

    /**
     * {@link ListDataListenerSupport} COMMENT.
     */
    private ListDataListenerSupport ldls;

    /**
     * COMMENT.
     * 
     * @param innerVal
     *            {@link ListModel}
     */
    public BufferedListModel(final ListModel innerVal) {
	this(innerVal, ObserverType.RUN_IN_SWINGTHREAD);
    }

    /**
     * COMMENT.
     * 
     * @param innerVal
     *            {@link ListModel}
     */
    public BufferedListModel(final ListModel innerVal,
	    final ObserverType observerType) {
	this.inner = innerVal;
	this.ldls = new ListDataListenerSupport(this, observerType);
	innerVal.addListDataListener(createListDataListenerToInnerListModel());
	fillBuffer();
    }

    /**
     * COMMENT.
     * 
     * @return {@link ListDataListener}
     */
    private ListDataListener createListDataListenerToInnerListModel() {
	return new ListDataListener() {
	    public void contentsChanged(final ListDataEvent e) {
		for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
		    synchronized (BufferedListModel.this.buffer) {
			BufferedListModel.this.buffer.remove(i);
			BufferedListModel.this.buffer.add(i,
				BufferedListModel.this.inner.getElementAt(i));
		    }
		}
		BufferedListModel.this.ldls.fireContentsChanged(e.getIndex0(),
			e.getIndex1());
	    }

	    public void intervalAdded(final ListDataEvent e) {
		for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
		    synchronized (BufferedListModel.this.buffer) {
			BufferedListModel.this.buffer.add(i,
				BufferedListModel.this.inner.getElementAt(i));
		    }
		}
		BufferedListModel.this.ldls.fireIntervalAdded(e.getIndex0(),
			e.getIndex1());
	    }

	    public void intervalRemoved(final ListDataEvent e) {
		for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
		    synchronized (BufferedListModel.this.buffer) {
			BufferedListModel.this.buffer.remove(i);
		    }
		}
		BufferedListModel.this.ldls.fireIntervalRemoved(e.getIndex0(),
			e.getIndex1());
	    }
	};
    }

    /**
     * COMMENT.
     */
    private synchronized void fillBuffer() {
	for (int i = 0; i < this.inner.getSize(); i++) {
	    this.buffer.add(i, this.inner.getElementAt(i));
	}
    }

    /**
     * @return <code>int</code>
     * @see javax.swing.ListModel#getSize()
     */
    public final synchronized int getSize() {
	return this.buffer.size();
    }

    /**
     * @param index
     *            <code>int</code>
     * @return {@link Object}
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public final synchronized Object getElementAt(final int index) {
	return this.buffer.get(index);
    }

    /**
     * @param l
     *            {@link ListDataListener}
     * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
     */
    public final void addListDataListener(final ListDataListener l) {
	this.ldls.addListDataListener(l);
    }

    /**
     * @param l
     *            {@link ListDataListener}
     * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
     */
    public final void removeListDataListener(final ListDataListener l) {
	this.ldls.removeListDataListener(l);
    }

}
/*
 * $Log: BufferedListModel.java,v $ Revision 1.2 2006-06-06 16:39:37
 * mschneiderTFH update to java 1.5
 * 
 * Revision 1.1 2006/05/02 16:06:00 sweissTFH cleaned up tools and moved
 * everything to appropriate new packages
 * 
 * Revision 1.5 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
 * 
 * Revision 1.4 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean
 * up!
 * 
 * Revision 1.3 2005/10/26 16:56:23 mschneiderTFH start of very big clean up and
 * commenting! (sw)
 */
