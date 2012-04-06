package de.wsdevel.tools.app.storing;

/**
 * Created on 08.10.2003.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.1 $ -- $Date: 2006-05-02 16:06:00 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 */
public abstract class AbstractStorable implements Storable {

    /**
     * COMMENT.
     * 
     */
    public AbstractStorable() {
        DataStorer.addStorable(this);
    }

    /**
     * @see de.wsdevel.tools.app.storing.Storable#store()
     */
    public abstract void store();

}
/*
 * $Log: AbstractStorable.java,v $
 * Revision 1.1  2006-05-02 16:06:00  sweissTFH
 * cleaned up tools and moved everything to appropriate new packages
 *
 * Revision 1.4  2006/04/10 15:34:12  sweissTFH
 * cleaned up checkstyle errors
 *
 * Revision 1.3  2005/10/26 16:56:23  mschneiderTFH
 * start of very big clean up and commenting! (sw)
 *
 */
