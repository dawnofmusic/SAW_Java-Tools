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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.wsdevel.tools.math.Graph;
import de.wsdevel.tools.math.ValueTuple;

/**
 * Buffer
 */
public abstract class Buffer {

    /** {@link Log} The LOG. */
    private static final Log LOG = LogFactory.getLog(Buffer.class);

    /** {@link String} The PROPERTY_NAME_MAXIMUM_BUFFER_SIZE. */
    public static final String PROPERTY_NAME_MAXIMUM_BUFFER_SIZE = "maximumBufferSize";

    /** {@link String} The PROPERTY_NAME_STATE. */
    public static final String PROPERTY_NAME_STATE = "state"; //$NON-NLS-1$

    /** {@link int} The _50PercentTreshold. */
    private int _50PercentTreshold;

    /** {@link BufferBehavior} The bevavior. */
    private BufferBehavior bevavior;

    /**
     * {@link Graph} fillingLevelHistory
     */
    private Graph fillingLevelHistory = null;

    /**
     * {@link Object} fillingLevelLock
     */
    private final Object fillingLevelLock = new Object();

    /**
     * bufferSize.
     * 
     * @return <code>int</code>
     */
    private long maximumNumberOfElements = -1;

    /** {@link PropertyChangeSupport} The pcs. */
    protected final PropertyChangeSupport pcs;

    /** {@link int} The preFill. */
    private int preFillTreshold;

    /** {@link int} The preFill. */
    private int readingTreshold;

    /** {@link BufferState} The state. */
    private BufferState state = BufferState.filling;

    /**
     * @return the {@link boolean} keepFillingLevelHistory
     */
    public boolean isKeepFillingLevelHistory() {
	return this.fillingLevelHistory != null;
    }

    /**
     * @param keepFillingLevelHistoryVal
     *            {@link boolean} the keepFillingLevelHistory to set
     */
    public void setKeepFillingLevelHistory(boolean keepFillingLevelHistoryVal) {
	final boolean oldValue = isKeepFillingLevelHistory();
	if (oldValue != keepFillingLevelHistoryVal) {
	    if (keepFillingLevelHistoryVal) {
		initFillingLevelHistory();
	    } else {
		this.fillingLevelHistory = null;
	    }
	    this.pcs.firePropertyChange("keepFillingLevelHistory", oldValue,
		    keepFillingLevelHistoryVal);
	}
    }

    /**
     * Buffer constructor.
     * 
     * @param maximumNumberOfElements
     *            {@code long}
     */
    public Buffer(final long maximumNumberOfElements,
	    final boolean keepFillingLevelHistoryVal) {
	this.pcs = new PropertyChangeSupport(this);
	setMaximumNumberOfElements(maximumNumberOfElements);
	setKeepFillingLevelHistory(keepFillingLevelHistoryVal);
    }

    /**
     * Buffer constructor.
     * 
     * @param maximumNumberOfElementsVal
     *            <code>long</code>
     */
    public Buffer(final long maximumNumberOfElementsVal) {
	this(maximumNumberOfElementsVal, false);
    }

    /**
     * addPropertyChangeListener
     * 
     * @param listener
     *            {@link PropertyChangeListener}
     * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
	this.pcs.addPropertyChangeListener(listener);
    }

    /**
     * addPropertyChangeListener
     * 
     * @param propertyName
     *            {@link String}
     * @param listener
     *            {@link PropertyChangeListener}
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
     * checkFillingLevel.
     */
    private void checkFillingLevel() {
	if (this.fillingLevelHistory != null) {
	    final ValueTuple tuple = new ValueTuple(
		    (System.currentTimeMillis() / 1000d),
		    // (System.currentTimeMillis() - this.startMillis) / 1000d,
		    // (100 * this.bufferFillingLevel)
		    // / (double) getMaximumBufferSize());
		    (double) getCurrentNumberOfElements());
	    this.fillingLevelHistory.addTuple(tuple);
	}
    }

    /**
     * checkStatus.
     */
    protected void checkStatus() {
	switch (getState()) {
	case filling:
	    if (getCurrentNumberOfElements() > this.preFillTreshold) {
		unblockReadAccess();
		setState(BufferState.reading);
		if (Buffer.LOG.isInfoEnabled()) {
		    Buffer.LOG.info("Unblocked Access. Delta was [" //$NON-NLS-1$
			    + getCurrentNumberOfElements() + "]."); //$NON-NLS-1$
		}
	    }
	    break;
	case reading:
	default:
	    // if (getCurrentBytes() > this.preFillTreshold) {
	    // // if (getBevavior() != BufferBehavior.fast) {
	    // // blockWriteAccess();
	    // // }
	    // } else
	    if (getCurrentNumberOfElements() < this.readingTreshold) {
		if (getBevavior() != BufferBehavior.fastAccessRingBuffer) {
		    blockReadAccess();
		    setState(BufferState.filling);
		    if (Buffer.LOG.isInfoEnabled()) {
			Buffer.LOG.info("Blocked Access. Delta was [" //$NON-NLS-1$
				+ getCurrentNumberOfElements() + "]."); //$NON-NLS-1$
		    }
		}
	    }
	    // if (getCurrentBytes() < this._50PercentTreshold) {
	    // unblock write access if necessary;
	    // unblockWriteAccess();
	    // }
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
     * getCurrentNumberOfElements.
     * 
     * @return {@code long} the current number of elements in this buffer;
     */
    public abstract long getCurrentNumberOfElements();

    /**
     * @return the {@link Graph} fillingLevelHistory
     */
    public Graph getFillingLevelHistory() {
	return this.fillingLevelHistory;
    }

    /**
     * Returns the maximumBufferSize.
     * 
     * @return {@link long}
     */
    public long getMaximumNumberOfElements() {
	return this.maximumNumberOfElements;
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
     * initFillingLevelHistory.
     */
    private void initFillingLevelHistory() {
	this.fillingLevelHistory = new Graph();
	this.fillingLevelHistory.setMaxNumberOfValues(300);
    }

    /**
     * removePropertyChangeListener
     * 
     * @param listener
     *            {@link PropertyChangeListener}
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
     *            {@link String}
     * @param listener
     *            {@link PropertyChangeListener}
     * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(final String propertyName,
	    final PropertyChangeListener listener) {
	this.pcs.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * resetFillingLevel.
     */
    protected void resetFillingLevel() {
	synchronized (this.fillingLevelLock) {
	    initFillingLevelHistory();
	}
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
    public void setMaximumNumberOfElements(final long maximumBufferSize) {
	if (maximumBufferSize < 1) {
	    throw new IllegalArgumentException(
		    "maximumBufferSize MUST be bigger than 0!");
	}
	final long oldValue = this.maximumNumberOfElements;
	this.maximumNumberOfElements = maximumBufferSize;
	this.preFillTreshold = Math.round(this.maximumNumberOfElements * 0.7f);
	this.readingTreshold = Math.round(this.maximumNumberOfElements * 0.2f);
	this._50PercentTreshold = Math
		.round(this.maximumNumberOfElements * 0.5f);
	this.pcs.firePropertyChange(Buffer.PROPERTY_NAME_MAXIMUM_BUFFER_SIZE,
		oldValue, this.maximumNumberOfElements);
    }

    /**
     * Sets the state.
     * 
     * @param stateRef
     *            {@link BufferState}
     */
    public void setState(final BufferState stateRef) {
	final BufferState oldValue = this.state;
	if (stateRef != oldValue) {
	    this.state = stateRef;
	    this.pcs.firePropertyChange(Buffer.PROPERTY_NAME_STATE, oldValue,
		    this.state);
	}
    }

    /**
     * unblockReadAccess.
     */
    public abstract void unblockReadAccess();

    /**
     * unblockWriteAccess.
     */
    public abstract void unblockWriteAccess();

    /**
     * updateFillingLevelHistory.
     * 
     * @param deltaFillingLevel
     *            <code>long</code>
     */
    protected void updateFillingLevelHistory() {
	synchronized (this.fillingLevelLock) {
	    checkFillingLevel();
	    checkStatus();
	}
    }

}
// ============[VERSION-CONTROL-LOG-START]==============
// -----------------------------------------------------
// $Log: $
// _____________________________________________________
// ============[VERSION-CONTROL-LOG-END]================