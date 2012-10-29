package hussachai.assignment.textreplacer;

import hussachai.assignment.textreplacer.RegExTextReplacer;
import hussachai.assignment.textreplacer.Result;
import hussachai.assignment.textreplacer.TextReplacer;
import junit.framework.Assert;

import org.junit.Test;

public class RegExTextReplacerTest {

	
	@Test
	public void testGeneralCases(){
		TextReplacer textReplacer = new RegExTextReplacer();
		Result result = textReplacer.replaceAll("hello how are you?", "[hH]", "J");
		Assert.assertEquals("Jello Jow are you?", result.getOutput());
		Assert.assertEquals(2, result.getOccurrences());
		Assert.assertTrue(result.isModified());
	}
	
	
}
