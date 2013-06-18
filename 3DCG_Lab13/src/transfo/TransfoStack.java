package transfo;

import java.util.Stack;

public class TransfoStack {
	private Stack<Transfo> stack = new Stack<Transfo>();
	
	public TransfoStack(){
		stack.add(new Transfo());
	}
	
	public Transfo peek(){
		return this.stack.peek(); 
	}
	
	public void pop(){
		this.stack.pop();
	}
	
	public void push(){
		this.stack.push(new Transfo(this.stack.peek())); 
	}
	
	public void transform(Transfo transfo){
		this.peek().transform(transfo);
	}
	
	public Stack<Transfo> getStack(){
		return this.stack;
	}
}
