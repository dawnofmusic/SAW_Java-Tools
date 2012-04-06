package de.wsdevel.tools.app.storing;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created on 08.10.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.2 $ -- $Date: 2005/12/27
 *          16:06:01 $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class DataStorer {

    /**
     * Hidden constructor.
     * 
     */
    private DataStorer() {
    }

    /**
     * {@link List} COMMENT.
     */
    private static List<Storable> storables = new LinkedList<Storable>();

    /**
     * COMMENT.
     * 
     * @param storable
     *            {@link Storable}
     */
    public static void addStorable(final Storable storable) {
	storables.add(storable);
    }

    /**
     * COMMENT.
     * 
     */
    public static void saveData() {
	Iterator<Storable> it = storables.iterator();
	while (it.hasNext()) {
	    it.next().store();
	}
    }
}
//
// $Log: DataStorer.java,v $
// Revision 1.2  2009-02-09 13:15:16  sweiss
// clean up of stuff
//
// Revision 1.1 2006/05/02 16:06:00 sweissTFH
// cleaned
// up tools and moved everything to
// appropriate new packages
// 
// Revision 1.5 2006/04/05 18:19:35 sweissTFH
// cleaned up checkstyle errors
// 
// Revision 1.4 2005/12/27 16:06:01 sweissTFH
// moved to java 5 and very big clean
// up!
// 
// Revision 1.3 2005/10/26 16:56:23 mschneiderTFH
// start of very big clean up and
// commenting! (sw)
//
