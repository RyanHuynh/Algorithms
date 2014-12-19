package Sorting;

/** Insertion Sort algorithm implementation
 * 
 * @author Ryan Huynh
 * @version Dec 2014
 *
 */
public class InsertionSort extends Sorting{
	public InsertionSort(int sizeOfArray){
		super(sizeOfArray);
	}
	
	//Implement insertion sort
	@Override
	public void doSort(){
		int[] tempArray = this.getArray();
		for(int i = 1; i < tempArray.length; i++){
			int value = tempArray[i];
			boolean done = false;
			int j = i - 1;
			while(j >= 0 && !done){
				int compareValue = tempArray[j];
				if(value > compareValue){
					done = true;				
				}
				else
				{
					tempArray[j] = value;
					tempArray[j + 1] = compareValue;
				}
				j--;
			}
		}
		this.setArray(tempArray);
	}
	
	//TESTING
	public static void main(String[] args){
		
		//Insertion sort.
		System.out.println("Insertion Sort:");
		InsertionSort newISort = new InsertionSort(20);
		newISort.print();
		newISort.doSort();
		newISort.print();
	}
}
