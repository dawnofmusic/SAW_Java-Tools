/**
 * Class:    ContainerOutputStream<br/>
 * <br/>
 * Created:  20.04.2013<br/>
 * Filename: ContainerOutputStream.java<br/>
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
import java.io.OutputStream;

/**
 * ContainerOutputStream
 * 
 * @param <code>T</code> any type extending {@link Frame}
 */
public abstract class ContainerOutputStream<T extends Frame> extends
	OutputStream {

    /**
     * {@link OutputStream} innerOs
     */
    protected final OutputStream innerOs;

    /**
     * ContainerOutputStream constructor.
     * 
     * @param innerOsRef
     *            {@link OutputStream}
     */
    public ContainerOutputStream(final OutputStream innerOsRef) {
	this.innerOs = innerOsRef;
    }

    /**
     * @throws IOException
     * @see java.io.OutputStream#close()
     */
    @Override
    public void close() throws IOException {
	this.innerOs.close();
    }

    /**
     * @throws IOException
     * @see java.io.OutputStream#flush()
     */
    @Override
    public void flush() throws IOException {
	this.innerOs.flush();
    }

    /**
     * @param b
     *            <code>byte[]</code>
     * @param off
     *            <code>int</code>
     * @param len
     *            <code>int</code>
     * @throws IOException
     * @see java.io.OutputStream#write(byte[], int, int)
     */
    @Override
    public void write(final byte[] b, final int off, final int len)
	    throws IOException {
	this.innerOs.write(b, off, len);
    }

    /**
     * @param b
     *            <code>int</code>
     * @throws IOException
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write(final int b) throws IOException {
	this.innerOs.write(b);
    }

    /**
     * writeFrame.
     * 
     * @param frame
     *            <code>T</code>
     */
    public abstract void writeFrame(T frame);

    /**
     * writeFrames.
     * 
     * @param frames
     *            <code>T</code> to be written
     * @param off
     *            <code>int</code> offset to take first frame from.
     * @param len
     *            <code>int</code> number of frames to write.
     */
    public abstract void writeFrames(T[] frames, int off, int len);

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================