/**
 * Class:    FileSystemSegmentCache<br/>
 * <br/>
 * Created:  22.05.2013<br/>
 * Filename: FileSystemSegmentCache.java<br/>
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
import java.text.ParseException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.wsdevel.tools.streams.Streams;
import de.wsdevel.tools.streams.container.Frame;
import de.wsdevel.tools.streams.container.Segment;

/**
 * FileSystemSegmentCache
 * 
 */
public class FileSystemSegmentCache<F extends Frame, S extends Segment<F>>
	extends SegmentCache<F, S> {

    private static final int MIN_LENGTH_FILENAME_SPLIT = 4;

    /**
     * {@link String} MINUS
     */
    private static final String MINUS = "-";

    /**
     * {@link int} DEFAULT_COPY_BUFFER_SIZE
     */
    private static final int DEFAULT_COPY_BUFFER_SIZE = 256 * 1024;

    /** {@link Log} LOG */
    private static final Log LOG = LogFactory
	    .getLog(FileSystemSegmentCache.class);

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
     * @param cacheDir
     *            {@link String}
     * @return {@link File}
     */
    private static File createChunkFileForChunkURI(final String chunkURI,
	    final File cacheDir) {
	return new File(cacheDir.getAbsolutePath()
		+ File.separator
		+ chunkURI.replaceAll(
			FileSystemSegmentCache.URL_BAD_CHAR_REGEXP,
			FileSystemSegmentCache.URL_BAD_CHAR_REPLACEMENT));
    }

    /**
     * {@link File} cacheDir
     */
    private File cacheDir;

    /**
     * {@link HashMap<Long,File>} timestampRegistry
     */
    final HashMap<Long, File> timestampRegistry = new HashMap<Long, File>();

    /**
     * {@link HashMap<Long,File>} sequenceNumberRegistry
     */
    final HashMap<Integer, File> sequenceNumberRegistry = new HashMap<Integer, File>();

    /**
     * FileSystemFrameQueue constructor.
     * 
     * @param deserializerRef
     */
    public FileSystemSegmentCache(final Deserializer<S> deserializerRef) {
	this("UNKNOWN", deserializerRef); //$NON-NLS-1$
    }

    /**
     * FileSystemFrameQueue constructor.
     * 
     * @param nameVal
     * @param deserializerRef
     */
    public FileSystemSegmentCache(final String nameVal,
	    final Deserializer<S> deserializerRef) {
	setName(nameVal);
	setDeserializer(deserializerRef);
    }

    /**
     * createFilenameForChunk.
     * 
     * @param e
     * @param timestamp
     * @return
     */
    private String createFilenameForChunk(final S e, final long timestamp) {
	return Long.toString(timestamp) + FileSystemSegmentCache.MINUS
		+ Integer.toString(e.getSequenceNumber())
		+ FileSystemSegmentCache.MINUS
		+ Long.toString(e.getDurationNanos())
		+ FileSystemSegmentCache.MINUS + getName();
    }

    /**
     * updateSegmentFromFilename.
     * 
     * @param e
     * @param filename
     */
    private void updateSegmentFromFilename(final S e, final String filename) {
	final String[] split = filename.split(FileSystemSegmentCache.MINUS);
	if (split.length >= MIN_LENGTH_FILENAME_SPLIT) {
	    e.setSequenceNumber(Integer.parseInt(split[1]));
	    e.setDurationNanos(Long.parseLong(split[2]));
	}
    }

    /**
     * getSequenceNumberFromFilename.
     * 
     * @param filename
     * @return
     * @throws ParseException
     */
    private int getSequenceNumberFromFilename(final String filename)
	    throws ParseException {
	final String[] split = filename.split(FileSystemSegmentCache.MINUS);
	if (split.length >= MIN_LENGTH_FILENAME_SPLIT) {
	    return Integer.parseInt(split[1]);
	}
	throw new ParseException("Invalid filename [" + filename + "]", -1);
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
			FileSystemSegmentCache.DEFAULT_COPY_BUFFER_SIZE);
		final S deserialize = getDeserializer().deserialize(
			baos.toByteArray());
		deserialize.setId(file.getName());
		updateSegmentFromFilename(deserialize, file.getName());
		return deserialize;
	    } catch (final FileNotFoundException e) {
		FileSystemSegmentCache.LOG.error(e.getLocalizedMessage(), e);
	    }
	}
	return null;
    }

    /**
     * offer.
     * 
     * @see java.util.Queue#offer(java.lang.Object)
     * @param e
     *            <code>S</code>
     * @return <code>long</code>
     */
    public boolean offer(final S e, final long timestamp) {
	final File file = createChunkFileForChunkURI(
		createFilenameForChunk(e, timestamp), this.cacheDir);
	// if (e.getDurationNanos() < 0) {
	// System.out.println("chunk with negative duration: "
	// + file.getAbsolutePath());
	// }
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
	if (writeSegmentToFS(e, file)) {
	    setLastOfferedTimestamp(timestamp);
	    this.timestampRegistry.put(timestamp, file);
	    this.sequenceNumberRegistry.put(e.getSequenceNumber(), file);
	    this.timestamps.add(timestamp);
	    switch (getBehaviour()) {
	    case maxSizeFIFOQueue:
		final Long peek = this.timestamps.peek();
		if ((peek + getMaxDurationInMillis()) < System
			.currentTimeMillis()) {
		    final File remove = this.timestampRegistry
			    .remove(this.timestamps.poll());
		    if (remove != null) {
			// 20130610 BUGFIX checking whether remove is null
			// or not.
			try {
			    final int sequenceNumberFromFilename = getSequenceNumberFromFilename(remove
				    .getName());
			    this.sequenceNumberRegistry
				    .remove(sequenceNumberFromFilename);
			} catch (ParseException e1) {
			    LOG.error(e1.getLocalizedMessage(), e1);
			}
			if (!remove.delete()) {
			    FileSystemSegmentCache.LOG
				    .error("Could not delete [" + remove.getAbsolutePath() + "]"); //$NON-NLS-1$//$NON-NLS-2$
			}
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
     * @param nameVal
     *            {@link String} the name to set
     */
    public void setName(final String nameVal) {
	super.setName(nameVal);
	this.cacheDir = new File(
		FileSystemSegmentCache.SOURCE_CACHE_ROOT_DIRECTORY
			+ File.separator + getName());
	if (this.cacheDir.exists()) {
	    final File[] listFiles = this.cacheDir.listFiles();
	    for (final File file : listFiles) {
		// clean cache directory (20130419 saw)
		// 20130610 BUGFIX cache dir shouldn't be cleaned up since old
		// chunks
		// will be removed by the queue
		// file.delete();
		final String[] split = file.getName().split(
			FileSystemSegmentCache.MINUS);
		if (split.length >= MIN_LENGTH_FILENAME_SPLIT) {
		    try {
			// 20130610 add timestamp to queue
			final long parseLong = Long.parseLong(split[0]);
			this.timestamps.add(parseLong);
		    } catch (NumberFormatException nfe) {
			LOG.error(nfe.getLocalizedMessage(), nfe);
		    }
		}
	    }
	}
	if (!this.cacheDir.exists() && !this.cacheDir.mkdirs()) {
	    FileSystemSegmentCache.LOG.error("Could not create dir [" //$NON-NLS-1$
		    + this.cacheDir.getAbsolutePath() + "]."); //$NON-NLS-1$
	}
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
	    FileSystemSegmentCache.LOG.error(e1.getLocalizedMessage(), e1);
	} finally {
	    if (fos != null) {
		try {
		    fos.close();
		} catch (final IOException e1) {
		    FileSystemSegmentCache.LOG.error(e1.getLocalizedMessage(),
			    e1);
		}
	    }
	}
	return false;
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
    public S getSegmentForTimestamp(Long timestamp) {
	return getSFromFile(this.timestampRegistry.get(timestamp));
    }

    /**
     * getSegmentForSequenceNumber.
     * 
     * @see de.wsdevel.tools.streams.buffer.SegmentCache#getSegmentForSequenceNumber(int)
     * @param sequenceNumber
     * @return
     */
    @Override
    public S getSegmentForSequenceNumber(int sequenceNumber) {
	return getSFromFile(this.sequenceNumberRegistry.get(sequenceNumber));
    }

}

// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: $
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================