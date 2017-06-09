package Comp282Proj1;

public class Main {
	public static void main(String[] args) {
		RedBlackTree BT = new RedBlackTree();

		BT.insert(100);
		BT.insert(110);

		BT.insert(120);

		BT.insert(115);
		BT.insert(117);
		BT.insert(116);
		BT.insert(90);
		BT.insert(80);
		BT.insert(85);
		BT.insert(70);
		BT.insert(65);
		BT.print();

		BT.inOrderPrint(RedBlackTree.root);
		BT.delete(117);
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");

		BT.print();

		BT.inOrderPrint(RedBlackTree.root);

	}
}
