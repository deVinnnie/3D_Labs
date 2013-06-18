package test;

import static org.junit.Assert.*;

import org.junit.Test;

import transfo.Transfo;
import transfo.TransfoStack;

public class TransfoStackTest {
	
	@Test 
	public void test_Peek(){
		TransfoStack transfoStack = new TransfoStack(); 
		assertEquals(new Transfo(), transfoStack.peek());
	}
	
	@Test
	public void test_Push() {
		TransfoStack transfoStack = new TransfoStack(); 
		transfoStack.push(); 
		assertEquals(2, transfoStack.getStack().size()); 
		assertEquals(new Transfo(), transfoStack.peek());
	}
	
	@Test
	public void test_Pop(){
		TransfoStack transfoStack = new TransfoStack(); 
		transfoStack.pop();
		assertEquals(0, transfoStack.getStack().size()); 
	}
}
