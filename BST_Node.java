package SPLT_A4;

public class BST_Node {
	String data;
	BST_Node left;
	BST_Node right;
	BST_Node par;

	BST_Node(String data){
		this.data=data;
	}

	BST_Node(String data, BST_Node left,BST_Node right,BST_Node par){ //feel free to modify this constructor to suit your needs
		this.data=data;
		this.left=left;
		this.right=right;
		this.par=par;
	}


	public String getData(){
		return data;
	}
	public BST_Node getLeft(){
		return left;
	}
	public BST_Node getRight(){
		return right;
	}

	//add splaying
	public BST_Node containsNode(String s){
		if(data.equals(s)) {
			splay(this);
			return this;
		}
		if(data.compareTo(s)>0){    //s lexiconically less than data
			if(left==null) {
				splay(this);
				return this;
			}
			return left.containsNode(s);
		}
		if(data.compareTo(s)<0){
			if(right==null) {
				splay(this);
				return this;
			}
			return right.containsNode(s);
		}
		return this; //shouldn't hit
	}
	
	public boolean containsNodeNoSplay(String s) {
		if(data.equals(s)) {
			return true;
		}
		if(data.compareTo(s)>0){    //s lexiconically less than data
			if(left==null) {
				return false;
			}
			return left.containsNodeNoSplay(s);
		}
		if(data.compareTo(s)<0){
			if(right==null) {
				return false;
			}
			return right.containsNodeNoSplay(s);
		}
		return false; //shouldn't hit
	}
	//add splay method
	public BST_Node insertNode(String s){
		if(data.compareTo(s)>0){
			if(left==null){
				left=new BST_Node(s);
				left.par = this;
				return splay(left);
			}
			return left.insertNode(s);
		}
		if(data.compareTo(s)<0){
			if(right==null){
				right=new BST_Node(s);
				right.par = this;
				return splay(right);
			}
			return right.insertNode(s);
		}
		return splay(this);//ie we have a duplicate
	}
	
	//don't need to add splaying, didn't use this method
	public boolean removeNode(String s){
		if(data==null)return false;
		if(data.equals(s)){
			if(left!=null){
				data=left.findMax().data;
				left.removeNode(data);
				if(left.data==null) {
					left=null;
				}
			}
			else if(right!=null){
				data=right.findMin().data;
				right.removeNode(data);
				if(right.data==null) {
					right=null;
				}
			}
			else data=null;
			return true;
		}
		else if(data.compareTo(s)>0){
			if(left==null)return false;
			if(!left.removeNode(s))return false;
			if(left.data==null)left=null;
			return true;
		}
		else if(data.compareTo(s)<0){
			if(right==null)return false;
			if(!right.removeNode(s))return false;
			if(right.data==null)right=null;
			return true;
		}
		return false;
	}
	
	//add splay method
	public BST_Node findMin(){
		if(left!=null)return left.findMin();
		splay(this);
		return this;
	}
	
	//add splay method
	public BST_Node findMax(){
		if(right!=null)return right.findMax();
		splay(this);
		return this;
	}
	public int getHeight(){
		int l=0;
		int r=0;
		if(left!=null)l+=left.getHeight()+1;
		if(right!=null)r+=right.getHeight()+1;
		return Integer.max(l, r);
	}
	public String toString(){
		return "Data: "+this.data+", Left: "+((this.left!=null)?left.data:"null")+",Right: "+((this.right!=null)?right.data:"null");
	}
	
	public BST_Node splay(BST_Node toSplay) {
		//if just made or par == null, then the node will be at the root
		if (toSplay.par != null) {
		
			//if grandparent is null, then just need to zig
			if (toSplay.par.par == null) {
				toSplay = zig(toSplay);
			} else if ((toSplay.par == toSplay.par.par.left && toSplay == toSplay.par.left) ||
					(toSplay.par == toSplay.par.par.right && toSplay == toSplay.par.right)) { //if parent is left/right child, and node to splay is the same sided child
				toSplay = zigZig(toSplay);
			} else { //otherwise it's a zig-zag operation
				toSplay = zigZag(toSplay);
			}
			splay(toSplay);
		}
		return toSplay;
	}
	
	private BST_Node zig(BST_Node toZig) {
		//check if it's a right zig
		if (toZig == toZig.par.left) {
			toZig.par.left = toZig.right;
			if (toZig.right != null) toZig.right.par = toZig.par;//points to null if toZig doesn't have a right child
			toZig.right = toZig.par;
			//check if there are grandparents
			if (toZig.par.par != null) { //grandparents
				//check which child toZig will be for the grandparent
				if (toZig.par.par.left == toZig.par) {
					toZig.par = toZig.par.par;
					toZig.par.left = toZig;
				} else {
					toZig.par = toZig.par.par;
					toZig.par.right = toZig;
				}
			} else { //no grandparents
				toZig.par = null;
			}
			toZig.right.par = toZig;
			return toZig;
		} else { //otherwise it's a left zig
			toZig.par.right = toZig.left;
;			if (toZig.left != null) toZig.left.par = toZig.par;
			toZig.left = toZig.par;
			//check if there are grandparents
			if (toZig.par.par != null) {
				if (toZig.par.par.left == toZig.par) {
					toZig.par = toZig.par.par;
					toZig.par.left = toZig;
				} else {
					toZig.par = toZig.par.par;
					toZig.par.right = toZig;
				}
			} else {
				toZig.par = null;
			}
			toZig.left.par = toZig;
			return toZig;
		}
	}
	
	private BST_Node zigZig(BST_Node toZigZig) {
		//perform zig on parent, then zig on the node being splayed
			zig(toZigZig.par);
			zig(toZigZig);
			return toZigZig;
	}
	
	private BST_Node zigZag(BST_Node toZigZag) {
		//check if its a RL zig-zag
		zig(toZigZag);
		zig(toZigZag);
		return toZigZag;
	}

}