package hussachai.assignment.textreplacer;

import java.io.File;
import java.nio.charset.Charset;


/**
 * 
 * This is the simple option parser. Java standard library does not contain the option parser.
 * However, There are a lot of third party for this kind of job but I decided not to include
 * the external library for the super portable purpose. Thus, I created the manual command line
 * parser here. It's not general purpose options parser because I don't have time and the 
 * question does not require me to do so.
 *   
 * @author hussachai
 *
 */
public class OptionsParser {
	
	/**
	 * 
	 * @author hussachai
	 *
	 */
	public static class Options extends ConfigParam {
		private File targetFile;
		private File fileLog;
		private String search;
		private String replacement;
		public File getTargetFile() {
			return targetFile;
		}
		public void setTargetFile(File targetFile) {
			this.targetFile = targetFile;
		}
		public File getFileLog() {
			return fileLog;
		}
		public void setFileLog(File fileLog) {
			this.fileLog = fileLog;
		}
		public String getSearch() {
			return search;
		}
		public void setSearch(String search) {
			this.search = search;
		}
		public String getReplacement() {
			return replacement;
		}
		public void setReplacement(String replacement) {
			this.replacement = replacement;
		}
	}
	
	/**
	 * 
	 * @author hussachai
	 *
	 */
	public static class ParseException extends RuntimeException{
		private static final long serialVersionUID = 1L;

		public ParseException() {
			super();
		}

		public ParseException(String message, Throwable cause) {
			super(message, cause);
		}

		public ParseException(String message) {
			super(message);
		}

		public ParseException(Throwable cause) {
			super(cause);
		}
	}
	
	/**
	 * The key method for parsing command line arguments
	 * 
	 * @param args
	 * @return {@link Options}
	 */
	public static Options parse(String[] args) {
		if(args.length==0){
			System.out.println("TextReplacer by Hussachai Puripunpinyo");
			System.out.println("=========================================");
			System.out.println("Interactive mode:");
			System.out.println("java jar textreplacer-${version}.jar -i");
			System.out.println("Batch mode:");
			System.out.println("java jar textreplacer-${version}.jar [OPTION]...");
			System.out.println("Mandatory options");
			System.out.println("-f\"filename\"\t\t\tSpecify the target file or directory");
			System.out.println("-s\"text or pattern\"\t\tText or pattern that is used for matching");
			System.out.println("-r\"text or pattern\"\t\tText or pattern that is used for replacing the matching phrases");
			
			System.out.println("Other options");
			System.out.println("-R\t\t\t\tSearch and replace will perform on specified directory recursively");
			System.out.println("-L\"logname\"\t\t\tSpecify the modified file log");
			System.out.println("-p[0|1]\t\t\t\t-p0 for literal search, -p1 for pattern search");
			System.out.println("-b\t\t\t\tBackup the original file if it's modified");
			System.out.println("-b\"backupSuffix\"\t\tChange the default backup suffix (.bak) value");
			System.out.println("-F\"fileFilterList\"\t\tThe default value is *.* You can set multiple filters using semi-colon\n\t\t\t\t to separate each one. Ex. -F\"*.txt;*.csv;*.xml\"");
			System.out.println("--encoding encoding\t\tChange the default file encoding which is UTF-8 to a desired file encoding");
			System.out.println("--terminateOnError\t\tTerminate the program if it detects error");
		}
		Options opts = new Options();
		for(int i=0;i<args.length;i++){
			String arg = args[i];
			String value = null;
			if(arg.startsWith("-f")){
				value = arg.substring(2);
				File targetFile = new File(value);
				if(!targetFile.exists()){
					throw new ParseException("Target file:"+targetFile
							+" does not exist");
				}
				opts.setTargetFile(targetFile);
			}else if("-R".equals(arg)){
				opts.setRecursive(true);
			}else if(arg.startsWith("-L")){
				value = arg.substring(2);
				File fileLogFile = new File(value);
				opts.setFileLog(fileLogFile);
			}else if(arg.startsWith("-p")){
				value = arg.substring(2);
				if("0".equals(value)){
					opts.setTextReplacer(new SimpleTextReplacer());
				}else if("1".equals(value)){
					opts.setTextReplacer(new RegExTextReplacer());
				}else{
					throw new ParseException("Unknown value: "+value);
				}
			}else if(arg.startsWith("-s")){
				value = arg.substring(2);
				opts.setSearch(value);
			}else if(arg.startsWith("-r")){
				value = arg.substring(2);
				opts.setReplacement(value);
			}else if("-b".equals(arg)){
				opts.setBackup(true);
				value = getArgValue(args, i+1);
				if(value!=null){
					opts.setBackupSuffix(value);
				}
			}else if(arg.startsWith("-F")){
				value = arg.substring(2);
				opts.setFileFilter(value);
			}else if("--encoding".equals(arg)){
				value = getArgValue(args, i+1);
				if(Charset.isSupported(value)){
					opts.setFileEncoding(value);
				}else{
					throw new ParseException("Unsupported encoding: "+value);
				}
			}else if("--terminateOnError".equals(arg)){
				opts.setTerminateOnError(true);
			}
			
		}
		return opts;
		
	}
	
	/**
	 * Get the next value in the command line argument. 
	 * @param args
	 * @param idx
	 * @return null if it does not exist or it starts with hyphen (-)
	 */
	private static String getArgValue(String[] args, int idx){
		if(args.length<=idx){
			return null;
		}
		String value = args[idx];
		if(value.startsWith("-")){
			return null;
		}
		return value;
	}
	
	/**
	 * 
	 * @param input
	 * @return true if the input is y or yes (case insensitive)
	 */
	public static boolean isYes(String input){
		if("y".equalsIgnoreCase(input) || "yes".equalsIgnoreCase(input)){
			return true;
		}
		return false;
	}
	
	/**
	 *   
	 * @param input
	 * @return true if the input is n or no (case insensitive)
	 */
	public static boolean isNo(String input){
		if("n".equalsIgnoreCase(input) || "no".equalsIgnoreCase(input)){
			return true;
		}
		return false;
	}
	
	
}
