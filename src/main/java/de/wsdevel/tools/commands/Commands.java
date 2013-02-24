package de.wsdevel.tools.commands;

/**
 * Created on 10.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.3 $ -- $Date: 2005/10/26
 *          16:56:23 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public final class Commands {

    /**
     * Hidden constructor.
     */
    private Commands() {
    }

    /**
     * COMMENT.
     * 
     * @param c
     *            {@link Command}
     */
    public static void execute(final Command c) {
        c.run();
    }

    /**
     * COMMENT.
     * 
     * @param c
     *            {@link CommandWithThrowable}
     * @param th
     *            {@link ThrowableHandler}
     */
    public static void execute(final CommandWithThrowable c,
            final ThrowableHandler th) {
        try {
            c.run();
        } catch (Throwable e) {
            th.handle(e);
        }
    }

    /**
     * COMMENT.
     * 
     * @param c
     *            {@link Command}
     */
    public static void executeInOtherThread(final Command c) {
        new Thread(c).start();
    }

    /**
     * COMMENT.
     * 
     * @param c
     *            {@link Command}
     * @param th
     *            {@link ThrowableHandler}
     */
    public static void executeInOtherThread(final CommandWithThrowable c,
            final ThrowableHandler th) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    c.run();
                } catch (Throwable e) {
                    th.handle(e);
                }
            }
        }).start();
    }

}
/*
 * $Log: Commands.java,v $
 * Revision 1.3  2007-09-04 15:31:11  sweiss
 * bug fixing
 *
 * Revision 1.2  2007-09-04 14:21:00  sweiss
 * chages due to CommandWithThrowable
 *
 * Revision 1.1  2006/05/02 16:06:01  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.3  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.2 2005/10/26 16:56:23 mschneiderTFH start
 * of very big clean up and commenting! (sw)
 * 
 */
