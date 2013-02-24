package de.wsdevel.tools.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created on 17.04.2006.
 * 
 * for project: Java_Tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.2 $ -- $Date: 2006/05/02 16:06:00
 *          $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class FileTreeFlatterer {

    /**
     * hidden constructor.
     */
    private FileTreeFlatterer() {
    }

    /**
     * COMMENT.
     * 
     * @param args
     *            {@link String}[]
     */
    public static void main(final String[] args) {
	try {
	    FileWriter fw = new FileWriter(
		    new File(
			    "D:/usr/home/sweiss/radio_c/PHP_Radio_C_Archive/res/flattenedFiles.txt"));
	    Iterator<File> filesIt = Files.getFlattenedDirectoryTree(
		    new File("G:\\"), true, true).iterator();
	    while (filesIt.hasNext()) {
		File file = filesIt.next();
		String absolutePath = file.getAbsolutePath();
		fw.write(absolutePath + "\r\n");
	    }
	    fw.flush();
	    fw.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
/*
 * $Log: FileTreeFlatterer.java,v $ Revision 1.2 2009-02-09 13:15:16 sweiss
 * clean up of stuff Revision 1.1 2006/05/02 16:06:00 sweissTFH cleaned up tools
 * and moved everything to appropriate new packages
 * 
 * Revision 1.1 2006/04/17 15:15:55 sweissTFH added FileTreeFlatterer and
 * cleaned up
 */
