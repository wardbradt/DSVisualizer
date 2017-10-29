/**
 * Authors: Ward Bradt
 * Finish Date: April 12, 2017
 *
 */
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryTree<T> extends AbstractCollection<T> implements Collection<T> {
    private T contents;
    private BinaryTree<T> left, right;

    public BinaryTree() {
        contents = null;
        right = null;
        left = null;
    }

    public BinaryTree(T obj) {
        contents = obj;
        right = null;
        left = null;
    }

    public BinaryTree(BinaryTree<T> r) {
        contents = r.contents;
        left = r.left;
        right = r.right;
    }

    public void clear() {
    	contents = null;
    	right = null;
    	left = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!BinaryTree.class.isAssignableFrom(o.getClass())) {
            return false;
        }
        final BinaryTree<T> other = (BinaryTree<T>)o;

        Queue<BinaryTree<T>> otherNodes = new Queue<>(other);
        Queue<BinaryTree<T>> thisNodes = new Queue<>(this);

        while (!thisNodes.isEmpty() || !other.isEmpty()) {
            if (thisNodes.peek().contents != otherNodes.peek().contents) return false;

            if (thisNodes.peek().contents != null) {
                if (thisNodes.peek().left != null) {
                    thisNodes.add(thisNodes.peek().left);
                }
                if (thisNodes.peek().right != null)
                    thisNodes.add(thisNodes.peek().right);
                thisNodes.iterator().next();
            }
            else {
                thisNodes.iterator().next();
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Add <code>T item</code> to the <code>BinaryTree</code>. At every <code>Node</code>, first checks if the left
     * node == null then checks the right node == null. If neither are, it
     * goes to the right and checks again.
     *
     * @param item the T item to be added to the tree
     * @return true if T item is added.
     */
    public boolean add(T item) {
        return add(new BinaryTree<>(item));
//        if (contents == null && left == null && right == null) {
//            contents = item;
//        }
//        if (left == null) {
//            left = new BinaryTree<T>(item);
//            return true;
//        }
//        if (right == null) {
//            right = new BinaryTree<T>(item);
//            return true;
//        }
//
//        return left.add(item) || right.add(item);
    }

//    public boolean addX(T item) {
//        Queue<BinaryTree<T>> nodes = new Queue<>(this);
//        // A breadth first traversal in this form never looks at root.
//        while (!nodes.isEmpty()) {
//            if (nodes.peek() != null) {
//                if (nodes.peek().left != null) {
//                    nodes.add(nodes.peek().left);
//                }
//                if (nodes.peek().right != null)
//                    nodes.add(nodes.peek().right);
////                breadth.add(nodes.iterator().next().contents);
//            }
//            else {
//                nodes.iterator().next();
//            }
//        }
//    }

    public boolean add(BinaryTree<T> tree) {
        /*
         * The following is done when there is no left or right
         * element, and the the data/ contents is null. This situation
         * occurs after .clear()/ instantiation with a zero-args constructor
         */
        if (contents == null && left == null && right == null) {
            copy(tree);
            return true;
        }
        Queue<BinaryTree<T>> nodes = new Queue<>(this);
        while (!nodes.isEmpty()) {
            if (nodes.peek().left != null) {
                nodes.add(nodes.peek().left);
            } else {
                nodes.peek().left = tree;
                return true;
            }
            if (nodes.peek().right != null) {
                nodes.add(nodes.peek().right);
            } else {
                nodes.peek().right = tree;
                return true;
            }
            nodes.iterator().next();
        }
        return true;
//        if (left == null) {
//            left = tree;
//            return true;
//        }
//        if (right == null) {
//            right = tree;
//            return true;
//        }
//
//        return left.add(tree) || right.add(tree);
    }

    /**
     * Removes the first instance of <code>T item</code> in a breadth-first traversal.
     *
     * @param o is the item to be removed
     * @throws NullPointerException if item is null or if item is not in the binary tree.
     */
    @Override
    public boolean remove(Object o) throws NoSuchElementException {
        Queue<BinaryTree<T>> nodes = new Queue<BinaryTree<T>>(this);
        // base case outside of while loop:
        if (nodes.peek().contents == o) {
            BinaryTree<T> swapped = new BinaryTree<T>();
            // if the head node has any children
            if (left != null || right != null) {
                swapped = left;
                // swapped must be at the end of a branch.
                while (swapped != null) {
                    // If swapped is not at the end of a branch
                    if (swapped.left == null || swapped.right == null) {
                        swapped = swapped.left;
                    }
                }

                // todo these next T/Cs can probably be removed
                // Swap swapped and removed.
                try {
                    swapped.right = right;
                } catch (NullPointerException ifNull) {
                }
                try {
                    swapped.left = left;
                } catch (NullPointerException ifNull) {
                }
            }

            copy(swapped);

            return true;
        }

        while (!nodes.isEmpty()) {
            if (nodes.peek().left != null) {
                if (nodes.peek().left.contents == o) {
                    return removeNodeFromTree(nodes.peek(), true);
                }
                nodes.add(nodes.peek().left);
            }
            if (nodes.peek().right != null) {
                if (nodes.peek().right.contents == o)
                    return removeNodeFromTree(nodes.peek(), false);
                nodes.add(nodes.peek().right);
            }
            nodes.iterator().next();
        }
        // throws exception if tree does not contain item
        throw new NoSuchElementException("item is not in the BinaryTree.");
    }

    private boolean removeNodeFromTree(BinaryTree<T> nodeParent, boolean isLeft) {
        BinaryTree<T> removed;
        if (isLeft) removed = nodeParent.left;
        else removed = nodeParent.right;

        BinaryTree<T> swapped = new BinaryTree<T>();
        // if removed is not at the end of a branch
        if (removed.left != null || removed.right != null) {
            swapped = removed.left;
            // swapped must be at the end of a branch. iterate to the bottom right-most BinaryTree/ node
            // while either swapped.left or right is not null
            while (swapped.left != null || swapped.right != null) {
                if (swapped.right != null) swapped = swapped.right;
                else swapped = swapped.left;
            }

            if (isLeft) nodeParent.left.contents = swapped.contents;
            else nodeParent.right.contents = swapped.contents;
        }

        return true;
    }

    @Override
    public boolean isEmpty() {
        return size() == 1 && contents == null;
    }

    @Override
    public int size() {
        int sum = 1;
        if (left != null) {
            sum += left.size();
        }
        if (right != null) {
            sum += right.size();
        }
        return sum;
    }

    /**
     * Determines if any <code>Node</code>s in the tree contain a specified <code>Object</code>.
     *
     * @param o is the o the method searches for.
     * @return if <code>o</code> is in the <code>BinaryTree</code>
     */
    @Override
    public boolean contains(Object o) {
        if (contents != o) {
            // create binary tree from left and right then create a branched recursion
            // to check if each subbranch contains o.
            if (left != null) {
                if (left.contains(o)) return true;
            }
            if (right != null) {
                if (right.contains(o)) return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return breadthFirst().iterator();
    }

    @Override
    public Object[] toArray() {
        Queue<T> breadthArray = breadthFirst();

        return breadthArray.toArray();
    }

//    /**
//     * <code>get(T item)</code> searches for an <code>Object</code> in the <code>BinaryTree</code>.
//     * Received help from
//     * http://stackoverflow.com/questions/5262308/how-do-implement-a-breadth-first-traversal
//     *
//     * @param item is what is being searched for in the <code>BinaryTree</code>
//     * @return the first breadth-first reference to T item if it is in the BinaryTree
//     * @throws NullPointerException if item == null or if item is not in the BinaryTree
//     */
//    public Node<T> get(T item) throws NullPointerException {
//        if (item == null) throw new NullPointerException("parameters cannot be null!");
//        Queue<Node<T>> nodes = new Queue<>(root);
//        while (!nodes.isEmpty()) {
//            if (nodes.peek().getContents() == item)
//                return nodes.iterator().next();
//            if (nodes.peek().getLeft() != null)
//                nodes.add(nodes.peek().getLeft());
//            if (nodes.peek().getRight() != null)
//                nodes.add(nodes.peek().getRight());
//            nodes.iterator().next();
//        }
//        throw new NullPointerException("item is not in the BinaryTree.");
//    }

    public Queue<T> breadthFirst() {
        Queue<T> breadth = new Queue<T>();
        Queue<BinaryTree<T>> nodes = new Queue<>(this);
        // A breadth first traversal in this form never looks at root.
        while (!nodes.isEmpty()) {
            if (nodes.peek().left != null) {
                nodes.add(nodes.peek().left);
            }
            if (nodes.peek().right != null)
                nodes.add(nodes.peek().right);
            breadth.add(nodes.iterator().next().contents);
        }
        return breadth;
    }
    
    /**
     * Returns a depth-first iterable object of the branch tree.
     * <code>Queue</code> class implements <code>Iterable<T></code> using <code>java.util.Iterator</code>
     *
     * @return a breadth-first <code>Iterable</code> object of the tree.
     */
    public Queue<T> depthFirst() {
        Stack<BinaryTree<T>> nodes = new Stack<BinaryTree<T>>(this);
        Queue<T> depth = new Queue<T>(contents);
        if (nodes.peek().left != null) {
            depth.addQueue(new BinaryTree<>(nodes.peek().left).depthFirst());
        }
        if (nodes.peek().right != null) {
            depth.addQueue(new BinaryTree<>(nodes.peek().right).depthFirst());
        }
        nodes.pop();
        return depth;
    }

    public void setContents(T contents) {
        this.contents = contents;
    }

    public T getContents() {
        return contents;
    }

    public BinaryTree<T> getLeft() {
        return left;
    }

    public BinaryTree<T> getRight() {
        return right;
    }

    private void copy(BinaryTree<T> other) {
        this.contents = other.contents;
        this.left = other.left;
        this.right = other.right;
    }
}
