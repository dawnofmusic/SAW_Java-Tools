/**
 * Class:    FileSystemFrameQueue<br/>
 * <br/>
 * Created:  22.05.2013<br/>
 * Filename: FileSystemFrameQueue.java<br/>
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.AbstractQueue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.wsdevel.tools.streams.Streams;
import de.wsdevel.tools.streams.container.Frame;
import de.wsdevel.tools.streams.container.Segment;

/**
 * FileSystemFrameQueue
 * 
 */
public class FileSystemFrameQueue<F extends Frame, S extends Segment<F>>
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
     * {@link int} DEFAULT_COPY_BUFFER_SIZE
     */
    private static final int DEFAULT_COPY_BUFFER_SIZE = 256 * 1024;

    /** {@link Log} LOG */
    private static final Log LOG = LogFactory
	    .getLog(FileSystemFrameQueue.class);

    /**
     * {@link int} MAXIMUM_MAX_DURATION_IN_MILLIS. 4 hours.
     */
    private static final int MAXIMUM_MAX_DURATION_IN_MILLIS = 4 * 60 * 60 * 10000;

    /**
     * {@link String} SOURCE_CACHE_ROOT_DIRECTORY
     */
    private static final String SOURCE_CACHE_ROOT_DIRECTORY = ".source-cache"; //$NON-NLS-1$

    /**
     * {@link String} URL_BAD_CHAR_REGEXP
     */
    public static final String URL_BAD_CHAR_REGEXP = "[\\?\\=\\s+\\/\\:\\\\]"; //$NON-NLS-1$

    /**
     * {@link String} URL_BAD_CHAR_REPLACEMENT
     */
    public static final String URL_BAD_CHAR_REPLACEMENT = "_"; //$NON-NLS-1$

    /**
     * createChunkgFileForChunkURI.
     * 
     * @param chunkURI
     *            {@link String}
     * @param streamnodeConfigCacheDir
     *            {@link String}
     * @return {@link File}
     */
    private static File createChunkFileForChunkURI(final String chunkURI,
	    final File streamnodeConfigCacheDir) {
	return new File(streamnodeConfigCacheDir.getAbsolutePath()
		+ File.separator
		+ chunkURI.replaceAll(FileSystemFrameQueue.URL_BAD_CHAR_REGEXP,
			FileSystemFrameQueue.URL_BAD_CHAR_REPLACEMENT));
    }

    /**
     * {@link QueueBehaviour} behaviour
     */
    private QueueBehaviour behaviour = QueueBehaviour.maxSizeFIFOQueue;

    /**
     * {@link File} cacheDir
     */
    private File cacheDir;

    /**
     * {@link HashMap<Long,File>} chunkBuffer
     */
    final HashMap<Long, File> chunkBuffer = new HashMap<Long, File>();

    /**
     * {@link Deserializer<S>} deserializer
     */
    private Deserializer<S> deserializer;

    /**
     * {@link long} maxDurationInMillis
     */
    private long maxDurationInMillis = FileSystemFrameQueue.MAXIMUM_MAX_DURATION_IN_MILLIS;

    /**
     * {@link String} name
     */
    private String name;

    /**
     * {@link Queue<Long>} timestamps
     */
    final Queue<Long> timestamps = new ConcurrentLinkedQueue<Long>();

    /**
     * FileSystemFrameQueue constructor.
     * 
     * @param deserializerRef
     */
    public FileSystemFrameQueue(final Deserializer<S> deserializerRef) {
	this("UNKNOWN", deserializerRef); //$NON-NLS-1$
    }

    /**
     * FileSystemFrameQueue constructor.
     * 
     * @param nameVal
     * @param deserializerRef
     */
    public FileSystemFrameQueue(final String nameVal,
	    final Deserializer<S> deserializerRef) {
	setName(nameVal);
	this.deserializer = deserializerRef;
    }

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
     * @return the {@link QueueBehaviour} behaviour
     */
    public QueueBehaviour getBehaviour() {
	return this.behaviour;
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
     * getTFromFile.
     * 
     * @param file
     *            {@link File}
     */
    S getSFromFile(final File file) {
	if (file != null) {
	    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    try {
		Streams.readFromInWriteToOut(new FileInputStream(file), baos,
			FileSystemFrameQueue.DEFAULT_COPY_BUFFER_SIZE);
		final S deserialize = this.deserializer.deserialize(baos
			.toByteArray());
		deserialize.setId(file.getName());
		return deserialize;
	    } catch (final FileNotFoundException e) {
		FileSystemFrameQueue.LOG.error(e.getLocalizedMessage(), e);
	    }
	}
	return null;
    }

    /**
     * iterator.
     * 
     * @see java.util.AbstractCollection#iterator()
     * @return
     */
    @Override
    public Iterator<S> iterator() {
	throw new UnsupportedOperationException();
    }

    /**
     * {@link long} lastOfferedTimestamp
     */
    private long lastOfferedTimestamp = -1;

    /**
     * {@link long} lastChunksBW
     */
    private long lastChunksBWInBPS = -1;

    /**
     * @return the {@link long} lastChunksBWInBPS
     */
    public long getLastChunksBWInBPS() {
	return lastChunksBWInBPS;
    }

    /**
     * offer.
     * 
     * @see java.util.Queue#offer(java.lang.Object)
     * @param e
     * @return
     */
    public boolean offer(final S e, final long timestamp) {
	final File file = createChunkFileForChunkURI(Long.toString(timestamp)
		+ "-" + this.name, this.cacheDir); //$NON-NLS-1$
	if (lastOfferedTimestamp > -1) {
	    long deltat = timestamp - lastOfferedTimestamp;
	    if (deltat > 0) {
		this.lastChunksBWInBPS = Math.round((1000l * e.getSize() * 8l)
			/ (double) deltat);
		// System.out.println("size: " + e.getSize() + ", bytes.size: "
		// + e.getData().length + ", deltat: " + deltat
		// + ", lastChunkBW: " + this.lastChunksBWInBPS);
	    }
	}
	if (writeSegmentToFS(e, file)) {
	    this.lastOfferedTimestamp = timestamp;
	    this.chunkBuffer.put(timestamp, file);
	    this.timestamps.add(timestamp);
	    switch (this.behaviour) {
	    case maxSizeFIFOQueue:
		final Long peek = this.timestamps.peek();
		if ((peek + getMaxDurationInMillis()) < System
			.currentTimeMillis()) {
		    final File remove = this.chunkBuffer.remove(this.timestamps
			    .poll());
		    if (!remove.delete()) {
			FileSystemFrameQueue.LOG
				.error("Could not delete [" + remove.getAbsolutePath() + "]"); //$NON-NLS-1$//$NON-NLS-2$
		    }
		}
	    case growingQueue:
	    default:
		return true;
	    }
	}
	return false;
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
	return getSFromFile(this.chunkBuffer.get(peek));
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
	return getSFromFile(this.chunkBuffer.get(poll));
    }

    /**
     * @param behaviourRef
     *            {@link QueueBehaviour} the behaviour to set
     */
    public void setBehaviour(final QueueBehaviour behaviourRef) {
	this.behaviour = behaviourRef;
    }

    /**
     * @param maxDurationInMillisVal
     *            {@link long} the maxDurationInMillis to set
     */
    public void setMaxDurationInMillis(final long maxDurationInMillisVal) {
	if (maxDurationInMillisVal > FileSystemFrameQueue.MAXIMUM_MAX_DURATION_IN_MILLIS) {
	    throw new IllegalArgumentException(
		    "Maximum duration must not exceed [" + FileSystemFrameQueue.MAXIMUM_MAX_DURATION_IN_MILLIS + "] milliseconds."); //$NON-NLS-1$//$NON-NLS-2$
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
	this.cacheDir = new File(
		FileSystemFrameQueue.SOURCE_CACHE_ROOT_DIRECTORY
			+ File.separator + this.name);
	if (this.cacheDir.exists()) {
	    final File[] listFiles = this.cacheDir.listFiles();
	    for (final File file : listFiles) {
		// clean cache directory (20130419 saw)
		file.delete();
	    }
	}
	if (!this.cacheDir.exists() && !this.cacheDir.mkdirs()) {
	    FileSystemFrameQueue.LOG.error("Could not create dir [" //$NON-NLS-1$
		    + this.cacheDir.getAbsolutePath() + "]."); //$NON-NLS-1$
	}
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

    /**
     * writeFrameToFS.
     * 
     * @param e
     *            <code>T</code>
     * @param file
     *            {@link File}
     * @return <code>T</code>
     */
    public boolean writeSegmentToFS(final S e, final File file) {
	FileOutputStream fos = null;
	try {
	    fos = new FileOutputStream(file);
	    fos.write(e.toBytes());
	    fos.flush();
	    return true;
	} catch (final Throwable e1) {
	    FileSystemFrameQueue.LOG.error(e1.getLocalizedMessage(), e1);
	} finally {
	    if (fos != null) {
		try {
		    fos.close();
		} catch (final IOException e1) {
		    FileSystemFrameQueue.LOG
			    .error(e1.getLocalizedMessage(), e1);
		}
	    }
	}
	return false;
    }

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================