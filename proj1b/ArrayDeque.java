public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    private void resize(int n) {
        T[] newArr = (T[]) new Object[n];
        for (int i = 1; i <= size; i++) {
            newArr[i] = items[(nextFirst + i) % items.length];
        }
        items = newArr;
        nextFirst = 0;
        nextLast = size + 1;
    }

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

//    public ArrayDeque(ArrayDeque other) {
//        items = (T[]) new Object[other.size()];
//        for (int i = 0; i < other.size(); i++) {
//            items[i] = (T) other.get(i);
//        }
//        nextFirst = other.nextFirst;
//        nextLast = other.nextLast;
//    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        size++;
        nextFirst = (nextFirst + items.length - 1) % items.length;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        size++;
        nextLast = (nextLast + 1) % items.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[(i + nextFirst + 1) % items.length] + " ");
        }
        System.out.println();
    }

    private void checkUsage() {
        if (items.length >= 16 && size * 4 < items.length) {
            resize(items.length  / 2);
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T first = items[(nextFirst + 1) % items.length];
        nextFirst = (nextFirst + 1) % items.length;
        size--;
        checkUsage();
        return first;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T last = items[(nextLast - 1 + items.length) % items.length];
        nextLast = (nextLast - 1 + items.length) % items.length;
        size--;
        checkUsage();
        return last;
    }

    public T get(int index) {
        return items[(nextFirst + 1 + index) % items.length];
    }
}
