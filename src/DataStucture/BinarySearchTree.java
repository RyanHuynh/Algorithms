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
	
	//Search for element in our tree
	public boolean search(int element){
		if(element == data)
			return true;
		else{
			//If the current node is a leaf node then the element is not in the tree.
			if(this.isLeaf())
				return false;
			else{
				//Search left tree.
				if(element < data){
					if(!this.hasLeft())//No left tree
						return false;
					else
						return this.leftTree.search(element);//Recursive search on left tree.
				}
				//Search right tree.
				else{
					if(!this.hasRight())
						return false;
					else
						return this.rightTree.search(element);
				}
			}
		}
	}
	
	//Get the element from the tree. Return null if the element does not exist.
	public BinarySearchTree getElement(int element){
		if(element == data)
			return this;
		else{
			//If the current node is a leaf node then the element is not in the tree.
			if(this.isLeaf())
				return null;
			else{
				//Search left tree.
				if(element < data){
					if(!this.hasLeft())//No left tree
						return null;
					else
						return this.leftTree.getElement(element);//Recursive search on left tree.
				}
				//Search right tree.
				else{
					if(!this.hasRight())
						return null;
					else
						return this.rightTree.getElement(element);
				}
			}
		}
	}
	//Remove Element from the tree.
	public void removeElement(int element){
		//Do a search on the tree to see if the element we want to remove is in the tree.
		BinarySearchTree item = this.getElement(element);
		if(item == null)
			System.out.println("The element " + element + " is not the in the tree.");
		else{
			//Check to see if the element is a leaf node tree. In this case, we just have to remove it.
			if(item.isLeaf())
				item = null;
			//Check to see if the element has external tree node ( that is no left or right tree node).
			else if(!item.hasLeft() || !item.hasRight()){
				//Do a remove external node on current tree node.
				BinarySearchTree childNode = null;
				if(item.hasLeft()){
					//Replace the element node with its left child node.
					childNode = item.leftTree;
					item = childNode;
				}
				else if(item.hasRight()){
					//Replace the element node with its right child node.
					childNode = item.rightTree;
					item = childNode;
				}
			}
			//The element has both left and right tree node. 
			else{
				//Find the first internal node y that follows element in inorder traversal in tree.
			}
		}
		
	}
	/*ULTILITY FUNTIONS*/
	//Check if the node tree is a leaf.
	private boolean isLeaf(){
		return leftTree == null && rightTree == null;
	}
	
	//Check if the current Node has left child.
	private boolean hasLeft(){
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
	//Get the first node the follow the input node in inorder traversal.
	private BinarySearchTree getNextInorderNode(BinarySearchTree input){
		if(!input.hasRight())
			return null;
		else{
			BinarySearchTree currentNode = input.rightTree;
			while(currentNode.hasLeft())
				currentNode = currentNode.leftTree;
			return currentNode;
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
			
			System.out.println("'5' is in the tree?:" + ourTree.search(5) );
			System.out.println("'99' is in the tree?:" + ourTree.search(99) );

		}
}
