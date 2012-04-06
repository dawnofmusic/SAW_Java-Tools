package de.wsdevel.tools.persistence;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.net.URL;

/**
 * Created on 09.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.2 $ -- $Date: 2005/10/31
 *          18:22:30 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public class URLPersistenceDelegate extends PersistenceDelegate {

    /**
     * @param oldInstance
     *            {@link Object}
     * @param out
     *            {@link Encoder}
     * @return {@link Expression}
     * @see java.beans.PersistenceDelegate#instantiate(java.lang.Object,
     *      java.beans.Encoder)
     */
    @Override
    protected final Expression instantiate(final Object oldInstance,
            final Encoder out) {
        return new Expression(oldInstance, oldInstance.getClass(), "new",
                new Object[] { ((URL) oldInstance).toString() });
    }
}
/*
 * $Log: URLPersistenceDelegate.java,v $
 * Revision 1.2  2006-06-10 13:00:33  sweissTFH
 * cleanup and smaller changes due to new compiler settings
 *
 * Revision 1.1  2006/05/02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.3  2006/04/05 18:19:35  sweissTFH
 * cleaned up checkstyle errors
 * 
 * Revision 1.2 2005/10/31 18:22:30
 * sweissTFH clean up and commenting
 * 
 */
