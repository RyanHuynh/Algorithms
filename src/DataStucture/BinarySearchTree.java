package DataStucture;

public class BinarySearchTree{
	private int data;
	private BinarySearchTree leftTree, rightTree;
	
	/*CONSTRUCTORS*/
	//Default Tree with no children.
	public BinarySearchTree(int value){
		data = value;
		leftTree = null;
		rightTree = null;
	}
	//Construct our Binary Tree from a list.
	public BinarySearchTree(int[] elementList) {
		//Construct the root of our tree using the 1st element from the list. Set left and right Node to null.
		data = elementList[0];
		leftTree = null;
		rightTree = null;
		
		//Begin to add more tree node to our tree.
		for(int i = 1; i < elementList.length; i++){
			this.insert(elementList[i]);
		}
	}
	
	//Insert element to our tree
	public void insert(int value) {
		//Compare the insert value with the current value. 
		//Travel to left node if the value is less than current value.
		//Travel to right node of the value is greater than current value.
		int currentValue = data;
		if(value <= currentValue){
			if(this.hasLeft()){
				leftTree.insert(value);
			}
			else
				leftTree = new BinarySearchTree(value);
		}
		else{
			if(this.hasRight()){
				rightTree.insert(value);
			}
			else
				rightTree = new BinarySearchTree(value);
		}		
	}
	
	/*ULTILITY FUNTIONS*/
	//Check if the node tree is a leaf.
	private boolean isLeaf(){
		return leftTree == null && rightTree == null;
	}
	
	//Check if the current Node has left child.
	protected boolean hasLeft(){
		return leftTree != null;
	}
	
	//Check if the current Node has right child.
	private boolean hasRight(){
		return rightTree != null;
	}
	
	//Return the height of the tree.
	public int height(){
		if(this.isLeaf())
			return 0;
		else{
			int height = 0;
			if(this.hasLeft()){
				height = Math.max(height,  leftTree.height());
			}
			if(this.hasRight()){
				height = Math.max(height,  rightTree.height());
			}
			return 1 + height;
		}
	}
	
	/*TRAVERSAL FUCTIONS*/
	//Pre-order travel
	public void preoder(){
		System.out.print(data + " ");
		if(this.hasLeft()){
			leftTree.preoder();
		}
		if(this.hasRight()){
			rightTree.preoder();
		}
	}
	
	//Inorder travel
	public void inoder(){
		if(this.hasLeft()){
			leftTree.inoder();
		}
		System.out.print(data + " ");
		if(this.hasRight()){
			rightTree.inoder();
		}
	}
	
	//Post-order travel
	public void postoder(){
		if(this.hasLeft()){
			leftTree.postoder();
		}
		if(this.hasRight()){
			rightTree.postoder();
		}
		System.out.print(data + " ");
	}
	
	//TESTING
		public static void main(String[] args){		
			int[] elements = {7,10,5,12,8};
			BinarySearchTree ourTree = new BinarySearchTree (elements);
			ourTree.insert(9);
			ourTree.insert(6);
			
			System.out.print("Pre-order travel: ");
			ourTree.preoder();
			
			System.out.print("\nInorder travel: ");
			ourTree.inoder();
			
			System.out.print("\nPostorder travel: ");
			ourTree.postoder();

			System.out.println("\nHeight of our tree: " + ourTree.height());
		}
}
