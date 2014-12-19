package DataStructure;

/** Stack data structure (FILO) implementation using int[].
 * 
 * @author Ryan Huynh
 * @version Dec 2014
 *
 */
public class Stack {
	private int[] stack;
	private int topIndex = -1;
	private int stackSize;
	
	/**
	 * Instantiates a new stack.
	 *
	 * @param size the default size of the stack.
	 */
	public Stack (int size){
		stack = new int[size];
		stackSize = size;
	}
	
	/**SOME FUNCTIONS FOR OUR STACKS*/
	
	/**
	 * Display the keys in the stack.
	 */
	public void print(){
		for (int i = 0; i <= topIndex; i++)
		{
			System.out.print(stack[i] + " " );
		}
		System.out.println();
	}
	
	/**
	 * Check to see if the stack is empty.
	 *
	 * @return true if it is empty, false otherwise.
	 */
	private boolean emptyStack(){
		if(topIndex == -1){
			System.out.println("The stack is empty.");
			return true;
		}
		else 
			return false;
	}
	
	/**
	 * Check to see if the stack is full.
	 *
	 * @return true if it is empty, false otherwise.
	 */
	private boolean fullStack(){
		if(topIndex == (stackSize - 1)){
			System.out.println("The stack is full.");
			return true;
		}
		else 
			return false;
	}
	
	/**
	 * Push new key to the top of the stack.
	 *
	 * @param element the element
	 */
	public void push(int key){
		if(!fullStack()){
			stack[topIndex + 1] = key;
			topIndex++;
		}
	}
	
	/**
	 * Return the top key of the stack
	 *
	 * @return key
	 */
	public int top(){
		if(!emptyStack())
			return stack[topIndex];
		else
			return -1;
	}
	
	/**
	 * Display the top key of the stack then remove it from the stack
	 */
	public void pop(){
		if(!emptyStack()){		
			System.out.println("The top item(to be removed) is: " + stack[topIndex]);
			topIndex--;
		}
	}
	
	/**TESTING*/
	public static void main (String[] args){
		Stack newStack = new Stack(5);		
		
		//Add 5 numbers
		newStack.push(5);
		newStack.push(1);
		newStack.push(4);
		newStack.push(6);
		newStack.push(7);
		newStack.print();
		
		//Add another number, the list should be full now.
		newStack.push(5);
		
		//Display top item now
		System.out.println("Top item now is :" + newStack.top());
		
		//Remove top item 
		newStack.pop();
		
		//Print result.
		newStack.print();
	}
}
