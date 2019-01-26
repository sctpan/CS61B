public class ArrayDeque<T> {
    private T[] array;
    private int first = 0;
    private int last = 1;
    private int size = 0;
    private int capacity = 8;

    private void copyTo(T[] b) {
        int bIndex = 0;
        int index = (first + 1) % capacity;
        int tempSize = size;
        while ((tempSize--) > 0) {
            b[bIndex++] = array[index];
            index = (index + 1) % capacity;
        }
    }

    private void resize() {
        T[] largerArray = (T[]) new Object[size * 2];
        copyTo(largerArray);
        array = largerArray;
        capacity = size * 2;
        first = capacity - 1;
        last = size;
    }

    private void shrink() {
        T[] smallerArray = (T[]) new Object[capacity / 2];
        copyTo(smallerArray);
        array = smallerArray;
        capacity = capacity / 2;
        first = capacity - 1;
        last = size;
    }

    public ArrayDeque() {
        array = (T[]) new Object[capacity];
    }

    public void addFirst(T item) {
        array[first] = item;
        first = first - 1 < 0 ? capacity - 1 : first - 1;
        size++;
        if (size == capacity) {
            resize();
        }
    }

    public void addLast(T item) {
        if (size == 0) {
            array[first] = item;
            first = first - 1 < 0 ? capacity - 1 : first - 1;
        } else {
            array[last] = item;
            last = (last + 1) % capacity;
        }
        size++;
        if (size == capacity) {
            resize();
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size == 0) {
            return;
        }
        int index = (first + 1) % capacity;
        int tempSize = size;
        while ((tempSize--) > 1) {
            System.out.print(array[index] + " ");
            index = (index + 1) % capacity;
        }
        System.out.println(array[index]);
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size--;
        first = (first + 1) % capacity;
        T res = array[first];
        if ((size * 1.0 / capacity) < 0.251) {
            shrink();
        }
        return res;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size--;
        last = last - 1 < 0 ? capacity - 1 : last - 1;
        T res = array[last];
        if ((size * 1.0 / capacity) < 0.251) {
            shrink();
        }
        return res;
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return array[(first + index + 1) % capacity];
    }


//    public static void main(String[] args){
//        ArrayDeque<Integer> a = new ArrayDeque<>();
//        a.addLast(5);
//        a.addLast(6);
//        a.addLast(7);
//        a.addFirst(4);
//
//        a.addLast(8);
//        a.addLast(9);
//        a.addLast(10);
//        a.addFirst(3);
//        a.addFirst(2);
//        a.addFirst(1);
//
//        a.printDeque();
//
//        a.removeFirst();
//        a.removeLast();
//        a.removeLast();
//        a.removeLast();
//        a.removeLast();
//        a.removeLast();
//        a.printDeque();
//        System.out.println(a.capacity);
//    }
}
