package bst;

/**
 * The {@code BinarySearchTree} class represents a
 *
 * @param <E>
 * @author eneashallsten
 */
public class BinarySearchTree<E extends Comparable<? super E>> {
	BinaryNode<E> root;
	int size;

	/**
	 * Constructs an empty binary searchtree.
	 */
	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	/**
	 * Inserts the specified element in the tree if no duplicate exists.
	 * 
	 * @param x
	 *            the element to be inserted
	 * @return true if the the element was inserted
	 */
	public boolean add(E x) {
		if (root == null) {
			root = new BinaryNode<E>(x);
			size++;
			return true;
		} else {
			size++;
			 return root.recAdd(x);
		}
	}

	/**
	 * Computes the height of tree.
	 * 
	 * @return the height of the tree
	 */
	public int height() {
		return recHeight(root);
	}

	/**
	 * Recursive method for the method height
	 * 
	 * @param root
	 *            in which BinaryNode the height is to be calculated
	 * @return the height of the tree
	 */
	private int recHeight(BinaryNode<E> root) {
		if (root == null) {
			return 0;
		} else {
			return 1 + Math.max(recHeight(root.left), recHeight(root.right)); // Returns the biggest value of them both
		}
	}

	/**
	 * @return the number of elements in this tree
	 */
	public int size() {
		return size;
	}

	/**
	 * Print tree contents in inorder.
	 */
	public void printTree() {
		printInorder(root);
	}

	/**
	 * Recursive method to print the tree in inorder.
	 * 
	 * @param n
	 *            the tree which is it be printed
	 */
	private void printInorder(BinaryNode<E> n) {
		if (n != null) {
			printInorder(n.left);
			System.out.println(n.element);
			printInorder(n.right);
		}
	}

	/**
	 * Builds a complete tree from the elements in the tree.
	 */
	public void rebuild() {
		@SuppressWarnings("unchecked")
		E[] a = (E[]) new Comparable[size];
		toArray(root, a, 0);
		root = buildTree(a, 0, a.length - 1);
	}

	/*
	 * Adds all elements from the tree rooted at n in inorder to the array a
	 * starting at a[index]. Returns the index of the last inserted element + 1 (the
	 * first empty position in a).
	 */
	private int toArray(BinaryNode<E> n, E[] a, int index) {
		if (n != null) {
			index = toArray(n.left, a, index);
			a[index++] = n.element;
			index = toArray(n.right, a, index);
		}
		return index;
	}

	/*
	 * Builds a complete tree from the elements a[first]..a[last]. Elements in the
	 * array a are assumed to be in ascending order. Returns the root of tree.
	 */
	private BinaryNode<E> buildTree(E[] a, int first, int last) {
		if (first > last) {
			return null;
		} else {
			int mid = first + ((last - first) / 2);
			BinaryNode<E> root = new BinaryNode<E>(a[mid]);
			root.left = buildTree(a, first, mid - 1);
			root.right = buildTree(a, mid + 1, last);
			return root;
		}
	}

	/**
	 * The {@code BinaryNode} class represents a node which have a value of the
	 * generic type that is chosen by the user. The node also have references to two
	 * other nodes, left and right. They are of the same type as the element itself.
	 *
	 * @param <E>
	 *            element type
	 * @author eneashallsten
	 */
	static class BinaryNode<E extends Comparable<? super E>> {
		E element;
		BinaryNode<E> left;
		BinaryNode<E> right;

		private BinaryNode(E element) {
			this.element = element;
		}

		/**
		 * Recursive method for the method add
		 * 
		 * @param root
		 *            the BinaryNode in which
		 * @param x
		 *            the element it to be added
		 * @return true if the element was added
		 */
		private boolean recAdd(E x) {
			if (x.compareTo(element) < 0) {
				if (left == null) {
					left = new BinaryNode<E>(x);
					return true;
				}
				return left.recAdd(x);
			} else if (x.compareTo(element) > 0) {
				if (right == null) {
					right = new BinaryNode<E>(x);
					return true;
				}
				return right.recAdd(x);
			}
			return false;
		}

	}

	public static void main(String[] args) {
		BSTVisualizer bstv = new BSTVisualizer("Eneas fina fönster", 300, 300);
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		bst.add(1);
		bst.add(2);
		bst.add(3);
		bst.add(4);
		bst.add(5);
		bst.add(6);
		bst.add(7);
		bst.add(8);
		bst.add(9);
		bst.add(10);
		bst.add(11);
		bst.rebuild();
		bstv.drawTree(bst);

	}

}
