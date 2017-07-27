public class CircularLinkedList<T> extends LinkedList<T> {
    public CircularLinkedList(T object) {
        super(object);
        setNext(this);
    }

    public CircularLinkedList() {
        super();
    }

    public boolean add(T o) {
        /*
         * The following is done when there is no next
         * element, and the the data is null. This situation
         * occurs after .clear()/
         */
        if (getContents() == null && getNext() == null) {
            System.out.println("empty");
            setContents(o);
            return true;
        }

        LinkedList<T> nextNode = this;
        /*
         * We start at 'next' because there is no scenario in which
         * the first element is empty (it is either 'null' or it
         * has data — that is, if you don't count 'null' as data!)
         */
        while (nextNode.getNext() != this) {
            nextNode = nextNode.getNext();
        }
        LinkedList<T> nodeToBeInserted = new LinkedList<T>(o);
        nodeToBeInserted.setNext(this);
        nextNode.setNext(nodeToBeInserted);

        return true;
    }
}
