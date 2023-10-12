public class driver3 {
    public static void main(String args[]) {
        String name;
        Deque<String> myDeque = new Deque<String>();
        myDeque.addToBack("Jim");
        myDeque.addToBack("Jess");
        myDeque.addToBack("Jill");
        myDeque.addToBack("Jane");
        while (!myDeque.isEmpty()) {
            name = myDeque.removeFront();
            System.out.println(name);
        }
        System.out.println();

        myDeque.addToBack("Jim");
        myDeque.addToBack("Jess");
        myDeque.addToBack("Jill");
        myDeque.addToBack("Jane");

        name = myDeque.getFront();
        myDeque.addToBack(name);
        myDeque.removeFront();
        myDeque.addToFront(myDeque.removeBack());

        while (!myDeque.isEmpty()) {
            name = myDeque.removeFront();
            System.out.println(name);
        }

    }
}
