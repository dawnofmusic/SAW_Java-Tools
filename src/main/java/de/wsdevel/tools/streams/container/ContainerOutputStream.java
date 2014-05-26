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

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ContainerOutputStream
 * 
 * @param <code>T</code> any type extending {@link Frame}
 */
public class ContainerOutputStream<F extends Frame, S extends Segment<F>>
	extends DataOutputStream {

    /**
     * writeFrame.
     * 
     * @param frame
     *            <code>T</code>
     * @throws IOException
     */
    private static void writeFrame(final OutputStream innerOs, final Frame frame)
	    throws IOException {
	innerOs.write(frame.toBytes());
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
    public static void writeFrames(final OutputStream innerOs,
	    final Frame[] frames, final int off, final int len)
	    throws IOException {
	if (frames == null) {
	    throw new NullPointerException("frames MUSt NOT be null!");
	}
	synchronized (frames) {
	    for (int i = off; i < (off + len); i++) {
		if (frames[i] != null) {
		    // (20131107 saw) actually this should never happen, but
		    // sometimes...
		    writeFrame(innerOs, frames[i]);
		}
	    }
	}
    }

    /**
     * ContainerOutputStream constructor.
     * 
     * @param innerOsRef
     *            {@link OutputStream}
     */
    public ContainerOutputStream(final OutputStream innerOsRef) {
	super(innerOsRef);
    }

    /**
     * writeFrame.
     * 
     * @throws IOException
     */
    public void writeFrame(final F frame) throws IOException {
	write(frame.toBytes());
    }

    /**
     * writeSegment.
     * 
     * @param segment
     * @throws IOException
     */
    public void writeSegment(final S segment) throws IOException {
	if (segment == null) {
	    throw new NullPointerException("segment MUST NOT be null!");
	}
	synchronized (segment) {
	    switch (segment.getState()) {
	    case deserialized:
		for (final F t : segment.getFrames()) {
		    if (t != null) {
			// (20131107 saw) actually this should never happen, but
			// sometimes...
			write(t.toBytes());
			// writeFrame(t);
		    }
		}
	    case binary:
	    case both:
		write(segment.getData());
	    default:
	    }
	}
    }

}
// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================