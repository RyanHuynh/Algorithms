package Sorting;

public class SelectionSort extends Sorting{
	public SelectionSort(int sizeOfArray){
		super(sizeOfArray);
	}
	
	//Selection Sort implementation.
	@Override
	public void doSort(){
		int minNum;	
		int minIndex;
		int[] tempArray = this.getArray();
		for(int i = 0; i < tempArray.length; i++){
			minNum = tempArray[i];	
			minIndex = i;
			for(int j = i + 1; j < tempArray.length; j++){
				if(minNum > tempArray[j]){
					minNum = tempArray[j];
					minIndex = j;
				}				
			}
			
			//Swap value of two numbers.
			if(minNum != tempArray[i]){
				int tempValue = tempArray[i];
				tempArray[i] = minNum;
				tempArray[minIndex] = tempValue;				
			}
			
			//Copy the sorted array to result array.
			this.setArray(tempArray);
		}
	}
	
	
	
	//TESTING
	public static void main(String[] args){
		
		//Selection Sort
		System.out.println("Selection Sort: ");
		SelectionSort newSSort = new SelectionSort(20);
		newSSort.print();
		newSSort.doSort();
		newSSort.print();
		
	}
}
