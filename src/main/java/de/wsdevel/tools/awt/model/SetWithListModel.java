package de.wsdevel.tools.awt.model;

import java.util.Set;

import javax.swing.ListModel;

/**
 * Created on 09.11.2006.
 *
 * for project: Java_Tools
 *
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2006-11-12 19:01:18 $
 *
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 *
 * @param <V>
 * 
 */
public interface SetWithListModel<V> extends Set<V> {

    /**
     * COMMENT.
     *
     * @return {@link ListModel}
     */
    ListModel getListModel();
}
/*
 * $Log: SetWithListModel.java,v $
 * Revision 1.1  2006-11-12 19:01:18  sweissTFH
 * new setwithlistmodel
 *
 */
