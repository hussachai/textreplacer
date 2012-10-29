package hussachai.assignment.textreplacer;

import hussachai.assignment.textreplacer.Result;
import hussachai.assignment.textreplacer.SimpleTextReplacer;
import hussachai.assignment.textreplacer.TextReplacer;
import junit.framework.Assert;

import org.junit.Test;

public class SimpleTextReplacerTest {

	
	@Test
	public void testGeneralCases(){
		TextReplacer textReplacer = new SimpleTextReplacer();
		Result result = textReplacer.replaceAll("[h]ello [h]ow are you?", "[h]", "H");
		Assert.assertEquals("Hello How are you?", result.getOutput());
		Assert.assertEquals(2, result.getOccurrences());
		Assert.assertTrue(result.isModified());
	}
}
