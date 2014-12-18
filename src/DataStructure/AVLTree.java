package DataStructure;

import DataStructure.BinarySearchTree.Node;

/**
  * AVL Tree data structure. AVL is extended version of Binary Search Tree where its a height is always logn where n is number of 
  * tree node in the tree.
  * AVL tree will have search, insert and remove methods run time of O(logn) 
  * Since this class extend from BST class, it will have basic methods you can do with regular BST.
  * 
  *  @author Ryan Huynh
  *  @version Dec 2014
  */
public class AVLTree extends BinarySearchTree{

	/**
	 * Instantiates a new AVL tree using BST.
	 *
	 * @param inputArray the input array
	 */
	public AVLTree(int[] inputArray){
		super(inputArray);
		
	}
	
	public void insert(int value){
		Node root = this.getRoot();
		this.insert(root, value);
	}
	public void insert(Node currentNode, int value){
		//Compare the insert value with the current value. 
		//Travel to left node if the value is less than current value.
		//Travel to right node of the value is greater than current value.
		int currentValue = currentNode.getData();
		if(value <= currentValue){
			if(currentNode.hasLeft()){
				Node leftChild = currentNode.getLeftChild();
				this.insert(leftChild, value);
				//Check for balance, restructure the tree if needed.
				this.balance(currentNode, value);
				//Update height when new node added
				currentNode.updateHeight();	
			}
			else{
				Node newNode = new Node(value, currentNode);
				currentNode.setLeftChild(newNode);
				//Update height when new node added
				currentNode.updateHeight();			}	
		}
		else{
			if(currentNode.hasRight()){
				Node rightChild = currentNode.getRightChild();
				this.insert(rightChild, value);
				//Check for balance, restructure the tree if needed.
				this.balance(currentNode, value);
				//Update height when new node added
				currentNode.updateHeight();	
			}
			else{
				Node newNode = new Node(value, currentNode);
				currentNode.setRightChild(newNode);
				//Update height when new node added
				currentNode.updateHeight();			
			}
		}		
	}
	
	/**SOME ULTILITY FUNCTION*/
	/**
	 * Gets the first unbalanced node. Starting from the current node and going up to its ancestors.
	 *
	 * @param currentNode the current node
	 * @return the first unbalanced node
	 */
	private Node getFirstUnbalancedNode(Node currentNode){
		Node parent = currentNode.getParent();
		if(parent == null)
			return null;
		else{
			//Get children nodes of this parent. Then get their height.
			Node leftChild = parent.getLeftChild();
			Node rightChild = parent.getRightChild();
			int leftChildHeight = -1;
			if(leftChild != null)
				leftChildHeight = leftChild.getHeight();
			int rightChildHeight = -1;
			if(rightChild != null)
				rightChildHeight = rightChild.getHeight();
			//Compare their height. The node is unbalanced if absolute difference of their children's height > 1
			if(Math.abs(rightChildHeight - leftChildHeight) > 1)
				return parent;
			else return this.getFirstUnbalancedNode(parent);
		}
	}
	 private void balance(Node currentNode, int valueInserted){
		 int rightChildHeight = -1;
		 int leftChildHeight = -1;
		
		 Node leftChild = currentNode.getLeftChild();
		 if(leftChild != null)
			leftChildHeight = leftChild.getHeight();
		 Node rightChild = currentNode.getRightChild();
		 if(rightChild != null)
			rightChildHeight = rightChild.getHeight();
		
		 int balanceFactor = Math.abs(leftChildHeight - rightChildHeight);
		 if(balanceFactor > 1){ //Unbalanced
			 
			 //Decide which tyoe of rotation
			 Node z = currentNode;
			 Node y = null; 
			 Node x = null;
			 if(leftChildHeight > rightChildHeight){
				 y = leftChild;
				 int leftChildData = leftChild.getData();
				 
				 //Left left Case
				 if(valueInserted < leftChildData){
					 x = leftChild.getLeftChild();
					 //Single right rotation
					 this.rightRotate(z, y);
				 }
				 
				 //Left Right Case
				 else{
					 x = leftChild.getRightChild();
					 //Do left rotation first
					 Node newRoot = this.leftRotate(y, x);
					 //Then do right rotation
					 this.rightRotate(z, newRoot);
				 }
			 }
			 else
			 {
				 y = rightChild;
				 int rightChildData = rightChild.getData();
				 
				 //Right left Case
				 if(valueInserted < rightChildData){
					 x = rightChild.getLeftChild();
					 //Do right rotation first
					 Node newRoot = this.rightRotate(y, x);
					 //Do a left rotation
					 this.leftRotate(z, newRoot);
				 }
				 
				 //Right Right Case
				 else{
					 x = rightChild.getRightChild();
					 //Do a single left rotation
					 this.leftRotate(z, y);
				 }
			 }
		 }
			 
	 }
	 
	 private Node leftRotate(Node root, Node newRoot){
		 Node leftChildOfNewRoot = newRoot.getLeftChild();
		 Node parentOfRoot = root.getParent();
		 
		 //Set parent of newRoot as root's parent
		 newRoot.setParent(parentOfRoot);
		 //Parent of root accepts newRoot as its child. But need to find out whether root is left or right child of its parent.
		 //In case root is already root of our tree, then just assign the root to newRoot
		 if(parentOfRoot == null)
			 this.setRoot(newRoot);
		 else{
			 int parentData = parentOfRoot.getData();
			 int rootData = root.getData();
			 if(rootData < parentData) //root is left child
				 parentOfRoot.setLeftChild(newRoot);
			 else //root is right child.
				 parentOfRoot.setRightChild(newRoot);
		 }
		 
		 //root node will be left child of newRoot
		 newRoot.setLeftChild(root);
		 //root will accept newRoot as its parent.
		 root.setParent(newRoot);
		 
		 //Old root will take newRoot leftChild as its rightChild
		 root.setRightChild(leftChildOfNewRoot);
	 	 //Left child of new root accepts old root as its parent
		 if(leftChildOfNewRoot != null){
			 leftChildOfNewRoot.setParent(root);
		 }
		 
		 //Update height.
		 root.updateHeight();
		 newRoot.updateHeight();
		 
		 return newRoot;
	 }
	 
	 private Node rightRotate(Node root, Node newRoot){
		 Node rightChildOfNewRoot = newRoot.getRightChild();
		 Node parentOfRoot = root.getParent();
		 
		//Set parent of newRoot as root's parent
		 newRoot.setParent(parentOfRoot);
		 //Parent of root accepts newRoot as its child. But need to find out whether root is left or right child of its parent.
		 //In case root is already root of our tree, then just assign the root to newRoot
		 if(parentOfRoot == null)
			 this.setRoot(newRoot);
		 else{
			 int parentData = parentOfRoot.getData();
			 int rootData = root.getData();
			 if(rootData < parentData) //root is left child
				 parentOfRoot.setLeftChild(newRoot);
			 else //root is right child.
				 parentOfRoot.setRightChild(newRoot);
		 }
		 
		 //root node will be right child of newRoot
		 newRoot.setRightChild(root);
		 //root will accept newRoot as its parent.
		 root.setParent(newRoot);
		 
		 //Old root will take newRoot rightChild as its leftChild
		 root.setLeftChild(rightChildOfNewRoot);
		 //Right child of new root accepts old root as its parent
		 if(rightChildOfNewRoot != null){
			 rightChildOfNewRoot.setParent(root);
		 }
		 
		 //Update height.
		 root.updateHeight();
		 newRoot.updateHeight();
		 
		 return newRoot;
	 }
	 	/**TESTING*/
	public static void main (String[] args){
		int[] elements = {7,10,5,12,8};
		AVLTree ourTree = new AVLTree (elements);
		ourTree.insert(9);
		ourTree.insert(6);
		ourTree.insert(13);
		ourTree.insert(14);
		ourTree.insert(15);
		//System.out.println();
		//Node root = ourTree.getRoot();
		//Node currentNode = ourTree.getElement(root, 12);
		//System.out.println(ourTree.getFirstUnbalancedNode(currentNode).getData());
		
		
		//ourTree.removeElement(12);
		//System.out.print("Verify 10 is removed by doing another inorder travel:");
		ourTree.inorder();
	}
}
