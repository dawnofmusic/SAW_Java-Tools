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

package de.wsdevel.tools.streams.container;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * FrameQueueBuffer
 */
public class FrameQueueBuffer<T extends Frame> {

    /**
     * {@link ConcurrentLinkedQueue}<T> queue
     */
    private final ConcurrentLinkedQueue<T> queue;

    /**
     * {@link int} maxBufferSize
     */
    private int maxBufferSize = -1;

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
	this.queue = new ConcurrentLinkedQueue<T>();
	this.maxBufferSize = maxBufferSizeVal;
	this.cos = new ContainerOutputStream<T>(null) {
	    @Override
	    public void writeFrame(final T frame) throws IOException {
		FrameQueueBuffer.this.write(frame);
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
	    public T readFrame() throws IOException {
		return FrameQueueBuffer.this.read();
	    }

	    @Override
	    public int readFrames(T[] frames, int off, int len)
		    throws IOException {
		int count = 0;
		for (int i = off; i < frames.length && i < off + len; i++) {
		    frames[i] = readFrame();
		}
		if (count == 0) {
		    return -1;
		}
		return count;
	    }

	    @Override
	    public Segment<T> readSegment(int numberOfFrames)
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

    /**
     * read.
     * 
     * @return <code>T</code>
     */
    public T read() {
	synchronized (this.queue) {
	    final T poll = this.queue.poll();
	    if (poll != null) {
		this.bufferSize -= poll.getSize();
		return poll;
	    }
	}
	return null;
    }

    /**
     * write.
     * 
     * @param frame
     *            <code>T</code>
     */
    public void write(final T frame) {
	synchronized (this.queue) {
	    this.queue.add(frame);
	    this.bufferSize += frame.getSize();
	}
	while (this.bufferSize > this.maxBufferSize) {
	    // read frames to dev null (20130424 saw)
	    read();
	}
    }

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================