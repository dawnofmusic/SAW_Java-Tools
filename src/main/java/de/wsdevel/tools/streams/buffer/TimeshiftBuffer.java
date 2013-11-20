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

    // /**
    // * {@link FrameQueueBuffer.SegmentFactory<F,S>} factory
    // */
    // private final FrameQueueBuffer.SegmentFactory<F, S> factory;

    // /**
    // * {@link LinkedList<Long>} knownTS
    // */
    // private LinkedList<Long> knownTS = null;

    // /**
    // * {@link Long} lastTS
    // */
    // private Long lastTS = 0l;

    /**
     * {@link Log} LOG
     */
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(TimeshiftBuffer.class);

    /**
     * {@link int} NANOS_IN_MILLIS
     */
    public static final int NANOS_IN_MILLIS = 1000 * 1000;

    // /**
    // * {@link S} currentSegment
    // */
    // private S currentSegment;

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

    // /**
    // * {@link int} lastSequenceNumber
    // */
    // private int lastSequenceNumber = -1;
    //
    // /**
    // * {@link int} sequenceNumberOffset
    // */
    // private int sequenceNumberOffset = 0;
    //
    /**
     * {@link boolean} offsetChanged
     */
    private boolean offsetChanged = true;

    // /**
    // * getOrCreateNewKnownTS.
    // *
    // * @return {@link Long}
    // */
    // private Long getNextTS() {
    // if (this.knownTS == null) {
    // final long now = System.currentTimeMillis();
    // final long startTS = now + (this.offset * 1000);
    // this.knownTS = new LinkedList<Long>(this.queue.timestamps);
    // this.lastTS = 0l;
    // while (this.lastTS < startTS) {
    // this.lastTS = this.knownTS.pollFirst();
    // if (this.lastTS == null) {
    // this.lastTS = 0l;
    // return null;
    // }
    // }
    // return this.lastTS;
    // }
    // Long newVal = this.knownTS.poll();
    // // SEBASTIAN maybe we should refresh the knownTS List earlier!
    // if (newVal == null) {
    // do {
    // this.knownTS = new LinkedList<Long>(this.queue.timestamps);
    // newVal = 0l;
    // inner: while (newVal <= this.lastTS) {
    // newVal = this.knownTS.pollFirst();
    // if (newVal == null) {
    // // no new timestamps yet, try it again
    // try {
    // Thread.sleep(1000);
    // } catch (final InterruptedException e) {
    // }
    // break inner;
    // }
    // }
    // } while (newVal == null);
    // }
    // this.lastTS = newVal;
    // // System.out.println(DateFormat.getTimeInstance()
    // // .format(new Date(lastTS)));
    // return this.lastTS;
    // }

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
	    this.lastSequenceNumber = sFromFile.getSequenceNumber();
	    this.offsetChanged = false;
	    return sFromFile;
	}
	return this.queue
		.getSegmentForSequenceNumber(++this.lastSequenceNumber);
    }

    // /**
    // * readSegment.
    // *
    // * @param numberOfFrames
    // * <code>int</code>
    // * @return <code>S</code>
    // */
    // private S readSegment(final int framesToRead) {
    // if (this.currentSegment == null) {
    // final S readSegment = readSegment();
    // if (readSegment == null) {
    // return null;
    // }
    // this.currentSegment = readSegment;
    // }
    // S returnVal = null;
    // final F[] frames = this.currentSegment.getFrames();
    // if (frames.length <= framesToRead) {
    // returnVal = this.currentSegment;
    // this.currentSegment = null;
    // return returnVal;
    // }
    // // SEBASTIAN sequence number of segments gets broken! needs to be
    // // checked
    // returnVal = this.factory.createSegment(Arrays.copyOfRange(frames, 0,
    // framesToRead));
    // this.currentSegment = this.factory.createSegment(Arrays.copyOfRange(
    // frames, framesToRead, frames.length));
    // return returnVal;
    // }

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