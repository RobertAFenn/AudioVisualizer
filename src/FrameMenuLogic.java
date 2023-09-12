import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class FrameMenuLogic implements ActionListener {

    public JButton menuButton, importButton, visualizerButton;
    private JPanel currentPanel;
    private JLabel topText;
    private Font customFont;
    private JLabel alertLabel;

    private GridBagConstraints gbc;

    FrameMenuLogic(JPanel panel) {
        currentPanel = panel;
        menuButton = new JButton("Return To Menu");
        visualizerButton = new JButton("Select Items");
        importButton = new JButton("Import New Files");
        menuButton.addActionListener(this);
        importButton.addActionListener(this);
        visualizerButton.addActionListener(this);

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

        switch (buttonText) {
            case "Return To Menu":
                removeAllButTopLabel();
                initializeMenu();
                break;

            case "Select Items":
                removeAllButTopLabel();
                gbc.insets = new Insets(-20, 0, 0, 0);
                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.CENTER;
                currentPanel.add(menuButton, gbc);

                Path directory = Path.of("music");
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                    for (Path file : stream) {
                        if (Files.isRegularFile(file)) {

                            System.out.println(file.getFileName());
                            JButton mySongButton = new JButton(file.getFileName().toString());

                            mySongButton.addActionListener(this);

                        }
                    }
                } catch (IOException err) {
                    err.printStackTrace();
                }
                initializeButtons();

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
                    // TODO: handle exception
                }

                if (result == JFileChooser.APPROVE_OPTION) {
                    // The user selected a file
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    System.out.println("Selected File: " + filePath);
                    // Specify the destination directory
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

            default:
                break;
        }
    }

    private void initializeMenu() {
        gbc.insets = new Insets(20, 0, 20, 0);
        topText = new JLabel("Audio Visualizer");
        topText.setFont(customFont);
        topText.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        currentPanel.add(topText, gbc);

        addComponentWithConstraints(visualizerButton, 1, GridBagConstraints.CENTER);
        addComponentWithConstraints(Box.createVerticalStrut(20), 2, GridBagConstraints.CENTER);
        addComponentWithConstraints(importButton, 3, GridBagConstraints.SOUTH);
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
        for (JButton button : Arrays.asList(menuButton, importButton, visualizerButton)) {
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
