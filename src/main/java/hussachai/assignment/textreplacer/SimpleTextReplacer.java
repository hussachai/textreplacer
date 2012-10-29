package hussachai.assignment.textreplacer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * This implementation supports literal text matching only.
 * 
 * @author hussachai
 * 
 */
public class SimpleTextReplacer implements TextReplacer {

	@Override
	public Result replaceAll(String input, String search, String replacement) {
		Result result = new Result();
		Matcher matcher = Pattern.compile(search, Pattern.LITERAL).matcher(input);
		matcher.reset();
		boolean found = matcher.find();
		if (found) {
			result.setModified(true);
			result.setOccurrences(result.getOccurrences() + 1);
			StringBuffer sb = new StringBuffer();
			do {
				matcher.appendReplacement(sb,
						Matcher.quoteReplacement(replacement));
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
