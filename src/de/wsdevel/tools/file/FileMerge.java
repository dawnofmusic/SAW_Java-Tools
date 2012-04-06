package de.wsdevel.tools.file;

/**
 * Created on 25.12.2005.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: ischmidt $ -- $Revision: 1.2 $ -- $Date: 2006/05/02
 *          16:06:00 $
 * 
 * <br>
 * (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights reserved.
 * 
 * An application that just merges files in a directory to one file. In order to
 * do that, the files will be appended in order of appearance.
 * 
 */
public final class FileMerge {

    /**
     * hidden constructor.
     */
    private FileMerge() {
    }

    /**
     * {@link String} COMMENT.
     */
    private static final String DIR_NAME = "d:/os";

    /**
     * {@link String} COMMENT.
     */
    private static final String OUTPUT_FILENAME = "d:/os.mp3";

    /**
     * COMMENT.
     * 
     * @param args
     *            {@link String}[]
     */
    public static void main(final String[] args) {
        Files.mergeFilesInDirectory(DIR_NAME, OUTPUT_FILENAME);
    }

}
/*
 * $Log: FileMerge.java,v $
 * Revision 1.2  2007-08-06 17:33:39  ischmidt
 * switched to commmons logging
 * Revision 1.1 2006/05/02 16:06:00 sweissTFH cleaned
 * up tools and moved everything to appropriate new packages
 * 
 * Revision 1.1 2006/04/17 15:15:55 sweissTFH added FileTreeFlatterer and
 * cleaned up Revision 1.3 2006/04/10 15:34:12 sweissTFH cleaned up checkstyle
 * errors
 * 
 * Revision 1.2 2006/04/05 18:19:34 sweissTFH cleaned up checkstyle errors
 * 
 * Revision 1.1 2005/12/27 16:06:01 sweissTFH moved to java 5 and very big clean
 * up!
 * 
 */
