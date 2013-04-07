/**
 * Class:	BlockableInputStream<br/>
 * <br/>
 * Created:	30.03.2013<br/>
 * Filename: BlockableInputStream.java<br/>
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
 * BlockableInputStream
 */
public class BlockableInputStream extends InputStream {

    /** {@link InputStream} The innerIs. */
    private final InputStream innerIs;

    /** {@link boolean} The blocked. */
    private boolean blocked;

    /**
     * BlockableInputStream constructor.
     * 
     * @param innerIsRef
     *            {@link InputStream}
     */
    public BlockableInputStream(final InputStream innerIsRef) {
	this.innerIs = innerIsRef;
    }

    /**
     * @see java.io.InputStream#available()
     * @return
     * @throws IOException
     */
    @Override
    public int available() throws IOException {
	if (!isBlocked()) {
	    return this.innerIs.available();
	}
	return 0;
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
     * isBlocked.
     * 
     * @return
     */
    public boolean isBlocked() {
	return this.blocked;
    }

    /**
     * mark
     * 
     * @param readlimit
     * @see java.io.InputStream#mark(int)
     */
    @Override
    public synchronized void mark(final int readlimit) {
	throw new UnsupportedOperationException();
    }

    /**
     * markSupported
     * 
     * @return
     * @see java.io.InputStream#markSupported()
     */
    @Override
    public boolean markSupported() {
	return false;
    }

    /**
     * @see java.io.InputStream#read()
     * @return
     * @throws IOException
     */
    @Override
    public int read() throws IOException {
	waitUntilUnblocked();
	return this.innerIs.read();
    }

    /**
     * @see java.io.InputStream#read(byte[])
     * @param b
     * @return
     * @throws IOException
     */
    @Override
    public int read(final byte[] b) throws IOException {
	waitUntilUnblocked();
	return this.innerIs.read(b);
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
	waitUntilUnblocked();
	return this.innerIs.read(b, off, len);
    }

    /**
     * reset
     * 
     * @throws IOException
     * @see java.io.InputStream#reset()
     */
    @Override
    public synchronized void reset() throws IOException {
	throw new UnsupportedOperationException();
    }

    /**
     * setBlocked.
     * 
     * @param blockedVal
     */
    public void setBlocked(final boolean blockedVal) {
	this.blocked = blockedVal;
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
	throw new UnsupportedOperationException();
    }

    /**
     * waitUntilUnblocked.
     */
    private void waitUntilUnblocked() {
	while (isBlocked()) {
	    try {
		Thread.sleep(100);
	    } catch (final InterruptedException e) {
		// don't care
	    }
	}
    }

}

// ============[VERSION-CONTROL-LOG-START]==============
// -----------------------------------------------------
// $Log: $
// _____________________________________________________
// ============[VERSION-CONTROL-LOG-END]================