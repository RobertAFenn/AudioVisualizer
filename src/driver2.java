
public class driver2 {
    /*
     * Testing Stacks
     * FIFO
     */
    public static void main(String[] args) {
        Stack<String> myNames = new Stack<String>();

        System.out.println("Size " + myNames.getSize());
        System.out.println("Empty? " + myNames.isEmpty());

        System.out.println("\nAdding Names...");

        myNames.push("Amy");
        myNames.push("John");
        myNames.push("Bob");
        myNames.push("Carl");
        myNames.push("Xavier");

        while (myNames.getSize() != 0) {
            System.out.println("Size " + myNames.getSize());
            System.out.println("Empty? " + myNames.isEmpty());
            System.out.println("Peek " + myNames.peek());
            System.out.println("Pop " + myNames.pop());
            System.out.println();
        }

        try {
            System.out.println("Peek " + myNames.peek());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        try {
            System.out.println("Pop " + myNames.pop());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
        System.out.println("Size " + myNames.getSize());
        System.out.println("Empty? " + myNames.isEmpty());

        System.out.println("\nAdding More Names...");
        for (int i = 0; i < 10; i++) {
            myNames.push("Name " + i);
        }

        while (myNames.getSize() != 0) {
            System.out.println("Size " + myNames.getSize());
            System.out.println("Empty? " + myNames.isEmpty());
            System.out.println("Peek " + myNames.peek());
            System.out.println("Pop " + myNames.pop());
            System.out.println();
        }

        System.out.println("Size " + myNames.getSize());
        System.out.println("Empty? " + myNames.isEmpty());

        System.out.println("\nAdding More Names...");
        for (int i = 0; i < 10; i++) {
            myNames.push("Name " + i);
        }

        System.out.println("Using clear()");
        myNames.clear();
        System.out.println("Size " + myNames.getSize());
        System.out.println("Empty? " + myNames.isEmpty());

    }
}
