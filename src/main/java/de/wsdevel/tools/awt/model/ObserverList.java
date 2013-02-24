package de.wsdevel.tools.awt.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created on 30.11.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.4 $ -- $Date: 2005/10/26
 *          16:56:23 $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 * @param <L>
 *            class of listener
 * 
 */
public class ObserverList<L> {

    /**
     * {@link LinkedList} containing the listeners.
     */
    private LinkedList<L> listeners = new LinkedList<L>();

    /**
     * Created on 26.10.2005.
     * 
     * for project: tools
     * 
     * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss -
     *         Weiss und Schmidt, Mediale Systeme GbR</a>
     * @version $Author: sweiss $ -- $Revision: 1.4 $ -- $Date: 2005/10/26
     *          16:56:23 $
     * 
     * <br>
     *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
     *          reserved.
     * 
     * @param <L>
     *            class of listener
     */
    public static interface Action<L> {
	/**
	 * @param listener
	 *            {@link Object}
	 */
	void doit(L listener);
    }

    /**
     * @param doit
     *            {@link Action} to be executed on observation
     */
    @SuppressWarnings("unchecked")
    public final void observe(final Action<L> doit) {
	List<L> copy;
	synchronized (this.listeners) {
	    copy = (List<L>) this.listeners.clone();
	}
	Iterator<L> it = copy.iterator();
	while (it.hasNext()) {
	    informListener(doit, it.next());
	}
    }

    /**
     * @param doit
     *            {@link Action}
     * @param listener
     *            {@link Object}
     */
    protected void informListener(final Action<L> doit, final L listener) {
	doit.doit(listener);
    }

    /**
     * @param listener
     *            {@link Object}
     */
    public final void addListener(final L listener) {
	this.listeners.add(listener);
    }

    /**
     * @param listener
     *            {@link Object}
     */
    public final void removeListener(final L listener) {
	this.listeners.remove(listener);
    }

}
/*
 * $Log: ObserverList.java,v $
 * Revision 1.4  2009-02-09 13:15:13  sweiss
 * clean up of stuff
 * Revision 1.3 2006/06/10 13:00:32 sweissTFH
 * cleanup and smaller changes due to new compiler settings
 * 
 * Revision 1.2 2006/05/29 09:27:08 sweissTFH unworking version of new space
 * 
 * Revision 1.1 2006/05/02 16:06:00 sweissTFH cleaned up tools and moved
 * everything to appropriate new packages
 * 
 * Revision 1.6 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
 * 
 * Revision 1.5 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean
 * up!
 * 
 * Revision 1.4 2005/10/26 16:56:23 mschneiderTFH start of very big clean up and
 * commenting! (sw)
 */
