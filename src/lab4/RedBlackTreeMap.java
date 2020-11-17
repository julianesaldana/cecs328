package lab4;


// A Map ADT structure using a red-black tree, where keys must implement
// Comparable.
// The key type of a map must be Comparable. Values can be any type.
public class RedBlackTreeMap<TKey extends Comparable<TKey>, TValue> {
	// A Node class.
	private class Node {
		private TKey mKey;
		private TValue mValue;
		private Node mParent;
		private Node mLeft;
		private Node mRight;
		private boolean mIsRed;

		// Constructs a new node with NIL children.
		public Node(TKey key, TValue data, boolean isRed) {
			mKey = key;
			mValue = data;
			mIsRed = isRed;

			mLeft = NIL_NODE;
			mRight = NIL_NODE;
		}

		@Override
		public String toString() {
			return mKey + " : " + mValue + " (" + (mIsRed ? "red)" : "black)");
		}
	}

	private Node mRoot;
	private int mCount;

	// Rather than create a "blank" black Node for each NIL, we use one shared node for all NIL leaves
	private final Node NIL_NODE = new Node(null, null, false);

	//////////////////// I give you these utility functions for free.

	// Get the # of keys in the tree.
	public int getCount() {
		return mCount;
	}

	// Finds the value associated with the given key.
	public TValue find(TKey key) {
		Node n = bstFind(key, mRoot); // find the Node containing the key if any
		if (n == null || n == NIL_NODE)
			throw new RuntimeException("Key not found");
		return n.mValue;
	}

	/////////////////// You must finish the rest of these methods.

	// Inserts a key/value pair into the tree, updating the red/black balance of nodes as necessary. Starts with a
	// normal BST insert, then adjusts.
	public void add(TKey key, TValue data) {
		Node n = new Node(key, data, true); // nodes start red

		// normal BST insert; n will be placed into its initial position.
		// returns false if an existing node was updated (no rebalancing needed)
		boolean insertedNew = bstInsert(n, mRoot);
		if (!insertedNew)
			return;

		// check cases 1-5 for balance violations.
		checkBalance(n);
	}

	// Applies rules 1-5 to check the balance of a tree with newly inserted node n.
	private void checkBalance(Node n) {
		// case 1: new node is root.
		if (n == mRoot) {
			n.mIsRed = false;
			return;
		}

		// case 2: parent is black
		if (!n.mParent.mIsRed){
			return;
		}

		// case 3: parent, child, uncle are all red. recolor parent and uncle to black and grandparent to red
		if (getUncle(n) != null && getUncle(n).mIsRed && n.mParent.mIsRed) {
			n.mParent.mIsRed = false;
			getUncle(n).mIsRed = false;
			getGrandparent(n).mIsRed = true;
			checkBalance(getGrandparent(n));
		}

		// case 4, parent is red and uncle is black
		Node parent = n.mParent;
		Node grandparent = getGrandparent(n);

		if (n == parent.mRight && parent == grandparent.mLeft){
			singleRotateLeft(parent);
			n = n.mLeft;
		}
		else if (n == parent.mLeft && parent == grandparent.mRight){
			singleRotateRight(parent);
			n = n.mRight;
		}

		// case 5, parent is red and uncle is black, child is now parent of
		if (n == parent.mLeft && grandparent.mLeft == parent)
			singleRotateRight(grandparent);
		else if (n == parent.mRight && grandparent.mRight == parent)
			singleRotateLeft(grandparent);

		parent.mIsRed = false;
		grandparent.mIsRed = true;
	}

	// Returns true if the given key is in the tree.
	public boolean containsKey(TKey key) {
		Node n = bstFind(key, mRoot); // find the Node containing the key if any
		if (n == null || n == NIL_NODE)
			return false;
		return true;
	}

	// Prints a pre-order traversal of the tree's nodes, printing the key, value, and color of each node.
	public void printStructure(Node n) {
		if (n != NIL_NODE) {
			System.out.println(n.toString());
			printStructure(n.mLeft);
			printStructure(n.mRight);
		}
	}

	// Returns the Node containing the given key. Recursive.
	private Node bstFind(TKey key, Node currentNode) {
		// Given a key to find and a node to start at,
		// proceed left/right from the current node until finding a node whose
		// key is equal to the given key.
		// Base Cases: root is null or key is present at root
		if (currentNode==null || currentNode.mKey == key)
			return currentNode;

		// Key is greater than root's key
		if (currentNode.mKey.compareTo(key) > 0)
			return bstFind(key, currentNode.mRight);

		// Key is smaller than root's key
		return bstFind(key, currentNode.mLeft);
	}



	//////////////// These functions are needed for insertion cases.

	// Gets the grandparent of n.
	private Node getGrandparent(Node n) {
		if (n.mParent.mParent != null)
			return n.mParent.mParent;
		return null;
	}

	// Gets the uncle (parent's sibling) of n.
	private Node getUncle(Node n) {
		Node grandParent = getGrandparent(n);
		assert grandParent != null;
		if (grandParent.mLeft == n.mParent)
			return grandParent.mRight;
		else if (grandParent.mRight == n.mParent)
			return grandParent.mLeft;
		return null;
	}

	// Rotate the tree right at the given node.
	private void singleRotateRight(Node n) {
		Node l = n.mLeft, lr = l.mRight, p = n.mParent;
		n.mLeft = lr;
		lr.mParent = n;
		l.mRight = n;
		n.mParent = l;

		if (p == null) { // n is root
			mRoot = l;
		}
		else if (p.mLeft == n) {
			p.mLeft = l;
		}
		else {
			p.mRight = l;
		}

		l.mParent = p;
	}

	// Rotate the tree left at the given node.
	private void singleRotateLeft(Node n) {
		Node r = n.mRight, rl = r.mLeft, p = n.mParent;
		n.mRight = rl;
		rl.mParent = n;
		r.mLeft = n;
		n.mParent = r;

		if (p == null) { // n is root
			mRoot = r;
		}
		else if (p.mRight == n) {
			p.mRight = r;
		}
		else {
			p.mLeft = r;
		}

		r.mParent = p;
	}


	// This method is used by insert. It is complete.
	// Inserts the key/value into the BST, and returns true if the key wasn't previously in the tree.
	private boolean bstInsert(Node newNode, Node currentNode) {
		if (mRoot == null) {
			// case 1
			mRoot = newNode;
			return true;
		} 
		else {
			int compare = currentNode.mKey.compareTo(newNode.mKey);
			if (compare < 0) {
				// newNode is larger; go right.
				if (currentNode.mRight != NIL_NODE) {
					return bstInsert(newNode, currentNode.mRight);
				}
				else {
					currentNode.mRight = newNode;
					newNode.mParent = currentNode;
					mCount++;
					return true;
				}
			} 
			else if (compare > 0) {
				if (currentNode.mLeft != NIL_NODE) {
					return bstInsert(newNode, currentNode.mLeft);
				}
				else {
					currentNode.mLeft = newNode;
					newNode.mParent = currentNode;
					mCount++;
					return true;
				}
			} 
			else {
				// found a node with the given key; update value.
				currentNode.mValue = newNode.mValue;
				return false; // did NOT insert a new node.
			}
		}
	}


	public Node getmRoot(){
		return mRoot;
	}
}
