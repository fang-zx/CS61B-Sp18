public interface Deque<T> {

    void addFirst(T item);
    void addLast(T item);
    boolean isEmpty();
    int size();
    void printDeque();
    T removeFirst();
    public T removeLast();
    public T get(int index);
}
