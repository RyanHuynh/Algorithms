package Sorting;

import java.util.ArrayList;

public abstract class Sorting {
	private final int MAX_ARRAY_SIZE = 50;
	private int[] defaultArray;
	private int[] sortedArray;
	
	//Constructor creates a default array that has random number from 0 to MAX_ARRAY_SIZE.
	public Sorting(int sizeOfArray){
		if(sizeOfArray > MAX_ARRAY_SIZE)
			sizeOfArray = MAX_ARRAY_SIZE;
		defaultArray = new int[sizeOfArray];
		for(int i = 0; i < defaultArray.length; i++){
			int randNumber = (int)(Math.random() * MAX_ARRAY_SIZE);
			defaultArray[i] = randNumber;
		}
		
		//Copy the default to sortedArray for the print function.
		sortedArray = defaultArray;
	}
	
	/*SOME UTILITY FUNCTIONS*/
	
	//Return the default array
	public int[] getArray(){
		return defaultArray;
	}
	
	//Copy the sorted array to result array
	public void setArray(int[] result){
		sortedArray = result;
	}
	public void setArray(ArrayList<Integer> result){
		for(int i = 0; i < result.size(); i ++){
			sortedArray[i] = result.get(i);
		}
	}
	
	//Each algorithm will implement this differently
	public abstract void doSort();
	
	//print the array.
	public void print(){
		System.out.print("The array is: ");
		for(int i = 0; i < sortedArray.length; i++){
			System.out.print(sortedArray[i] + " ");
		}
		System.out.println();
	}
}
