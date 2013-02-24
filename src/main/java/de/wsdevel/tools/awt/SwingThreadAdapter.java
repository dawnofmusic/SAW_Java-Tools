package de.wsdevel.tools.awt;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

/**
 * Created on 08.12.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.2 $ -- $Date: 2005/10/31
 *          18:22:30 $ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class SwingThreadAdapter {

    /**
     * this is a hidden constructor.
     */
    private SwingThreadAdapter() {
    }

    /**
     * @param run
     *            {@link Runnable}
     */
    public static void runInSwingThread(final Runnable run) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(run);
        } else {
            run.run();
        }
    }

    /**
     * @param run
     *            {@link Runnable}
     * @throws InterruptedException
     *             COMMENT
     * @throws InvocationTargetException
     *             COMMENT
     */
    public static void runImmediatlyInSwingThread(final Runnable run)
            throws InterruptedException, InvocationTargetException {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeAndWait(run);
        } else {
            run.run();
        }
    }

}
//
// $Log: SwingThreadAdapter.java,v $
// Revision 1.2  2008-06-24 06:01:46  sweiss
// cleanup
//
// Revision 1.1  2006-05-02 16:06:00  sweissTFH
// cleaned up tools and moved everything to appropriate new packages
//
// Revision 1.4  2006/04/05 18:19:35  sweissTFH
// cleaned up checkstyle errors
//
// Revision 1.3 2005/10/31 18:22:30 sweissTFH 
// clean up and commenting
// 
// Revision 1.2 2005/08/18 12:41:31 mschneiderTFH 
// added comments
// 
//
