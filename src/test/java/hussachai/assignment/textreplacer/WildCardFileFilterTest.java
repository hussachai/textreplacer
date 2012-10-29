package hussachai.assignment.textreplacer;

import hussachai.assignment.textreplacer.WildCardFileFilter;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

public class WildCardFileFilterTest {
	
	@Test
	public void testAlwaysTrue1(){
		WildCardFileFilter wcf = new WildCardFileFilter();
		wcf.setFilters("*");
		Assert.assertTrue(wcf.accept(new File("no extension")));
		Assert.assertTrue(wcf.accept(new File("hello2.pdf")));
		Assert.assertTrue(wcf.accept(new File("dot.dot.dot.pdf")));
		Assert.assertTrue(wcf.accept(new File("space space .dot.pdf")));
	}
	
	@Test
	public void testAlwaysTrue2(){
		WildCardFileFilter wcf = new WildCardFileFilter();
		wcf.setFilters("*.*");
		Assert.assertTrue(wcf.accept(new File("no extension")));
		Assert.assertTrue(wcf.accept(new File("hello2.pdf")));
		Assert.assertTrue(wcf.accept(new File("dot.dot.dot.pdf")));
		Assert.assertTrue(wcf.accept(new File("space space .dot.pdf")));
	}
	
	@Test
	public void testGeneralCases(){
		
		WildCardFileFilter wcf = new WildCardFileFilter();
		wcf.setFilters("*.pdf;*.txt;hello.*");
		Assert.assertTrue(wcf.accept(new File("hello2.pdf")));
		Assert.assertTrue(wcf.accept(new File("dot.dot.dot.pdf")));
		Assert.assertTrue(wcf.accept(new File("space space .dot.pdf")));
		
		Assert.assertTrue(wcf.accept(new File("hello2.txt")));
		Assert.assertTrue(wcf.accept(new File("dot.dot.dot.txt")));
		Assert.assertTrue(wcf.accept(new File("space space .dot.txt")));
		
		Assert.assertTrue(wcf.accept(new File("hello.yes")));
		Assert.assertFalse(wcf.accept(new File("hello2.no"))); //FALSE
		
	}
	
	@Test
	public void testIncompletedFilter(){
		WildCardFileFilter wcf = new WildCardFileFilter();
		wcf.setFilters("*.pdf;");
		Assert.assertTrue(wcf.accept(new File("hello2.pdf")));
		Assert.assertTrue(wcf.accept(new File("dot.dot.dot.pdf")));
		Assert.assertTrue(wcf.accept(new File("space space .dot.pdf")));
	}
}
