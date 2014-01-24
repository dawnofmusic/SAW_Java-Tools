/**
 * Class:    FrameQueueBuffer<br/>
 * <br/>
 * Created:  24.04.2013<br/>
 * Filename: FrameQueueBuffer.java<br/>
 * Version:  $Revision: $<br/>
 * <br/>
 * last modified on $Date:  $<br/>
 *               by $Author: $<br/>
 * <br/>
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * (c) 2013, Sebastian A. Weiss - All rights reserved.
 */

package de.wsdevel.tools.streams.buffer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import de.wsdevel.tools.streams.container.ContainerInputStream;
import de.wsdevel.tools.streams.container.ContainerOutputStream;
import de.wsdevel.tools.streams.container.Frame;
import de.wsdevel.tools.streams.container.Segment;
import de.wsdevel.tools.streams.shaping.ShapingHelper;

/**
 * FrameQueueBuffer
 */
public class FrameQueueBuffer<F extends Frame, S extends Segment<F>> extends
	Buffer {

    private static final class Lock {
    }

    /**
     * SegmentFactory
     */
    public static interface SegmentFactory<F extends Frame, S extends Segment<F>> {

	/**
	 * createFrameArray.
	 * 
	 * @param size
	 * @return
	 */
	F[] createFrameArray(int size);

	/**
	 * createSegment.
	 * 
	 * @param frames
	 * @return
	 */
	S createSegment(F[] frames);

	/**
	 * getMaxFrameSize.
	 * 
	 * @return <code>int</code>
	 */
	int getMaxFrameSize();
    }

    /**
     * {@link ContainerInputStream<T>} cis
     */
    private ContainerInputStream<F, S> cis;

    /**
     * {@link boolean} closed
     */
    private boolean closed;

    /**
     * {@link ContainerOutputStream<T>} cos
     */
    private final ContainerOutputStream<F, S> cos;

    /**
     * {@link BlockingQueue}<S> queue
     */
    private final BlockingQueue<S> queue;

    /** {@link boolean} The readBlocked. */
    private boolean readBlocked = false;

    /** {@link Object} readingLock */
    private final Object readingLock = new Lock();

    /** {@link long} The lastTimestamp. */
    private long waitUntil = -1;

    /** {@link boolean} The writeBlocked. */
    private boolean writeBlocked = false;

    /** {@link Object} writingLock */
    private final Object writingLock = new Lock();

    /**
     * SegmentQueueBuffer constructor.
     */
    public FrameQueueBuffer(final int maxNumberOfElements,
	    final boolean closeableCIS, final boolean keepFillingLevelHistory) {
	super(maxNumberOfElements, keepFillingLevelHistory);
	setBevavior(BufferBehavior.fastAccessRingBuffer);
	// (20131105 saw) selection of best performing queues. For us
	// ArrayBlockingQueue should be the best selection.
	// this.queue = new ConcurrentLinkedQueue<F>();
	// this.queue = new LinkedBlockingQueue<F>();
	this.queue = new ArrayBlockingQueue<S>(maxNumberOfElements);
	this.cos = new ContainerOutputStream<F, S>(null) {
	    @Override
	    public void close() throws IOException {
		FrameQueueBuffer.this.close();
	    }

	    @Override
	    public void flush() throws IOException {
	    }

	    @Override
	    public void writeSegment(final S segment) throws IOException {
		checkClosed();
		FrameQueueBuffer.this.write(segment);
	    }
	};
	this.cis = new ContainerInputStream<F, S>(null) {
	    @Override
	    public void close() throws IOException {
		if (closeableCIS) {
		    FrameQueueBuffer.this.close();
		}
	    }

	    @Override
	    public S readSegment() throws IOException {
		checkClosed();
		return FrameQueueBuffer.this.read();
	    }
	};
	addPropertyChangeListener(Buffer.PROPERTY_NAME_STATE,
		new PropertyChangeListener() {
		    @Override
		    public void propertyChange(final PropertyChangeEvent evt) {
			if (evt.getNewValue() == BufferState.filling) {
			    FrameQueueBuffer.this.waitUntil = -1;
			}
		    }
		});
    }

    /**
     * @see de.wsdevel.tools.streams.buffer.Buffer#blockReadAccess()
     */
    @Override
    public void blockReadAccess() {
	this.readBlocked = true;
    }

    /**
     * @see de.wsdevel.tools.streams.buffer.Buffer#blockWriteAccess()
     */
    @Override
    public void blockWriteAccess() {
	this.writeBlocked = true;
    }

    /**
     * checkClosed.
     * 
     * @throws IOException
     */
    private void checkClosed() throws IOException {
	if (this.closed) {
	    throw new IOException("FrameQueueBuffer already closed!");
	}
    }

    /**
     * clear.
     */
    public void clear() {
	this.queue.clear();
	this.waitUntil = -1;
	resetFillingLevel();
    }

    /**
     * close.
     */
    private void close() {
	this.closed = true;
	this.queue.clear();
    }

    /**
     * getContainerInputStream.
     * 
     * @return {@link ContainerInputStream}
     */
    public ContainerInputStream<F, S> getContainerInputStream() {
	return this.cis;
    }

    /**
     * getContainerOutputStream.
     * 
     * @return {@link ContainerOutputStream}
     */
    public ContainerOutputStream<F, S> getContainerOutputStream() {
	return this.cos;
    }

    /**
     * internalFastWrite.
     * 
     * @param frame
     */
    private void internalFastWrite(final S frame) {
	updateFillingLevelHistory();
	while (!this.queue.offer(frame)) {
	    // read frames to dev null (20131108 saw)
	    read();
	}
	updateFillingLevelHistory();
	synchronized (this.readingLock) {
	    this.readingLock.notifyAll();
	}
    }

    /**
     * internalShapingWrite.
     * 
     * @param frame
     */
    private void internalShapingWrite(final S frame) {
	updateFillingLevelHistory();
	if (this.queue.offer(frame)) {
	    updateFillingLevelHistory();
	    synchronized (this.readingLock) {
		this.readingLock.notifyAll();
	    }
	} else {
	    // (20131108 saw) queue is full
	    unblockReadAccess();
	    // SEBASTIAN frame is lost, improvements?
	}
    }

    /**
     * read.
     * 
     * @return <code>T</code>
     */
    private S read() {
	switch (getBevavior()) {
	case trafficShapingBlockingBuffer:
	    synchronized (this.readingLock) {
		while (this.readBlocked) {
		    try {
			this.readingLock.wait(200);
		    } catch (final InterruptedException e) {
		    }
		}
	    }
	    // try {
	    updateFillingLevelHistory();
	    final S poll = this.queue.poll();
	    if (poll == null) {
		this.waitUntil = -1;
		return null;
	    } else {
		if (this.waitUntil < 0) {
		    // first visit, initialize
		    this.waitUntil = System.nanoTime()
			    + poll.getDurationNanos();
		} else {
		    final long nanosToSleep = this.waitUntil
			    - System.nanoTime();
		    this.waitUntil += poll.getDurationNanos();
		    if (nanosToSleep > 0) {

			// (20131122 saw) Thread.sleep is better than
			// wait()!

			// long now = System.nanoTime();
			// synchronized (this.readingLock) {
			// try {
			// this.readingLock
			// .wait(ShapingHelper
			// .getMillisPartFromNanos(nanosToSleep),
			// ShapingHelper
			// .getNanosRestFromNanos(nanosToSleep));
			// } catch (final InterruptedException e) {
			// }
			// }

			try {
			    Thread.sleep(
				    ShapingHelper
					    .getMillisPartFromNanos(nanosToSleep),
				    ShapingHelper
					    .getNanosRestFromNanos(nanosToSleep));
			} catch (final InterruptedException e) {
			}
		    }
		}
		synchronized (this.writingLock) {
		    this.writingLock.notifyAll();
		}
		updateFillingLevelHistory();
		return poll;
	    }
	    // } catch (final InterruptedException e) {
	    // }
	case fastAccessRingBuffer:
	default:
	    updateFillingLevelHistory();
	    // System.out.println("during read before take!");
	    final S poll2 = this.queue.poll();
	    // System.out.println("during read after take!");
	    if (poll2 != null) {
		updateFillingLevelHistory();
	    }
	    return poll2;
	}
	// // will happen only in case of being interrupted
	// return null;
    }

    /**
     * resetShaping.
     */
    public void resetShaping() {
	this.waitUntil = -1;
    }

    /**
     * @see de.wsdevel.tools.streams.buffer.Buffer#unblockReadAccess()
     */
    @Override
    public void unblockReadAccess() {
	synchronized (this.readingLock) {
	    this.readBlocked = false;
	    this.readingLock.notifyAll();
	}
    }

    /**
     * @see de.wsdevel.tools.streams.buffer.Buffer#unblockWriteAccess()
     */
    @Override
    public void unblockWriteAccess() {
	synchronized (this.writingLock) {
	    this.writeBlocked = false;
	    this.writingLock.notifyAll();
	}
    }

    /**
     * write.
     * 
     * @param frame
     *            <code>T</code>
     */
    private void write(final S frame) {
	if (frame == null) {
	    throw new NullPointerException("frame MUST NOT be null.");
	}
	switch (getBevavior()) {
	case trafficShapingBlockingBuffer:
	    synchronized (this.writingLock) {
		while (this.writeBlocked
			|| (getCurrentNumberOfElements() > getMaximumNumberOfElements())) {
		    try {
			this.writingLock.wait(200);
		    } catch (final InterruptedException e) {
		    }
		}
		internalShapingWrite(frame);
	    }
	    break;
	case fastAccessRingBuffer:
	default:
	    synchronized (this.readingLock) {
		internalFastWrite(frame);
		while (getCurrentNumberOfElements() > getMaximumNumberOfElements()) {
		    // read frames to dev null (20130424 saw)
		    read();
		    // final S read = read();
		    // System.out
		    // .println("during write reading to dev null Segment ["
		    // + read.getSequenceNumber() + "]!");
		}
	    }
	    break;
	}
    }

    /**
     * getCurrentNumberOfElements.
     * 
     * @see de.wsdevel.tools.streams.buffer.Buffer#getCurrentNumberOfElements()
     * @return <code>long</code>
     */
    @Override
    public long getCurrentNumberOfElements() {
	return this.queue.size();
    }

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================