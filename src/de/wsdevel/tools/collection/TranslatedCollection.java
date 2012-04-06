package de.wsdevel.tools.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import de.wsdevel.tools.common.interfaces.Translator;

/**
 * Created on 23.06.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2005/10/26
 *          16:56:23 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 * @param <In>
 * @param <Out>
 * 
 */
public class TranslatedCollection<In, Out> extends LinkedList<Out> implements
        Collection<Out> {

    /**
     * <code>long</code> serial version unique id.
     */
    private static final long serialVersionUID = 6061640491815751542L;

    /**
     * @param inner
     *            {@link Collection}
     * @param translator
     *            {@link Translator}
     */
    public TranslatedCollection(final Collection<In> inner,
            final Translator<In, Out> translator) {
        Iterator<In> it = inner.iterator();
        while (it.hasNext()) {
            add(translator.translate(it.next()));
        }
    }
}
/*
 * $Log: TranslatedCollection.java,v $
 * Revision 1.1  2006-05-02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.5  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.4 2005/12/27 16:06:01
 * sweissTFH moved to java 5 and very big clean up!
 * 
 * Revision 1.3 2005/10/26 16:56:23 mschneiderTFH start of very big clean up and
 * commenting! (sw)
 * 
 * Revision 1.2 2005/06/20 15:30:05 sweissTFH some comments
 * 
 */
