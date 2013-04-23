/**
 * Class:    Segment<br/>
 * <br/>
 * Created:  23.04.2013<br/>
 * Filename: Segment.java<br/>
 * Version:  $Revision: 1.1 $<br/>
 * <br/>
 * last modified on $Date: 2013-04-23 16:44:12 $<br/>
 *               by $Author: sweiss $<br/>
 * <br/>
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author: sweiss $ -- $Revision: 1.1 $ -- $Date: 2013-04-23 16:44:12 $
 * <br/>
 * (c) 2013, Sebastian A. Weiss - All rights reserved.
 */

package de.wsdevel.tools.streams.container;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Segment
 */
public abstract class Segment<T extends Frame> implements Frame {

    /**
     * SegmentState
     */
    public static enum SegmentState {

	/**
	 * {@link SegmentState} binary
	 */
	binary,

	/**
	 * {@link SegmentState} deserialized
	 */
	deserialized,

	/**
	 * {@link SegmentState} both
	 */
	both;
    }

    /**
     * {@link SegmentState} state
     */
    protected SegmentState state = SegmentState.binary;

    /**
     * {@link byte[]} data
     */
    protected byte[] data;
    /**
     * <code>T[]</code> frames
     */
    protected T[] frames;

    /**
     * {@link Log} LOG
     */
    private static final Log LOG = LogFactory.getLog(Segment.class);

    /**
     * deserialize.
     * 
     * @return <code>true</code> if deserialization was successful;
     */
    public synchronized boolean deserialize() {
	if (getState().equals(SegmentState.binary) && (this.data != null)) {
	    try {
		this.frames = deserializeFrames();
		setState(SegmentState.both);
		return true;
	    } catch (final IOException e) {
		Segment.LOG.error(e.getLocalizedMessage(), e);
	    }
	}
	return false;
    }

    /**
     * deserializeFrames.
     * 
     * @return <code>T[]</code>
     * @throws IOException
     */
    protected abstract T[] deserializeFrames() throws IOException;

    /**
     * @return the {@link byte[]} data
     */
    public byte[] getData() {
	return this.data;
    }

    /**
     * @return the <code>T[]</code> frames
     */
    public T[] getFrames() {
	return this.frames;
    }

    /**
     * @return the {@link SegmentState} state
     */
    public SegmentState getState() {
	return this.state;
    }

    /**
     * serializeFrames.
     * 
     * @return <code>byte[]</code>
     */
    protected abstract byte[] serializeFrames();

    /**
     * @param dataRef
     *            {@link byte[]} the data to set
     */
    public synchronized void setData(final byte[] dataRef) {
	// SEBASTIAN implement Frame.getFrameSize
	// if ((dataRef.length % MPEGTSFrame.TRANSPORT_PACKET_SIZE) != 0) {
	// throw new IllegalArgumentException(
	//        	    "length of dataRef must be n times " + MPEGTSFrame.TRANSPORT_PACKET_SIZE); //$NON-NLS-1$
	// }
	this.frames = null;
	this.data = dataRef;
	setState(SegmentState.binary);
    }

    /**
     * @param framesRef
     *            {@link T[]} the frames to set
     */
    public synchronized void setFrames(final T[] framesRef) {
	if (framesRef == null) {
	    throw new NullPointerException("framesRef must not be null!"); //$NON-NLS-1$
	}
	this.frames = framesRef;
	setState(SegmentState.deserialized);
    }

    /**
     * @param stateRef
     *            {@link SegmentState} the state to set
     */
    protected void setState(final SegmentState stateRef) {
	this.state = stateRef;
    }

    /**
     * toBytes.
     * 
     * @see de.wsdevel.tools.streams.container.Frame#toBytes()
     * @return <code>bytes</code>
     */
    @Override
    public byte[] toBytes() {
	switch (this.state) {
	case deserialized:
	    return serializeFrames();
	case both:
	case binary:
	default:
	    return getData();
	}
    }

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: Segment.java,v $
// Revision 1.1 2013-04-23 16:44:12 sweiss
// introduced Segment
//
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================