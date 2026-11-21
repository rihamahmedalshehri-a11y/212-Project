package ridesharingsystem;

public class LinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private Node<T> current;
    private int size;

    public LinkedList() {
        head = null;
        tail = null;
        current = null;
        size = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }

        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> node = head;

        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }

        return node.getData();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean empty() {
        return isEmpty();
    }

    public void clear() {
        head = null;
        tail = null;
        current = null;
        size = 0;
    }

    public void insert(T data) {
        add(data);
        current = tail;
    }

    public void findFirst() {
        current = head;
    }

    public void findNext() {
        if (current != null) {
            current = current.getNext();
        }
    }

    public T retrieve() {
        if (current == null) {
            return null;
        }

        return current.getData();
    }

    public boolean last() {
        return current != null && current == tail;
    }

    public void insert_before_current(T data) {
        if (current == null) {
            return;
        }

        Node<T> newNode = new Node<>(data);

        if (current == head) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node<T> previous = head;

            while (previous != null
                    && previous.getNext() != current) {
                previous = previous.getNext();
            }

            if (previous == null) {
                return;
            }

            previous.setNext(newNode);
            newNode.setNext(current);
        }

        size++;
    }

    public void remove() {
        if (current == null) {
            return;
        }

        if (current == head) {
            head = head.getNext();
            current = head;

            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> previous = head;

            while (previous != null
                    && previous.getNext() != current) {
                previous = previous.getNext();
            }

            if (previous == null) {
                return;
            }

            previous.setNext(current.getNext());

            if (current == tail) {
                tail = previous;
                current = null;
            } else {
                current = previous.getNext();
            }
        }

        size--;
    }
}
