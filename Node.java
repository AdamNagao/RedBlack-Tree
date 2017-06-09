package Comp282Proj1;

public class Node {
	
	Node leftChild;
	Node rightChild;
	Node parent;
	int data;
	int color = 0;
	public Node(int D, Node left, Node right){
		leftChild = left;
		rightChild = right;
		data = D;
		color = 0;
	}
	public Node(int D){
		this(D,null,null);
	}

}
