package SPLT_A4;

public class SPLT implements SPLT_Interface{
	private BST_Node root;
	private int size;

	public SPLT() {
		this.size = 0;
	} 

	public BST_Node getRoot() { //please keep this in here! I need your root node to test your tree!
		return root;
	}

	@Override
	public void insert(String s) {
		if(root==null){
			root=new BST_Node(s);
			size++;
		} else {
			if (root.containsNodeNoSplay(s) == false) size++;
			BST_Node insertedNode = root.insertNode(s);
			root = insertedNode;
		}
	}

	@Override
	public void remove(String s) {
		if (root != null) {
			if(size==1&&root.data.equals(s)){
				root=null;
				size--;
			}
			//checks if the removedNode is contained, if it is then it splays it to the top
			BST_Node containedNode = root.containsNode(s);
			if (containedNode.data == s) {
				root = containedNode;
				//saves the variables left and right tree
				BST_Node leftTree = root.left;
				BST_Node rightTree = root.right;

				//splays the max node to the root from the left tree, or the min node from the right tree
				if(leftTree != null) {
					leftTree.par = null;
					root = leftTree.findMax();
					root.right = rightTree;
					rightTree.par = root;
				}
				else{
					rightTree.par = null;
					root = rightTree.findMin();
				}
				size--;
			} else {
				root = containedNode;
			}
		}
	}

	@Override
	public String findMin() {
		if(root==null)return null;
		BST_Node minNode = root.findMin();
		root = minNode;
		return minNode.data;
	}

	@Override
	public String findMax() {
		if(root==null)return null;
		BST_Node maxNode = root.findMax();
		root = maxNode;
		return maxNode.data;
	}

	@Override
	public boolean empty() {
		if (this.size == 0) return true;
		return false;
	}

	@Override
	public boolean contains(String s) {
		if(empty())return false;
		BST_Node containedNode = root.containsNode(s);
		if (containedNode.data == s) {
			root = containedNode;
			return true;
		}
		return false;
	}

	public boolean containsNonSplay(String s) {
		if(empty())return false;
		if (root.containsNodeNoSplay(s)) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public int height() {
		if(root==null)return -1;
		return root.getHeight();
	}  
}