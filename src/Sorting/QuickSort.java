package Sorting;

import java.util.ArrayList;
import java.util.Random;

public class QuickSort extends Sorting {

	public QuickSort(int sizeOfArray) {
		super(sizeOfArray);
	}
	
	//Implement Quick Sort
	@Override
	public void doSort() {
		int[] inputArray = this.getArray();
		ArrayList<Integer> result = quickSort(toArrayList(inputArray));
		this.setArray(result);		
	}
	
	//
	private ArrayList<Integer> quickSort(ArrayList<Integer> input){
		if(input.size() <= 1)
			return input;
		else{
			
			//Pick a random pivot point.
			Random rndGen = new Random();
			int rndIndex = rndGen.nextInt(input.size());
			int pivot = input.get(rndIndex);
			
			//3 ArrayLists to hold elements. LeftList: elements less than pivot, RightList: element larger than pivot, MiddleList: elements equal pivot.
			ArrayList<Integer> leftList = new ArrayList<Integer>();
			ArrayList<Integer> middleList = new ArrayList<Integer>();
			ArrayList<Integer> rightList = new ArrayList<Integer>();
			//Copy element to the list
			for(int i = 0; i < input.size(); i++){
				int value = input.get(i);
				if(value < pivot)
					leftList.add(value);
				if(value == pivot)
					middleList.add(value);
				if(value > pivot)
					rightList.add(value);
			}
			//Recursive until size of the array is 1.
			if(leftList.size() > 1)
				leftList = quickSort(leftList);
			if(rightList.size() > 1)
				rightList = quickSort(rightList);
			//Merge all 3 lists.
			ArrayList<Integer> result = new ArrayList<Integer>();
			result.addAll(leftList);
			result.addAll(middleList);
			result.addAll(rightList);
			return result;
		}
	}
	
	//Helper function.
	private ArrayList<Integer> toArrayList(int[] input){ 
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i = 0 ; i < input.length; i++){
			result.add(input[i]);
		}
		return result;
	}
	//TESTING
	public static void main (String[] args){
		QuickSort newQSort = new QuickSort(20);
		System.out.println("Quick Sort: ");
		newQSort.print();
		newQSort.doSort();
		newQSort.print();
		
	}

}
