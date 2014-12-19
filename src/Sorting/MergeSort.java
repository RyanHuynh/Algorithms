package Sorting;

/** Merge Sort algorithm implementation
 * 
 * @author Ryan Huynh
 * @version Dec 2014
 *
 */
public class MergeSort extends Sorting{
	public MergeSort(int sizeOfArray){
		super(sizeOfArray);
	}
	
	//Implement Merge Sort
	@Override
	public void doSort(){
		int[] tempArray = this.getArray();
		tempArray = mergeSort(tempArray);
		this.setArray(tempArray);
	}
	
	/**
	 * Divine part of the algorithm
	 *
	 * @param inputArray the input array
	 * @return int[] divided array.
	 */
	private int[] mergeSort(int[] inputArray){
		if(inputArray.length <= 1)
			return inputArray;
		else
		{
			int arraySize = inputArray.length;
			int[] unsortedSequence1 = new int[arraySize / 2];
			int[] unsortedSequence2 = new int[arraySize - arraySize / 2];
			for(int i = 0; i < arraySize / 2; i++)
				unsortedSequence1[i] = inputArray[i];
			
			int indexSequence2 = 0;
			for(int i =  arraySize / 2; i <  arraySize; i++){
				unsortedSequence2[indexSequence2] = inputArray[i];
				indexSequence2++;
			}
			int[] sortedSequence1 = mergeSort(unsortedSequence1);
			int[] sortedSequence2 = mergeSort(unsortedSequence2);
			return doMerge(sortedSequence1, sortedSequence2);
		}
	}
	
	/**
	 * Compare element in 2 lists then merge then together
	 *
	 * @param inputList1 the input list1
	 * @param inputList2 the input list2
	 * @return the int[] mergered array.
	 */
	private int[] doMerge(int[] inputList1, int[] inputList2){
		int resultArraySize = inputList1.length + inputList2.length;
		int[] resultArray = new int[resultArraySize];
		
		int indexOfResult = 0;
		int indexFirstList = 0;
		int indexSecondList = 0;
		
		//Compare value of each list then copy min value to result list. Loop end when either list reach the end
		while(!(indexFirstList >= inputList1.length || indexSecondList >= inputList2.length)){
			int firstListValue = inputList1[indexFirstList];
			int secondListValue = inputList2[indexSecondList];
			if(firstListValue <= secondListValue){
				resultArray[indexOfResult] = firstListValue;
				indexFirstList++;
				indexOfResult++;
			}
			else
			{
				resultArray[indexOfResult] = secondListValue;
				indexSecondList++;
				indexOfResult++;
			}
		}
		//Copy the remaining element to result list
		for(int i = indexFirstList; i < inputList1.length; i ++){
			resultArray[indexOfResult] = inputList1[i];
			indexOfResult++;
		}		
		for(int i = indexSecondList; i < inputList2.length; i ++){
			resultArray[indexOfResult] = inputList2[i];
			indexOfResult++;
		}	
		return resultArray;
	}
	
	/**TESTING*/
	public static void main(String[] args){
		
		System.out.println("Merge Sort:");
		MergeSort newMSort = new MergeSort(21);
		newMSort.print();
		newMSort.doSort();
		newMSort.print();		
	}
}
