package de.wsdevel.tools.common.interfaces.impl;

import java.io.Serializable;

import de.wsdevel.tools.common.interfaces.Named;

/**
 * Created on 02.05.2005.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss und
 *         Schmidt, Mediale Systeme GbR</a>
 * @version $Author: mschneider $ -- $Revision: 1.3 $ -- $Date: 2007-08-08 13:41:51 $ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public class NamedImpl implements Named, Serializable {

    /**
     * <code>long</code> serial version unique id.
     */
    private static final long serialVersionUID = -4438246706977743564L;

    /**
     * the name.
     */
    private String name;

    /**
     * USED FOR PERSISTENCE ONLY!
     */
    public NamedImpl() {
        setName(null);
    }

    /**
     * @param nameVal
     *            String
     */
    public NamedImpl(final String nameVal) {
        setName(nameVal);
    }

    /**
     * @param obj
     *            {@link Object}
     * @return <code>boolean</code>
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NamedImpl other = (NamedImpl) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * @return Returns the name.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * @return <code>int</code>
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    /**
     * This method is not final, so every inheriting class can overwrite and implement any
     * proves that might be necessary (sw).
     * 
     * @param nameVal
     *            The name to set.
     */
    public void setName(final String nameVal) {
        this.name = nameVal;
    }

}
/*
 * $Log: NamedImpl.java,v $
 * Revision 1.3  2007-08-08 13:41:51  mschneider
 * moved common modules stuff to new project
 * Revision 1.2 2006-06-10 13:00:32 sweissTFH cleanup and smaller
 * changes due to new compiler settings
 * 
 * Revision 1.1 2006/05/02 16:06:01 sweissTFH cleaned up tools and moved everything to
 * appropriate new packages
 * 
 * Revision 1.6 2006/04/05 18:19:35 sweissTFH cleaned up checkstyle errors
 * 
 * Revision 1.5 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean up!
 * 
 * Revision 1.4 2005/10/31 18:22:30 sweissTFH clean up and commenting Revision 1.3
 * 2005/09/09 12:42:15 sweissTFH some clean ups
 * 
 * Revision 1.2 2005/08/23 15:15:55 sweissTFH NamedImpl became serializable
 * 
 * Revision 1.1 2005/06/14 16:22:39 sweissTFH meved AbstractNamed to NamedImpl
 * 
 */
