import java.nio.file.Path;
public interface IAudioVisualizer { // ? Extend JComponent

    /*
     * Should use variables such as
     * - Frequency
     * - Decibels
     * - Subdivided frequencies, average out the decibel over that range
     * Both should be arrays due to the fact that there will be lots of data at one
     * time
     */

    /*
     * Draw the data to the graph, humans can only hear up to 20k in frequencies, so
     * thats where most computers output
     * since we have 20k frequency's, different bars, of course considering a
     * machines performance, lower bars more performance
     * Divide the frequencies up into different categories (by 200 to get the 100
     * bars), because creating a new bar for every single frequency doesn't seem
     * possible without the use of a gpu, and is somewhat unneeded for this project
     */

    public void createVisualizerPanel(int[] frequencies, int[] decibels, int frequencySplit);

    /*
     * Grab Data Relating to the song files frequencies
     * Should take in the files path in order to get the song
     */
    public int[] listenToFrequencies(Path path);

    /*
     * Grab Data Relating to the song files decibels for given frequencies
     * Should take in the files path in order to get the song
     */

    public int[] listenToDecibels(Path path);

    // ! Helper methods below

    private void drawVisualizer(int[] Frequency, int[] decabel) {
        /*
         * Used as a helper method within createVisualizerPanel(), hence why this is
         * private
         */
    }

}
