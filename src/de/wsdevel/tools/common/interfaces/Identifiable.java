package de.wsdevel.tools.common.interfaces;

import java.io.Serializable;

/**
  * Created on 08.05.2004.
  *
  * for project: tools
  *
  * @author <a href="mailto:weiss@dawnofmusic.com">Sebastian A. Weiss - dawn of music</a>
  * @version $Revision: 1.2 $ -- $Date: 2007-10-01 09:40:03 $
  *
  * (c) dawn of music 2004 - All rights reserved.
  *
  */
public interface Identifiable {

    /**
     * @return the ID of the Identifiable
     */
    Serializable getId();
}
//
// $Log: Identifiable.java,v $
// Revision 1.2  2007-10-01 09:40:03  sweiss
// IDENTIFIABLE returns a Serializable
//
//
