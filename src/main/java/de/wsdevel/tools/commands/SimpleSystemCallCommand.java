package de.wsdevel.tools.commands;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author sweiss
 * 
 *         A simple system command executer Created on 15.01.2005
 * 
 *         for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.7 $ -- $Date: 2007-09-04 14:21:00
 *          $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public class SimpleSystemCallCommand extends AbstractCommand implements
	CommandWithThrowable {

    /**
     * {@link Log}.
     */
    private static final Log LOG = LogFactory
	    .getLog(SimpleSystemCallCommand.class);

    /**
     * {@link String} COMMENT.
     */
    private String command;

    /**
     * <code>boolean</code> COMMENT.
     */
    private boolean blocking = true;

    /**
     * COMMENT.
     * 
     * @param cmd
     *            {@link String}
     * @param blockingVal
     *            <code>boolean</code>
     */
    public SimpleSystemCallCommand(final String cmd, final boolean blockingVal) {
	this.command = cmd;
	this.blocking = blockingVal;
    }

    /**
     * Constructs a blocking {@link SimpleSystemCallCommand}.
     * 
     * @param cmd
     *            {@link String}
     */
    public SimpleSystemCallCommand(final String cmd) {
	this(cmd, true);
    }

    /**
     * @throws Exception
     *             COMMENT
     * @see de.wsdevel.tools.commands.CommandWithThrowable#run()
     */
    public final void run() throws Exception {
	if (LOG.isDebugEnabled()) {
	    LOG.debug("executing command: " + this.command);
	}

	final Process process = Runtime.getRuntime().exec(this.command);
	// process = new
	// ProcessBuilder(command).redirectErrorStream(true).start();

	// SEBASTIAN to be tested!
	new Timer(false).schedule(new TimerTask() {
	    @Override
	    public void run() {
		Reader reader = new InputStreamReader(process.getInputStream());
		Writer writer = new PrintWriter(System.out);
		try {
		    if (reader.ready()) {
			int i = 0;
			while (true) {
			    i = reader.read();
			    if (i < 0) {
				break;
			    }
			    writer.append((char) i);
			}
		    }
		} catch (IOException e) {
		    LOG.error(e.getLocalizedMessage(), LOG.isDebugEnabled() ? e
			    : null);
		} finally {
		    try {
			reader.close();
			writer.close();
		    } catch (IOException e) {
			LOG.error(e.getLocalizedMessage(),
				LOG.isDebugEnabled() ? e : null);
		    }
		}
	    }
	}, 0);

	if (this.blocking) {
	    if (process != null) {
		try {
		    int rv = process.waitFor();
		    if (rv != 0) {
			LOG.warn("Return value of process was not 0! Value: "
				+ rv);
		    }
		} catch (InterruptedException ie) {
		    LOG.error("error during wait", ie);
		}
	    }
	}
    }

}
//
// $Log: SimpleSystemCallCommand.java,v $
// Revision 1.7  2009-02-09 16:54:25  sweiss
// bug fixing and cleanup
//
// Revision 1.6 2009-02-09 13:15:14 sweiss
// clean up of stuff
//
// Revision 1.5 2007-09-04 14:21:00 sweiss
// chages due to CommandWithThrowable
//
// Revision 1.4 2007-08-06 17:33:39 ischmidt
// switched to commmons logging
//
// Revision 1.3 2007-05-30 17:02:25
// sweiss some ui improvements on listwithlistmodel
// 
// Revision 1.2 2006-05-26 22:09:37 sweissTFH
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
// Revision 1.4 2005/11/20 15:19:06 sweissTFH
// now command is blocking the
// current thread and the output is written sysout
// 
// Revision 1.3 2005/11/19 19:18:17 sweissTFH
// as standard this command is
// blocking, but that is configurable
// 
// Revision 1.2 2005/10/26 16:56:23 mschneiderTFH
// start of very big clean up and
// commenting! (sw)
// 
//
