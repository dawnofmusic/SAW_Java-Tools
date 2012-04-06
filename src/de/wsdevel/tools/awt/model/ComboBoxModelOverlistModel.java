package de.wsdevel.tools.awt.model;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Created on 02.01.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2005/10/31
 *          19:11:33 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public class ComboBoxModelOverlistModel extends AbstractListModel implements
        ComboBoxModel {

    /**
     * <code>long</code> serial version unique id.
     */
    private static final long serialVersionUID = -6394379997925184278L;

    /**
     * <code>int</code> COMMENT.
     */
    private int selectedItemIndex = 0;

    /**
     * {@link ListModel} COMMENT.
     */
    private ListModel innerLM;

    /**
     * COMMENT.
     * 
     * @param innerLMVal
     *            {@link ListModel}
     */
    public ComboBoxModelOverlistModel(final ListModel innerLMVal) {
        this.innerLM = innerLMVal;
        innerLMVal.addListDataListener(new ListDataListener() {
            public void contentsChanged(final ListDataEvent e) {
                fireContentsChanged(this, e.getIndex0(), e.getIndex1());
            }

            public void intervalAdded(final ListDataEvent e) {
                fireIntervalAdded(this, e.getIndex0(), e.getIndex1());
            }

            public void intervalRemoved(final ListDataEvent e) {
                fireIntervalRemoved(this, e.getIndex0(), e.getIndex1());
            }
        });
    }

    /**
     * @return {@link Object}
     * @see javax.swing.ComboBoxModel#getSelectedItem()
     */
    public final Object getSelectedItem() {
        if (this.selectedItemIndex < this.innerLM.getSize()) {
            return this.innerLM.getElementAt(this.selectedItemIndex);
        }
        return null;
    }

    /**
     * @param anItem
     *            {@link Object}
     * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
     */
    public final void setSelectedItem(final Object anItem) {
        if (anItem == null) {
            return;
        }
        for (int i = 0; i < this.innerLM.getSize(); i++) {
            if (anItem.equals(this.innerLM.getElementAt(i))) {
                this.selectedItemIndex = i;
            }
        }
        // please do not ask! have a look into DefaultComboBoxModel (sw);
        fireContentsChanged(this, -1, -1);
    }

    /**
     * @return <code>int</code>
     * @see javax.swing.ListModel#getSize()
     */
    public final int getSize() {
        return this.innerLM.getSize();
    }

    /**
     * @param index
     *            <code>int</code>
     * @return {@link Object}
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public final Object getElementAt(final int index) {
        return this.innerLM.getElementAt(index);
    }

}
/*
 * $Log: ComboBoxModelOverlistModel.java,v $
 * Revision 1.1  2006-05-02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.4  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.3 2005/12/27 16:06:01
 * sweissTFH moved to java 5 and very big clean up!
 * 
 * Revision 1.2 2005/10/31 19:11:33 sweissTFH cleaned up and commented a bit
 * 
 */
