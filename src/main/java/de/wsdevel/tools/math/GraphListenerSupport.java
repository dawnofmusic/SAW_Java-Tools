package de.wsdevel.tools.math;

import de.wsdevel.tools.awt.model.observer.ObserverList;

/**
 * Created on 06.04.2009.
 * 
 * (c) 2008, Sebastian A. Weiss - All rights reserved.
 * 
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author$ -- $Revision$ -- $Date$
 */
public class GraphListenerSupport {

    /**
     * {@link ObserverList<GraphListener>} COMMENT.
     */
    private ObserverList<GraphListener> listeners = new ObserverList<GraphListener>();

    /**
     * @param listener
     *            {@link GraphListener}
     */
    public final void addListener(final GraphListener listener) {
	this.listeners.addListener(listener);
    }

    /**
     * COMMENT.
     */
    public final void fireGraphChanged() {
	this.listeners.observe(new ObserverList.Action<GraphListener>() {
	    public void doit(final GraphListener listener) {
		listener.graphChanged();
	    }
	});
    }

    /**
     * @param listener
     *            {@link GraphListener}
     */
    public final void removeListener(final GraphListener listener) {
	this.listeners.removeListener(listener);
    }

}
//
// $Log$
// Revision 1.1 2009-04-09 09:57:31 sweiss
// real time logging of probes
//
//
