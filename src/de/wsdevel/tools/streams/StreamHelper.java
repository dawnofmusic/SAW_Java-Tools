package de.wsdevel.tools.streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created on 08.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.9 $ -- $Date: 2008-05-27 12:46:10
 *          $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class StreamHelper {

	/**
	 * <code>int</code>.
	 */
	private static final int BUFFER_SIZE = 1024;

	/**
	 * logger.
	 */
	private static final Log LOG = LogFactory.getLog(StreamHelper.class);

	/**
	 * {@link OutputStream} COMMENT.
	 */
	public static final OutputStream NOWHERE_OUTPUT_STREAM = new OutputStream() {
		@Override
		public void write(final int b) throws IOException {
			// do nothing (sw)
		}
	};

	/**
	 * Hidden constructor.
	 */
	private StreamHelper() {
	}

	/**
	 * @param inputStream
	 *            {@link InputStream}
	 * @return String
	 */
	public static String readStringFromStream(final InputStream inputStream) {
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Content: ");
			}
			String line = "";
			while (true) {
				line = reader.readLine();
				if (line != null) {
					if (buffer.length() > 0) {
						buffer.append("\n");
					}
					buffer.append(line);
					if (LOG.isDebugEnabled()) {
						LOG.debug(line);
					}
				} else {
					break;
				}
			}
		} catch (IOException e) {
			LOG.error("could not extract content from URLConnection: ", e);
		}
		return buffer.toString();
	}

	/**
	 * reads from in and writes to out. both streams will be closed after
	 * processing!
	 * 
	 * @param in
	 *            {@link InputStream}
	 * @param closeIn
	 *            <code>boolean</code>
	 * @param out
	 *            {@link OutputStream}
	 * @param closeOut
	 *            <code>boolean</code>
	 * @throws IOException
	 *             if an error occurs
	 */
	public static void readFromInWriteToOut(final InputStream in,
			final boolean closeIn, final OutputStream out,
			final boolean closeOut) throws IOException {

		try {
			// Transfer bytes from in to out
			byte[] buf = new byte[BUFFER_SIZE];
			int len;
			while ((len = in.read(buf)) > -1) {
				out.write(buf, 0, len);
			}
		} finally {
			if (closeIn) {
				in.close();
			}
			if (closeOut) {
				out.close();
			}
		}
	}

	/**
	 * @param is
	 *            {@link InputStream} to read from.
	 * @param toBeFilled
	 *            <code>byte[]</code> to be filled.
	 * @throws IOException
	 *             {@link IOException} due to errors.
	 */
	public static void fillByteArrayFromStream(final InputStream is,
			final byte[] toBeFilled) throws IOException {
		int bytesRead = 0;
		if (LOG.isDebugEnabled()) {
			LOG.debug("Going to read " + toBeFilled.length
					+ " bytes from stream...");
		}
		while (bytesRead < toBeFilled.length) {
			int read = is.read(toBeFilled, bytesRead, toBeFilled.length
					- bytesRead);
			if (read == -1) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("End of stream reached.");
				}
				break;
			}
			// if (LOG.isDebugEnabled()) {
			// LOG.debug("Read " + read + " bytes from stream.");
			// }
			bytesRead += read;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Read " + bytesRead + " bytes from stream in sum.");
		}
	}

}
//
// $Log: StreamHelper.java,v $
// Revision 1.9  2009-02-20 10:16:19  sweiss
// bugfixing
//
// Revision 1.8 2008-05-27 12:46:10 sweiss
// fixes and logging
//
// Revision 1.7 2008-05-20 17:38:08 sweiss
// *** empty log message ***
//
// Revision 1.6 2007-09-11 16:34:01 ischmidt
// added try/finally block in readFromInWriteToOut
// 
// Revision 1.5 2007-08-06 17:33:39 ischmidt
// switched to commmons logging
// 
// Revision 1.4 2007-05-14 13:36:31 sweiss
// *** empty log message ***
// 
// Revision 1.3 2006/12/19 13:22:27 sweissTFH
// clean up
// 
// Revision 1.2 2006/05/26 22:09:37 sweissTFH
// small cleanup
// 
// Revision 1.1 2006/05/02 16:06:01 sweissTFH
// cleaned up tools and moved
// everything to appropriate new packages
// 
// Revision 1.6 2006/04/05 18:19:34 sweissTFH
// cleaned up checkstyle errors
// 
// Revision 1.5 2005/12/27 16:06:01 sweissTFH
// moved to java 5 and very big clean
// up!
// 
// Revision 1.4 2005/10/31 18:22:30 sweissTFH
// clean up and commenting
// 
// Revision 1.3 2005/05/27 11:18:31 sweissTFH
// *** empty log message ***
// 
//
