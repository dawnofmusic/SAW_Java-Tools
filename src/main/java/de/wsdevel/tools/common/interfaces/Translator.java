package de.wsdevel.tools.common.interfaces;

/**
 * Created on 26.04.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2005/10/31
 *          18:22:30 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 * @param <In>
 * @param <Out>
 * 
 */
public interface Translator<In, Out> {

    /**
     * @param value
     *            of type <code>In</code> to be translated
     * @return the translated object of type <code>Out</code>
     */
    Out translate(In value);
}
/*
 * $Log: Translator.java,v $
 * Revision 1.1  2006-05-02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.5  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.4 2005/12/27 16:06:01 sweissTFH moved to
 * java 5 and very big clean up!
 * 
 * Revision 1.3 2005/10/31 18:22:30 sweissTFH clean up and commenting
 * 
 */
