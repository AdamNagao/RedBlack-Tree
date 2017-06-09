package Comp282Proj1;

public class BinarySearchTree {

	public static Node root;

	public static void leftRotate(Node nd){
		//This method will left rotate on Node nd
		Node exParent = nd.parent;
	    Node grandParent = exParent.parent;

	    
	    exParent.rightChild = nd.leftChild; 
	    if (nd.leftChild != null) {
	      nd.leftChild.parent = exParent;
	    }

	    
	    nd.leftChild = exParent;
	    exParent.parent = nd;

	    nd.parent = grandParent;
	    if (grandParent == null) {
	      root = nd;
	    } else if (grandParent.rightChild == exParent) {
	      grandParent.rightChild = nd;
	    } else {
	      grandParent.leftChild = nd;
	    }
	}

	public static void rightRotate(Node nd){
		//This method will right rotate on Node nd
		Node exParent = nd.parent;
	    Node grandParent = exParent.parent;

	    
	    exParent.leftChild = nd.rightChild; 
	    if (nd.rightChild != null) {
	      nd.rightChild.parent = exParent;
	    }

	    
	    nd.rightChild = exParent;
	    exParent.parent = nd;

	    nd.parent = grandParent;
	    if (grandParent == null) {
	      root = nd;
	    } else if (grandParent.leftChild == exParent) {
	      grandParent.leftChild = nd;
	    } else {
	      grandParent.rightChild = nd;
	    }
	}
	public void insert(int data){
		Node newNode = new Node(data);
		if(root==null){
			root = newNode;
			return;
		}
		Node current = root;
		Node parent = null;
		while(true){
			parent = current;
			if(data < current.data){				
				current = current.leftChild;
				if(current==null){
					parent.leftChild = newNode;
					newNode.parent = parent;
					return;
				}
			}else{
				current = current.rightChild;
				if(current==null){
					parent.rightChild = newNode;
					newNode.parent = parent;
					return;
				}
			}
		}
	}
	
	public void delete(int data){
		Node current = root;
		Node parent = null;
		boolean isLeftChild = false;
		while(current.data != data){
			parent = current;
			if(data > current.data){
				current = current.rightChild;
				isLeftChild = false;
			} else {
				current = current.leftChild;
				isLeftChild = true;
			}
			if(current == null){
				System.out.println("Whoops" );
				return;
			}
		}
		//Case 1 (Node is a leaf)
		if(current.leftChild ==null && current.rightChild == null){
			if(current == root){
				root = null;
			}
			else if (isLeftChild){
				parent.leftChild = null;
			} 
			else {
				parent.rightChild = null;
			}
		}
	// Case 2 (Node being deleted has one child)
	else if(current.rightChild==null){
		if(current==root){
			root = current.leftChild;
		}else if(isLeftChild){
			parent.leftChild = current.leftChild;
		}else{
			parent.rightChild = current.rightChild;
		}
	}
	else if(current.leftChild==null){
		if(current==root){
			root = current.rightChild;
		}else if(isLeftChild){
			parent.leftChild = current.rightChild;
		}else{
			parent.rightChild = current.rightChild;
		}
	}
		//Case 3 (node has two children, find the lowest value in the subtree
	else if(current.leftChild != null && current.rightChild != null){

		Node successsor =null;
		Node successsorParent =null;
		Node current1 = current.rightChild;
		while(current1!=null){
			successsorParent = successsor;
			successsor = current1;
			current1 = current1.leftChild;
		}

		if(successsor!=current.rightChild){
			successsorParent.leftChild = successsor.rightChild;
			successsor.rightChild = current.rightChild;
		}
		
		if(current==root){
			root = successsor;
		}else if(isLeftChild){
			parent.leftChild = successsor;
		}else{
			parent.rightChild = successsor;
		}			
		successsor.leftChild = current.leftChild;
	}		
	}
	
	public void inOrderPrint(Node nd){
		if(nd == null){
			return;
		}
		inOrderPrint(nd.leftChild);
		System.out.print(nd.data + " ");
		inOrderPrint(nd.rightChild);
		
	}
	public void postOrderPrint(Node nd){
		if(nd == null){
			return;
		}
		inOrderPrint(nd.leftChild);
		inOrderPrint(nd.rightChild);
		System.out.print(nd.data + " ");
	}
	public void preOrderPrint(Node nd){
		if(nd == null){
			return;
		}
		System.out.print(nd.data + " ");
		inOrderPrint(nd.leftChild);
		inOrderPrint(nd.rightChild);
	}
	public void displayTree(Node nd){
		//This method only works for a tree of height 3, and full
		System.out.println("                " + nd.data);
		System.out.println("            " +nd.leftChild.data + "      " + nd.rightChild.data);
		System.out.println("      " + nd.leftChild.leftChild.data + "     " + nd.leftChild.rightChild.data + "      " + nd.rightChild.leftChild.data + "    " + nd.rightChild.rightChild.data);
	}
}
