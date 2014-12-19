package DataStructure;

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
	
	/**
	 * Insert element into the tree.
	 *
	 * @param currentNode the current node
	 * @param value the value
	 */
	private void insert(Node currentNode, int value){
		//Compare the insert value with the current value. 
		//Travel to left node if the value is less than current value.
		//Travel to right node of the value is greater than current value.
		int currentValue = currentNode.getData();
		if(value <= currentValue){
			if(currentNode.hasLeft()){
				Node leftChild = currentNode.getLeftChild();
				this.insert(leftChild, value);
				//Check for balance, restructure the tree if needed.
				this.balance(currentNode);
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
				this.balance(currentNode);
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
	
	/**
 	 * Remove element from the tree.
 	 * If the node to be removed (w) is an internal node then there are 2 cases:
 	 * 		- If one of the children of the node is an external node, then we just replace the node with its sibling.
 	 *		- If both children of the node are internal node then we need to find the first internal node (y) that follow w
 	 *		  in an Inorder traversal of T. We then copy the value of y to w and remove node y.
 	 * @param element element to be removed.
 	 */
	public void removeElement(int element){
		//Do a search on the tree to see if the element we want to remove is in the tree.
		Node removeNode = this.getElement(this.getRoot(), element);
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
					removeNode.replaceWith(childNode);
				}
				else if(removeNode.hasRight()){
					//Replace the element node with its right child node.
					childNode = removeNode.getRightChild();
					removeNode.replaceWith(childNode);
				}
			}
			//The element has both left and right tree node. 
			else{
				//Find the first internal node y that follows element in inorder traversal in tree.
				Node nextInorderNode = this.getNextInorderNode(removeNode);
				//Now we will replace the data of the remove element with this node
				removeNode.setData(nextInorderNode.getData());
				//Next we will remove the nextInorderNode (since we already copy data to the remove element).
				//If it is a leaf node, just remove it.
				if(nextInorderNode.isLeaf())
					this.delete(nextInorderNode);
				else{
					//We will do remove external node similarly to the one we did earlier.
					//Notice here we don't need to check for left tree because the nextInorderNode is already left most node.
					Node childNode = nextInorderNode.getRightChild();
					//Replace nextInorderNode with its child
					nextInorderNode.replaceWith(childNode);
				}
			}
			
			//Update height of the parent. check for height balance and re-balance the tree if needed.
			Node currentNode = removeNode.getParent();
			while(currentNode != null){
				this.balance(currentNode);
				currentNode.updateHeight();
				currentNode = currentNode.getParent();
			}
		}
	}
	
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
	 
 	/**
 	 * Balance the tree.
 	 * This function will check the current node's balance factor (the absolute value of the difference of its children's height)
 	 * If the balance factor is higher than > 1, then the current node is unbalanced. We then pick z,y,x nodes where z is the unbalanced node
 	 * y is the heavier children of z, and x is heavier child of y (in case both child have the same height then pick the one on the side with parent y)
 	 * There are 4 scenarios: 
 	 * 		- Left Left Pattern.
 	 * 		- Right Right Pattern.
 	 * 		- Left Right Pattern.
 	 * 		- Right Left Pattern.
 	 * We then perform rotate nodes based on the pattern.
 	 *
 	 * @param currentNode the current node
 	 * @param valueInserted the value inserted
 	 */
 	private void balance(Node currentNode){
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
			 
			 //Decide which type of rotation
			 Node z = currentNode;
			 Node y = null; 
			 Node x = null;
			 if(leftChildHeight > rightChildHeight){
				 y = leftChild;
				 Node leftGrandChild = leftChild.getLeftChild();
				 Node rightGrandChild = leftChild.getRightChild();
				 int rightGrandChildHeight = -1;
				 int leftGrandChildHeight = -1;
				 if(leftGrandChild != null)
					 leftGrandChildHeight = leftGrandChild.getHeight();
				 if(rightGrandChild != null)
					 rightGrandChildHeight = rightGrandChild.getHeight();
				 
				 //Left left case
				 if(leftGrandChildHeight > rightGrandChildHeight){
					//Single right rotation
					 this.rightRotate(z, y);
				 }
				 //Left right case
				 else if(leftGrandChildHeight < rightGrandChildHeight){
					 x = rightGrandChild;
					//Do left rotation first
					 Node newRoot = this.leftRotate(y, x);
					 //Then do right rotation
					 this.rightRotate(z, newRoot);
				 }
				 //Left left case. This is when both height are equal (this checked is needed for removal)
				 else{
					//Single right rotation
					 this.rightRotate(z, y);
				 }
			 }
			 else
			 {
				 y = rightChild;
				 Node leftGrandChild = rightChild.getLeftChild();
				 Node rightGrandChild = rightChild.getRightChild();
				 int rightGrandChildHeight = -1;
				 int leftGrandChildHeight = -1;
				 if(leftGrandChild != null)
					 leftGrandChildHeight = leftGrandChild.getHeight();
				 if(rightGrandChild != null)
					 rightGrandChildHeight = rightGrandChild.getHeight();
				 
				 //Right left case
				 if(leftGrandChildHeight > rightGrandChildHeight){
					 x = leftGrandChild;
					//Do right rotation first
					 Node newRoot = this.rightRotate(y, x);
					 //Do a left rotation
					 this.leftRotate(z, newRoot);
				 }
				 //Right right case
				 else if(leftGrandChildHeight < rightGrandChildHeight){
					 //Do a single left rotation
					 this.leftRotate(z, y);
				 }
				 //Right right case. This is when both height are equal (this checked is needed for removal)
				 else{
					//Single left rotation
					 this.leftRotate(z, y);
				 }
			 }
		 }
 	 }
	 
	 /**
 	 * Left rotate, move right child of root to become the root, and the old root becomes this new root left child.
 	 *
 	 * @param root the root
 	 * @param newRoot the new root
 	 * @return the node
 	 */
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
	 
	 /**
 	 * Right rotate,  move the left child of the root to become the new root, and the old root becomes the right child of this new root.
 	 *
 	 * @param root the root
 	 * @param newRoot the new root
 	 * @return the node
 	 */
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
		//int[] elements = {1,2,3};
		AVLTree ourTree = new AVLTree (elements);
		ourTree.insert(9);
		ourTree.removeElement(7);
		ourTree.removeElement(5);
		ourTree.preorder();
	}
}
