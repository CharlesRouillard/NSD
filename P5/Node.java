package kcore;

import java.util.ArrayList;

/**
     * Implements a node of the Fibonacci heap. It holds the information
     * necessary for maintaining the structure of the heap. It acts as
     * an opaque handle for the data element, and serves as the key to
     * retrieving the data from the heap.
     *
     * @author  Nathan Fiedler
     */
public class Node {
        /** Data object for this node, holds the key value. */
        private Object data;
        /** Key value for this node. */
        private Comparable key;
        /** Parent node. */
        Node parent;
        /** First child node. */
        private Node child;
        /** Right sibling node. */
        Node right;
        /** Left sibling node. */
        Node left;
        /** Number of children of this node. */
        private int degree;
        /** True if this node has had a child removed since this node was
         * added to its parent. */
        private boolean mark;

        /**
         * Two-arg constructor which sets the data and key fields to the
         * passed arguments. It also initializes the right and left pointers,
         * making this a circular doubly-linked list.
         *
         * @param  data  data object to associate with this node
         * @param  key   key value for this data object
         */
        public Node(Object data, Comparable key) {
            this.setData(data);
            this.setKey(key);
            setRight(this);
            setLeft(this);
        }

		public Comparable getKey() {return key;}
		public Object getData() {return data;}

        /**
         * Performs a cascading cut operation. Cuts this from its parent
         * and then does the same for its parent, and so on up the tree.
         *
         * <p><em>Running time: O(log n)</em></p>
         *
         * @param  min  the minimum heap node, to which nodes will be added.
         */
        public void cascadingCut(Node min) {
            Node z = getParent();
            // if there's a parent...
            if (z != null) {
                if (mark) {
                    // it's marked, cut it from parent
                    z.cut(this, min);
                    // cut its parent as well
                    z.cascadingCut(min);
                } else {
                    // if y is unmarked, set it marked
                    mark = true;
                }
            }
        }

        /**
         * The reverse of the link operation: removes x from the child
         * list of this node.
         *
         * <p><em>Running time: O(1)</em></p>
         *
         * @param  x    child to be removed from this node's child list
         * @param  min  the minimum heap node, to which x is added.
         */
        public void cut(Node x, Node min) {
            // remove x from childlist and decrement degree
            x.getLeft().setRight(x.getRight());
            x.getRight().setLeft(x.getLeft());
            setDegree(getDegree() - 1);
            // reset child if necessary
            if (getDegree() == 0) {
                setChild(null);
            } else if (getChild() == x) {
                setChild(x.getRight());
            }
            // add x to root list of heap
            x.setRight(min);
            x.setLeft(min.getLeft());
            min.setLeft(x);
            x.getLeft().setRight(x);
            // set parent[x] to nil
            x.setParent(null);
            // set mark[x] to false
            x.mark = false;
        }

        /**
         * Make this node a child of the given parent node. All linkages
         * are updated, the degree of the parent is incremented, and
         * mark is set to false.
         *
         * @param  parent  the new parent node.
         */
        public void link(Node parent) {
            // Note: putting this code here in Node makes it 7x faster
            // because it doesn't have to use generated accessor methods,
            // which add a lot of time when called millions of times.
            // remove this from its circular list
            getLeft().setRight(right);
            getRight().setLeft(left);
            // make this a child of x
            this.setParent(parent);
            if (parent.getChild() == null) {
                parent.setChild(this);
                setRight(this);
                setLeft(this);
            } else {
                setLeft(parent.getChild());
                setRight(parent.getChild().getRight());
                parent.getChild().setRight(this);
                getRight().setLeft(this);
            }
            // increase degree[x]
            parent.setDegree(parent.getDegree() + 1);
            // set mark false
            mark = false;
        }

		public void addToList(ArrayList<Node> l) {
			Node cur = this;
			do {
				l.add(cur);
				if (cur.getChild() != null) cur.getChild().addToList(l);
				cur = cur.getRight();
			} while (cur != this);
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		public int getDegree() {
			return degree;
		}

		public void setDegree(int degree) {
			this.degree = degree;
		}

		public void setKey(Comparable key) {
			this.key = key;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getChild() {
			return child;
		}

		public void setChild(Node child) {
			this.child = child;
		}
    }