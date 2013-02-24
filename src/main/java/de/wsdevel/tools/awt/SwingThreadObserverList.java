package de.wsdevel.tools.awt;

import de.wsdevel.tools.awt.model.ObserverList;

/**
 * Created on 08.12.2003 from.
 * 
 * @author Sebastian A. Weiss
 * 
 * @version $Author: mschneiderTFH $ -- $Revision: 1.4 $ -- $Date: 2005/12/27
 *          16:06:01 $
 * 
 * for project: tools
 * 
 * Copyright by dawn of music - 2003
 * 
 * @param <L> class of listener
 */
public class SwingThreadObserverList<L> extends ObserverList<L> {

    /**
     * @param doit
     *            {@link ObserverList.Action}
     * @param listener
     *            {@link Object}
     * @see ObserverList#informListener(Action, Object)
     */
    @Override
    protected final void informListener(final ObserverList.Action<L> doit,
            final L listener) {
        SwingThreadAdapter.runInSwingThread(new Runnable() {
            public final void run() {
                SwingThreadObserverList.super.informListener(doit, listener);
            }
        });
    }

}
/*
 * $Log: SwingThreadObserverList.java,v $
 * Revision 1.4  2006-06-27 14:55:59  mschneiderTFH
 * fixed type safety
 *
 * Revision 1.3  2006/06/10 13:00:32  sweissTFH
 * cleanup and smaller changes due to new compiler settings
 *
 * Revision 1.2  2006/05/29 09:27:08  sweissTFH
 * unworking version of new space
 *
 * Revision 1.1  2006/05/02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.7  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * 
 * Revision 1.6 2005/12/27 16:06:01
 * sweissTFH moved to java 5 and very big clean up!
 * 
 * Revision 1.5 2005/10/26 16:56:23 mschneiderTFH start of very big clean up and
 * commenting! (sw)
 * 
 */
