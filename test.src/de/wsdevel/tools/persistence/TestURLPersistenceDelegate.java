package de.wsdevel.tools.persistence;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

/**
 * Created on 10.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:weiss@dawnofmusic.com">Sebastian A. Weiss - dawn of
 *         music</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.2 $ -- $Date: 2005/12/27
 *          16:06:01 $
 * 
 * (c) dawn of music 2004 - All rights reserved.
 * 
 */
public class TestURLPersistenceDelegate extends TestCase {

    /**
     * {@link String} COMMENT.
     */
    private static final String TEST_URL_STRING = "http://www.dawnofmusic.de:8080/testit/";

    /**
     * COMMENT.
     * 
     * @param arg0
     *            {@link String}[]
     */
    public TestURLPersistenceDelegate(final String arg0) {
        super(arg0);
    }

    /**
     * COMMENT.
     * 
     * @throws MalformedURLException
     *             COMMENT
     */
    public final void testIt() throws MalformedURLException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLEncoder enc = new XMLEncoder(baos);
        enc.setPersistenceDelegate(URL.class, new URLPersistenceDelegate());
        URL url = new URL(TEST_URL_STRING);
        enc.writeObject(url);
        enc.close();

        XMLDecoder dec = new XMLDecoder(new ByteArrayInputStream(baos
                .toByteArray()));
        Object o = dec.readObject();

        assertEquals("URLs are not equal", url, o);
    }

}
/*
 * $Log: TestURLPersistenceDelegate.java,v $
 * Revision 1.2  2006-05-12 15:36:43  sweissTFH
 * cleanup
 *
 * Revision 1.1  2006/05/02 16:06:01  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.4  2006/04/05 18:19:35  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.3 2005/12/29 14:06:58
 * sweissTFH removed some deprecated code
 * 
 * Revision 1.2 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean
 * up!
 * 
 */
