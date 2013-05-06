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

/**
 * FrameQueueBuffer
 */
public class FrameQueueBuffer<T extends Frame> extends Buffer {

    /**
     * SegmentFactory
     */
    public static interface SegmentFactory<T extends Frame> {

	/**
	 * createFrameArray.
	 * 
	 * @param size
	 * @return
	 */
	T[] createFrameArray(int size);

	/**
	 * createSegment.
	 * 
	 * @param frames
	 * @return
	 */
	Segment<T> createSegment(T[] frames);
    }

    /**
     * {@link ConcurrentLinkedQueue}<T> queue
     */
    private final ConcurrentLinkedQueue<T> queue;

    /**
     * {@link int} bufferSize
     */
    private int bufferSize = 0;

    /**
     * {@link ContainerOutputStream<T>} cos
     */
    private final ContainerOutputStream<T> cos;

    /**
     * {@link ContainerInputStream<T>} cis
     */
    private ContainerInputStream<T> cis;

    /** {@link SegmentFactory<T>} The segmentFactory. */
    private final SegmentFactory<T> segmentFactory;

    /** {@link long} The lastTimestamp. */
    private long waitUntil;

    /** {@link boolean} The readBlocked. */
    private boolean readBlocked = false;

    /** {@link boolean} The writeBlocked. */
    private boolean writeBlocked = false;

    /**
     * SegmentQueueBuffer constructor.
     */
    public FrameQueueBuffer(final int maxBufferSizeVal,
	    final SegmentFactory<T> segmentFactoryRef) {
	super(maxBufferSizeVal);
	this.segmentFactory = segmentFactoryRef;
	setBevavior(BufferBehavior.fast);
	this.queue = new ConcurrentLinkedQueue<T>();
	this.cos = new ContainerOutputStream<T>(null) {
	    @Override
	    public void close() throws IOException {
	    }

	    @Override
	    public void flush() throws IOException {
	    }

	    @Override
	    public void writeFrame(final T frame) throws IOException {
		FrameQueueBuffer.this.write(frame);
		// System.out.println("wrote frame. size: " + queue.size());
	    }

	    @Override
	    public void writeFrames(final T[] frames, final int off,
		    final int len) throws IOException {
		for (int i = off; (i < frames.length) && (i < (off + len)); i++) {
		    writeFrame(frames[i]);
		}
	    }
	};
	this.cis = new ContainerInputStream<T>(null) {
	    @Override
	    public void close() throws IOException {
	    }

	    @Override
	    public T readFrame() throws IOException {
		// System.out.println("read frame size: " + queue.size());
		return FrameQueueBuffer.this.read();
	    }

	    @Override
	    public int readFrames(final T[] frames, final int off, final int len)
		    throws IOException {
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
	    public Segment<T> readSegment(final int numberOfFrames)
		    throws IOException {
		final T[] frames = FrameQueueBuffer.this.segmentFactory
			.createFrameArray(numberOfFrames);
		readFrames(frames, 0, numberOfFrames);
		// SEBASTIAN potential bug. Could be frames than requested.
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
     * getContainerInputStream.
     * 
     * @return {@link ContainerInputStream}
     */
    public ContainerInputStream<T> getContainerInputStream() {
	return this.cis;
    }

    /**
     * getContainerOutputStream.
     * 
     * @return {@link ContainerOutputStream}
     */
    public ContainerOutputStream<T> getContainerOutputStream() {
	return this.cos;
    }

    /**
     * @see de.wsdevel.tools.streams.buffer.Buffer#getCurrentBytes()
     * @return {@code long}
     */
    @Override
    public long getCurrentBytes() {
	return getBufferSize();
    }

    /**
     * read.
     * 
     * @return <code>T</code>
     */
    private T read() {
	while (this.readBlocked) {
	    try {
		Thread.sleep(100);
	    } catch (final InterruptedException e) {
	    }
	}
	// synchronized (this.queue) {
	final T poll = this.queue.poll();
	if (poll != null) {
	    this.bufferSize -= poll.getSize();
	    if (getBevavior() == BufferBehavior.shaping) {
		final long millisToSleep = this.waitUntil
			- System.currentTimeMillis();
		this.waitUntil = System.currentTimeMillis()
			+ poll.getDuration();
		if (millisToSleep > 0) {
		    try {
			Thread.sleep(millisToSleep);
		    } catch (final InterruptedException e) {
		    }
		}
	    }
	    return poll;
	}
	// }
	return null;
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
    private void write(final T frame) {
	while (this.writeBlocked) {
	    try {
		Thread.sleep(100);
	    } catch (final InterruptedException e) {
	    }
	}

	// synchronized (this.queue) {
	this.queue.add(frame);
	this.bufferSize += frame.getSize();
	System.out.println("buffer size [" + (this.bufferSize / 1024) + " KB]");
	// }
	while (this.bufferSize > getMaximumBufferSize()) {
	    // read frames to dev null (20130424 saw)
	    read();
	    // System.out.println("discarded one frame");
	}
    }

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================