package de.wsdevel.tools.streams;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * This is a big time kludge work-around for a problem in Java. If you have
 * multiple piped writers sending to a single piped reader you will get broken
 * pipe IO exceptions. The code below simply tells the program to ignore any
 * broken pipe messages and to print any other exceptions. You will get the
 * "Pipe Broken" message from Java 1.1 and the "Write end dead" message from
 * Java 2.
 * 
 * Created on 31.10.2005
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
public class StablePipedReader extends PipedReader {

    /**
     * {@link String} COMMENT.
     */
    private static final String WRITE_END_MESSAGE = "Write end dead";

    /**
     * {@link String} COMMENT.
     */
    private static final String PIPE_BROKEN_MESSAGE = "Pipe broken";

    /**
     * COMMENT.
     * 
     * @param writer
     *            {@link PipedWriter}
     * @throws IOException
     *             COMMENT
     */
    public StablePipedReader(final PipedWriter writer) throws IOException {
        super(writer);
    }

    /**
     * @return <code>int</code>
     * @throws IOException
     *             COMMENT
     * @see java.io.Reader#read()
     */
    @Override
    public final synchronized int read() throws IOException {
        try {
            return super.read();
        } catch (IOException ioe) {
            return handlePossibleStupidException(ioe);
        }
    }

    /**
     * COMMENT.
     * 
     * @param msg
     *            {@link String}
     * @return <code>boolean</code>
     */
    private boolean isStupidException(final String msg) {
        return msg.equalsIgnoreCase(PIPE_BROKEN_MESSAGE)
                || msg.equalsIgnoreCase(WRITE_END_MESSAGE);
    }

    /**
     * @param cbuf
     *            <code>char[]</code>
     * @return <code>int</code>
     * @throws IOException
     *             COMMENT
     * @see java.io.Reader#read(char[])
     */
    @Override
    public final int read(final char[] cbuf) throws IOException {
        try {
            return super.read(cbuf);
        } catch (IOException ioe) {
            return handlePossibleStupidException(ioe);
        }
    }

    /**
     * @param cbuf
     *            <code>char[]</code>
     * @param off
     *            <code>int</code>
     * @param len
     *            <code>int</code>
     * @return <code>int</code>
     * @throws IOException
     *             COMMENT
     * @see java.io.Reader#read(char[], int, int)
     */
    @Override
    public final synchronized int read(final char[] cbuf, final int off,
            final int len) throws IOException {
        try {
            return super.read(cbuf, off, len);
        } catch (IOException ioe) {
            return handlePossibleStupidException(ioe);
        }
    }

    /**
     * COMMENT.
     * 
     * @param ioe
     *            {@link IOException}
     * @return <code>int</code>
     * @throws IOException
     *             COMMENT
     */
    private int handlePossibleStupidException(final IOException ioe)
            throws IOException {
        String msg = ioe.getMessage();
        if (isStupidException(msg)) {
            return -1;
        }
        throw ioe;
    }
}
/*
 * $Log: StablePipedReader.java,v $
 * Revision 1.2  2006-06-10 13:00:33  sweissTFH
 * cleanup and smaller changes due to new compiler settings
 *
 * Revision 1.1  2006/05/02 16:06:01  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.3  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * 
 * Revision 1.2 2005/10/31 18:22:30 sweissTFH
 * clean up and commenting
 * 
 */
