package de.wsdevel.tools.streams;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created on 26.04.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.2 $ -- $Date: 2005/10/31
 *          18:22:30 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public class TeeOutputStream extends OutputStream {

    /**
     * {@link OutputStream} COMMENT.
     */
    private OutputStream os1 = null;

    /**
     * {@link OutputStream} COMMENT.
     */
    private OutputStream os2 = null;

    /**
     * COMMENT.
     * 
     * @param os1Val
     *            {@link OutputStream}
     * @param os2Val
     *            {@link OutputStream}
     */
    public TeeOutputStream(final OutputStream os1Val, final OutputStream os2Val) {
        this.os1 = os1Val;
        this.os2 = os2Val;
    }

    /**
     * @param b
     *            <code>int</code>
     * @throws IOException
     *             COMMENT
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public final void write(final int b) throws IOException {
        this.os1.write(b);
        this.os2.write(b);
    }

    /**
     * @throws IOException
     *             COMMENT
     * @see java.io.Closeable#close()
     */
    @Override
    public final void close() throws IOException {
        this.os1.close();
        this.os2.close();
    }

    /**
     * @throws IOException
     *             COMMENT
     * @see java.io.Flushable#flush()
     */
    @Override
    public final void flush() throws IOException {
        this.os1.flush();
        this.os2.flush();
    }

    /**
     * @param b
     *            <code>byte[]</code>
     * @param off
     *            <code>int</code>
     * @param len
     *            <code>int</code>
     * @throws IOException
     *             COMMENT
     * @see java.io.OutputStream#write(byte[], int, int)
     */
    @Override
    public final void write(final byte[] b, final int off, final int len)
            throws IOException {
        this.os1.write(b, off, len);
        this.os2.write(b, off, len);
    }

    /**
     * @param b
     *            <code>byte[]</code>
     * @throws IOException
     *             COMMENT
     * @see java.io.OutputStream#write(byte[])
     */
    @Override
    public final void write(final byte[] b) throws IOException {
        this.os1.write(b);
        this.os2.write(b);
    }
}
/*
 * $Log: TeeOutputStream.java,v $
 * Revision 1.2  2006-06-10 13:00:33  sweissTFH
 * cleanup and smaller changes due to new compiler settings
 *
 * Revision 1.1  2006/05/02 16:06:01  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.4  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * 
 * Revision 1.3 2005/12/27 16:06:01 sweissTFH
 * moved to java 5 and very big clean up!
 * 
 * Revision 1.2 2005/10/31 18:22:30 sweissTFH clean up and commenting
 * 
 */
