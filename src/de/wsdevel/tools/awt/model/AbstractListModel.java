package de.wsdevel.tools.awt.model;

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
 */
public abstract class AbstractListModel extends ListDataListenerSupport
        implements ListModel {

    /**
     * COMMENT.
     *
     * @param sourceVal {@link Object}
     */
    public AbstractListModel(final Object sourceVal) {
        super(sourceVal);
    }

}
/*
 * $Log: AbstractListModel.java,v $
 * Revision 1.1  2006-11-12 19:01:18  sweissTFH
 * new setwithlistmodel
 *
 */
