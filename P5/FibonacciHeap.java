package kcore;

import java.util.ArrayList;

/**
 * This class implements a Fibonacci heap data structure. Much of the
 * code in this class is based on the algorithms in Chapter 21 of the
 * "Introduction to Algorithms" by Cormen, Leiserson, Rivest, and Stein.
 * The amortized running time of most of these methods is O(1), making
 * it a very fast data structure. Several have an actual running time
 * of O(1). removeMin() and delete() have O(log n) amortized running
 * times because they do the heap consolidation.
 * @author  Nathan Fiedler
 */
public class FibonacciHeap{
    /** Points to the minimum node in the heap. */
    private Node min;
    /** Number of nodes in the heap. If the type is ever widened,
     * (e.g. changed to long) then recalcuate the maximum degree
     * value used in the consolidate() method. */
    private int n;

    /**
     * Removes all elements from this heap.
     *
     * <p><em>Running time: O(1)</em></p>
     */
    public void clear() {
        min = null;
        n = 0;
    }

    /**
     * Consolidates the trees in the heap by joining trees of equal
     * degree until there are no more trees of equal degree in the
     * root list.
     *
     * <p><em>Running time: O(log n) amortized</em></p>
     */
    private void consolidate() {
        // The magic 45 comes from log base phi of Integer.MAX_VALUE,
        // which is the most elements we will ever hold, and log base
        // phi represents the largest degree of any root list node.
        Node[] A = new Node[45];

        // For each root list node look for others of the same degree.
        Node start = min;
        Node w = min;
        do {
            Node x = w;
            // Because x might be moved, save its sibling now.
            Node nextW = w.getRight();
            int d = x.getDegree();
            while (A[d] != null) {
                // Make one of the nodes a child of the other.
                Node y = A[d];
                if (x.getKey().compareTo(y.getKey()) > 0) {
                    Node temp = y;
                    y = x;
                    x = temp;
                }
                if (y == start) {
                    // Because removeMin() arbitrarily assigned the min
                    // reference, we have to ensure we do not miss the
                    // end of the root node list.
                    start = start.getRight();
                }
                if (y == nextW) {
                    // If we wrapped around we need to check for this case.
                    nextW = nextW.getRight();
                }
                // Node y disappears from root list.
                y.link(x);
                // We've handled this degree, go to next one.
                A[d] = null;
                d++;
            }
            // Save this node for later when we might encounter another
            // of the same degree.
            A[d] = x;
            // Move forward through list.
            w = nextW;
        } while (w != start);

        // The node considered to be min may have been changed above.
        min = start;
        // Find the minimum key again.
        for (Node a : A) {
            if (a != null && a.getKey().compareTo(min.getKey()) < 0) {
                min = a;
            }
        }
    }

    /**
     * Decreases the key value for a heap node, given the new value
     * to take on. The structure of the heap may be changed, but will
     * not be consolidated.
     *
     * <p><em>Running time: O(1) amortized</em></p>
     *
     * @param  x  node to decrease the key of
     * @param  k  new key value for node x
     * @exception  IllegalArgumentException
     *             if k is larger than x.key value.
     */
    public void decreaseKey(Node x, Object newData, Comparable k) {
        decreaseKey(x, newData, k, false);
    }

    /**
     * Decrease the key value of a node, or simply bubble it up to the
     * top of the heap in preparation for a delete operation.
     *
     * @param  x       node to decrease the key of.
     * @param  k       new key value for node x.
     * @param  delete  true if deleting node (in which case, k is ignored).
     */
    private void decreaseKey(Node x, Object newData, Comparable k, boolean delete) {
        if (!delete && k.compareTo(x.getKey()) > 0) {
            throw new IllegalArgumentException("cannot increase key value");
        }
        x.setKey(k);
		x.setData(newData);
        Node y = x.getParent();
        if (y != null && (delete || k.compareTo(y.getKey()) < 0)) {
            y.cut(x, min);
            y.cascadingCut(min);
        }
        if (delete || k.compareTo(min.getKey()) < 0) {
            min = x;
        }
    }

    /**
     * Deletes a node from the heap given the reference to the node.
     * The trees in the heap will be consolidated, if necessary.
     *
     * <p><em>Running time: O(log n) amortized</em></p>
     *
     * @param  x  node to remove from heap.
     */
    public void delete(Node x) {
        // make x as small as possible
        decreaseKey(x, x.getData(), 0, true);
        // remove the smallest, which decreases n also
        removeMin();
    }

    /**
     * Tests if the Fibonacci heap is empty or not. Returns true if
     * the heap is empty, false otherwise.
     *
     * <p><em>Running time: O(1)</em></p>
     *
     * @return  true if the heap is empty, false otherwise.
     */
    public boolean isEmpty() {
        return min == null;
    }

    /**
     * Inserts a new data element into the heap. No heap consolidation
     * is performed at this time, the new node is simply inserted into
     * the root list of this heap.
     *
     * <p><em>Running time: O(1)</em></p>
     *
     * @param  x    data object to insert into heap.
     * @param  key  key value associated with data object.
     * @return newly created heap node.
     */
    public Node insert(Object x, Comparable key) {
        Node node = new Node(x, key);
        // concatenate node into min list
        if (min != null) {
            node.setRight(min);
            node.setLeft(min.getLeft());
            min.setLeft(node);
            node.getLeft().right = node;
            if (key.compareTo(min.getKey()) < 0) {
                min = node;
            }
        } else {
            min = node;
        }
        n++;
        return node;
    }

    /**
     * Returns the smallest element in the heap. This smallest element
     * is the one with the minimum key value.
     *
     * <p><em>Running time: O(1)</em></p>
     *
     * @return  heap node with the smallest key, or null if empty.
     */
    public Node min() {
        return min;
    }

    /**
     * Removes the smallest element from the heap. This will cause
     * the trees in the heap to be consolidated, if necessary.
     *
     * <p><em>Running time: O(log n) amortized</em></p>
     *
     * @return  data object with the smallest key.
     */
    public Object removeMin() {
        Node z = min;
        if (z == null) {
            return null;
        }
        if (z.getChild() != null) {
            z.getChild().parent = null;
            // for each child of z do...
            for (Node x = z.getChild().right; x != z.getChild(); x = x.getRight()) {
                // set parent[x] to null
                x.setParent(null);
            }
            // merge the children into root list
            Node minleft = min.getLeft();
            Node zchildleft = z.getChild().getLeft();
            min.setLeft(zchildleft);
            zchildleft.setRight(min);
            z.getChild().left = minleft;
            minleft.setRight(z.getChild());
        }
        // remove z from root list of heap
        z.getLeft().right = z.getRight();
        z.getRight().setLeft(z.getLeft());
        if (z == z.getRight()) {
            min = null;
        } else {
            min = z.getRight();
            consolidate();
        }
        // decrement size of heap
        n--;
        return z.getData();
    }

    /**
     * Returns the size of the heap which is measured in the
     * number of elements contained in the heap.
     *
     * <p><em>Running time: O(1)</em></p>
     *
     * @return  number of elements in the heap.
     */
    public int size() {
        return n;
    }

	public int count() {
		return n;
	}

    /**
     * Joins two Fibonacci heaps into a new one. No heap consolidation is
     * performed at this time. The two root lists are simply joined together.
     *
     * <p><em>Running time: O(1)</em></p>
     *
     * @param  H1  first heap
     * @param  H2  second heap
     * @return  new heap containing H1 and H2
     */
    public static FibonacciHeap union(FibonacciHeap H1, FibonacciHeap H2) {
        FibonacciHeap H = new FibonacciHeap();
        if (H1 != null && H2 != null) {
            H.min = H1.min;
            if (H.min != null) {
                if (H2.min != null) {
                    H.min.getRight().setLeft(H2.min.getLeft());
                    H2.min.getLeft().right = H.min.getRight();
                    H.min.setRight(H2.min);
                    H2.min.setLeft(H.min);
                    if (H2.min.getKey().compareTo(H1.min.getKey()) < 0) {
                        H.min = H2.min;
                    }
                }
            } else {
                H.min = H2.min;
            }
            H.n = H1.n + H2.n;
        }
        return H;
    }

	public ArrayList<Node> nodeList() {
		ArrayList<Node> l = new ArrayList<Node>();
		if (min != null) min.addToList(l);
		return l;
	}
}