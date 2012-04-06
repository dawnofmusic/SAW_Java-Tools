package de.wsdevel.tools.common.interfaces.impl;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.wsdevel.tools.common.interfaces.Named;

/**
 * SEBASTIAN do i really need this id grab? write test for persistence!
 * 
 * Created on 31.10.2005
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.6 $ -- $Date: 2005/12/27 16:06:01
 *          $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class Gender implements Named, Serializable {

    /**
     * <code>long</code>serial version unique id.
     */
    private static final long serialVersionUID = -900983413645439871L;

    /**
     * logger.
     */
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(Gender.class);

    /**
     * {@link String} COMMENT.
     */
    private static final String MALE_ID = "male";

    /**
     * {@link Gender} COMMENT.
     */
    public static final Gender MALE = new Gender(MALE_ID);

    /**
     * {@link String} COMMENT.
     */
    private static final String FEMALE_ID = "female";

    /**
     * {@link Gender} COMMENT.
     */
    public static final Gender FEMALE = new Gender(FEMALE_ID);

    /**
     * {@link String} COMMENT.
     */
    private static final String HERMAPHRODITE_ID = "hermaphrodite";

    /**
     * {@link Gender} COMMENT.
     */
    public static final Gender HERMAPHRODITE = new Gender(HERMAPHRODITE_ID);

    /**
     * the naming of gender.
     */
    private String name;

    /**
     * constructor with name.
     * 
     * @param nameVal
     *            <code>String</code> the naming of gender
     */
    private Gender(final String nameVal) {
	setName(nameVal);
    }

    /**
     * Returns the corresponding gender for the specified id.
     * 
     * @param id
     *            the gender id
     * @return the corresponding <code>Gender</code> object
     */
    public static Gender getGenderForID(final String id) {
	if (id.compareToIgnoreCase(MALE.getName()) == 0) {
	    return MALE;
	} else if (id.compareToIgnoreCase(FEMALE.getName()) == 0) {
	    return FEMALE;
	} else if (id.compareToIgnoreCase(HERMAPHRODITE.getName()) == 0) {
	    return HERMAPHRODITE;
	}
	return null;
    }

    /**
     * @return name <code>String</code> the naming of gender
     */
    public String getName() {
	return this.name;
    }

    /**
     * @param string
     *            <code>String</code> the naming of gender
     */
    public void setName(final String string) {
	this.name = string;
    }

    // object identification --------------------------------------------------

    /**
     * @param obj
     *            {@link Object}
     * @return <code>boolean</code>
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
	return (obj instanceof Gender)
		&& (((Gender) obj).getName().equals(getName()));
    }

    /**
     * @return <code>int</code>
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	return getName().hashCode();
    }

    /**
     * @return {@link String}
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "[Gender: " + getName() + "]";
    }

    // graphical representation -----------------------------------------------

    /**
     * COMMENT.
     * 
     * @return {@link ComboBoxModel}
     */
    public static ComboBoxModel createGenderComboBoxModel() {
	return new DefaultComboBoxModel(new Gender[] { FEMALE, MALE,
		HERMAPHRODITE });
    }

    /**
     * COMMENT.
     * 
     * @return {@link ListCellRenderer}
     */
    public static ListCellRenderer createDefaultGenderListCellRenderer() {
	return new DefaultListCellRenderer() {
	    /**
	     * serial version unique id.
	     */
	    private static final long serialVersionUID = -1271018046498606247L;

	    @Override
	    public Component getListCellRendererComponent(final JList list,
		    final Object value, final int index,
		    final boolean isSelected, final boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index,
			isSelected, cellHasFocus);
		setText((value == null) ? "null" : ((Gender) value).getName());
		return this;
	    }
	};
    }

}
//
// $Log: Gender.java,v $
// Revision 1.6  2009-02-09 13:15:16  sweiss
// clean up of stuff
//
// Revision 1.5 2008-03-19 11:50:05 sweiss
// clean up
//
// Revision 1.4 2007-08-06 17:33:39 ischmidt
// switched to commmons logging
// 
// Revision 1.3 2006/06/10 13:00:32 sweissTFH
// cleanup and
// smaller changes due to new compiler settings
// 
// Revision 1.2 2006/05/26 22:09:37 sweissTFH
// small cleanup
// 
// Revision 1.1 2006/05/02 16:06:01 sweissTFH
// cleaned up tools and moved
// everything to appropriate new packages
// 
// Revision 1.3 2006/04/05 18:19:35 sweissTFH
// cleaned up checkstyle errors
// 
// Revision 1.2 2005/12/27 16:06:01 sweissTFH
// moved to java 5 and very big clean
// up!
// 
// Revision 1.1 2005/10/31 18:22:30 sweissTFH
// clean up and commenting
// 
//
