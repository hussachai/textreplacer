package hussachai.assignment.textreplacer;

import hussachai.assignment.textreplacer.OptionsParser.Options;
import hussachai.assignment.textreplacer.logging.JdkLoggerFactory;
import hussachai.assignment.textreplacer.logging.Logger;
import hussachai.assignment.textreplacer.utils.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;



/**
 * 
 * This is the main class of TextReplacer application. This program can be run in different mode -
 * interactive mode and batch mode. The interactive mode is recommended mode for human because
 * it's more intuitive than the batch mode. However, we need batch mode in scheduled task or 
 * programming task. 
 * In this version, program has 2 implementations of pattern matcher. One for literal and another one
 * for regular expression but we can add more implementation later due to flexible design of this program.
 * 
 * @author hussachai
 * 
 */
public class TextReplacerMain {
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final Logger log = JdkLoggerFactory.getLogger(TextReplacer.class);
	private static final int FILE_LOG_FLUSH = 5;//flush every N times
	
	private BufferedWriter modifiedFileLog;
	private int modifiedFiles = 0; //number of modified files
	private File fileLog;
	private int readFiles = 0; //number of read files
	
	private long t1, t2;
	
	private TextReplacerMain(){}
	
	/**
	 * If the modified file log is set, this method must be called first before updating any files.
	 * @param file
	 */
	private void openModifiedFileLog(File file){
		this.fileLog = file;
		try{
			modifiedFileLog = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), DEFAULT_ENCODING));
			modifiedFileLog.append("file\toriginalSize\tmodifiedSize\toccurrences").append(LINE_SEPARATOR);
		}catch(IOException e){
			log.error(e, "Unable to create file log");
		}
	}
	
	/**
	 * If the modified file log is set, this method must be called last before program is going to exit.
	 */
	private void closeModifiedFileLog(){
		if(modifiedFileLog!=null){
			try{
				modifiedFileLog.flush();
				modifiedFileLog.close();
			}catch(IOException e){
				log.error(e, "Fail to close file log");
			}
		}
	}
	
	/**
	 * Print info such as start time, 
	 * @param param
	 */
	private void printStartInfo(ConfigParam param){
		log.info("=============== START =====================");
		log.info("Start searching at ", new Date());
		log.info("Text Replacer Impl: ", param.getTextReplacer().getClass().getName());
		log.info("File encoding: ", param.getFileEncoding());
		log.info("Recursive: ", param.isRecursive());
		log.info("File filter: ", param.getFileFilter());
		log.info("Backup: ", param.isBackup());
		log.info("Backup suffix: ", param.getBackupSuffix());
		log.info("Terminate on error: ", param.isTerminateOnError());
		log.info("^^^^^^^^^^^^^^^ START ^^^^^^^^^^^^^^^^^^^^^");
		t1 = System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @param param
	 */
	private void printFinishInfo(ConfigParam param){
		t2 = System.currentTimeMillis();
		log.info("=============== DONE =====================");
		log.info("Finish processing at: ", new Date());
		log.info("Total read files: ", readFiles);
		log.info("Total modified files: ", modifiedFiles);
		log.info("Elapsed time: ", (t2-t1)/1000, " second(s)");
		log.info("^^^^^^^^^^^^^^^ DONE ^^^^^^^^^^^^^^^^^^^^^");
	}
	/**
	 * 
	 * Replace the text with the replacement in the specified file.
	 * @param file  The target file or directory that will be searched and modified
	 * @param search  The literal or pattern that is used for matching
	 * @param replacement  The literal or pattern that is used for replacing the matching phrase.
	 * @param param  The optional options
	 * @throws Exception
	 * 
	 */
	private void replaceTextInFile(File file, String search, String replacement, ConfigParam param) throws Exception {
		if(file.isDirectory()) throw new IllegalArgumentException("File object must NOT be directory");
		
		//Prevent reading fileLog in case fileLog is created inside processing directory.
		if(fileLog!=null && fileLog.equals(file)){
			log.info("Skip reading file log as an textreplacer's input");
			return;
		}
		//Skip reading backup file
		if(file.getName().endsWith(param.getBackupSuffix())){
			log.debug("Skip reading backup file: ", file);
			return;
		}
		readFiles++;
		log.info("Reading file: ", file);
		log.debug("Searching for: ", search);
		log.debug("Replacing with: ", replacement);
		
		TextReplacer textReplacer = param.getTextReplacer();
		try{
			//read file to string
			String data = IOUtils.readFileToString(file, param.getFileEncoding());
			Result result = textReplacer.replaceAll(data, search, replacement);
			if(result.isModified()){
				if(param.isBackup()){
					File bakFile = new File(file.getAbsolutePath()+param.getBackupSuffix());
					log.debug("Creating backup file to: ", bakFile);
					IOUtils.copyFile(file, bakFile);
				}
				
				long originalSize = file.length();
				IOUtils.writeStringToFile(file, result.getOutput(), param.getFileEncoding());
				long modifiedSize = file.length(); 
				modifiedFiles++;
				log.info(result.getOccurrences(), " occurrences have been replaced.");
				if(modifiedFileLog!=null){
					modifiedFileLog.append(file.getAbsolutePath())
					.append("\t").append(String.valueOf(originalSize))
					.append("\t").append(String.valueOf(modifiedSize))
					.append("\t").append(String.valueOf(result.getOccurrences()))
					.append(LINE_SEPARATOR);
					if(modifiedFiles%FILE_LOG_FLUSH==0){
						modifiedFileLog.flush();
					}
				}
			}
		}catch(IOException e){
			log.error(e, e.getMessage());
			if(param.isTerminateOnError()) throw e;
		}catch(Exception e){
			log.error(e, "Unexpected error");
			if(param.isTerminateOnError()) throw e;
		}
	}
	
	/**
	 * Read file in directory. If there are more than one file, read all files that match the pre-defined file filter.
	 * Read sub-directory if the recursive flag is true. This method is used for directory traversal only.
	 * It does not perform file update but it delegate the update task to {@link TextReplacerMain#replaceTextInFile(File, String, String, ConfigParam)}
	 * @param dir
	 * @param search
	 * @param replacement
	 * @param param
	 * @throws Exception
	 */
	private void replaceTextInDir(File dir, String search, String replacement, ConfigParam param) throws Exception{
		if(!dir.isDirectory()) throw new IllegalArgumentException("File object must be directory");
		
		for(File file: dir.listFiles(param.getFileFilter())){
			if(file.isDirectory()){
				if(param.isRecursive()){
					replaceTextInDir(file, search, replacement, param);
				}
			}else{
				replaceTextInFile(file, search, replacement, param);
			}
		}
	}
	
	/**
	 * Interactive mode is the mode that prompt user a question and wait for the answer.
	 * It's more intuitive than batch mode but it need user interaction. Thus, this mode
	 * is not suitable for programming or scheduled task.
	 * 
	 * @throws IOException
	 */
	public void runInInteractiveMode() throws IOException {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		File targetFile = null;
		ConfigParam param = new ConfigParam();
		
		//loop until the value is satisfied.
		do{
			System.out.print("Target directory/file: ");
			targetFile = new File(console.readLine());
			if(!targetFile.exists()){
				System.out.println("File does not exist! Please enter file path again.");
			}
		}while(!targetFile.exists());
		
		if(targetFile.isDirectory()){
			System.out.println("Note: Target file is directory");
			while(true){
				System.out.print("Read all files in subdirectory (recursive)? [yes|no] yes: ");
				input = console.readLine().trim();
				if("".equals(input) || OptionsParser.isYes(input)){
					param.setRecursive(true);
					break;
				}else if(OptionsParser.isNo(input)){
					param.setRecursive(false);
					break;
				}else{
					continue;
				}
			}
			
			System.out.print("File filter? (default: *.*) Ex. *.txt;*.csv;*.xml : ");
			input = console.readLine().trim();
			if(!"".equals(input)){
				param.setFileFilter(input);
			}
		}
		
		while(true){
			System.out.print("Backup file before overwriting? [yes|no] yes: ");
			input = console.readLine().trim();
			if("".equals(input) || OptionsParser.isYes(input)){
				param.setBackup(true);
				break;
			}else if(OptionsParser.isNo(input)){
				param.setBackup(false);
				break;
			}else{
				continue;
			}
		}
		
		while(true){
			System.out.print("Create modified file log? [yes|no] yes: ");
			input = console.readLine().trim();
			if("".equals(input) || OptionsParser.isYes(input)){
				System.out.print("file log path: ");
				File logFile = new File(console.readLine().trim());
				openModifiedFileLog(logFile);
				break;
			}else if(OptionsParser.isNo(input)){
				break;
			}else{
				continue;
			}
		}
		
		System.out.println("Note: Searching by text performs faster and consumes lesser memory than searching by pattern.");
		while(true){
			System.out.print("Search by text(0) or pattern(1) [0|1] 0: ");
			input = console.readLine().trim();
			if("".equals(input) || "0".equals(input)){
				param.setTextReplacer(new SimpleTextReplacer());
				break;
			}else if("1".equals(input)){
				param.setTextReplacer(new RegExTextReplacer());
				break;
			}else{
				continue;
			}
		}
		
		
		String search = null, replacement = null;
		
		if(param.getTextReplacer() instanceof SimpleTextReplacer)
			System.out.print("Search text:");
		else
			System.out.print("Search pattern: ");
		search = console.readLine().trim();
		
		System.out.print("Replace with:");
		replacement = console.readLine().trim();
		
		printStartInfo(param);
		try{
			if(targetFile.isDirectory()){
				replaceTextInDir(targetFile, search.toString(), replacement.toString(), param);
			}else{
				replaceTextInFile(targetFile, search.toString(), replacement.toString(), param);
			}
		}catch(Exception e){
			log.fatal(e, "Unable to continue running due to "+e.getMessage());
		}finally{
			closeModifiedFileLog();
			printFinishInfo(param);
		}
	}
	
	/**
	 * Batch mode is the command line version of this program. This mode parse
	 * the arguments that receives from the console. With this mode, you can
	 * create batch program or schedule the task easily.
	 * 
	 * @param options
	 */
	public void runInBatchMode(Options options){
		
		if(options.getTargetFile()==null){
			log.fatal("-f\"targetFile\" is required");
			System.exit(1);
		}
		if(options.getSearch()==null){
			log.fatal("-s\"searchString\" is required");
			System.exit(1);
		}
		if(options.getReplacement()==null){
			log.fatal("-r\"replaceString\" is required");
			System.exit(1);
		}

		printStartInfo(options);
		try{
			if(options.getFileLog()!=null){
				openModifiedFileLog(options.getFileLog());
			}
			if(options.getTargetFile().isDirectory()){
				replaceTextInDir(options.getTargetFile(), options.getSearch(), options.getReplacement(), options);
			}else{
				replaceTextInFile(options.getTargetFile(), options.getSearch(), options.getReplacement(), options);
			}
		}catch(Exception e){
			log.fatal(e, "Unable to continue running due to "+e.getMessage());
		}finally{
			closeModifiedFileLog();
			printFinishInfo(options);
		}
	}
	
	/**
	 * Main method
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		TextReplacerMain textReplacer = new TextReplacerMain();
		if(args.length==1 && "-i".equals(args[0])){
			textReplacer.runInInteractiveMode();
		}else{
			try{
				Options options = OptionsParser.parse(args);
				textReplacer.runInBatchMode(options);
			}catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
}
