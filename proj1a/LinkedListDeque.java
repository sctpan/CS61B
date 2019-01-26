public class LinkedListDeque<T> {
    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

        public Node() {
            this.next = null;
            this.prev = null;
        }
    }

    private Node<T> sentinel;
    private int totalSize;

    public LinkedListDeque() {
        sentinel = new Node<T>();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        totalSize = 0;
    }

    public void addLast(T item) {
        sentinel.prev.next = new Node<T>(item, sentinel.prev, sentinel);
        sentinel.prev = sentinel.prev.next;
        this.totalSize++;
    }

    public void addFirst(T item) {
        Node<T> first = new Node<T>(item, sentinel, sentinel.next);
        sentinel.next = first;
        first.next.prev = first;
        this.totalSize++;
    }

    public boolean isEmpty() {
        if (this.totalSize == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return totalSize;
    }

    public void printDeque() {
        if (totalSize == 0) {
            System.out.println("empty deque!");
            return;
        }
        Node<T> p = sentinel.next;
        System.out.print(p.item);
        while (p.next != sentinel) {
            p = p.next;
            System.out.print(" " + p.item);
        }
        System.out.print("\n");
    }

    public T removeFirst() {
        if (totalSize == 0) {
            return null;
        }
        totalSize--;
        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return item;
    }

    public T removeLast() {
        if (totalSize == 0) {
            return null;
        }
        totalSize--;
        T item = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        return item;
    }

    public T get(int index) {
        if (totalSize <= index) {
            return null;
        }
        Node<T> p = sentinel.next;
        while ((index--) > 0) {
            p = p.next;
        }
        return p.item;
    }

    private T getRecursiveHelper(int index, Node<T> p) {
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(index - 1, p.next);
    }

    public T getRecursive(int index) {
        if (index >= totalSize || index < 0) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }
}