package de.wsdevel.tools.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created on 09.10.2008.
 * 
 * for project: Scenejo__Project
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.2 $ -- $Date: 2008-10-14 17:42:16
 *          $
 * 
 * <br>
 *          (c) 2007, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class ZipFileHelper {

	/**
	 * {@link Log} the logger.
	 */
	private static final Log LOG = LogFactory.getLog(ZipFileHelper.class);

	/**
	 * Hidden constructor.
	 */
	private ZipFileHelper() {
	}

	/**
	 * COMMENT.
	 * 
	 * @param zipFileName
	 *            {@link String}
	 * @param filesToBeZipped
	 *            {@link File}[]
	 * @return {@link File} the ziped file. Or <code>null</code> if an error
	 *         occurred.
	 */
	public static File zip(final String zipFileName,
			final File dirFilesAreRelatedTo, final File[] filesToBeZipped,
			final boolean doItRecursive) {

		if (zipFileName == null || zipFileName.trim().equals("")) {
			throw new IllegalArgumentException(
					"zipFileName may neither be null nor empty!");
		}
		if (dirFilesAreRelatedTo == null || dirFilesAreRelatedTo.isFile()) {
			throw new IllegalArgumentException(
					"dirFilesAreRelatedTo may neither be null nor a file! Has to be a directory! ["
							+ (dirFilesAreRelatedTo != null ? dirFilesAreRelatedTo
									.getAbsolutePath()
									: "null") + "]");
		}
		if (filesToBeZipped == null || filesToBeZipped.length < 1) {
			LOG.warn("no files to be zipped given!");
			return null;
		}

		File zipFile = new File(zipFileName);
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipFile));
			innerzip(dirFilesAreRelatedTo, filesToBeZipped, doItRecursive, out);
			out.close();
		} catch (IOException e) {
			LOG.error(e.getLocalizedMessage(), LOG.isDebugEnabled() ? e : null);
		}
		return zipFile;
	}

	/**
	 * COMMENT.
	 * 
	 * @param dirFilesAreRelatedTo
	 * @param filesToBeZipped
	 * @param doItRecursive
	 * @param out
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void innerzip(final File dirFilesAreRelatedTo,
			final File[] filesToBeZipped, final boolean doItRecursive,
			final ZipOutputStream out) throws FileNotFoundException,
			IOException {
		byte[] buf = new byte[1024];
		// Compress the files
		for (int i = 0; i < filesToBeZipped.length; i++) {
			File file = filesToBeZipped[i];
			if (file.isDirectory() && doItRecursive) {
				innerzip(dirFilesAreRelatedTo, file.listFiles(), doItRecursive,
						out);
			} else {
				FileInputStream in = new FileInputStream(file);

				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(
						filesToBeZipped[i].getAbsolutePath()
								.substring(
										dirFilesAreRelatedTo.getAbsolutePath()
												.length())));

				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				// Complete the entry
				out.closeEntry();
				in.close();
			}
		}
	}

	/**
	 * COMMENT.
	 * 
	 * @param dirToExtractTo
	 *            {@link File}
	 * @param zipFile
	 *            {@link File}
	 * @return {@link File}[] containing references to the extracted files (if
	 *         needed at all).
	 */
	public static File[] unzip(final File dirToExtractTo, final File zipFile) {
		ArrayList<File> fileList = new ArrayList<File>();
		try {
			ZipInputStream in = new ZipInputStream(new FileInputStream(zipFile));
			byte[] buf = new byte[1024];

			ZipEntry entry = null;
			while ((entry = in.getNextEntry()) != null) {
				// Open the output file
				File file = new File(dirToExtractTo.getAbsolutePath()
						+ entry.getName());
				if (file.getParentFile().mkdirs()) {
					LOG.error("Could not create directory ["
							+ file.getParentFile().getAbsolutePath() + "]");
				}
				OutputStream out = new FileOutputStream(file);
				try {
					// Transfer bytes from the ZIP file to the output file
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
				} finally {
					// Close the streams
					out.close();
				}
				fileList.add(file);
			}
			in.close();
		} catch (IOException e) {
			LOG.error(e.getLocalizedMessage(), LOG.isDebugEnabled() ? e : null);
		}
		return fileList.toArray(new File[] {});
	}
}
//
// $Log: ZipFileHelper.java,v $
// Revision 1.2  2009-02-09 16:54:25  sweiss
// bug fixing and cleanup
//
// Revision 1.1 2008-10-14 17:42:16 sweiss
// zip file support
//
// Revision 1.1 2008-10-09 16:58:33 weiss
// added zipfile support
//
//
