package Sorting;
import java.util.ArrayList;

/** This abstract class will create a random int[] with chosen size for us to sort.
 *  
 * @author Ryan Huynh
 * @version Dec 2014
 *
 */
public abstract class Sorting {
	private final int MAX_ARRAY_SIZE = 50;
	private int[] defaultArray;
	private int[] sortedArray;
	
	/**
	 * Instantiates default array that has random number from 0 to MAX_ARRAY_SIZE.
	 *
	 * @param sizeOfArray the size of array
	 */
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
	
	/**SOME UTILITY FUNCTIONS*/
	
	/**
	 * Return the default array
	 * @return defaultArray
	 */
	protected int[] getArray(){
		return defaultArray;
	}
	
	/**
	 * Copy the result array to sorted Array.
	 *
	 * @param result the sorted array.
	 */
	protected void setArray(int[] result){
		sortedArray = result;
	}
	
	/**
	 * Copy the result array to sorted Array.
	 *
	 * @param result the sorted array.
	 */
	protected void setArray(ArrayList<Integer> result){
		for(int i = 0; i < result.size(); i ++){
			sortedArray[i] = result.get(i);
		}
	}
	
	//Each algorithm will implement this differently
	/**
	 * Sort the array. Each algorithm will implement this differently.
	 */
	public abstract void doSort();
	
	/**
	 * Prints the array .For testing purposes.
	 */
	public void print(){
		System.out.print("The array is: ");
		for(int i = 0; i < sortedArray.length; i++){
			System.out.print(sortedArray[i] + " ");
		}
		System.out.println();
	}
}
