package Comp282Proj1;

public class RedBlackTree {

	public static Node root;

	public int getColor(Node nd) {
		// 0 Is red, 1 black, 2 double black
		return nd.color;
	}

	public static void setColor(Node nd, int i) {
		if (nd != null) {
			// 0 is red, 1 is black, 2 double black
			nd.color = i;
		}
	}

	public static void leftRotate(Node nd) {
		// This method will left rotate on Node nd
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

	public static void rightRotate(Node nd) {
		// This method will right rotate on Node nd
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

	public void insert(int data) {
		Node newNode = new Node(data);
		if (root == null) {
			newNode.color = 1;
			newNode.parent = null;
			root = newNode;

			adjustTreeAfterInsert(root);
			return;
		}
		Node current = root;
		Node parent = null;
		while (true) {
			parent = current;
			if (data < current.data) {
				current = current.leftChild;
				if (current == null) {
					parent.leftChild = newNode;
					newNode.parent = parent;
					adjustTreeAfterInsert(newNode);
					return;
				}
			} else {
				current = current.rightChild;
				if (current == null) {
					parent.rightChild = newNode;
					newNode.parent = parent;
					adjustTreeAfterInsert(newNode);
					return;
				}
			}
		}
	}

	public void adjustTreeAfterInsert(Node nd) {
		// This will recolor and adjust the tree
		if (nd.parent == null) {
			// this is the root, color it black

			setColor(nd, 1);

		} else {
			if (getUncle(nd) != null) {
				if (getColor(getUncle(nd)) == 0 && getColor(nd.parent) == 0) {
					setColor(getUncle(nd), 1);
					setColor(nd.parent, 1);
					setColor(getGrandParent(nd), 0);
					adjustTreeAfterInsert(getGrandParent(nd));
				}
			}

			if (getColor(nd.parent) == 0) {
				System.out.println("Parent is red!");
				if (getUncle(nd) == null || getColor(getUncle(nd)) == 1) {

					// My parent is red, and I either have a black or null
					// uncle
					if (nd.parent.leftChild == nd && getGrandParent(nd).leftChild == nd.parent) {
						// I am a left child of a left child
						setColor(nd.parent,0);
						setColor(nd,1);
						rightRotate(nd.parent);
						System.out.println("Right Rotate!");
					} else if (nd.parent.leftChild != nd && getGrandParent(nd).leftChild == nd.parent) {
						// I am a Right child of left child
						leftRotate(nd);

						setColor(nd, 1); // Set old grand parent's
												// color

						rightRotate(nd);
						setColor(nd.rightChild, 0);
					} else if (nd.parent.rightChild == nd && getGrandParent(nd).rightChild == nd.parent) {

						/// I am a right child of a right child
						setColor(nd.parent,0);
						setColor(nd,1);
						leftRotate(nd.parent);
						System.out.println("left Rotate!");
					
					} else if (nd.parent.leftChild == nd && getGrandParent(nd).rightChild == nd.parent) {
						// I am a left child of right child

						rightRotate(nd);

						setColor(nd, 1);

						leftRotate(nd);
						setColor(nd.leftChild, 0); // Set old grandparent's
													// color

					}
				}
			}

			setColor(root, 1);
		}
	}

	public Node getUncle(Node nd) {

		if (nd.parent != null)
			if (nd.parent.parent != null)
				if (nd.parent.parent.leftChild != nd.parent) {
					// return Uncle
					return nd.parent.parent.leftChild;
				} else {
					return nd.parent.parent.rightChild;
				}
		return null;
	}

	public Node getGrandParent(Node nd) {
		return nd.parent.parent;
	}

	public Node getSibling(Node nd) {
		if (nd.parent != null) {
			if (nd.parent.leftChild != nd) {
				return nd.parent.leftChild;
			} else {
				return nd.parent.rightChild;
			}
		}
		return null;
	}

	
	public Node getNephew(Node nd){
		
		
	}
	public void delete(int data) {
		Node current = root;
		Node parent = null;
		boolean isLeftChild = false;
		while (current.data != data) {
			parent = current;
			if (data > current.data) {
				current = current.rightChild;
				isLeftChild = false;
			} else {
				current = current.leftChild;
				isLeftChild = true;
			}
			if (current == null) {
				System.out.println("Whoops");
				return;
			}
		}
		// Case 1 (Node is a leaf)
		if (current.leftChild == null && current.rightChild == null) {
			if (current == root) {
				root = null;
			} else if (isLeftChild) {
				parent.leftChild = null;
			} else {
				parent.rightChild = null;
			}
		}

		// Case 3 (node has two children, find the lowest value in the subtree
		else if (current.leftChild != null && current.rightChild != null) {

			Node successsor = null;
			Node successsorParent = null;
			Node current1 = current.rightChild;
			while (current1 != null) {
				successsorParent = successsor;
				successsor = current1;
				current1 = current1.leftChild;
			}

			if (successsor != current.rightChild) {
				successsorParent.leftChild = successsor.rightChild;
				successsor.rightChild = current.rightChild;
			}

			if (current == root) {
				root = successsor;
			} else if (isLeftChild) {
				if (current.color == 0) {
					setColor(successsor, 1);
				} else {
					setColor(successsor, 2);
					adjustTreeAfterDeletion(successsor);
				}
				parent.leftChild = successsor;
			} else {
				if (current.color == 0) {
					setColor(successsor, 1);
				} else {
					setColor(successsor, 2);
					adjustTreeAfterDeletion(successsor);
				}
				parent.rightChild = successsor;
			}
			successsor.leftChild = current.leftChild;
		} else if (current.leftChild != null) {
			// We have a left child
			if (isLeftChild) { // current is a left child with a left child
				if (current.color == 0) {
					setColor(current.leftChild, 1);
				} else {
					setColor(current.leftChild, 2);
					adjustTreeAfterDeletion(current.leftChild);
				}
				parent.leftChild = current.leftChild;
			} else {
				if (current.color == 0) {
					setColor(current.leftChild, 1);
				} else {
					setColor(current.leftChild, 2);
					adjustTreeAfterDeletion(current.leftChild);
				}
				// current is a right child with a left child
				parent.rightChild = current.leftChild;

			}
		} else if (current.rightChild != null) {
			// we have one right child
			if (isLeftChild) {
				if (current.color == 0) {
					setColor(current.rightChild, 1);
				} else {
					setColor(current.rightChild, 2);
					adjustTreeAfterDeletion(current.leftChild);
				}
				// current is left child with a right child
				parent.leftChild = current.rightChild;
			} else {
				if (current.color == 0) {
					setColor(current.rightChild, 1);
				} else {
					setColor(current.rightChild, 2);
					adjustTreeAfterDeletion(current.leftChild);
				}
				// current is a right child with a right child
				parent.rightChild = current.rightChild;
			}
		}

	}

	public void adjustTreeAfterDeletion(Node nd) {
		// This method will fix the tree by recoloring and restructuring after a
		// deletion
		//Case One Black sibling with a red child
		if(getColor(getSibling(nd)) == 0 ){
			
		}
	}

	public void inOrderPrint(Node nd) {
		String m = " ";
		if (nd == null) {
			return;
		}
		inOrderPrint(nd.leftChild);
		if (getColor(nd) == 0) {
			m = "red";
		} else if (getColor(nd) == 1) {
			m = "black";
		} else if (getColor(nd) == 2) {
			m = "Doubleblack";
		}
		System.out.print(nd.data + "/" + m + " ");
		inOrderPrint(nd.rightChild);

	}

	public void postOrderPrint(Node nd) {
		if (nd == null) {
			return;
		}
		inOrderPrint(nd.leftChild);
		inOrderPrint(nd.rightChild);
		System.out.print(nd.data + " ");
	}

	public void preOrderPrint(Node nd) {
		if (nd == null) {
			return;
		}
		System.out.print(nd.data + " ");
		inOrderPrint(nd.leftChild);
		inOrderPrint(nd.rightChild);
	}

	public void print() {
		printHelper(root, "");
	}

	/**
	 * Print the BST rooted at root, with indent preceding all output lines. The
	 * nodes are printed in in-order.
	 * 
	 * @param root
	 *            The root of the tree to be printed.
	 * @param indent
	 *            The string to go before output lines.
	 */
	private static void printHelper(Node root, String indent) {
		if (root == null) {
			System.out.println(indent + "null");
			return;
		}

		// Pick a pretty indent.
		String newIndent;
		if (indent.equals("")) {
			newIndent = "-- ";
		} else {
			newIndent = "---" + indent;
		}

		printHelper(root.leftChild, newIndent);
		System.out.println(indent + root.data);
		printHelper(root.rightChild, newIndent);
	}

}
