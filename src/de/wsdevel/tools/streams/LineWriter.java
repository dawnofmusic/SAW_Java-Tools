package de.wsdevel.tools.streams;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created on 19.10.2006.
 *
 * for project: FormGen
 *
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2006-10-30 18:21:21 $
 *
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 *
 */
public class LineWriter {

    /**
     * {@link OutputStream} COMMENT.
     */
    private OutputStream os;

    /**
     * {@link String} COMMENT.
     */
    private String encoding;

    /**
     * COMMENT.
     *
     * @param osRef {@link OutputStream}
     * @param charSet {@link String}
     */
    public LineWriter(final OutputStream osRef, final String charSet) {
        this.os = osRef;
        this.encoding = charSet;
    }

    /**
     * COMMENT.
     *
     * @throws IOException COMMENT 
     */
    public final void wl() throws IOException {
        this.wl("");
    }

    /**
     * COMMENT.
     *
     * @param line {@link String}
     * @throws IOException COMMENT 
     */
    public final void wl(final String line) throws IOException {
        this.os.write((line + "\r\n").getBytes(this.encoding));
    }
}
/*
 * $Log: LineWriter.java,v $
 * Revision 1.1  2006-10-30 18:21:21  sweissTFH
 * *** empty log message ***
 *
 */
