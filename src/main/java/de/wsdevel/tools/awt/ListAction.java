package de.wsdevel.tools.awt;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created on 10.01.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.2 $ -- $Date: 2005/10/31
 *          18:22:30 $ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public abstract class ListAction extends AbstractAction implements
	ListSelectionListener {

    /**
     * <code>long</code> serial version unique id.
     */
    private static final long serialVersionUID = -4522007150949041444L;

    /**
     * {@link JList} COMMENT.
     */
    private JList list;

    /**
     * {@link Object} COMMENT.
     */
    private Object value;

    /**
     * COMMENT.
     * 
     * @param icon
     *            {@link Icon}
     * @param listVal
     *            {@link JList}
     * @param enabled
     *            <code>boolean</code>
     */
    public ListAction(final Icon icon, final JList listVal,
	    final boolean enabled) {
	this(listVal, enabled);
	putValue(Action.SMALL_ICON, icon);
    }

    /**
     * COMMENT.
     * 
     * @param caption
     *            {@link String}
     * @param listVal
     *            {@link JList}
     * @param enabled
     *            <code>boolean</code>
     */
    public ListAction(final String caption, final JList listVal,
	    final boolean enabled) {
	this(listVal, enabled);
	putValue(Action.NAME, caption);
    }

    /**
     * COMMENT.
     * 
     * @param listVal
     *            {@link JList}
     * @param enabled
     *            <code>boolean</code>
     */
    private ListAction(final JList listVal, final boolean enabled) {
	this.list = listVal;
	setEnabled(enabled);
    }

    /**
     * @param e
     *            {@link ListSelectionEvent}
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    public final void valueChanged(final ListSelectionEvent e) {
	this.value = this.list.getSelectedValue();
	checkStateForValue();
    }

    /**
     * COMMENT.
     * 
     */
    protected final void checkStateForValue() {
	if (this.value != null) {
	    setEnabled(true);
	} else {
	    setEnabled(false);
	}
    }
}
/*
 * $Log: ListAction.java,v $
 * Revision 1.2  2009-02-09 13:15:14  sweiss
 * clean up of stuff
 * Revision 1.1 2006/05/02 16:06:00 sweissTFH cleaned
 * up tools and moved everything to appropriate new packages
 * 
 * Revision 1.4 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
 * Revision 1.3 2005/10/31 18:22:30 sweissTFH clean up and commenting
 */
