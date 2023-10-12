
public interface StackInterface<T> {
    public boolean push(T t);

    public T pop();

    public T peek();

    public int getSize();

    public boolean isEmpty();

    public void clear();

}
