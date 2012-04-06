package de.wsdevel.tools.file;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created on 09.10.2008.
 * 
 * for project: Scenejo__Project
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.2 $ -- $Date: 2008-10-14 17:42:17
 *          $
 * 
 * <br>
 *          (c) 2007, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public class AppTestZipFileHelper {

	/**
	 * {@link Log} the logger.
	 */
	private static final Log LOG = LogFactory
			.getLog(AppTestZipFileHelper.class);

	public static final void main(String[] args) {

		String filename = "test.zip";

		File dirFilesAreRelatedTo = new File("D:\\usr\\local\\jgraph");
		File[] filesToBeZipped = new File[] {
				new File("D:\\usr\\local\\jgraph\\doc"),
				new File("D:\\usr\\local\\jgraph\\examples"),
				new File("D:\\usr\\local\\jgraph\\lib"),
				new File("D:\\usr\\local\\jgraph\\src"),
				new File("D:\\usr\\local\\jgraph\\build.xml"),
				new File("D:\\usr\\local\\jgraph\\migrate.xml") };

		ZipFileHelper
				.zip(filename, dirFilesAreRelatedTo, filesToBeZipped, true);

		File dir = new File("./out/");
		if (!dir.exists()) {
			if (dir.mkdir()) {
				LOG.error("could not create directories!");
			}
		}
		ZipFileHelper.unzip(dir, new File(filename));
	}
}
//
// $Log: AppTestZipFileHelper.java,v $
// Revision 1.2  2009-02-09 16:54:27  sweiss
// bug fixing and cleanup
//
// Revision 1.1 2008-10-14 17:42:17 sweiss
// zip file support
//
// Revision 1.1 2008-10-09 16:58:33 weiss
// added zipfile support
//
//
