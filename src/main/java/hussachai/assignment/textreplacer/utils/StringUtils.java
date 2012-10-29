package hussachai.assignment.textreplacer.utils;

/**
 * 
 * The general purpose String manipulation utilities.
 * @author hussachai
 *
 */
public final class StringUtils {
	
	public static final String NULL_STRING = "null";
	
	/**
	 * Parse each object to string and combine them together.
	 * @param values
	 * @return the combined string value
	 */
	public static String combine(Object[] values){
		if(values!=null){
			if(values.length==1){
				return String.valueOf(values[0]);
			}else if(values.length>1){
				StringBuilder buffer = new StringBuilder();
				for(Object value: values){
					buffer.append(String.valueOf(value));
				}
				return buffer.toString();
			}
		}
		return NULL_STRING;
	}
	
	/**
	 * 
	 * @param value
	 * @return true if the String value is null or length==0 
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.length() == 0;
	}
	
	/**
	 * 
	 * @param value
	 * @return true if the String value is null or the length of the trim version==0 
	 */
	public static boolean isBlank(String value) {
		return value == null || value.trim().length()==0;
	}
	
}
