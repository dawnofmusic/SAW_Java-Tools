package de.wsdevel.tools.app.config;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import de.wsdevel.tools.persistence.XMLEncoderFactory;

/**
 * Created on 07.04.2005.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.3 $ -- $Date: 2005/10/26
 *          16:56:23 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public class BasicSerializableEnDecoder implements SerializableEnDecoder {

    /**
     * @param encoded
     *            {@link String}
     * @return {@link Serializable}
     * @throws IOException
     *             COMMENT
     * @throws ClassNotFoundException
     *             COMMENT
     * @see de.wsdevel.tools.app.config.SerializableEnDecoder#decodeSerializableFromString(java.lang.String)
     */
    public final Serializable decodeSerializableFromString(final String encoded)
            throws IOException, ClassNotFoundException {
        XMLDecoder dec = new XMLDecoder(new ByteArrayInputStream(encoded
                .getBytes()));
        Serializable object = (Serializable) dec.readObject();
        dec.close();
        return object;
    }

    /**
     * @param ser
     *            {@link Serializable}
     * @return {@link String}
     * @throws IOException
     *             COMMENT
     * @see de.wsdevel.tools.app.config.SerializableEnDecoder#encodeSerializableToString(java.io.Serializable)
     */
    public final String encodeSerializableToString(final Serializable ser)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLEncoder enc = XMLEncoderFactory.createDefaultXMLEncoder(baos);
        enc.writeObject(ser);
        enc.flush();
        enc.close();
        String res = baos.toString();
        baos.close();
        return res;
    }

}
/*
 * $Log: BasicSerializableEnDecoder.java,v $
 * Revision 1.3  2006-05-23 12:58:32  sweissTFH
 * new xml properties file
 *
 * Revision 1.2  2006/05/23 10:26:05  sweissTFH
 * added functionality to config and cleaned up a bit
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
