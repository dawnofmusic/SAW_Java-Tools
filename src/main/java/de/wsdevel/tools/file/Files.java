package de.wsdevel.tools.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.wsdevel.tools.streams.Streams;

/**
 * Created on 27.10.2004.
 * 
 * for project: tools
 * 
 * @author <a href="mailto:sweiss@weissundschmidt.de">Sebastian A. Weiss - Weiss
 *         und Schmidt, Mediale Systeme GbR</a>
 * @version $Author: sweiss $ -- $Revision: 1.4 $ -- $Date: 2006/05/02 16:06:00
 *          $
 * 
 * <br>
 *          (c) 2005, Weiss und Schmidt, Mediale Systeme GbR - All rights
 *          reserved.
 * 
 */
public final class Files {

	/**
	 * {@link Log} the logger.
	 */
	private static final Log LOG = LogFactory.getLog(Files.class);

	/**
	 * private constructor.
	 */
	private Files() {
	}

	/**
	 * @param dir
	 *            {@link File}
	 */
	public static void clearDirectory(final File dir) {
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException("Given file is not a directory!");
		}
		File[] files = dir.listFiles();
		if (files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				if (!files[i].delete()) {
					LOG.error("Could not delete file ["
							+ files[i].getAbsolutePath() + "]");
				}
			}
		}
	}

	/**
	 * @param src
	 *            {@link File}source
	 * @param dst
	 *            {@link File}destination
	 * @throws IOException
	 *             if an error occurs while reading / writing
	 */
	public static void copy(final File src, final File dst) throws IOException {
		Streams.readFromInWriteToOut(new FileInputStream(src), true,
				new FileOutputStream(dst), true);
	}

	/**
	 * COMMENT.
	 * 
	 * @param directoryName
	 *            {@link String}
	 * @param outputFileName
	 *            {@link String}
	 */
	public static void mergeFilesInDirectory(final String directoryName,
			final String outputFileName) {
		File file = new File(directoryName);
		File[] files = file.listFiles();
		if (file.isDirectory() && files.length > 0) {
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(
						outputFileName);
				for (int i = 0; i < files.length; i++) {
					Streams.readFromInWriteToOut(new FileInputStream(
							files[i]), true, fileOutputStream, false);
					if (LOG.isDebugEnabled()) {
						LOG.debug("Appended [" + files[i].getName()
								+ "] to merged file!");
					}
				}
				fileOutputStream.close();
			} catch (FileNotFoundException e) {
				LOG.error(e.getLocalizedMessage(), LOG.isDebugEnabled() ? e
						: null);
			} catch (IOException e) {
				LOG.error(e.getLocalizedMessage(), LOG.isDebugEnabled() ? e
						: null);
			}
		} else {
			LOG
					.error("given directory name does not denote a directory or is empty!");
		}
	}

	/**
	 * COMMENT.
	 * 
	 * @param rootDir
	 *            {@link File}
	 * @param ignoreHiddenFiles
	 *            <code>boolean</code>
	 * @param sortFileNames
	 *            <code>boolean</code>
	 * @return {@link Collection}
	 */
	public static Collection<File> getFlattenedDirectoryTree(
			final File rootDir, final boolean ignoreHiddenFiles,
			final boolean sortFileNames) {
		Collection<File> flattenedCollection;
		if (sortFileNames) {
			flattenedCollection = new TreeSet<File>(new Comparator<File>() {
				public int compare(final File arg0, final File arg1) {
					return arg0.getAbsolutePath().compareTo(
							arg1.getAbsolutePath());
				}
			});
		} else {
			flattenedCollection = new LinkedList<File>();
		}

		if (!rootDir.exists() || !rootDir.isDirectory()) {
			throw new IllegalArgumentException("rootDir ["
					+ rootDir.getAbsolutePath()
					+ "]has to be a directory und has to exist!");
		}
		if (!rootDir.canRead()) {
			throw new IllegalArgumentException("Cannot read from rootDir ["
					+ rootDir.getAbsolutePath() + "]");
		}

		File[] files = rootDir.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				flattenedCollection.addAll(getFlattenedDirectoryTree(files[i],
						ignoreHiddenFiles, sortFileNames));
			} else if (files[i].isFile()) {
				if (!(ignoreHiddenFiles && files[i].isHidden())) {
					flattenedCollection.add(files[i]);
				}
			}
		}
		return flattenedCollection;
	}

	/**
	 * COMMENT.
	 * 
	 * @param paramName
	 *            {@link String}
	 * @param paramDir
	 *            {@link File}
	 */
	public static void checkForValidDir(final String paramName,
			final File paramDir) {
		if (paramDir == null) {
			throw new NullPointerException(paramName + " may not be null!");
		}
		if (!paramDir.exists() || !paramDir.isDirectory()) {
			throw new IllegalArgumentException(paramName
					+ " has to be a valid directory!");
		}
	}

	/**
	 * @param dir
	 *            {@link File}
	 */
	public static void clearDirectoryRecursively(final File dir) {
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException("Given file is not a directory!");
		}
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				clearDirectoryRecursively(files[i]);
			}
			files[i].delete();
		}
	}

	/**
	 * COMMENT.
	 * 
	 * @param file
	 *            {@link File}
	 * @return <code>boolean</code>
	 * @throws IOException
	 *             COMMENT
	 */
	public static boolean createNewFile(final File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
			return true;
		}
		return false;
	}

	/**
	 * COMMENT.
	 * 
	 * @param fileName
	 *            {@link String}
	 * @return <code>boolean</code>
	 * @throws FileNotFoundException
	 *             COMMENT
	 */
	public static boolean deleteFile(final String fileName)
			throws FileNotFoundException {
		File file = getExistingFile(fileName);
		return file.delete();
	}

	/**
	 * COMMENT.
	 * 
	 * @param directoryPath
	 *            {@link String}
	 * @return {@link File}
	 */
	public static File getDirectory(final String directoryPath) {
		File dir = new File(directoryPath);
		if (!dir.exists()) {
			dir.mkdir();
		} else {
			if (!dir.isDirectory()) {
				dir.mkdir();
			}
		}
		return dir;
	}

	/**
	 * COMMENT.
	 * 
	 * @param path
	 *            {@link String}
	 * @return {@link File}
	 * @throws FileNotFoundException
	 *             COMMENT
	 */
	public static File getExistingDirectory(final String path)
			throws FileNotFoundException {
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			return file;
		}
		throw new FileNotFoundException();
	}

	/**
	 * COMMENT.
	 * 
	 * @param path
	 *            {@link String}
	 * @return {@link File}
	 * @throws FileNotFoundException
	 *             COMMENT
	 */
	public static File getExistingFile(final String path)
			throws FileNotFoundException {
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			return file;
		}
		throw new FileNotFoundException();
	}

	/**
	 * COMMENT.
	 * 
	 * @param path
	 *            {@link String}
	 * @return {@link File}
	 * @throws IOException
	 *             COMMENT
	 */
	public static File getFile(final String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	/**
	 * @param path
	 *            {@link String} containing '\' or '/'
	 * @return {@link String} using correct slashes for current os
	 */
	public static String getOSDependendPath(final String path) {
		return path.replace('/', File.separatorChar).replace('\\',
				File.separatorChar);
	}

	/**
	 * COMMENT.
	 * 
	 * @param name
	 *            {@link String}
	 * @return <code>boolean</code>
	 */
	public static boolean removeFile(final String name) {
		File file = new File(name);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * Exports the specified text to a default file.
	 * 
	 * @param inText
	 *            {@link String}
	 * @param file
	 *            {@link File}
	 * @throws IOException
	 *             COMMENT
	 */
	public static void writeTextToFile(final String inText, final File file)
			throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("exporting text '" + inText + "' to file: "
					+ file.getAbsolutePath());
		}
		final FileWriter fw = new FileWriter(file);
		fw.write(inText);
		fw.close();
	}

}
//
// $Log: Files.java,v $
// Revision 1.4  2009-02-20 10:16:38  sweiss
// added some functionality
//
// Revision 1.3 2009-02-09 16:54:25 sweiss
// bug fixing and
// cleanup
// 
// Revision 1.2 2007-08-06 17:33:39 ischmidt
// switched to commmons
// logging
// 
// Revision 1.1 2006/05/02 16:06:00 sweissTFH
// cleaned up tools and moved
// everything to appropriate new packages
// 
// Revision 1.1 2006/04/17 15:15:55 sweissTFH
// added FileTreeFlatterer and
// cleaned up
// 
// Revision 1.2 2006/04/04 11:55:38 sweissTFH
// empty log message
// 
// Revision 1.1 2005/12/27 16:06:01 sweissTFH
// moved to java 5 and very big clean
// up!
// 
// Revision 1.4 2005/10/31 18:22:30 sweissTFH
// clean up and commenting
// 
// Revision 1.3 2005/05/27 11:18:31 sweissTFH
// empty log message
//
