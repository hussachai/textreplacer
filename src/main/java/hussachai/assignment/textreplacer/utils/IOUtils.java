package hussachai.assignment.textreplacer.utils;

import hussachai.assignment.textreplacer.logging.JdkLoggerFactory;
import hussachai.assignment.textreplacer.logging.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;



/**
 * The general utilities for Input/Output operation.
 * 
 * @author hussachai
 *
 */
public class IOUtils {

	private static final Logger log = JdkLoggerFactory.getLogger(IOUtils.class);
	
	private static final int DEFAULT_BUFFER_SIZE = 1024;
	
	/**
	 * Copy file from source file to destination file. This method is used
	 * for backing up file
	 * @param srcFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(File srcFile, File destFile) throws IOException {
		
		FileInputStream input = new FileInputStream(srcFile);
		try {
			long count = 0;
			int n = 0;
			FileOutputStream output = new FileOutputStream(destFile);
			try {
				byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
				while ((n = input.read(buffer))!=-1) {
					output.write(buffer, 0, n);
					count += n;
				}
			} finally {
				log.debug("Total bytes written : ", count);
				if(output!=null) try{ output.close(); }catch(Exception e){}
			}
			log.debug("File: ", srcFile, " was copied to file: ", destFile);
		} finally {
			if(input!=null) try{ input.close(); }catch(Exception e){}
		}
		
	}
	
	/**
	 * 
	 * @param file
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(File file, String encoding)
			throws IOException {
		FileInputStream finput = new FileInputStream(file);
		StringWriter output = new StringWriter();
		InputStreamReader input = new InputStreamReader(finput, encoding);
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toString();
	}
	
	/**
	 * 
	 * @param file
	 * @param data
	 * @param encoding
	 * @throws IOException
	 */
	public static void writeStringToFile(File file, String data, String encoding) throws IOException{
		FileOutputStream foutput = new FileOutputStream(file);
		foutput.write(data.getBytes(encoding));
	}
	
	
}
