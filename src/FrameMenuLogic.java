import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class FrameMenuLogic implements ActionListener {

    private JButton menuButton, importButton, visualizerButton, reportButton, libraryButton, mySongButton;
    private ArrayList<JButton> myButtons = new ArrayList<JButton>();
    private JPanel currentPanel;
    private JLabel topText;
    private Font customFont;
    private JLabel alertLabel;

    private LinkedListBag<Path> songPaths = new LinkedListBag<Path>(999);
    private Stack<String> songStack = new Stack<String>();
    private Deque<String> bugReportDeque = new Deque<String>();

    private GridBagConstraints gbc;

    FrameMenuLogic(JPanel panel) {

        currentPanel = panel;
        menuButton = new JButton("Return To Menu");
        visualizerButton = new JButton("Select Items");
        reportButton = new JButton("Report a bug");
        importButton = new JButton("Import New Files");
        libraryButton = new JButton("Load Library");

        menuButton.addActionListener(this);
        importButton.addActionListener(this);
        reportButton.addActionListener(this);
        visualizerButton.addActionListener(this);
        libraryButton.addActionListener(this);
        JButton[] Buttons = { menuButton, importButton, reportButton, visualizerButton, libraryButton };

        for (int i = 0; i < Buttons.length; i++) {
            JButton button = Buttons[i];
            myButtons.add(button);
        }

        loadCustomFont();

        currentPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        initializeMenu();
        initializeButtons();
    }

    private void loadCustomFont() {
        try {
            InputStream is = new FileInputStream(new File("VCR_OSD_MONO_1.001.ttf"));
            customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 35);
            System.out.println("Font name: " + customFont.getFontName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Component> getAllComponents(Container container) {
        Component[] components = container.getComponents();
        List<Component> componentList = new ArrayList<>();

        for (Component component : components) {
            componentList.add(component);
        }

        return componentList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton myButton = (JButton) e.getSource();
        String buttonText = myButton.getText();
        System.out.println(buttonText);
        if (buttonText.contains(".")) { // meaning its a song
            topText.setText(buttonText);

            try {
                Path newPath = null;
                for (int i = 0; i < songPaths.getCurrentSize(); i++) {
                    if (songPaths.get(i).toString().contains(buttonText)) {
                        System.out.println("Path Found");
                        newPath = songPaths.get(i);
                        i = songPaths.getCurrentSize(); // You can break out of the loop since you found the path.
                    } else {
                        System.out.println("No path found");
                    }
                }

                if (newPath != null) {

                    System.out.println("Imagine Music Being Playing Here...");

                    songStack.push(buttonText);
                    System.out.println(buttonText + " Added to the stack");

                } else {
                    System.out.println("No valid path found for audio file.");
                }
            } catch (Exception exp) {
                exp.printStackTrace();
                System.out.println("Error playing audio: " + exp.getMessage());
            }

            // Update the UI here within the EDT if you're working with a GUI

        } else {
            topText.setText("Audio Visualizer");
        }

        switch (buttonText) {
            case "Return To Menu":
                removeAllButTopLabel();
                initializeMenu();
                break;

            case "Select Items":
                removeAllButTopLabel();
                initializeButtons();

                // Create a new GridBagConstraints for libraryButton
                gbc.insets = new Insets(-10, 0, 0, 0);
                gbc.fill = GridBagConstraints.NONE;
                gbc.gridx = 0; // Place menuButton in column 0
                gbc.gridy = 1;
                gbc.weightx = 1; // Make menuButton span the entire space
                gbc.anchor = GridBagConstraints.CENTER;
                currentPanel.add(menuButton, gbc);
                gbc.gridy = 2;
                currentPanel.add(libraryButton, gbc);
                gbc.insets = new Insets(10, 0, 0, 0);
                break;

            case "Import New Files":
                System.out.println("Import button clicked");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(null);

                // Remove existing alert labels before adding a new one
                try {
                    if (alertLabel != null) {
                        currentPanel.remove(alertLabel);
                        currentPanel.revalidate();
                        currentPanel.repaint();
                    } else {
                        alertLabel = new JLabel();
                        // Assuming you have a Font object called customFont
                        Font newFont = customFont.deriveFont(15.0f); // Set the font size (16.0f in this example)
                        alertLabel.setFont(newFont); // Set the new font for your component
                    }
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }

                if (result == JFileChooser.APPROVE_OPTION) {
                    // The user selected a file
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    System.out.println("Selected File: " + filePath);
                    Path destinationDirectory = Paths.get("music"); // Adjust the destination directory as needed
                    gbc.insets = new Insets(0, 0, 0, 0); // Set the insets

                    try {
                        // Move the file to the destination directory
                        Files.move(selectedFile.toPath(), destinationDirectory.resolve(selectedFile.getName()),
                                StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File moved successfully.");
                        alertLabel.setText("Success! " + selectedFile.getName() + " has been added for selection!");
                        alertLabel.setForeground(Color.GREEN);
                        gbc.gridy = 4; // Set the grid position to 3
                        gbc.gridx = 0; // Set the grid column to 0
                        gbc.anchor = GridBagConstraints.CENTER; // Set the anchor to the center
                        currentPanel.add(alertLabel, gbc);
                        currentPanel.revalidate(); // Revalidate the panel to ensure the changes are applied
                        currentPanel.repaint();

                    } catch (IOException err) {
                        System.err.println("Error moving file: " + err.getMessage());
                        alertLabel.setText("ERROR moving file");
                        alertLabel.setForeground(Color.RED);
                        gbc.gridy = 4; // Set the grid position to 3
                        gbc.gridx = 0; // Set the grid column to 0
                        gbc.anchor = GridBagConstraints.CENTER; // Set the anchor to the center
                        currentPanel.add(alertLabel, gbc);
                        currentPanel.revalidate(); // Revalidate the panel to ensure the changes are applied
                        currentPanel.repaint();
                    }
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("File selection canceled");
                    alertLabel.setText("Selection canceled");
                    alertLabel.setForeground(Color.GRAY);
                    gbc.gridy = 4; // Set the grid position to 3
                    gbc.gridx = 0; // Set the grid column to 0
                    gbc.anchor = GridBagConstraints.CENTER; // Set the anchor to the center
                    currentPanel.add(alertLabel, gbc);
                    currentPanel.revalidate(); // Revalidate the panel to ensure the changes are applied
                    currentPanel.repaint();
                }

                gbc.insets = new Insets(10, 0, 10, 0); // Set the insets

                break;

            case "Report a bug":
                new BugReportWindow(bugReportDeque);

                break;

            case "Load Library":
                JFrame musicSelection = new JFrame("Selection");
                musicSelection.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                Path directory = Path.of("music");

                JPanel musicPanel = new JPanel();
                musicPanel.setLayout(new BoxLayout(musicPanel, BoxLayout.Y_AXIS)); // Stack elements vertically

                ScrollPane musicScrollPane = new ScrollPane();
                musicScrollPane.add(musicPanel);
                musicSelection.add(musicScrollPane); // Add ScrollPane to the frame

                // Calculate the frame size based on the number of buttons
                int frameWidth = 300;
                int frameHeight = 400; // Adjust the height based on button size
                musicSelection.setSize(frameWidth, frameHeight);

                Font smallerFont = customFont.deriveFont(10.0f); // Smaller font
                FontMetrics fm = musicPanel.getFontMetrics(smallerFont);

                // Find the maximum button width based on text length

                try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                    for (Path file : stream) {
                        if (Files.isRegularFile(file)) {

                            if (!songPaths.contains(file)) {
                                songPaths.add(file);
                            }
                            mySongButton = new JButton(file.getFileName().toString());
                            mySongButton.addActionListener(this);
                            mySongButton.setFont(smallerFont);
                            mySongButton.setVerticalAlignment(SwingConstants.CENTER); // Center text vertically
                            mySongButton.setBackground(Color.BLACK);
                            mySongButton.setForeground(Color.WHITE);
                            mySongButton.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Add a border
                            musicPanel.add(mySongButton);
                            // Update the maximum button width

                        }
                    }
                } catch (IOException err) {
                    err.printStackTrace();
                }
                System.out.println("Printing song's to terminal...");
                songPaths.printAllItems();

                // Set the same fixed size for all buttons based on the maximum button width
                Dimension buttonSize = new Dimension(280, 50);
                for (Component component : musicPanel.getComponents()) {
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        button.setPreferredSize(buttonSize);
                        button.setMaximumSize(buttonSize);
                        button.setMinimumSize(buttonSize);
                    }
                }

                // Center the musicScrollPane within the frame
                musicSelection.add(musicScrollPane, BorderLayout.CENTER);

                musicSelection.setBackground(Color.BLACK);
                musicSelection.setVisible(true);
                musicPanel.setBackground(Color.BLACK);
                musicSelection.setResizable(false);
                break;

            default:
                break;
        }
    }

    private void initializeMenu() {
        gbc.insets = new Insets(20, 0, 20, 0);
        topText = new JLabel("Audio Visualizer");
        topText.setFont(customFont);
        topText.setForeground(Color.RED);
        gbc.gridx = 0; // Center horizontally
        gbc.gridy = 0; // Center vertically
        gbc.anchor = GridBagConstraints.CENTER;
        currentPanel.add(topText, gbc);

        addComponentWithConstraints(visualizerButton, 1, GridBagConstraints.CENTER);
        addComponentWithConstraints(Box.createVerticalStrut(20), 2, GridBagConstraints.CENTER);
        addComponentWithConstraints(importButton, 2, GridBagConstraints.SOUTH);
        addComponentWithConstraints(reportButton, 3, GridBagConstraints.CENTER);

    }

    private void removeAllButTopLabel() {
        List<Component> components = getAllComponents(currentPanel);

        for (Component component : components) {
            if (component != topText) {
                currentPanel.remove(component);
            }
        }

        currentPanel.revalidate();
        currentPanel.repaint();
    }

    private void addComponentWithConstraints(Component component, int gridY, int anchor) {
        gbc.gridy = gridY;
        gbc.anchor = anchor;
        currentPanel.add(component, gbc);
    }

    private void initializeButtons() {
        for (int i = 0; i < myButtons.size(); i++) {

            JButton button = myButtons.get(i);
            System.out.println(button.getText());
            button.setPreferredSize(new Dimension(200, 50));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(0, 0, 0, 0));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            Font newFont = customFont.deriveFont(16.0f);
            button.setFont(newFont);

            button.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(new Color(0, 0, 0, 0));
                    button.setForeground(Color.GRAY);

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // Restore button appearance on mouse exit
                    button.setBackground(new Color(0, 0, 0, 0));
                    button.setForeground(Color.WHITE);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // Change button appearance on mouse press
                    button.setBackground(new Color(0, 0, 0, 0));
                    button.setForeground(Color.WHITE);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // Restore button appearance on mouse release
                    button.setBackground(new Color(0, 0, 0, 0));
                    button.setForeground(Color.GRAY);
                }
            });
        }

    }

}