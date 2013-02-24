package de.wsdevel.tools.awt;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Created on 23.06.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.2 $ -- $Date: 2005/10/31
 *          18:22:30 $ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class EventHelper {

    /**
     * amount that should be added to scrollable component if scrolled to
     * bottom.
     */
    private static final int SCROLL_ADDON = 1000;

    /**
     * hidden constructor.
     */
    private EventHelper() {
    }

    /**
     * @param comp
     *            {@link Component}
     * @param action
     *            {@link ActionListener}
     * @return {@link KeyListener}
     */
    public static KeyListener createEnterHandler(final Component comp,
            final ActionListener action) {
        return new EnterAndFocusLostHandler(comp, action);
    }

    /**
     * @param comp
     *            {@link Component}
     * @param action
     *            {@link ActionListener}
     * @return {@link FocusListener}
     */
    public static FocusListener createFocusLostListener(final Component comp,
            final ActionListener action) {
        return new EnterAndFocusLostHandler(comp, action);
    }

    /**
     * @param comp
     *            {@link Component}
     * @param action
     *            {@link ActionListener}
     */
    public static void addEnterAndFocusLostHandlerToComponent(
            final Component comp, final ActionListener action) {
        EnterAndFocusLostHandler eaflh = new EnterAndFocusLostHandler(comp,
                action);
        comp.addKeyListener(eaflh);
        comp.addFocusListener(eaflh);
    }

    /**
     * helper class merging events.
     */
    private static class EnterAndFocusLostHandler extends KeyAdapter implements
            KeyListener, FocusListener {

        /**
         * the component we are listening to.
         */
        private Component comp;

        /**
         * the action listener we shell inform if we got an event.
         */
        private ActionListener action;

        /**
         * @param compVal
         *            {@link Component}
         * @param actionVal
         *            {@link ActionListener}
         */
        public EnterAndFocusLostHandler(final Component compVal,
                final ActionListener actionVal) {
            this.comp = compVal;
            this.action = actionVal;
        }

        /**
         * @param evt
         *            {@link FocusEvent}
         */
        public final void focusGained(final FocusEvent evt) {
            // forget about it
        }

        /**
         * @param evt
         *            {@link FocusEvent}
         */
        public final void focusLost(final FocusEvent evt) {
            this.action.actionPerformed(new ActionEvent(this.comp, evt.getID(),
                    ""));
        }

        /**
         * @param evt
         *            {@link KeyEvent}
         */
        @Override
        public final void keyPressed(final KeyEvent evt) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                this.action.actionPerformed(new ActionEvent(this.comp, evt
                        .getID(), ""));
            }
        }
    }

    /**
     * @param scrollableToBeScrolled
     *            {@link JPanel}
     * @return {@link ListDataListener}
     */
    public static ListDataListener createSrollToBottomListener(
            final JPanel scrollableToBeScrolled) {
        return new ListDataListener() {
            public void intervalAdded(final ListDataEvent e) {
                scrollScrollableToBottom(scrollableToBeScrolled);
            }

            public void intervalRemoved(final ListDataEvent e) {
            }

            public void contentsChanged(final ListDataEvent e) {
            }
        };
    }

    /**
     * @param scrollableToBeScrolled
     *            {@link JPanel}
     */
    public static void scrollScrollableToBottom(
            final JPanel scrollableToBeScrolled) {
        final Rectangle rect = new Rectangle(0, 0, 1, 1);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                rect.y = scrollableToBeScrolled.getBounds().height
                        + SCROLL_ADDON;
                scrollableToBeScrolled.scrollRectToVisible(rect);
            }
        });
    }
}
/*
 * $Log: EventHelper.java,v $
 * Revision 1.2  2006-06-02 16:23:16  sweissTFH
 * clean up and wrote test for JZommableComponent
 *
 * Revision 1.1  2006/05/02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.4  2006/04/05 18:19:35  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.3 2005/10/31 18:22:30 sweissTFH clean
 * up and commenting
 * 
 */
