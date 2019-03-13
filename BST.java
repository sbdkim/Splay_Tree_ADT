package SPLT_A4;

public class BST implements BST_Interface {
	public BST_Node root;
	int size;

	public BST(){
		size=0;
		root=null;
	}

	@Override
	public BST_Node getRoot(){ //used for testing
		return root;
	}


	@Override
	public boolean insert(String s) { //if empty (ie root==null) you are to insert new node with data s into the root
		if(root==null){
			root=new BST_Node(s);
			size++;
			return true;
		}

		BST_Node insertedNode = root.insertNode(s);
		if(insertedNode.data == s){
			size++;
			return true;
		}
		return false;
	}


	@Override
	public boolean remove(String s) { //if you are removing the root in a list of size 1, you must set the root back to null.
		if(root==null)return false;
		if(size==1&&root.data.equals(s)){
			root=null;
			size--;
			return true;
		}
		if(root.removeNode(s)){
			size--;
			return true;
		}
		return false;
	}


	@Override
	public String findMin() {
		if(root==null)return null;
		return root.findMin().data;
	}


	@Override
	public String findMax() {
		if(root==null)return null;
		return root.findMax().data;
	}


	@Override
	public boolean empty() {
		return size==0;
	}


	@Override
	public int size() {
		return size;
	}


	@Override
	public int height() {
		if(root==null)return -1;
		return root.getHeight();
	}


	@Override
	public boolean contains(String s) {
		if(empty())return false;
		if (root.containsNode(s).data == s) return true;
		return false;
	}

}
