/*Implement Heap Structure using ArrayList*/

package DataStructure;

import java.util.ArrayList;

public class Heap {
	private ArrayList<Integer> heap;
	public Heap(){
		heap = new ArrayList<Integer>();
	}
	
	//Add new element to our heap
	public void insert(int value){
		heap.add(value);
		int lastNodeIndex = heap.size() - 1;
		upHeapBubbling(lastNodeIndex);
	}
	
	//Find the index of the parent node
	private int getParentIndex(int childIndex){
		//Since array index start at 0.
		return (childIndex - 1) / 2;
	}
	
	//Up-Heap Bubbling after insertion to make sure Heap qualify the heap property.
	private void upHeapBubbling(int nodeIndex){
		
		//Stop bubbling if the current node reach root.
		if(nodeIndex != 0){			
			int currentValue = heap.get(nodeIndex);
			int parentIndex = getParentIndex(nodeIndex);
			int parentValue = heap.get(parentIndex);
			
			//Compare value of current node and parent node.
			if(currentValue < parentValue){
				int tempValue = currentValue;
				heap.set(nodeIndex, parentValue);
				heap.set(parentIndex, tempValue);
				upHeapBubbling(parentIndex);
			}
		}
	}
	
	//Remove the smallest element of the heap.
	public void removeMin(){
		//First we need to copy the last element to the root and remove the last element.
		//Doing so will guarantee the heap qualify complete binary tree property.
		int lastIndex = heap.size() - 1;
		int value = heap.get(lastIndex);
		heap.set(0, value);
		heap.remove(lastIndex);
		
		//Downheap bubbling from the root.
		downHeapBubbling(0);
	}
	
	//DownHeap Bubbling after removal to make sure the heap qualify heap property.
	private void downHeapBubbling(int nodeIndex){
		//Stop bubbling if reach the leaf node.
		if((nodeIndex * 2 + 1) < heap.size()){
			int currentValue = heap.get(nodeIndex);
			int smallerChildIndex = getSmallestChildIndex(nodeIndex);
			int childValue = heap.get(smallerChildIndex);
			
			//Compare value of current node and child node.
			if(currentValue > childValue){
				int tempValue = currentValue;
				heap.set(nodeIndex, childValue);
				heap.set(smallerChildIndex, tempValue);
				downHeapBubbling(smallerChildIndex);
			}
		}
	}
	
	//Get index of the child that has smaller value
	private int getSmallestChildIndex(int parentIndex){
		int leftChildIndex = (parentIndex * 2) + 1;
		int leftChildValue = heap.get(leftChildIndex);
		
		int rightChildIndex = (parentIndex * 2) + 2;
		//Check if the parent node has right child node.
		int rightChildValue = Integer.MAX_VALUE;
		if(rightChildIndex < heap.size()){
			rightChildValue = heap.get(rightChildIndex);
		}
		//Compare value of the left and right child and return smaller child's index.
		return (leftChildValue < rightChildValue) ? leftChildIndex : rightChildIndex;
	}
	//Print the heap.
	public void print(){
		System.out.print("Our heap is: ");
		for(int i = 0; i < heap.size(); i++){
			System.out.print(heap.get(i) + " ");
		}
		System.out.println();
	}
	
	
	
	//TESTING
	public static void main(String[] args){
		Heap newHeap = new Heap();
		newHeap.insert(6);
		newHeap.insert(7);
		newHeap.insert(8);
		newHeap.insert(15);
		newHeap.insert(11);
		newHeap.insert(3);
		newHeap.insert(5);
		
		newHeap.print();
		
		//remove elements
		newHeap.removeMin();
		newHeap.print();
		
		//remove elements
		newHeap.removeMin();
		newHeap.print();
		
		newHeap.insert(2);
		newHeap.print();
	}
}
