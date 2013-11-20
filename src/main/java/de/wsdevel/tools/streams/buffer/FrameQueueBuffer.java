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
     * {@link int} bufferSize
     */
    private int bufferSize = 0;

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

    // /** {@link SegmentFactory<T>} The segmentFactory. */
    // private final SegmentFactory<F, S> segmentFactory;

    /** {@link long} The lastTimestamp. */
    private long waitUntil = -1;

    /** {@link boolean} The writeBlocked. */
    private boolean writeBlocked = false;

    /** {@link Object} writingLock */
    private final Object writingLock = new Lock();

    /**
     * SegmentQueueBuffer constructor.
     */
    public FrameQueueBuffer(final int maxBufferSizeVal,
	    final SegmentFactory<F, S> segmentFactoryRef,
	    final boolean closeableCIS) {
	super(maxBufferSizeVal);
	// this.segmentFactory = segmentFactoryRef;
	setBevavior(BufferBehavior.fastAccessRingBuffer);
	// (20131105 saw) selection of best performing queues. For us
	// ArrayBlockingQueue should be the best selection.
	// this.queue = new ConcurrentLinkedQueue<F>();
	// this.queue = new LinkedBlockingQueue<F>();

	final int maxElements = Math.round((1.1f * maxBufferSizeVal)
		/ segmentFactoryRef.getMaxFrameSize());
	this.queue = new ArrayBlockingQueue<S>(maxElements);
	this.cos = new ContainerOutputStream<F, S>(null) {
	    @Override
	    public void close() throws IOException {
		FrameQueueBuffer.this.close();
	    }

	    @Override
	    public void flush() throws IOException {
	    }

	    // @Override
	    // public void writeFrame(final F frame) throws IOException {
	    // checkClosed();
	    // FrameQueueBuffer.this.write(frame);
	    // // System.out.println("wrote frame. size: " + queue.size());
	    // }

	    @Override
	    public void writeSegment(S segment) throws IOException {
		checkClosed();
		FrameQueueBuffer.this.write(segment);
	    }

	    // @Override
	    // public void writeFrames(final F[] frames, final int off,
	    // final int len) throws IOException {
	    // checkClosed();
	    // for (int i = off; (i < frames.length) && (i < (off + len)); i++)
	    // {
	    // writeFrame(frames[i]);
	    // }
	    // }
	};
	this.cis = new ContainerInputStream<F, S>(null) {
	    @Override
	    public void close() throws IOException {
		if (closeableCIS) {
		    FrameQueueBuffer.this.close();
		}
	    }

	    // @Override
	    // public F readFrame() throws IOException {
	    // checkClosed();
	    // // System.out.println("read frame size: " + queue.size());
	    // return (F) FrameQueueBuffer.this.read();
	    // }
	    //
	    // @Override
	    // public int readFrames(final F[] frames, final int off, final int
	    // len)
	    // throws IOException {
	    // checkClosed();
	    // int count = 0;
	    // for (int i = off; (i < frames.length) && (i < (off + len)); i++)
	    // {
	    // frames[i] = readFrame();
	    // count++;
	    // }
	    // // never return -1! Since this buffer could get empty but filled
	    // // afterwards, we would stop any communication (20130428 saw)
	    // // if (count == 0) {
	    // // return -1;
	    // // }
	    // return count;
	    // }

	    @Override
	    public S readSegment() throws IOException {
		checkClosed();
		return FrameQueueBuffer.this.read();
		// final F[] frames = FrameQueueBuffer.this.segmentFactory
		// .createFrameArray(numberOfFrames);
		// readFrames(frames, 0, numberOfFrames);
		// // SEBASTIAN potential bug. Could be less frames than
		// requested.
		// return FrameQueueBuffer.this.segmentFactory
		// .createSegment(frames);
	    }
	};
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
	this.bufferSize = 0;
	this.waitUntil = -1;
	updateFillingLevelHistory();
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
     * @see de.wsdevel.tools.streams.buffer.Buffer#getCurrentBytes()
     * @return {@code long}
     */
    @Override
    public long getCurrentBytes() {
	return this.bufferSize;
    }

    /**
     * internalFastWrite.
     * 
     * @param frame
     */
    private void internalFastWrite(final S frame) {
	if (this.queue.offer(frame)) {
	    this.bufferSize += frame.getSize();
	    updateFillingLevelHistory();
	    this.readingLock.notifyAll();
	} else {
	    // read frames to dev null (20131108 saw)
	    read();
	}
    }

    /**
     * internalShapingWrite.
     * 
     * @param frame
     */
    private void internalShapingWrite(final S frame) {
	synchronized (this.readingLock) {
	    if (this.queue.offer(frame)) {
		this.bufferSize += frame.getSize();
		updateFillingLevelHistory();
		this.readingLock.notifyAll();
	    } else {
		// (20131108 saw) queue is full
		unblockReadAccess();
		// SEBASTIAN frame is lost, improvements?
	    }
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
			this.readingLock.wait(1000);
		    } catch (final InterruptedException e) {
		    }
		}
	    }
	    try {
		final S poll = this.queue.take();
		if (this.waitUntil < 0) {
		    // first visit, initialize
		    this.waitUntil = System.nanoTime()
			    + poll.getDurationNanos();
		} else {
		    final long nanosToSleep = this.waitUntil
			    - System.nanoTime();
		    this.waitUntil += poll.getDurationNanos();
		    if (nanosToSleep > 0) {
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
		this.bufferSize -= poll.getSize();
		updateFillingLevelHistory();
		return poll;
	    } catch (final InterruptedException e) {
	    }
	case fastAccessRingBuffer:
	default:
	    try {
		final S poll = this.queue.take();
		if (poll != null) {
		    this.bufferSize -= poll.getSize();
		    updateFillingLevelHistory();
		}
		return poll;
	    } catch (final InterruptedException e) {
	    }
	}
	// will happen only in case of being interrupted
	return null;
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
			|| (this.bufferSize > getMaximumBufferSize())) {
		    try {
			this.writingLock.wait(1000);
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
		while (this.bufferSize > getMaximumBufferSize()) {
		    // read frames to dev null (20130424 saw)
		    read();
		}
	    }
	    break;
	}
    }

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================