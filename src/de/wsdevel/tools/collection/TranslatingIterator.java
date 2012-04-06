package de.wsdevel.tools.collection;

import java.util.Iterator;

import de.wsdevel.tools.common.interfaces.Translator;

/**
 * Created on 20.06.2005.
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
 * @param <IN>
 *            COMMENT.
 * @param <OUT>
 *            COMMENT.
 * 
 */
public class TranslatingIterator<IN, OUT> implements Iterator<OUT> {

    /**
     * {@link Iterator}.
     */
    private Iterator<IN> innerIt = null;

    /**
     * {@link Translator}.
     */
    private Translator<IN, OUT> translator = null;

    /**
     * @param innerItVal
     *            {@link Iterator}
     * @param translatorVal
     *            {@link Translator}
     */
    public TranslatingIterator(final Iterator<IN> innerItVal,
            final Translator<IN, OUT> translatorVal) {
        this.innerIt = innerItVal;
        this.translator = translatorVal;
    }

    /**
     * @see java.util.Iterator#remove()
     */
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return <code>boolean</code>
     * @see java.util.Iterator#hasNext()
     */
    public final boolean hasNext() {
        return this.innerIt.hasNext();
    }

    /**
     * @return element of type OUT
     * @see java.util.Iterator#next()
     */
    public final OUT next() {
        return this.translator.translate(this.innerIt.next());
    }

}
/*
 * $Log: TranslatingIterator.java,v $
 * Revision 1.1  2006-05-02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.4  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.3 2005/12/27 16:06:01 sweissTFH
 * moved to java 5 and very big clean up!
 * 
 * Revision 1.2 2005/10/26 16:56:23 mschneiderTFH start of very big clean up and
 * commenting! (sw)
 * 
 * Revision 1.1 2005/06/20 15:30:19 sweissTFH *** empty log message ***
 * 
 */
