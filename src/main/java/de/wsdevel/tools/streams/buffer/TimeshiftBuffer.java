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

import java.util.LinkedList;

import de.wsdevel.tools.streams.container.Frame;
import de.wsdevel.tools.streams.container.Segment;

/**
 * TimeshiftBuffer
 */
public class TimeshiftBuffer<F extends Frame, S extends Segment<F>> {

    /**
     * {@link LinkedList<Long>} knownTS
     */
    private LinkedList<Long> knownTS;

    /**
     * {@link Long} lastTS
     */
    private Long lastTS = null;

    /**
     * {@link int} offset
     */
    private int offset = 0;

    /**
     * {@link FileSystemFrameQueue<F,S>} queue
     */
    private final FileSystemFrameQueue<F, S> queue;

    /**
     * Timeshift constructor.
     * 
     * @param queueRef
     */
    public TimeshiftBuffer(final FileSystemFrameQueue<F, S> queueRef) {
	this.queue = queueRef;
    }

    /**
     * getOrCreateNewKnownTS.
     * 
     * @return
     */
    public Long getNextTS() {
	if (this.knownTS == null) {
	    final long now = System.currentTimeMillis();
	    final long startTS = now - (this.offset * 1000);
	    this.knownTS = new LinkedList<Long>(this.queue.timestamps);
	    this.lastTS = 0l;
	    while ((this.lastTS = this.knownTS.pollFirst()) < startTS) {
	    }
	    return this.lastTS;
	}
	this.lastTS = this.knownTS.poll();
	if (this.lastTS == null) {
	    this.knownTS = new LinkedList<Long>(this.queue.timestamps);
	    Long newVal = 0l;
	    while ((newVal = this.knownTS.pollFirst()) <= this.lastTS) {
	    }
	    this.lastTS = newVal;
	}
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
    public S readSegment() {
	final Long ts = getNextTS();
	if (ts == null) {
	    return null;
	}
	return this.queue.getTFromFile(this.queue.chunkBuffer.get(ts));
    }

    /**
     * @param offset
     *            {@link int} the offset to set
     */
    public void setOffset(final int offset) {
	if (offset > 0) {
	    throw new IllegalArgumentException(
		    "offset MUST be less than pr equal to 0!");
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