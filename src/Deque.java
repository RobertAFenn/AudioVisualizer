import java.util.ArrayList;

public class Deque<T> implements DequeInterface<T> {

    private ArrayList<T> myArr = new ArrayList<T>();

    @Override
    public void addToFront(T newEntry) {
        myArr.add(newEntry);
    }

    @Override
    public void addToBack(T newEntry) {
        myArr.add(myArr.size(), newEntry);
    }

    @Override
    public T removeFront() {
        if (isEmpty()) {
            throw new Error("Deque is empty");
        }

        T copy = myArr.get(0);
        myArr.remove(0);
        return copy;
    }

    @Override
    public T removeBack() {
        if (isEmpty()) {
            throw new Error("Deque is empty");
        }
        T copy = myArr.get(myArr.size() - 1);
        myArr.remove(myArr.size() - 1);
        return copy;
    }

    @Override
    public T getFront() {
        if (isEmpty()) {
            throw new Error("Deque is empty");
        }
        return myArr.get(0);
    }

    @Override
    public T getBack() {
        if (isEmpty()) {
            throw new Error("Deque is empty");
        }

        return myArr.get(myArr.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return myArr.size() == 0;
    }

    @Override
    public void clear() {
        myArr.clear();
    }

}
