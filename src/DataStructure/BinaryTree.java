package DataStructure;

public class BinaryTree<E> {
	private E data;
	protected BinaryTree<E> leftTree;
	private BinaryTree<E> rightTree;
	
	/*CONSTRUCTORS*/
	//Construct a leaf node tree (left and right child are null).
	public BinaryTree(E data){
		this.data = data;
		leftTree = null;
		rightTree = null;
	}
	
	//Construct an inner node tree( tree that has children.
	public BinaryTree(E data, BinaryTree<E> leftNode, BinaryTree<E> rightNode){
		this.data = data;
		this.leftTree = leftNode;
		this.rightTree = rightNode;
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
	public static void main(String[] args){
		
	}
	
	
	
}
