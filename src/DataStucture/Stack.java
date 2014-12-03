package DataStucture;

public class Stack {
	private int[] stack;
	private int topIndex = -1;
	private int stackSize;
	
	//Construct new stack with default size.
	public Stack (int size){
		stack = new int[size];
		stackSize = size;
	}
	
	/*SOME FUNCTIONS FOR OUR STACKS*/
	
	//Print the item in the stack.
	public void print(){
		for (int i = 0; i <= topIndex; i++)
		{
			System.out.print(stack[i] + " " );
		}
		System.out.println();
	}
	
	//Check to see if the stack is empty
	private boolean emptyStack(){
		if(topIndex == -1){
			System.out.println("The stack is empty.");
			return true;
		}
		else 
			return false;
	}
	
	//Check to see if the stack is full.
	private boolean fullStack(){
		if(topIndex == (stackSize - 1)){
			System.out.println("The stack is full.");
			return true;
		}
		else 
			return false;
	}
	
	//Push item to the top of the list
	public void push(int element){
		if(!fullStack()){
			stack[topIndex + 1] = element;
			topIndex++;
		}
	}
	
	//Display top item of the list.
	public int top(){
		if(!emptyStack())
			return stack[topIndex];
		else
			return -1;
	}
	
	//Display item on the top of the list and remove this item.
	public void pop(){
		if(!emptyStack()){		
			System.out.println("The top item(to be removed) is: " + stack[topIndex]);
			topIndex--;
		}
	}
	
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
