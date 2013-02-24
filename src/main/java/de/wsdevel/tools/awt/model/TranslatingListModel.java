package de.wsdevel.tools.awt.model;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import de.wsdevel.tools.common.interfaces.Translator;

/**
 * Created on 14.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.2 $ -- $Date: 2005/10/26
 *          16:56:23 $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 * @param <IN>
 *            COMMENT
 * @param <OUT>
 *            COMMENT
 * 
 */
public class TranslatingListModel<IN, OUT> implements ListModel {

    /**
     * {@link ListModel} COMMENT.
     */
    private ListModel innerListModel;

    /**
     * {@link Translator} COMMENT.
     */
    private Translator<IN, OUT> translator;

    /**
     * COMMENT.
     * 
     * @param innerListModelVal
     *            {@link ListModel}
     * @param translatorVal
     *            {@link Translator}
     */
    public TranslatingListModel(final ListModel innerListModelVal,
	    final Translator<IN, OUT> translatorVal) {
	this.innerListModel = innerListModelVal;
	this.translator = translatorVal;
    }

    /**
     * @param index
     *            <code>int</code>
     * @return element of type OUT
     * @see javax.swing.ListModel#getElementAt(int)
     */
    @SuppressWarnings("unchecked")
    public final OUT getElementAt(final int index) {
	return this.translator.translate((IN) this.innerListModel
		.getElementAt(index));
    }

    /**
     * @return <code>int</code>
     * @see javax.swing.ListModel#getSize()
     */
    public final int getSize() {
	return this.innerListModel.getSize();
    }

    /**
     * @param l
     *            {@link ListDataListener}
     * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
     */
    public final void addListDataListener(final ListDataListener l) {
	this.innerListModel.addListDataListener(l);
    }

    /**
     * @param l
     *            {@link ListDataListener}
     * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
     */
    public final void removeListDataListener(final ListDataListener l) {
	this.innerListModel.removeListDataListener(l);
    }

}
//
// $Log: TranslatingListModel.java,v $
// Revision 1.2  2009-02-09 13:15:13  sweiss
// clean up of stuff
//
// Revision 1.1 2006/05/02 16:06:00 sweissTFH
// cleaned up tools and moved everything to appropriate new packages
// 
// Revision 1.4 2006/04/05 18:19:34 sweissTFH
// cleaned up checkstyle errors
// 
// Revision 1.3 2005/12/27 16:06:01 sweissTFH
// moved to java 5 and very big clean
// up!
// 
// Revision 1.2 2005/10/26 16:56:23 mschneiderTFH
// start of very big clean up and
// commenting! (sw)
//
