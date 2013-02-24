package de.wsdevel.tools.app.config;

import java.util.HashMap;

/**
 * Created on 08.10.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.6 $ -- $Date: 2005/10/26
 *          16:56:23 $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class ConfigRes {

    /**
     * {@link HashMap} COMMENT.
     */
    private static HashMap<Class<? extends ConfigResource>, ConfigResource> instances = new HashMap<Class<? extends ConfigResource>, ConfigResource>();

    /**
     * @param key
     *            {@link Object}
     * @return {@link ConfigResource}the instance to be used
     */
    public static ConfigResource getInstance(
	    final Class<? extends ConfigResource> key) {
	if (!ConfigRes.instances.containsKey(key)) {
	    throw new IllegalArgumentException("unknown key!");
	}
	return ConfigRes.instances.get(key);
    }

    /**
     * @return {@link Object}
     * @throws CloneNotSupportedException
     *             COMMENT
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
	throw new CloneNotSupportedException();
    }

    /**
     * @param key
     *            {@link Object}
     * @param config
     *            {@link ConfigResource}the resource to be used to store
     *            properties
     */
    public static void add(final Class<? extends ConfigResource> key,
	    final ConfigResource config) {
	if (ConfigRes.instances.containsKey(key)) {
	    throw new IllegalArgumentException("key allready in use!");
	}
	ConfigRes.instances.put(key, config);
    }

    /**
     * To be called from an Applets context on destroy(), if ConfigRes was
     * inited in init().
     */
    public static void killInstances() {
	ConfigRes.instances.clear();
    }

}
/*
 * $Log: ConfigRes.java,v $
 * Revision 1.6  2009-02-09 13:15:15  sweiss
 * clean up of stuff
 * Revision 1.5 2006/07/24 10:44:28 sweissTFH fixed
 * destroy for Applets
 * 
 * Revision 1.4 2006/07/21 11:11:00 sweissTFH some comment fixes
 * 
 * Revision 1.3 2006/05/23 10:46:30 sweissTFH empty log message
 * 
 * Revision 1.2 2006/05/23 10:45:39 sweissTFH using class as key
 * 
 * Revision 1.1 2006/05/02 16:06:00 sweissTFH cleaned up tools and moved
 * everything to appropriate new packages
 * 
 * Revision 1.11 2006/04/10 15:34:12 sweissTFH cleaned up checkstyle errors
 * 
 * Revision 1.10 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
 * 
 * Revision 1.9 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean
 * up!
 * 
 * Revision 1.8 2005/10/26 16:56:23 mschneiderTFH start of very big clean up and
 * commenting! (sw)
 * 
 * Revision 1.7 2005/10/13 15:42:21 sweissTFH some enhancements concerning
 * BasicConfigRes, as well cleaned up projects package
 * 
 * Revision 1.6 2005/08/22 14:32:20 sweissTFH added boolean
 * 
 * Revision 1.5 2005/08/17 13:53:40 sweissTFH empty log message
 * 
 * Revision 1.4 2005/08/17 13:51:45 sweissTFH added float support
 */
