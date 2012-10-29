package hussachai.assignment.textreplacer;

import hussachai.assignment.textreplacer.utils.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;


/**
 * This is the simple version of wild card file filter. 
 * This class is used for filter out the unwanted files. It's very important
 * for selective processing. 
 * @author hussachai
 *
 */
public class WildCardFileFilter implements FileFilter{

	private List<SimpleMatcher> filters;
	
	private String filterText;
	
	public WildCardFileFilter(){}
	
	public WildCardFileFilter(String filterTexts){
		setFilters(filterTexts);
	}
	
	@Override
	public String toString(){
		return filterText;
	}
	/**
	 * Set the filter rules for WildCardFileFilter.<br/>
	 * <b>Example:</b> *.txt;*.csv;*.pad<br/>
	 * Separate each filter with semicolon (;)<br/>
	 * You can use wild card on the file name part and the extension part.
	 * @param filterTexts
	 */
	public void setFilters(String filterText) {
		
		this.filterText = filterText;
		filters = new ArrayList<SimpleMatcher>();
		
		String patterns[] = filterText.split(";");
		for(String pattern: patterns){
			if(StringUtils.isBlank(pattern)) continue;
			int delimIdx = pattern.lastIndexOf(".");
			if(delimIdx!=-1){
				String name = pattern.substring(0, delimIdx);
				String ext = pattern.substring(delimIdx+1, pattern.length());
				filters.add(new SimpleMatcher(name, ext));
			}else{
				filters.add(new SimpleMatcher(pattern, null));
			}
		}
	}
	
	
	@Override
	public boolean accept(File file) {
		
		if(file.isDirectory()) return true;
		
		for(SimpleMatcher matcher: filters){
			if(matcher.matches(file.getName())) return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @author hussachai
	 *
	 */
	static class SimpleMatcher {
		
		private String name;
		private String ext;
		public SimpleMatcher(String name, String ext){
			this.name = name;
			this.ext = ext;
		}
		public boolean matches(String filename){
			if("*".equals(name) && (ext==null || "*".equals(ext)) ){
				return true;
			}
			int delimIdx = filename.lastIndexOf(".");
			if(delimIdx!=-1){
				String part1 = filename.substring(0, delimIdx);
				String part2 = filename.substring(delimIdx+1, filename.length());
				boolean matcher = false;
				if("*".equals(name) || name.equals(part1)){
					matcher = true;
				}
				if(!"*".equals(ext) && (ext!=null && !ext.equals(part2)) ){
					matcher = false;
				}
				return matcher;
			}else{
				//file extension not found
				//matches only filename
				if(ext!=null && !"*".equals(ext)) return false;
				if("*".equals(name)) return true;
				if(name.equals(filename)) return true;
			}
			return false;
		}
	}
	
}
