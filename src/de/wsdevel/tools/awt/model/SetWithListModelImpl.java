package de.wsdevel.tools.awt.model;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
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
public class SetWithListModelImpl<V> extends AbstractSet<V> implements
        SetWithListModel<V> {

    /**
     * {@link ListModel} COMMENT.
     */
    private AbstractListModel lisModel;

    /**
     * {@link Set} COMMENT.
     */
    private Set<V> innerSet;

    /**
     * COMMENT.
     */
    public SetWithListModelImpl() {
        this.innerSet = new HashSet<V>();
        this.lisModel = new AbstractListModel(this) {

            public int getSize() {
                return SetWithListModelImpl.this.innerSet.size();
            }

            public Object getElementAt(final int index) {
                return SetWithListModelImpl.this.innerSet.toArray()[index];
            }

        };
    }

    /**
     * @return {@link ListModel}
     * @see de.wsdevel.tools.awt.model.SetWithListModel#getListModel()
     */
    public final ListModel getListModel() {
        return this.lisModel;
    }

    /**
     * @param o V
     * @return <code>boolean</code>
     * @see java.util.Set#add(E)
     */
    @Override
    public final boolean add(final V o) {
        boolean add = this.innerSet.add(o);
        int i = getIndexOfV(o);
        if (add) {
            SetWithListModelImpl.this.lisModel.fireIntervalAdded(i, i);
        }
        return add;
    }

    /**
     * @return {@link Iterator}< V>
     * @see java.util.Set#iterator()
     */
    @Override
    public final Iterator<V> iterator() {
        return new Iterator<V>() {
            private Iterator<V> innerIt = SetWithListModelImpl.this.innerSet
                    .iterator();

            private V next;

            public boolean hasNext() {
                return this.innerIt.hasNext();
            }

            public V next() {
                this.next = this.innerIt.next();
                return this.next;
            }

            public void remove() {
                int index = getIndexOfV(this.next);
                this.innerIt.remove();
                SetWithListModelImpl.this.lisModel.fireIntervalRemoved(index,
                        index);
            }

        };
    }

    /**
     * COMMENT.
     *
     * @param v V
     * @return <code>int</code>
     */
    private int getIndexOfV(final V v) {
        int index = 0;
        Object[] objects = SetWithListModelImpl.this.innerSet.toArray();
        for (int i = 0; i < objects.length; i++) {
            if (objects[i].equals(v)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * @return <code>int</code>
     * @see java.util.Set#size()
     */
    @Override
    public final int size() {
        return this.innerSet.size();
    }

}
/*
 * $Log: SetWithListModelImpl.java,v $
 * Revision 1.1  2006-11-12 19:01:18  sweissTFH
 * new setwithlistmodel
 *
 */
