package de.wsdevel.tools.commands;

/**
 * Created on 08.12.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2005/10/26
 *          16:56:23 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public interface ThrowableHandler {
    /**
     * COMMENT.
     * 
     * @param t
     *            {@link Throwable}
     */
    void handle(Throwable t);

    /**
     * {@link ThrowableHandler} COMMENT.
     */
    ThrowableHandler IGNORE = new ThrowableHandler() {
        public void handle(final Throwable t) {
        }
    };
}
/*
 * $Log: ThrowableHandler.java,v $
 * Revision 1.1  2006-05-02 16:06:01  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.3  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.2 2005/10/26 16:56:23
 * mschneiderTFH start of very big clean up and commenting! (sw)
 * 
 */
