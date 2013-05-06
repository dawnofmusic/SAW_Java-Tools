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

    /**
     * SegmentQueueBuffer constructor.
     */
    public FrameQueueBuffer(final int maxBufferSizeVal) {
	super(maxBufferSizeVal);
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
		throw new UnsupportedOperationException();
	    }
	};
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

    /** {@link long} The lastTimestamp. */
    private long waitUntil;

    /**
     * read.
     * 
     * @return <code>T</code>
     */
    private T read() {
	while (this.readBlocked) {
	    try {
		Thread.sleep(100);
	    } catch (InterruptedException e) {
	    }
	}
	// synchronized (this.queue) {
	final T poll = this.queue.poll();
	if (poll != null) {
	    this.bufferSize -= poll.getSize();
	    if (getBevavior() == BufferBehavior.shaping) {
		long millisToSleep = waitUntil - System.currentTimeMillis();
		waitUntil = System.currentTimeMillis() + poll.getDuration();
		if (millisToSleep > 0) {
		    try {
			Thread.sleep(millisToSleep);
		    } catch (InterruptedException e) {
		    }
		}
	    }
	    return poll;
	}
	// }
	return null;
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
	    } catch (InterruptedException e) {
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

    /** {@link boolean} The readBlocked. */
    private boolean readBlocked = false;

    /** {@link boolean} The writeBlocked. */
    private boolean writeBlocked = false;

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
     * @see de.wsdevel.tools.streams.buffer.Buffer#getCurrentBytes()
     * @return {@code long}
     */
    @Override
    public long getCurrentBytes() {
	return getBufferSize();
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

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================