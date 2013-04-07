/**
 * Class:	CountingInputStream<br/>
 * <br/>
 * Created:	30.03.2013<br/>
 * Filename: CountingInputStream.java<br/>
 * Version: $Revision: $<br/>
 * <br/>
 * @author Sebastian A. Weiß <a href="mailto:post@sebastian-weiss.de">post@sebastian-weiss.de</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * &copy; Sebastian A. Weiß, 2013 - All rights reserved.
 */
package de.wsdevel.tools.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * CountingInputStream
 */
public class CountingInputStream extends InputStream {

    /** {@link String} The PROPERTY_NAME_BYTES_READ. */
    public static final String PROPERTY_NAME_BYTES_READ = "bytesRead"; //$NON-NLS-1$

    /** {@link long} The bytesRead. */
    private long bytesRead;

    /** {@link InputStream} The innerIs. */
    private final InputStream innerIs;

    // /** {@link PropertyChangeSupport} The pcs. */
    // private final PropertyChangeSupport pcs;

    /**
     * CountingInputStream constructor.
     * 
     * @param innerIsRef
     *            {@link InputStream}
     */
    public CountingInputStream(final InputStream innerIsRef) {
	this.innerIs = innerIsRef;
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
     * available
     * 
     * @return
     * @throws IOException
     * @see java.io.InputStream#available()
     */
    @Override
    public int available() throws IOException {
	return this.innerIs.available();
    }

    /**
     * close
     * 
     * @throws IOException
     * @see java.io.InputStream#close()
     */
    @Override
    public void close() throws IOException {
	this.innerIs.close();
    }

    /**
     * Returns the bytesRead.
     * 
     * @return {@link long}
     */
    public long getBytesRead() {
	return this.bytesRead;
    }

    /**
     * mark
     * 
     * @param readlimit
     * @see java.io.InputStream#mark(int)
     */
    @Override
    public synchronized void mark(final int readlimit) {
	this.innerIs.mark(readlimit);
    }

    /**
     * markSupported
     * 
     * @return
     * @see java.io.InputStream#markSupported()
     */
    @Override
    public boolean markSupported() {
	return this.innerIs.markSupported();
    }

    /**
     * @see java.io.InputStream#read()
     * @return
     * @throws IOException
     */
    @Override
    public int read() throws IOException {
	final int read = this.innerIs.read();
	if (read > -1) {
	    setBytesRead(this.bytesRead + 1);
	}
	return read;
    }

    /**
     * @see java.io.InputStream#read(byte[])
     * @param b
     * @return
     * @throws IOException
     */
    @Override
    public int read(final byte[] b) throws IOException {
	final int read = this.innerIs.read(b);
	if (read > -1) {
	    setBytesRead(this.bytesRead + read);
	}
	return read;
    }

    /**
     * @see java.io.InputStream#read(byte[], int, int)
     * @param b
     * @param off
     * @param len
     * @return
     * @throws IOException
     */
    @Override
    public int read(final byte[] b, final int off, final int len)
	    throws IOException {
	final int read = this.innerIs.read(b, off, len);
	if (read > -1) {
	    setBytesRead(this.bytesRead + read);
	}
	return read;
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
     * reset
     * 
     * @throws IOException
     * @see java.io.InputStream#reset()
     */
    @Override
    public synchronized void reset() throws IOException {
	this.innerIs.reset();
    }

    /**
     * Sets the bytesRead.
     * 
     * @param bytesReadVal
     *            {@link long}
     */
    private void setBytesRead(final long bytesReadVal) {
	// final long oldValue = this.bytesRead;
	this.bytesRead = bytesReadVal;
	// this.pcs.firePropertyChange(
	// CountingInputStream.PROPERTY_NAME_BYTES_READ, oldValue,
	// this.bytesRead);
    }

    /**
     * skip
     * 
     * @param n
     * @return
     * @throws IOException
     * @see java.io.InputStream#skip(long)
     */
    @Override
    public long skip(final long n) throws IOException {
	return this.innerIs.skip(n);
    }

}

// ============[VERSION-CONTROL-LOG-START]==============
// -----------------------------------------------------
// $Log: $
// _____________________________________________________
// ============[VERSION-CONTROL-LOG-END]================