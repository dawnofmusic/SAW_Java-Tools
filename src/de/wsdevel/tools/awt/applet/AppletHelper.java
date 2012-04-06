package de.wsdevel.tools.awt.applet;

import java.applet.Applet;
import java.awt.Container;
import java.awt.Frame;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created on 28.07.2006.
 *
 * for project: Java_Tools
 *
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweissTFH $ -- $Revision: 1.5 $ -- $Date: 2006-11-14 13:16:50 $
 *
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 *
 */
public final class AppletHelper {

    /**
     * {@link String} COMMENT.
     */
    private static final String SLASH = "/";

    /**
     * Hidden constructor.
     */
    private AppletHelper() {
    }

    /**
     * COMMENT.
     *
     * @param applet {@link Applet}
     * @param fileSpec {@link String}
     * @return {@link String}
     * @throws MalformedURLException COMMENT
     */
    public static URL getURL(final Applet applet, final String fileSpec)
            throws MalformedURLException {
        return new URL(applet.getDocumentBase().getProtocol(), applet
                .getDocumentBase().getHost(), applet.getDocumentBase()
                .getPort(), getPathToParent(applet) + fileSpec);
    }

    /**
     * @see AppletHelper#getPathToParent(String)
     *
     * @param applet {@link Applet}
     * @return {@link String} with an ending "/"
     */
    public static String getPathToParent(final Applet applet) {
        return getPathToParent(applet.getDocumentBase().getPath());
    }

    /**
     * COMMENT.
     *
     * @param path {@link String}
     * @return {@link String} with an ending "/"
     */
    public static String getPathToParent(final String path) {
        if (path.endsWith(AppletHelper.SLASH)) {
            return path;
        }
        String[] elements = path.split(AppletHelper.SLASH);
        String newPath = "";
        for (int i = 0; i < (elements.length - 1); i++) {
            newPath += elements[i] + AppletHelper.SLASH;
        }
        return newPath;
    }

    /**
     * COMMENT.
     * @param applet {@link Applet}
     * @return {@link Frame}
     */
    public static Frame findParentFrame(final Applet applet) {
        Container c = applet;
        while (c != null) {
            if (c instanceof Frame) {
                return (Frame) c;
            }
            c = c.getParent();
        }
        return null;
    }

}
/*
 * $Log: AppletHelper.java,v $
 * Revision 1.5  2006-11-14 13:16:50  sweissTFH
 * cleanup
 *
 * Revision 1.4  2006/08/04 11:31:26  sweissTFH
 * *** empty log message ***
 *
 * Revision 1.3  2006/07/29 16:45:46  sweissTFH
 * fixing paths
 *
 * Revision 1.2  2006/07/28 15:40:07  sweissTFH
 * clean up
 *
 * Revision 1.1  2006/07/28 14:38:17  sweissTFH
 * new AppletHelper
 *
 */
