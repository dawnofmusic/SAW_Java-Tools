package de.wsdevel.tools.awt.model;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Created on 31.05.2007.
 *
 * for project: Java__Tools
 *
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.2 $ -- $Date: 2007-05-31 14:02:18 $
 *
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 *
 */
public class MaxSizeListModelOverListModel extends AbstractListModel implements
        ListModel {

    /**
     * {@link ListModel} COMMENT.
     */
    private ListModel inner;

    /**
     * {@link int} COMMENT.
     */
    private int maxSize = 0;

    /**
     * COMMENT.
     *
     * @param innerListModel {@link ListModel}
     * @param maxSizeVal <code>int</code>
     */
    public MaxSizeListModelOverListModel(final ListModel innerListModel,
            final int maxSizeVal) {
        super(new Object());
        this.inner = innerListModel;
        this.maxSize = maxSizeVal;
        this.inner.addListDataListener(new ListDataListener() {
            public void contentsChanged(final ListDataEvent e) {
                if (e.getIndex0() < MaxSizeListModelOverListModel.this.maxSize) {
                    if (e.getIndex1() < MaxSizeListModelOverListModel.this.maxSize) {
                        fireContentsChanged(e.getIndex0(), e.getIndex1());
                    } else {
                        fireContentsChanged(e.getIndex0(),
                                MaxSizeListModelOverListModel.this.maxSize - 1);
                    }
                }
            }

            public void intervalAdded(final ListDataEvent e) {
                if (e.getIndex0() < MaxSizeListModelOverListModel.this.maxSize) {
                    if (e.getIndex1() < MaxSizeListModelOverListModel.this.maxSize) {
                        fireIntervalAdded(e.getIndex0(), e.getIndex1());
                    } else {
                        fireIntervalAdded(e.getIndex0(),
                                MaxSizeListModelOverListModel.this.maxSize - 1);
                    }
                }
            }

            public void intervalRemoved(final ListDataEvent e) {
                if (e.getIndex0() < MaxSizeListModelOverListModel.this.maxSize) {
                    if (e.getIndex1() < MaxSizeListModelOverListModel.this.maxSize) {
                        fireIntervalRemoved(e.getIndex0(), e.getIndex1());
                    } else {
                        fireIntervalRemoved(e.getIndex0(),
                                MaxSizeListModelOverListModel.this.maxSize - 1);
                    }
                }
            }
        });
    }

    /**
     * @param index <code>int</code>
     * @return {@link Object}
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public final Object getElementAt(final int index) {
        if (index < getSize()) {
            return this.inner.getElementAt(index);
        }
        return null;
        // SEBASTIAN maybe synchronize
        //        throw new IndexOutOfBoundsException("index: " + index);
    }

    /**
     * @return <code>int</code> the size
     * @see javax.swing.ListModel#getSize()
     */
    public final int getSize() {
        if (this.inner.getSize() <= this.maxSize) {
            return this.inner.getSize();
        }
        return this.maxSize;
    }

}
/*
 * $Log: MaxSizeListModelOverListModel.java,v $
 * Revision 1.2  2007-05-31 14:02:18  sweiss
 * a lot of bug fixes and performance issues
 *
 * Revision 1.1  2007-05-31 12:11:13  sweiss
 * *** empty log message ***
 *
 */
