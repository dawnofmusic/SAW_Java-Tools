/**
 * Class:    SegmentFlyweight<br/>
 * <br/>
 * Created:  20.11.2013<br/>
 * Filename: SegmentFlyweight.java<br/>
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
import java.util.Map;

/**
 * SegmentFlyweight
 */
public class SegmentFlyweight<F extends Frame> extends Segment<F> {

    /**
     * {@link int} sequenceNumber
     */
    private final int sequenceNumber = -1;

    /**
     * {@link long} durationNanos
     */
    private final long durationNanos = -1;

    /**
     * {@link Segment<F>} delegate
     */
    private final Segment<F> delegate;

    /**
     * SegmentFlyweight constructor.
     * 
     * @param delegateRef
     *            {@link Segment}
     */
    public SegmentFlyweight(final Segment<F> delegateRef) {
	this.delegate = delegateRef;
    }

    /**
     * @return
     * @see de.wsdevel.tools.streams.container.Segment#deserialize()
     */
    @Override
    public boolean deserialize() {
	return this.delegate.deserialize();
    }

    /**
     * @param hints
     * @return
     * @see de.wsdevel.tools.streams.container.Segment#deserialize(java.util.Map)
     */
    @Override
    public boolean deserialize(final Map<String, Object> hints) {
	return this.delegate.deserialize(hints);
    }

    /**
     * deserializeFrames.
     * 
     * @see de.wsdevel.tools.streams.container.Segment#deserializeFrames(java.util.Map)
     * @param hints
     * @return
     * @throws IOException
     */
    @Override
    protected F[] deserializeFrames(final Map<String, Object> hints)
	    throws IOException {
	return this.delegate.deserializeFrames(hints);
    }

    /**
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
	return this.delegate.equals(obj);
    }

    /**
     * @return
     * @see de.wsdevel.tools.streams.container.Segment#getData()
     */
    @Override
    public byte[] getData() {
	return this.delegate.getData();
    }

    /**
     * @return
     * @see de.wsdevel.tools.streams.container.Segment#getDurationNanos()
     */
    @Override
    public long getDurationNanos() {
	if (this.durationNanos < 0) {
	    return this.delegate.getDurationNanos();
	}
	return this.durationNanos;
    }

    /**
     * @return
     * @see de.wsdevel.tools.streams.container.Segment#getFrames()
     */
    @Override
    public F[] getFrames() {
	return this.delegate.getFrames();
    }

    /**
     * @return
     * @see de.wsdevel.tools.streams.container.Segment#getId()
     */
    @Override
    public String getId() {
	return this.delegate.getId();
    }

    /**
     * @return
     * @see de.wsdevel.tools.streams.container.Segment#getSequenceNumber()
     */
    @Override
    public int getSequenceNumber() {
	if (this.sequenceNumber < 0) {
	    return this.delegate.getSequenceNumber();
	}
	return this.sequenceNumber;
    }

    /**
     * @return
     * @see de.wsdevel.tools.streams.container.Segment#getSize()
     */
    @Override
    public int getSize() {
	return this.delegate.getSize();
    }

    /**
     * @return
     * @see de.wsdevel.tools.streams.container.Segment#getState()
     */
    @Override
    public de.wsdevel.tools.streams.container.Segment.SegmentState getState() {
	return this.delegate.getState();
    }

    /**
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	return this.delegate.hashCode();
    }

    /**
     * serializeFrames.
     * 
     * @see de.wsdevel.tools.streams.container.Segment#serializeFrames()
     * @return
     */
    @Override
    protected byte[] serializeFrames() {
	return this.delegate.serializeFrames();
    }

    /**
     * @param dataRef
     * @see de.wsdevel.tools.streams.container.Segment#setData(byte[])
     */
    @Override
    public void setData(final byte[] dataRef) {
	this.delegate.setData(dataRef);
    }

    /**
     * @param durationNanosVal
     * @see de.wsdevel.tools.streams.container.Segment#setDurationNanos(long)
     */
    @Override
    public void setDurationNanos(final long durationNanosVal) {
	this.delegate.setDurationNanos(durationNanosVal);
    }

    /**
     * @param framesRef
     * @see de.wsdevel.tools.streams.container.Segment#setFrames(de.wsdevel.tools.streams.container.Frame[])
     */
    @Override
    public void setFrames(final F[] framesRef) {
	this.delegate.setFrames(framesRef);
    }

    /**
     * @param id
     * @see de.wsdevel.tools.streams.container.Segment#setId(java.lang.String)
     */
    @Override
    public void setId(final String id) {
	this.delegate.setId(id);
    }

    /**
     * @param sequenceNumber
     * @see de.wsdevel.tools.streams.container.Segment#setSequenceNumber(int)
     */
    @Override
    public void setSequenceNumber(final int sequenceNumber) {
	this.delegate.setSequenceNumber(sequenceNumber);
    }

    /**
     * @return
     * @see de.wsdevel.tools.streams.container.Segment#toBytes()
     */
    @Override
    public byte[] toBytes() {
	return this.delegate.toBytes();
    }

    /**
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return this.delegate.toString();
    }

}
// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================