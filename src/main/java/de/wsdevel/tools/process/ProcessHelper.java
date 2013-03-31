package de.wsdevel.tools.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created on 24.02.2009.
 * 
 * (c) 2007, Sebastian A. Weiss - All rights reserved.
 * 
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author: sweiss $ -- $Revision: 1.1 $ -- $Date: 2009-03-05 09:22:52
 *          $
 */
public final class ProcessHelper {

	/**
	 * {@link Log} the logger.
	 */
	private static final Log LOG = LogFactory.getLog(ProcessHelper.class);

	/**
	 * COMMENT.
	 * 
	 * @param encodingCommand
	 *            {@link String}
	 * @param workingDirectory
	 *            {@link File}
	 * @return <code>int</code> return value of process
	 * @throws IOException
	 *             COMMENT
	 * @throws InterruptedException
	 *             COMMENT
	 * @throws UnsupportedEncodingException
	 *             COMMENT
	 */
	public static ProcessResult process(final String encodingCommand,
			final File workingDirectory) throws IOException,
			InterruptedException, UnsupportedEncodingException {
		final String[] split = encodingCommand.split("\\s");
		Process p = new ProcessBuilder(split).directory(workingDirectory)
				.redirectErrorStream(true).start();
		BufferedReader isr = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		final ProcessResult pr = new ProcessResult();
		try {
			String line = null;
			while ((line = isr.readLine()) != null) {
				final String string = new String(line.getBytes(), "UTF-8");
				pr.addConsoleOutputLine(string);
				if (LOG.isInfoEnabled()) {
					LOG.info("console output: [" + string + "]");
				}
			}
		} finally {
			isr.close();
		}
		int result = p.waitFor();
		pr.setResult(result);
		return pr;
	}
}
//
// $Log: ProcessHelper.java,v $
// Revision 1.1 2009-03-05 09:22:52 sweiss
// *** empty log message ***
//
// Revision 1.3 2009-03-04 10:38:38 sweiss
// fixed line processing of job result
//
// Revision 1.2 2009-03-03 16:09:19 sweiss
// *** empty log message ***
//
// Revision 1.1 2009-03-01 16:10:40 sweiss
// refactored filer services, added ftp service and cleanup
//
// Revision 1.1 2009-02-25 12:07:30 sweiss
// cleanup and tried to fixe charset of console output
//
// Revision 1.1 2009-02-25 10:37:43 sweiss
// added filer stuff
//
//
