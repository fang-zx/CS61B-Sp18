public class LinkedListDeque<T> {
    private int size;
    private Node sentinel;

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    private class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node() {}
        public Node(T item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    public LinkedListDeque(LinkedListDeque other) {

    }
    public void addFirst(T item) {
        size++;
        Node newNode = new Node(item);
        newNode.next = sentinel.next;
        newNode.prev = sentinel;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
    }

    public void addLast(T item) {
        size++;
        Node newNode = new Node(item);
        newNode.next = sentinel;
        newNode.prev = sentinel.prev;
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        Node removed = sentinel.next;
        if (removed != sentinel) {
            size--;
            removed.next.prev = sentinel;
            sentinel.next = removed.next;
            removed.prev = null;
            removed.next = null;
        }
        return removed.item;
    }

    public T removeLast() {
        Node removed = sentinel.prev;
        if (removed != sentinel) {
            size--;
            removed.prev.next = sentinel;
            sentinel.prev = removed.prev;
            removed.prev = null;
            removed.next = null;
        }
        return removed.item;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node  p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    private T getRecursizeHelper(Node node, int index) {
        if (node == null) return null;
        if (index == 0) return node.item;
        return getRecursizeHelper(node.next, index-1);
    }
    public T getRecursize(int index) {
        return getRecursizeHelper(sentinel.next, index);
    }
}
