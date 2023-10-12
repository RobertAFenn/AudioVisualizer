

public class driver1 {
    public static void main(String[] args) {
        LinkedListBag<String> mySongs = new LinkedListBag<String>(999);

        mySongs.add("Song 1");
        mySongs.add("Song 2");
        mySongs.add("Song 3");
        mySongs.add("Song 4");
        mySongs.add("Song 5");
        mySongs.printAllItems();
        System.out.println();

        // Convert the bag to an array
        Object[] objectArray = mySongs.toArray();
        String[] myArray = new String[objectArray.length];
        System.arraycopy(objectArray, 0, myArray, 0, objectArray.length);

        // Iterate over the array and print its contents
        for (String n : myArray) {
            System.out.println(n);
        }

        System.out.println();
        System.out.println("Current Size: " + mySongs.getCurrentSize());
        System.out.println("Is Empty: " + mySongs.isEmpty());

        // Access and iterate through the bag elements using a loop
        mySongs.printAllItems();
        System.out.println();

        mySongs.remove();
        mySongs.remove();

        String badSong = "Anything from Weezer";
        mySongs.add(badSong);
        mySongs.printAllItems();
        System.out.println();

        mySongs.remove(badSong);
        mySongs.printAllItems();

        System.out.println();
        for (int i = 0; i < mySongs.getCurrentSize(); i++) {

            System.out.println(mySongs.get(i));
        }

        mySongs.clear();
        System.out.println(mySongs.isEmpty());
        mySongs.printAllItems(); // should not return anything due to being true
        System.out.println();

        for (int i = 0; i < 10; i++) {
            mySongs.add(i + "");
            mySongs.add(badSong);

        }
        System.out.println(mySongs.isEmpty());
        mySongs.printAllItems();
        System.out.println(mySongs.getFrequencyOf(badSong));

        System.out.println(mySongs.remove("Non-existent Song"));
        System.out.println(mySongs.getFrequencyOf("Non-existent Song"));
        mySongs.clear();

        for (int i = 0; i < 1000; i++) {
            System.out.println(mySongs.add("Song " + (i + 1)));
        }
        System.out.println("Current size is " + mySongs.getCurrentSize());
        System.out.println(mySongs.get(0));
        System.out.println(mySongs.get(999 - 1));

        System.out.println("Below is an intentional error in a try and catch");
        try {
            System.out.println(mySongs.get(1000));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
