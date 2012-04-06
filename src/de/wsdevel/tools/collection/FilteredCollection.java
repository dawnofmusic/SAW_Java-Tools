package de.wsdevel.tools.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import de.wsdevel.tools.common.interfaces.Filter;

/**
 * Created on 03.08.2007.
 *
 * for project: Java__Tools
 *
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss und Schmidt, Mediale Systeme GbR</a>
 * @version $Author$ -- $Revision$ -- $Date$
 *
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 *
 *  @param <T> contained type
 */
public class FilteredCollection<T> extends LinkedList<T> {

    /**
     * {@link long} COMMENT.
     */
    private static final long serialVersionUID = -6832520707290060528L;

    /**
     * COMMENT.
     *
     * @param inner {@link Collection}< T>
     * @param filter {@link Filter}
     */
    public FilteredCollection(final Collection<T> inner, final Filter<T> filter) {
        Iterator<T> iterator = inner.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (filter.accept(next)) {
                add(next);
            }
        }
    }

}
/*
 * $Log$
 * Revision 1.1  2007-08-03 13:13:19  sweiss
 * added FilteredCollection
 *
 */
