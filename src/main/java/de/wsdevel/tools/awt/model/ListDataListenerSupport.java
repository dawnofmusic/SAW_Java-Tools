package de.wsdevel.tools.awt.model;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.wsdevel.tools.awt.model.observer.ObserverList;
import de.wsdevel.tools.awt.model.observer.ObserverList.Action;
import de.wsdevel.tools.awt.model.observer.SwingThreadObserverList;

/**
 * Created on 07.10.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.4 $ -- $Date: 2005/10/26
 *          16:56:23 $ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public class ListDataListenerSupport {

    /**
     * Created on 03.03.2013 for project: SAW_Java-Tools
     * 
     * (c) 2012 Sebastian A. Weiß - All rights reserved.
     * 
     * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
     * @version $Author: $ -- $Revision: $ -- $Date: $
     */
    public static enum ObserverType {

	/**
	 * {@link ObserverType}.
	 */
	KEEP_IN_THREAD,

	/**
	 * {@link ObserverType}.
	 */
	RUN_IN_SWINGTHREAD;

    }

    /**
     * {@link Object} COMMENT.
     */
    private final Object source;

    /**
     * {@link ObserverList} COMMENT.
     */
    private ObserverList<ListDataListener> listeners;

    /**
     * COMMENT.
     * 
     * @param sourceVal
     *            {@link Object}
     */
    public ListDataListenerSupport(final Object sourceVal) {
	this(sourceVal, ObserverType.RUN_IN_SWINGTHREAD);
    }

    /**
     * COMMENT.
     * 
     * @param sourceVal
     *            {@link Object}
     */
    public ListDataListenerSupport(final Object sourceVal,
	    final ObserverType observerType) {
	this.source = sourceVal;
	switch (observerType) {
	case KEEP_IN_THREAD:
	    this.listeners = new ObserverList<ListDataListener>();
	    break;
	case RUN_IN_SWINGTHREAD:
	default:
	    this.listeners = new SwingThreadObserverList<ListDataListener>();
	    break;
	}
    }

    /**
     * COMMENT.
     * 
     * @param listener
     *            {@link ListDataListener}
     */
    public final void addListDataListener(final ListDataListener listener) {
	this.listeners.addListener(listener);
    }

    /**
     * COMMENT.
     * 
     * @param index0
     *            <code>int</code>
     * @param index1
     *            <code>int</code>
     */
    public final void fireContentsChanged(final int index0, final int index1) {
	final ListDataEvent evt = new ListDataEvent(this.source,
		ListDataEvent.CONTENTS_CHANGED, index0, index1);
	this.listeners.observe(new Action<ListDataListener>() {
	    public void doit(final ListDataListener listener) {
		listener.contentsChanged(evt);
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param index0
     *            <code>int</code>
     * @param index1
     *            <code>int</code>
     */
    public final void fireIntervalAdded(final int index0, final int index1) {
	final ListDataEvent evt = new ListDataEvent(this.source,
		ListDataEvent.INTERVAL_ADDED, index0, index1);
	this.listeners.observe(new Action<ListDataListener>() {
	    public void doit(final ListDataListener listener) {
		listener.intervalAdded(evt);
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param index0
     *            <code>int</code>
     * @param index1
     *            <code>int</code>
     */
    public final void fireIntervalRemoved(final int index0, final int index1) {
	final ListDataEvent evt = new ListDataEvent(this.source,
		ListDataEvent.INTERVAL_REMOVED, index0, index1);
	this.listeners.observe(new Action<ListDataListener>() {
	    public void doit(final ListDataListener listener) {
		listener.intervalRemoved(evt);
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param listener
     *            {@link ListDataListener}
     */
    public final void removeListDataListener(final ListDataListener listener) {
	this.listeners.removeListener(listener);
    }

}
/*
 * $Log: ListDataListenerSupport.java,v $ Revision 1.4 2006-06-10 13:00:32
 * sweissTFH cleanup and smaller changes due to new compiler settings
 * 
 * Revision 1.3 2006/06/06 16:39:37 mschneiderTFH update to java 1.5
 * 
 * Revision 1.2 2006/05/29 09:27:08 sweissTFH unworking version of new space
 * 
 * Revision 1.1 2006/05/02 16:06:00 sweissTFH cleaned up tools and moved
 * everything to appropriate new packages
 * 
 * Revision 1.7 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
 * 
 * Revision 1.6 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean
 * up!
 * 
 * Revision 1.5 2005/10/26 16:56:23 mschneiderTFH start of very big clean up and
 * commenting! (sw)
 */
