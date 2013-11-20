/**
 * Class:    SegmentCache<br/>
 * <br/>
 * Created:  18.11.2013<br/>
 * Filename: SegmentCache.java<br/>
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

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.wsdevel.tools.streams.container.Frame;
import de.wsdevel.tools.streams.container.Segment;

/**
 * SegmentCache
 */
public abstract class SegmentCache<F extends Frame, S extends Segment<F>>
	extends AbstractQueue<S> implements Queue<S> {

    /**
     * Deserializer
     * 
     * @param <T>
     */
    public static interface Deserializer<S> {

	/**
	 * deserialize.
	 * 
	 * @param data
	 * @return <code>T</code>
	 */
	S deserialize(byte[] data);
    }

    /**
     * {@link int} MAXIMUM_MAX_DURATION_IN_MILLIS. 4 hours.
     */
    private static final int MAXIMUM_MAX_DURATION_IN_MILLIS = 4 * 60 * 60 * 10000;

    /**
     * {@link QueueBehaviour} behaviour
     */
    private QueueBehaviour behaviour = QueueBehaviour.maxSizeFIFOQueue;

    /**
     * {@link Deserializer<S>} deserializer
     */
    private Deserializer<S> deserializer;

    /**
     * {@link long} lastChunksBW
     */
    private long lastChunksBWInBPS = -1;

    /**
     * {@link long} lastOfferedTimestamp
     */
    private long lastOfferedTimestamp = -1;

    /**
     * {@link long} maxDurationInMillis
     */
    private long maxDurationInMillis = SegmentCache.MAXIMUM_MAX_DURATION_IN_MILLIS;

    /**
     * {@link String} name
     */
    private String name;

    /**
     * {@link Queue<Long>} timestamps
     */
    final Queue<Long> timestamps = new ConcurrentLinkedQueue<Long>();

    /**
     * calcAverageSegmentDuration.
     * 
     * @return <code>long</code>
     */
    public long calcAverageSegmentDuration() {
	final LinkedList<Long> clone = new LinkedList<Long>(this.timestamps);
	if (clone.size() < 2) {
	    return 0;
	}
	Long last = 0l;
	long sum = 0;
	for (final Long long1 : clone) {
	    if (last > 0) {
		sum += long1 - last;
	    }
	    last = long1;
	}
	return sum / (clone.size() - 1);
    }

    /**
     * findNearestTimestamp.
     * 
     * @param timestampRef
     *            <code>long</code>
     * @return <code>long</code>
     */
    public long findNearestTimestamp(final long timestampRef) {
	Long lastTimestamp = 0l;
	for (final Long timestamp : this.timestamps) {
	    if (timestampRef < timestamp) {
		return timestamp;
	    }
	    lastTimestamp = timestamp;
	}
	return lastTimestamp;
    }

    /**
     * @return the {@link QueueBehaviour} behaviour
     */
    public QueueBehaviour getBehaviour() {
	return this.behaviour;
    }

    /**
     * @return the {@link Deserializer<S>} deserializer
     */
    protected Deserializer<S> getDeserializer() {
	return this.deserializer;
    }

    /**
     * @return the {@link long} lastChunksBWInBPS
     */
    public long getLastChunksBWInBPS() {
	return this.lastChunksBWInBPS;
    }

    /**
     * @return the {@link long} lastOfferedTimestamp
     */
    protected long getLastOfferedTimestamp() {
	return this.lastOfferedTimestamp;
    }

    /**
     * @return the {@link long} maxDurationInMillis
     */
    public long getMaxDurationInMillis() {
	return this.maxDurationInMillis;
    }

    /**
     * @return the {@link String} name
     */
    public String getName() {
	return this.name;
    }

    /**
     * getSegmentForSequenceNumber.
     * 
     * @param sequenceNumber
     * @return
     */
    public abstract S getSegmentForSequenceNumber(int sequenceNumber);

    /**
     * getSegmentForTimestamp.
     * 
     * @param timestamp
     * @return
     */
    public abstract S getSegmentForTimestamp(Long timestamp);

    /**
     * iterator.
     * 
     * @see java.util.AbstractCollection#iterator()
     * @return {@link Iterator}<code>S</code>
     */
    @Override
    public Iterator<S> iterator() {
	throw new UnsupportedOperationException();
    }

    /**
     * offer.
     * 
     * @see java.util.Queue#offer(java.lang.Object)
     * @param e
     * @return
     */
    @Override
    public boolean offer(final S e) {
	return offer(e, System.currentTimeMillis());
    }

    /**
     * offer.
     * 
     * @param e
     *            <code>S</code>
     * @param timestamp
     *            <code>long</code>
     * @return <code>boolean</code>
     */
    public abstract boolean offer(final S e, final long timestamp);

    /**
     * peek.
     * 
     * @see java.util.Queue#peek()
     * @return <code>T</code>
     */
    @Override
    public S peek() {
	final Long peek = this.timestamps.peek();
	if (peek == null) {
	    return null;
	}
	return getSegmentForTimestamp(peek);
    }

    /**
     * poll.
     * 
     * @see java.util.Queue#poll()
     * @return
     */
    @Override
    public S poll() {
	final Long poll = this.timestamps.poll();
	if (poll == null) {
	    return null;
	}
	return getSegmentForTimestamp(poll);
    }

    /**
     * @param behaviourRef
     *            {@link QueueBehaviour} the behaviour to set
     */
    public void setBehaviour(final QueueBehaviour behaviourRef) {
	this.behaviour = behaviourRef;
    }

    /**
     * @param deserializer
     *            {@link Deserializer<S>} the deserializer to set
     */
    protected void setDeserializer(final Deserializer<S> deserializer) {
	this.deserializer = deserializer;
    }

    /**
     * @param lastChunksBWInBPS
     *            {@link long} the lastChunksBWInBPS to set
     */
    protected void setLastChunksBWInBPS(final long lastChunksBWInBPS) {
	this.lastChunksBWInBPS = lastChunksBWInBPS;
    }

    /**
     * @param lastOfferedTimestamp
     *            {@link long} the lastOfferedTimestamp to set
     */
    protected void setLastOfferedTimestamp(final long lastOfferedTimestamp) {
	this.lastOfferedTimestamp = lastOfferedTimestamp;
    }

    /**
     * @param maxDurationInMillisVal
     *            {@link long} the maxDurationInMillis to set
     */
    public void setMaxDurationInMillis(final long maxDurationInMillisVal) {
	if (maxDurationInMillisVal > SegmentCache.MAXIMUM_MAX_DURATION_IN_MILLIS) {
	    throw new IllegalArgumentException(
		    "Maximum duration must not exceed [" + SegmentCache.MAXIMUM_MAX_DURATION_IN_MILLIS + "] milliseconds."); //$NON-NLS-1$//$NON-NLS-2$
	}
	this.maxDurationInMillis = maxDurationInMillisVal;
    }

    /**
     * @param nameVal
     *            {@link String} the name to set
     */
    public void setName(final String nameVal) {
	if (nameVal == null) {
	    throw new NullPointerException("nameVal must not be null!"); //$NON-NLS-1$
	}
	if (nameVal.trim().equals("")) { //$NON-NLS-1$
	    throw new IllegalArgumentException(
		    "nameVal must not be an empty String!"); //$NON-NLS-1$
	}
	this.name = nameVal;
    }

    /**
     * size.
     * 
     * @see java.util.AbstractCollection#size()
     * @return <code>int</code> the size of this queue.
     */
    @Override
    public int size() {
	return this.timestamps.size();
    }

}
// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================