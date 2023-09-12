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

                break;

            case "Import New Files":
                System.out.println("Import button clicked");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    // The user selected a file
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    System.out.println("Selected File: " + filePath);

                    // Specify the destination directory
                    Path destinationDirectory = Paths.get("music"); // Adjust the destination directory as needed

                    try {
                        // Move the file to the destination directory
                        Files.move(selectedFile.toPath(), destinationDirectory.resolve(selectedFile.getName()),
                                StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File moved successfully.");
                        importButton.setForeground(Color.GREEN);
                    } catch (IOException err) {
                        System.err.println("Error moving file: " + err.getMessage());
                        importButton.setForeground(Color.RED);
                    }
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("File selection canceled");
                }
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
            button.setBackground(new Color(255, 255, 255, 100));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            Font newFont = customFont.deriveFont(16.0f);
            button.setFont(newFont);
        }
    }

}
