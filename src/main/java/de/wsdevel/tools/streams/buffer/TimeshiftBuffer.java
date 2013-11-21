/**
 * Class:    TimeshiftBuffer<br/>
 * <br/>
 * Created:  22.05.2013<br/>
 * Filename: Timeshift.java<br/>
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

package de.wsdevel.tools.streams.buffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.wsdevel.tools.streams.container.Frame;
import de.wsdevel.tools.streams.container.Segment;

/**
 * TimeshiftBuffer
 */
public class TimeshiftBuffer<F extends Frame, S extends Segment<F>> {

    /**
     * {@link Log} LOG
     */
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(TimeshiftBuffer.class);

    /**
     * {@link int} NANOS_IN_MILLIS
     */
    public static final int NANOS_IN_MILLIS = 1000 * 1000;

    /**
     * {@link int} lastSequenceNumber
     */
    private int lastSequenceNumber = -1;

    /**
     * {@link int} minOffset
     */
    private int minOffset = 0;

    /**
     * {@link int} offset
     */
    private int offset = 0;

    /**
     * {@link boolean} offsetChanged
     */
    private boolean offsetChanged = true;

    /**
     * {@link SegmentCache}<F,S> queue
     */
    private final SegmentCache<F, S> queue;

    /**
     * TimeshiftBuffer constructor.
     * 
     * @param queueRef
     *            {@link SegmentCache}
     */
    public TimeshiftBuffer(final SegmentCache<F, S> queueRef) {
	this.queue = queueRef;
    }

    /**
     * @return the {@link int} minOffset
     */
    public int getMinOffset() {
	return this.minOffset;
    }

    /**
     * @return the {@link int} offset
     */
    public int getOffset() {
	return this.offset;
    }

    /**
     * innerSetOffset.
     * 
     * @param offset
     *            <code>int</code>
     */
    private void innerSetOffset(final int offset) {
	if (this.offset != offset) {
	    this.offset = offset;
	    this.offsetChanged = true;
	}
    }

    /**
     * readSegment.
     * 
     * @return <code>S</code>
     */
    public S readSegment() {
	if (this.offsetChanged || (this.lastSequenceNumber < 0)) {
	    final long nearestTimestamp = this.queue
		    .findNearestTimestamp(System.currentTimeMillis()
			    + (getOffset() * 1000));
	    final S sFromFile = this.queue
		    .getSegmentForTimestamp(nearestTimestamp);
	    if (sFromFile != null) {
		this.lastSequenceNumber = sFromFile.getSequenceNumber();
		this.offsetChanged = false;
		// System.out.println("init: lastSequenceNumber "
		// + this.lastSequenceNumber + ", segment: " + sFromFile);
	    }
	    return sFromFile;
	}
	final int sequenceNumber = ++this.lastSequenceNumber;
	final S segmentForSequenceNumber = this.queue
		.getSegmentForSequenceNumber(sequenceNumber);
	// System.out.println("normal: sequenceNumber " +
	// this.lastSequenceNumber
	// + ", segment: " + segmentForSequenceNumber);
	if (segmentForSequenceNumber == null) {
	    this.lastSequenceNumber--;
	}
	return segmentForSequenceNumber;
    }

    /**
     * @param minOffset
     *            {@link int} the minOffset to set
     */
    public void setMinOffset(final int minOffset) {
	this.minOffset = minOffset;
	if (this.offset > this.minOffset) {
	    this.offset = this.minOffset;
	    this.offsetChanged = true;
	}
    }

    /**
     * @param offset
     *            {@link int} the offset to set
     */
    public void setOffset(final int offset) {
	if (offset > getMinOffset()) {
	    innerSetOffset(getMinOffset());
	} else {
	    innerSetOffset(offset);
	}
    }
}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================