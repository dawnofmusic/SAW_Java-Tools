package de.wsdevel.tools.persistence;

import java.beans.ExceptionListener;
import java.beans.XMLEncoder;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created on 09.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: ischmidt $ -- $Revision: 1.2 $ -- $Date: 2005/10/31
 *          18:22:30 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public final class XMLEncoderFactory {

    /**
     * COMMENT.
     */
    private static final Log LOG = LogFactory.getLog(XMLEncoderFactory.class);

    /**
     * Hidden constructor.
     */
    private XMLEncoderFactory() {
    }

    /**
     * COMMENT.
     * 
     * @param out
     *            {@link OutputStream}
     * @return {@link XMLEncoder}
     */
    public static XMLEncoder createDefaultXMLEncoder(final OutputStream out) {
        XMLEncoder enc = new XMLEncoder(out);

        // some default persistence delegates
        enc.setPersistenceDelegate(URL.class, new URLPersistenceDelegate());

        enc.setExceptionListener(new ExceptionListener() {
            public void exceptionThrown(final Exception e) {
                LOG.error(e.getLocalizedMessage(), LOG.isDebugEnabled() ? e
                        : null);
            }
        });

        return enc;
    }
}
/*
 * $Log: XMLEncoderFactory.java,v $
 * Revision 1.2  2007-08-06 17:33:39  ischmidt
 * switched to commmons logging
 * Revision 1.1 2006/05/02 16:06:00 sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 * 
 * Revision 1.4 2006/04/05 18:19:35 sweissTFH cleaned up checkstyle errors
 * Revision 1.3 2005/11/20 15:20:34 sweissTFH added logger as an exception
 * listener
 * 
 * Revision 1.2 2005/10/31 18:22:30 sweissTFH clean up and commenting
 * 
 */
