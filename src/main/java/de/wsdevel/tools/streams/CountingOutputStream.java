/**
 * Class:	CountingOutputStream<br/>
 * <br/>
 * Created:	30.03.2013<br/>
 * Filename: CountingOutputStream.java<br/>
 * Version: $Revision: $<br/>
 * <br/>
 * @author Sebastian A. Weiß <a href="mailto:post@sebastian-weiss.de">post@sebastian-weiss.de</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * &copy; Sebastian A. Weiß, 2013 - All rights reserved.
 */
package de.wsdevel.tools.streams;

import java.io.IOException;
import java.io.OutputStream;

/**
 * CountingOutputStream
 */
public class CountingOutputStream extends OutputStream {

    // /** {@link String} The PROPERTY_NAME_BYTES_WRITTEN. */
    //    public static final String PROPERTY_NAME_BYTES_WRITTEN = "bytesWritten"; //$NON-NLS-1$

    /** {@link OutputStream} The innerOs. */
    private final OutputStream innerOs;

    /** {@link long} The bytesWritten. */
    private long bytesWritten;

    // /** {@link PropertyChangeSupport} The pcs. */
    // private final PropertyChangeSupport pcs;

    /**
     * CountingOutputStream constructor.
     * 
     * @param innerOsRef
     */
    public CountingOutputStream(final OutputStream innerOsRef) {
	this.innerOs = innerOsRef;
	// this.pcs = new PropertyChangeSupport(this);
    }

    // /**
    // * addPropertyChangeListener
    // *
    // * @param listener
    // * @see
    // java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
    // */
    // public void addPropertyChangeListener(final PropertyChangeListener
    // listener) {
    // this.pcs.addPropertyChangeListener(listener);
    // }
    //
    // /**
    // * addPropertyChangeListener
    // *
    // * @param propertyName
    // * @param listener
    // * @see
    // java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String,
    // * java.beans.PropertyChangeListener)
    // */
    // public void addPropertyChangeListener(final String propertyName,
    // final PropertyChangeListener listener) {
    // this.pcs.addPropertyChangeListener(propertyName, listener);
    // }

    /**
     * close
     * 
     * @throws IOException
     * @see java.io.OutputStream#close()
     */
    @Override
    public void close() throws IOException {
	this.innerOs.close();
    }

    /**
     * flush
     * 
     * @throws IOException
     * @see java.io.OutputStream#flush()
     */
    @Override
    public void flush() throws IOException {
	this.innerOs.flush();
    }

    /**
     * Returns the bytesWritten.
     * 
     * @return {@link long}
     */
    public long getBytesWritten() {
	return this.bytesWritten;
    }

    // /**
    // * removePropertyChangeListener
    // *
    // * @param listener
    // * @see
    // java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
    // */
    // public void removePropertyChangeListener(
    // final PropertyChangeListener listener) {
    // this.pcs.removePropertyChangeListener(listener);
    // }
    //
    // /**
    // * removePropertyChangeListener
    // *
    // * @param propertyName
    // * @param listener
    // * @see
    // java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String,
    // * java.beans.PropertyChangeListener)
    // */
    // public void removePropertyChangeListener(final String propertyName,
    // final PropertyChangeListener listener) {
    // this.pcs.removePropertyChangeListener(propertyName, listener);
    // }

    /**
     * Sets the bytesWritten.
     * 
     * @param bytesWrittenVal
     *            {@link long}
     */
    private void setBytesWritten(final long bytesWrittenVal) {
	// final long oldValue = this.bytesWritten;
	this.bytesWritten = bytesWrittenVal;
	// this.pcs.firePropertyChange(
	// CountingOutputStream.PROPERTY_NAME_BYTES_WRITTEN, oldValue,
	// this.bytesWritten);
    }

    /**
     * write
     * 
     * @param b
     * @throws IOException
     * @see java.io.OutputStream#write(byte[])
     */
    @Override
    public void write(final byte[] b) throws IOException {
	this.innerOs.write(b);
	setBytesWritten(this.bytesWritten + b.length);
    }

    /**
     * write
     * 
     * @param b
     * @param off
     * @param len
     * @throws IOException
     * @see java.io.OutputStream#write(byte[], int, int)
     */
    @Override
    public void write(final byte[] b, final int off, final int len)
	    throws IOException {
	this.innerOs.write(b, off, len);
	setBytesWritten(this.bytesWritten + len);
    }

    /**
     * write
     * 
     * @param b
     * @throws IOException
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write(final int b) throws IOException {
	this.innerOs.write(b);
	setBytesWritten(this.bytesWritten + 1);
    }

}

// ============[VERSION-CONTROL-LOG-START]==============
// -----------------------------------------------------
// $Log: $
// _____________________________________________________
// ============[VERSION-CONTROL-LOG-END]================