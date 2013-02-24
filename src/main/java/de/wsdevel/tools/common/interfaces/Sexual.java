package de.wsdevel.tools.common.interfaces;

import de.wsdevel.tools.common.interfaces.impl.Gender;

/**
 * Created on 24.05.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:weiss@dawnofmusic.com">Sebastian A. Weiss - dawn of
 *         music</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2005/10/31
 *          18:22:30 $ <br>
 *          (c) dawn of music 2004 - All rights reserved.
 * 
 */
public interface Sexual {

    /**
     * @return the gender of the Sexual
     */
    Gender getGender();
}
/*
 * $Log: Sexual.java,v $
 * Revision 1.1  2006-05-02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.5  2006/04/05 18:19:34  sweissTFH
 * cleaned up checkstyle errors
 * Revision 1.4 2005/11/20 15:20:04 sweissTFH small
 * cleanup
 * 
 */
