/**
 * Class:	BlockableOutputStream<br/>
 * <br/>
 * Created:	30.03.2013<br/>
 * Filename: BlockableOutputStream.java<br/>
 * Version: $Revision: $<br/>
 * <br/>
 * @author Sebastian A. Weiß <a href="mailto:post@sebastian-weiss.de">post@sebastian-weiss.de</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * &copy; Sebastian A. Weiß, 2013 - All rights reserved.
 */
package de.wsdevel.tools.streams.blocking;

import java.io.IOException;
import java.io.OutputStream;

/**
 * BlockableOutputStream
 */
public class BlockableOutputStream extends OutputStream {

    /** {@link OutputStream} The innerOS. */
    private final OutputStream innerOs;

    /** {@link boolean} The blocked. */
    private boolean blocked;

    /**
     * BlockableInputStream constructor.
     * 
     * @param innerOsRef
     *            {@link OutputStream}
     */
    public BlockableOutputStream(final OutputStream innerOsRef) {
	this.innerOs = innerOsRef;
    }

    /**
     * close
     * 
     * @throws IOException
     * @see java.io.InputStream#close()
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
	waitUntilUnblocked();
	this.innerOs.flush();
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
     * setBlocked.
     * 
     * @param blockedVal
     */
    public void setBlocked(final boolean blockedVal) {
	this.blocked = blockedVal;
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

    /**
     * write
     * 
     * @param b
     * @throws IOException
     * @see java.io.OutputStream#write(byte[])
     */
    @Override
    public void write(final byte[] b) throws IOException {
	waitUntilUnblocked();
	this.innerOs.write(b);
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
	waitUntilUnblocked();
	this.innerOs.write(b, off, len);
    }

    /**
     * write
     * 
     * @param b
     *            <code>int</code>
     * @throws IOException
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write(final int b) throws IOException {
	waitUntilUnblocked();
	this.innerOs.write(b);
    }

}

// ============[VERSION-CONTROL-LOG-START]==============
// -----------------------------------------------------
// $Log: $
// _____________________________________________________
// ============[VERSION-CONTROL-LOG-END]================