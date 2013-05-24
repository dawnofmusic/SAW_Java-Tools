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

import java.util.Arrays;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.wsdevel.tools.streams.container.Frame;
import de.wsdevel.tools.streams.container.Segment;

/**
 * TimeshiftBuffer
 */
public class TimeshiftBuffer<F extends Frame, S extends Segment<F>> {

    /**
     * {@link FrameQueueBuffer.SegmentFactory<F,S>} factory
     */
    private final FrameQueueBuffer.SegmentFactory<F, S> factory;

    /**
     * {@link LinkedList<Long>} knownTS
     */
    private LinkedList<Long> knownTS = null;

    /**
     * {@link Long} lastTS
     */
    private Long lastTS = 0l;

    /**
     * {@link int} offset
     */
    private int offset = 0;

    /**
     * {@link FileSystemFrameQueue<F,S>} queue
     */
    private final FileSystemFrameQueue<F, S> queue;

    /**
     * {@link S} currentSegment
     */
    private S currentSegment;

    /**
     * Timeshift constructor.
     * 
     * @param queueRef
     */
    public TimeshiftBuffer(final FileSystemFrameQueue<F, S> queueRef,
	    final FrameQueueBuffer.SegmentFactory<F, S> factoryRef) {
	this.queue = queueRef;
	this.factory = factoryRef;
    }

    /**
     * {@link Log} LOG
     */
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(TimeshiftBuffer.class);

    /**
     * getOrCreateNewKnownTS.
     * 
     * @return
     */
    private Long getNextTS() {
	if (this.knownTS == null) {
	    final long now = System.currentTimeMillis();
	    final long startTS = now + (this.offset * 1000);
	    this.knownTS = new LinkedList<Long>(this.queue.timestamps);
	    this.lastTS = 0l;
	    while (this.lastTS < startTS) {
		this.lastTS = this.knownTS.pollFirst();
		if (this.lastTS == null) {
		    this.lastTS = 0l;
		    return null;
		}
	    }
	    return this.lastTS;
	}
	Long newVal = this.knownTS.poll();
	// SEBASTIAN maybe we should refresh the knownTS List earlier!
	if (newVal == null) {
	    do {
		this.knownTS = new LinkedList<Long>(this.queue.timestamps);
		newVal = 0l;
		inner: while (newVal <= this.lastTS) {
		    newVal = this.knownTS.pollFirst();
		    if (newVal == null) {
			// no new timestamps yet, try it again
			try {
			    Thread.sleep(1000);
			} catch (final InterruptedException e) {
			}
			break inner;
		    }
		}
	    } while (newVal == null);
	}
	this.lastTS = newVal;
	// System.out.println(DateFormat.getTimeInstance()
	// .format(new Date(lastTS)));
	return this.lastTS;
    }

    /**
     * @return the {@link int} offset
     */
    public int getOffset() {
	return this.offset;
    }

    /**
     * readSegment.
     * 
     * @param numberOfFrames
     *            <code>int</code>
     * @return <code>S</code>
     */
    public S readSegment(final int framesToRead) {
	if (this.currentSegment == null) {
	    final Long ts = getNextTS();
	    if (ts == null) {
		return null;
	    }
	    this.currentSegment = this.queue
		    .getTFromFile(this.queue.chunkBuffer.get(ts));
	}
	S returnVal = null;
	final F[] frames = this.currentSegment.getFrames();
	if (frames.length <= framesToRead) {
	    returnVal = this.currentSegment;
	    this.currentSegment = null;
	    return returnVal;
	}
	returnVal = this.factory.createSegment(Arrays.copyOfRange(frames, 0,
		framesToRead));
	this.currentSegment = this.factory.createSegment(Arrays.copyOfRange(
		frames, framesToRead, frames.length));
	return returnVal;
    }

    /**
     * @param offset
     *            {@link int} the offset to set
     */
    public void setOffset(final int offset) {
	if (offset > 0) {
	    throw new IllegalArgumentException(
		    "offset MUST be less than or equal to 0!");
	}
	this.offset = offset;
	if (this.knownTS != null) {
	    this.knownTS.clear();
	    this.knownTS = null;
	}
	this.lastTS = 0l;
    }
}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================