package de.wsdevel.tools.awt.model;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Created on 12.11.2006.
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
public class MergedListModel extends AbstractListModel implements ListModel {

    /**
     * {@link LinkedList}< {@link ListModel}> COMMENT.
     */
    private LinkedList<ListModel> innerListModels = new LinkedList<ListModel>();

    /**
     * COMMENT.
     * @param source {@link Object}
     */
    public MergedListModel(final Object source) {
        super(source);
    }

    /**
     * COMMENT.
     *
     * @param listModel {@link ListModel}
     */
    public final void addListModel(final ListModel listModel) {
        final int index = this.innerListModels.size();
        this.innerListModels.add(listModel);

        int currentSize = getSize();
        fireIntervalAdded(currentSize, currentSize + listModel.getSize());

        listModel.addListDataListener(new ListDataListener() {

            public void intervalAdded(final ListDataEvent e) {
                int count = getNumberOfElementsInListModelsBefore(index);
                fireIntervalAdded(count + e.getIndex0() - 1, count
                        + e.getIndex1() - 1);
            }

            /**
             * COMMENT.
             *
             * @param index <code>int</code>
             * @return <code>int</code>
             */
            private int getNumberOfElementsInListModelsBefore(final int index) {
                int count = 0;
                for (int i = 0; i < index; i++) {
                    count += MergedListModel.this.innerListModels.get(i)
                            .getSize();
                }
                return count;
            }

            public void intervalRemoved(final ListDataEvent e) {
                int count = getNumberOfElementsInListModelsBefore(index);
                fireIntervalRemoved(count + e.getIndex0() - 1, count
                        + e.getIndex1() - 1);
            }

            public void contentsChanged(final ListDataEvent e) {
                int count = getNumberOfElementsInListModelsBefore(index);
                fireContentsChanged(count + e.getIndex0() - 1, count
                        + e.getIndex1() - 1);
            }
        });
    }

    /**
     * @return <code>int</code>
     * @see javax.swing.ListModel#getSize()
     */
    public final int getSize() {
        int size = 0;
        Iterator<ListModel> iterator = this.innerListModels.iterator();
        while (iterator.hasNext()) {
            size += iterator.next().getSize();
        }
        return size;
    }

    /**
     * @param index <code>int</code>
     * @return {@link Object}
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public final Object getElementAt(final int index) {
        int offset = 0;
        Iterator<ListModel> iterator = this.innerListModels.iterator();
        while (iterator.hasNext()) {
            ListModel next = iterator.next();
            if (index < (offset + next.getSize())) {
                return next.getElementAt(index - offset);
            }
            offset += next.getSize();
        }
        return null;
    }

}
/*
 * $Log: MergedListModel.java,v $
 * Revision 1.1  2006-11-12 19:01:18  sweissTFH
 * new setwithlistmodel
 *
 */
