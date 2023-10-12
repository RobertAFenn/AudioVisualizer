

public class LinkedListBag<T> implements BagInterface<T> {
    private Node<T> head;
    private int numberOfEntries = 0;
    private int capacity;

    public LinkedListBag(int capacity) {
        head = null;
        numberOfEntries = 0;
        this.capacity = capacity;
    }

    @Override
    public int getCurrentSize() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public boolean add(T newEntry) {
        if (numberOfEntries < capacity) {
            Node<T> newNode = new Node<>(newEntry);
            newNode.setNextNode(head);
            head = newNode;
            numberOfEntries++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            return null;
        }
        T removedData = head.getData();
        head = head.getNextNode();
        numberOfEntries--;
        return removedData;
    }

    @Override
    public boolean remove(T anEntry) {
        Node<T> current = head;
        Node<T> previous = null;
        while (current != null) {
            if (current.getData().equals(anEntry)) {
                if (previous == null) {
                    head = current.getNextNode();
                } else {
                    previous.setNextNode(current.getNextNode());
                }
                numberOfEntries--;
                return true;
            }
            previous = current;
            current = current.getNextNode();
        }
        return false;
    }

    @Override
    public void clear() {
        head = null;
        numberOfEntries = 0;
    }

    @Override
    public int getFrequencyOf(T anEntry) {
        int frequency = 0;
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(anEntry)) {
                frequency++;
            }
            current = current.getNextNode();
        }
        return frequency;
    }

    @Override
    public boolean contains(T anEntry) {
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(anEntry)) {
                return true;
            }
            current = current.getNextNode();
        }
        return false;
    }

    @Override
    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[numberOfEntries];
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            result[index] = current.getData();
            current = current.getNextNode();
            index++;
        }
        return result;
    }

    public void printAllItems() {
        Node<T> current = head;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNextNode();
        }
    }

    public T get(int index) {
        if (index < 0 || index >= numberOfEntries) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNextNode();
        }
        return current.getData();
    }

    private static class Node<T> {
        private T data;
        private Node<T> nextNode;

        Node(T data) {
            this.data = data;
            this.nextNode = null;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node<T> nextNode) {
            this.nextNode = nextNode;
        }
    }
}
