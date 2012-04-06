package de.wsdevel.tools.commands;

/**
 * Created on 12.11.2006.
 *
 * for project: Java_Tools
 *
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2006-11-12 19:01:18 $
 *
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 *
 */
public abstract class AbstractCommand {

    /**
     * {@link CommandListenerSupport} COMMENT.
     */
    private CommandListenerSupport commandListenerSupport = new CommandListenerSupport();

    /**
     * COMMENT.
     *
     * @param listener {@link CommandListener} 
     */
    public final void addCommandListener(final CommandListener listener) {
        this.commandListenerSupport.addCommandListener(listener);
    }

    /**
     * COMMENT.
     *
     * @param listener {@link CommandListener} 
     */
    public final void removeCommandListener(final CommandListener listener) {
        this.commandListenerSupport.removeCommandListener(listener);
    }

    /**
     * @return {@link CommandListenerSupport} the cls.
     */
    protected final CommandListenerSupport getCommandListenerSupport() {
        return this.commandListenerSupport;
    }

}
/*
 * $Log: AbstractCommand.java,v $
 * Revision 1.1  2006-11-12 19:01:18  sweissTFH
 * new setwithlistmodel
 *
 */
