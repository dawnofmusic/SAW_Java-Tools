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
import java.util.concurrent.ConcurrentLinkedQueue;

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
    }

    /**
     * {@link ConcurrentLinkedQueue}<T> queue
     */
    private final ConcurrentLinkedQueue<F> queue;

    /**
     * {@link int} bufferSize
     */
    private int bufferSize = 0;

    /**
     * {@link ContainerOutputStream<T>} cos
     */
    private final ContainerOutputStream<F, S> cos;

    /**
     * {@link ContainerInputStream<T>} cis
     */
    private ContainerInputStream<F, S> cis;

    /** {@link SegmentFactory<T>} The segmentFactory. */
    private final SegmentFactory<F, S> segmentFactory;

    /** {@link long} The lastTimestamp. */
    private long waitUntil = -1;

    /** {@link boolean} The readBlocked. */
    private boolean readBlocked = false;

    /** {@link boolean} The writeBlocked. */
    private boolean writeBlocked = false;

    /**
     * {@link boolean} closed
     */
    private boolean closed;

    /**
     * SegmentQueueBuffer constructor.
     */
    public FrameQueueBuffer(final int maxBufferSizeVal,
	    final SegmentFactory<F, S> segmentFactoryRef,
	    final boolean closeableCIS) {
	super(maxBufferSizeVal);
	this.segmentFactory = segmentFactoryRef;
	setBevavior(BufferBehavior.fastAccessRingBuffer);
	this.queue = new ConcurrentLinkedQueue<F>();
	this.cos = new ContainerOutputStream<F, S>(null) {
	    @Override
	    public void close() throws IOException {
		FrameQueueBuffer.this.close();
	    }

	    @Override
	    public void flush() throws IOException {
	    }

	    @Override
	    public void writeFrame(final F frame) throws IOException {
		checkClosed();
		FrameQueueBuffer.this.write(frame);
		// System.out.println("wrote frame. size: " + queue.size());
	    }

	    @Override
	    public void writeFrames(final F[] frames, final int off,
		    final int len) throws IOException {
		checkClosed();
		for (int i = off; (i < frames.length) && (i < (off + len)); i++) {
		    writeFrame(frames[i]);
		}
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
	    public F readFrame() throws IOException {
		checkClosed();
		// System.out.println("read frame size: " + queue.size());
		return FrameQueueBuffer.this.read();
	    }

	    @Override
	    public int readFrames(final F[] frames, final int off, final int len)
		    throws IOException {
		checkClosed();
		int count = 0;
		for (int i = off; (i < frames.length) && (i < (off + len)); i++) {
		    frames[i] = readFrame();
		    count++;
		}
		// never return -1! Since this buffer could get empty but filled
		// afterwards, we would stop any communication (20130428 saw)
		// if (count == 0) {
		// return -1;
		// }
		return count;
	    }

	    @Override
	    public S readSegment(final int numberOfFrames) throws IOException {
		checkClosed();
		final F[] frames = FrameQueueBuffer.this.segmentFactory
			.createFrameArray(numberOfFrames);
		readFrames(frames, 0, numberOfFrames);
		// SEBASTIAN potential bug. Could be less frames than requested.
		return FrameQueueBuffer.this.segmentFactory
			.createSegment(frames);
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
     * read.
     * 
     * @return <code>T</code>
     */
    private F read() {
	switch (getBevavior()) {
	case trafficShapingBlockingBuffer:
	    while (this.readBlocked) {
		try {
		    Thread.sleep(1000);
		} catch (final InterruptedException e) {
		}
	    }
	    F poll = null;
	    while ((poll = this.queue.poll()) == null) {
		try {
		    Thread.sleep(500);
		} catch (final InterruptedException e) {
		}
	    }
	    if (this.waitUntil < 0) {
		// first visit, initialize
		this.waitUntil = System.nanoTime() + poll.getDurationNanos();
	    } else {
		final long nanosToSleep = this.waitUntil - System.nanoTime();
		this.waitUntil += poll.getDurationNanos();
		if (nanosToSleep > 0) {
		    try {
			Thread.sleep(ShapingHelper
				.getMillisPartFromNanos(nanosToSleep),
				ShapingHelper
					.getNanosRestFromNanos(nanosToSleep));
		    } catch (final InterruptedException e) {
		    }
		}
	    }
	    this.bufferSize -= poll.getSize();
	    return poll;
	case fastAccessRingBuffer:
	default:
	    while ((poll = this.queue.poll()) == null) {
		try {
		    Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	    }
	    if (poll != null) {
		this.bufferSize -= poll.getSize();
	    }
	    return poll;
	}
    }

    /**
     * @see de.wsdevel.tools.streams.buffer.Buffer#unblockReadAccess()
     */
    @Override
    public void unblockReadAccess() {
	this.readBlocked = false;
    }

    /**
     * @see de.wsdevel.tools.streams.buffer.Buffer#unblockWriteAccess()
     */
    @Override
    public void unblockWriteAccess() {
	this.writeBlocked = false;
    }

    /**
     * write.
     * 
     * @param frame
     *            <code>T</code>
     */
    private void write(final F frame) {
	switch (getBevavior()) {
	case trafficShapingBlockingBuffer:
	    while (this.writeBlocked
		    || (this.bufferSize > getMaximumBufferSize())) {
		try {
		    Thread.sleep(1000);
		} catch (final InterruptedException e) {
		}
	    }
	    this.queue.add(frame);
	    this.bufferSize += frame.getSize();
	    break;
	case fastAccessRingBuffer:
	default:
	    this.queue.add(frame);
	    this.bufferSize += frame.getSize();
	    while (this.bufferSize > getMaximumBufferSize()) {
		// read frames to dev null (20130424 saw)
		read();
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