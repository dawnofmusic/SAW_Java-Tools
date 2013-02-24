package de.wsdevel.tools.commands;

import de.wsdevel.tools.awt.SwingThreadObserverList;
import de.wsdevel.tools.awt.model.ObserverList;

/**
 * Created on 12.11.2006.
 *
 * for project: scenejo
 *
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2006-11-12 19:01:18 $
 *
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 *
 */
public final class CommandListenerSupport {

    /**
     * {@link ObserverList}< {@link CommandListener}> COMMENT.
     */
    private ObserverList<CommandListener> listeners = new SwingThreadObserverList<CommandListener>();

    /**
     * COMMENT.
     *
     * @param listener {@link CommandListener}
     */
    public void addCommandListener(final CommandListener listener) {
        this.listeners.addListener(listener);
    }

    /**
     * COMMENT.
     *
     * @param listener {@link CommandListener}
     */
    public void removeCommandListener(final CommandListener listener) {
        this.listeners.removeListener(listener);
    }

    /**
     * COMMENT.
     *
     */
    public void fireCommandFinished() {
        this.listeners.observe(new ObserverList.Action<CommandListener>() {
            public void doit(final CommandListener listener) {
                listener.commandFinished();
            }
        });
    }

}
/*
 * $Log: CommandListenerSupport.java,v $
 * Revision 1.1  2006-11-12 19:01:18  sweissTFH
 * new setwithlistmodel
 *
 */
