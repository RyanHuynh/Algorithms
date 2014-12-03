package DataStucture;

public class Queue {
	private int[] queue;
	private int queueSize;
	private int count = 0;
	private int front;
	private int rear;
	
	//Construct a new queue with default size.
	public Queue(int size)
	{
		//Add extra slot in the array to make sure rear and front cannot meet when the queue is not empty.
		queue = new int[size + 1];
		front = 0;
		rear = 0;
		queueSize = size;
	}
	
	/*SOME BASIC FUNCTIONS FOR OUR QUEUE*/
	
	//Print the item in the queue.
	public void print(){
		int tempCount = count;
		int tempFront = front;
		while(tempCount > 0){
			System.out.print(queue[tempFront] + " ");
			tempFront = (tempFront + 1)%(queueSize + 1);
			tempCount--;
		}
		System.out.println();				
	}
	
	//Check to see if the queue is empty. NOTE: if you didn't want to add extra slot in array, you can check for full array 1st here before comparing rear and front.
	private boolean emptyQueue(){
		if(front == rear){
			System.out.println("The queue is empty.");
			return true;
		}
		else
			return false;
	}
	
	//Check to see if the queue is full
	private boolean fullQueue(){
		if(count == queueSize){
			System.out.println("The Queue is full.");
			return true;
		}
		else
			return false;			
	}
	
	
	//Insert new item to the rear of the queue.
	public void enqueue(int element){
		if(!fullQueue()){
			queue[rear] = element;
			rear = (rear + 1)%(queueSize + 1);
			count++;
		}			
	}
	
	//Display front element.
	public int front(){
		if(!emptyQueue())
			return queue[front];
		else
			return -1;
	}
	
	//Display front element and remove it from queue
	public void dequeue()
	{
		if(!emptyQueue()){
			System.out.println("The front element (to be removed) is: " + queue[front] );
			front = (front + 1)%(queueSize + 1);
			count--;
		}
	}
	
	//TESTING
	public static void main(String[] args){
		Queue newQueue = new Queue(5);
		
		//Add 5 elements
		newQueue.enqueue(2);
		newQueue.enqueue(5);
		newQueue.enqueue(1);
		newQueue.enqueue(7);
		newQueue.enqueue(9);
		newQueue.print();
		
		//Try to add another one. should be full now
		newQueue.enqueue(10);
		
		//Remove one element 
		newQueue.dequeue();
		
		//Display the front element
		System.out.println("The top element is: " + newQueue.front() );
		
		//Display result
		newQueue.print();
		
		//Try to add another one again, should be fine now
		newQueue.enqueue(10);
		newQueue.print();
		
		//Remove the remain queue.
		newQueue.dequeue();
		newQueue.dequeue();
		newQueue.dequeue();
		newQueue.dequeue();
		newQueue.dequeue();
		
		//remove Empty queue should yield error.
		newQueue.dequeue();
	}
	
}
