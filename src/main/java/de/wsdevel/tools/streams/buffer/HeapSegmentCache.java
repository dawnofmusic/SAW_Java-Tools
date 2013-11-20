/**
 * Class:    HeapSegmentQueue<br/>
 * <br/>
 * Created:  18.11.2013<br/>
 * Filename: HeapSegmentQueue.java<br/>
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

import java.util.TreeMap;

import de.wsdevel.tools.streams.container.Frame;
import de.wsdevel.tools.streams.container.Segment;

/**
 * HeapSegmentCache
 */
public class HeapSegmentCache<F extends Frame, S extends Segment<F>> extends
	SegmentCache<F, S> {

    /**
     * {@link TreeMap<Long,S>} segmentStore
     */
    private final TreeMap<Long, S> segmentStore = new TreeMap<Long, S>();

    /**
     * {@link TreeMap<Integer,S>} segmentStoreBySequenceNumber
     */
    private final TreeMap<Integer, S> segmentStoreBySequenceNumber = new TreeMap<Integer, S>();

    /**
     * HeapSegmentQueue constructor.
     */
    public HeapSegmentCache() {
	setMaxDurationInMillis(60000);
    }

    /**
     * getSegmentForSequenceNumber.
     * 
     * @see de.wsdevel.tools.streams.buffer.SegmentCache#getSegmentForSequenceNumber(int)
     * @param sequenceNumber
     * @return
     */
    @Override
    public S getSegmentForSequenceNumber(final int sequenceNumber) {
	return this.segmentStoreBySequenceNumber.get(sequenceNumber);
    }

    /**
     * getSegmentForTimestamp.
     * 
     * @see de.wsdevel.tools.streams.buffer.SegmentCache#getSegmentForTimestamp(java.lang.Long)
     * @param timestamp
     *            {@link Long}
     * @return <code>S</code>
     */
    @Override
    public S getSegmentForTimestamp(final Long timestamp) {
	return this.segmentStore.get(timestamp);
    }

    /**
     * offer.
     * 
     * @see de.wsdevel.tools.streams.buffer.SegmentCache#offer(de.wsdevel.tools.streams.container.Segment,
     *      long)
     * @param e
     *            <code>S</code>
     * @param timestamp
     *            <code>long</code>
     * @return <code>boolean</code>
     */
    @Override
    public boolean offer(final S e, final long timestamp) {
	if (getLastOfferedTimestamp() > -1) {
	    final long deltat = timestamp - getLastOfferedTimestamp();
	    if (deltat > 0) {
		setLastChunksBWInBPS(Math.round((1000l * e.getSize() * 8l)
			/ (double) deltat));
		// System.out.println("size: " + e.getSize() + ", bytes.size: "
		// + e.getData().length + ", deltat: " + deltat
		// + ", lastChunkBW: " + this.lastChunksBWInBPS);
	    }
	}
	this.segmentStore.put(timestamp, e);
	this.segmentStoreBySequenceNumber.put(e.getSequenceNumber(), e);
	setLastOfferedTimestamp(timestamp);
	this.timestamps.add(timestamp);
	switch (getBehaviour()) {
	case maxSizeFIFOQueue:
	    final Long peek = this.timestamps.peek();
	    if ((peek + getMaxDurationInMillis()) < System.currentTimeMillis()) {
		final S remove = this.segmentStore.remove(this.timestamps
			.poll());
		this.segmentStoreBySequenceNumber.remove(remove
			.getSequenceNumber());
	    }
	case growingQueue:
	default:
	    return true;
	}
    }

}
// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================