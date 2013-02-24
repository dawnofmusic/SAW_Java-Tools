package de.wsdevel.tools.awt.model;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.wsdevel.tools.awt.SwingThreadAdapter;

/**
 * Created on 18.03.2007.
 * 
 * for project: Java_Tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author$ -- $Revision$ -- $Date$
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public class EventConsumingListModel extends AbstractListModel implements
	ListModel {

    /**
     * {@link ListModel} COMMENT.
     */
    private ListModel inner;

    /**
     * COMMENT.
     * 
     * @param innerRef
     */
    public EventConsumingListModel(final ListModel innerRef) {
	super(innerRef);
	this.inner = innerRef;

	this.inner.addListDataListener(new ListDataListener() {

	    public void contentsChanged(ListDataEvent e) {
		reset(innerRef.getSize());
	    }

	    public void intervalAdded(ListDataEvent e) {
		reset(innerRef.getSize());
	    }

	    public void intervalRemoved(ListDataEvent e) {
		reset(innerRef.getSize());
	    }
	});
    }

    /**
     * {@link Timer} COMMENT.
     */
    private Timer componentBuilder = new Timer("Component-Builder");

    /**
     * <code>boolean</code> COMMENT.
     */
    private boolean allreadyScheduled = false;

    /**
     * COMMENT.
     * 
     */
    private void reset(final int oldSize) {
	if (!this.allreadyScheduled) {
	    this.allreadyScheduled = true;
	    this.componentBuilder.schedule(new TimerTask() {
		@Override
		public void run() {
		    SwingThreadAdapter.runInSwingThread(new Runnable() {
			public void run() {
			    if (oldSize > 0) {
				fireIntervalRemoved(0, oldSize - 1);
			    }
			    synchronized (EventConsumingListModel.this.inner) {
				if (EventConsumingListModel.this.inner
					.getSize() > 0) {
				    fireIntervalAdded(0,
					    EventConsumingListModel.this.inner
						    .getSize() - 1);
				}
			    }
			}
		    });
		    EventConsumingListModel.this.allreadyScheduled = false;
		}
	    }, 500);
	}
    }

    /**
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public Object getElementAt(int index) {
	return this.inner.getElementAt(index);
    }

    /**
     * @see javax.swing.ListModel#getSize()
     */
    public int getSize() {
	return this.inner.getSize();
    }

}
/*
 * $Log$
 * Revision 1.2  2009-02-10 08:58:57  sweiss
 * bug fixing
 * Revision 1.1 2007/03/18 18:30:03 sweissTFH a lot of event consuming
 * stuff for listDataListeners
 */
