package de.wsdevel.tools.app.config;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created on 07.04.2005.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.2 $ -- $Date: 2005/10/26
 *          16:56:23 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public interface SerializableEnDecoder {

    /**
     * @param string
     *            String
     * @return Serializable decoded from String
     * @throws IOException
     *             during io erros
     * @throws ClassNotFoundException
     *             if class could not be resolved
     */
    Serializable decodeSerializableFromString(String string)
            throws IOException, ClassNotFoundException;

    /**
     * @param serializable
     *            Serializable
     * @return String encoded Serializable
     * @throws IOException
     *             during io errors
     */
    String encodeSerializableToString(Serializable serializable)
            throws IOException;
}
/*
 * $Log: SerializableEnDecoder.java,v $
 * Revision 1.2  2006-05-23 12:58:32  sweissTFH
 * new xml properties file
 *
 * Revision 1.1  2006/05/02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.3  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * 
 * Revision 1.2 2005/10/26 16:56:23
 * mschneiderTFH start of very big clean up and commenting! (sw)
 * 
 * Revision 1.1 2005/04/07 17:43:27 sweiss cleaned up a lot, new delegate for
 * serializable encoding
 * 
 */
