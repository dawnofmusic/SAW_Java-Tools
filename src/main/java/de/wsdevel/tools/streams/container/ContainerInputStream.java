/**
 * Class:    ContainerInputStream<br/>
 * <br/>
 * Created:  20.04.2013<br/>
 * Filename: ContainerInputStream.java<br/>
 * Version:  $Revision: $<br/>
 * <br/>
 * last modified on $Date:  $<br/>
 *               by $Author: $<br/>
 * <br/>
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * (c) 2013 Sebastian A. Weiss - All rights reserved.
 */

package de.wsdevel.tools.streams.container;

import java.io.IOException;
import java.io.InputStream;

/**
 * ContainerInputStream
 */
public abstract class ContainerInputStream extends InputStream {

    /**
     * {@link InputStream} innerIs
     */
    private final InputStream innerIs;

    /**
     * ContainerInputStream constructor.
     */
    public ContainerInputStream(final InputStream innerIsRef) {
	this.innerIs = innerIsRef;
    }

    /**
     * @return
     * @throws IOException
     * @see java.io.InputStream#available()
     */
    @Override
    public int available() throws IOException {
	return this.innerIs.available();
    }

    /**
     * @throws IOException
     * @see java.io.InputStream#close()
     */
    @Override
    public void close() throws IOException {
	this.innerIs.close();
    }

    /**
     * @param readlimit
     * @see java.io.InputStream#mark(int)
     */
    @Override
    public synchronized void mark(final int readlimit) {
	throw new UnsupportedOperationException();
    }

    /**
     * @return
     * @see java.io.InputStream#markSupported()
     */
    @Override
    public boolean markSupported() {
	return false;
    }

    /**
     * @return
     * @throws IOException
     * @see java.io.InputStream#read()
     */
    @Override
    public int read() throws IOException {
	return this.innerIs.read();
    }

    /**
     * @param b
     * @param off
     * @param len
     * @return
     * @throws IOException
     * @see java.io.InputStream#read(byte[], int, int)
     */
    @Override
    public int read(final byte[] b, final int off, final int len)
	    throws IOException {
	return this.innerIs.read(b, off, len);
    }

    /**
     * readFrame.
     * 
     * @return {@link Frame}
     */
    public abstract Frame readFrame();

    /**
     * readFrames.
     * 
     * @param frames
     *            {@link Frame}[] to store frames into.
     * @param off
     *            <code>int</code> offset to start writing from.
     * @param len
     *            <code>int</code> number of frames to be read.
     * @return <code>int</code> the number of frames read or <code>-1</code> if
     *         end of stream was reached.
     */
    public abstract int readFrames(Frame[] frames, int off, int len);

    /**
     * @throws IOException
     * @see java.io.InputStream#reset()
     */
    @Override
    public synchronized void reset() throws IOException {
	throw new UnsupportedOperationException();
    }

    /**
     * @param n
     * @return
     * @throws IOException
     * @see java.io.InputStream#skip(long)
     */
    @Override
    public long skip(final long n) throws IOException {
	return this.innerIs.skip(n);
    }

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================