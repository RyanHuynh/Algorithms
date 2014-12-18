package DataStructure;

/** Binary Search Tree Data Structure
 * This class create a BST from combine/linking tree node(Node object)
 * It has some common functions you can perform on a BST like search, insert, remove element (for simplicity, data of each node is
 * just Integer). It also has traversal function such as inorder, preorder, postorder.
 * 
 * @author Ryan Huynh
 * @version Dec 2014
 */
public class BinarySearchTree{
	
	/*Tree node for our BinarySearchTree*/
	protected class Node{
		//Variables for Node.
		private int data, height;
		private Node leftChild, rightChild, parent;
		
		/**
		 * Constructor to create a node with default value, and link it to its parent.
		 *
		 * @param value the value
		 * @param p the parent node.
		 */
		protected Node(int value,Node p){
			data = value;
			parent = p;
			leftChild = null;
			rightChild = null;
			height = 0;
		}
		
		//GETTERS methods
		/**
		 * Gets the left child.
		 *
		 * @return the left child
		 */
		protected Node getLeftChild(){
			return leftChild;
		}
		
		/**
		 * Gets the right child.
		 *
		 * @return the right child
		 */
		protected Node getRightChild(){
			return rightChild;
		}
		
		/**
		 * Gets the parent.
		 *
		 * @return the parent
		 */
		protected Node getParent(){
			return parent;
		}
		
		/**
		 * Gets the data.
		 *
		 * @return the data
		 */
		protected int getData(){
			return data;
		}
		
		/**
		 * Gets the height.
		 *
		 * @return the height
		 */
		protected int getHeight(){
			return height;
		}
		
		/**
		 * Checks if node is leaf(external node with no children).
		 *
		 * @return true, if it is leaf
		 */
		protected boolean isLeaf(){
			return leftChild == null && rightChild == null;
		}
		
		/**
		 * Checks if the node has left child.
		 *
		 * @return true, if it has left child
		 */
		protected boolean hasLeft(){
			return leftChild != null;
		}		
		
		/**
		 * Checks if the node has right child.
		 *
		 * @return true, if it has right child.
		 */
		protected boolean hasRight(){
			return rightChild != null;
		}
				
		//SETTER methods
		/**
		 * Sets the height.
		 *
		 * @param newHeight the new height
		 */
		protected void setHeight(int newHeight){
			height = newHeight;
		}
		
		/**
		 * Sets the data.
		 *
		 * @param newData the new data
		 */
		protected void setData(int newData){
			data= newData;
		}	
		
		/**
		 * Sets the left child.
		 *
		 * @param newChild the new left child
		 */
		protected void setLeftChild(Node newChild){
			leftChild = newChild;
		}
		
		/**
		 * Sets the right child.
		 *
		 * @param newChild the new right child
		 */
		protected void setRightChild(Node newChild){
			rightChild = newChild;
		}
		
		protected void setParent(Node newParent){
			parent = newParent;
		}
		/**
		 * Update height of caller node.
		 * The new height will be the max height of its children + 1
		 */
		protected void updateHeight(){
			int rightChildHeight = -1;
			int leftChildHeight = -1;
			
			Node leftChild = this.getLeftChild();
			if(leftChild != null)
				leftChildHeight = leftChild.getHeight();
			Node rightChild = this.getRightChild();
			if(rightChild != null)
				rightChildHeight = rightChild.getHeight();
			
			int newHeight = Math.max(leftChildHeight, rightChildHeight) + 1;
			this.setHeight(newHeight);
		}
	}
	
	//Variable for BinarySearchTree
	private Node root;
	
	/*CONSTRUCTORS*/
	/**
	 * Instantiates a new binary search tree from an array.
	 *
	 * @param elementList the int[] array contain initial element for our tree.
	 */
	public BinarySearchTree(int[] elementList) {
		//Construct the root of our tree using the 1st element from the list. Root has no parent so it's null.
		int data = elementList[0];
		root = new Node(data, null);
		
		//Begin to add more tree node to our tree.
		for(int i = 1; i < elementList.length; i++){
			this.insert(elementList[i]);
		}
	}
	
	/*SOME COMMON FUNCTION FOR OUR TREE*/
	/**
	 * Insert new element into our tree. 
	 * This method acts as interface.
	 *
	 * @param value the value
	 */
	public void insert(int value){
		//Start insert from the root.
		this.insert(root, value);
	}
	
	/**
	 * Insert element into our tree.
	 *
	 * @param currentNode the current node.
	 * @param value the value
	 */
	private void insert(Node currentNode, int value) {
		//Compare the insert value with the current value. 
		//Travel to left node if the value is less than current value.
		//Travel to right node of the value is greater than current value.
		int currentValue = currentNode.getData();
		if(value <= currentValue){
			if(currentNode.hasLeft()){
				Node leftChild = currentNode.getLeftChild();
				this.insert(leftChild, value);
				//Update height when new node added
				currentNode.updateHeight();
			}
			else{
				Node newNode = new Node(value, currentNode);
				currentNode.setLeftChild(newNode);
				currentNode.updateHeight();
			}	
		}
		else{
			if(currentNode.hasRight()){
				Node rightChild = currentNode.getRightChild();
				this.insert(rightChild, value);
				//Update height when new node added.
				currentNode.updateHeight();
			}
			else{
				Node newNode = new Node(value, currentNode);
				currentNode.setRightChild(newNode);
				currentNode.updateHeight();
			}
		}		
	}
	
	/**
	 * Search for the element in our tree.
	 * This method acts as interface.
	 *
	 * @param element the element
	 * @return true if the element is in the tree, false otherwise.
	 */
	public boolean search(int element){
		//Start searching from the root.
		return this.search(root, element);
	}
	
	/**
	 * Search the element from the current node.
	 *
	 * @param currentNode the current node
	 * @param element the element
	 * @return true if the element is in the tree, false otherwise.
	 */
	private boolean search(Node currentNode, int element){
		int value = currentNode.getData();
		if(element == value)
			return true;
		else{
			//If the current node is a leaf node then the element is not in the tree.
			if(currentNode.isLeaf())
				return false;
			else{
				//Search left tree.
				if(element < value){
					if(!currentNode.hasLeft())//No left Child
						return false;
					else{
						Node leftChild = currentNode.getLeftChild();
						return this.search(leftChild, element);//Recursive search on left tree.
					}
				}
				//Search right tree.
				else{
					if(!currentNode.hasRight())//No right Child
						return false;
					else{
						Node rightChild = currentNode.getRightChild();
						return this.search(rightChild, element);//Recursive search on right tree.
					}
				}
			}
		}
	}
	
	/**
	 * Gets the height of our tree (height at root).
	 *
	 * @return the height
	 */
	public int getHeight(){
		return root.getHeight();
	}
	
	/**
	 * Removes the element.
	 * This function acts as interface.
	 *
	 * @param element the element
	 */
	public void removeElement(int element){
		this.removeElement(root, element);
	}
	
	/**
	 * Removes the element from the tree.
	 *
	 * @param currentNode the current node
	 * @param element the element
	 */
	private void removeElement(Node currentNode, int element){
		//Do a search on the tree to see if the element we want to remove is in the tree.
		Node removeNode = this.getElement(currentNode, element);
		if(removeNode == null)
			System.out.println("The element " + element + " is not the in the tree.");
		else{
			//Check to see if the element is a leaf node tree. In this case, we just have to remove it.
			if(removeNode.isLeaf())
				this.delete(removeNode);
			//Check to see if the element has external tree node ( that is no left or right tree node).
			else if(!removeNode.hasLeft() || !removeNode.hasRight()){
				//Do a remove external node on current tree node.
				Node childNode = null;
				if(removeNode.hasLeft()){
					//Replace the element node with its left child node.
					childNode = removeNode.getLeftChild();
					removeNode.setLeftChild(childNode.getLeftChild());
					removeNode.setRightChild(childNode.getRightChild());
					removeNode.setData(childNode.getData());
				}
				else if(removeNode.hasRight()){
					//Replace the element node with its right child node.
					childNode = removeNode.getLeftChild();
					removeNode.setLeftChild(childNode.getLeftChild());
					removeNode.setRightChild(childNode.getRightChild());
					removeNode.setData(childNode.getData());
					
				}
			}
			//The element has both left and right tree node. 
			else{
				//Find the first internal node y that follows element in inorder traversal in tree.
				Node nextInorderNode = this.getNextInorderNode(removeNode);
				//Now we will replace the data of the remove element with this node
				removeNode.data = nextInorderNode.data;
				//Next we will remove the nextInorderNode (since we already copy data to the remove element).
				//If it is a leaf node, just remove it.
				if(nextInorderNode.isLeaf())
					this.delete(nextInorderNode);
				else{
					//We will do remove external node similarly to the one we did earlier.
					//Notice here we don't need to check for left tree because the nextInorderNode is already left most node.
					Node childNode = nextInorderNode.rightChild;
					//Replace nextInorderNode with its child
					nextInorderNode.leftChild = childNode.leftChild;
					nextInorderNode.rightChild = childNode.rightChild;
					nextInorderNode.data = childNode.data;
				}
			}
		}
	}
	
	/*TRAVERSAL FUCTIONS*/
	/**
	 * Preorder traversal: Visit the root then travel left, right.
	 */
	public void preorder(){
		this.preorder(root);
	}
	
	/**
	 * Preorder traversal: Visit the root then travel left child, right child. Starting from the current node.
	 *
	 * @param currentNode the current node
	 */
	private void preorder(Node currentNode){
		System.out.print(currentNode.getData() + " ");
		if(currentNode.hasLeft()){
			this.preorder(currentNode.getLeftChild());
		}
		if(currentNode.hasRight()){
			this.preorder(currentNode.getRightChild());
		}
	}
	
	/**
	 * Inorder traversal. Visit the left child then root then right child.
	 */
	public void inorder(){
		this.inorder(root);
	}
	
	/**
	 * Inorder traversal. Visit the left child then root then right child. Starting from the current node.
	 *
	 * @param currentNode the current node
	 */
	private void inorder(Node currentNode){
		if(currentNode.hasLeft()){
			this.inorder(currentNode.getLeftChild());
		}
		System.out.print(currentNode.getData() + " ");
		if(currentNode.hasRight()){
			this.inorder(currentNode.getRightChild());
		}
	}
	
	/**
	 * Postorder traversal. Visit the left child, then the right child, then root.
	 */
	public void postorder(){
		this.postorder(root);
	}
	
	/**
	 * Postorder traversal. Visit the left child, then the right child, then root. Starting from the current node.
	 *
	 * @param currentNode the current node
	 */
	private void postorder(Node currentNode){
		if(currentNode.hasLeft()){
			this.postorder(currentNode.getLeftChild());
		}
		if(currentNode.hasRight()){
			this.postorder(currentNode.getRightChild());
		}
		System.out.print(currentNode.getData() + " ");
	}
		
	/*ULTILITY FUNTIONS*/	
	/**
	 * Gets the root node.
	 *
	 * @return the root
	 */
	protected Node getRoot(){
		return root;
	}
	
	/**
	 * Sets a new root for our tree.
	 *
	 * @param newRoot the new root
	 */
	protected void setRoot(Node newRoot){
		root = newRoot;
	}
	
	/**
	 * Get the first node the follow the input node in inorder traversal.
	 * The first node the follow up the input node will be the next left most node.
	 * 
	 * @param inputNode the input node
	 * @return the first inorder node follow up from input node.
	 */
	private Node getNextInorderNode(Node inputNode){
		if(!inputNode.hasRight())
			return null;
		else{
			//Move to first right child.
			Node currentNode = inputNode.rightChild;
			while(currentNode.hasLeft())//Move down the tree if the node has left child.
				currentNode = currentNode.leftChild;
			return currentNode;
		}
	}

	/**
	 * Delete the reference to this node.
	 * Find the parent node 1st then delete input node from its parent.
	 *
	 * @param inputNode the input node
	 */
	private void delete(Node inputNode){
		//Get the parent node of this node.
		Node parent = inputNode.getParent();
		//If this node doesn't has parent which mean it is a root node.
		if(parent == null)
			System.out.println("Cannot delete root node");
		else{
			//Compare it with left and right child of parent.
			if(parent.getLeftChild() == inputNode)
				parent.setLeftChild(null);
			else
				parent.setRightChild(null);
		}
	}
	
	/**
	 * Return a tree node with the input value in the tree. 
	 *
	 * @param currentNode the current node
	 * @param inputValue the input value
	 * @return the node element, return null if the element doesn't existed in the tree.
	 */
	protected Node getElement(Node currentNode, int inputValue){
		int data = currentNode.getData();
		if(inputValue == data)
			return currentNode;
		else{
			//If the current node is a leaf node then the element is not in the tree.
			if(currentNode.isLeaf())
				return null;
			else{
				//Search left tree.
				if(inputValue < data){
					if(!currentNode.hasLeft())//No left child
						return null;
					else{
						Node leftChild = currentNode.getLeftChild();
						return this.getElement(leftChild, inputValue);//Recursive search on left tree.
					}
				}
				//Search right tree.
				else{
					if(!currentNode.hasRight())//No right child.
						return null;
					else{
						Node rightChild = currentNode.getRightChild();
						return this.getElement(rightChild, inputValue);//Recursive search on right tree.
					}
				}
			}
		}
	}
	
	//TESTING
		public static void main(String[] args){		
			int[] elements = {7,10,5,12,8};
			BinarySearchTree ourTree = new BinarySearchTree (elements);
			
			ourTree.insert(9);
			ourTree.insert(6);
			
			System.out.print("Pre-order travel: ");
			ourTree.preorder();
			
			System.out.print("\nInorder travel: ");
			ourTree.inorder();
			
			System.out.print("\nPostorder travel: ");
			ourTree.postorder();

			System.out.println("\nHeight of our tree: " + ourTree.getHeight());
			
			System.out.println("'5' is in the tree?:" + ourTree.search(5) );
			System.out.println("'99' is in the tree?:" + ourTree.search(99) );
			
			ourTree.removeElement(7);
			System.out.print("Verify 7 is removed by doing another inorder travel:");
			ourTree.inorder();

		}
}
