package de.wsdevel.tools.common.interfaces.impl;

import java.io.Serializable;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.wsdevel.tools.common.GUIDFactory;
import de.wsdevel.tools.common.interfaces.Identifiable;

/**
 * Created on 08.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.5 $ -- $Date: 2005/10/31
 *          18:22:30 $ <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class IdentifiableImpl implements Identifiable {

    /**
     * {@link Log}.
     */
    private static final Log LOG = LogFactory.getLog(IdentifiableImpl.class);

    /**
     * {@link Serializable} COMMENT.
     */
    private Serializable id = null;

    /**
     * @return {@link Object}
     * @see de.wsdevel.tools.common.interfaces.Identifiable#getId()
     */
    public Serializable getId() {
	if (this.id == null) {
	    try {
		this.setId(GUIDFactory.generateGUID(this));
	    } catch (UnknownHostException e) {
		LOG.fatal("Couldn't create GUID", e);
		if (LOG.isInfoEnabled()) {
		    LOG.info("Using simple object instance address as an ID!");
		}
		this.setId(new Object().hashCode());
	    }
	}
	return this.id;
    }

    /**
     * @param obj
     *                {@link Object}
     * @return <code>boolean</code>
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
	return (obj instanceof IdentifiableImpl)
		&& ((IdentifiableImpl) obj).getId().equals(getId());
    }

    /**
     * @return <code>int</code>
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	return getId().hashCode();
    }

    /**
     * @param idVal
     *                {@link Serializable}
     * 
     * COMMENT
     */
    public void setId(final Serializable idVal) {
	this.id = idVal;
    }

}
//
// $Log: IdentifiableImpl.java,v $
// Revision 1.5  2007-10-01 09:40:03  sweiss
// IDENTIFIABLE returns a Serializable
//
// Revision 1.4 2007-08-06 17:33:39 ischmidt
// switched to commmons logging
//
// Revision 1.3 2006/06/10 13:00:32 sweissTFH
// cleanup and smaller changes due to new compiler settings
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
// moved to java 5 and very big clean up!
// 
// Revision 1.1 2005/10/31 18:22:30 sweissTFH
// clean up and commenting
// 
//
