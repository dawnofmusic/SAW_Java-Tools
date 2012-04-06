package de.wsdevel.tools.common.interfaces;

import java.io.Serializable;

/**
 * Created on 08.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2005/10/31
 *          18:22:30 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public interface Memento extends Serializable {

    /**
     * COMMENT.
     * 
     * @param o
     *            {@link Object}
     * @return {@link Memento}
     */
    Memento freeze(Object o);

    /**
     * COMMENT.
     * 
     * @return {@link Object}
     */
    Object defrost();

    /**
     * COMMENT.
     * 
     * @param o
     *            {@link Object}
     */
    void fill(Object o);
}
/*
 * $Log: Memento.java,v $
 * Revision 1.1  2006-05-02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.3  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.2 2005/10/31 18:22:30 sweissTFH clean up
 * and commenting
 * 
 */
