package hussachai.assignment.textreplacer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * This implementation supports regular expression matching.
 * 
 * @author hussachai
 * 
 */
public class RegExTextReplacer implements TextReplacer {

	@Override
	public Result replaceAll(String input, String search, String replacement) {
		Result result = new Result();
		Matcher matcher = Pattern.compile(search).matcher(input);
		matcher.reset();
		boolean found = matcher.find();
		if (found) {
			result.setModified(true);
			result.setOccurrences(result.getOccurrences() + 1);
			StringBuffer sb = new StringBuffer();
			do {
				matcher.appendReplacement(sb, replacement);
				found = matcher.find();
				if (found)
					result.setOccurrences(result.getOccurrences() + 1);
			} while (found);
			matcher.appendTail(sb);
			result.setOutput(sb.toString());
		}
		return result;
	}

}
