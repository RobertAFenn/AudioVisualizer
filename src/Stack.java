
import java.util.ArrayList;

public class Stack<T> implements StackInterface<T> {

    private ArrayList<T> arr = new ArrayList<T>();

    @Override
    public boolean push(T t) {
        arr.add(t);
        return true;
    }

    @Override
    public T pop() {

        if (isEmpty()) {
            try {
                throw new Exception("Cannot Pop, Stack is Empty!");
            } catch (Exception e) {

            }
        }

        T t = arr.get(arr.size() - 1);
        arr.remove(getSize() - 1);
        return t;
    }

    @Override
    public T peek() {

        if (isEmpty()) {
            try {
                throw new Exception("Cannot Peek, Stack is Empty!");
            } catch (Exception e) {
            }

        }

        return arr.get(arr.size() - 1);
    }

    @Override
    public int getSize() {
        return arr.size();
    }

    @Override
    public boolean isEmpty() {
        return arr.size() == 0;
    }

    @Override
    public void clear() {
        arr.clear();
    }

}
