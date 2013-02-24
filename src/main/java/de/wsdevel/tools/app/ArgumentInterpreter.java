package de.wsdevel.tools.app;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created on 08.10.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.4 $ -- $Date: 2008-03-18 19:56:37 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public final class ArgumentInterpreter {

	/**
	 * Created on 31.10.2005.
	 * 
	 * for project: tools
	 * 
	 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss -
	 *         Weiss und Schmidt, Mediale Systeme GbR</a>
	 * @version $Author: sweiss $ -- $Revision: 1.4 $ -- $Date: 2005/10/31
	 *          18:22:30 $
	 * 
	 * <br>
	 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
	 * 
	 */
	public interface ArgumentHandle {
		/**
		 * COMMENT.
		 * 
		 * @param arg
		 *            {@link String}
		 */
		void handle(String arg);

		/**
		 * COMMENT.
		 * 
		 * @return {@link String} containing information about usage of param.
		 */
		String usage();
	}

	/**
	 * {@link HashMap} COMMENT.
	 */
	private HashMap<String, ArgumentHandle> handlers = new HashMap<String, ArgumentHandle>();

	/**
	 * COMMENT.
	 * 
	 * @param key
	 *            {@link String}
	 * @param handle
	 *            {@link ArgumentHandle}
	 */
	public void add(final String key, final ArgumentHandle handle) {
		this.handlers.put(key, handle);
	}

	/**
	 * COMMENT.
	 * 
	 * @param args
	 *            {@link String}[]
	 */
	public void interpret(final String[] args) {
		int i = 0;

		while (i < args.length) {
			ArgumentHandle handle = this.handlers.get(args[i]);
			if (handle != null) {
				try {
					handle.handle(args[++i]);
				} catch (ArrayIndexOutOfBoundsException aioobe) {
					usage(args[--i]);
					break;
				}
				i++;
			} else {
				usage(args[i]);
				break;
			}
		}
	}

	/**
	 * COMMENT.
	 * 
	 * @param argument
	 *            {@link String}
	 */
	private void usage(final String argument) {
		ArgumentHandle handle = this.handlers.get(argument);
		if (handle != null) {
			System.err.println(handle.usage());
		} else {
			System.err.println("Invalid arguments : " + argument);
		}
	}

	/**
	 * COMMENT.
	 * 
	 * @return {@link String}
	 */
	public String usage() {
		String usage = "USAGE: ";
		Iterator<ArgumentHandle> iterator = this.handlers.values().iterator();
		while (iterator.hasNext()) {
			usage += iterator.next().usage();
			if (iterator.hasNext()) {
				usage += " ";
			}
		}
		return usage;
	}

}
//
// $Log: ArgumentInterpreter.java,v $
// Revision 1.4  2008-03-18 19:56:37  sweiss
// clean up
//
// Revision 1.3 2007-05-14 13:36:31 sweiss
// *** empty log message ***
//
// Revision 1.2 2007-04-22 18:47:08 sweiss
// improvements of ArgumentInterpretation
//
// Revision 1.1 2006/05/02 16:06:01 sweissTFH
// cleaned up tools and moved everything to appropriate new packages
//
// Revision 1.5 2006/04/05 18:19:35 sweissTFH
// cleaned up checkstyle errors
// 
// Revision 1.4 2005/12/27 16:06:01 sweissTFH
// moved to java 5 and very big clean up!
// 
// Revision 1.3 2005/10/31 18:22:30 sweissTFH 
// clean up and commenting
// 
//
