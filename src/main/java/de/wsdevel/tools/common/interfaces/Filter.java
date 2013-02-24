package de.wsdevel.tools.common.interfaces;

/**
 * Created on 26.10.2005.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author$ -- $Revision$ -- $Date$ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 * @param <T>
 *            type to be filtered
 * 
 */
public interface Filter<T> {
    /**
     * COMMENT.
     * 
     * @param element
     *            <code>T</code>
     * @return <code>boolean</code>
     */
    boolean accept(T element);
}
//
// $Log$
// Revision 1.2  2009-02-09 13:15:16  sweiss
// clean up of stuff
//
// Revision 1.1 2007-08-03 13:13:19 sweiss
// added FilteredCollection
//
