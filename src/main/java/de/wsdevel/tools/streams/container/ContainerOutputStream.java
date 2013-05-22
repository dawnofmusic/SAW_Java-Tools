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
public class ContainerOutputStream<F extends Frame, S extends Segment<F>>
	extends OutputStream {

    /**
     * {@link OutputStream} innerOs
     */
    protected OutputStream innerOs;

    /**
     * ContainerOutputStream constructor.
     */
    public ContainerOutputStream() {
    }

    /**
     * ContainerOutputStream constructor.
     * 
     * @param innerOsRef
     *            {@link OutputStream}
     */
    public ContainerOutputStream(final OutputStream innerOsRef) {
	setInnerOs(innerOsRef);
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
     * Returns the innerOs.
     * 
     * @return {@link OutputStream}
     */
    public OutputStream getInnerOs() {
	return this.innerOs;
    }

    /**
     * Sets the innerOs.
     * 
     * @param innerOs
     *            {@link OutputStream}
     */
    public void setInnerOs(final OutputStream innerOs) {
	this.innerOs = innerOs;
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
     * @throws IOException
     */
    public void writeFrame(final F frame) throws IOException {
	this.innerOs.write(frame.toBytes());
    }

    /**
     * writeSegment.
     * 
     * @param segment
     * @throws IOException
     */
    public void writeSegment(final S segment) throws IOException {
	for (F t : segment.getFrames()) {
	    writeFrame(t);
	}
    }

    /**
     * writeFrames.
     * 
     * @param frames
     *            <code>T</code> to be written
     * @param off
     *            <code>int</code> offset to take first frame from.
     * @param len
     *            <code>int</code> number of frames to write.
     * @throws IOException
     */
    public void writeFrames(final F[] frames, final int off, final int len)
	    throws IOException {
	for (int i = off; i < (off + len); i++) {
	    if (frames[i] != null) {
		writeFrame(frames[i]);
	    }
	}
    }

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================