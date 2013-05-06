/**
 * Class:	Buffer<br/>
 * <br/>
 * Created:	02.05.2013<br/>
 * Filename: Buffer.java<br/>
 * Version: $Revision: $<br/>
 * <br/>
 * @author Sebastian A. Weiß <a href="mailto:post@sebastian-weiss.de">post@sebastian-weiss.de</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * &copy; Sebastian A. Weiß, 2013 - All rights reserved.
 */
package de.wsdevel.tools.streams.buffer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Buffer
 */
public abstract class Buffer {

    /** {@link String} The PROPERTY_NAME_MAXIMUM_BUFFER_SIZE. */
    private static final String PROPERTY_NAME_MAXIMUM_BUFFER_SIZE = "maximumBufferSize";

    /** {@link String} The PROPERTY_NAME_STATE. */
    public static final String PROPERTY_NAME_STATE = "state"; //$NON-NLS-1$

    /** {@link BufferState} The state. */
    private BufferState state = BufferState.filling;

    /** {@link PropertyChangeSupport} The pcs. */
    protected final PropertyChangeSupport pcs;

    /**
     * bufferSize.
     * 
     * @return <code>int</code>
     */
    private long maximumBufferSize = -1;

    /** {@link Timer} The stateCheckTimer. */
    private static Timer stateCheckTimer = new Timer(
	    "Circular-Buffer-State-Checker"); //$NON-NLS-1$

    /** {@link Log} The LOG. */
    private static final Log LOG = LogFactory.getLog(Buffer.class);

    /** {@link int} The preFill. */
    private int preFillTreshold;

    /** {@link int} The _50PercentTreshold. */
    private int _50PercentTreshold;

    /** {@link int} The preFill. */
    private int readingTreshold;

    /** {@link BufferBehavior} The bevavior. */
    private BufferBehavior bevavior;

    /**
     * Buffer constructor.
     * 
     * @param maximumBufferSizeVal
     *            {@code long}
     */
    public Buffer(final long maximumBufferSizeVal) {
	this.pcs = new PropertyChangeSupport(this);
	setMaximumBufferSize(maximumBufferSizeVal);

	Buffer.stateCheckTimer.schedule(new TimerTask() {
	    @Override
	    public void run() {
		checkStatus();
	    }
	}, 0, 200);
    }

    /**
     * addPropertyChangeListener
     * 
     * @param listener
     * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
	this.pcs.addPropertyChangeListener(listener);
    }

    /**
     * addPropertyChangeListener
     * 
     * @param propertyName
     * @param listener
     * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(final String propertyName,
	    final PropertyChangeListener listener) {
	this.pcs.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * blockReadAccess.
     */
    public abstract void blockReadAccess();

    /**
     * blockWriteAccess.
     */
    public abstract void blockWriteAccess();

    /**
     * checkStatus.
     */
    protected void checkStatus() {
	switch (getState()) {
	case filling:
	    if (getCurrentBytes() > this.preFillTreshold) {
		unblockReadAccess();
		setState(BufferState.reading);
		Buffer.LOG.info("Unblocked Access. Delta was [" //$NON-NLS-1$
			+ getCurrentBytes() + "]."); //$NON-NLS-1$
	    }
	    break;
	case reading:
	default:
	    if (getCurrentBytes() > this.preFillTreshold) {
		// if (getBevavior() != BufferBehavior.fast) {
		// blockWriteAccess();
		// }
	    } else if (getCurrentBytes() < this.readingTreshold) {
		if (getBevavior() != BufferBehavior.fastAccessRingBuffer) {
		    blockReadAccess();
		}
		setState(BufferState.filling);
		Buffer.LOG.info("Blocked Access. Delta was [" //$NON-NLS-1$
			+ getCurrentBytes() + "]."); //$NON-NLS-1$
	    }
	    if (getCurrentBytes() < this._50PercentTreshold) {
		// unblock write access if necessary;
		unblockWriteAccess();
	    }
	    break;
	}
    }

    /**
     * Returns the _50PercentTreshold.
     * 
     * @return {@link int}
     */
    public int get_50PercentTreshold() {
	return this._50PercentTreshold;
    }

    /**
     * Returns the bevavior.
     * 
     * @return {@link BufferBehavior}
     */
    public BufferBehavior getBevavior() {
	return this.bevavior;
    }

    /**
     * getCurrentBytes.
     * 
     * @return {@code long} the current number of bytes used by this buffer;
     */
    public abstract long getCurrentBytes();

    /**
     * Returns the maximumBufferSize.
     * 
     * @return {@link long}
     */
    public long getMaximumBufferSize() {
	return this.maximumBufferSize;
    }

    /**
     * Returns the preFillTreshold.
     * 
     * @return {@link int}
     */
    public int getPreFillTreshold() {
	return this.preFillTreshold;
    }

    /**
     * Returns the readingTreshold.
     * 
     * @return {@link int}
     */
    public int getReadingTreshold() {
	return this.readingTreshold;
    }

    /**
     * Returns the state.
     * 
     * @return {@link BufferState}
     */
    public BufferState getState() {
	return this.state;
    }

    /**
     * removePropertyChangeListener
     * 
     * @param listener
     * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(
	    final PropertyChangeListener listener) {
	this.pcs.removePropertyChangeListener(listener);
    }

    /**
     * removePropertyChangeListener
     * 
     * @param propertyName
     * @param listener
     * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(final String propertyName,
	    final PropertyChangeListener listener) {
	this.pcs.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Sets the bevavior.
     * 
     * @param bevaviorVal
     *            {@link BufferBehavior}
     */
    public void setBevavior(final BufferBehavior bevaviorVal) {
	if (bevaviorVal == BufferBehavior.fastAccessRingBuffer) {
	    unblockReadAccess();
	    unblockWriteAccess();
	}
	this.bevavior = bevaviorVal;
    }

    /**
     * Sets the maximumBufferSize.
     * 
     * @param maximumBufferSize
     *            {@link long}
     */
    public void setMaximumBufferSize(final long maximumBufferSize) {
	final long oldValue = this.maximumBufferSize;
	this.maximumBufferSize = maximumBufferSize;
	this.preFillTreshold = Math.round(this.maximumBufferSize * 0.8f);
	this.readingTreshold = Math.round(this.maximumBufferSize * 0.3f);
	this._50PercentTreshold = Math.round(this.maximumBufferSize * 0.5f);
	this.pcs.firePropertyChange(Buffer.PROPERTY_NAME_MAXIMUM_BUFFER_SIZE,
		oldValue, this.maximumBufferSize);
    }

    /**
     * Sets the state.
     * 
     * @param stateRef
     *            {@link BufferState}
     */
    public void setState(final BufferState stateRef) {
	final BufferState oldValue = this.state;
	this.state = stateRef;
	this.pcs.firePropertyChange(Buffer.PROPERTY_NAME_STATE, oldValue,
		this.state);
    }

    /**
     * unblockReadAccess.
     */
    public abstract void unblockReadAccess();

    /**
     * unblockWriteAccess.
     */
    public abstract void unblockWriteAccess();

}
// ============[VERSION-CONTROL-LOG-START]==============
// -----------------------------------------------------
// $Log: $
// _____________________________________________________
// ============[VERSION-CONTROL-LOG-END]================