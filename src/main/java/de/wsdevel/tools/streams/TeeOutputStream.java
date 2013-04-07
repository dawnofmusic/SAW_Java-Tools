package de.wsdevel.tools.streams;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created on 26.04.2004.
 * 
 * for project: tools
 * 
 * (c) 2004-2013, Sebastian A. Weiss - All rights reserved.
 * 
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.2 $ -- $Date: 2005/10/31
 *          18:22:30 $
 */
public class TeeOutputStream extends OutputStream {

	/**
	 * {@link Log} COMMENT.
	 */
	private static final Log LOG = LogFactory.getLog(TeeOutputStream.class);

	/**
	 * {@link OutputStream} COMMENT.
	 */
	private OutputStream os1;

	/**
	 * {@link OutputStream} COMMENT.
	 */
	private OutputStream os2;

	/**
	 * COMMENT.
	 * 
	 * @param first
	 *            {@link OutputStream}
	 * @param second
	 *            {@link OutputStream}
	 */
	public TeeOutputStream(final OutputStream first, final OutputStream second) {
		setOs1(first);
		setOs2(second);
	}

	/**
	 * Closes both, chained and tee, streams.
	 */
	@Override
	public void close() throws IOException {
		flush();
		if (this.os1 != null) {
			this.os1.close();
			this.os1 = null;
		}
		if (this.os2 != null) {
			this.os2.close();
			this.os2 = null;
		}
	}

	/**
	 * Flushes chained stream; the tee stream is flushed each time a character
	 * is written to it.
	 */
	@Override
	public void flush() throws IOException {
		if (this.os1 != null) {
			this.os1.flush();
		}
		if (this.os2 != null) {
			this.os2.flush();
		}
	}

	/**
	 * @return {@link OutputStream} the os1.
	 */
	public final OutputStream getOs1() {
		return this.os1;
	}

	/**
	 * @return {@link OutputStream} the os2.
	 */
	public final OutputStream getOs2() {
		return this.os2;
	}

	/**
	 * @param os1Ref
	 *            {@link OutputStream} the os1 to set.
	 */
	public final void setOs1(final OutputStream os1Ref) {
		this.os1 = os1Ref;
	}

	/**
	 * @param os2Ref
	 *            {@link OutputStream} the os2 to set.
	 */
	public final void setOs2(final OutputStream os2Ref) {
		this.os2 = os2Ref;
	}

	/**
	 * @param b
	 *            <code>byte[]</code>
	 * @throws IOException
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(final byte[] b) throws IOException {
		write(b, 0, b.length);
	}

	/**
	 * @param b
	 *            <code>byte[]</code>
	 * @param off
	 *            <code>int</code>
	 * @param len
	 *            <code>int</code>
	 * @throws IOException
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(final byte[] b, final int off, final int len)
			throws IOException {
		if (this.os1 != null) {
			try {
				this.os1.write(b, off, len);
			} catch (final IOException ioe) {
				if (LOG.isDebugEnabled()) {
					TeeOutputStream.LOG.debug(ioe.getLocalizedMessage(), ioe);
				}
				this.os1 = null;
			}
		}
		if (this.os2 != null) {
			try {
				this.os2.write(b, off, len);
			} catch (final IOException ioe) {
				if (LOG.isDebugEnabled()) {
					TeeOutputStream.LOG.debug(ioe.getLocalizedMessage(), ioe);
				}
				this.os2 = null;
			}
		}
	}

	/**
	 * Implementation for parent's abstract write method. This writes out the
	 * passed in character to the both, the chained stream and "tee" stream.
	 */
	@Override
	public void write(final int c) throws IOException {
		if (this.os1 != null) {
			try {
				this.os1.write(c);
			} catch (final IOException ioe) {
				if (LOG.isDebugEnabled()) {
					TeeOutputStream.LOG.debug(ioe.getLocalizedMessage(), ioe);
				}
				this.os1 = null;
			}
		}
		if (this.os2 != null) {
			try {
				this.os2.write(c);
			} catch (final IOException ioe) {
				if (LOG.isDebugEnabled()) {
					TeeOutputStream.LOG.debug(ioe.getLocalizedMessage(), ioe);
				}
				this.os2 = null;
			}
		}
	}
}
//
// $Log: TeeOutputStream.java,v $
// Revision 1.4 2012-09-24 14:56:23 sweiss
// fixed bug increasing depth in OutputStreamTree
//
// Revision 1.3 2011-09-30 08:17:56 sweiss
// Fixed closing of sessions, also a lot of improvements conecerning dropped
// frames
//
// Revision 1.2 2011-09-29 07:34:27 sweiss
// cleaned up a lot!!!
//
// Revision 1.1 2011-09-28 17:40:53 sweiss
// first steps writing stream node configuration
//
// Revision 1.2 2011-09-28 12:35:52 sweiss
// several issues concerning stream piping
//
// Revision 1.1 2011-09-28 12:08:49 sweiss
// *** empty log message ***
//
// Revision 1.2 2006-06-10 13:00:33 sweissTFH
// cleanup and smaller changes due to new compiler settings
//
// Revision 1.1 2006/05/02 16:06:01 sweissTFH cleaned up tools and moved
// everything to appropriate new packages
//
// Revision 1.4 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
//
// Revision 1.3 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean
// up!
//
// Revision 1.2 2005/10/31 18:22:30 sweissTFH clean up and commenting
//
