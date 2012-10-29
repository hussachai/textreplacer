package hussachai.assignment.textreplacer;

import hussachai.assignment.textreplacer.utils.StringUtils;

import java.io.FileFilter;


/**
 * The ConfigParam object is used for passing the options that is important
 * but it's not directly relate to the search and replace semantic.
 * @author hussachai
 *
 */
public class ConfigParam {
	
	/**
	 * Default file encoding is UTF-8
	 */
	private String fileEncoding = TextReplacerMain.DEFAULT_ENCODING;
	/**
	 * The recursive property is effective on directory traversal
	 */
	private boolean recursive = true;
	
	/**
	 * @see {@link WildCardFileFilter#setFilters(String)}
	 */
	private FileFilter fileFilter = new WildCardFileFilter("*.*");
	/**
	 * The implementation of <code>{@link TextReplacer}</code>
	 */
	private TextReplacer textReplacer = new SimpleTextReplacer();
	/**
	 * If this flag is true, the program will create backup file in case file is modified.
	 */
	private boolean backup = true;
	/**
	 * The additional extension to indicate the file that it is the copy of the original one.  
	 */
	private String backupSuffix = ".bak";
	/**
	 * If this flag is true, the program will be terminated when error occurs.
	 */
	private boolean terminateOnError = false;
	
	public String getFileEncoding() {
		return fileEncoding;
	}
	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}
	public boolean isRecursive() {
		return recursive;
	}
	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}
	public FileFilter getFileFilter(){
		return fileFilter;
	}
	public void setFileFilter(String filterTexts) {
		if(this.fileFilter instanceof WildCardFileFilter){
			((WildCardFileFilter)this.fileFilter).setFilters(filterTexts);
		}
	}
	public TextReplacer getTextReplacer() {
		return textReplacer;
	}
	public void setTextReplacer(TextReplacer textReplacer) {
		this.textReplacer = textReplacer;
	}
	public boolean isBackup() {
		return backup;
	}
	public void setBackup(boolean backup) {
		this.backup = backup;
	}
	public String getBackupSuffix() {
		return backupSuffix;
	}
	public void setBackupSuffix(String backupSuffix) {
		if(StringUtils.isBlank(backupSuffix)){
			throw new IllegalArgumentException("Backup suffix may not be empty");
		}
		this.backupSuffix = backupSuffix;
	}
	public boolean isTerminateOnError() {
		return terminateOnError;
	}
	public void setTerminateOnError(boolean terminateOnError) {
		this.terminateOnError = terminateOnError;
	}
	
}
