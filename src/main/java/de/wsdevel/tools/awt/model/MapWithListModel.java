package de.wsdevel.tools.awt.model;

import java.util.Map;

import javax.swing.ListModel;

/**
 * Created on 08.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2005/10/26
 *          16:56:23 $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 * @param <K>
 * @param <V>
 * 
 */
public interface MapWithListModel<K, V> extends Map<K, V> {
	/**
	 * COMMENT.
	 * 
	 * @return {@link ListModel}
	 */
	ListModel<V> getListModel();
}
/*
 * $Log: MapWithListModel.java,v $ Revision 1.1 2006-05-02 16:06:00 sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 * 
 * Revision 1.4 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
 * Revision 1.3 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean
 * up!
 * 
 * Revision 1.2 2005/10/26 16:56:23 mschneiderTFH start of very big clean up and
 * commenting! (sw)
 */
