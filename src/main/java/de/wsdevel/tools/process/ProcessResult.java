package de.wsdevel.tools.process;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 24.02.2009.
 * 
 * (c) 2007, Sebastian A. Weiss - All rights reserved.
 * 
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author: sweiss $ -- $Revision: 1.1 $ -- $Date: 2009-03-05 09:22:52
 *          $
 */
public class ProcessResult {

	/**
	 * {@link List}< {@link String}> COMMENT.
	 */
	private List<String> consoleOutput = new LinkedList<String>();

	/**
	 * <code>int</code> COMMENT.
	 */
	private int result = 0;

	/**
	 * @param consoleOutputRef
	 *            {@link String} the consoleOutput to set.
	 */
	public final void addConsoleOutputLine(final String consoleOutputRef) {
		this.consoleOutput.add(consoleOutputRef);
	}

	/**
	 * @return List<String> the consoleOutput.
	 */
	public final List<String> getConsoleOutput() {
		return this.consoleOutput;
	}

	/**
	 * @return <code>int</code> the result.
	 */
	public final int getResult() {
		return this.result;
	}

	/**
	 * @param consoleOutputRef
	 *            {@link String} the consoleOutput to set.
	 */
	public final void setConsoleOutput(final List<String> consoleOutputRef) {
		this.consoleOutput = consoleOutputRef;
	}

	/**
	 * @param resultVal
	 *            <code>int</code> the result to set.
	 */
	public final void setResult(final int resultVal) {
		this.result = resultVal;
	}

}
//
// $Log: ProcessResult.java,v $
// Revision 1.1 2009-03-05 09:22:52 sweiss
// *** empty log message ***
//
// Revision 1.2 2009-03-04 10:38:38 sweiss
// fixed line processing of job result
//
// Revision 1.1 2009-03-01 16:10:40 sweiss
// refactored filer services, added ftp service and cleanup
//
// Revision 1.1 2009-02-25 12:07:30 sweiss
// cleanup and tried to fixe charset of console output
//
// Revision 1.1 2009-02-25 10:37:42 sweiss
// added filer stuff
//
//
