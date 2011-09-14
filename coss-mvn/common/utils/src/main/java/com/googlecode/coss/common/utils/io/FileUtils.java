/**
 * 
 */
package com.googlecode.coss.common.utils.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.googlecode.coss.common.utils.collections.CharStack;
import com.googlecode.coss.common.utils.collections.CollectionUtils;
import com.googlecode.coss.common.utils.lang.StringUtils;
import com.googlecode.coss.common.utils.lang.exception.Assert;

/**
 * <p>
 * File Operation
 * </p>
 */
public class FileUtils {

	public static final int BUFFER_SIZE = 4096;

	/**
	 * <p>
	 * Get All File List from appointed path, Directory not included
	 * </p>
	 * 
	 * @param path
	 * @return
	 */
	public static List<File> getFileList(String path) {
		File file = new File(path);
		return getFileList(file);
	}

	/**
	 * <p>
	 * Get All File List from appointed path, Directory not included
	 * </p>
	 * 
	 * @param file
	 * @return
	 */
	public static List<File> getFileList(File file) {
		File[] files = file.listFiles();
		if (files == null || files.length < 1) {
			return null;
		}
		List<File> fileList = new ArrayList<File>();
		for (File f : files) {
			if (!f.isDirectory()) {
				fileList.add(f);
			} else {
				fileList = (List<File>) CollectionUtils.contact(fileList, getFileList(f.getPath()));
			}
		}
		return fileList;
	}

	/**
	 * <p>
	 * Read from text file, return the file content as a StringBuilder
	 * </p>
	 * 
	 * @param path
	 * @return StringBuilder
	 */
	public static StringBuilder readFileToString(String path) {
		BufferedReader reader = null;
		StringBuilder sb = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			sb = new StringBuilder();
			int c;
			c = reader.read();
			while (c != -1) {
				sb.append((char) c);
				c = reader.read();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(reader);
		}
		return sb;
	}

	/**
	 * <p>
	 * Read from text file, return the file content as a StringBuilder
	 * </p>
	 * 
	 * @param path
	 * @param encoding
	 * @return
	 */
	public static StringBuilder readFileToString(String path, String encoding) {
		BufferedReader reader = null;
		StringBuilder sb = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding));
			sb = new StringBuilder();
			int c;
			c = reader.read();
			while (c != -1) {
				sb.append((char) c);
				c = reader.read();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(reader);
		}
		return sb;
	}

	/**
	 * <p>
	 * Save String to text file
	 * </p>
	 * 
	 * @param path
	 * @param content
	 */
	public static void saveStringToFile(String path, String content) {
		makeDir(path);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(path));
			writer.write(content);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	/**
	 * <p>
	 * Save StringBuilder to text file
	 * </p>
	 * 
	 * @param path
	 * @param content
	 */
	public static void saveStringToFile(String path, StringBuilder content) {
		saveStringToFile(path, content.toString());
	}

	/**
	 * <p>
	 * Save String to text file
	 * </p>
	 * 
	 * @param path
	 * @param content
	 * @param encoding
	 */
	public static void saveStringToFile(String path, String content, String encoding) {
		makeDir(path);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), encoding));
			writer.write(content);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	/**
	 * <p>
	 * Save String to text file
	 * </p>
	 * 
	 * @param path
	 * @param content
	 * @param encoding
	 */
	public static void saveStringToFile(String path, StringBuilder content, String encoding) {
		saveStringToFile(path, content.toString(), encoding);
	}

	/**
	 * <p>
	 * Delete File or folder, if it is a folder delete it's child file first
	 * </p>
	 * 
	 * @param path
	 *            the path of file or folder to delete
	 * @return
	 */
	public static boolean deleteFileOrFolder(String path) {
		return deleteFileOrFolder(new File(path));
	}

	/**
	 * <p>
	 * Delete File or folder, if it is a folder delete it's child file first
	 * </p>
	 * 
	 * @param file
	 *            the file or folder to delete
	 * @return
	 */
	public static boolean deleteFileOrFolder(File file) {
		if (!file.exists()) {
			throw new java.lang.IllegalArgumentException("file not found");
		}
		File[] childs = file.listFiles();
		if (childs == null || childs.length < 1) {// element

		} else {// not element
			for (File child : childs) {
				deleteFileOrFolder(child);
			}
		}
		return file.delete();
	}

	/**
	 * <p>
	 * Get file extension name from file name or file path
	 * </p>
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getExtensionName(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return StringUtils.EMPTY;
		}
		int lastDotPos = filePath.lastIndexOf(".");
		int lastPathPos = filePath.lastIndexOf(File.separator);
		if (lastPathPos == -1) {
			lastPathPos = filePath.lastIndexOf("/");
		}
		if (lastDotPos != -1) {
			if (lastPathPos > lastDotPos) {
				return StringUtils.EMPTY;
			} else {
				return filePath.substring(lastDotPos + 1);
			}
		} else {
			return StringUtils.EMPTY;
		}
	}

	/**
	 * <p>
	 * Get file extension name from file
	 * </p>
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getExtensionName(File file) {
		return getExtensionName(file.getName());
	}

	/**
	 * Get file name from file path
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return StringUtils.EMPTY;
		}
		CharStack stack = new CharStack();
		stack.pushString(filePath);
		return stack.popUntil(File.separatorChar, '/');
	}

	/**
	 * <p>
	 * Force make folder, if the path is a folder path, make it; if the path is
	 * a file path, make its parent folder
	 * </p>
	 * 
	 * @param path
	 * @return
	 */
	public static boolean makeDir(String path) {
		File file = new File(path);
		if (!file.isDirectory()) {
			file = file.getParentFile();
		}
		if (!file.exists()) {
			return file.mkdirs();
		}
		return false;
	}

	/**
	 * <p>
	 * Copy a file or folder(include children folder or file) to appointing path
	 * </p>
	 * 
	 * @param sourceFile
	 * @param toPath
	 */
	public static void copy(File sourceFile, String toPath) {
		copy(sourceFile.getPath(), toPath);
	}

	/**
	 * <p>
	 * Copy a file or folder(include children folder or file) to appointing path
	 * </p>
	 * 
	 * @param sourceFile
	 * @param toPath
	 */
	public static void copy(String fromPath, String toPath) {
		makeDir(toPath);
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(fromPath));
			OutputStream os = new BufferedOutputStream(new FileOutputStream(toPath));
			copy(is, os);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * Move a file or folder(include children folder or file) to appointing path
	 * </p>
	 * 
	 * @param fromPath
	 * @param toPath
	 */
	public static void move(String fromPath, String toPath) {
		copy(fromPath, toPath);
		FileUtils.deleteFileOrFolder(fromPath);
	}

	/**
	 * <p>
	 * Move a file or folder(include children folder or file) to appointing path
	 * </p>
	 * 
	 * @param sourceFile
	 * @param toPath
	 */
	public static void move(File sourceFile, String toPath) {
		move(sourceFile.getPath(), toPath);
	}

	/**
	 * Copy the contents of the given input File to the given output File.
	 * 
	 * @param in
	 *            the file to copy from
	 * @param out
	 *            the file to copy to
	 * @return the number of bytes copied
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static int copy(File in, File out) throws IOException {
		return copy(new BufferedInputStream(new FileInputStream(in)), new BufferedOutputStream(
				new FileOutputStream(out)));
	}

	/**
	 * Copy the contents of the given byte array to the given output File.
	 * 
	 * @param in
	 *            the byte array to copy from
	 * @param out
	 *            the file to copy to
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static void copy(byte[] in, File out) throws IOException {
		ByteArrayInputStream inStream = new ByteArrayInputStream(in);
		OutputStream outStream = new BufferedOutputStream(new FileOutputStream(out));
		copy(inStream, outStream);
	}

	/**
	 * Copy the contents of the given input File into a new byte array.
	 * 
	 * @param in
	 *            the file to copy from
	 * @return the new byte array that has been copied to
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static byte[] copyToByteArray(File in) throws IOException {
		return copyToByteArray(new BufferedInputStream(new FileInputStream(in)));
	}

	/**
	 * Copy the contents of the given InputStream to the given OutputStream.
	 * Closes both streams when done.
	 * 
	 * @param in
	 *            the stream to copy from
	 * @param out
	 *            the stream to copy to
	 * @return the number of bytes copied
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static int copy(InputStream in, OutputStream out) throws IOException {
		try {
			int byteCount = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * Copy the contents of the given byte array to the given OutputStream.
	 * Closes the stream when done.
	 * 
	 * @param in
	 *            the byte array to copy from
	 * @param out
	 *            the OutputStream to copy to
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static void copy(byte[] in, OutputStream out) throws IOException {
		try {
			out.write(in);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * Copy the contents of the given InputStream into a new byte array. Closes
	 * the stream when done.
	 * 
	 * @param in
	 *            the stream to copy from
	 * @return the new byte array that has been copied to
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static byte[] copyToByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		copy(in, out);
		return out.toByteArray();
	}

	/**
	 * Copy the contents of the given Reader to the given Writer. Closes both
	 * when done.
	 * 
	 * @param in
	 *            the Reader to copy from
	 * @param out
	 *            the Writer to copy to
	 * @return the number of characters copied
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static int copy(Reader in, Writer out) throws IOException {
		try {
			int byteCount = 0;
			char[] buffer = new char[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * Copy the contents of the given String to the given output Writer. Closes
	 * the write when done.
	 * 
	 * @param in
	 *            the String to copy from
	 * @param out
	 *            the Writer to copy to
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static void copy(String in, Writer out) throws IOException {
		try {
			out.write(in);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * Copy the contents of the given Reader into a String. Closes the reader
	 * when done.
	 * 
	 * @param in
	 *            the reader to copy from
	 * @return the String that has been copied to
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static String copyToString(Reader in) throws IOException {
		StringWriter out = new StringWriter();
		copy(in, out);
		return out.toString();
	}

	public static List<File> getClassFiles(String parent, String packageDesc) {
		Assert.hasText(packageDesc);
		packageDesc = packageDesc.replaceAll(".", File.separator);
		if (packageDesc.endsWith("*")) {
			packageDesc = packageDesc.replace("*", "");
		}
		return FileUtils.getFileList(parent + packageDesc);
	}

	public static <T extends Object> String getClassFolder(Class<T> c) {
		String className = c.getName();
		String classNamePath = className.replace(".", "/") + ".class";
		URL url = c.getClassLoader().getResource(classNamePath);
		String path = url.getFile();
		path = path.replace("%20", " ").replace(".class", "");
		int index = path.lastIndexOf("/");
		if (index > -1) {
			path = path.substring(0, index);
		}
		return path;
	}

	/**
	 * 得到相对路径
	 */
	public static String getRelativePath(File baseDir, File file) {
		if (baseDir.equals(file))
			return "";
		if (baseDir.getParentFile() == null)
			return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
		return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length() + 1);
	}

	public static List<File> searchAllNotIgnoreFile(File dir) throws IOException {
		ArrayList<File> arrayList = new ArrayList<File>();
		searchAllNotIgnoreFile(dir, arrayList);
		Collections.sort(arrayList, new Comparator<File>() {
			public int compare(File o1, File o2) {
				return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
			}
		});
		return arrayList;
	}

	public static InputStream getInputStream(String file) throws FileNotFoundException {
		InputStream inputStream = null;
		if (file.startsWith("classpath:")) {
			inputStream = FileUtils.class.getClassLoader().getResourceAsStream(file.substring("classpath:".length()));
		} else {
			inputStream = new FileInputStream(file);
		}
		return inputStream;
	}

	public static void searchAllNotIgnoreFile(File dir, List<File> collector) throws IOException {
		collector.add(dir);
		if ((!dir.isHidden() && dir.isDirectory()) && !isIgnoreFile(dir)) {
			File[] subFiles = dir.listFiles();
			for (int i = 0; i < subFiles.length; i++) {
				searchAllNotIgnoreFile(subFiles[i], collector);
			}
		}
	}

	public static File mkdir(String dir, String file) {
		if (dir == null)
			throw new IllegalArgumentException("dir must be not null");
		File result = new File(dir, file);
		parnetMkdir(result);
		return result;
	}

	public static void parnetMkdir(File outputFile) {
		if (outputFile.getParentFile() != null) {
			outputFile.getParentFile().mkdirs();
		}
	}

	public static List<String> ignoreList = new ArrayList<String>();
	static {
		ignoreList.add(".svn");
		ignoreList.add("CVS");
		ignoreList.add(".cvsignore");
		ignoreList.add(".copyarea.db"); // ClearCase
		ignoreList.add("SCCS");
		ignoreList.add("vssver.scc");
		ignoreList.add(".DS_Store");
		ignoreList.add(".git");
		ignoreList.add(".gitignore");
	}

	private static boolean isIgnoreFile(File file) {

		for (int i = 0; i < ignoreList.size(); i++) {
			if (file.getName().equals(ignoreList.get(i))) {
				return true;
			}
		}
		return false;
	}

	public static Set<String> binaryExtentionsList = new HashSet<String>();

	/** 检查文件是否是二进制文件 */
	public static boolean isBinaryFile(File file) {
		if (file.isDirectory())
			return false;
		return isBinaryFile(file.getName());
	}

	public static boolean isBinaryFile(String filename) {
		if (StringUtils.isBlank(getExtension(filename)))
			return false;
		return binaryExtentionsList.contains(getExtension(filename).toLowerCase());
	}

	public static String getExtension(String filename) {
		if (filename == null) {
			return null;
		}
		int index = filename.indexOf(".");
		if (index == -1) {
			return "";
		} else {
			return filename.substring(index + 1);
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * Deletes a directory recursively.
	 * 
	 * @param directory
	 *            directory to delete
	 * @throws IOException
	 *             in case deletion is unsuccessful
	 */
	public static void deleteDirectory(File directory) throws IOException {
		if (!directory.exists()) {
			return;
		}

		cleanDirectory(directory);
		if (!directory.delete()) {
			String message = "Unable to delete directory " + directory + ".";
			throw new IOException(message);
		}
	}

	/**
	 * Deletes a file, never throwing an exception. If file is a directory,
	 * delete it and all sub-directories.
	 * <p>
	 * The difference between File.delete() and this method are:
	 * <ul>
	 * <li>A directory to be deleted does not have to be empty.</li>
	 * <li>No exceptions are thrown when a file or directory cannot be deleted.</li>
	 * </ul>
	 * 
	 * @param file
	 *            file or directory to delete, can be <code>null</code>
	 * @return <code>true</code> if the file or directory was deleted, otherwise
	 *         <code>false</code>
	 * 
	 * @since Commons IO 1.4
	 */
	public static boolean deleteQuietly(File file) {
		if (file == null) {
			return false;
		}
		try {
			if (file.isDirectory()) {
				cleanDirectory(file);
			}
		} catch (Exception e) {
		}

		try {
			return file.delete();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Cleans a directory without deleting it.
	 * 
	 * @param directory
	 *            directory to clean
	 * @throws IOException
	 *             in case cleaning is unsuccessful
	 */
	public static void cleanDirectory(File directory) throws IOException {
		if (!directory.exists()) {
			String message = directory + " does not exist";
			throw new IllegalArgumentException(message);
		}

		if (!directory.isDirectory()) {
			String message = directory + " is not a directory";
			throw new IllegalArgumentException(message);
		}

		File[] files = directory.listFiles();
		if (files == null) { // null if security restricted
			throw new IOException("Failed to list contents of " + directory);
		}

		IOException exception = null;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			try {
				forceDelete(file);
			} catch (IOException ioe) {
				exception = ioe;
			}
		}

		if (null != exception) {
			throw exception;
		}
	}

	public static void forceDelete(File file) throws IOException {
		if (file.isDirectory()) {
			deleteDirectory(file);
		} else {
			boolean filePresent = file.exists();
			if (!file.delete()) {
				if (!filePresent) {
					throw new FileNotFoundException("File does not exist: " + file);
				}
				String message = "Unable to delete file: " + file;
				throw new IOException(message);
			}
		}
	}

	/**
	 * Delete the supplied {@link File} - for directories,
	 * recursively delete any nested directories or files as well.
	 * @param root the root <code>File</code> to delete
	 * @return <code>true</code> if the <code>File</code> was deleted,
	 * otherwise <code>false</code>
	 */
	public static boolean deleteRecursively(File root) {
		if (root != null && root.exists()) {
			if (root.isDirectory()) {
				File[] children = root.listFiles();
				if (children != null) {
					for (File child : children) {
						deleteRecursively(child);
					}
				}
			}
			return root.delete();
		}
		return false;
	}

	/**
	 * Recursively copy the contents of the <code>src</code> file/directory
	 * to the <code>dest</code> file/directory.
	 * @param src the source directory
	 * @param dest the destination directory
	 * @throws IOException in the case of I/O errors
	 */
	public static void copyRecursively(File src, File dest) throws IOException {
		Assert.isTrue(src != null && (src.isDirectory() || src.isFile()), "Source File must denote a directory or file");
		Assert.notNull(dest, "Destination File must not be null");
		doCopyRecursively(src, dest);
	}

	/**
	 * Actually copy the contents of the <code>src</code> file/directory
	 * to the <code>dest</code> file/directory.
	 * @param src the source directory
	 * @param dest the destination directory
	 * @throws IOException in the case of I/O errors
	 */
	private static void doCopyRecursively(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			dest.mkdir();
			File[] entries = src.listFiles();
			if (entries == null) {
				throw new IOException("Could not list files in directory: " + src);
			}
			for (File entry : entries) {
				doCopyRecursively(entry, new File(dest, entry.getName()));
			}
		} else if (src.isFile()) {
			try {
				dest.createNewFile();
			} catch (IOException ex) {
				IOException ioex = new IOException("Failed to create file: " + dest);
				ioex.initCause(ex);
				throw ioex;
			}
			copy(src, dest);
		} else {
			// Special File handle: neither a file not a directory.
			// Simply skip it when contained in nested directory...
		}
	}

}
