package de.wsdevel.tools.awt.model;

import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 18.03.2007.
 *
 * for project: Java_Tools
 *
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss und Schmidt, Mediale Systeme GbR</a>
 * @version $Author$ -- $Revision$ -- $Date$
 *
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 * @param <V>
 *
 */
public class HashMapList<V> extends AbstractList<V> implements List<V> {

    /**
     * {@link HashMap< {@link Integer}, V>} COMMENT.
     */
    private HashMap<Integer, V> elements;

    /**
     * COMMENT.
     */
    public HashMapList() {
        this.elements = new HashMap<Integer, V>();
    }

    /**
     * @param index
     *            <code>int</code>
     * @param element
     *            of type V
     * @see java.util.List#add(int, java.lang.Object)
     */
    @Override
    public final synchronized void add(final int index, final V element) {
        if (index < this.elements.size()) {
            for (int i = this.elements.size() - 1; i >= index; i--) {
                this.elements.put(i + 1, this.elements.get(i));
            }
        }
        this.elements.put(index, element);
    }

    /**
     * @param index
     *            <code>int</code>
     * @return element of type V
     * @see java.util.List#remove(int)
     */
    @Override
    public final synchronized V remove(final int index) {
        V o = this.elements.get(index);
        for (int i = index; i < this.elements.size(); i++) {
            this.elements.put(i, this.elements.get(i + 1));
        }
        this.elements.remove(this.elements.size() - 1);
        return o;
    }

    /**
     * @param index
     *            <code>int</code>
     * @param element
     *            of type V
     * @return element of type V
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public final V set(final int index, final V element) {
        V oldValue = this.elements.put(index, element);
        return oldValue;
    }

    /**
     * @param index <code>int</code>
     * @return V
     * @see java.util.AbstractList#get(int)
     */
    @Override
    public final V get(final int index) {
        return this.elements.get(index);
    }

    /**
     * @return <code>int</code>
     * @see java.util.AbstractCollection#size()
     */
    @Override
    public final int size() {
        return this.elements.size();
    }

}
/*
 * $Log$
 * Revision 1.1  2007-03-18 18:30:03  sweissTFH
 * a lot of event consuming stuff for listDataListeners
 *
 */
